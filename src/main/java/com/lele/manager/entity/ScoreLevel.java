package com.lele.manager.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "score_level")
public class ScoreLevel implements Serializable {

	private static final long serialVersionUID = -2006309400239858983L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private int scoreIndex;
	
	private String scoreDescription;
	
	@OneToMany(mappedBy = "scoreLevel", cascade = { CascadeType.ALL }, 
			fetch = FetchType.LAZY, orphanRemoval = true)
	private transient Set<ClassInfo> classInfoSet = new HashSet<ClassInfo>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getScoreIndex() {
		return scoreIndex;
	}

	public void setScoreIndex(int scoreIndex) {
		this.scoreIndex = scoreIndex;
	}

	public String getScoreDescription() {
		return scoreDescription;
	}

	public void setScoreDescription(String scoreDescription) {
		this.scoreDescription = scoreDescription;
	}

	public Set<ClassInfo> getClassInfoSet() {
		return classInfoSet;
	}

	public void setClassInfoSet(Set<ClassInfo> classInfoSet) {
		this.classInfoSet = classInfoSet;
	}

}
