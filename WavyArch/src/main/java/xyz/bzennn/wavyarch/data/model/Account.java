package xyz.bzennn.wavyarch.data.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	@Column(name = "image_path", nullable = true)
	private String imagePath;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "role_id")
	private AccountRole role;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
	private Set<AccountPlaylist> playlists;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "audio")
	private Set<AccountAudio> audios;
	
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

	public Set<AccountPlaylist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Set<AccountPlaylist> playlists) {
		this.playlists = playlists;
	}

	public Set<AccountAudio> getAudios() {
		return audios;
	}

	public void setAudios(Set<AccountAudio> audios) {
		this.audios = audios;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", login=" + login + ", passwordHash=" + passwordHash + ", imagePath=" + imagePath
				+ "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (passwordHash == null) {
			if (other.passwordHash != null)
				return false;
		} else if (!passwordHash.equals(other.passwordHash))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
		return result;
	}
}
