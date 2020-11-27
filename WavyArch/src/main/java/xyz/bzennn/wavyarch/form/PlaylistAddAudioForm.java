package xyz.bzennn.wavyarch.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import xyz.bzennn.wavyarch.validation.annotation.AudioExists;
import xyz.bzennn.wavyarch.validation.annotation.PlaylistExists;

@PlaylistExists(login = "login", name = "playlistName", message = "{field.playlist.exists}")
public class PlaylistAddAudioForm {
	
	@NotNull(message = "{field.login.required}")
	@NotBlank(message = "{field.login.required}")
	@Size(min = 1, max = 20, message = "{field.login.size}")
	private String login;
	
	@NotNull(message = "{field.playlist.required}")
	@NotBlank(message = "{field.playlist.required}")
	@Size(min = 1, max = 100, message = "{field.playlist.size}")
	private String playlistName;
	
	@NotNull(message = "{field.audioName.required}")
	@NotBlank(message = "{field.audioName.required}")
	@Size(min = 1, max = 60, message = "{field.audioName.size}")
	@AudioExists(message = "{field.audioName.exists}")
	private String audioName;

	public PlaylistAddAudioForm(String login, String playlistName, String audioName) {
		this.login = login.trim();
		this.playlistName = playlistName.trim();
		this.audioName = audioName.trim();
	}

	public String getLogin() {
		return login;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public String getAudioName() {
		return audioName;
	}
	
}
