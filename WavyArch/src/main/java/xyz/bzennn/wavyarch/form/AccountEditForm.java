package xyz.bzennn.wavyarch.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.validation.annotation.AccountCanBeAuthenticated;
import xyz.bzennn.wavyarch.validation.annotation.FieldsValueMatch;

@FieldsValueMatch(field = "newPassword", fieldMatch = "newPasswordRepeat", message = "{field.password.match}")
@AccountCanBeAuthenticated(login = "login", password = "oldPassword", message = "{field.password.wrong}")
public class AccountEditForm {
	
	@NotNull(message = "{field.login.required}")
	@NotBlank(message = "{field.login.required}")
	@Size(min = 1, max = 20, message = "{field.login.size}")
	private String login;
	
	@Size(min = 1, max = 20, message = "{field.password.size}")
	private String oldPassword;
	
	@Size(min = 0, max = 20, message = "{field.password.size}")
	private String newPassword;
	
	@Size(min = 0, max = 20, message = "{field.password.size}")
	private String newPasswordRepeat;
	
	public AccountEditForm(String login, String oldPassword, String newPassword, String newPasswordRepeat) {
		this.login = login.trim();
		this.oldPassword = oldPassword.trim();
		this.newPassword = newPassword.trim();
		this.newPasswordRepeat = newPasswordRepeat.trim();
	}

	public String getLogin() {
		return login;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getNewPasswordRepeat() {
		return newPasswordRepeat;
	}
	
	public Account buildAccount() {
		Account account = new Account();
		account.setLogin(login);
		account.setRawPassword(newPassword);
		account.setRawPasswordConfirm(newPasswordRepeat);
		
		return account;
	}
	
}
