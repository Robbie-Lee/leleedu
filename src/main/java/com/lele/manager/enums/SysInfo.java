package com.lele.manager.enums;

public enum SysInfo {
	
	// Success
	SUCCESS("success"),
	
	PERMISSION_DENIED("permission denied"),
	
	// Job invoker error
	PARAM_ERROR("parameter error"),
	
	TASK_GRAPH_ERROR("task graph error"),
	
	NOT_LOGININ_USER("not login user"),
	
	TASK_PARSE_ERROR("task info parse error"), LOAD_TASK_TODB_ERROR("load task to db error"),
	
	SAME_TASK_EXIST("same task is exist"),
	
	TASK_IS_DEPRECATED("task is deprecated"),
	
	NOT_ALLOW_MODIFY("can't modify running task"),
	
	HAS_RUNNING_TASK("has running task"),
	
	ALREADY_STARTUP("task is already startup"),
	
	ALREADY_STOP("task is already canceled or complete"),
	
	NOT_EXECUTABLE_MODEL("it's not executable model"), 
	DEPENDANCY_MODEL_NOT_COMPLETE("dependancy model is not complete"), 
	COMPLETED_JOB_NOT_ALLOW_RESTART("completed job can't be retart"), 
	RUNNING_JOB_NOT_ALLOW_RESTART("running job can't be retart"), 
	JOB_ALREADY_STOP("not running job can't be cancel"),
	
	DEPENDANCY_MODEL_NOT_EXIST("dependancy model is not exist"),
	
	INNER_ERROR("inner error"),
	
	STOPPING_JOB("stopping job"),
	STARTING_JOB("starting job"),
	
	//	plugin
	
	INIT_PLUGIN_ERROR("init plugin error"),
	CONFIG_PLUGIN_ERROR("config plugin error"),
	INVOKE_PLUGIN_ERROR("invoke plugin error"),
	
	ANALYSIS_PLUGIN_ERROR("analysis plugin error");
	
	private String sysInfo;
	
	SysInfo(String sysInfo) {
		this.sysInfo = sysInfo;
	}
	
	public String info() {
		return this.sysInfo;
	}
}
