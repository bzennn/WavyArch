package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountAudio;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * Implementation of {@link AccountAudioDao}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AccountAudioDaoImpl extends BaseDaoImpl<AccountAudio> implements AccountAudioDao {

	@Override
	public List<AccountAudio> findByAudioId(Long audioId) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<AccountAudio> list = session.createQuery("from AccountAudio where audio_id=:audio_id").setParameter("audio_id", audioId).getResultList();
			transaction.commit();
			session.close();

			return list;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find account audios!", e);
		}
	}

	@Override
	public AccountAudio findByAudioAndAccount(Audio audio, Account account) throws DaoLayerException {
		Long audioId = audio.getId();
		Long accountId = account.getId();
		
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query = "from AccountAudio where audio_id=:audio_id and account_id=:account_id";
			AccountAudio accountAudio = (AccountAudio) session.createQuery(query).setParameter("audio_id", audioId).setParameter("account_id", accountId).uniqueResult();
			transaction.commit();
			session.close();

			return accountAudio;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find account audio!", e);
		}
	}

	@Override
	public boolean isAccountAudioExists(Audio audio, Account account) throws DaoLayerException {
		try {
			AccountAudio accountAudio = findByAudioAndAccount(audio, account);
			return accountAudio != null;
		} catch (Exception e) {
			throw new DaoLayerException(e);
		}
	}

	@Override
	public List<AccountAudio> findByAccountId(Long accountId) throws DaoLayerException {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<AccountAudio> list = session.createQuery("from AccountAudio where account_id=:account_id").setParameter("account_id", accountId).getResultList();
			transaction.commit();
			session.close();

			return list;
		} catch (Exception e) {
			throw new DaoLayerException("Failed to find account audios!", e);
		}
	}

}
