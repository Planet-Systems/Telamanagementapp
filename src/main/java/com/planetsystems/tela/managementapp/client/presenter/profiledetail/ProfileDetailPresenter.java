package com.planetsystems.tela.managementapp.client.presenter.profiledetail;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserProfileWindow;
import com.planetsystems.tela.managementapp.client.widget.Masthead;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemMenuCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserProfileRequestConstant;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
public class ProfileDetailPresenter extends Presenter<ProfileDetailPresenter.MyView, ProfileDetailPresenter.MyProxy>  {
    interface MyView extends View  {

    	public ProfileDetailPane getProfileDetailPane();
    	public Masthead getMastHead();

    }
    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_ProfileDetail = new Type<RevealContentHandler<?>>();
    
	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
    
    @Inject
	private PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;

    @NameToken(NameTokens.ProfileDetail)
    @ProxyCodeSplit
    interface MyProxy extends ProxyPlace<ProfileDetailPresenter> {
    }

    @Inject
    ProfileDetailPresenter(
            EventBus eventBus,
            MyView view, 
            MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);
        
    }
    
    @Override
    protected void onBind() {
    	super.onBind();
    	manageUserProfile(Cookies.getCookie(RequestConstant.USERNAME), "");
    	getSystemUserProfileDetails();
    	enableFields();
    	saveProfile();
    }
    
    
    private void getSystemUserProfileDetails() {
    	LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.GET_SYSTEM_USER_PROFILE);
		
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				
             SystemUserProfileDTO dto = result.getSystemUserProfileDTO();
         	GeneralUserDetailDTO userDetailDTO = dto.getGeneralUserDetailDTO();
        // 	SC.say("Success "+userDetailDTO.getFirstName());
         	getView().getProfileDetailPane().getFirstNameField().setValue(userDetailDTO.getFirstName());
         	getView().getProfileDetailPane().getEmailField().setValue(userDetailDTO.getEmail());
         	getView().getProfileDetailPane().getLastNameField().setValue(userDetailDTO.getLastName());
         	getView().getProfileDetailPane().getPhoneNumberField().setValue(userDetailDTO.getPhoneNumber());
         	getView().getProfileDetailPane().getNationalIdField().setValue(userDetailDTO.getNationalId());
         	getView().getProfileDetailPane().getDobItem().setValue(userDetailDTO.getDob());
         	loadGenderComboBox(userDetailDTO.getGender());
			}
		});
	}

	private void saveProfile() {
    	getView().getProfileDetailPane().getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				disableFields();
				SystemUserProfileDTO dto = new SystemUserProfileDTO();
				GeneralUserDetailDTO userDetailDTO = new GeneralUserDetailDTO();
				userDetailDTO.setFirstName(getView().getProfileDetailPane().getFirstNameField().getValueAsString());
				userDetailDTO.setLastName(getView().getProfileDetailPane().getFirstNameField().getValueAsString());
				userDetailDTO.setGender(getView().getProfileDetailPane().getFirstNameField().getValueAsString());
				userDetailDTO.setPhoneNumber(getView().getProfileDetailPane().getFirstNameField().getValueAsString());
				userDetailDTO.setNationalId(getView().getProfileDetailPane().getFirstNameField().getValueAsString());
				userDetailDTO.setDob(dateFormat.format(getView().getProfileDetailPane().getDobItem().getValueAsDate()));
				
				dto.setGeneralUserDetailDTO(userDetailDTO);
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(SystemUserProfileRequestConstant.DATA, dto);
				map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.UPDATE_LOGGED_SYSTEM_USER_PROFILE_DETAILS);
				
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
					
					@Override
					public void onNetworkResult(RequestResult result) {
                     SC.say(result.getSystemFeedbackDTO().getMessage());
                     disableFields();
                     getSystemUserProfileDetails();
					}
				});
				
			}
		});
		
	}

	public void loadGenderComboBox(String defaultValue) {

		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("female", "Female");
		valueMap.put("male", "Male");
		getView().getProfileDetailPane().getGenderCombo().setValueMap(valueMap);
		if (defaultValue != null) {
			getView().getProfileDetailPane().getGenderCombo().setValue(defaultValue);
		}
	}
    
    
	private void enableFields() {
    	getView().getProfileDetailPane().getEditButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().getProfileDetailPane().getFirstNameField().enable();
		    	getView().getProfileDetailPane().getLastNameField().enable();
//		    	getView().getProfileDetailPane().getEmailField().disable();
		    	getView().getProfileDetailPane().getDobItem().enable();
		    	getView().getProfileDetailPane().getNationalIdField().enable();
		    	getView().getProfileDetailPane().getPhoneNumberField().enable();
		    	getView().getProfileDetailPane().getGenderCombo().enable();
		    	loadGenderComboBox(null);
		    	getView().getProfileDetailPane().getButtonLayout().setMembers(getView().getProfileDetailPane().getSaveButton());
			}
		});
    }
    
    
    private void disableFields() {
    	getView().getProfileDetailPane().getFirstNameField().disable();
    	getView().getProfileDetailPane().getLastNameField().disable();
//    	getView().getProfileDetailPane().getEmailField().disable();
    	getView().getProfileDetailPane().getDobItem().disable();
    	getView().getProfileDetailPane().getNationalIdField().disable();
    	getView().getProfileDetailPane().getPhoneNumberField().disable();
    	getView().getProfileDetailPane().getGenderCombo().disable();
    	
    	getView().getProfileDetailPane().getButtonLayout().setMembers(getView().getProfileDetailPane().getEditButton());
    }
    
    
    
    
    
    private void manageUserProfile(final String userName, final String role) {

		getView().getMastHead().getUserProfile().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

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
				MenuItem item4 = new MenuItem("Dashboard");
				MenuItem item2 = new MenuItem("Change Password");

				MenuItem item3 = new MenuItem("Logout");

				item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						// changePassword();
					}
				});

				item3.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {

						SessionManager.getInstance().logOut(placeManager);

					}
				});
				
				item4.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {

						PlaceRequest placeRequest = new PlaceRequest.Builder()
								.nameToken(NameTokens.dashboard).build();

						placeManager.revealPlace(placeRequest);
					}
				});

				MenuItemSeparator separator = new MenuItemSeparator();
				menu.setItems(item1, item4 , item2, separator, item3);

				menu.showNextTo(getView().getMastHead().getUserProfile(), "bottom");

			}
		});

	}
    
    
}