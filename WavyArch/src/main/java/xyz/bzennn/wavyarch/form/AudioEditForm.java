package xyz.bzennn.wavyarch.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import xyz.bzennn.wavyarch.form.model.AuthorAndRole;

public class AudioEditForm {
	
	@NotNull(message = "{field.audioName.required}")
	@NotBlank(message = "{field.audioName.required}")
	@Size(min = 1, max = 60, message = "{field.audioName.size}")
	private String name;

	private Date creationDate;

	@Size(min = 0, max = 60, message = "{field.genre.size}")
	private String genre;
	
	@Size(min = 0, max = 60, message = "{field.genre.size}")
	private String album;

	private List<@Size(min = 0, max = 60, message = "{field.performer.size}") String> performers;
	
	private List<@Valid AuthorAndRole> authors;

	private List<@Size(min = 0, max = 20, message = "{field.tag.size}") String> tags;

	private String filePath;
	
	private Integer duration;
	
	public AudioEditForm(
			String name, 
			String creationDate, 
			String genre, 
			String album,
			@Size(min = 0, max = 1000, message = "{field.performers.size}") String performers,
			@Size(min = 0, max = 1000, message = "{field.authors.size}") String authors,
			@Size(min = 0, max = 400, message = "{field.tags.size}") String tags) {
		this.name = name.trim();
		try {
			this.creationDate = new SimpleDateFormat("yyyy-MM").parse(creationDate.trim());
		} catch (ParseException e) {
			this.creationDate = null;
		}
		this.genre = genre.trim();
		this.album = album.trim();
		this.performers = processStringList(performers.trim());
		this.authors = processAuthorsList(authors.trim());
		this.tags = processStringList(tags.trim());
	}

	private List<String> processStringList(String listStr) {
		if (listStr == null || listStr.isEmpty()) {
			return Collections.emptyList();
		}
		
		String[] listArr = listStr.split(";");
		for (String part : listArr) {
			part = part.trim();
		}
		List<String> list = Arrays.asList(listArr);
		list.removeIf(s -> s.isEmpty());
		
		return list;
	}

	private List<AuthorAndRole> processAuthorsList(String listStr) {
		if (listStr == null || listStr.isEmpty()) {
			return Collections.emptyList();
		}
		
		String[] authors = listStr.split(";");

		List<AuthorAndRole> resultList = new ArrayList<AuthorAndRole>();
		for (String author : authors) {
			String[] authorAndRole = author.split(":");

			AuthorAndRole authorRole;
			if (authorAndRole.length < 2) {
				if (authorAndRole[0].isEmpty()) {
					continue;
				}
				authorRole = new AuthorAndRole(authorAndRole[0], null);
			} else {
				authorRole = new AuthorAndRole(authorAndRole[0], authorAndRole[1]);
			}
			resultList.add(authorRole);
		}
		

		return resultList;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public String getName() {
		return name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getGenre() {
		return genre;
	}

	public String getAlbum() {
		return album;
	}

	public List<String> getPerformers() {
		return performers;
	}

	public List<AuthorAndRole> getAuthors() {
		return authors;
	}

	public List<String> getTags() {
		return tags;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
}
