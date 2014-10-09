package com.ail.creyate.generic_spring.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ail.creyate.generic_spring.db.beans.LogsBean;
import com.ail.creyate.generic_spring.db.dao.LogsDAO;
import com.ail.creyate.generic_spring.utils.DateUtils;

@Service
public class LogsModel {

	@Autowired(required=true)
	private LogsDAO logsDAO;

	public  void addInfo(LogsBean logsBean) {
		logsDAO.create(logsBean, null);
	}
	
	public void log(String changedFields,String pageId, String name, String email,String id){
		if(changedFields != null && changedFields.length() > 0 && !changedFields.equals("{}") && !changedFields.equals("[]")){
			LogsBean logsBean = new LogsBean();
			logsBean.setUserName(name);
			logsBean.setEmail(email);
			logsBean.setChangedFields(changedFields);
			logsBean.setPageId(pageId);
			logsBean.setItemId(id);
			logsBean.setModifiedTime(DateUtils.getCurrentDateTimeINYYYYMMDDHHmmss());
			addInfo(logsBean);
		}
	}
	
	public LogsBean getLastUpdatedUserInfo(String pageId, String itemId){
		return logsDAO.getLastUpdatedUserInfo(pageId, itemId, null);
	}
	
	public List<Serializable> fetchData(String query){
		List<Serializable> objects = logsDAO.fetchData(query,null);
		if(objects != null && objects instanceof List<?>){
			return objects;
		}
		return null;
	}
	
	public int getNumberOfResultsForId(String id, String key) {
		int result = logsDAO.getNumberOfResultsForId(id,key,null);
		return result;
	}	 
	
	public List<LogsBean> getAllLogsForIdByLimit(String id, int startIndex, int maxResults, String key) {

		List<Serializable> logsSer;
		if(key==null){
			logsSer = logsDAO.fetchDataByLimit(" from LogsBean where item_id='"+id+"'", startIndex,maxResults, null);
		}
		else{
			logsSer = logsDAO.fetchDataByLimit(" from LogsBean where item_id='"+id+"' "+key, startIndex,maxResults, null);
		}
		List<LogsBean> logs = new ArrayList<LogsBean>();
		for(int i = 0; i < logsSer.size(); i++) {
			LogsBean productsBean = (LogsBean)logsSer.get(i);
			logs.add(productsBean);
		}
		return logs;

	}
	
}
