package xyz.bzennn.wavyarch.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * POJO that represents audio maker
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "AudioMakers")
public class AudioMaker implements Serializable {

	private static final long serialVersionUID = -9000968577185697026L;

	@Id
	@Column(name = "audio_maker_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "image_path", nullable = true)
	private String imagePath;
	
	@Column(name = "creation_date", nullable = true)
	private Date creationDate;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "audioMaker")
	private Set<Performer> audiosPerformed;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "audioMaker")
	private Set<Author> audiosCreated;
	
	public AudioMaker() {}

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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<Performer> getAudiosPerformed() {
		return audiosPerformed;
	}

	public void setAudiosPerformed(Set<Performer> audiosPerformed) {
		this.audiosPerformed = audiosPerformed;
	}

	public Set<Author> getAudiosCreated() {
		return audiosCreated;
	}

	public void setAudiosCreated(Set<Author> audiosCreated) {
		this.audiosCreated = audiosCreated;
	}

	@Override
	public String toString() {
		return "AudioMaker [id=" + id + ", name=" + name + ", imagePath=" + imagePath + ", creationDate=" + creationDate
				+ ", description=" + description + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AudioMaker other = (AudioMaker) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
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
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}
