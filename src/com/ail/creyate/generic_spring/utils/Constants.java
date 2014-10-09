package com.ail.creyate.generic_spring.utils;



public class Constants {
	
	public static enum ORDER_ERP_STATUS {
		NEW(0), FETCHED_ORDER_DETAILS(1), FETCHED_ERP_DETAILS(2), DONE(3), UNKNOWN(4);
		
		private Integer orderStatusNumber;
		
		ORDER_ERP_STATUS(Integer orderStatusNumber) {
			this.orderStatusNumber = orderStatusNumber;
		}
		
		public Integer getOrderERPStatusNumber() {
			return this.orderStatusNumber;
		}
		
		public static ORDER_ERP_STATUS getOrderERPStatusByNumber(Integer number) {
			switch(number) {
			case 0:
				return ORDER_ERP_STATUS.NEW;
			case 1:
				return ORDER_ERP_STATUS.FETCHED_ORDER_DETAILS;
			case 2:
				return ORDER_ERP_STATUS.FETCHED_ERP_DETAILS;
			case 3:
				return ORDER_ERP_STATUS.DONE;
			default:
				return ORDER_ERP_STATUS.UNKNOWN;
			}
		}
		
	}
	
	public static enum CATEGORY {

		MENS_FORMAL_SHIRTS("mens-formal-shirt","MSG"), MENS_SHIRTS("mens-shirt","MSG"), MENS_CHINOS("mens-chinos","MTG"), MENS_JEANS("mens-jeans","MJG"), MENS_SUITS("mens-suits","MSU"), MENS_BLAZER("mens-blazer","MSJ"), MENS_TROUSER("mens-trousers","MST"), WOMENS_FORMAL_SHIRT("womens-formal-shirt","MSG"), WOMENS_CASUAL_SHIRT("womens-casual-shirt","MSG"),  WOMENS_SHIRT("womens-shirt","MSG"), WOMENS_CHINOS("womens-chinos","MTG"), WOMENS_JEANS("womens-jeans","MJG"), DEFAULT("shirts","MSG");
		private String categoryName;
		private String categoryCode;
		CATEGORY(String categoryName, String categoryCode) {
			this.categoryName = categoryName;
			this.categoryCode = categoryCode;
		}
		
		public String getCategoryName() {
			return this.categoryName;
		}
		public String getCategoryCode() {
			return this.categoryCode;
		}
		public String getReservationWareHouseCode(String wareType) {
			if("F".equalsIgnoreCase(wareType) && this.categoryCode.equalsIgnoreCase("MSU"))
				return "SSFABAGL";
			if("O".equalsIgnoreCase(wareType) && this.categoryCode.equalsIgnoreCase("MSU"))
				return "SMGTAGL";
			return ("F".equalsIgnoreCase(wareType))?"SSFABOT":"SMGTAFL1";
		}
		
		public static CATEGORY getCategoryByCategoryName(String categoryName) {
			if("mens-formal-shirt".equalsIgnoreCase(categoryName))
				return MENS_FORMAL_SHIRTS;
			if("mens-shirt".equalsIgnoreCase(categoryName))
				return MENS_SHIRTS;
			if("mens-chinos".equalsIgnoreCase(categoryName))
				return MENS_CHINOS;
			if("mens-jeans".equalsIgnoreCase(categoryName))
				return MENS_JEANS;
			if ("mens-suits".equalsIgnoreCase(categoryName))
				return MENS_SUITS;
			if ("mens-blazer".equalsIgnoreCase(categoryName))
				return MENS_BLAZER;
			if ("mens-trousers".equalsIgnoreCase(categoryName))
				return MENS_TROUSER;
			if ("womens-formal-shirt".equalsIgnoreCase(categoryName))
				return WOMENS_FORMAL_SHIRT;
			if ("womens-casual-shirt".equalsIgnoreCase(categoryName))
				return WOMENS_CASUAL_SHIRT;
			if ("womens-shirt".equalsIgnoreCase(categoryName))
				return WOMENS_SHIRT;
			if ("womens-chinos".equalsIgnoreCase(categoryName))
				return WOMENS_CHINOS;
			if ("womens-jeans".equalsIgnoreCase(categoryName))
				return WOMENS_JEANS;
			return DEFAULT;
		}
		
	}
	
	
	
	public static enum TRIMS {
		BUTTON("button"), PIPING("piping"), TAPES("tapes"), DEFAULT("default");
		private String name;
		TRIMS(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
		public static TRIMS getTrimByName(String name) {
			if("button".equalsIgnoreCase(name))
				return BUTTON;
			if("piping".equalsIgnoreCase(name))
				return PIPING;
			if("tapes".equalsIgnoreCase(name))
				return TAPES;
		return DEFAULT;
		}
	}
	
	public static enum ENVIRONMENT {
		PROD("production","PROD"), LOCAL("local","local"),QA("qa","qa"), DEV("development","development"),DEFAULT(ApplicationProperties.getProperty("environment"),ApplicationProperties.getProperty("environment")),DISABLED("disabled","disabled");
		private String name;
		private String shortName;
		
		ENVIRONMENT(String name, String shortName) {
			this.name = name;
			this.shortName = shortName;
		}
		public String getName() {
			return this.name;
		}
		public String getShortName() {
			return this.shortName;
		}
		public static ENVIRONMENT getEnvByName(String name) {
			if("production".equalsIgnoreCase(name))
				return PROD;
			if("development".equalsIgnoreCase(name))
				return DEV;
			if("local".equalsIgnoreCase(name))
				return LOCAL;
			if("disabled".equalsIgnoreCase(name))
				return DISABLED;
			return DEFAULT;
		}
		public static ENVIRONMENT getEnvByShortName(String shortName) {
			if("PROD".equalsIgnoreCase(shortName))
				return PROD;
			if("development".equalsIgnoreCase(shortName))
				return DEV;
			if("local".equalsIgnoreCase(shortName))
				return LOCAL;
			if("disabled".equalsIgnoreCase(shortName))
				return DISABLED;
			return DEFAULT;
		}
	}
	
	public static enum AVAILABILITY {
		NOT_AVAILABLE("not available", 0), AVAILABLE("available", 1), FAST_FILLING("fast filling", 2);
		
		private String status;
		private Integer statusNumber;
		
		AVAILABILITY(String status, int statusNumber) {
			this.status = status;
			this.statusNumber = statusNumber;
		}
		
		public String getStatus() {
			return this.status;
		}
		
		public Integer getStatusNumber() {
			return this.statusNumber;
		}
		
		public static AVAILABILITY getAvailabilityByName(String status) {
			if("not available".equalsIgnoreCase(status))
				return NOT_AVAILABLE;
			if("available".equalsIgnoreCase(status))
				return AVAILABLE;
			if("fast filling".equalsIgnoreCase(status))
				return FAST_FILLING;
			return NOT_AVAILABLE;
		}
		
		public static AVAILABILITY getAvailabilityByNumber(Integer statusNumber) {
			if(statusNumber == 0)
				return NOT_AVAILABLE;
			if(statusNumber == 1)
				return AVAILABLE;
			if(statusNumber == 2)
				return FAST_FILLING;
			return NOT_AVAILABLE;
		}
	}
	
	public static enum PRODUCTION_READY_STATUS {
		PENDING_BASE_INFO("pending_base_info"), PENDING_CONTENT("pending_content"), PENDING_IMAGES("pending_images"), PENDING_CUSTOMISATION("pending_customisation"), PRODUCTION_READY("production_ready"), PENDING_CONTRAST_FABRIC_DATA("pending_contrast_fabric_data"), PENDING_TRIMS_DATA("pending_trims_Data");
		
		private String name;
		
		PRODUCTION_READY_STATUS(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}
	
	
	public static enum SWATCH_TYPES{
		RENDERABLE("repeatable_swatch",true), CHOICE("choice_swatch",false),DEFAULT("repeatable_swatch",false),ROMATIC_SHOT("romanticshot",false);
		private String name;
		private boolean isRenderable;
		
		SWATCH_TYPES(String name,boolean val) {
			// TODO Auto-generated constructor stub
			this.name = name;
			this.isRenderable = val;
		}
		public String getName() {
			return this.name;
		}
		public boolean isRenderable(){
			return this.isRenderable;
		}
		public static SWATCH_TYPES getSwatchByName(String name) {
			if("repeatable_swatch".equalsIgnoreCase(name))
				return RENDERABLE;
			if("choice_swatch".equalsIgnoreCase(name))
				return CHOICE;
			return DEFAULT;
		}
	}
	
	public static enum PRODUCT_VIEWS{
		FRONT("front"), SIDE("side"),BACK("back"),DEFAULT("front");
		private String name;
		
		private PRODUCT_VIEWS(String name) {
			// TODO Auto-generated constructor stub
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
		public static PRODUCT_VIEWS getViewByName(String name) {
			if("front".equalsIgnoreCase(name))
				return FRONT;
			if("back".equalsIgnoreCase(name))
				return BACK;
			if("side".equalsIgnoreCase(name))
				return SIDE;
			return DEFAULT;
		}
	}
	
	public static enum RESOLUTION{
		ZOOM_RESOLUTION(1500,1500,"Zoom"),HIGH_DESKTOP_RESOLUTION(1000,1000,"Large_desktop"),HIGH_IPAD_RESOLUTION(632,632,"Large_ipad"), MEDIUM_RESOLUTION(250,250,"Medium"),THUMBNAIL_RESOLUTION(80,80,"Thumbnail"),DEFAULT(100,100,"default");
		private int width;
		private int height;
		private String name;
		private RESOLUTION(int width,int height,String name) {
			this.width = width;
			this.height = height;
			this.name = name;
		}
		public int getWidth() {
			return this.width;
		}
		public int getHeight(){
			return this.height;
		}
		public String getName(){
			return this.name;
		}
	}
	
	public static final String IMAGE_RENDER_DB = "ImageRendering";
	public static final String WEBSITE_DB = "website";

	public static final String HOME_PAGE = "/fabrics/viewMain";
	public static final String LOGIN_ERROR = "/login/error";
	public static final String STUDENT_VIEW = "/student/addConfirm";
	public static final String FABRICS_VIEW = "/fabrics/viewFabric";
	public static final String ADD_FABRICS_VIEW = "/fabrics/viewAddFabric";
	public static final String CONTRAST_FABRICS_VIEW = "/fabrics/viewContrastFabric";
	public static final String IMAGES_VIEW = "/fabrics/viewImages";
	public static final String ERROR_VIEW = "/error/viewErrorPage";
	public static final String TRIMS_VIEW = "/trims/viewFabricsTrims";
	public static final String TRIMS_ADD_NEW = "/trims/addNew";
	public static final String TRIMS_BASE_VIEW = "/trims/viewBaseTrim";
	public static final String TRIMS_PAGE_ID = "trims_base_information";
	public static final String TRIMS_IMAGES_VIEW = "/trims/viewImages";
	public static final String FABRICS_MAIN_VIEW = "/fabrics/viewMain";
	
	public static final String FABRICS_CONTENT_VIEW = "/fabrics/viewFabricContent";
	public static final String FABRICS_WEBSITE_VIEW = "/fabrics/viewFabricWebsiteContent";
	public static final String FABRICS_BACKEND_VIEW = "/fabrics/viewFabricBackendContent";
	
	public static final String MENUS_ADD_PAGE = "/resources/menus/addMenuItem";
	public static final String SYSTEMVARS_ADD_PAGE = "/resources/systemvars/addVarItem";
	
	public static final String MAIN_PAGE = "/homePage";
	
	public static final String PRODUCTS_BASE_VIEW = "/products/viewBaseProduct";
	public static final String PRODUCTS_CONTENT_VIEW = "/products/viewProductContent";
	public static final String PRODUCTS_IMAGES_VIEW = "/products/viewImages";
	public static final String PRODUCTS_CUSTOMISE_VIEW = "/products/viewCustomisedLinkage";
	public static final String PRODUCTS_MAIN_VIEW = "/products/viewMain";
	public static final String PRODUCTS_UPLOAD_EXCEL = "/products/uploadExcel";
	public static final String FABRICS_UPLOAD_EXCEL = "/fabrics/uploadExcel";
	public static final String TRIMS_UPLOAD_EXCEL = "/trims/uploadExcel";
	public static final String PRODUCTS_ADD_NEW = "/products/addNew";
	public static final String TRIMS_MAIN_VIEW = "/trims/viewMain";
	
	public static final String HEALTH_PAGE = "/health";
	public static final String SHOW_ALL_PRODUCTS_VIEW = "/products/viewAllProducts";
	public static final String SHOW_ALL_FABRICS_VIEW = "/fabrics/viewAllFabrics";
	public static final String SHOW_ALL_TRIMS_VIEW = "/trims/viewAllTrims";
	public static final String SHOW_ALL_EMBROIDERY_VIEW = "/embroidery/viewAllEmbroidery";
	public static final String TRIMS_CONTENT_VIEW = "/trims/viewTrimsContent";
	public static final String TRIMS_BACKEND_VIEW = "/trims/viewTrimsBackend";
	public static final String PROCESS_CF_VIEW = "/fabrics/processCFView";
	public static final String MANUAL_PRODUCTS_IMAGES_UPLOAD_VIEW = "/products/manualUploadImagesToS3";
	public static final String PROCESS_FABRIC_VERSION_VIEW = "/fabrics/processFabricVersionView";
	
	public static final String PRODUCTS_IMAGES_PROCESS_STATUS_VIEW = "productImagesProcessingStatus";

	public static final String FABRICS_PAGE_ID = "fabrics_base_information";
	public static final String FABRICS_CONTENT_PAGE_ID = "fabrics_content_information";
	public static final String FABRICS_WEBSITE_PAGE_ID = "fabrics_website_information";
	public static final String FABRICS_BACKEND_PAGE_ID = "fabrics_backend_information";
	
	public static final String TRIMS_BACKEND_PAGE_ID = "trims_backend_information";
	public static final String TRIMS_CONTENT_PAGE_ID = "trims_content_information";
	
	public static final String CONTRAST_FABRICS_PAGE_ID = "contrast_fabrics";
	public static final String FABRICS_IMAGES_PAGE_ID = "fabrics_images";
	public static final String FABRICS_TRIMS_PAGE_ID = "trims";
	public static final String FABRICS_UPLOAD_IMAGES_PAGE_ID = "fabrics_upload_page";
	public static final String CHECK_FOR_MISSING_IMAGES_VIEW = "/checkForMissingImages";
	
	public static final String PRODUCTS_PAGE_ID = "products_base_information";
	public static final String PRODUCTS_CONTENT_PAGE_ID = "products_contents";
	public static final String PRODUCTS_IMAGES_PAGE_ID = "products_images";
	public static final String TRIMS_IMAGES_PAGE_ID = "trims_images";
	public static final String PRODCUTS_CUSTOMIZATION_LINKAGE_PAGE_ID = "products_customization_linkage";
	public static final String PRODUCTS_UPLOAD_IMAGES_PAGE_ID = "products_upload_page";
	public static final String PRODUCTS_PHOTOSHOOT_UPLOAD_IMAGES = "products_photoshoot_upload_page";
	public static final String TRIMS_UPLOAD_IMAGES_PAGE_ID = "trims_upload_page";
	public static final String PHOTOSHOOT_IMAGES_PAGE_ID = "photoshoot_images_page";
	
	public static final String COPY_PAGE_ID = "copy_page_id";
	public static final String COPY_TO_PROD_PAGE_ID = "copy_to_producttion";
	
	public static final String LOGS_VIEW = "/viewLogs";
	
	public static final String UPLOAD_MULTIPLE_PRODUCTS_IMAGES = "uploadMultipleImages";
	public static final String PHOTOSHOOT_IMAGES_VIEW = "/products/viewPhotoshootImages";
	public static final String PHOOTSHOOT_DATA_MAIN_VIEW = "/products/viewPhotoshootDataHome";
	public static final String VIEW_FABRIC_ASSOCIATED_GROUP_PAGE = "/fabrics/viewFabricAssociatedGroup";
	
	public static final String COPY_VIEW = "/viewCopyPage";
	public static final String COMMENTS_VIEW = "/user_comments";
	public static final String DEFAULT_IMAGE_FORMAT = "png";

	public static final String COMPONENTS_ADD_NEW = "/components/addNew";
	public static final String COMPONENTS_BASE_VIEW="/components/metaInfo";
	public static final String COMPONENTS_MAIN_VIEW = "/components/viewMain";
	
	public static final String FABRIC_CONSUMPTION_UNIT = "MT";
	public static final String BUTTON_CONSUMPTION_UNIT = "NO";
	public static final String THREAD_CONSUMPTION_UNIT = "MT";
	public static final String INTERLINING_CONSUMPTION_UNIT = "MT";
	public static final String TAPE_CONSUMPTION_UNIT = "MT";
	public static final String LEATHER_PATCH_CONSUMPTION_UNIT = "NO";
	public static final String WASH_CARE_LABEL_CONSUMPTION_UNIT = "NO";
	public static final String ZIPPER_FLY_CONSUMPTION_UNIT = "NO";
	public static final String RIVET_CONSUMPTION_UNIT = "NO";
	public static final String GENERIC_RESPONSE = "/response";

}
