package it.unipv.payroll.view;

import it.unipv.payroll.controller.LoginController;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("loginb")
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	LoginController loginController;

	int EmpId;
	String password;
	String userType;
	
	public String validateForm(){
		
		userType = loginController.validate(EmpId, password);
		
		if(userType.equals("nouser")){
			FacesContext context = FacesContext.getCurrentInstance();
			String outputmsg = "Invalid user ID or password";
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Credential Error ", outputmsg ) );
		}
		
		return userType;
		
	}
	
	public int getEmpId() {
		return EmpId;
	}
	public void setEmpId(int empId) {
		EmpId = empId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
