
package com.tepusoft.common.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tepusoft.common.utils.IdGen;
import com.tepusoft.modules.sys.entity.Office;
import com.tepusoft.modules.sys.entity.User;
import com.tepusoft.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 企业数据Entity类
 *
 * @author xqz
 * @version 2017-06-05
 */
public abstract class CompanyDataEntity<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;

    private String companyId;		// 商户ID

    public CompanyDataEntity() {
        super();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public CompanyDataEntity(String id) {
        super(id);
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
