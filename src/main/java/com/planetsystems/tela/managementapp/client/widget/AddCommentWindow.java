package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.VLayout;



public class AddCommentWindow extends Window {
	
	private ComboBox taskActor;
	private TextAreaItem comment;
	private IButton addCommentButton;
	public AddCommentWindow() {
		super();
		
		taskActor=new ComboBox();
		taskActor.setTitle("Refer to");
		taskActor.hide();
		
		comment=new TextAreaItem();
		comment.setTitle("Reason");
		comment.setShowTitle(true);
		comment.setWidth(400);
		comment.setHeight(150);	
		 
		 
		
		addCommentButton = new IButton("Ok");
		
		DynamicForm formContainer = new DynamicForm(); 
		formContainer.setFields(taskActor,comment);
		formContainer.setMargin(20);
		
		VLayout layout2=new VLayout();
		layout2.addMember(addCommentButton);
		
		VLayout widgetLayout = new VLayout();
		widgetLayout.addMember(formContainer);
		widgetLayout.addMember(layout2);
		//widgetLayout.setMargin
		
		this.addItem(widgetLayout);
		this.setWidth("40%");
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("Reason");
		this.setIsModal(true);
		this.setShowModalMask(true);
		 
		
	}
	public TextAreaItem getComment() {
		return comment;
	}
	public IButton getAddCommentButton() {
		return addCommentButton;
	}
	public ComboBox getTaskActor() {
		return taskActor;
	}
	
	
}
