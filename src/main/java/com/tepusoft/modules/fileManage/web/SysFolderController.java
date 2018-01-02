/**
 * Copyright &copy; 2012-2016<a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.fileManage.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.modules.fileManage.entity.SysFile;
import com.tepusoft.modules.fileManage.service.SysFileRecordService;
import com.tepusoft.modules.fileManage.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.fileManage.entity.SysFolder;
import com.tepusoft.modules.fileManage.service.SysFolderService;

/**
 * 树结构Controller
 *
 * @author liy
 * @version 2017-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/fileManage/sysFolder")
public class SysFolderController extends BaseController {

    @Autowired
    private SysFolderService sysFolderService;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private SysFileRecordService sysFileRecordService;

    @ModelAttribute
    public SysFolder get(@RequestParam(required = false) String id) {
        SysFolder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = sysFolderService.get(id);
        }
        if (entity == null) {
            entity = new SysFolder();
        }
        return entity;
    }

    @RequestMapping(value = {"index"})
    public String index(SysFolder sysFolder, Model model) {
        return "modules/fileManage/sysFolderList";
    }


    @RequestMapping(value = {"list", ""})
    public String list(SysFolder sysFolder, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/fileManage/sysFolderList";
    }

    @ResponseBody
    @RequestMapping(value = {"datalist"})
    public Page<SysFolder> datalist(SysFolder sysFolder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysFolder> page = sysFolderService.findPage(new Page<SysFolder>(request, response), sysFolder);
        return page;
    }

    @RequestMapping(value = "form")
    public String form(SysFolder sysFolder, Model model, HttpServletRequest request,String type) {
//       type为1则是文件对象
        if ("1".equals(type)) {
            SysFile sysFile = sysFileService.get(sysFolder.getId());
            sysFile.setParent(sysFolderService.get(sysFile.getParentId()));
            model.addAttribute("sysFolder", sysFile);
        } else {
            if (sysFolder.getParent() != null && StringUtils.isNotBlank(sysFolder.getParent().getId())) {
                sysFolder.setParent(sysFolderService.get(sysFolder.getParent().getId()));
                model.addAttribute("sysFolder", sysFolder);
            }
        }
        return "modules/fileManage/sysFolderForm";
    }

    @RequestMapping(value = "moveSave")
    @ResponseBody
    public JqueryResult moveSave(SysFolder sysFolder, String parentId, String fileIds, String folderIds, Model model, HttpServletRequest request) {
        SysFolder parent = sysFolderService.get(parentId);
        sysFolder.setParent(parent);
        if (!folderIds.isEmpty()) {
            String[] id = folderIds.split(",");
            for (int i = 0; i < id.length; i++) {
                sysFolder = sysFolderService.get(id[i]);
                sysFolder.setParentId(parentId);
                sysFolder.setParent(parent);
                this.saveFolder(sysFolder);
            }
        }
        if (!fileIds.isEmpty()) {
            String[] id = fileIds.split(",");
            for (int i = 0; i < id.length; i++) {
                SysFile sysFile = sysFileService.get(id[i]);
                sysFile.setParent(parent);
                this.saveFile(sysFile);
            }
        }
        String message = "成功移动到文件夹";
        return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public JqueryResult save(SysFolder sysFolder,String type, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String message = beanValidatorStr(model, sysFolder);
        if (StringUtils.isNotBlank(message)) {
            return new JqueryResult.Builder(FAIL_CODE).message(message).build();
        }
        if ("1".equals(type)) {
//          文件的修改  此时传回的sysFolder实际是一个文件
            SysFile sysFile = sysFileService.get(sysFolder.getId());
            sysFile.setName(sysFolder.getName());
            sysFile.setRemarks(sysFolder.getRemarks());
            sysFile.setParent(sysFolderService.get(sysFolder.getParent().getId()));
            message = this.saveFile(sysFile);
        } else {
            if (StringUtils.isNotBlank(sysFolder.getId())) {
                message = this.saveFolder(sysFolder);
            } else {

                if(!sysFolderService.checkByName(sysFolder)){
                    sysFolderService.save(sysFolder);
                    message = "新增文件夹成功";
                }else{
                    message = "该文件名已存在";
                    return new JqueryResult.Builder(FAIL_CODE).message(message).build();
                }
            }
        }
        return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public JqueryResult delete(String fileIds, String folderIds, SysFolder sysFolder, RedirectAttributes redirectAttributes) {
//        文件夹的删除
        if (!folderIds.isEmpty()) {
            String[] id = folderIds.split(",");
            for (int i = 0; i < id.length; i++) {
                sysFolder = this.get(id[i]);
                sysFolderService.delete(sysFolder);
                sysFolderService.updateSize(sysFolder.getParentIds(), sysFolder.getFileSize(), false);
            }
        }
//        文件的删除
        if (!fileIds.isEmpty()) {
            String[] id = fileIds.split(",");
            for (int i = 0; i < id.length; i++) {
                SysFile sysFile = sysFileService.get(id[i]);
                sysFileService.delete(sysFile);
                sysFolderService.updateSize(sysFile.getParentIds(), sysFile.getFileSize(), false);
            }
        }
        String message = "删除文件成功";
        return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();
    }

    @ResponseBody
    @RequestMapping(value = "addRecord")
    public String addRecord(SysFolder sysFolder, RedirectAttributes redirectAttributes) {
        //新增文件夹点击记录 0文件夹,0点击
        if ("0".equals(sysFolder.getType())) {
            sysFolder.setClickNum(sysFolder.getClickNum() + 1);
            sysFolderService.save(sysFolder);
        } else {
            SysFile sysFile = sysFileService.get(sysFolder.getId());
            sysFile.setClickNum(sysFile.getClickNum() + 1);
            sysFileService.save(sysFile);
        }
        sysFileRecordService.addRecord(sysFolder.getId(), sysFolder.getType(), "0");
        return "0";
    }

    @ResponseBody
    @RequestMapping(value = "tree")
    public List<Map<String, Object>> tree(@RequestParam(required = false) String id, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        if (StringUtils.isBlank(id)) {
            List<SysFolder> list = sysFolderService.findList(new SysFolder());
            map.put("id", list.get(0).getId());
            map.put("text", list.get(0).getName());
            map.put("state", "closed");
            mapList.add(map);
        } else {
            List<SysFolder> list = sysFolderService.findList(sysFolderService.get(id));
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> child = Maps.newHashMap();
                SysFolder e = list.get(i);
                child.put("id", list.get(i).getId());
                child.put("text", list.get(i).getName());
                child.put("state", "closed");
                mapList.add(child);
            }

        }
        return mapList;
    }

    @RequestMapping(value = "upload")
    public String upload(SysFolder sysFolder, Model model) {
        model.addAttribute("sysFolder", sysFolder);
        return "modules/fileManage/uploadFile";
    }

    public String saveFolder(SysFolder sysFolder) {
        String oldParentIds = sysFolder.getParentIds();
        sysFolderService.save(sysFolder);
        String newParentIds = sysFolder.getParentIds();
        //   更新父文件夹的大小 旧的－，新的+
        if(StringUtils.isNotBlank(oldParentIds)&&StringUtils.isNotBlank(newParentIds)){
            sysFolderService.updateSize(oldParentIds, sysFolder.getFileSize(), false);
            sysFolderService.updateSize(newParentIds, sysFolder.getFileSize(), true);
        }

        String message = "保存文件夹成功";
        return message;
    }

    public String saveFile(SysFile sysFile) {
//            得到旧的parentIds
        String oldParentIds = sysFile.getParentIds();
        //     保存
        sysFileService.save(sysFile);
//            得到新的parentIds
        String newParentIds = sysFile.getParentIds();
        //   更新父文件夹的大小 旧的－，新的+
        if(StringUtils.isNotBlank(oldParentIds)&&StringUtils.isNotBlank(newParentIds)) {
            sysFolderService.updateSize(oldParentIds, sysFile.getFileSize(), false);
            sysFolderService.updateSize(newParentIds, sysFile.getFileSize(), true);
        }
        String message = "保存文件成功";
        return message;
    }
}