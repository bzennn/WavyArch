package xyz.bzennn.wavyarch.config;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

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
@PropertySource("classpath:admins.properties")
public class AccountsRolesSetup {

	private Logger logger = LogManager.getLogger(AccountsRolesSetup.class);
	
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
			
			logger.info("Accounts role setup!");
			for (Account account : accounts) {
				if (logins.contains(account.getLogin()) && !account.getRole().equals(adminRole)) {
					account.setRole(adminRole);
					accountDao.update(account);
					logger.info("Added admin role to user: " + account.getLogin());
				} else if (account.getRole().equals(adminRole)) {
					account.setRole(userRole);
					accountDao.update(account);
				}
			}
		}
	}
	
}
