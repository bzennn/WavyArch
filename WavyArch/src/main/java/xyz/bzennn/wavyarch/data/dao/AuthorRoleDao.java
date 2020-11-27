package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.AuthorRole;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link AuthorRole} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AuthorRoleDao {
	void save(AuthorRole authorRole) throws DaoLayerException;
	void update(AuthorRole authorRole) throws DaoLayerException;
	void delete(AuthorRole authorRole) throws DaoLayerException;
	void refresh(AuthorRole authorRole) throws DaoLayerException;
	AuthorRole findByName(String name) throws DaoLayerException;
	boolean isAuthorRoleExists(String name) throws DaoLayerException;
	List<AuthorRole> findAll() throws DaoLayerException;
}
