package com.lele.manager.sys.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.Assert;

/**
 * 
 * @param <T>
 */
public class Pagination<T> implements Serializable {

	private static final long serialVersionUID = -4486106205521513763L;

	private List<T> elements = new ArrayList<T>();
	private int pageSize;
	private int pageNumber;
	private long totalElements = 0;
	
	public Pagination() {
		
	}

	public Pagination(String resultHql, Session session, int pageNumber, int pageSize, Object... values) {
		Query resultQuery = session.createQuery(resultHql);
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		try {
			// String countHql = "select count(*) " +
			// removeSelect(removeOrders(resultQuery.getQueryString()));
			String countHql = checkGroupBy(resultQuery.getQueryString());
			Query countQuery = session.createQuery(countHql);
			// resultQuery.setCacheMode(CacheMode.REFRESH);//强行刷新
			for (int i = 0; i < values.length; i++) {
				countQuery.setParameter("" + i, values[i]);
				resultQuery.setParameter("" + i, values[i]);
			}

			List<T> listCount = countQuery.list();
			if (listCount.size() == 1) {
				this.totalElements = (Long) listCount.get(0);
			} else {
				this.totalElements = listCount.size();
			}
			// this.totalElements = (Long) countQuery.uniqueResult();
			if (Integer.MAX_VALUE == this.pageNumber || this.pageNumber > getLastPageNumber()) {
				this.pageNumber = getLastPageNumber();
			}
			if (this.pageNumber < 1) {
				this.pageNumber = 1;
			}
            if(this.pageSize == 0){
                elements = resultQuery.list();
            }
            else
			    elements = resultQuery.setFirstResult((this.pageNumber - 1) * this.pageSize)
							.setMaxResults(this.pageSize).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}

	public Pagination(Query resultQuery, Session session, int pageNumber, int pageSize, Object... values) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		try {
			// String countHql = "select count(*) " +
			// removeSelect(removeOrders(resultQuery.getQueryString()));
			String countHql = checkGroupBy(resultQuery.getQueryString());
			Query countQuery = session.createQuery(countHql);
			for (int i = 0; i < values.length; i++) {
				countQuery.setParameter(i, values[i]);
				resultQuery.setParameter(i, values[i]);
			}
			this.totalElements = (Long) countQuery.uniqueResult();
			if (Integer.MAX_VALUE == this.pageNumber || this.pageNumber > getLastPageNumber()) {
				this.pageNumber = getLastPageNumber();
			}
			if (this.pageNumber < 1) {
				this.pageNumber = 1;
			}
            if(this.pageSize == 0){
                elements = resultQuery.list();
            }
            else
			    elements = resultQuery.setFirstResult((this.pageNumber - 1) * this.pageSize)
							.setMaxResults(this.pageSize).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 构建Pagination对象，完成Hibernate的Query数据的分页处理
	 * 
	 * @param resultQuery
	 *            Hibernate的Query对象
	 * @param total
	 *            统计总数
	 * @param pageNumber
	 *            当前页编码，从1开始，如果传的值为Integer.MAX_VALUE表示获取最后一页。
	 *            如果你不知道最后一页编码，传Integer.MAX_VALUE即可。如果当前页超过总页数，也表示最后一页。 这两种情况将重新更改当前页的页码，为最后一页编码。
	 * @param pageSize
	 *            每一页显示的条目数
	 */
	public Pagination(Query resultQuery, int total, int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		try {
			this.totalElements = total;
			if (Integer.MAX_VALUE == this.pageNumber || this.pageNumber > getLastPageNumber()) // last
			// page
			{
				this.pageNumber = getLastPageNumber();
			}
			if (this.pageNumber < 1) {
				this.pageNumber = 1;
			}
            if(this.pageSize == 0){
                elements = resultQuery.list();
            }
            else
			elements = resultQuery.setFirstResult((this.pageNumber - 1) * this.pageSize)
							.setMaxResults(this.pageSize).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 构建Pagination对象，完成Hibernate的分页处理
	 * 
	 * @param elements
	 *            当前页面要显示的数据
	 * @param total
	 *            所有页面数据总数
	 * @param pageNumber
	 *            当前页编码，从1开始，如果传的值为Integer.MAX_VALUE表示获取最后一页。
	 *            如果你不知道最后一页编码，传Integer.MAX_VALUE即可。如果当前页超过总页数，也表示最后一页。 这两种情况将重新更改当前页的页码，为最后一页编码。
	 * @param pageSize
	 *            每一页显示的条目数
	 */
	public Pagination(List<T> elements, long total, int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = total;

		if (Integer.MAX_VALUE == this.pageNumber || this.pageNumber > getLastPageNumber()) { // last
			// page
			this.pageNumber = getLastPageNumber();
		}
		if (this.pageNumber < 1) {
			this.pageNumber = 1;
		}

		this.elements = elements;
	}

	/**
	 * 构建Pagination对象，完成Hibernate的分页处理
	 * 
	 * @param dataList
	 *            数据List
	 * @param pageNumber
	 *            当前页编码，从1开始，如果传的值为Integer.MAX_VALUE表示获取最后一页。
	 *            如果你不知道最后一页编码，传Integer.MAX_VALUE即可。如果当前页超过总页数，也表示最后一页。 这两种情况将重新更改当前页的页码，为最后一页编码。
	 * @param pageSize
	 *            每一页显示的条目数
	 */
	public Pagination(List<T> dataList, int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = dataList.size();
		if (Integer.MAX_VALUE == this.pageNumber) { // last
			// page
			this.pageNumber = getLastPageNumber();
		} else if (this.pageNumber > getLastPageNumber()) { // last
			if (this.pageNumber % getLastPageNumber() == 0) {
				this.pageNumber = getLastPageNumber();
			} else {
				this.pageNumber = this.pageNumber % getLastPageNumber();
			}
		}
		if (this.pageNumber < 1) {
			this.pageNumber = 1;
		}
		List<T> eList = new ArrayList<T>();
		if (!dataList.isEmpty()) {
			int limit = this.pageNumber * pageSize;
			if (this.pageNumber * pageSize > dataList.size()) {
				limit = dataList.size();
			}
			for (int i = (this.pageNumber - 1) * pageSize; i < limit; i++) {
				eList.add(dataList.get(i));
			}
		}
		this.elements = eList;
	}

	public boolean isFirstPage() {
		return getPageNumber() == 1;
	}

	public boolean isLastPage() {
		return getPageNumber() >= getLastPageNumber();
	}

	public boolean hasNextPage() {
		return getLastPageNumber() > getPageNumber();
	}

	public boolean hasPreviousPage() {
		return getPageNumber() > 1;
	}

	public int getLastPageNumber() {
        if(this.pageSize == 0) return 1;
		return (int) (totalElements % this.pageSize == 0 ? totalElements / this.pageSize : totalElements
						/ this.pageSize + 1);
	}

	/**
	 * 返回List类型数据
	 * 
	 * @return List数据源
	 */
	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public int getThisPageFirstElementNumber() {
		return (getPageNumber() - 1) * getPageSize() + 1;
	}

	public long getThisPageLastElementNumber() {
		int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
		return getTotalElements() < fullPage ? getTotalElements() : fullPage;
	}

	public int getNextPageNumber() {
		return getPageNumber() + 1;
	}

	public int getPreviousPageNumber() {
		return getPageNumber() - 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getElementsSize() {
		return elements.size();
	}

	/*
	 * 去除select 子句，未考虑union的情况
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/*
	 * 去除orderby 子句
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s+by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	private static String checkGroupBy(String hql) {
		Assert.hasText(hql);
		hql = removeSelect(removeOrders(hql));
		Pattern p = Pattern.compile("group\\s+by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		StringBuffer last = new StringBuffer();
		boolean checkFlag = false;
		while (m.find()) {
			checkFlag = true;
			Pattern tempPattern = Pattern.compile("having[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
			Matcher tempMatcher = tempPattern.matcher(m.group());
			while (tempMatcher.find()) {
				tempMatcher.appendReplacement(sb, "");
			}
			tempMatcher.appendTail(sb);
		}
		if (checkFlag) {
			Pattern tempPattern = Pattern.compile("group\\s+by", Pattern.CASE_INSENSITIVE);
			Matcher tempMatcher = tempPattern.matcher(sb.toString());
			while (tempMatcher.find()) {
				tempMatcher.appendReplacement(last, "");
			}
			tempMatcher.appendTail(last);
			if (last.toString().indexOf(",") != -1) {
				last = new StringBuffer(last.toString().split(",")[0]);
			}
			return "select count(distinct " + last + ") " + hql;
		} else {
			return "select count(*) " + hql;
		}

	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @param totalElements
	 *            the totalElements to set
	 */
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}	
}
