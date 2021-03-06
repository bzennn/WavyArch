package xyz.bzennn.wavyarch.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * POJO that maps audios to playlist
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "PlaylistAudios")
public class PlaylistAudio implements Serializable {

	private static final long serialVersionUID = -4630430073616644444L;

	@EmbeddedId
	private PlaylistAudioPK id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@MapsId("playlistId")
	@JoinColumn(name = "playlist_id")
	private AccountPlaylist playlist;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@MapsId("audioId")
	@JoinColumn(name = "audio_id")
	private Audio audio;
	
	@Column(name = "added_date", insertable = false)
	private Date addedDate;
	
	public PlaylistAudio() {}

	public PlaylistAudioPK getId() {
		return id;
	}

	public void setId(PlaylistAudioPK id) {
		this.id = id;
	}

	public AccountPlaylist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(AccountPlaylist playlist) {
		this.playlist = playlist;
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
		return "PlaylistAudio [id=" + id + ", addedDate=" + addedDate + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaylistAudio other = (PlaylistAudio) obj;
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
