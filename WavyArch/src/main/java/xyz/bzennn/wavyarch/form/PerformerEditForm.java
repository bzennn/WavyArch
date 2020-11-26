package xyz.bzennn.wavyarch.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PerformerEditForm {

	@NotNull(message = "{field.performer.required}")
	@NotBlank(message = "{field.performer.required}")
	@Size(min = 1, max = 60, message = "{field.performer.size}")
	private String name;

	private Date creationDate;

	@Size(min = 0, max = 200, message = "{field.description.size}")
	private String description;

	private String imagePath;

	public PerformerEditForm(String name, String creationDate, String description) {
		this.name = name.trim();
		try {
			this.creationDate = new SimpleDateFormat("yyyy-MM").parse(creationDate.trim());
		} catch (ParseException e) {
			this.creationDate = null;
		}
		this.description = description.trim();
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

	public String getDescription() {
		return description;
	}

	public String getImagePath() {
		return imagePath;
	}

}
