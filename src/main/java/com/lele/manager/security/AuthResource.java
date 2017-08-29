package com.lele.manager.security;

public class AuthResource {
	private String menuUrl;
	private String ctrlId;
	
	public AuthResource(String menuUrl, String ctrlId) {
		this.menuUrl = menuUrl;
		this.ctrlId = ctrlId;
	}
	
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getCtrlId() {
		return ctrlId;
	}
	public void setCtrlId(String ctrlId) {
		this.ctrlId = ctrlId;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof AuthResource)) {
			return false;
		}
		
		AuthResource that = (AuthResource)obj;
		
		if (that.ctrlId.equals(this.ctrlId) && that.menuUrl.equals(this.menuUrl)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return (this.ctrlId + this.menuUrl).hashCode(); 
	}
}
