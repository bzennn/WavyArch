package xyz.bzennn.wavyarch.data.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
public class AccountDaoImpl implements AccountDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void save(Account account) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(account);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DaoLayerException("Failed to save account!", e);
		}
	}

	@Override
	public void update(Account account) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(account);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DaoLayerException("Failed to update account!", e);
		}
	}

	@Override
	public Account findByLogin(String login) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Account account = (Account) session.createQuery("from Account where login=:login").setParameter("login", login)
					.uniqueResult();
			transaction.commit();
			session.close();

			return account;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find account!", e);
		}
	}

	@Override
	public void delete(Account account) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.delete(account);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DaoLayerException("Failed to delete account!", e);
		}
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

	@Override
	public void refresh(Account account) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			session.refresh(account);
			session.close();
		} catch (Exception e) {
			throw new DaoLayerException("Failed to delete account!", e);
		}
	}
}
