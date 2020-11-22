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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * POJO that represents audio record
 *
 * @author bzennn
 * @version 1.0
 */
@Entity
@Table(schema = "music_library", name = "Audios")
public class Audio implements Serializable {

	private static final long serialVersionUID = 6847517334997689899L;

	@Id
	@Column(name = "audio_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "duration")
	private Integer duration;
	
	@Column(name = "creation_date", nullable = true)
	private Date creationDate;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "genre_id")
	private AudioGenre genre;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "album_id")
	private AudioAlbum album;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "audio")
	private Set<Performer> performers;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "audio")
	private Set<Author> authors;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(schema = "music_library", name = "AudioTags",
			joinColumns = @JoinColumn(name = "audio_id", referencedColumnName = "audio_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
	private Set<AudioTag> tags;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "audio")
	private Set<AccountAudio> accounts;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "audio")
	private Set<PlaylistAudio> playlists;
	
	public Audio() {}

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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public AudioGenre getGenre() {
		return genre;
	}

	public void setGenre(AudioGenre genre) {
		this.genre = genre;
	}

	public AudioAlbum getAlbum() {
		return album;
	}

	public void setAlbum(AudioAlbum album) {
		this.album = album;
	}
	
	public Set<Performer> getPerformers() {
		return performers;
	}

	public void setPerformers(Set<Performer> performers) {
		this.performers = performers;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Set<AudioTag> getTags() {
		return tags;
	}

	public void setTags(Set<AudioTag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Audio [id=" + id + ", name=" + name + ", filePath=" + filePath + ", duration=" + duration
				+ ", creationDate=" + creationDate + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Audio other = (Audio) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
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
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}
