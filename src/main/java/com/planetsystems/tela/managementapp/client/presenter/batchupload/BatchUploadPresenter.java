package com.planetsystems.tela.managementapp.client.presenter.batchupload;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class BatchUploadPresenter extends Presenter<BatchUploadPresenter.MyView, BatchUploadPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	interface MyView extends View {
		public BatchUploadPane getBatchUploadPane();

		public ControlsPane getControlsPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_BatchUpload = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.batchupload)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<BatchUploadPresenter> {

	}

	@Inject
	BatchUploadPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	@Override
	protected void onBind() {
		super.onBind();

		loadToolbarMenu();
		getSchoolLicenseKeys();
		getLicenseKeys();

	}

	private void loadToolbarMenu() {

		MenuButton importbutton = new MenuButton("Import");
		MenuButton exportbutton = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();

		buttons.add(importbutton);
		buttons.add(exportbutton);

		batchDataUpload(importbutton);

		getView().getControlsPane().addMenuButtons("Batch Data Uploads", buttons);

	}

	private void batchDataUpload(final MenuButton importbutton) {
		final Menu menu = new Menu();
		MenuItem importLicenseKeys = new MenuItem("Upload License Keys");
		MenuItem importSchoolLicenseKeys = new MenuItem("Upload School License Keys");
		menu.setItems(importLicenseKeys, importSchoolLicenseKeys);

		importbutton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				menu.showNextTo(importbutton, "bottom");

			}
		});

		importLicenseKeys.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				fileUploadLicenseKey();
			}
		});

		importSchoolLicenseKeys.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				fileUploadSchoolLicenses();

			}
		});
	}

	private void fileUploadLicenseKey() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_FILE_UPLOAD_LINK, map),
				new AsyncCallback<RequestResult>() {
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.say("ERROR", caught.getMessage());
						SC.clearPrompt();
					}

					public void onSuccess(RequestResult result) {

						SC.clearPrompt();

						if (result != null) {

							if (result.getSystemFeedbackDTO() != null) {

								// SC.say("GET_FILE_UPLOAD_LINK:: "+result.getSystemFeedbackDTO().getMessage());

								FileUploadWindow window = new FileUploadWindow();

								uploadFile(window, result.getSystemFeedbackDTO().getMessage());
								window.show();

							}

						} else {
							SC.say("ERROR", "Unknow error");
						}

					}
				});
	}

	private void uploadFile(final FileUploadWindow window, final String link) {

		window.getUploadButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				final String fileName = window.getUpload().getFilename();

				String fileExtension = UtilityManager.getInstance().getFileExtension(fileName);

				if (fileExtension.equalsIgnoreCase("xlsx")) {

					SC.showPrompt("", "", new SwizimaLoader());

					final StringBuilder url = new StringBuilder();

					url.append(link + "importLicenses").append("?");

					String arg0Name = URL.encodeQueryString("fileName");
					url.append(arg0Name);
					url.append("=");
					String arg0Value = URL.encodeQueryString(fileName);
					url.append(arg0Value);

					window.getUploadForm().setAction(url.toString());
					window.getUploadForm().submit();

				} else {
					SC.warn("ERROR", "Only xlsx files allowed");
				}

				window.getUploadForm().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

					public void onSubmitComplete(SubmitCompleteEvent event) {

						SC.clearPrompt();

						// String serverResponse = event.getResults();

						SC.say("Sucess", "Upload Successful", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									window.close();
								}

							}
						});

						GWT.log("serverResponse::::: " + event.getResults());

					}

				});

			}

		});

	}

	private void fileUploadSchoolLicenses() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_FILE_UPLOAD_LINK, map),
				new AsyncCallback<RequestResult>() {
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.say("ERROR", caught.getMessage());
						SC.clearPrompt();
					}

					public void onSuccess(RequestResult result) {

						SC.clearPrompt();

						if (result != null) {

							if (result.getSystemFeedbackDTO() != null) {

								// SC.say("GET_FILE_UPLOAD_LINK:: "+result.getSystemFeedbackDTO().getMessage());

								FileUploadWindow window = new FileUploadWindow();

								uploadSchoolLicenseFile(window, result.getSystemFeedbackDTO().getMessage());
								window.show();

							}

						} else {
							SC.say("ERROR", "Unknow error");
						}

					}
				});
	}

	private void uploadSchoolLicenseFile(final FileUploadWindow window, final String link) {

		window.getUploadButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				final String fileName = window.getUpload().getFilename();

				String fileExtension = UtilityManager.getInstance().getFileExtension(fileName);

				if (fileExtension.equalsIgnoreCase("xlsx")) {

					SC.showPrompt("", "", new SwizimaLoader());

					final StringBuilder url = new StringBuilder();

					url.append(link + "importLicenses/school").append("?");

					String arg0Name = URL.encodeQueryString("fileName");
					url.append(arg0Name);
					url.append("=");
					String arg0Value = URL.encodeQueryString(fileName);
					url.append(arg0Value);

					window.getUploadForm().setAction(url.toString());
					window.getUploadForm().submit();

				} else {
					SC.warn("ERROR", "Only xlsx files allowed");
				}

				window.getUploadForm().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

					public void onSubmitComplete(SubmitCompleteEvent event) {

						SC.clearPrompt();

						// String serverResponse = event.getResults();

						SC.say("Sucess", "Upload Successful", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									window.close();
								}

							}
						});

						GWT.log("serverResponse::::: " + event.getResults());

					}

				});

			}

		});

	}

	private void getLicenseKeys() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_LICENSE_KEYS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				getView().getBatchUploadPane().getTelaLicenseKeyListgrid()
						.addRecordsToGrid(result.getTelaLicenseKeyDTOs());

			}
		});
	}

	private void getSchoolLicenseKeys() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_LICENSE_KEYS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				getView().getBatchUploadPane().getTelaSchoolLicenseListgrid()
						.addRecordsToGrid(result.getTelaSchoolLicenseDTOs());

			}
		});
	}

}