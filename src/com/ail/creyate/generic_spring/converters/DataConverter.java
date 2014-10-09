package com.ail.creyate.generic_spring.converters;

import com.ail.creyate.generic_spring.utils.ExcelUtils;



public class DataConverter {

	private static String getMetaHeader(String str) {
		if(str != null) {
			String[] split = str.split("\\"+ExcelUtils.HEADER_SEPARATOR);
			if(split.length == 2) {
				return split[0];
			}
		}
		return null;
	}
	
	private static String getHeader(String str) {
		if(str != null) {
			String[] split = str.split("\\"+ExcelUtils.HEADER_SEPARATOR);
			if(split.length == 2) {
				return split[1];
			}
		}
		return null;
	}
	
	private static String toProperCase(String value,Character delimiter){
		if(value == null){
			return "";
		}
		String temp = "";
		int capFlag=1;
		for(int i=0 ; i<value.length() ; i++){
			if((delimiter != null && value.charAt(i)==delimiter) || (delimiter != null && value.charAt(i)==' ')){
				capFlag=1;
				temp+=value.charAt(i);
			}
			else if(capFlag==1){
				temp+=Character.toUpperCase(value.charAt(i));
				capFlag=0;
			}
			else{
				temp+=Character.toLowerCase(value.charAt(i));
			}
		}
		return temp;
	}
	

	
	/*public static List<FabricsBean> getFabricsBeanList(String garmentType, String filePathToxls)throws JSONException{
		List<FabricsBean> result = new ArrayList<FabricsBean>();
		FabricsDAO fabricsDAO = new FabricsDAO();
		TrimsDAO trimsDAO = new TrimsDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);
		//if(garmentType.equalsIgnoreCase("Mens-SHIRT")){
			for(int i = 0; i < listOfMaps.size(); i++) {
				FabricsBean fabricsBean = new FabricsBean();
				Map<String, String> map = listOfMaps.get(i);
				boolean nullFlag = false;
				JSONObject priceGroupJson = new JSONObject();
				for(Map.Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey().trim();
					String value = entry.getValue().trim();
					String header = getHeader(key).trim();
					String metaHeader = getMetaHeader(key).trim();
					int otherTrimsNumber = 0;

					if(header != null) {
						if("Fabric_Id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")) {
							if(StringUtils.trimToNull(value) == null) {
								nullFlag = true;
								break;
							}
							Object obj = fabricsDAO.findOne(value, null);
							fabricsBean = (obj != null && obj instanceof FabricsBean)?(FabricsBean)obj:null;
							if(fabricsBean == null) {
								fabricsBean = new FabricsBean();
							}
							fabricsBean.setFabricId(value);
						} else if("Fabric_interlining_shade".equalsIgnoreCase(header)) {
							fabricsBean.setFabricInterliningShade(value);
						} else if("Default_Matching_Pocketing_shade".equalsIgnoreCase(header)) {
							fabricsBean.setDefaultMatchingPocketingShade(value);;
						} else if("Default_Matching_Zipper_Shade".equalsIgnoreCase(header)) {
							fabricsBean.setDefaultMatchingZipperShade(value);
						}  
					}
				}
				
				fabricsBean.setId(getUniqueId(fabricsBean.getFabricId()));//, fabricsBean.getLifestyle(), fabricsBean.getCollection()));
				fabricsBean.setImgURL(fabricsBean.getFabricId()+".png");
				//String tempType = fabricsBean.getGender()+"-"+fabricsBean.getLifestyle()+"-"+fabricsBean.getGarmentType();
				//fabricsBean.setType(tempType);
				
				if(!nullFlag) {
					result.add(fabricsBean);		
				}
		}
		return result;
	
	}
	
	
	public static List<TrimsBean> getTrimsBeanList(String garmentType, String filePathToxls)throws JSONException{
		List<TrimsBean> result = new ArrayList<TrimsBean>();
		TrimsDAO trimsDAO = new TrimsDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);
		//if(garmentType.equalsIgnoreCase("Mens-SHIRT")){
			for(int i = 0; i < listOfMaps.size(); i++) {
				TrimsBean trimsBean = new TrimsBean();
				Map<String, String> map = listOfMaps.get(i);
				boolean nullFlag = false;
				
				for(Map.Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey().trim();
					String value = entry.getValue().trim();
					String header = getHeader(key).trim();

					if(header != null) {
						if("Id".equalsIgnoreCase(header)) {
							if(StringUtils.trimToNull(value) == null) {
								nullFlag = true;
								break;
							}
							Object obj = trimsDAO.findOne(value, null);
							trimsBean = (obj != null && obj instanceof TrimsBean)?(TrimsBean)obj:null;
							if(trimsBean == null) {
								trimsBean = new TrimsBean();
							}
							trimsBean.setId(value);
						} else if("Associated_Group_id".equalsIgnoreCase(header)) {
							trimsBean.setAssociatedGroupId(value);
						} else if("Lifestyle".equalsIgnoreCase(header)) {
							trimsBean.setLifestyle(toProperCase(value,null));
						} else if("Gender".equalsIgnoreCase(header)) {
							trimsBean.setGender(toProperCase(value,'/'));
						} else if("Garment_Type".equalsIgnoreCase(header)) {
							trimsBean.setGarmentType(toProperCase(value,null));
						} else if("Thread".equalsIgnoreCase(header)) {
							trimsBean.setThread(value);
						} else if("Trim_Id".equalsIgnoreCase(header)) {
							trimsBean.setTrimId(value);
						} else if("Category".equalsIgnoreCase(header)) {
							trimsBean.setCategory(toProperCase(value,'-'));
						} else if("Trim_Type".equalsIgnoreCase(header)) {
							trimsBean.setTrimType(toProperCase(value,'_'));
						} else if("Size".equalsIgnoreCase(header)) {
							trimsBean.setSize(value);
						} else if("Color".equalsIgnoreCase(header)) {
							trimsBean.setColor(toProperCase(value,','));
						} else if("Nice_Name".equalsIgnoreCase(header)) {
							value = GenericUtils.processSpecialCharacters(value);
							trimsBean.setNiceName(value);
						} else if("Long_Description".equalsIgnoreCase(header)) {
							value = GenericUtils.processSpecialCharacters(value);
							trimsBean.setLongDescription(value);
						} else if("Price".equalsIgnoreCase(header)) {
							if(value != null) {
								if(value.trim() == "") {
									value = "0.0";
								}
								trimsBean.setPrice(Float.parseFloat(value));
							}
						} else if("ERP_Id".equalsIgnoreCase(header)) {
							trimsBean.setErpId(value);
						} else if("last_Modified".equalsIgnoreCase(header)) {
							trimsBean.setLastModified(value);
						} else if("Available".equalsIgnoreCase(header)) {
							if(value != null) {
								if(value.trim() == "") {
									value = "0";
								}
								trimsBean.setAvailable(Integer.parseInt(value));
							}
						} else if("Rendering_Ready".equalsIgnoreCase(header)) {
							if(value != null) {
								if(value.trim() == "") {
									value = "0";
								}
								trimsBean.setRenderingReady(Integer.parseInt(value));
							}
						} else if("Approved".equalsIgnoreCase(header)) {
							trimsBean.setApproved(value);
						} else if("Stocks".equalsIgnoreCase(header)) {
							if(value != null) {
								if(value.trim() == "") {
									value = "0";
								}
								trimsBean.setStocks(Integer.parseInt(value));
							}
						}
					}
				}
				//Null checks:
				if(trimsBean.getApproved()==null || trimsBean.getApproved().equals("")){
					trimsBean.setApproved(Constants.ENVIRONMENT.DEFAULT.getShortName());
				}
				
				if(!nullFlag) {
					result.add(trimsBean);		
				}
		}
		return result;
	}
	
	
	
	
	public static List<ThreadsBean> getThreadsBeanList(String garmentType, String filePathToxls)throws JSONException{
		List<ThreadsBean> result = new ArrayList<ThreadsBean>();
		ThreadsDAO trimsDAO = new ThreadsDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);
		for(int i = 0; i < listOfMaps.size(); i++) {
			ThreadsBean threadsBean = new ThreadsBean();
			Map<String, String> map = listOfMaps.get(i);
			boolean nullFlag = false;

			for(Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().trim();
				String value = entry.getValue().trim();
				String header = getHeader(key).trim();
				String metaHeader = getMetaHeader(key).trim();
				
				if("type".equalsIgnoreCase(header) && value.contains(",")){
					Map<String, String> secondMap = new LinkedHashMap<String, String>();
					String[] tokens = value.split(",");
					value = tokens[0];
					secondMap.putAll(map);
					secondMap.put(key, tokens[1]);
					listOfMaps.add(secondMap);
					}

				if(header != null && metaHeader != null && !metaHeader.equals("")) {
					if("Thread_Id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")) {
						if(StringUtils.trimToNull(value) == null) {
							nullFlag = true;
							break;
						}
						Object obj = trimsDAO.findOne(value, null);
						threadsBean = (obj != null && obj instanceof ThreadsBean)?(ThreadsBean)obj:null;
						if(threadsBean == null) {
							threadsBean = new ThreadsBean();
						}
						threadsBean.setThreadId(value);
					} else if("type".equalsIgnoreCase(header)) {
						threadsBean.setType(value);
					} else if("Erp_Id".equalsIgnoreCase(header)) {
						threadsBean.setErpId(value);
					} else if("Lifestyle".equalsIgnoreCase(header)) {
						threadsBean.setLifestyle(value);
					} else if("tkt".equalsIgnoreCase(header)) {
						threadsBean.setTkt(value);
					} 

										
				}
			}

			threadsBean.setId(getUniqueId(threadsBean.getThreadId(), threadsBean.getType()));
			
			
			if(!nullFlag) {
				result.add(threadsBean);		
			}
		}
		return result;
	}
	
	
	public static List<InterliningBean> getInterlingBeanList(String garmentType, String filePathToxls)throws JSONException{
		List<InterliningBean> result = new ArrayList<InterliningBean>();
		InterliningDAO trimsDAO = new InterliningDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);
		for(int i = 0; i < listOfMaps.size(); i++) {
			InterliningBean interliningBean = new InterliningBean();
			Map<String, String> map = listOfMaps.get(i);
			boolean nullFlag = false;

			for(Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().trim();
				String value = entry.getValue().trim();
				String header = getHeader(key).trim();
				String metaHeader = getMetaHeader(key).trim();


				if(header != null && metaHeader != null&& !metaHeader.equals("")) {
					if("component_id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")) {
						if(StringUtils.trimToNull(value) == null) {
							nullFlag = true;
							break;
						}
						Object obj = trimsDAO.findOne(value, null);
						interliningBean = (obj != null && obj instanceof InterliningBean)?(InterliningBean)obj:null;
						if(interliningBean == null) {
							interliningBean = new InterliningBean();
						}
						interliningBean.setSubComponentName(value);
					} else if("fabric_shade".equalsIgnoreCase(header)) {
						interliningBean.setFabricShade(value);
					} else if("Erp_Id".equalsIgnoreCase(header)) {
						interliningBean.setErpId(value);
					} else if("Lifestyle".equalsIgnoreCase(header)) {
						interliningBean.setLifestyle(value);
					} else if("Construction_Part".equalsIgnoreCase(header)) {
						interliningBean.setConstructionPart(value);
					} else if("Interlining_Type".equalsIgnoreCase(header)) {
						interliningBean.setInterliningType(value);
					} 

					
					
				}
			}
			
			interliningBean.setId(getUniqueId(interliningBean.getSubComponentName(),interliningBean.getLifestyle(), interliningBean.getFabricShade(), interliningBean.getInterliningType(), interliningBean.getConstructionPart()));
			
			if(!nullFlag) {
				result.add(interliningBean);	
			}
		}
		return result;
	}
	
	
	
	public static List<SubComponentsBean> getSubComponentsBeanList(String garmentType, String filePathToxls)throws JSONException{
		List<SubComponentsBean> result = new ArrayList<SubComponentsBean>();
		SubComponentsDAO subComponentsDAO = new SubComponentsDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);
		//if(garmentType.equalsIgnoreCase("Mens-SHIRT")){
			for(int i = 0; i < listOfMaps.size(); i++) {
				SubComponentsBean subComponentsBean = new SubComponentsBean();
				Map<String, String> map = listOfMaps.get(i);
				boolean nullFlag = false;
				JSONObject thread_type_consumption_qty_json = new JSONObject();
				JSONObject button_size_consumption_qty_json = new JSONObject();
				
				for(Map.Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey().trim();
					String value = entry.getValue().trim();
					String header = getHeader(key).trim();
					String metaHeader = getMetaHeader(key).trim();

					if(header != null) {
						if("Component_Id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")) {
							if(StringUtils.trimToNull(value) == null) {
								nullFlag = true;
								break;
							}
							Object obj = subComponentsDAO.findOne(value, null);
							subComponentsBean = (obj != null && obj instanceof SubComponentsBean)?(SubComponentsBean)obj:null;
							if(subComponentsBean == null) {
								subComponentsBean = new SubComponentsBean();
							}
							subComponentsBean.setComponentId(value);
						} else if("sub_component_name".equalsIgnoreCase(header)) {
							subComponentsBean.setSubComponentName(value);
						} else if("button_size".equalsIgnoreCase(header)) {
							
							if(value != null) {
								if(value.trim() == "") {
									value = "0";
								}
								subComponentsBean.setButtonSize(value);
							}
							
						} else if("thread_type".equalsIgnoreCase(header)) {
							subComponentsBean.setThreadType(toProperCase(value,null));
						} else if("rule".equalsIgnoreCase(header)) {
							subComponentsBean.setRules(value);
						} else if("fabric_consumption_unit".equalsIgnoreCase(header)) {
							subComponentsBean.setFabricConsumptionUnit(value);
						} else if("fabric_consumption_qty".equalsIgnoreCase(header)) {
							subComponentsBean.setFabricConsumptionQty(value);
						} else if("thread_type_consumption_qty".equalsIgnoreCase(metaHeader)) {
							
							if(StringUtils.trimToNull(header) != null) {
								if(StringUtils.trimToNull(value) != null) {
									thread_type_consumption_qty_json.put(header, value);
									
								}
							}
							
						} else if("thread_type_consumption_unit".equalsIgnoreCase(header)) {
							subComponentsBean.setThreadTypeConsumptionUnit(value);
						} else if("button_size_consumption_qty".equalsIgnoreCase(metaHeader)) {
							if(StringUtils.trimToNull(header) != null) {
								if(StringUtils.trimToNull(value) != null) {
									button_size_consumption_qty_json.put(header, value);
									
								}
							}
						
						} else if("button_size_consumption_unit".equalsIgnoreCase(header)) {
							subComponentsBean.setButtonSizeConsumptionUnit(value);
						} else if("interlining_consumption_qty".equalsIgnoreCase(header)) {
							//fabricsBean.setImgURL(value);
							subComponentsBean.setInterliningConsumptionQty(value);
						} else if("interlining_consumption_unit".equalsIgnoreCase(header)) {
							subComponentsBean.setInterliningConsumptionUnit(value);
						} 
					}
				}
				
			
			    //subComponentsBean.setThreadTypeConsumptionQty(thread_type_consumption_qty_json.toString());
			   
			  
			    
				
				//Null checks:
				if(( thread_type_consumption_qty_json == null || thread_type_consumption_qty_json.toString().equals("") || !GenericUtils.isJSONValid(thread_type_consumption_qty_json.toString()))){
					subComponentsBean.setThreadTypeConsumptionQty("{}");
					
				} else{
					 subComponentsBean.setThreadTypeConsumptionQty(thread_type_consumption_qty_json.toString());
				}
				
				
				if((button_size_consumption_qty_json==null || button_size_consumption_qty_json.toString().equals("")|| !GenericUtils.isJSONValid(button_size_consumption_qty_json.toString()))){
					subComponentsBean.setButtonSizeConsumptionQty("{}");
					
				} else{
					 subComponentsBean.setButtonSizeConsumptionQty(button_size_consumption_qty_json.toString());
				}
				
				
				
				
				subComponentsBean.setId(getUniqueId(subComponentsBean.getComponentId(), subComponentsBean.getSubComponentName()));
				
				if(!nullFlag) {
					result.add(subComponentsBean);		
				}
		}
		return result;
	}
	

	
	public static List<AccessoriesBean> getAccessoriesBeanList(String garmentType, String filePathToxls)throws JSONException{
		List<AccessoriesBean> result = new ArrayList<AccessoriesBean>();
		AccessoriesDAO accessoriesDAO = new AccessoriesDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);

		for(int i = 0; i < listOfMaps.size(); i++) {
			AccessoriesBean accessoriesBean = new AccessoriesBean();
			Map<String, String> map = listOfMaps.get(i);
			boolean nullFlag = false;
	
			for(Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().trim();
				String value = entry.getValue().trim();
				String header = getHeader(key).trim();
				String metaHeader = getMetaHeader(key).trim();
				
				if (header != null){
					
					if("Id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")){
//							if(StringUtils.trimToNull(value) == null) {
//								nullFlag = true;
//								break;
//							}
							Object obj = accessoriesDAO.findOne(value,null);
							accessoriesBean = (obj != null && obj instanceof AccessoriesBean)?(AccessoriesBean)obj:null ;
							if (accessoriesBean == null)
							{
								accessoriesBean = new AccessoriesBean();
							}
							accessoriesBean.setId(value);
					} else if ("name".equalsIgnoreCase(header)){
							accessoriesBean.setName(value);
					} else if("Garment_Type".equalsIgnoreCase(header)) {
							accessoriesBean.setGarmentType(toProperCase(value, null));
					} else if("Lifestyle".equalsIgnoreCase(header)) {
							accessoriesBean.setLifestyle(toProperCase(value,null));
					} else if("collection".equalsIgnoreCase(header)){
							accessoriesBean.setCollection(value);
					} else if("erp_id".equalsIgnoreCase(header)) {
							accessoriesBean.setErpId(value);
					} else if("consumption_qty".equalsIgnoreCase(header)) {
							accessoriesBean.setConsumptionQty(value);
					} else if("consumption_unit".equalsIgnoreCase(header)) {
							accessoriesBean.setConsumptionUnit(toProperCase(value, null));
					} 
					
					
				}
				
				
			}
			
			accessoriesBean.setId(getUniqueId(accessoriesBean.getGarmentType(), accessoriesBean.getLifestyle() ,accessoriesBean.getCollection(), accessoriesBean.getName()));

			if (!nullFlag){
				result.add(accessoriesBean);
			}
			
		}
		
		
	return result ;
	}
	
	

	public static List<ButtonsBean> getButtonsBeansList(String garmentType, String filePathToxls)throws JSONException{
		List<ButtonsBean> result = new ArrayList<ButtonsBean>();
		ButtonsDAO buttonDAO = new ButtonsDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);
		
		for(int i = 0; i < listOfMaps.size(); i++) {
			ButtonsBean buttonBean = new ButtonsBean();
			Map<String, String> map = listOfMaps.get(i);
			boolean nullFlag = false;
			
			for(Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().trim();
				String value = entry.getValue().trim();
				String header = getHeader(key).trim();
				String metaHeader = getMetaHeader(key).trim();
				
				if(header != null){
					
						if("Id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")){
								Object obj = buttonDAO.findOne(value,null);
								buttonBean  = (obj != null && obj instanceof ButtonsBean)?(ButtonsBean)obj:null;
								if(buttonBean == null){
									buttonBean = new ButtonsBean();
								}
						} else if("btn_id".equalsIgnoreCase(header)) {
								buttonBean.setBtnId(value);
						} else if("size".equalsIgnoreCase(header)) {
								buttonBean.setSize(value);
						} else if("erp_id".equalsIgnoreCase(header)) {
								buttonBean.setErpId(value);
						} else if("matching_rivet_id".equalsIgnoreCase(header)) {
							buttonBean.setMatchingRivetId(value);
						} else if("matching_fly_button_id".equalsIgnoreCase(header)) {
							buttonBean.setMatchingFlyButtonId(value);
						}

				}
			}
			buttonBean.setId(getUniqueId(buttonBean.getBtnId(), buttonBean.getSize()));
			
						
			if (!nullFlag){
				result.add(buttonBean);
			}
			
		}
		
		
		return result;
	}
	
	public static List<PocketingFabricBean> getPocketingFabric(String garmentType, String filePathToxls)throws JSONException{
		List<PocketingFabricBean> result = new ArrayList<PocketingFabricBean>();
		PocketingFabricDAO pocketingFabricDAO = new PocketingFabricDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);
		
		for(int i = 0; i < listOfMaps.size(); i++) {
			PocketingFabricBean pocketingFabricBean = new PocketingFabricBean();
			Map<String, String> map = listOfMaps.get(i);
			boolean nullFlag = false;
			
			for(Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().trim();
				String value = entry.getValue().trim();
				String header = getHeader(key).trim();
				String metaHeader = getMetaHeader(key).trim();
				
				if(header != null){
					
						if("Id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")){
								Object obj = pocketingFabricDAO.findOne(value,null);
								pocketingFabricBean  = (obj != null && obj instanceof PocketingFabricBean)?(PocketingFabricBean)obj:null;
								if(pocketingFabricBean == null){
									pocketingFabricBean = new PocketingFabricBean();
								}
						} else if("component_id".equalsIgnoreCase(header)){
								pocketingFabricBean.setComponentId(value);
						} else if("Garment_Type".equalsIgnoreCase(header)){
								pocketingFabricBean.setGarment_type(value);
						} else if("erp_id".equalsIgnoreCase(header)){
								pocketingFabricBean.setErpId(value);
						} else if ("fabric_shade".equalsIgnoreCase(header)) {
								pocketingFabricBean.setFabricShade(value);
						}

				}
			}
			pocketingFabricBean.setId(getUniqueId(pocketingFabricBean.getGarment_type(), pocketingFabricBean.getFabricShade()));
			
						
			if (!nullFlag){
				result.add(pocketingFabricBean);
			}
			
		}
		
		
		return result;
	}
	
	
	public static List<ZippersBean> getZippersList(String garmentType, String filePathToxls)throws JSONException{
		List<ZippersBean> result = new ArrayList<ZippersBean>();
		ZippersDAO zippersDAO = new ZippersDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);
		
		for(int i = 0; i < listOfMaps.size(); i++) {
			ZippersBean zippersBean = new ZippersBean();
			Map<String, String> map = listOfMaps.get(i);
			boolean nullFlag = false;
			
			for(Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().trim();
				String value = entry.getValue().trim();
				String header = getHeader(key).trim();
				String metaHeader = getMetaHeader(key).trim();
				
				if(header != null){
					
						if("Id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")){
								Object obj = zippersDAO.findOne(value,null);
								zippersBean  = (obj != null && obj instanceof ZippersBean)?(ZippersBean)obj:null;
								if(zippersBean == null){
									zippersBean = new ZippersBean();
								}
						} else if("component_id".equalsIgnoreCase(header)){
								zippersBean.setComponentId(value);
						} else if("Garment_Type".equalsIgnoreCase(header)){
								zippersBean.setGarment_type(value);
						} else if("erp_id".equalsIgnoreCase(header)){
								zippersBean.setErpId(value);
						} else if ("fabric_shade".equalsIgnoreCase(header)) {
								zippersBean.setFabricShade(value);
						} else if ("Zipper_Size".equalsIgnoreCase(header)) {
								zippersBean.setZipperSize(Float.parseFloat(value));
						}

				}
			}
			zippersBean.setId(getUniqueId(zippersBean.getGarment_type(), zippersBean.getFabricShade(), Float.toString(zippersBean.getZipperSize())));
			
						
			if (!nullFlag){
				result.add(zippersBean);
			}
			
		}
		
		
		return result;
	}

	
	
	public static List<HeroProductsBean> getHeroProductsBeanList(String garmentType, String filePathToxls)throws JSONException{
		List<HeroProductsBean> result = new ArrayList<HeroProductsBean>();
		HeroProductsDAO heroProductsDAO = new HeroProductsDAO();
		List<Map<String, String>> listOfMaps = ExcelUtils.convertToListofMapsXSSF(filePathToxls);

		for(int i = 0; i < listOfMaps.size(); i+=2) {
			List<HeroProductsBean> beansList = new ArrayList<HeroProductsBean>();
			Map<String, String> map = listOfMaps.get(i);
			Map<String, String> erpIdsMap = listOfMaps.get(i+1);
			boolean nullFlag = false;
			
			java.util.Iterator<Entry<String, String>> entries = map.entrySet().iterator();
			java.util.Iterator<Entry<String, String>> ERPentries = map.entrySet().iterator();
			Entry<String, String> entry = entries.next();
			Entry<String, String> ERPentry = entries.next();
			String key = entry.getKey().trim();
			String value = entry.getValue().trim();
			String header = getHeader(key).trim();
			String metaHeader = getMetaHeader(key).trim();
			String product_id = null;
			if (header != null){
				if("sku_id".equalsIgnoreCase(header) && metaHeader.equalsIgnoreCase("Meta")){
					product_id = new String(value);
				}
			}
			while(entries.hasNext()) {
				entry = entries.next();
				ERPentry = entries.next();
				key = entry.getKey().trim();
				value = entry.getValue().trim();
				header = getHeader(key).trim();
				metaHeader = getMetaHeader(key).trim();
				String ERPvalue = ERPentry.getValue().trim();
				HeroProductsBean heroProductsBean = new HeroProductsBean();

				if (header != null && (ERPvalue != "" || ERPvalue != null)){
					heroProductsBean.setProductId(product_id);
					heroProductsBean.setPart(header);
					heroProductsBean.setERPId(ERPvalue);
					entry = entries.next();
					ERPentries.next();
					value = entry.getValue().trim();
					heroProductsBean.setConsumption(value);
					beansList.add(heroProductsBean);
				}
			}
			result.addAll(beansList);
			
		}	
	return result ;
	}
	
	public static String getUniqueId(String... columns) {
		if(columns == null) {
			return null;
		}
		if(columns.length == 0) {
			return null;
		}
		if(columns.length == 1) {
			return columns[0];
		}
		StringBuffer uniqueId = new StringBuffer(columns[0]);
		for(int i = 1; i < columns.length; i++) {
			if(columns[i] != "" && StringUtils.trimToNull(columns[i]) != null){
				uniqueId.append("-").append(columns[i]);
				}
			}
		return uniqueId.toString();
	}
	
	public static void main(String[] args) {
		try {
			
//List<InterliningBean> InterliningBean = getInterlingBeanList("Mens-FORMAL-SHIRT","/home/arvind/Desktop/BOM_EXCEL/bomexcels/interlining_bom.xlsx");
			List<AccessoriesBean> accessoriesBeans = getAccessoriesBeanList("Mens-FORMAL-SHIRT","/home/adi/acc.xlsx");
			for(int i = 0; i < accessoriesBeans.size(); i++) {
				AccessoriesDAO t = new AccessoriesDAO();
				t.saveOrUpdate(accessoriesBeans.get(i), null);
			}  
			List<ButtonsBean> buttonsBeans = getButtonsBeansList("Mens-FORMAL-SHIRT","/home/adi/Documents/BOM_UTILS/BOM_EXCELS/fwdbomfinalexcels/buttons_bom.xlsx");
			for(int j = 0; j < buttonsBeans.size(); j++) {
				ButtonsDAO u = new ButtonsDAO();
				u.saveOrUpdate(buttonsBeans.get(j), null);
			
			}
			
			List<SubComponentsBean> subcomponentBean = getSubComponentsBeanList("Mens-FORMAL-SHIRT","/home/adi/sub_com.xlsx");
			for(int j = 0; j < subcomponentBean.size(); j++) {
				SubComponentsDAO u = new SubComponentsDAO();
				u.saveOrUpdate(subcomponentBean.get(j), null);
			}
			
			List<InterliningBean> InterliningBean = getInterlingBeanList("Mens-Chinos","/home/adi/Documents/BOM_UTILS/Garment_Type_Excels/chinos_excels/interlining_bom_chinos.xlsx");
			for(int j = 0; j < InterliningBean.size(); j++) {
				InterliningDAO u = new InterliningDAO();
				u.saveOrUpdate(InterliningBean.get(j), null);
			
			}
			
			List<ThreadsBean> threadsBeans = getThreadsBeanList("Mens-Chinos","/home/adi/Documents/BOM_UTILS/Garment_Type_Excels/chinos_excels/threads_bom_chinos.xlsx");
			for(int j = 0; j < threadsBeans.size(); j++) {
				ThreadsDAO u = new ThreadsDAO();
				u.saveOrUpdate(threadsBeans.get(j), null);
			}
			
			List<FabricsBean> fabricsBeans = getFabricsBeanList("Mens-Chinos","/home/adi/Documents/BOM_UTILS/Garment_Type_Excels/chinos_excels/fabrics_bom_chinos.xlsx");
			for(int j = 0; j < fabricsBeans.size(); j++) {
				FabricsDAO u = new FabricsDAO();
				u.saveOrUpdate(fabricsBeans.get(j), null);
			
			}
			
			List<PocketingFabricBean> pocketingFabricBeans = getPocketingFabric("Mens-Chinos","/home/adi/Documents/BOM_UTILS/Garment_Type_Excels/chinos_excels/Pocketing_Fabric_chinos.xlsx");
			for(int j = 0; j < pocketingFabricBeans.size(); j++) {
				PocketingFabricDAO u = new PocketingFabricDAO();
				u.saveOrUpdate(pocketingFabricBeans.get(j), null);
			}
			
			List<ZippersBean> zippersBeans = getZippersList("Mens-Chinos","/home/adi/Documents/BOM_UTILS/Garment_Type_Excels/chinos_excels/Zipper_chinos.xlsx");
			for(int j = 0; j < zippersBeans.size(); j++) {
				ZippersDAO u = new ZippersDAO();
				u.saveOrUpdate(zippersBeans.get(j), null);
			}
			
			
		} catch(JSONException e) {
			e.printStackTrace();
			}
		}	
		*/
			
		
			/*List<SubComponentsBean> threadsBeans = getSubComponentsBeanList("Mens-FORMAL-SHIRT","/home/arvind/Desktop/BOM_EXCEL/bomexcels/sub_component_bom.xlsx");
			
			for(int i = 0; i < threadsBeans.size(); i++) {
				ThreadsDAO t = new ThreadsDAO();
				t.saveOrUpdate(threadsBeans.get(i), null);
			   
			}*/
		/*	String s = GenericUtils.setBeantoSerializedString((Serializable) fabricBeans);
			System.out.println(s);
			Object obj = GenericUtils.getBeanfromSerializedString(s);
			System.out.println(obj);
			List<FabricsBean> prBeans = (List<FabricsBean>)obj;*/
			
		/**
		 * The code below is used to convert the customised JSON in a table with line breaks to  without line breaks, it removes line breaks
		 * */
		/*SessionFactory sessionFactory = HibernateUtilities.getCustomDBSessionFactory("local", "website");
		
		ProductsDAO productsDAO = new ProductsDAO();
		productsDAO.setSessionFactory(sessionFactory);
		
		List<Serializable> list = productsDAO.findAll(null);
		for(int i = 0; i < list.size(); i++) {
			ProductsBean productsBean = (ProductsBean)list.get(i);
			//ProductsBean productsBean = (ProductsBean)productsDAO.findOne("MCH540-16", null);
			if(productsBean != null) {
				productsBean.setCustomisedJson(productsBean.getCustomisedJson().replace("\n", "").replace("\r", "").replace(" ", ""));
				productsDAO.update(productsBean, null);
			}
		}*/
		
		/**
		 * The code below is used to convert the Description in a table with Spl chars to regular chars
		 * */
		/*SessionFactory sessionFactory = HibernateUtilities.getCustomDBSessionFactory("local", "website");
		
		ProductsDAO productsDAO = new ProductsDAO();
		productsDAO.setSessionFactory(sessionFactory);
		
		List<Serializable> list = productsDAO.findAll(null);
		for(int i = 0; i < list.size(); i++) {
			ProductsBean productsBean = (ProductsBean)list.get(i);
			//ProductsBean productsBean = (ProductsBean)productsDAO.findOne("MCH540-16", null);
			if(productsBean != null) {
				if(productsBean.getLongDescription() != null) {
					productsBean.setLongDescription(productsBean.getLongDescription().replace("–", "-").replace("’", "'").replace("‘", "'").replace("\n", " ").replace("\r", " ").replace("é", "e").replace("á", "a").replace("í","i").replace("ó","o").replace("ñ", "n").replace("ü","u").replace("ú", "u").replace("à", "a").replace("è", "e").replace("ì","i").replace("Ò","o").replace("Ù", "u").trim());
					productsDAO.update(productsBean, null);
				}
			}
		}*/
		/*FabricsDAO fabricsDAO = new FabricsDAO();
		fabricsDAO.setSessionFactory(sessionFactory);
		
		List<Serializable> list = fabricsDAO.findAll(null);
		for(int i = 0; i < list.size(); i++) {
			FabricsBean fabricsBean = (FabricsBean)list.get(i);
			//ProductsBean productsBean = (ProductsBean)productsDAO.findOne("MCH540-16", null);
			if(fabricsBean != null) {
				if(fabricsBean.getLongDescription() != null) {
					fabricsBean.setLongDescription(fabricsBean.getLongDescription().replace("–", "-").replace("’", "'").replace("‘", "'").replace("\n", " ").replace("\r", " ").replace("é", "e").replace("á", "a").replace("í","i").replace("ó","o").replace("ñ", "n").replace("ü","u").replace("ú", "u").replace("à", "a").replace("è", "e").replace("ì","i").replace("Ò","o").replace("Ù", "u").trim());
					fabricsDAO.update(fabricsBean, null);
				}
			}
		}*/
		/*SessionFactory sessionFactory = HibernateUtilities.getCustomDBSessionFactory("local", "website");
		
		TrimsDAO trimsDAO = new TrimsDAO();
		trimsDAO.setSessionFactory(sessionFactory);
		
		List<Serializable> list = trimsDAO.findAll(null);
		for(int i = 0; i < list.size(); i++) {
			TrimsBean trimsBean = (TrimsBean)list.get(i);
			//ProductsBean productsBean = (ProductsBean)productsDAO.findOne("MCH540-16", null);
			if(trimsBean != null) {
				if(trimsBean.getLongDescription() != null) {
					trimsBean.setLongDescription(trimsBean.getLongDescription().replace("–", "-").replace("’", "'").replace("‘", "'"));
				}
				if(trimsBean.getNiceName() != null) {
					trimsBean.setNiceName(trimsBean.getNiceName().replace("–", "-").replace("’", "'").replace("‘", "'").replace(" With ", " with "));
					
				}
				trimsDAO.update(trimsBean, null);
			}
		}
		
	}*/
}
