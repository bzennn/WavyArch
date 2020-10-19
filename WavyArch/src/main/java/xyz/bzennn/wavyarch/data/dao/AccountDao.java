package xyz.bzennn.wavyarch.data.dao;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Account;

/**
 * DAO interface for {@link Account}
 *
 * @author bzennn
 * @version 1.0
 */
public interface AccountDao {
	void save(Account account);
	Account findByLogin(String login);
}
