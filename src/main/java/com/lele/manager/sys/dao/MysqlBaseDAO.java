package com.lele.manager.sys.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@SuppressWarnings({ "unchecked" })
public abstract class MysqlBaseDAO<T> {

//	private static final Log LOG = LogFactory.getLog(MysqlBaseDAO.class);

	private static final String ORDER_LIST_PROPERTY_NAME = "orderList";
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected Class<T> clazz;
	
	public MysqlBaseDAO() {
		this.clazz = getPoClass();
	}
	
	private Class<T> getPoClass() {
		Type type = getClass().getGenericSuperclass();
		Class<T> poClass = null;
		if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			poClass = (Class<T>) pType.getActualTypeArguments()[0];
		}
		return poClass;
	}
	
	public Session getNewSession() {
		return sessionFactory.openSession();
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public T getById(Session session, Serializable id) {
		Assert.notNull(id, "id is required");
        T t = (T)session.get(clazz, id);
		return t;
	}
	
	public T getById(Serializable id) {
		Assert.notNull(id, "id is required");
        Session session = getNewSession();
        T t = (T)session.get(clazz, id);
        session.close();
		return t;
	}

    @Transactional
	public void delete(T po) {
		Assert.notNull(po, "entity is required");
		getSession().delete(po);

	}
    
	@Transactional
	public void createAll(List<?> polist) {
		for (int i = 0; i < polist.size(); i++) {
			Object po = polist.get(i);
			getSession().saveOrUpdate(po);
		}
	}

	public List<T> findByJDBC(String hql, Object... values) {
        Session session = getNewSession();
		SQLQuery query = session.createSQLQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		List<T> list = query.list();
        session.close();
		return list;
	}

    @Transactional
	public void deleteById(Serializable id) {
		Assert.notNull(id, "id is required");
		Object o = getById(id);
		if (o != null) {
			getSession().delete(o);
		}
	}

	public List<T> findAll() {
        Session session = getNewSession();
		List<T> list = session.createQuery(getFromHql()).list();
        session.close();
		return list;
	}

    @Transactional
	public void save(T po) {
		Assert.notNull(po, "entity is required");
		getSession().saveOrUpdate(po);
	}

    @Transactional
	public void create(T po) {
		Assert.notNull(po, "entity is required");
	    getSession().save(po);
	}

    @Transactional
    public void rollback() {
    	getSession().getTransaction().rollback();
    }
    
    @Transactional
	public void merge(T po) {
		Assert.notNull(po, "entity is required");
		getSession().merge(po);
	}

    @Transactional
	public void update(T po) {
		Assert.notNull(po, "entity is required");
		getSession().merge(po);
	}

	public Pagination<T> doQuery(String hql, int curPage, int pageSize, Object... values) {

        Session session = getNewSession();
        Pagination<T> hp = new Pagination<T>(hql, session, curPage, pageSize, values);
        session.close();
        return hp;
	}
	
	public Pagination<Map> doQueryListMap(String hql, int curPage, int pageSize, Object... values) {
		Session session = getNewSession();
//		List<Map> lmap = session.createQuery(hql).list();
//		session.close();
		Pagination<Map> plmap = new Pagination<Map>(hql, session, curPage, pageSize, values);
		session.close();
		return plmap;
	}
	
	public Pagination<T> doSqlQuery(String sql, int curPage, int pageSize, Object ...values) {
		Session session = getNewSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
		
        Pagination<T> hp = new Pagination<T>(sqlQuery, session, curPage, pageSize, values);
		session.close();
		return hp;
	}

	public Pagination<T> doQuery(Session session, String hql, int curPage, int pageSize, Object... values) {

        Pagination<T> hp = new Pagination<T>(hql, session, curPage, pageSize, values);
        return hp;
	}
	
	public T doQueryUnique(final String hql, final Object... values) {
        Session session = getNewSession();
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		T t = (T) query.uniqueResult();
        session.close();
		return t;
	}
	
	public T doQueryUnique(Session session, final String hql, final Object... values) {
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		T t = (T) query.uniqueResult();
		return t;
	}	
	
	public <R> R doQueryUnique(Class<R> resultClazz, final String hql, final Object... values) {
		
		Session session = getNewSession();
		
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		
		try {
			Object obj = query.uniqueResult();
			if (obj != null) {
				return (R) obj;
			}
		}
		catch (HibernateException e) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < values.length; i++) {
				sb.append(values[i]);
				sb.append(" ");
			}
			
			System.out.println("QueryUnique Exception: " + hql + " " + sb.toString());
			e.printStackTrace();
			return null;
		} 
		finally {
	        session.close();
		}
		
		return null;
	}	
	
	
	public T doQueryFirst(final String hql, final Object... values) {
        Session session = getNewSession();
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<T> list = query.list();
        session.close();
		return list.isEmpty() ? null : (T) list.get(0);

	}
	
	public T doQueryFirst(Session session, final String hql, final Object... values) {
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<T> list = query.list();
		return list.isEmpty() ? null : (T) list.get(0);
	}
	
	public <R> R doQueryFirst(Class<R> resultClazz, final String hql, final Object... values) {
		
		Session session = getNewSession();
		
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		try {
			List<R> list = query.list();
	        return list.isEmpty() ? null : (R) list.get(0);
		}
		catch (Exception e) {
			return null;
		}
		finally {
			session.close();
		}
	}	
	
   @Transactional
	public void executeHsql(final String hql, final Object... values) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		query.executeUpdate();
		sessionFactory.getCache().evictEntityRegion(clazz);
	}

    @Transactional
	public void executeHsqlWithoutEvict(final String hql, final Object... values) {
    	Session session = getNewSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		query.executeUpdate();
		session.close();
	}

    @Transactional
	public void executeNameQuery(final String sql, final Object... values) {
		SQLQuery query = getSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		query.executeUpdate();
	}

    public <R> List<R> doQueryList(Class<R> clazz, final String hql, final Object... values) {
        Session session = getNewSession();
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		List<R> list = query.list();
        session.close();
		return list;
    }
    
	public List<T> doQueryList(final String hql, final Object... values) {
        Session session = getNewSession();
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		List<T> list = query.list();
        session.close();
		return list;
	}
	
	public List<T> doQueryList(Session session, final String hql, final Object... values) {
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		List<T> list = query.list();
		return list;
	}

	public List<Object> doSQLQueryListObject(final String sql, final Object... values) {
        Session session = getNewSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
		
		for (int i = 0; i < values.length; i++) {
			sqlQuery.setParameter("" + i, values[i]);
		}
		
		List<Object> list = sqlQuery.list();
		session.close();
		return list;
	}
	
	public List<T> doSQLQueryList(final String sql, final Object... values) {
        Session session = getNewSession();
        
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(getPoClass());

		for (int i = 0; i < values.length; i++) {
			sqlQuery.setParameter("" + i, values[i]);
		}

        List<T> list = sqlQuery.list();
		session.close();

		return list;
	}
	
	public List<Object> doQueryGroupBy(final String hql, final Object... values) {
		Session session = getNewSession();
		Query query = session.createQuery(hql);

		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}

		List<Object> list = query.list();
		session.close();
		return list;
	}
	
	public List<Object> doQueryMapList(final String sql, final Object... values) {
        Session session = getNewSession();
        
        SQLQuery sqlQuery = session.createSQLQuery(sql);
		Query query = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}

		List<Object> list = query.list();
		session.close();
		return list;
	}
	
	public Pagination<T> doQueryByConds(final String hql, int curPage, int pageSize,
												final List<String> conditions, final Object ... args) {
	
		StringBuilder hqlStr = new StringBuilder();
		hqlStr.append(hql);
		
		if (conditions.size() > 0) {
			hqlStr.append(" where ");
			
			Iterator<String> it = conditions.iterator();

			int index = 0;
			do {
				hqlStr.append(it.next() + "=?" + index);
				
				if (it.hasNext()) {
					index ++;
					hqlStr.append(" and ");
				}
				else {
					break;
				}
			} while(true);
		}
		
		return doQuery(hqlStr.toString(), curPage, pageSize, args);
	}

/*	
	public List<Map<String, Object>> doQueryMapList(final List<String> columns, final String hql, final Object... values) {
        Session session = getNewSession();
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		List<Object[]> list = query.list();
		for (Object[] objs : list) {
			
			int index = 0;
			Map<String, Object> map = new HashMap<String, Object>();
			
			for (Object obj : objs) {
				map.put(columns.get(index), obj);
				index ++;
			}
			
			mapList.add(map);
		}
		
        session.close();
        
        return mapList;
	}
*/
	
    @Transactional
	public List<T> doQueryListInSession(final String sql, final Object... values) {
		SQLQuery query = getSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		List<T> list = query.list();
		return list;
	}
	
	public List<T> doQueryListByName(final String hql, Map<?, ?> values) {
        Session session = getNewSession();
        Query query = session.createQuery(hql);
		query.setCacheable(false);
		Object[] keys = values != null ? values.keySet().toArray() : null;
		Object value = null;
		for (int i = 0; keys != null && i < keys.length; i++) {
			value = values.get(keys[i]);
			if(value instanceof List){
				query.setParameterList((String)keys[i], (List<?>)value);
			}else{
				query.setParameter((String)keys[i], value);
			}
		}
		
		List<T> list = query.list();
        session.close();
		return list;

	}

	public String getFromHql() {
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(clazz);
		
		for (String prop : classMetadata.getPropertyNames()) {
			if (prop.equals(ORDER_LIST_PROPERTY_NAME)) {
				return  "from " + clazz.getName()
				+ " as entity order by entity." + ORDER_LIST_PROPERTY_NAME
				+ " desc";				
			}
		}
		
		return "from " + clazz.getName();
	}

	public long doQueryCount(final String hql, final Object... values) {
        Session session = getNewSession();
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		Long count = (Long) query.uniqueResult();
        session.close();
		return count != null ? count.longValue() : 0;
	}

	public List<T> doQueryLimitListFromZero(final String hql, final int dataNum, final Object... values) {
		return doQueryLimitList(hql, 0, dataNum, values);
	}

	public List<T> doQueryLimitList(final String hql, final int firstResult, final int dataNum,
			final Object... values) {
        Session session = getNewSession();
		Query query = session.createQuery(hql);
		query.setCacheable(false);
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(dataNum);
		List<T> list = query.list();
        session.close();
		return list;

	}
	
	public List<T> doQueryListWithNewSession(final String hql, final Object... values) {
		Session session = this.getNewSession();
		Query query = session.createQuery(hql);
		query.setCacheable(false); 
		for (int i = 0; i < values.length; i++) {
			query.setParameter("" + i, values[i]);
		}
		List<T> list = query.list();
		session.close();
		return list;

	}
}