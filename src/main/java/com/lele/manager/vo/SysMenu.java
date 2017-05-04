package com.lele.manager.vo;

import java.io.Serializable;

public class SysMenu implements Serializable {

	private static final long serialVersionUID = -5325817342568344047L;

	public static final int MENU_LEVEL_NUM = 2;
	
	private String level1;
	
	private String level2;

	public SysMenu() {
	}

	public SysMenu(String level1, String level2) {
		this.level1 = level1;
		this.level2 = level2;
	}

	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	@Override
	public String toString() {
		return "SysMenu [level1=" + level1 + ", level2=" + level2 + "]";
	}
}
