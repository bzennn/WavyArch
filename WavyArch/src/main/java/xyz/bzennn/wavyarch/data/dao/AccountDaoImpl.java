package xyz.bzennn.wavyarch.data.dao;

import org.springframework.stereotype.Repository;

import xyz.bzennn.wavyarch.data.model.Account;

/**
 * Implementation for {@link AccountDao}
 *
 * @author bzennn
 * @version 1.0
 */
@Repository
public class AccountDaoImpl implements AccountDao {

	@Override
	public void save(Account account) {
		// TODO Auto-generated method stub

	}

	@Override
	public Account findByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
