package com.lele.manager.utils;

public class Constants {

    public final static int DEFAULT_COOKIE_MAXAGE = 3600 * 24 * 7;
    public final static String DEFAULT_COOKIE_DOMAIN = "";
	public final static String DEFAULT_COOKIE_PATH = "/";

	public final static String DEFAULT_COOKIE_TOKEN = "c_leap_user_token";
	public final static String DEFAULT_COOKIE_LOGIN_NAME = "c_login_name";

	public final static String DEFAULT_SESSION_ATTRIBUTE_NAME = "s_leap_user_info"; 
	
	//////////////////////////////////////////////////////////////////////////////////
	public final static String MODEL_ATTR_PREFIX = "_";
	
	public final static String CUR_USER = MODEL_ATTR_PREFIX + "cur_user";
	public final static String CUR_MENU = MODEL_ATTR_PREFIX + "cur_menu";
	public final static String PROC_RESULT = MODEL_ATTR_PREFIX + "proc_result";
	
	//////////////////////////////////////////////////////////////////////////////////
	public final static String SUBMIT_TASK_INTERFACE = "submit_task_interface";
	public final static String ENABLE_TASK_INTERFACE = "enable_task_interface";

	public final static String STOP_JOB_INTERFACE = "stop_job_interface";
	public final static String START_JOB_INTERFACE = "start_job_interface";
	
	public final static String JOB_STATUS_INTERFACE = "job_status_interface";
	
	public final static String ANALYSIS_RESULT_INTERFACE = "analysis_result_interface";
	public final static String TPCDS_ID_INTERFACE = "tpcds_id_interface";
	public final static String TPCDS_NODEID_INTERFACE = "tpcds_nodeid_interface";
	
	public final static String TASKLIST_INTERFACE = "tasklist_interface";
	public final static String EXECLIST_INTERFACE = "execlist_interface";
	
	public final static String JOBLIST_INTERFACE = "joblist_interface";
	
	public final static String RESULT_FILE_INTERFACE = "result_file_interface";
	
	//////////////////////////////////////////////////////////////////////////////////
	public final static String OVERVIEW_INTERFACE = "overview_interface";
	public final static String OVERVIEW_TASKDETAIL_INTERFACE = "overview_taskdetail_interface";
	public final static String OVERVIEW_JOBDETAIL_INTERFACE = "overview_jobdetail_interface";
}
