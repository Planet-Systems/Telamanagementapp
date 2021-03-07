package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class SubjectCategoryView extends ViewImpl implements SubjectCategoryPresenter.MyView {

	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private ControlsPane controlsPane;
	private TabSet tabSet;
	private SubCategoryPane subCategoryPane;
	private SubjectPane subjectPane;
	
	public static final String SUB_CATEGORY_TAB_TITLE= "Subject Categories";
	public static final String SUBJECT_TAB_TITLE= "Subjects";

    @Inject
    SubjectCategoryView() {
    	panel = new VLayout();
    	controlsPane = new ControlsPane();
    	subCategoryPane = new SubCategoryPane();
    	subjectPane = new SubjectPane();
    	tabSet = new TabSet();
    	
    	Tab subcategoryTab = new Tab();
    	subcategoryTab.setTitle(SUB_CATEGORY_TAB_TITLE);
    	subcategoryTab.setPane(subCategoryPane);
    	
    	Tab subjecTabTab = new Tab();
    	subjecTabTab.setTitle(SUBJECT_TAB_TITLE);
    	subjecTabTab.setPane(subjectPane);
    	
    	tabSet.addTab(subjecTabTab);
    	tabSet.addTab(subcategoryTab);
    	
    	
    	panel.addMember(controlsPane);
    	panel.addMember(tabSet);
    	panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
      
    }
    
    public Widget asWidget() {
		return panel;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}

	public SubCategoryPane getSubCategoryPane() {
		return subCategoryPane;
	}

	public SubjectPane getSubjectPane() {
		return subjectPane;
	}
	
 
}