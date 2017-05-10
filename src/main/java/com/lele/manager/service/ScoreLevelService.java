package com.lele.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lele.manager.dao.ScoreLevelDAO;
import com.lele.manager.entity.ScoreLevel;

@Service("scoreLevelService")
public class ScoreLevelService {

	@Autowired
	ScoreLevelDAO scoreLevelDao;
	
	public List<ScoreLevel> getAllScoreLevel() {
		return scoreLevelDao.getAllScoreLevel();
	}
	
	public ScoreLevel getScoreLevel(int scoreIndex) {
		return scoreLevelDao.getScoreLevel(scoreIndex);
	}
}
