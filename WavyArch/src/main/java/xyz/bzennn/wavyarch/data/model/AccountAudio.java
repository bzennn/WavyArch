package xyz.bzennn.wavyarch.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * POJO that maps audios to account
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "AccountAudios")
public class AccountAudio implements Serializable {

	private static final long serialVersionUID = -3117981009673449296L;

	@EmbeddedId
	private AccountAudioPK id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@MapsId("accountId")
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@MapsId("audioId")
	@JoinColumn(name = "audio_id")
	private Audio audio;
	
	@Column(name = "added_date")
	private Date addedDate;
	
	public AccountAudio() {}

	public AccountAudioPK getId() {
		return id;
	}

	public void setId(AccountAudioPK id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Audio getAudio() {
		return audio;
	}

	public void setAudio(Audio audio) {
		this.audio = audio;
	}
	
	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	@Override
	public String toString() {
		return "AccountAudio [id=" + id + ", addedDate=" + addedDate + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountAudio other = (AccountAudio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
