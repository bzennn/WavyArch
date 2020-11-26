package xyz.bzennn.wavyarch.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.validation.annotation.FieldsValueMatch;
import xyz.bzennn.wavyarch.validation.annotation.LoginUnique;

@FieldsValueMatch(field = "password", fieldMatch = "passwordRepeat", message = "{field.password.match}")
public class UserSignUpForm {

	@NotNull(message = "{field.login.required}")
	@NotBlank(message = "{field.login.required}")
	@Size(min = 1, max = 20, message = "{field.login.size}")
	@LoginUnique(message = "{field.login.unique}")
	private String login;

	@NotNull(message = "{field.password.required}")
	@NotBlank(message = "{field.password.required}")
	@Size(min = 1, max = 20, message = "{field.password.size}")
	private String password;

	@NotNull(message = "{field.password_repeat.required}")
	@NotBlank(message = "{field.password_repeat.required}")
	@Size(min = 1, max = 20, message = "{field.password.size}")
	private String passwordRepeat;

	public UserSignUpForm(String login, String password, String passwordRepeat) {
		this.login = login.trim();
		this.password = password.trim();
		this.passwordRepeat = passwordRepeat.trim();
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}
	
	public Account buildAccount() {
		Account account = new Account();
		account.setLogin(login);
		account.setRawPassword(password);
		account.setRawPasswordConfirm(passwordRepeat);
		
		return account;
	}
}
