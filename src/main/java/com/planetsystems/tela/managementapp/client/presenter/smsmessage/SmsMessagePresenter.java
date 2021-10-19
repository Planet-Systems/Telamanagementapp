package com.planetsystems.tela.managementapp.client.presenter.smsmessage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent.Type;
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
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SmsSchoolStaffDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.smsmessage.smsstaff.SchoolStaffPane;
import com.planetsystems.tela.managementapp.client.presenter.smsmessage.smssystemuser.SystemUserPane;
import com.planetsystems.tela.managementapp.shared.requestconstants.SmsRequest;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class SmsMessagePresenter extends Presenter<SmsMessagePresenter.MyView, SmsMessagePresenter.MyProxy> {
	interface MyView extends View {
		TabSet getTabSet();

		SchoolStaffPane getSchoolStaffPane();

		SystemUserPane getSystemUserPane();

	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SmsMessage = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.SMSMESSAGING)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SmsMessagePresenter> {
	}

	@Inject
	SmsMessagePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	@Override
	protected void onBind() {
		super.onBind();
		onTabSelected();
	}

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(SmsMessageView.SCHOOL_STAFF_TAB_TITLE)) {
					final String defaultValue = null;

					ComboUtil.loadRegionCombo(getView().getSchoolStaffPane().getRegionCombo(), dispatcher, placeManager,
							defaultValue);

					getView().getSchoolStaffPane().getRegionCombo().addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {
							ComboUtil.loadDistrictComboByRegion(getView().getSchoolStaffPane().getRegionCombo(),
									getView().getSchoolStaffPane().getDistrictCombo(), dispatcher, placeManager,
									defaultValue);
						}
					});

					getView().getSchoolStaffPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {
							ComboUtil.loadSchoolComboByDistrict(getView().getSchoolStaffPane().getDistrictCombo(),
									getView().getSchoolStaffPane().getSchoolCombo(), dispatcher, placeManager,
									defaultValue);
						}
					});

					getView().getSchoolStaffPane().getSchoolCombo().addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {
						ComboUtil.loadSchoolStaffMultiComboBySchool(getView().getSchoolStaffPane().getSchoolCombo(), getView().getSchoolStaffPane().getSchoolStaffCombo(), dispatcher, placeManager, defaultValue);
						}
					});
					
					sendMessage();

				} else if (selectedTab.equalsIgnoreCase(SmsMessageView.SYSTEM_USER_TAB_TITLE)) {

				} else {
					List<MenuButton> buttons = new ArrayList<>();
//					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}
	
	private void sendMessage() {
		getView().getSchoolStaffPane().getSendButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String[] staffs = getView().getSchoolStaffPane().getSchoolStaffCombo().getValues();
				String message = getView().getSchoolStaffPane().getMessageField().getValueAsString();
			    List<SchoolStaffDTO> schoolStaffDTOs = new ArrayList<SchoolStaffDTO>();
			    
				for (int i = 0; i < staffs.length; i++) {
					SchoolStaffDTO staffDTO = new SchoolStaffDTO(staffs[i]);
					schoolStaffDTOs.add(staffDTO);
				}
				SmsSchoolStaffDTO dto = new SmsSchoolStaffDTO();
				dto.setSchoolStaffDTOS(schoolStaffDTOs);
				dto.setMessage(message);
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(NetworkDataUtil.ACTION, SmsRequest.SMS_STAFF);
				    map.put(SmsRequest.DATA, dto);
				    
				    NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
						
						@Override
						public void onNetworkResult(RequestResult result) {
							SC.say("SMS SSENT");
						}
					});
				    
			   
			}
		});
       
       
		
	}

}