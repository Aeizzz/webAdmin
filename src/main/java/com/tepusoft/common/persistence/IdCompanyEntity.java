package com.tepusoft.common.persistence;

import com.tepusoft.common.persistence.DataEntity;

/**
 * 所有的关于业务上实体类必须继承IdCompanyEntity
 *
 * Created by wwhui on 2017-5-28.
 */
public abstract class IdCompanyEntity<T> extends DataEntity<T>{
    /***
     * 每个商户的id
     */
    private String companyId;
}
