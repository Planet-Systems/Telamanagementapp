package com.planetsystems.tela.managementapp.client.presenter.datauploadstat;

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
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.planetsystems.tela.dto.exports.SchoolStaffExportDTO;
import com.planetsystems.tela.dto.reports.DataUploadStatDTO;
import com.planetsystems.tela.dto.reports.DataUploadStatSummaryDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class DataUploadStatPresenter
		extends Presenter<DataUploadStatPresenter.MyView, DataUploadStatPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	interface MyView extends View {

		public DataUploadStatPane getDataUploadStatPane();

		public ControlsPane getControlsPane();

		public DataUploadSummaryStatPane getDataUploadSummaryStatPane();

		public TabSet getTabSet();

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_DataUploadStat = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.datauploadstat)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<DataUploadStatPresenter> {
	}

	@Inject
	DataUploadStatPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		onTabSelected();
		getSummarisedStatistics();

	}

	List<DataUploadStatDTO> dataUploadStatDTOs = new ArrayList<>();
	List<DataUploadStatSummaryDTO> dataUploadStatSummaryDTOs = new ArrayList<>();

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(DataUploadStatView.SUMMARY)) {

					MenuButton exportbutton = new MenuButton("Export");
					MenuButton refreshbutton = new MenuButton("Refresh");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();

					buttons.add(refreshbutton);
					buttons.add(exportbutton);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons("Data Upload Summary Statistics", buttons);

					refreshbutton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {

							getSummarisedStatistics();

						}
					});

					filterSummarySelectOption(filter);
					onExportDataUploadStatSummary(exportbutton);

				} else if (selectedTab.equalsIgnoreCase(DataUploadStatView.DETAILED)) {

					MenuButton exportbutton = new MenuButton("Export");
					MenuButton refreshbutton = new MenuButton("Refresh");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();

					buttons.add(refreshbutton);
					buttons.add(exportbutton);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons("Data Upload Detailed Statistics", buttons);

					refreshbutton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {

							getDetailedStatistics();

						}
					});

					filterDetailedSelectOption(filter);

					onExportataUploadStat(exportbutton);

					// getDetailedStatistics();

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}

	private void filterDetailedSelectOption(final MenuButton filter) {
		final Menu menu = new Menu();
		MenuItem basic = new MenuItem("Basic Filter");

		menu.setItems(basic);

		filter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				menu.showNextTo(filter, "bottom");
			}
		});

		basic.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getView().getDataUploadStatPane().getListgrid().setShowFilterEditor(true);
			}
		});

	}

	private void filterSummarySelectOption(final MenuButton filter) {
		final Menu menu = new Menu();
		MenuItem basic = new MenuItem("Basic Filter");

		menu.setItems(basic);

		filter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				menu.showNextTo(filter, "bottom");
			}
		});

		basic.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getView().getDataUploadSummaryStatPane().getListgrid().setShowFilterEditor(true);
			}
		});

	}

	private void getDetailedStatistics() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_Data_upload_stat);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				dataUploadStatDTOs = result.getDataUploadStatDTOs();
				getView().getDataUploadStatPane().getListgrid().addRecordsToGrid(result.getDataUploadStatDTOs());

			}
		});
	}

	private void getSummarisedStatistics() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_Data_upload_summary_stat);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				dataUploadStatSummaryDTOs = result.getDataUploadStatSummaryDTOs();
				getView().getDataUploadSummaryStatPane().getListgrid()
						.addRecordsToGrid(result.getDataUploadStatSummaryDTOs());

			}
		});
	}

	private void onExportataUploadStat(final MenuButton exportbutton) {
		exportbutton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (dataUploadStatDTOs != null && !dataUploadStatDTOs.isEmpty()) {
					exportataUploadStat(dataUploadStatDTOs);
				} else {
					SC.warn("ERROR", "There is no data to export");
				}

			}
		});
	}

	private void onExportDataUploadStatSummary(final MenuButton exportbutton) {
		exportbutton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (dataUploadStatSummaryDTOs != null && !dataUploadStatSummaryDTOs.isEmpty()) {
					exportDataUploadStatSummary(dataUploadStatSummaryDTOs);
				} else {
					SC.warn("ERROR", "There is no data to export");
				}

			}
		});
	}

	private void exportataUploadStat(List<DataUploadStatDTO> dtos) {

		List<DataUploadStatDTO> list = new ArrayList<DataUploadStatDTO>();

		for (DataUploadStatDTO dto : dtos) {

			list.add(dto);

		}

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, RequestConstant.EXCEL_EXPORT_Data_upload_stat);
		map.put(RequestConstant.EXCEL_EXPORT_Data_upload_stat, list);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				UtilityManager.getInstance().download(result.getSystemFeedbackDTO().getMessage());
			}
		});
	}

	private void exportDataUploadStatSummary(List<DataUploadStatSummaryDTO> dtos) {

		List<DataUploadStatSummaryDTO> list = new ArrayList<DataUploadStatSummaryDTO>();

		for (DataUploadStatSummaryDTO dto : dtos) {

			list.add(dto);

		}

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, RequestConstant.EXCEL_Data_upload_summary_stat);
		map.put(RequestConstant.EXCEL_Data_upload_summary_stat, list);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				UtilityManager.getInstance().download(result.getSystemFeedbackDTO().getMessage());
			}
		});
	}

}