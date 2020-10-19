package xyz.bzennn.wavyarch.service;

import xyz.bzennn.wavyarch.data.model.Account;

/**
 * Service class for {@link Account}
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountService {
	void save(Account account);
	Account findByLogin(String login);
}
