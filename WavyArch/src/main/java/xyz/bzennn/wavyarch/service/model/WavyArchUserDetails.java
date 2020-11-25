package xyz.bzennn.wavyarch.service.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import xyz.bzennn.wavyarch.data.model.Account;


/**
 * Custom implementation of {@link UserDetails} 
 *
 * @author bzennn
 * @version 1.0
 */
public class WavyArchUserDetails implements UserDetails {

	private static final long serialVersionUID = 822446611534651757L;
	
	private Account account;
	
	public WavyArchUserDetails(Account account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(account.getRole().getName()));
        return authorities;
	}

	@Override
	public String getPassword() {
		return account.getPasswordHash();
	}

	@Override
	public String getUsername() {
		return account.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public Account getAccount() {
		return account;
	}

}
