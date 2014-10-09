package com.ail.creyate.generic_spring.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ail.creyate.generic_spring.db.beans.LogsBean;
import com.ail.creyate.generic_spring.models.LogsModel;
import com.ail.creyate.generic_spring.utils.Constants;
import com.ail.creyate.generic_spring.utils.GenericUtils;

@Controller
@RequestMapping(value="logs")
public class LogsController {
	
	@Autowired(required=true)
	protected  LogsModel logsModel;

	@RequestMapping(value="/fetchLogs/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public String fetchLogs(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		String pageNumber = GenericUtils.getRequestParameter(request, "pageNumber", "0");
		int resultsPerPage = Integer.parseInt(GenericUtils.getRequestParameter(request, "resultsPerPage", "10"));
		int startIndex = Integer.parseInt(pageNumber)*resultsPerPage;
		int dbHitFlag = Integer.parseInt(GenericUtils.getRequestParameter(request, "dbHitFlag", "1"));
		int numberOfResults,numberOfPages;
		
		if(dbHitFlag==1){
			numberOfResults = logsModel.getNumberOfResultsForId(id,null);
			numberOfPages = numberOfResults/resultsPerPage;
			if(numberOfResults>numberOfPages*resultsPerPage){
				numberOfPages++;
			}
		}
		else{
			numberOfPages=Integer.parseInt(GenericUtils.getRequestParameter(request, "numberOfPages", "0"));
		}

		List<LogsBean> logsBeans = logsModel.getAllLogsForIdByLimit(id, startIndex, resultsPerPage, "order by modified_time desc");
		List<LogsBean> formattedLogBeans = new ArrayList<LogsBean>();
		for(int i = 0; i < logsBeans.size(); i++){
			LogsBean logsBean = logsBeans.get(i);
			String changedFieldStr = logsBean.getChangedFields();
			int type = GenericUtils.getDataType(changedFieldStr);
			changedFieldStr = GenericUtils.convertToFormattedString(changedFieldStr, type);
			if(StringUtils.trimToNull(changedFieldStr) != null){
				String pageId = logsBean.getPageId();
				if(pageId.equals(Constants.FABRICS_UPLOAD_IMAGES_PAGE_ID)) {
					changedFieldStr = "Uploaded fabric images<br><br>" + changedFieldStr;
				} else if (pageId.equals(Constants.PRODUCTS_UPLOAD_IMAGES_PAGE_ID)) {
					changedFieldStr = "Uploaded product images<br><br>" + changedFieldStr;
				} else if(pageId.equals(Constants.COPY_PAGE_ID)) {
					changedFieldStr = "Copied Ids:" + id + " from " + changedFieldStr; 
				} else if(pageId.equals(Constants.COPY_TO_PROD_PAGE_ID)) {
					changedFieldStr = "Copied Ids:" + id + " to production"; 
				}
				
				logsBean.setChangedFields(changedFieldStr);
				formattedLogBeans.add(logsBean);
			}
		}
		request.setAttribute("resultsPerPage",""+resultsPerPage);
		request.setAttribute("numberOfPages",""+numberOfPages);
		request.setAttribute("pageNumber", ""+pageNumber);
		request.setAttribute("logs", formattedLogBeans);
		request.setAttribute("id", id);
		return Constants.LOGS_VIEW; 
	}

	


}







