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
public class PlaylistAudioPK implements Serializable {

	private static final long serialVersionUID = -4803871158005607109L;

	@Column(name = "playlist_id")
	private Long playlistId;
	
	@Column(name = "audio_id")
	private Long audioId;
	
	public PlaylistAudioPK() {}

	public Long getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Long playlistId) {
		this.playlistId = playlistId;
	}

	public Long getAudioId() {
		return audioId;
	}

	public void setAudioId(Long audioId) {
		this.audioId = audioId;
	}

	@Override
	public String toString() {
		return "PlaylistAudioPK [playlistId=" + playlistId + ", audioId=" + audioId + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaylistAudioPK other = (PlaylistAudioPK) obj;
		if (audioId == null) {
			if (other.audioId != null)
				return false;
		} else if (!audioId.equals(other.audioId))
			return false;
		if (playlistId == null) {
			if (other.playlistId != null)
				return false;
		} else if (!playlistId.equals(other.playlistId))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((audioId == null) ? 0 : audioId.hashCode());
		result = prime * result + ((playlistId == null) ? 0 : playlistId.hashCode());
		return result;
	}
	
}
