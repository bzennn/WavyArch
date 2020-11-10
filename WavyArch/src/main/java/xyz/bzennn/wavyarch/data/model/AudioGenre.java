package xyz.bzennn.wavyarch.data.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

/**
 * POJO that represents audio genre
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "Genres")
public class AudioGenre implements Serializable {
	
	private static final long serialVersionUID = 6739761052943929791L;

	@Id
	@Column(name = "genre_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "genre")
	private Set<Audio> audios;
	
	public AudioGenre() {}
	
	@PreRemove
	private void preRemove() {
	    for (Audio audio : audios) {
	        audio.setGenre(null);
	    }
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Audio> getAudios() {
		return audios;
	}

	public void setAudios(Set<Audio> audios) {
		this.audios = audios;
	}

	@Override
	public String toString() {
		return "AudioGenre [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AudioGenre other = (AudioGenre) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}
