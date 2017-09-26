package com.lele.manager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lele.manager.entity.RegisterInfo;
import com.lele.manager.sys.dao.MysqlBaseDAO;
import com.lele.manager.sys.dao.Pagination;

@Repository("registerInfoDAO")
public class RegisterInfoDAO extends MysqlBaseDAO<RegisterInfo> {

	private final String HQL_ENTITY = "RegisterInfo";
	
	public List<RegisterInfo> getRegisterInfo(Long classKeyId) {
		final String hql = "from " + HQL_ENTITY + " as r left join r.classInfos as cis where cis.id = ?0 and r.registerMode = ?1";
		return this.doQueryList(hql, classKeyId, 0);
	}
	
	public RegisterInfo getRegisterInfo(Long classKeyId, Long studentKeyId) {
		final String hql = "from " + HQL_ENTITY + " as r left join r.classInfos as cis left join r.studentInfos as sis "
				+ "where cis.id = ?0 and sis.id = ?1";
		
		Object obj = this.doQueryUnique(hql, classKeyId, studentKeyId);
		
		if (obj != null)
		{
			RegisterInfo ri = (RegisterInfo) ((Object[])obj)[0];
			return ri;
		}
		
		return null;
	}
	
/*	public long getRegisterCount(String classId) {
		String hql = "select count(*) from " + HQL_ENTITY + " as r left join r.classInfos as cis where cis = ?0";
		return this.doQueryCount(hql, classId);
	}
*/	
/*	public Pagination<RegisterInfo> getStudentRegisterInfo(int curPage, int pageSize, String studentId) {
		final String hql = "from " + HQL_ENTITY + " where studentId = ?0";
		return this.doQuery(hql, curPage, pageSize, studentId);
	}*/
	
	
	public void updateScore(long studentKeyId, long classKeyId, int score) {
		
		String hql = "from " + HQL_ENTITY + " as r left join r.classInfos as cis "
				+ " left join r.studentInfos as sis where sis.id = ?0 and cis.id = ?1";
		
		Object obj = this.doQueryUnique(hql, studentKeyId, classKeyId);
		
		RegisterInfo ri = (RegisterInfo) ((Object[])obj)[0];

		hql = "update " + HQL_ENTITY + " set classScore = ?0 where id = ?1";
//		"update " + HQL_ENTITY + " as r set r.classScore = ?0 left join r.classInfos as cis left join r.studentInfos as sis"
//				+ " where sid.id = ?1 and cid.id = ?2";
//		final String hql = "update " + HQL_ENTITY + " as r set r.classScore = ?0 "
//				+ " where r.classInfos.id = ?1 and r.studentInfos.id = ?2";
		this.executeHsqlWithoutEvict(hql, score, ri.getId());
	}
	
	public Pagination<Map> getRegisterInfoById(int curPage, int pageSize, long studentKeyId) {
		
		final String hql = "select new map (r.registerDate as registerDate, r.registerMode as registerMode, r.payFee as payFee, "
				+ "r.classScore as classScore, r.registerChannel as registerChannel, cis.classId as classId, cis.className as className, "
				+ "cis.teacherName as teacherName, cis.classCount as classCount, cis.registerCount as registerCount, "
				+ "cis.registerLimit as registerLimit, cis.startDate as startDate, cis.endDate as endDate, sis.scoreLevel as scoreLevel) from " 
				+ HQL_ENTITY + " as r left join r.studentInfos as sis left join r.classInfos cis where sis.id = ?0";
/*		
		final String hql = "from " + HQL_ENTITY + " as r left join r.studentInfos as sis "
				+ "where sis.id = ?0";
*/		
		Pagination<Map> plmap = this.doQueryListMap(hql, curPage, pageSize, studentKeyId);
		return plmap;
	}
	
	public Pagination<RegisterInfo> getRegisterInfoByPage(int curPage, int pageSize, 
			List<Long> classKeyIds, List<Long> studentKeyIds, Date startDate, Date endDate) {

		StringBuilder hql = new StringBuilder();
		hql.append("from " + HQL_ENTITY + " as r ");
		
		List<Object> values = new ArrayList<Object>();
		
		boolean classIsEmpty = (classKeyIds == null || classKeyIds.size() == 0);
		boolean studentIsEmpty = (studentKeyIds == null || studentKeyIds.size() == 0);
		
		if (classIsEmpty && studentIsEmpty) {
			hql.append(" where 1=1 ");
		}
		else if (!classIsEmpty && studentIsEmpty) {
			hql.append(" left join r.classInfos as cis where cis.id in (");
			
			for (int i = 0;i < classKeyIds.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < classKeyIds.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(classKeyIds.get(i));
			}
		}
		else if (classIsEmpty && !studentIsEmpty) {
			hql.append(" left join r.studentInfos as sis where sis.id in (");
			
			for (int i = 0;i < studentKeyIds.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < studentKeyIds.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(studentKeyIds.get(i));
			}
		}
		else {
			hql.append(" left join r.classInfos as cis left join r.studentInfos as sis where cis.id in (");

			for (int i = 0;i < classKeyIds.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < classKeyIds.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(classKeyIds.get(i));
			}
			
			hql.append(" and sis.id in (");
			for (int i = 0;i < studentKeyIds.size();i ++) {
				int size = i + values.size();
				hql.append("?" + size);
				if (i < studentKeyIds.size() - 1) {
					hql.append(",");
				}
				else {
					hql.append(")");
				}
				
				values.add(studentKeyIds.get(i));
			}			
		}
		
		if (startDate != null) {
			hql.append(" and registerDate >= ?" + values.size());
			values.add(startDate);
		}
		
		if (endDate != null) {
			hql.append(" and registerDate <= ?" + values.size());
			values.add(endDate);
		}
		
		return this.doQuery(hql.toString(), curPage, pageSize, values.toArray());
	}
}
