package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.data.model.AuthorRole;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * DAO interface for {@link AuthorRole} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AuthorRoleDao {
	void save(AuthorRole authorRole) throws ServiceLayerException;
	void update(AuthorRole authorRole) throws ServiceLayerException;
	void refresh(AuthorRole authorRole) throws ServiceLayerException;
	AuthorRole findByName(String name) throws ServiceLayerException;
	boolean isAuthorRoleExists(String name) throws ServiceLayerException;
}
