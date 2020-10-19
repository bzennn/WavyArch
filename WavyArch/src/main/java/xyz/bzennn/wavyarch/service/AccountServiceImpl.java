package xyz.bzennn.wavyarch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountDao;
import xyz.bzennn.wavyarch.data.dao.AccountRoleDao;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountRole;

/**
 * Implementation of {@link AccountService}
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private AccountRoleDao accountRoleDao;
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void save(Account account) {
		//account.setPasswordHash(bCryptPasswordEncoder.encode(account.getRawPassword()));
		AccountRole role = accountRoleDao.getById(2);
		account.setRole(role);
		accountDao.save(account);
	}

	@Override
	public Account findByLogin(String login) {
		return accountDao.findByLogin(login);
	}
}
