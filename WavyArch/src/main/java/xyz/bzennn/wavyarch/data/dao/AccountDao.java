package xyz.bzennn.wavyarch.data.dao;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link Account}
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountDao {
	void save(Account account) throws DaoLayerException;
	void update(Account account) throws DaoLayerException;
	void delete(Account account) throws DaoLayerException;
	Account findByLogin(String login) throws DaoLayerException;
	boolean isLoginExists(String login) throws DaoLayerException;
	void refresh(Account account) throws DaoLayerException;
}
