package xyz.bzennn.wavyarch.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embedded primary key for {@link AccountAudio} entity 
 *
 * @author bzennn
 * @version 1.0
 */
@Embeddable
public class AccountAudioPK implements Serializable {

	private static final long serialVersionUID = 6952501410655008683L;
	
	@Column(name = "account_id")
	private Long accountId;
	
	@Column(name = "audio_id")
	private Long audioId;
	
	public AccountAudioPK() {}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAudioId() {
		return audioId;
	}

	public void setAudioId(Long audioId) {
		this.audioId = audioId;
	}

	@Override
	public String toString() {
		return "AccountAudioPK [accountId=" + accountId + ", audioId=" + audioId + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountAudioPK other = (AccountAudioPK) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (audioId == null) {
			if (other.audioId != null)
				return false;
		} else if (!audioId.equals(other.audioId))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((audioId == null) ? 0 : audioId.hashCode());
		return result;
	}
}
