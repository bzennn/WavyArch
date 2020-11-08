package xyz.bzennn.wavyarch.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Embedded primary key for {@link Performer} entity 
 *
 * @author bzennn
 * @version 1.0
 */
@Embeddable
public class PerformerPK implements Serializable {

	private static final long serialVersionUID = -8173696991320811473L;

	@Column(name = "audio_id")
	private Long audioId;
	
	@Column(name = "audio_maker_id")
	private Long audioMakerId;
	
	public PerformerPK() {}
	
	public Long getAudioId() {
		return audioId;
	}

	public void setAudioId(Long audioId) {
		this.audioId = audioId;
	}

	public Long getAudioMakerId() {
		return audioMakerId;
	}

	public void setAudioMakerId(Long audioMakerId) {
		this.audioMakerId = audioMakerId;
	}

	@Override
	public String toString() {
		return "PerformerPK [audioId=" + audioId + ", audioMakerId=" + audioMakerId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerformerPK other = (PerformerPK) obj;
		if (audioId == null) {
			if (other.audioId != null)
				return false;
		} else if (!audioId.equals(other.audioId))
			return false;
		if (audioMakerId == null) {
			if (other.audioMakerId != null)
				return false;
		} else if (!audioMakerId.equals(other.audioMakerId))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((audioId == null) ? 0 : audioId.hashCode());
		result = prime * result + ((audioMakerId == null) ? 0 : audioMakerId.hashCode());
		return result;
	}
}
