package xyz.bzennn.wavyarch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountDao;
import xyz.bzennn.wavyarch.data.dao.AccountRoleDao;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountRole;
import xyz.bzennn.wavyarch.exception.ServiceLayerException;

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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void save(Account account) throws ServiceLayerException {
		try {
			account.setPasswordHash(passwordEncoder.encode(account.getRawPassword()));
			AccountRole role = accountRoleDao.findByRoleName("user");
			account.setRole(role);
			accountDao.save(account);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to save account!", e);
		}
	}

	@Override
	public Account findByLogin(String login) throws ServiceLayerException {
		try {
			return accountDao.findByLogin(login);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to find account by login!", e);
		}
	}

	@Override
	public boolean isLoginExists(String login) throws ServiceLayerException {
		try {
			return accountDao.isLoginExists(login);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to check if account exists!", e);
		}
	}

	@Override
	public boolean canAuthenticate(String login, String password) throws ServiceLayerException {
		try {
			Account account = findByLogin(login);
			if (account != null) {
				if (passwordEncoder.matches(password, account.getPasswordHash())) {
					return true;
				}
			}
			
			return false;
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to check if user can be authenticated!", e);
		}
	}

	@Override
	public void update(Account account) throws ServiceLayerException {
		try {
			Account currentAccount = findByLogin(account.getLogin());
			
			if (account.getRawPassword() != null && !account.getRawPassword().isEmpty()) {
				currentAccount.setPasswordHash(passwordEncoder.encode(account.getRawPassword()));
			}
			
			if (account.getImagePath() != null && !account.getImagePath().isEmpty() ) {
				currentAccount.setImagePath(account.getImagePath());
			}
			
			if (account.getRole() != null) {
				currentAccount.setRole(account.getRole());
			}
			
			accountDao.update(currentAccount);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to update account!", e);
		}
	}

	@Override
	public void refresh(Account account) throws ServiceLayerException {
		try {
			accountDao.refresh(account);
		} catch (Exception e) {
			throw new ServiceLayerException("Failed to refresh account!", e);
		}
	}
}
