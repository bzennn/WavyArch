package xyz.bzennn.wavyarch.service;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

/**
 * Service class for {@link Account}
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountService {
	void save(Account account) throws ServiceLayerException;
	Account findByLogin(String login) throws ServiceLayerException;
	boolean isLoginExists(String login) throws ServiceLayerException;
	boolean canAuthenticate(String login, String password) throws ServiceLayerException;
}
