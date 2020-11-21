package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Basic DAO interface
 *
 * @author bzennn
 * @version 1.0
 * @param <T>
 */
public interface BaseDao<T> {
	void save(T object) throws DaoLayerException;
	void update(T object) throws DaoLayerException;
	void delete(T object) throws DaoLayerException;
	void refresh(T object) throws DaoLayerException;
	T findByAttribute(Class<T> entityClass, String attributeName, Object attributeValue) throws DaoLayerException;
}
