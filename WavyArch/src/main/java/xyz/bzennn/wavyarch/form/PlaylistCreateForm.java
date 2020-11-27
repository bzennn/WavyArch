package xyz.bzennn.wavyarch.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import xyz.bzennn.wavyarch.validation.annotation.PlaylistUnique;

@PlaylistUnique(login = "login", name = "name", message = "{field.playlist.unique}")
public class PlaylistCreateForm {

	@NotNull(message = "{field.login.required}")
	@NotBlank(message = "{field.login.required}")
	@Size(min = 1, max = 20, message = "{field.login.size}")
	private String login; 
	
	@NotNull(message = "{field.playlist.required}")
	@NotBlank(message = "{field.playlist.required}")
	@Size(min = 1, max = 100, message = "{field.playlist.size}")
	private String name;

	private String imagePath;

	public PlaylistCreateForm(String login, String name) {
		this.login = login.trim();
		this.name = name.trim();
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLogin() {
		return login;
	}
	
	public String getName() {
		return name;
	}

	public String getImagePath() {
		return imagePath;
	}

}
