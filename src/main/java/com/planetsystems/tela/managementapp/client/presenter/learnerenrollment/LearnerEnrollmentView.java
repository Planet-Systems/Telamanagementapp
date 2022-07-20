package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class LearnerEnrollmentView extends ViewImpl implements LearnerEnrollmentPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;

	private LearnerEnrollementPane learnerEnrollementPane;
	private LeanerDetailsPane leanerDetailsPane;
	private ControlsPane controlsPane;

	private TabSet tabSet;

	@Inject
	LearnerEnrollmentView() {

		panel = new VLayout();

		learnerEnrollementPane = new LearnerEnrollementPane();
		leanerDetailsPane = new LeanerDetailsPane();

		controlsPane = new ControlsPane();

		tabSet = new TabSet();

		Tab tab1 = new Tab();
		tab1.setTitle("Learner Head Count");
		tab1.setPane(learnerEnrollementPane);

		Tab tab2 = new Tab();
		tab2.setTitle("Learner Details");
		tab2.setPane(leanerDetailsPane);

		tabSet.addTab(tab2);
		tabSet.addTab(tab1);

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

	public LearnerEnrollementPane getLearnerEnrollementPane() {
		return learnerEnrollementPane;
	}

	public LeanerDetailsPane getLeanerDetailsPane() {
		return leanerDetailsPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}
	
	

}