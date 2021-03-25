package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
public class SystemUserPresenter extends Presenter<SystemUserPresenter.MyView, SystemUserPresenter.MyProxy>  {
    interface MyView extends View  {
    	public ControlsPane getControlsPane();

    	public SystemUserPane getSystemUserPane();
    }
    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_SystemUser = new Type<RevealContentHandler<?>>();
    
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

    @Inject
    private DispatchAsync dispatcher;
    
    @Inject
    private PlaceManager placeManager;

    @NameToken(NameTokens.SystemUser)
    @ProxyCodeSplit
    interface MyProxy extends ProxyPlace<SystemUserPresenter> {
    }

    @Inject
    SystemUserPresenter(
            EventBus eventBus,
            MyView view, 
            MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
        
    }
    
    @Override
    protected void onBind() {
    	super.onBind();
    setControlsPaneMenuButtons();
    getAllSystemUsers();
    }
    
    public void setControlsPaneMenuButtons() {
		MenuButton newButton = new MenuButton("New");
		MenuButton edit = new MenuButton("Edit");
		MenuButton delete = new MenuButton("Delete");
		MenuButton filter = new MenuButton("Filter");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(newButton);
		buttons.add(edit);
		buttons.add(delete);
		buttons.add(filter);

		getView().getControlsPane().addMenuButtons(buttons);
		addSystemUser(newButton);
		
	}

	private void addSystemUser(MenuButton newButton) {
	  newButton.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
	     SystemUserWindow window = new SystemUserWindow();
	     loadEnabledRadioGroupItem(window);
	     loadGenderComboBox(window);
	     window.show();
	     saveSystemUser(window);
		}

	});
		
	}
	
	public void loadEnabledRadioGroupItem(SystemUserWindow window) {
		Map<Boolean, String> valueMap = new LinkedHashMap<Boolean, String>();
		valueMap.put(true , "Yes");
		valueMap.put(false , "No");
		
		window.getEnabledRadioGroupItem().setValueMap(valueMap);
	}
	
	public void loadGenderComboBox(SystemUserWindow window) {
		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("female" , "Female");
		valueMap.put("male" , "Male");
		
		window.getGenderComboBox().setValueMap(valueMap);
	}
    

	private void saveSystemUser(final SystemUserWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			  SystemUserDTO dto = new SystemUserDTO();
			  dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
			  dto.setEnabled(Boolean.valueOf(window.getEnabledRadioGroupItem().getValueAsString()));
			  dto.setPassword(window.getPasswordField().getValueAsString());
			  dto.setUserName(window.getUserNameField().getValueAsString());
			  
			  GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
			  generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
			  generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
			  generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
			  generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
			  generalUserDetailDTO.setGender(window.getGenderComboBox().getValueAsString());
			  generalUserDetailDTO.setNameAbbrev(window.getNameAbbrevField().getValueAsString());
			  generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
			  generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());
			  
			  dto.setGeneralUserDetailDTO(generalUserDetailDTO);
			  
			  GWT.log("USER "+dto);
			  if(confirmPassword(window)) {
				  if(checkIfNoSystemUserWindowFieldIsEmpty(window)) {
					  LinkedHashMap<String, Object> map = new LinkedHashMap<>();
						map.put(RequestConstant.SAVE_SYSTEM_USER, dto);
						map.put(NetworkDataUtil.ACTION , RequestConstant.SAVE_SYSTEM_USER);
						
						NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
							
							@Override
							public void onNetworkResult(RequestResult result) {
								clearSystemUserWindowFields(window);
								getAllSystemUsers();
							}
						});
				  }else {
					  SC.say("Please Fill all fields");	
				  }
							  
			  }else {
				  SC.say("Passwords don't match");
			  }
			  			  
			}
		});
	}
	
	
	private void clearSystemUserWindowFields(SystemUserWindow window) {
		window.getFirstNameField().clearValue();
		window.getLastNameField().clearValue();
		window.getPhoneNumberField().clearValue();
		window.getEmailField().clearValue();
		window.getDobItem().clearValue();
		window.getNationalIdField().clearValue();
		window.getGenderComboBox().clearValue();
		window.getNameAbbrevField().clearValue();
		window.getPasswordField().clearValue();
		window.getConfirmPasswordField().clearValue();
		window.getUserNameField().clearValue();
		window.getEnabledRadioGroupItem().clearValue();
	}
    
	public boolean confirmPassword(final SystemUserWindow window) {
		boolean flag = false;
		if(window.getPasswordField().getValueAsString().equals(window.getConfirmPasswordField().getValueAsString())) 
		  flag = true;
		return flag;
	}
	
	protected boolean checkIfNoSystemUserWindowFieldIsEmpty(SystemUserWindow window) {
		boolean flag = true;
		
		if(window.getFirstNameField().getValueAsString() == null) flag = false;
		
		if(window.getLastNameField().getValueAsString() == null) flag = false;
		
		if(window.getPhoneNumberField().getValueAsString() == null) flag = false;
		
		if(window.getEmailField().getValueAsString() == null) flag = false;
		
		if(window.getDobItem().getValueAsDate() == null) flag = false;
		
		if(window.getNationalIdField().getValueAsString() == null) flag = false;
		
		if(window.getGenderComboBox().getValueAsString() == null) flag = false;
		
		if(window.getNameAbbrevField().getValueAsString() == null) flag = false;
		
		if(window.getEnabledRadioGroupItem().getValueAsString() == null) flag = false;
		
		if(window.getPasswordField().getValueAsString() == null) flag = false;
		
		if(window.getConfirmPasswordField().getValueAsString() == null) flag = false;
		
		
		return flag;
	}

	
	private void getAllSystemUsers() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ALL_SYSTEM_USERS, null);
		map.put(NetworkDataUtil.ACTION ,RequestConstant.GET_ALL_SYSTEM_USERS);
		
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSystemUserPane().getSystemUserListGrid().addRecordsToGrid(result.getSystemUserDTOs());
			}
		});
	}
	
	
}