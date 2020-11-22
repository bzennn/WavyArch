package xyz.bzennn.wavyarch.data.dao;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountAudio;
import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
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

	@Override
	public Set<AccountAudio> loadAudios(Account account) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Set<AccountAudio> audios = account.getAudios();
			transaction.commit();
			session.close();

			return audios;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to lazily load audios!", e);
		}
	}

	@Override
	public Set<AccountPlaylist> loadPlaylists(Account account) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Set<AccountPlaylist> playlists = account.getPlaylists();
			transaction.commit();
			session.close();

			return playlists;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to lazily load playlists!", e);
		}
	}
	
}
