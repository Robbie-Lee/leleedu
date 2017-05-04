package com.lele.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.Resource;
import com.lele.manager.dao.MysqlBaseDAO;

@Repository("resourceDAO")
public class ResourceDAO extends MysqlBaseDAO<Resource> {

}
