package com.planetsystems.tela.managementapp.client.presenter.login;

import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
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

		/*Img desLogo = new Img("logo-2.png");*/
		Img desLogo = new Img("des_logo.jpg");
		desLogo.setWidth(100);
		desLogo.setHeight(100);
		desLogo.setTooltip("DIRECTORATE OF EDUCATION STANDARDS");
		desLogo.setLayoutAlign(Alignment.CENTER);
		desLogo.setAlign(Alignment.CENTER);

		username = new TextField();
		username.setTitle("<span style='color:#ffffff;font-weight: 600;'>UserName</span>");
		username.setHint("Username");
		username.setShowHintInField(true);
		username.setWidth("*");
		username.setHeight(30);

		password = new PasswordItem();
		password.setTitle("<span style='color:#ffffff;font-weight: 600;'>Password</span>");
		password.setHint("Password");
		password.setShowHintInField(true);
		password.setWidth("*");
		password.setHeight(30);

		DynamicForm form = new DynamicForm();
		form.setFields(username, password);
		form.setWrapItemTitles(true);
		form.setNumCols(2);
		form.setColWidths("120", "300");
		form.setCellPadding(10);

		forgotPasswordField = new Label();
		forgotPasswordField.setContents("<span style='color:#ffffff;font-weight: 600;'>Forgot password</span>");
		forgotPasswordField.setAutoHeight();
		forgotPasswordField.setCursor(Cursor.HAND);

		loginButton = new IButton();
		loginButton.setTitle("Login");

		signup = new Label();
		signup.setContents("<span style='color:#ffffff;font-weight: 600;'>If you don't have an account? SignUp</span>");
		signup.setAutoHeight();
		signup.setPadding(5);
		signup.setCursor(Cursor.HAND);
		signup.setAlign(Alignment.CENTER);

		HLayout loginbuttonLayout = new HLayout();
		loginbuttonLayout.addMember(loginButton);
		loginbuttonLayout.setAutoHeight();
		loginbuttonLayout.setAutoWidth();

		HLayout otherbuttonLayout = new HLayout();
		otherbuttonLayout.addMember(forgotPasswordField);
		otherbuttonLayout.addMember(signup);
		otherbuttonLayout.setAutoHeight();
		otherbuttonLayout.setAutoWidth();

		VLayout buttonLayout = new VLayout();
		buttonLayout.addMember(loginbuttonLayout);
		buttonLayout.addMember(otherbuttonLayout);
		buttonLayout.setStyleName("login-form-button");
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMembersMargin(10);

		VLayout layout = new VLayout();
		layout.addMember(desLogo);
		layout.addMember(form);
		layout.addMember(buttonLayout);
		layout.setStyleName("login-form");
		layout.setAutoHeight();
		layout.setAutoWidth();

		this.addMember(layout);
		this.setOverflow(Overflow.AUTO);
		this.setBackgroundColor("#f0f0f0");
		/* this.setBackgroundColor("#ffffff"); */

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
