package xyz.bzennn.wavyarch.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AlbumEditForm {

	@NotNull(message = "{field.albumName.required}")
	@NotBlank(message = "{field.albumName.required}")
	@Size(min = 1, max = 200, message = "{field.albumName.size}")
	private String name;

	private Date creationDate;

	@Size(min = 0, max = 60, message = "{field.author.size}")
	private String author;

	private String imagePath;
	
	public AlbumEditForm(String name, String creationDate, String author) {
		this.name = name.trim();
		try {
			this.creationDate = new SimpleDateFormat("yyyy-MM").parse(creationDate.trim());
		} catch (ParseException e) {
			this.creationDate = null;
		}
		this.author = author.trim();
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getName() {
		return name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getAuthor() {
		return author;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
}
