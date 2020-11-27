package xyz.bzennn.wavyarch.data.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * POJO that represents audio tag
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "Tags")
public class AudioTag implements Serializable {
	
	private static final long serialVersionUID = -2789625596224894770L;

	@Id
	@Column(name = "tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(schema = "music_library", name = "AudioTags",
			joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"),
			inverseJoinColumns = @JoinColumn(name = "audio_id", referencedColumnName = "audio_id"))
	private Set<Audio> audios;
	
	public AudioTag() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		return "AudioTag [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AudioTag other = (AudioTag) obj;
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
