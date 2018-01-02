package com.tepusoft.modules.demo.service;

import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.service.BaseService;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.demo.dao.DesginFieldDao;
import com.tepusoft.modules.demo.dao.DesginTableDao;
import com.tepusoft.modules.demo.entity.DesginField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * Created by liying on 2017/6/22.
 */
@Service
@Transactional(readOnly = true)
public class DesginFiledService extends BaseService {
    @Autowired
    private DesginTableDao desginTableDao;
    @Autowired
    private DesginFieldDao desginFieldDao;

      public DesginField getField(String id){
             return desginFieldDao.get(id);
     }

    @Transactional(readOnly = false)
    public void save(List<DesginField>  fieldList ) {
        for(DesginField field : fieldList) {
            if (StringUtils.isBlank(field.getId())) {
                field.preInsert();
                desginFieldDao.insert(field);
            } else {
                field.preUpdate();
                desginFieldDao.update(field);
            }
        }
    }
    public Page<DesginField> find(Page<DesginField> page,DesginField desginField) {
        desginField.setPage2(page);
        if(desginField.getTableId()!=null){
            page.setList(desginFieldDao.findList(desginField));
        }
        return page;
    }
    @Transactional(readOnly = false)
    public void delete(DesginField desginField) {
          desginFieldDao.delete(desginField);
    }

}
