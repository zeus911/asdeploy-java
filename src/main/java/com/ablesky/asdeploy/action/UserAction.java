package com.ablesky.asdeploy.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.ablesky.asdeploy.action.support.HttpMethod;
import com.ablesky.asdeploy.action.support.ModelMapActionSupport;
import com.ablesky.asdeploy.pojo.User;
import com.ablesky.asdeploy.service.IUserService;
import com.ablesky.asdeploy.util.AuthUtil;

@ParentPackage("base")
@Namespace("/user")
@Results({
	@Result(name="json", type="json", params={"root", "model"}),
	@Result(name="redirect", type="redirect", location="${redirectLocation}")
})
@SuppressWarnings("serial")
public class UserAction extends ModelMapActionSupport {
	
	@Autowired
	private IUserService userService;

	@Action(value="detail", results = {
		@Result(name="detail", location="detail.jsp")
	})
	public String detail() {
		return "detail";
	}
	
	@Action(value="changePassword", results = {
		@Result(name="changePassword", location="changePassword.jsp")
	})
	public String changePassword() {
		String method = request.getMethod();
		if(HttpMethod.POST.equals(method)) {
			return doChangePassword();
		} else {
			model.put("user", AuthUtil.getCurrentUser());
			return "changePassword";
		}
	}
	
	private String doChangePassword() {
		String oldPassword = getStringParam("oldPassword");
		String newPassword = getStringParam("newPassword");
		if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
			model.put("success", false);
			model.put("message", "原密码和新密码不能为空!");
			return "json";
		}
		User user = userService.getUserById(AuthUtil.getCurrentUser().getId());
		if(!user.getPassword().equals(AuthUtil.hashPassword(user.getUsername(), oldPassword))) {
			model.put("success", false);
			model.put("message", "原密码不正确!");
			return "json";
		}
		user.setPassword(AuthUtil.hashPassword(user.getUsername(), newPassword));
		userService.saveOrUpdateUser(user);
		model.put("success", true);
		return "json";
	}
	
}
