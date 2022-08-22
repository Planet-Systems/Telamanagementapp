package com.planetsystems.tela.managementapp.client.presenter.emailattachmentdownload;

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
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.PreviewReportWindow;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class EmailAttachmentDownloadPresenter
		extends Presenter<EmailAttachmentDownloadPresenter.MyView, EmailAttachmentDownloadPresenter.MyProxy> {

	@Inject
	private PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;

	interface MyView extends View {

		public EmailAttachmentDownloadPane getEmailAttachmentDownloadPane();

		public ControlsPane getControlsPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_EmailAttachmentDownload = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.emailattachmentdownload)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<EmailAttachmentDownloadPresenter> {
	}

	@Inject
	EmailAttachmentDownloadPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		loadControlsPaneMenuButtons();
	}

	public void loadControlsPaneMenuButtons() {

		MenuButton refresh = new MenuButton("Refresh");
		MenuButton previewButton = new MenuButton("Preview");
		MenuButton downloadButton = new MenuButton("Download");
		MenuButton processDataButton = new MenuButton("Process Data");
		MenuButton filter = new MenuButton("Filter");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(refresh);
		buttons.add(previewButton);
		buttons.add(downloadButton);
		buttons.add(processDataButton);
		buttons.add(filter);

		getView().getControlsPane().addMenuButtons("Email Attachment Downloads", buttons);

		loadEmailDownloadAttachments();
		refresh(refresh);
		preview(previewButton);

	}

	private void refresh(final MenuButton refresh) {
		refresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loadEmailDownloadAttachments();

			}
		});
	}

	private void loadEmailDownloadAttachments() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		map.put(NetworkDataUtil.ACTION, RequestConstant.LOAD_EMAIL_DOWNLOAD_ATTACHMENTS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				getView().getEmailAttachmentDownloadPane().getListGrid()
						.addRecordsToGrid(result.getEmailAttachmentDownloadDTOs());
			}
		});
	}

	private void preview(final MenuButton preview) {
		preview.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String url = getView().getEmailAttachmentDownloadPane().getListGrid().getSelectedRecord()
						.getAttribute(EmailAttachmentDownloadListgrid.Attachments);
				
				//PreviewReportWindow window=new PreviewReportWindow(url, "Preview Report");
				//window.show();
				UtilityManager.getInstance().preview(url, "Preview Report");

			}
		});

	}

}