package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.data.model.AccountRole;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link AccountRole}
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountRoleDao {
	AccountRole getById(int id) throws DaoLayerException;
}
