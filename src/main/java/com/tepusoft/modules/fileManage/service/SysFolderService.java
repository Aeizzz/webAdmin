/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.fileManage.service;

import java.util.ArrayList;
import java.util.List;

import com.tepusoft.common.persistence.Page;
import com.tepusoft.modules.fileManage.dao.SysFileRecordDao;
import com.tepusoft.modules.fileManage.entity.SysFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tepusoft.common.service.TreeService;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.fileManage.entity.SysFolder;
import com.tepusoft.modules.fileManage.dao.SysFolderDao;
import com.tepusoft.modules.fileManage.dao.SysFileDao;

/**
 * 树结构Service
 *
 * @author liy
 * @version 2017-07-05
 */
@Service
@Transactional(readOnly = true)
public class SysFolderService extends TreeService<SysFolderDao, SysFolder> {
    @Autowired
    private SysFolderDao sysFolderDao;
    @Autowired
    private SysFileDao sysFileDao;
    @Autowired
    private SysFileRecordDao sysFileRecordDao;

    public SysFolder get(String id) {
        return super.get(id);
    }

    public List<SysFolder> findList(SysFolder sysFolder) {
        if (sysFolder == null || sysFolder.getId() == null) {
            sysFolder.setId("0");
        }
        return super.findList(sysFolder);
    }

    public Page<SysFolder> findPage(Page<SysFolder> page, SysFolder sysFile) {
        List list = new ArrayList();
        list.addAll(sysFolderDao.findList(sysFile));
        SysFile file = new SysFile();
        file.setParentId(sysFile.getId());
        List<SysFile> sysFileList=sysFileDao.findList(file);
        for(SysFile file1:sysFileList){
            file1.setName(file1.getName()+"."+file1.getFileType());
        }
        list.addAll(sysFileDao.findList(file));

        if (list.size() > 0) {
            page.setList(list);
        }
        return page;
    }

    public List<SysFolder> findAllList(SysFolder sysFolder) {
        return sysFolderDao.findAllList(sysFolder);
    }

    @Transactional(readOnly = false)
    public void save(SysFolder sysFolder) {
        if (StringUtils.isBlank(sysFolder.getId())) {
            sysFolder.setClickNum(0);
            sysFolder.setDownloadNum(0);
            sysFolder.setType("0");
            sysFolder.setFileSize(0L);
        }
        super.save(sysFolder);
    }

    //文件夹及其子文件的删除
    @Transactional(readOnly = false)
    public void delete(SysFolder sysFolder) {
        super.delete(sysFolder);
        List<SysFolder> children = this.findChild(sysFolder);
        SysFile sysFile = new SysFile();
        sysFile.setParentIds(("%," + sysFolder.getId() + ",%"));
        List<SysFile> children2 = sysFileDao.findByParentIdsLike(sysFile);
//		删除文件夹
        for (SysFolder child : children) {
            super.delete(child);
        }
//		删除文件
        for (SysFile child : children2) {
            sysFileDao.delete(child);
        }

    }

    public List<SysFolder> findChild(SysFolder sysFolder) {
        SysFolder parent = new SysFolder();
        parent.setParentIds(("%," + sysFolder.getId() + ",%"));
        List<SysFolder> list = sysFolderDao.findByParentIdsLike(parent);
        return list;
    }

    //   检测该文件夹名称 在同一父文件夹中是否存在
    public boolean checkByName(SysFolder sysFolder) {
        List<SysFolder> list = sysFolderDao.findByName(sysFolder);
        return list.size() > 0;
    }

    //	更新文件大小
    @Transactional(readOnly = false)
    public void updateSize(String ids, Long size, boolean type) {
        String[] id = ids.split(",");
        for (int i = 1; i < id.length; i++) {
            SysFolder folder = this.get(id[i]);
            if (type) {
                folder.setFileSize(folder.getFileSize() + size);
            } else {
                folder.setFileSize(folder.getFileSize() - size);
            }
            this.save(folder);
        }
    }
}