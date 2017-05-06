package com.lele.manager.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.entity.Resource;

@Repository("resourceDAO")
public class ResourceDAO extends MysqlBaseDAO<Resource> {

}
