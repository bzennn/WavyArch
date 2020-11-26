package xyz.bzennn.wavyarch.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import xyz.bzennn.wavyarch.config.CommonProperties;
import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.form.AccountEditForm;
import xyz.bzennn.wavyarch.service.AccountService;
import xyz.bzennn.wavyarch.util.FileUtils;
import xyz.bzennn.wavyarch.util.ImageUtils;

/**
 * User account edit controller 
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("/account-edit")
@PropertySource("classpath:upload.properties")
public class AccountEditController {
	
	@Value("${upload.file_path}")
	private String uploadPath;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private ImageUtils imageUtils;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserDetailsAdvice userDetailsAdvice;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showEditAccountPage() {
		return "account_edit";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleAccountEdit(@Valid AccountEditForm form, BindingResult result, @RequestParam("avatar") MultipartFile file, Model model, HttpServletRequest request) {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("formData", form);

			return "account_edit";
		}
		
		Account account = form.buildAccount();
		
		if (file != null && file.getSize() != 0 && file.getContentType().contains("image/")) {
			String avatarPath = uploadPath + CommonProperties.AVATAR_FILE_PATH;
			File avatarFile = fileUtils.getUniqueFile(avatarPath, file.getContentType());
			
			try {
				file.transferTo(avatarFile);
			} catch (Exception e) {
				e.printStackTrace();
				return "account_edit";
			}
			
			try {
				imageUtils.cropImageToSquare(avatarFile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String currentAvatarPath = ((Account) model.getAttribute("user")).getImagePath();
			if (currentAvatarPath != null && !currentAvatarPath.isEmpty()) {
				fileUtils.deleteFile(uploadPath + CommonProperties.AVATAR_FILE_PATH, currentAvatarPath);
			}
			
			account.setImagePath(avatarFile.getName());
		}
		
		accountService.update(account);
		
		userDetailsAdvice.addUserAttribute(request, model);
		
		return "account_edit";
	}
	
}
