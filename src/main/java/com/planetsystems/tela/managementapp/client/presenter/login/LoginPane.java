package com.planetsystems.tela.managementapp.client.presenter.login;

import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LoginPane extends VLayout {

	private IButton loginButton;
	private TextField username;
	private PasswordItem password;

	private Label forgotPasswordField;
	
	private Label signup;

	public LoginPane() {
		super();
		
		
		  Img desLogo = new Img("des_logo.jpg");
			desLogo.setHeight(130);
			desLogo.setWidth(130);
			desLogo.setTooltip("DIRECTORATE OF EDUCATION STANDARDS");
			desLogo.setLayoutAlign(Alignment.CENTER);
            desLogo.setAlign(Alignment.CENTER);
			
			username = new TextField();
			username.setTitle("Username"); 
			username.setHint("Username");
			username.setShowHintInField(true);
			
			password = new PasswordItem();
			password.setTitle("Password");
			password.setWidth("*");
			password.setHint("Password");
			password.setShowHintInField(true);
			
			DynamicForm form = new DynamicForm();
			form.setFields(username, password);
			form.setWrapItemTitles(true);
			form.setNumCols(2);
			form.setColWidths("120", "300");
			form.setCellPadding(10);
			
			
			forgotPasswordField = new Label();
			forgotPasswordField.setContents("Forgot password");
			forgotPasswordField.setAutoHeight();
			forgotPasswordField.setCursor(Cursor.HAND);
	
			
			loginButton = new IButton();
			loginButton.setTitle("Login");
			
			HLayout buttonLayout = new HLayout();
			buttonLayout.setMembersMargin(40);
			buttonLayout.addMembers(loginButton , forgotPasswordField);
			buttonLayout.setAlign(Alignment.CENTER);		
			buttonLayout.setAutoHeight();
			buttonLayout.setAutoWidth();
			buttonLayout.setMembersMargin(10);
			
			
			signup = new Label();
			signup.setContents("If you don't have an account? SignUp");
			signup.setAutoHeight();
			signup.setPadding(5);
			signup.setCursor(Cursor.HAND);
			signup.setAlign(Alignment.CENTER);

			
			VLayout layout = new VLayout();
			layout.setLayoutAlign(Alignment.CENTER);
			layout.setMembersMargin(10);
			layout.setWidth100();
			layout.setHeight100();
			
			layout.addMembers(desLogo , form , buttonLayout);

		    this.addMember(layout);
		    this.setWidth100();
		    this.setHeight100();

	}

	public IButton getLoginButton() {
		return loginButton;
	}

	public TextField getUsername() {
		return username;
	}

	public PasswordItem getPassword() {
		return password;
	}


	public Label getForgotPasswordField() {
		return forgotPasswordField;
	}

	public Label getSignup() {
		return signup;
	}	

}
