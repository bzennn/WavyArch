package xyz.bzennn.wavyarch.data.dao;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.AuthorRole;
import xyz.bzennn.wavyarch.exception.DaoLayerException;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * Implementation for {@link AuthorRoleDao} 
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AuthorRoleDaoImpl extends BaseDaoImpl<AuthorRole> implements AuthorRoleDao {

	@Override
	public AuthorRole findByName(String name) throws ServiceLayerException {
		return findByAttribute(AuthorRole.class, "name", name);
	}

	@Override
	public boolean isAuthorRoleExists(String name) throws ServiceLayerException {
		try {
			AuthorRole authorRole = findByName(name);
			return authorRole != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
