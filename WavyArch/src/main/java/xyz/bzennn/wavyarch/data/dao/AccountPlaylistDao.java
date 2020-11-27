package xyz.bzennn.wavyarch.data.dao;

import java.util.List;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountPlaylist;
import xyz.bzennn.wavyarch.exception.DaoLayerException;

/**
 * DAO interface for {@link AccountPlaylist}  
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountPlaylistDao {
	void save(AccountPlaylist accountPlaylist) throws DaoLayerException;
	void update(AccountPlaylist accountPlaylist) throws DaoLayerException;
	void refresh(AccountPlaylist accountPlaylist) throws DaoLayerException;
	void delete(AccountPlaylist accountPlaylist) throws DaoLayerException;
	AccountPlaylist findByNameAndAccount(String name, Account account) throws DaoLayerException;
	List<AccountPlaylist> findByAccount(Account account) throws DaoLayerException;
	boolean isAccountPlaylistExists(String name, Account account) throws DaoLayerException;
}
