package xyz.bzennn.wavyarch.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import xyz.bzennn.wavyarch.validation.annotation.AccountCanBeAuthenticated;
import xyz.bzennn.wavyarch.validation.annotation.AccountExists;

@AccountCanBeAuthenticated(login = "login", password = "password", message = "{field.password.wrong}")
public class UserSignInForm {
	
	@NotNull(message = "{field.login.required}")
	@NotBlank(message = "{field.login.required}")
	@Size(min = 1, max = 20, message = "{field.login.size}")
	@AccountExists(message = "{field.login.exists}")
	private String login;
	
	@NotNull(message = "{field.password.required}")
	@NotBlank(message = "{field.password.required}")
	@Size(min = 1, max = 20, message = "{field.password.size}")
	private String password;

	public UserSignInForm(String login, String password) {
		this.login = login.trim();
		this.password = password.trim();
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
}
