package com.wang.lms.common.baseDao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import com.wang.lms.common.baseDao.BaseDao;
import com.wang.lms.common.page.PageModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;



public class BaseDaoImpl implements BaseDao {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	/** 获取Hibernate4 Session */
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	/**
	 * 统计总条数
	 * @param hql 查询语句
	 */
	@Override public Integer count(final String hql) {
		if (StringUtils.isEmpty(hql)){
    		throw new IllegalArgumentException("hql is null");
    	}
		Object result = getSession().createQuery(hql).uniqueResult();
		return ((Long)result).intValue();
	}
	/**
     * 批量修改或删除
     * @param queryString
     * @param values
     */
     public int bulkUpdate(String queryString, Object[] values){
    	 Query query = getSession().createQuery(queryString);
    	 if(values!=null){
    		 for(int i = 0; i < values.length; i++){
        		 query.setParameter(i, values[i]);
        	 }
    	 }
    	
    	 try{
    		 return query.executeUpdate();
    	 }catch(Exception ex){
    		 throw new RuntimeException(ex);
    	 }
     }
     /**
      * 批量删除
      * @param entities
      */
     public <T> void deleteAll(Collection<T> entities){
    	 for (T obj : entities){
    		 getSession().delete(obj);
    	 }
     }
	/**
	 * 按条件统计总条数
	 * @param hql
	 * @param obj
	 */
	@Override public Integer count(final String hql,final Object... obj) {
		if (ObjectUtils.isEmpty(obj)){
			return count(hql);
		}else{
			if (StringUtils.isEmpty(hql)){
	    		return this.count(hql);
	    	}
			Query query = getSession().createQuery(hql);
			for (int i = 0; i < obj.length; i++) {
				query.setParameter(i, obj[i]);
			}
			Object result = query.uniqueResult();
			return ((Long)result).intValue();
		}
	}
	/**
	 * 删除
	 * @param entities
	 */
	@Override public <T> void delete(T entity) {
		getSession().delete(entity);
	}
	/**
	 * 判断是否存在
	 * @param entities
	 */
	@Override public <T> boolean exist(Class<T> c, Serializable id) {
		if (get(c, id) != null)
			return true;
		return false;
	}
	/**
	 * 查询全部
	 * @param entities
	 */
	@Override public <T> List<T> find(String queryString) {
		Session session = getSession();
		Query query = session.createQuery(queryString);
		return query.list();
	}
	/**
     *  Execute an HQL query. 
     * @param bean
     * @return
     */
	@Override public <T> List<T> find(Class<T> bean){
    	String hql = "FROM " + bean.getSimpleName();
    	return find(hql);
    }
	/**
	 * 按条件查询全部
	 * @param queryString
	 * @param values
	 */
	@Override public List<?> find(String queryString, Object[] values) {
		if (ObjectUtils.isEmpty(values)){
			return find(queryString);
		}else{
			Query query = getSession().createQuery(queryString);
			for (int i = 0; i < values.length; i++){
				query.setParameter(i, values[i]);
			}
			return query.list();
		}
	}
	/**
     * 获取唯一实体
     * @param queryString HQL query string
     * @param params query object array params
     * @return unique object
     * @see org.hibernate#Session
     * @throws IllegalArgumentException if queryString is null
     */
	@Override 
	 public <T> T findUniqueEntity(final String queryString, final Object ... params){
    	if (StringUtils.isEmpty(queryString)){
    		throw new IllegalArgumentException("queryString is null");
    	}
    	if (ObjectUtils.isEmpty(params)){
			return (T)getSession().createQuery(queryString).uniqueResult();
    	}else{
			Query query = getSession().createQuery(queryString);
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			return (T)query.uniqueResult();
    	}
    }
	/**
	 * 命名查询
	 * @param queryName
	 */
	@Override public <T> List<T> findByNamedQuery(String queryName) {
		if (StringUtils.isEmpty(queryName)){
    		throw new IllegalArgumentException("queryName is null");
    	}
		return getSession().getNamedQuery(queryName).list();
	}
	/**
	 * 条件命名查询
	 * @param queryName
	 * @param values
	 */
	@Override public <T> List<T> findByNamedQuery(String queryName, Object... values) {
		if (ObjectUtils.isEmpty(values)){
			return this.findByNamedQuery(queryName);
		}
		Query query = getSession().getNamedQuery(queryName);
		for (int i = 0; i < values.length; i++){
			query.setParameter(i, values[i]);
		}
		return query.list();
	}

	/**
	 * 多条件分页查询
	 * @param hql query string
	 * @param startRow begin row
	 * @param pageSize page number
	 * @param params query object params array
	 * @return the query list<?> result
	 * @see org.hibernate#Session
     * @throws IllegalArgumentException if queryString is null
	 */
	@Override public <T> List<T> findByPage(String hql, Integer startRow,
			Integer pageSize, Object ... params) {
		if (StringUtils.isEmpty(hql)){
    		throw new IllegalArgumentException("hql is null");
    	}
		if (ObjectUtils.isEmpty(params)) {
			return getSession().createQuery(hql).setFirstResult(startRow).setMaxResults(pageSize).list();
		}else {
			Query query = getSession().createQuery(hql);
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			return query.setFirstResult(startRow).setMaxResults(
					pageSize).list();
		}
	}

	/**
	 * 获取一个实体
	 * @param entityClass
	 * @param id
	 */
	@Override public <T> T get(Class<T> entityClass, Serializable id) {
		return (T)getSession().get(entityClass, id);
	}

	@Override public <T> Iterator<T> iterate(String queryString) {
		return (Iterator<T>)getSession().createQuery(queryString).iterate();
	}

	@Override public <T> Iterator<T> iterate(String queryString, Object... values) {
		Query query = getSession().createQuery(queryString);
		for (int i = 0; i < values.length; i++){
			query.setParameter(i, values[i]);
		}
		return (Iterator<T>)query.iterate();
	}

	/**
	 * 加载一个实体
	 * @param entityClass
	 * @param id
	 */
	@Override public <T> T load(Class<T> entityClass, Serializable id) {
		return (T)getSession().load(entityClass, id);
	}

	@Override public <T> void persist(T entity) {
		getSession().persist(entity);
	}
	
	@Override public <T> void refresh(T entity) {
		getSession().refresh(entity);
	}

	/**
	 * 保存
	 * @param
	 * @throws IllegalArgumentException if entity is null
	 */
	@Override public <T> Serializable save(T entity) {
		if (entity == null){
			throw new IllegalArgumentException("entity is null");
		}
		return getSession().save(entity);
	}

	/**
	 * 保存与修改
	 * @param
	 */
	@Override public <T> void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	/**
	 * 保存与修改全部
	 * @param entities
	 */
	@Override public <T> void saveOrUpdateAll(Collection<T> entities) {
		for (T obj : entities){
			getSession().saveOrUpdate(obj);
		}
	}
	/**
	 * 修改
	 * @param entity
	 */
	@Override public <T> void update(T entity) {
		getSession().update(entity);
	}
	/**
	 * 修改所有的实体
	 * @param entities
	 * @throws IllegalArgumentException if entities is null
	 */
	@Override public <T> void updateAll(Collection<T> entities) {
		if (CollectionUtils.isEmpty(entities)){
			throw new IllegalArgumentException("entities is null");
		}
		int i = 0;
		for (Object obj : entities) {
			if (i % 30 == 0) {
				getSession().flush();
				getSession().clear();
			}
			getSession().update(obj);
			i++;
		}
	}
	/**
	 * 保存所有的实体
	 * @param entities
	 * @throws IllegalArgumentException if entities is null
	 */
	@Override public <T> void saveAll(Collection<T> entities) {
		if (CollectionUtils.isEmpty(entities)){
			throw new IllegalArgumentException("entities is null");
		}
		int i = 0;
		for (T obj : entities) {
			if (i % 30 == 0) {
				getSession().flush();
				getSession().clear();
			}
			save(obj);
			i++;
		}
	}
	/**
	 * 多条件分页查询
	 * @param queryString HQL语句
	 * @param pageModel 分页实体
	 * @param params   参数集合，没有参数可为NULL
	 * @return 分页查询后集合对象
	 * @see #findByPage(String, Integer, Integer, List)
	 */
	@Override public <T> List<T> findByPage(String queryString, 
			 	PageModel pageModel,  List<?> params){
		
		/** 处理不传where条件只传and的条件查询 (多条件查询时) where 1=1 */
		String hql = queryString;
   		if (queryString.toLowerCase().indexOf("where") == -1){
   			/** 正则表达式中学习的：从某个文本内容中搜索中我们想要的信息*/
   			Matcher m = Pattern.compile("and").matcher(queryString);
   			if (m.find()){
   				hql = m.replaceFirst("where");
   			}else{
   				m = Pattern.compile("AND").matcher(queryString);
   				if (m.find()){
   					hql = m.replaceFirst("WHERE");
   				}
   			}
   		}
   		// -----------查询总记录条数
		int fromIndex = hql.toLowerCase().indexOf("from");
        int orderIndex = hql.toLowerCase().indexOf("order");
        String hqlCount = "select count(*) " + hql
        		.substring(fromIndex, orderIndex > 0 ? orderIndex : hql.length());
        int totalCount = (params == null || params.isEmpty()) 
        		? count(hqlCount) : count(hqlCount, params.toArray());
        // 设置总记录条数
        pageModel.setRecordCount(totalCount);
        if(totalCount == 0){
        	return new ArrayList<T>();
        }
    	// -------------分页查询
        Object[] temps = (params == null || params.isEmpty()) 
        			? new Object[]{} : params.toArray();
        return this.findByPage(hql, pageModel.getFirstLimitParam(), 
        				pageModel.getPageSize(), temps);
	}

	
}