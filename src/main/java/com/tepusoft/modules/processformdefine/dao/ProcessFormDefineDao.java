package com.tepusoft.modules.processformdefine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tepusoft.common.persistence.CrudDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.processformdefine.entity.TableColumnEntity;

/**
 * 订单管理dao层
 * @author wxp
 * @version 2017-05-05
 */

@MyBatisDao
public interface ProcessFormDefineDao extends CrudDao<TableColumnEntity>{
	
	public List<TableColumnEntity> getColumnsByTableName(String tableName);
	
	public List<Map<String,Object>> findListByTableName(@Param(value="tableName")String tableName);
	
	public int saveInfo(@Param(value="tableName")String tableName,@Param(value="list")List<TableColumnEntity> columnEntityList);
	
	public int updateInfo(@Param(value="tableName")String tableName,@Param(value="list")List<TableColumnEntity> columnEntityList,@Param(value="id")Object id);
	
	public Map<String,Object> getInfo(@Param(value="id")String id,@Param(value="tableName")String tableName);
	
	public int deleteInfo(@Param(value="tableName")String tableName,@Param(value="id")String id);
	
}
