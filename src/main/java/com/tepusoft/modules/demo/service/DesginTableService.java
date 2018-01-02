package com.tepusoft.modules.demo.service;

import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.service.BaseService;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.demo.dao.DesginFieldDao;
import com.tepusoft.modules.demo.dao.DesginTableDao;

import com.tepusoft.modules.demo.entity.DesginTable;
import com.tepusoft.modules.demo.entity.DesginField;
import com.tepusoft.modules.gen.util.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class DesginTableService extends BaseService {
    @Autowired
    private DesginTableDao desginTableDao;
    @Autowired
    private DesginFieldDao desginFieldDao;

    public DesginTable get(String id) {
        DesginTable desginTable = desginTableDao.get(id);
        DesginField desginField = new DesginField();
        desginField.setDesginTable(new DesginTable(desginTable.getId()));
        desginTable.setFieldList(desginFieldDao.findList(desginField));
        return desginTable;
    }

    public Page<DesginTable> find(Page<DesginTable> page, DesginTable desginTable) {
        desginTable.setPage2(page);
        page.setList(desginTableDao.findList(desginTable));
        return page;
    }

    public List<DesginTable> findAll() {
        return desginTableDao.findAllList(new DesginTable());
    }
    /**
     * 验证表名是否可用，如果已存在，则返回false
     * @param
     * @return
     */
    public boolean checkTableName(String tableName){

        if (StringUtils.isBlank(tableName)){
            return true;
        }
        DesginTable desginTable = new DesginTable();
        desginTable.setTableName(tableName);
        List<DesginTable> list = desginTableDao.findList(desginTable);
        return list.size() == 0;
    }


    @Transactional(readOnly = false)
    public void save(DesginTable desginTable) {
        if (StringUtils.isBlank(desginTable.getId())){
            desginTable.preInsert();
            desginTable.setIsDbsynch("0");
            desginTableDao.insert(desginTable);

        }else{
            desginTable.preUpdate();
            desginTableDao.update(desginTable);
        }

    }
    @Transactional(readOnly = false)
    public void createTable(DesginTable desginTable) {
//       根据Id得到desginTable对象中数据
         desginTable =desginTableDao.get(desginTable.getId());

         //查询表字段list
        DesginField desginField=new DesginField();
        desginField.setTableId(desginTable.getTableName());
        List<DesginField> desginFields  = desginFieldDao.findList(desginField);
//填充到desginTable 对象中
        desginTable.setFieldList(desginFields);
//        同步数据库创建表
        desginTableDao.createTable(desginTable);
//设置同步数据库标记
        desginTable.setIsDbsynch("1");
        desginTable.preUpdate();
        desginTableDao.update(desginTable);
    }

/*
更新表结构
 */
    @Transactional(readOnly = false)
    public void updateTable(DesginTable desginTable) {
//       根据Id得到desginTable对象中数据
        desginTable =desginTableDao.get(desginTable.getId());
//        删除以前备份的表
        desginTableDao.dropReTable(desginTable);
        //备份现在表
        desginTableDao.reTable(desginTable);
//        删除表
        desginTableDao.dropTable(desginTable);
//        创建表
        this.createTable(desginTable);
    }

    @Transactional(readOnly = false)
    public void delete(DesginTable desginTable) {
        desginTableDao.delete(desginTable);

    }

}