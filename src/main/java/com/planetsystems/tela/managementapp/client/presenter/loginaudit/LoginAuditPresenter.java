package com.planetsystems.tela.managementapp.client.presenter.loginaudit;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserProfileRequestConstant;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.planetsystems.tela.dto.DateFilterDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class LoginAuditPresenter extends Presenter<LoginAuditPresenter.MyView, LoginAuditPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");

	interface MyView extends View {

		public ControlsPane getControlsPane();

		public LoginAuditPane getLoginAuditPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_LoginAudit = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.loginaudits)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<LoginAuditPresenter> {
	}

	@Inject
	LoginAuditPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();

	}

	private void loadMenuButtons() {

		MenuButton refresh = new MenuButton("Refresh");
		MenuButton run = new MenuButton("Run");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(refresh);
		buttons.add(run);

		getView().getControlsPane().addMenuButtons("User Login audits", buttons);
		showFilter(run);

		String fromDate = dateFormat.format(new Date());
		String toDate = dateFormat.format(new Date());

		loadLogs(fromDate, toDate);

	}

	private void showFilter(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();
				MenuItem item = new MenuItem("Load Login Trail");

				menu.setItems(item);

				menu.showNextTo(button, "bottom");

				item.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						LoginTrailWindow window = new LoginTrailWindow();
						loadLogigsByDate(window);
						window.show();

					}
				});

			}
		});
	}

	private void loadLogigsByDate(final LoginTrailWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String fromDate = dateFormat.format(window.getFromDate().getValueAsDate());
				String toDate = dateFormat.format(window.getToDate().getValueAsDate());

				loadLogs(fromDate, toDate);

			}
		});
	}

	private void loadLogs(final String fromDate, final String toDate) {

		DateFilterDTO dto = new DateFilterDTO();
		dto.setFromDate(fromDate);
		dto.setToDate(toDate);

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_LOGIN_TRAIL_BY_DATE);
		map.put(RequestConstant.GET_LOGIN_TRAIL_BY_DATE, dto);

	
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getLoginAuditPane().getListgrid().addRecordsToGrid(result.getLoginAuditDTOs());
			}
		});
	}

}