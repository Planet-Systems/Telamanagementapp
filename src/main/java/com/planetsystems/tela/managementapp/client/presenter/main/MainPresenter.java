package com.planetsystems.tela.managementapp.client.presenter.main;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent.HighlightActiveLinkHandler;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.menu.SystemAdministrationData;
import com.planetsystems.tela.managementapp.client.menu.SystemAdministrationDataSource;
import com.planetsystems.tela.managementapp.client.menu.SystemAttendanceData;
import com.planetsystems.tela.managementapp.client.menu.SystemAttendanceDataSource;
import com.planetsystems.tela.managementapp.client.menu.SystemEnrollmentData;
import com.planetsystems.tela.managementapp.client.menu.SystemEnrollmentDataSource;
import com.planetsystems.tela.managementapp.client.menu.SystemTimeTableData;
import com.planetsystems.tela.managementapp.client.menu.SystemTimeTableDataSource;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.widget.MainStatusBar;
import com.planetsystems.tela.managementapp.client.widget.Masthead;
import com.planetsystems.tela.managementapp.client.widget.NavigationPane;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

@SuppressWarnings("deprecation")
public class MainPresenter extends Presenter<MainPresenter.MyView, MainPresenter.MyProxy> implements HighlightActiveLinkHandler   {
    interface MyView extends View  {
    	public Masthead getMastHead();

    	public NavigationPane getNavigationPane();

    	public MainStatusBar getMainStatusBar();
    }
    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_Main = new Type<RevealContentHandler<?>>();

    @Inject
    private PlaceManager placeManager;
    
    @ProxyStandard
    interface MyProxy extends Proxy<MainPresenter> {
    }

    @Inject
    MainPresenter(
            EventBus eventBus,
            MyView view, 
            MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);   
    }
    
    @Override
    protected void onBind() {
    	super.onBind();
    	registerEvents();
    	manageUserProfile("Wanfadger", "Admin");
     loadMenu();
    }
    
    private void registerEvents() {
    	 registerHandler(getEventBus().addHandler(HighlightActiveLinkEvent.getType(), this));
		
	}

	@Override
    protected void onReset() {
    	super.onReset();
    }
    
  
    
   
    private void loadMenu() {
		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_CONFIGURATION,
				SystemAdministrationDataSource.getInstance(SystemAdministrationData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_CONFIGURATION , new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				onSystemConfigurationMenuClick(event);
			}

		});
		
		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_ENROLLMENT,
				SystemEnrollmentDataSource.getInstance(SystemEnrollmentData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_ENROLLMENT , new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				onSystemEnrollmentMenuClick(event);
			}

		} );
		
		
		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_ATTENDANCE,
				SystemAttendanceDataSource.getInstance(SystemAttendanceData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_ATTENDANCE , new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				onSystemAttendanceMenuClick(event);
			}

		});
		
		
		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_TABLES,
				SystemTimeTableDataSource.getInstance(SystemTimeTableData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_TABLES , new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				onSystemTimeTableMenuClick(event);
			}

		});
		
		
	}
    

	private void onSystemTimeTableMenuClick(RecordClickEvent event) {
		Record record = event.getRecord();
		String name = record.getAttributeAsString("name");
		 placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.timeTable).build());
		
	}
    
	private void onSystemAttendanceMenuClick(RecordClickEvent event) {

		Record record = event.getRecord();
		String name = record.getAttributeAsString("name");

		PlaceRequest placeRequest = null;
		
		switch (name) {
		case SystemAttendanceData.LEARNER :
			 placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.learnerAttendance).build();
			break;
			
		case SystemAttendanceData.STAFF:
			 placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.staffAttendance).build();
			break;
		}
		placeManager.revealPlace(placeRequest);
	}
    
	private void onSystemConfigurationMenuClick(RecordClickEvent event) {
		Record record = event.getRecord();
		String name = record.getAttributeAsString("name");

		PlaceRequest placeRequest = null;
		
		switch (name) {
		case SystemAdministrationData.ACADEMIC_YEAR:
			placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.academicYear).build();
			break;
		case SystemAdministrationData.LOCATION:
		    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.region).build();
		    break;
		case SystemAdministrationData.SCHOOLS:
		    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.schoolClassCategory).build();
	
		    break;
		case SystemAdministrationData.SUBJECTS:
		    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.subjectCategory).build();
		    break;
		}
		
		placeManager.revealPlace(placeRequest);
		
	}
    
    private void onSystemEnrollmentMenuClick(RecordClickEvent event) {
    	Record record = event.getRecord();
		String name = record.getAttributeAsString("name");

		PlaceRequest placeRequest = null;
	
		switch (name) {
		case SystemEnrollmentData.STAFF_ENROLLMENT :
			 placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.enrollment).build();
			break;
			
		case SystemEnrollmentData.LEARNER_ENROLLMENT :
			 placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.learnerEnrollment).build();
			break;
		}
		placeManager.revealPlace(placeRequest);
	}

    /*
     * to be removed after agreeing
     */
    @Deprecated
    private class NavigationPaneClickHandler implements RecordClickHandler {
		public void onRecordClick(RecordClickEvent event) {

			Record record = event.getRecord();
			String name = record.getAttributeAsString("name");
			

			PlaceRequest placeRequest = null;
			switch(name) {
//			case SystemAdministrationData.ACADEMIC_YEAR:
//				placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.academicyear).build();
//				break;
//			case SystemAdministrationData.LOCATION:
//			    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.region).build();
//			    break;
//			case SystemAdministrationData.SCHOOLS:
//			    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.schoolclasscategory).build();
//		
//			    break;
//			case SystemAdministrationData.SUBJECTS:
//			    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.subjectcategory).build();
//			    break;

//			case SystemEnrollmentData.STAFF_ENROLLMENT :
//				 placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.enrollment).build();
//				break;
//				
//			case SystemEnrollmentData.LEARNER_ENROLLMENT :
//				 placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.learnerEnrollment).build();
//				break;	
				
//			case SystemAttendanceData.LEARNER :
//				 placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.learnerAttendance).build();
//				break;
//				
//			case SystemAttendanceData.STAFF:
//				 placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.staffAttendance).build();
//				break;
		
			}

			placeManager.revealPlace(placeRequest);

		}

	}
//    
//	private void onSystemTimeTableMenuClick(RecordClickEvent event) {
//		Record record = event.getRecord();
//		String name = record.getAttributeAsString("name");
//	SC.say("YYE");	
//		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.timeTable).build();
//			placeManager.revealPlace(placeRequest);
//	
//	}

	@Override
	public void onHighlightActiveLink(HighlightActiveLinkEvent event) {	 
//	    SC.say("EVENT TRIGGERED "+event.getMessage());
//	      GWT.log("EVENT TRIGGERED "+event.getMessage());
		switch(event.getMessage()) {
		case NameTokens.academicYear:
			
			//getView().getNavigationPane().setBackgroundColor("blue");
		
			 break;
		case NameTokens.region:
			
			//getView().getNavigationPane().setBackgroundColor("red");
		  
			 break;
		case NameTokens.schoolClassCategory:

			 //getView().getNavigationPane().setBackgroundColor("green");

			 break;
		case NameTokens.subjectCategory:
		  
			 //getView().getNavigationPane().setBackgroundColor("pink");
		    break;
		}
	}
	
	
	private void manageUserProfile(String userName, String role) {

		Label banner2 = new Label();
		banner2.setContents("<p style='margin-left:10px; text-align:center;'>" + userName + "</p>"
				+ "<p style='margin-left:10px; text-align:center;'>" + role + "</p>");
		banner2.setAutoHeight();
		banner2.setAlign(Alignment.LEFT);
		banner2.setWidth100();

		VStack layout = new VStack();
		layout.addMember(banner2);
		layout.setMembersMargin(0);
		layout.setAutoHeight();

		final Menu menu = new Menu();

		MenuItem item1 = new MenuItem(userName);

		MenuItem item2 = new MenuItem("Change Password");

		MenuItem item3 = new MenuItem("Logout");

		item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				//changePassword();
			}
		});

		item3.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			public void onClick(MenuItemClickEvent event) {

				logOut();

			}
		});

		MenuItemSeparator separator = new MenuItemSeparator();
		menu.setItems(item1, item2, separator, item3);
		getView().getMastHead().getUserProfile().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			public void onClick(ClickEvent event) {
				menu.showNextTo(getView().getMastHead().getUserProfile(), "bottom");

			}
		});
		
	}
	
	
	
	
	public void logOut() {
		  SessionManager.getInstance().logOut(placeManager);	
	}
	
    
}