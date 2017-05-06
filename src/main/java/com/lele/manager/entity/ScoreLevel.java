package com.lele.manager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scoreLevel")
public class ScoreLevel implements Serializable {

	private static final long serialVersionUID = -2006309400239858983L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private int scoreLevel;
	
	private String scoreDescription;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getScoreLevel() {
		return scoreLevel;
	}

	public void setScoreLevel(int scoreLevel) {
		this.scoreLevel = scoreLevel;
	}

	public String getScoreDescription() {
		return scoreDescription;
	}

	public void setScoreDescription(String scoreDescription) {
		this.scoreDescription = scoreDescription;
	}
}
