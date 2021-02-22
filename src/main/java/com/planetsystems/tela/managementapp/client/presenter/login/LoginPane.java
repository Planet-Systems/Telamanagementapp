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

	private Label forgotPassword;
	
	private Label signup;

	public LoginPane() {
		super();
		
		  Img desLogo = new Img("/webapp/images/des_logo.jpg");
			desLogo.setHeight(130);
			desLogo.setWidth(130);
			desLogo.setTooltip("DES LOGO");
			desLogo.setLayoutAlign(Alignment.CENTER);
			//desLogo.setAltText("Des Logo");
			
			Img telaLogo = new Img("/images/telalog.png");
			telaLogo.setHeight(50);
			telaLogo.setWidth(400);
			telaLogo.setTooltip("TELA LOGO");
			telaLogo.setLayoutAlign(Alignment.CENTER);
			//telaLogo.setAltText("Tela log");

			VLayout imgLayout = new VLayout();
			//imgLayout.setBorder("1px solid red");
			imgLayout.setWidth100();
			imgLayout.setAutoHeight();
			imgLayout.setMembersMargin(5);
			imgLayout.addMembers(desLogo , telaLogo);
			
			
			Label loginLabel = new Label("Login");
			loginLabel.setAutoHeight();
			loginLabel.setAutoWidth();
			loginLabel.setPadding(5);
			loginLabel.setMargin(2);
			//loginLabel.setBorder("1px solid green");
			loginLabel.setLayoutAlign(Alignment.CENTER);
			
			
			username = new TextField();
			username.setTitle("Username"); 
			
			password = new PasswordItem();
			password.setTitle("Password");
			password.setWidth("*");
			
			DynamicForm form = new DynamicForm();
			form.setFields(username, password);
			form.setWrapItemTitles(true);
			form.setNumCols(2);
			form.setColWidths("120", "300");
			form.setCellPadding(10);
			
			
			forgotPassword = new Label();
			forgotPassword.setContents("Forgot password");
			forgotPassword.setAutoHeight();
			forgotPassword.setCursor(Cursor.HAND);
	
			
			loginButton = new IButton();
			loginButton.setTitle("Login");
			
			HLayout buttonLayout = new HLayout();
			buttonLayout.setMembersMargin(40);
			buttonLayout.addMembers(forgotPassword , loginButton);
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
			layout.setMembersMargin(10);		
			layout.setWidth100();
			layout.setAutoHeight();
			
						
			layout.addMember(imgLayout);
			layout.addMember(loginLabel);
			layout.addMember(form);
			layout.addMember(buttonLayout);
			layout.addMember(signup);
		    this.addMember(layout);

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

	public Label getForgotPassword() {
		return forgotPassword;
	}

	public Label getSignup() {
		return signup;
	}	

}
