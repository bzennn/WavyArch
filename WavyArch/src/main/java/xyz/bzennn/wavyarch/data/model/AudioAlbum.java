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
import javax.persistence.PreRemove;
import javax.persistence.Table;


/**
 * POJO that represents audio album 
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "Albums")
public class AudioAlbum implements Serializable {

	private static final long serialVersionUID = -5345581908083498251L;

	@Id
	@Column(name = "album_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "image_path", nullable = true)
	private String imagePath;
	
	@Column(name = "creation_date", nullable = true)
	private Date creationDate;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "album")
	private Set<Audio> audios;
	
	public AudioAlbum() {}

	@PreRemove
	private void preRemove() {
	    for (Audio audio : audios) {
	        audio.setAlbum(null);
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

	@Override
	public String toString() {
		return "AudioAlbum [id=" + id + ", name=" + name + ", imagePath=" + imagePath + ", creationDate=" + creationDate
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
		AudioAlbum other = (AudioAlbum) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
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
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}
