package com.planetsystems.tela.managementapp.client.presenter.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
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
import com.planetsystems.tela.managementapp.client.menu.SystemAdministrationData;
import com.planetsystems.tela.managementapp.client.menu.SystemAdministrationDataSource;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.widget.MainStatusBar;
import com.planetsystems.tela.managementapp.client.widget.Masthead;
import com.planetsystems.tela.managementapp.client.widget.NavigationPane;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

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

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_CONFIGURATION,
				new NavigationPaneClickHandler());

	}

    private class NavigationPaneClickHandler implements RecordClickHandler {
		public void onRecordClick(RecordClickEvent event) {

			Record record = event.getRecord();
			String name = record.getAttributeAsString("name");

			PlaceRequest placeRequest = null;
			//new PlaceRequest.Builder().nameToken(name).build();
			switch(name) {
			case SystemAdministrationData.ACADEMIC_YEAR:
				placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.academicyear).build();
				break;
			case SystemAdministrationData.LOCATION:
			    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.region).build();
			    break;
			case SystemAdministrationData.SCHOOLS:
			    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.schoolclasscategory).build();
		
			    break;
			case SystemAdministrationData.SUBJECTS:
			    placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.subjectcategory).build();
			    break;
			}

			placeManager.revealPlace(placeRequest);

		}

	}

	@Override
	public void onHighlightActiveLink(HighlightActiveLinkEvent event) {	 
//	    SC.say("EVENT TRIGGERED "+event.getMessage());
//	      GWT.log("EVENT TRIGGERED "+event.getMessage());
		switch(event.getMessage()) {
		case NameTokens.academicyear:
			
			//getView().getNavigationPane().setBackgroundColor("blue");
		
			 break;
		case NameTokens.region:
			
			//getView().getNavigationPane().setBackgroundColor("red");
		  
			 break;
		case NameTokens.schoolclasscategory:

			 //getView().getNavigationPane().setBackgroundColor("green");

			 break;
		case NameTokens.subjectcategory:
		  
			 //getView().getNavigationPane().setBackgroundColor("pink");
		    break;
		}
	}
    
}