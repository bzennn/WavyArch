package xyz.bzennn.wavyarch.data.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * POJO that represents audio performer 
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "Performers")
public class Performer implements Serializable {
	
	private static final long serialVersionUID = 6553030315203433363L;

	@EmbeddedId
	private PerformerPK id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@MapsId("audioId")
	@JoinColumn(name = "audio_id")
	private Audio audio;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@MapsId("audioMakerId")
	@JoinColumn(name = "audio_maker_id")
	private AudioMaker audioMaker;
	
	public Performer() {}
	
	public PerformerPK getId() {
		return id;
	}

	public void setId(PerformerPK id) {
		this.id = id;
	}

	public Audio getAudio() {
		return audio;
	}

	public void setAudio(Audio audio) {
		this.audio = audio;
	}

	public AudioMaker getAudioMaker() {
		return audioMaker;
	}

	public void setAudioMaker(AudioMaker audioMaker) {
		this.audioMaker = audioMaker;
	}
	
	@Override
	public String toString() {
		return "Performer [id=" + id + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Performer other = (Performer) obj;
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
