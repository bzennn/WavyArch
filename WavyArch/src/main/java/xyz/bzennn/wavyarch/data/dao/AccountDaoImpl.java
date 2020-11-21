package xyz.bzennn.wavyarch.data.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation for {@link AccountDao}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Account findByLogin(String login) throws DaoLayerException {
		return findByAttribute(Account.class, "login", login);
	}

	@Override
	public boolean isLoginExists(String login) throws DaoLayerException {
		try {
			Account account = findByLogin(login);
			return account != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}
	
}
