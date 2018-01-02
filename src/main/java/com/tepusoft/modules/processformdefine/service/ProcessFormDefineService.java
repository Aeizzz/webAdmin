package com.tepusoft.modules.processformdefine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.processformdefine.dao.ProcessFormDefineDao;
import com.tepusoft.modules.processformdefine.entity.TableColumnEntity;

/**
 * 订单管理service层
 * @author wxp
 * @version 2017-05-05
 */
@Service
@Transactional(readOnly = true)
public class ProcessFormDefineService extends CrudService<ProcessFormDefineDao,TableColumnEntity>{
	
	@Autowired
	private ProcessFormDefineDao processFormDefineDao;
	

	/**
	 * 根据表名获取字段名称
	 * @param tableName
	 */
	public List<Map<String,Object>> getColumnsList(String tableName,List<String> pageColumnNameList) {
		List<Map<String,Object>> sourceList=processFormDefineDao.findListByTableName(tableName);
		List<Map<String,Object>> targetList=new ArrayList<Map<String, Object>>();
		for(Map<String,Object> sourceMap:sourceList){
			Map<String,Object> targetMap=new HashMap<String, Object>();
			for(Entry<String, Object> entry:sourceMap.entrySet()){
//				System.out.println("key="+entry.getKey().replace("_", ""));
//				System.out.println("value="+entry.getValue());
				for(String pageColumnName:pageColumnNameList){
					//System.out.println(pageColumnName);
					if(pageColumnName.equalsIgnoreCase(entry.getKey().replace("_", ""))){
						targetMap.put(pageColumnName,entry.getValue());
						break;
					}
				}
			}
			targetList.add(targetMap);
		}
		return targetList;
	}
	
	
	public Map<String,String> getColumnsByTableName(String tableName) {
		List<TableColumnEntity> columnEntityList=processFormDefineDao.getColumnsByTableName(tableName);
		Map<String,String> pageColumnNameList= new LinkedHashMap<String, String>();
		if(columnEntityList!=null && columnEntityList.size()>0){
			for(TableColumnEntity tableColumnEntity:columnEntityList){
				String pageColumnName=tableColumnEntity.getPageColumnName();
				pageColumnNameList.put(pageColumnName, tableColumnEntity.getChineseName());
			}
		}
		return pageColumnNameList;
	}
	
	
	public List<TableColumnEntity> getColumnEntityList(String tableName) {
		List<TableColumnEntity> columnEntityList=processFormDefineDao.getColumnsByTableName(tableName);
		return columnEntityList;
	}
	
	/**
	 * 新增数据保存
	 * @param tableName
	 * @param columnEntityList
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveInfo(String tableName,List<TableColumnEntity> columnEntityList){
		int count=processFormDefineDao.saveInfo(tableName,columnEntityList);
		return count;
		
	}
	
	/**
	 * 修改数据保存
	 * @param tableName
	 * @param columnEntityList
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateInfo(String tableName,List<TableColumnEntity> columnEntityList,Object id){
		int count=processFormDefineDao.updateInfo(tableName,columnEntityList,id);
		return count;
		
	}
	
	public Map<String,Object> getInfo(String id,String tableName,List<String> pageColumnNameList){
		Map<String,Object> columnEntityMap=processFormDefineDao.getInfo(id,tableName);
		Map<String,Object> targetMap=new HashMap<String, Object>();
		for(Entry<String, Object> entry:columnEntityMap.entrySet()){
			for(String pageColumnName:pageColumnNameList){
				if(pageColumnName.equalsIgnoreCase(entry.getKey().replace("_", ""))){
					targetMap.put(pageColumnName,entry.getValue());
					break;
				}
			}
		}
		return targetMap;
	}
	
	/**
	 * 根据id和表名删除数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int deleteInfo(String tableName,String id){
		int count=processFormDefineDao.deleteInfo(tableName,id);
		return count;
		
	}
	
}
