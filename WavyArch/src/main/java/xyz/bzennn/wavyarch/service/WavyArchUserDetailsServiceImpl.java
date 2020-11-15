package xyz.bzennn.wavyarch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xyz.bzennn.wavyarch.data.dao.AccountDao;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.service.model.WavyArchUserDetails;

/**
 * Custom implementation of {@link UserDetailsService} 
 *
 * @author bzennn
 * @version 1.0
 */
@Service
public class WavyArchUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountDao.findByLogin(username);
		if (account == null) {
			throw new UsernameNotFoundException("Account with login " + username + " not exists!");
		}
		
		return new WavyArchUserDetails(account);
	}
	
}
