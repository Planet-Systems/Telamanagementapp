package com.planetsystems.tela.managementapp.client.presenter.useraccountrequest;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.UserAccountRequestDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserProfileWindow;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserGroupRequestConstant;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class UserAccountRequestPresenter
		extends Presenter<UserAccountRequestPresenter.MyView, UserAccountRequestPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	interface MyView extends View {

		public SignupPane getSignupPane();

		public Header getHeader();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_UserAccountRequest = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.useraccountrequest)
	@ProxyCodeSplit
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<UserAccountRequestPresenter> {
	}

	@Inject
	UserAccountRequestPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, RevealType.Root);

	}

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	protected void onBind() {
		super.onBind();
		loadRoles();
		loadGenderComboBox();
		submit();
		back();
	}

	private void loadRoles() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserGroupRequestConstant.GET_SYSTEM_USER_GROUPS);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
				for (SystemUserGroupDTO dto : result.getSystemUserGroupDTOs()) {
					hashMap.put(dto.getId(), dto.getName());
				}
				getView().getSignupPane().getSystemUserGroupCombo().setValueMap(hashMap);
			}
		});
	}

	public void loadGenderComboBox() {
		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("female", "Female");
		valueMap.put("male", "Male");

		getView().getSignupPane().getGenderCombo().setValueMap(valueMap);
	}

	private void submit() {
		getView().getSignupPane().getSubmitButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				UserAccountRequestDTO dto = new UserAccountRequestDTO();
				dto.setComment(getView().getSignupPane().getComment().getValueAsString());
				dto.setDob(dateFormat.format(getView().getSignupPane().getDobItem().getValueAsDate()));
				dto.setEmail(getView().getSignupPane().getEmailField().getValueAsString());
				dto.setFirstName(getView().getSignupPane().getFirstNameField().getValueAsString());
				dto.setGender(getView().getSignupPane().getGenderCombo().getValueAsString());
				dto.setLastName(getView().getSignupPane().getLastNameField().getValueAsString());
				dto.setNationalId(getView().getSignupPane().getNationalIdField().getValueAsString());
				dto.setPhoneNumber(getView().getSignupPane().getPhoneNumberField().getValueAsString());

				SystemUserGroupDTO systemUserGroup = new SystemUserGroupDTO();
				systemUserGroup.setId(getView().getSignupPane().getSystemUserGroupCombo().getValueAsString());
				dto.setSystemUserGroup(systemUserGroup);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.USER_ACCOUNT_REQUEST, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.USER_ACCOUNT_REQUEST);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						if (result.getSystemFeedbackDTO() != null) {
							if (result.getSystemFeedbackDTO().isResponse()) {
								SC.say("Sucess", "Your request been submited successfully", new BooleanCallback() {

									@Override
									public void execute(Boolean value) {

										if (value) {
											PlaceRequest placeRequest = new PlaceRequest.Builder()
													.nameToken(NameTokens.login).build();
											placeManager.revealPlace(placeRequest);
										}
									}
								});

							} else {
								SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
							}
						}

					}
				});

			}
		});
	}
	
	private void back() {
		getView().getSignupPane().getBackButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PlaceRequest placeRequest = new PlaceRequest.Builder()
						.nameToken(NameTokens.login).build();
				placeManager.revealPlace(placeRequest);
				
			}
		});
	}

}