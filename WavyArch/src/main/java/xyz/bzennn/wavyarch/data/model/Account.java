package xyz.bzennn.wavyarch.data.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * POJO that represents user account
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "Accounts")
public class Account {
	
	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "login")
	private String login;
	
	@Column(name = "password_hash")
	private String passwordHash;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id")
	private AccountRole role;
	
	@Transient
	private String rawPassword;
	
	@Transient
	private String rawPasswordConfirm;
	
	public Account() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public AccountRole getRole() {
		return role;
	}
	
	public void setRole(AccountRole role) {
		this.role = role;
	}

	public String getRawPassword() {
		return rawPassword;
	}

	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}

	public String getRawPasswordConfirm() {
		return rawPasswordConfirm;
	}

	public void setRawPasswordConfirm(String rawPasswordConfirm) {
		this.rawPasswordConfirm = rawPasswordConfirm;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", login=" + login + ", passwordHash=" + passwordHash + ", imagePath=" + imagePath
				+ ", role=" + role + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, new String[] { "id", "rawPassword", "rawPasswordConfirm" });
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, new String[] { "id", "rawPassword", "rawPasswordConfirm" });
	}
}
