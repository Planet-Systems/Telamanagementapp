package com.planetsystems.tela.managementapp.client.presenter.schoolstaff;

import java.util.ArrayList;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
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

@Deprecated
public class SchoolStaffPresenter extends Presenter<SchoolStaffPresenter.MyView, SchoolStaffPresenter.MyProxy>  {
    interface MyView extends View  {
     ControlsPane getControlsPane();
 	 SchoolStaffPane getStaffPane();
    }
    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_SchoolStaff = new Type<RevealContentHandler<?>>();
    
    @Inject
    private DispatchAsync dispatcher;
    
    @Inject
    private PlaceManager placeManager;
    
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

    @NameToken(NameTokens.assessmentperiod) 
    @ProxyCodeSplit
    interface MyProxy extends ProxyPlace<SchoolStaffPresenter> {
    }

    @Inject
    SchoolStaffPresenter(
            EventBus eventBus,
            MyView view, 
            MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
        
    }
    
    @Override
    protected void onBind() {
    	super.onBind();
    setControlsPaneMenuButtons();
    getAllSchoolStaff();
    }
    
    public void setControlsPaneMenuButtons() {
    	MenuButton newButton = new MenuButton("New");
		MenuButton edit = new MenuButton("Edit");
		MenuButton delete = new MenuButton("Delete");
		MenuButton fiter = new MenuButton("Filter");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(newButton);
		buttons.add(edit);
		buttons.add(delete);
		buttons.add(fiter);

		addSchoolStaff(newButton);
		getView().getControlsPane().addMenuButtons(buttons);
		
    	
    }

	private void addSchoolStaff(MenuButton newButton) {
		newButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				SchoolStaffWindow window = new SchoolStaffWindow();
				loadGenderCombo(window, null);
				loadRegisteredCombo(window, null);
				loadSchoolCombo(window, null);
				saveSchoolStaff(window);
				window.show();
				
			}

		});
		
	}
    
	private void saveSchoolStaff(final SchoolStaffWindow window) {
	window.getSaveButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			
			if(checkIfNoSchoolStaffWindowFieldIsEmpty(window)){
				 SchoolStaffDTO dto = new SchoolStaffDTO();
				 //  dto.setId(id);
				   
				   dto.setRegistered(Boolean.valueOf(window.getRegisteredComboBox().getValueAsString()));
				   dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
				 
				   dto.setStaffCode(window.getStaffCode().getValueAsString());
				 //  dto.setStatus(status);
				  // dto.setStaffType(staffType);
			
				   SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolComboBox().getValueAsString());
				   dto.setSchoolDTO(schoolDTO);
					
				   GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
				   generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
				   generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
				   generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
				   generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());
				   generalUserDetailDTO.setGender(window.getGenderComboBox().getValueAsString());
				   generalUserDetailDTO.setNameAbbrev(window.getNameAbrevField().getValueAsString());
				   generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
				   generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
				   
				   dto.setGeneralUserDetailDTO(generalUserDetailDTO);
				   
				   GWT.log("STAFF "+dto);
				   GWT.log("School "+dto.getSchoolDTO().getId());
				   
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SCHOOL_STAFF, dto);
					map.put(NetworkDataUtil.ACTION , RequestConstant.SAVE_SCHOOL_STAFF);
					
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
						
						@Override
						public void onNetworkResult(RequestResult result) {
							clearSchoolStaffWindowFields(window);
							SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getAllSchoolStaff();
						}
					});

			}else {
				SC.warn("Please Fill the fields");
			}
			
		}
	});
	}

	protected boolean checkIfNoSchoolStaffWindowFieldIsEmpty(SchoolStaffWindow window) {
		boolean flag = true;
		
		if(window.getFirstNameField().getValueAsString() == null) flag = false;
		
		if(window.getLastNameField().getValueAsString() == null) flag = false;
		
		if(window.getPhoneNumberField().getValueAsString() == null) flag = false;
		
		if(window.getEmailField().getValueAsString() == null) flag = false;
		
		if(window.getDobItem().getValueAsDate() == null) flag = false;
		
		if(window.getNationalIdField().getValueAsString() == null) flag = false;
		
		if(window.getGenderComboBox().getValueAsString() == null) flag = false;
		
		if(window.getNameAbrevField().getValueAsString() == null) flag = false;
		
		if(window.getStaffCode().getValueAsString() == null) flag = false;
		
		if(window.getRegisteredComboBox().getValueAsString() == null) flag = false;
		
		if(window.getSchoolComboBox().getValueAsString() == null) flag = false;
		
		
		return flag;
	}

	private void clearSchoolStaffWindowFields(SchoolStaffWindow window) {
		
		window.getFirstNameField().clearValue();
		window.getLastNameField().clearValue();
		window.getPhoneNumberField().clearValue();
		window.getEmailField().clearValue();
		window.getDobItem().clearValue();
		window.getNationalIdField().clearValue();
		window.getGenderComboBox().clearValue();
		window.getNameAbrevField().clearValue();
		window.getStaffCode().clearValue();
		window.getRegisteredComboBox().clearValue();
		window.getSchoolComboBox().clearValue();
	}
	
	private void loadGenderCombo(final SchoolStaffWindow window , final String defaultValue) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
		valueMap.put("female", "Female");
		valueMap.put("male", "Male");
		
		window.getGenderComboBox().setValueMap(valueMap);
	}
	
	private void loadRegisteredCombo(final SchoolStaffWindow window , final String defaultValue) {
		LinkedHashMap<Boolean, String> valueMap = new LinkedHashMap<>();
		valueMap.put(true, "Yes");
		valueMap.put(false, "No");
		
		window.getRegisteredComboBox().setValueMap(valueMap);
	}
	
	
	
	private void loadSchoolCombo(final SchoolStaffWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL, null);

		ComboUtil.loadSchoolCombo(window.getSchoolComboBox() , dispatcher , placeManager , defaultValue);
		
	}

	private void getAllSchoolStaff() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_STAFF, null);
		map.put(NetworkDataUtil.ACTION , RequestConstant.GET_SCHOOL_STAFF);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getStaffPane().getStaffListGrid().addRecordsToGrid(result.getSchoolStaffDTOs());
			}
		});

	}

	
    
}