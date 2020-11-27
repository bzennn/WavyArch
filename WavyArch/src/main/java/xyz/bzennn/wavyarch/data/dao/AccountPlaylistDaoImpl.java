package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation of {@link AccountPlaylistDao} 
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AccountPlaylistDaoImpl extends BaseDaoImpl<AccountPlaylist> implements AccountPlaylistDao {

	@Override
	public AccountPlaylist findByNameAndAccount(String name, Account account) throws DaoLayerException {
		Long accountId = account.getId();
		
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query = "from AccountPlaylist where name=:name and account_id=:account_id";
			AccountPlaylist accountPlaylist = (AccountPlaylist) session.createQuery(query).setParameter("name", name).setParameter("account_id", accountId).uniqueResult();
			transaction.commit();
			session.close();

			return accountPlaylist;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find account audio!", e);
		}
	}

	@Override
	public boolean isAccountPlaylistExists(String name, Account account) throws DaoLayerException {
		try {
			AccountPlaylist accountPlaylist = findByNameAndAccount(name, account);
			return accountPlaylist != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@Override
	public List<AccountPlaylist> findByAccount(Account account) throws DaoLayerException {
		Long accountId = account.getId();
		
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query = "from AccountPlaylist where account_id=:account_id";
			@SuppressWarnings("unchecked")
			List<AccountPlaylist> accountPlaylists = (List<AccountPlaylist>) session.createQuery(query).setParameter("account_id", accountId).getResultList();
			transaction.commit();
			session.close();
			
			return accountPlaylists;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

}
