package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountAudio;
import xyz.bzennn.wavyarch.data.model.Audio;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link AccountAudio} 
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountAudioDao {
	void save(AccountAudio accountAudio) throws DaoLayerException;
	void update(AccountAudio accountAudio) throws DaoLayerException;
	void refresh(AccountAudio accountAudio) throws DaoLayerException;
	void delete(AccountAudio accountAudio) throws DaoLayerException;
	List<AccountAudio> findByAudioId(Long audioId) throws DaoLayerException;
	List<AccountAudio> findByAccountId(Long accountId) throws DaoLayerException;
	AccountAudio findByAudioAndAccount(Audio audio, Account account) throws DaoLayerException;
	boolean isAccountAudioExists(Audio audio, Account account) throws DaoLayerException;
}
