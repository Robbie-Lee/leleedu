package com.lele.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.ScoreLevel;
import com.lele.manager.sys.dao.MysqlBaseDAO;

@Repository("scoreLevelDAO")
public class ScoreLevelDAO extends MysqlBaseDAO<ScoreLevel> {

	private final String HQL_ENTITY = "ScoreLevel";
	
	public List<ScoreLevel> getAllScoreLevel() {
		final String hql = "from " + HQL_ENTITY;
		return this.doQueryList(hql);
	}
	
}
