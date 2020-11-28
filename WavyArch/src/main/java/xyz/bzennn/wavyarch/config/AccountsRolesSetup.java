package xyz.bzennn.wavyarch.config;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import xyz.bzennn.wavyarch.data.dao.AccountDao;
import xyz.bzennn.wavyarch.data.dao.AccountRoleDao;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.data.model.AccountRole;

/**
 * Bean that adds admin role to accounts from admins.properties 
 *
 * @author bzennn
 * @version 1.0
 */
public class AccountsRolesSetup {

	@Value("${admins_list}")
	private String adminsList;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private AccountRoleDao accountRoleDao;
	
	@PostConstruct
	public void initAdminRoles() {
		if (adminsList != null && !adminsList.isEmpty()) {
			List<String> logins = Arrays.asList(adminsList.split(";"));
			
			AccountRole adminRole = accountRoleDao.findByRoleName("admin");
			AccountRole userRole = accountRoleDao.findByRoleName("user");
			
			List<Account> accounts = accountDao.findAll();
			
			for (Account account : accounts) {
				if (logins.contains(account.getLogin()) && !account.getRole().equals(adminRole)) {
					account.setRole(adminRole);
					accountDao.update(account);
				} else if (account.getRole().equals(adminRole)) {
					account.setRole(userRole);
					accountDao.update(account);
				}
			}
		}
	}
	
}
