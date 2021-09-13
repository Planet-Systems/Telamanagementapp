package com.planetsystems.tela.managementapp.client.presenter.reports.headteacherperformance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.timeontask.FilterSchoolTimeOnTaskSummaryWindow;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.timeontask.SchoolTimeOnTaskSummaryPane;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.planetsystems.tela.managementapp.shared.requestconstants.ReportsRequestConstant;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.enums.WeekNumber;
import com.planetsystems.tela.dto.enums.monthEnum;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class HeadTeacherPerformancePresenter
		extends Presenter<HeadTeacherPerformancePresenter.MyView, HeadTeacherPerformancePresenter.MyProxy> {

	@Inject
	PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	HeadTeacherClockInSummaryPane clockInSummaryPane;

	interface MyView extends View {

		public ControlsPane getControlsPane();

		public VLayout getContentPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_HeadTeacherPerformance = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.headteacherperformance)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<HeadTeacherPerformancePresenter> {

	}

	@Inject
	HeadTeacherPerformancePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
		clockInSummaryPane = new HeadTeacherClockInSummaryPane();

	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();

		getView().getContentPane().setMembers(clockInSummaryPane);
	}

	final FilterDTO teacherClockInSummaryDTO = new FilterDTO();

	final FilterDTO schoolEndOfWeekDTO = new FilterDTO();

	final FilterDTO schoolEndOfMonthDTO = new FilterDTO();

	final FilterDTO schoolEndOfTermDTO = new FilterDTO();

	final FilterDTO schoolTimeOntaskDTO = new FilterDTO();
	
	final FilterDTO headTeacherSupervisionDTO = new FilterDTO();

	private void loadMenuButtons() {
		MenuButton filter = new MenuButton("View");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("HeadTeacher Performance", buttons);

		showFilter(filter);

	}

	private void showFilter(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();

				MenuItem dashboard = new MenuItem("Dashboard");
				MenuItem supervision = new MenuItem("HeadTeacher Supervision Summary");
				MenuItem item1 = new MenuItem("HeadTeacher Clockin/Clockout Summary");
				MenuItem item2 = new MenuItem("End of Week Time Attendance Report");
				MenuItem item3 = new MenuItem("End of Month Time Attendance Report");
				MenuItem item4 = new MenuItem("End of Term Time Attendance Report");
				MenuItem item5 = new MenuItem("Time On Task Report");

				menu.setItems(dashboard, supervision,item1, item2, item3, item4);

				menu.showNextTo(button, "bottom");
				
				
				supervision.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() { 
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						
						HeadTeacherSupervisionReportPane pane=new HeadTeacherSupervisionReportPane();
						
						getView().getContentPane().setMembers(pane);

						MenuButton filter = new MenuButton("View");
						MenuButton refresh = new MenuButton("Refresh");
						MenuButton export = new MenuButton("Export");

						List<MenuButton> buttons = new ArrayList<>();
						buttons.add(filter);
						buttons.add(refresh);
						buttons.add(export);

						getView().getControlsPane().addMenuButtons("HeadTeacher Supervision Summary", buttons);
						showFilter(filter);
 
						exportHeadTeachSupervisionReport(export);

						final FilterSupervisionSummaryWindow window = new FilterSupervisionSummaryWindow();
						 
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager,
								defaultValue);
						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
										window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
							}
						});

						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
						window.getRegionCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						  
						window.show();
						getHeadTeacherServisionSummary(window,pane);
					
						
					}
				});

				item1.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						getView().getContentPane().setMembers(clockInSummaryPane);

						MenuButton filter = new MenuButton("View");
						MenuButton refresh = new MenuButton("Refresh");
						MenuButton export = new MenuButton("Export");

						List<MenuButton> buttons = new ArrayList<>();
						buttons.add(filter);
						buttons.add(refresh);
						buttons.add(export);

						getView().getControlsPane().addMenuButtons("HeadTeacher Clockin/Clockout Summary", buttons);
						showFilter(filter);

						exportHeadTeacherClockSummary(export);

						final FilterClockInSummaryWindow window = new FilterClockInSummaryWindow();
						window.getSchoolStaffCombo().setTitle("HeadTeacher"); 
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager,
								defaultValue);
						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
										window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
							}
						});

						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
						window.getRegionCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getSchoolCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadHeadteacherComboBySchool(window.getSchoolCombo(),
										window.getSchoolStaffCombo(), dispatcher, placeManager, defaultValue);
							}
						});

						window.show();
						getTeacherClockInSummary(window);
					}
				});

				item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						MenuButton filter = new MenuButton("View");
						MenuButton refresh = new MenuButton("Refresh");
						MenuButton export = new MenuButton("Export");

						List<MenuButton> buttons = new ArrayList<>();
						buttons.add(filter);
						buttons.add(refresh);
						buttons.add(export);

						getView().getControlsPane().addMenuButtons("End of Week Time Attendance Reports", buttons);
						showFilter(filter);

						exportSchoolEndOfWeek(export);

						HTEndOfWeekTimeAttendancePane pane = new HTEndOfWeekTimeAttendancePane();

						getView().getContentPane().setMembers(pane);

						final FilterWeeklyAttendanceWindow window = new FilterWeeklyAttendanceWindow();
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager,
								defaultValue);
						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
										window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
							}
						});

						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
						window.getRegionCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						LinkedHashMap<String, String> monthMap = new LinkedHashMap<>();
						for (int i = 0; i < monthEnum.values().length; i++) {
							String month = monthEnum.values()[i].getMonth();
							monthMap.put(month, month);
						}
						window.getMonthCombo().setValueMap(monthMap);

						LinkedHashMap<Integer, String> weekMap = new LinkedHashMap<>();
						for (int i = 0; i < WeekNumber.values().length; i++) {
							weekMap.put(WeekNumber.values()[i].getWeekNumber(), WeekNumber.values()[i].name());
						}
						window.getWeekCombo().setValueMap(weekMap);

						window.show();

						loadSchoolEndOfWeekTimeAttendance(window, pane);

					}
				});

				item3.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						MenuButton filter = new MenuButton("View");
						MenuButton refresh = new MenuButton("Refresh");
						MenuButton export = new MenuButton("Export");

						List<MenuButton> buttons = new ArrayList<>();
						buttons.add(filter);
						buttons.add(refresh);
						buttons.add(export);

						getView().getControlsPane().addMenuButtons("End of Month Time Attendance Report", buttons);
						showFilter(filter);

						exportSchoolEndOfMonth(export);

						HTEndOfMonthTimeAttendancePane pane = new HTEndOfMonthTimeAttendancePane();
						getView().getContentPane().setMembers(pane);

						final FilterMonthlyAttendanceSummaryWindow window = new FilterMonthlyAttendanceSummaryWindow();
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager,
								defaultValue);

						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {

								ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
										window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
							}
						});

						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
						window.getRegionCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						LinkedHashMap<String, String> monthMap = new LinkedHashMap<>();
						for (int i = 0; i < monthEnum.values().length; i++) {
							String month = monthEnum.values()[i].getMonth();
							monthMap.put(month, month);
						}
						window.getMonthCombo().setValueMap(monthMap);

						window.show();

						loadSchoolEndOfMonthTimeAttendance(window, pane);

					}
				});

				item4.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final HTEndOfTermTimeAttendancePane pane = new HTEndOfTermTimeAttendancePane();

						MenuButton filter = new MenuButton("View");
						MenuButton refresh = new MenuButton("Refresh");
						MenuButton export = new MenuButton("Export");

						List<MenuButton> buttons = new ArrayList<>();
						buttons.add(filter);
						buttons.add(refresh);
						buttons.add(export);

						getView().getControlsPane().addMenuButtons("HeadTeacher End of Term Time Attendance Report",
								buttons);

						showFilter(filter);

						exportSchoolEndOfTerm(export);

						getView().getContentPane().setMembers(pane);

						final FilterTermlyAttendanceSummaryWindow window = new FilterTermlyAttendanceSummaryWindow();
						window.show();
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager,
								defaultValue);

						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
										window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
							}
						});

						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);

						window.getRegionCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getSaveButton().addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								// FilterDTO dto = new FilterDTO();

								AcademicYearDTO academicYearDTO = new AcademicYearDTO(
										window.getAcademicYearCombo().getValueAsString());

								AcademicTermDTO academicTermDTO = new AcademicTermDTO(
										window.getAcademicTermCombo().getValueAsString());

								SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());

								schoolEndOfTermDTO.setAcademicTermDTO(academicTermDTO);
								schoolEndOfTermDTO.setSchoolDTO(schoolDTO);
								schoolEndOfTermDTO.setAcademicYearDTO(academicYearDTO);

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(NetworkDataUtil.ACTION,
										ReportsRequestConstant.HeadTeacherEndOfTermTimeAttendanceReport);
								map.put(ReportsRequestConstant.DATA, schoolEndOfTermDTO);

								NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

									@Override
									public void onNetworkResult(RequestResult result) {

										pane.getListgrid()
												.addRecordsToGrid(result.getSchoolEndOfTermTimeAttendanceDTOs());
									}
								});

							}
						});
					}
				});

				item5.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final SchoolTimeOnTaskSummaryPane pane = new SchoolTimeOnTaskSummaryPane();

						MenuButton filter = new MenuButton("View");
						MenuButton refresh = new MenuButton("Refresh");
						MenuButton export = new MenuButton("Export");

						List<MenuButton> buttons = new ArrayList<>();
						buttons.add(filter);
						buttons.add(refresh);
						buttons.add(export);

						getView().getControlsPane().addMenuButtons("HeadTeacher Time On Task Reports", buttons);

						showFilter(filter);

						exportTeachTimeOntaskReport(export);

						getView().getContentPane().setMembers(pane);

						final FilterSchoolTimeOnTaskSummaryWindow window = new FilterSchoolTimeOnTaskSummaryWindow();
						window.show();
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager,
								defaultValue);

						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
										window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
								;
							}
						});

						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);

						window.getRegionCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(),
										dispatcher, placeManager, defaultValue);
							}
						});

						window.getSchoolCombo().addChangedHandler(new ChangedHandler() {

							@Override
							public void onChanged(ChangedEvent event) {
								ComboUtil.loadSchoolStaffComboBySchool(window.getSchoolCombo(),
										window.getSchoolStaffCombo(), dispatcher, placeManager, defaultValue);
								;
							}
						});

						window.getSaveButton().addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								if (checkIfAllFieldsNotEmpty(window)) {
									// FilterDTO dto = new FilterDTO();
									schoolTimeOntaskDTO.setAcademicTermDTO(
											new AcademicTermDTO(window.getAcademicTermCombo().getValueAsString()));
									schoolTimeOntaskDTO
											.setSchoolDTO(new SchoolDTO(window.getSchoolCombo().getValueAsString()));
									schoolTimeOntaskDTO.setSchoolStaffDTO(
											new SchoolStaffDTO(window.getSchoolStaffCombo().getValueAsString()));
									schoolTimeOntaskDTO
											.setToDate(dateFormat.format(window.getToDateItem().getValueAsDate()));
									schoolTimeOntaskDTO
											.setFromDate(dateFormat.format(window.getFromDateItem().getValueAsDate()));

									LinkedHashMap<String, Object> map = new LinkedHashMap<>();
									map.put(NetworkDataUtil.ACTION, ReportsRequestConstant.SchoolTimeOnTaskSummary);
									map.put(ReportsRequestConstant.DATA, schoolTimeOntaskDTO);

									NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

										@Override
										public void onNetworkResult(RequestResult result) {
											pane.getListgrid()
													.addRecordsToGrid(result.getSchoolTimeOnTaskSummaryDTOs());
										}
									});

								} else {
									SC.say("fill the fields");
								}
							}

						});

					}
				});

			}
		});
	}
	
	
	private void getTeacherClockInSummary(final FilterClockInSummaryWindow window) {
		window.getFetchButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfAllFieldsNotEmpty(window)) {

					SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(window.getSchoolStaffCombo().getValueAsString());
					SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());
					AcademicTermDTO academicTermDTO = new AcademicTermDTO(
							window.getAcademicTermCombo().getValueAsString());
					String fromDate = dateFormat.format(window.getFromDateItem().getValueAsDate());
					String toDate = dateFormat.format(window.getToDateItem().getValueAsDate());

					teacherClockInSummaryDTO.setAcademicTermDTO(academicTermDTO);
					teacherClockInSummaryDTO.setSchoolStaffDTO(schoolStaffDTO);
					teacherClockInSummaryDTO.setSchoolDTO(schoolDTO);
					teacherClockInSummaryDTO.setFromDate(fromDate);
					teacherClockInSummaryDTO.setToDate(toDate);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(NetworkDataUtil.ACTION, ReportsRequestConstant.TeacherClockInSummaryREPORT);
					map.put(ReportsRequestConstant.DATA, teacherClockInSummaryDTO);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {

							clockInSummaryPane.getListgrid().addRecordsToGrid(result.getTeacherClockInSummaryDTOs());

						}
					});

				} else {
					SC.say("Fill the fields");
				}

			}

			@Deprecated
			private void loadTeacherClockInSummary() {
				HeadTeacherClockInSummaryPane pane = new HeadTeacherClockInSummaryPane();

				MenuButton filter = new MenuButton("View");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("HeadTeacher Clockin/Clockout Summary", buttons);

				showFilter(filter);

				getView().getContentPane().setMembers(pane);

			}

			@Deprecated
			private void loadSchoolEndOfWeekTimeAttendance() {
				HTEndOfWeekTimeAttendancePane pane = new HTEndOfWeekTimeAttendancePane();

				MenuButton filter = new MenuButton("View");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("HeadTeacher End of Week Time Attendance Report", buttons);

				showFilter(filter);
				getView().getContentPane().setMembers(pane);

				exportSchoolEndOfWeek(export);

			}

			@Deprecated
			private void loadSchoolEndOfMonthTimeAttendance() {

				HTEndOfMonthTimeAttendancePane pane = new HTEndOfMonthTimeAttendancePane();

				MenuButton filter = new MenuButton("View");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("HeadTeacher End of Month Time Attendance Report", buttons);

				showFilter(filter);

				getView().getContentPane().setMembers(pane);

			}
		});
	}

	 

	private void getHeadTeacherServisionSummary(final FilterSupervisionSummaryWindow window1,final HeadTeacherSupervisionReportPane pane) {
		window1.getFetchButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfAllFieldsNotEmpty(window1)) {
 
					SchoolDTO schoolDTO = new SchoolDTO(window1.getSchoolCombo().getValueAsString());
					AcademicTermDTO academicTermDTO = new AcademicTermDTO(
							window1.getAcademicTermCombo().getValueAsString());
					String fromDate = dateFormat.format(window1.getFromDateItem().getValueAsDate());
					 

					headTeacherSupervisionDTO.setAcademicTermDTO(academicTermDTO);
					 
					headTeacherSupervisionDTO.setSchoolDTO(schoolDTO);
					headTeacherSupervisionDTO.setFromDate(fromDate); 

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(NetworkDataUtil.ACTION, ReportsRequestConstant.HeadTeacherSupervisionReport);
					map.put(ReportsRequestConstant.DATA, headTeacherSupervisionDTO);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {

							pane.getListgrid().addRecordsToGrid(result.getHeadTeacherSupervisionReportDTO().getMrow_inputs());

						}
					});

				} else {
					SC.say("Fill the fields");
				}

			}

			@Deprecated
			private void loadTeacherClockInSummary() {
				HeadTeacherClockInSummaryPane pane = new HeadTeacherClockInSummaryPane();

				MenuButton filter = new MenuButton("View");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("HeadTeacher Clockin/Clockout Summary", buttons);

				showFilter(filter);

				getView().getContentPane().setMembers(pane);

			}

			@Deprecated
			private void loadSchoolEndOfWeekTimeAttendance() {
				HTEndOfWeekTimeAttendancePane pane = new HTEndOfWeekTimeAttendancePane();

				MenuButton filter = new MenuButton("View");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("HeadTeacher End of Week Time Attendance Report", buttons);

				showFilter(filter);
				getView().getContentPane().setMembers(pane);

				exportSchoolEndOfWeek(export);

			}

			@Deprecated
			private void loadSchoolEndOfMonthTimeAttendance() {

				HTEndOfMonthTimeAttendancePane pane = new HTEndOfMonthTimeAttendancePane();

				MenuButton filter = new MenuButton("View");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("HeadTeacher End of Month Time Attendance Report", buttons);

				showFilter(filter);

				getView().getContentPane().setMembers(pane);

			}
		});
	}
	
	
	
	private boolean checkIfAllFieldsNotEmpty(FilterSupervisionSummaryWindow window) {
		boolean status = true;
		if (window.getAcademicYearCombo().getValueAsString() == null)
			status = false;
		if (window.getAcademicTermCombo().getValueAsString() == null)
			status = false;
		if (window.getRegionCombo().getValueAsString() == null)
			status = false;
		if (window.getDistrictCombo().getValueAsString() == null)
			status = false;
		if (window.getSchoolCombo().getValueAsString() == null)
			status = false; 
		if (window.getFromDateItem().getValueAsDate() == null)
			status = false;
		  
		return status;
	}

	private boolean checkIfAllFieldsNotEmpty(FilterClockInSummaryWindow window) {
		boolean status = true;
		if (window.getAcademicYearCombo().getValueAsString() == null)
			status = false;
		if (window.getAcademicTermCombo().getValueAsString() == null)
			status = false;
		if (window.getRegionCombo().getValueAsString() == null)
			status = false;
		if (window.getDistrictCombo().getValueAsString() == null)
			status = false;
		if (window.getSchoolCombo().getValueAsString() == null)
			status = false;
		if (window.getSchoolStaffCombo().getValueAsString() == null)
			status = false;
		if (window.getFromDateItem().getValueAsDate() == null)
			status = false;
		if (window.getToDateItem().getValueAsDate() == null)
			status = false;

		return status;
	}

	private boolean checkIfAllFieldsNotEmpty(FilterSchoolTimeOnTaskSummaryWindow window) {
		boolean status = true;
		if (window.getAcademicYearCombo().getValueAsString() == null)
			status = false;
		if (window.getAcademicTermCombo().getValueAsString() == null)
			status = false;
		if (window.getRegionCombo().getValueAsString() == null)
			status = false;
		if (window.getDistrictCombo().getValueAsString() == null)
			status = false;
		if (window.getSchoolCombo().getValueAsString() == null)
			status = false;
		if (window.getSchoolStaffCombo().getValueAsString() == null)
			status = false;
		if (window.getFromDateItem().getValueAsDate() == null)
			status = false;
		if (window.getToDateItem().getValueAsDate() == null)
			status = false;

		return status;

	}

	private boolean checkIfAllFieldsNotEmpty(FilterWeeklyAttendanceWindow window) {
		boolean status = true;
		if (window.getAcademicYearCombo().getValueAsString() == null)
			status = false;
		if (window.getAcademicTermCombo().getValueAsString() == null)
			status = false;
		if (window.getRegionCombo().getValueAsString() == null)
			status = false;
		if (window.getDistrictCombo().getValueAsString() == null)
			status = false;
		if (window.getSchoolCombo().getValueAsString() == null)
			status = false;
		if (window.getMonthCombo().getValueAsString() == null)
			status = false;
		if (window.getWeekCombo().getValueAsString() == null)
			status = false;

		return status;
	}

	private boolean checkIfAllFieldsNotEmpty(FilterMonthlyAttendanceSummaryWindow window) {
		boolean status = true;
		if (window.getAcademicYearCombo().getValueAsString() == null)
			status = false;
		// if(window.getAcademicTermCombo().getValueAsString() == null) status = false;
		if (window.getRegionCombo().getValueAsString() == null)
			status = false;
		if (window.getDistrictCombo().getValueAsString() == null)
			status = false;
		if (window.getSchoolCombo().getValueAsString() == null)
			status = false;
		if (window.getMonthCombo().getValueAsString() == null)
			status = false;

		return status;
	}

	private void loadSchoolEndOfWeekTimeAttendance(final FilterWeeklyAttendanceWindow window,
			final HTEndOfWeekTimeAttendancePane pane) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfAllFieldsNotEmpty(window)) {

					SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());

					AcademicTermDTO academicTermDTO = new AcademicTermDTO(
							window.getAcademicTermCombo().getValueAsString());

					AcademicYearDTO academicYearDTO = new AcademicYearDTO(
							window.getAcademicYearCombo().getValueAsString());
					String month = window.getMonthCombo().getValueAsString();
					String week = window.getWeekCombo().getValueAsString();

					// FilterDTO dto = new FilterDTO();

					schoolEndOfWeekDTO.setAcademicYearDTO(academicYearDTO);
					schoolEndOfWeekDTO.setAcademicTermDTO(academicTermDTO);
					schoolEndOfWeekDTO.setSchoolDTO(schoolDTO);
					schoolEndOfWeekDTO.setMonth(month);
					schoolEndOfWeekDTO.setWeek(week);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(NetworkDataUtil.ACTION, ReportsRequestConstant.HeadTeacherEndOfWeekTimeAttendanceReport);
					map.put(ReportsRequestConstant.DATA, schoolEndOfWeekDTO);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							pane.getListgrid().addRecordsToGrid(result.getSchoolEndOfWeekTimeAttendanceDTOs());
						}
					});
				} else {
					SC.say("fill all fields");
				}

			}
		});

	}

	private void loadSchoolEndOfMonthTimeAttendance(final FilterMonthlyAttendanceSummaryWindow window,
			final HTEndOfMonthTimeAttendancePane pane) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (checkIfAllFieldsNotEmpty(window)) {

					SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());

					AcademicTermDTO academicTermDTO = new AcademicTermDTO(
							window.getAcademicTermCombo().getValueAsString());

					AcademicYearDTO academicYearDTO = new AcademicYearDTO(
							window.getAcademicYearCombo().getValueAsString());

					String month = window.getMonthCombo().getValueAsString();

					// FilterDTO dto = new FilterDTO();
					schoolEndOfMonthDTO.setAcademicYearDTO(academicYearDTO);
					schoolEndOfMonthDTO.setSchoolDTO(schoolDTO);
					schoolEndOfMonthDTO.setMonth(month);
					schoolEndOfMonthDTO.setAcademicTermDTO(academicTermDTO);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(NetworkDataUtil.ACTION, ReportsRequestConstant.HeadTeacherEndOfMonthTimeAttendanceREPORT);
					map.put(ReportsRequestConstant.DATA, schoolEndOfMonthDTO);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							// SC.say("SIZE "+result.getSchoolEndOfMonthTimeAttendanceDTOs().size());
							// GWT.log("MOnthly "+result.getSchoolEndOfMonthTimeAttendanceDTOs());
							pane.getListgrid().addRecordsToGrid(result.getSchoolEndOfMonthTimeAttendanceDTOs());
						}
					});
				} else {
					SC.say("fill all fields");
				}

			}
		});

	}

	private void exportHeadTeacherClockSummary(final MenuButton export) {
		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.HeadTeacherTimeAttendanceReportExport, teacherClockInSummaryDTO);

				map.put(NetworkDataUtil.ACTION, RequestConstant.HeadTeacherTimeAttendanceReportExport);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						UtilityManager.getInstance().preview(result.getSystemFeedbackDTO().getMessage(),
								"Preview Report");

					}
				});

			}
		});
	}

	private void exportSchoolEndOfWeek(final MenuButton export) {
		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.HeadTeacherEndOfWeekTimeAttendanceReportExport, schoolEndOfWeekDTO);

				map.put(NetworkDataUtil.ACTION, RequestConstant.HeadTeacherEndOfWeekTimeAttendanceReportExport);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						UtilityManager.getInstance().preview(result.getSystemFeedbackDTO().getMessage(),
								"Preview Report");

					}
				});

			}
		});
	}

	private void exportSchoolEndOfMonth(final MenuButton export) {
		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.HeadTeacherEndOfMonthTimeAttendanceReportExport, schoolEndOfMonthDTO);

				map.put(NetworkDataUtil.ACTION, RequestConstant.HeadTeacherEndOfMonthTimeAttendanceReportExport);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						UtilityManager.getInstance().preview(result.getSystemFeedbackDTO().getMessage(),
								"Preview Report");

					}
				});

			}
		});
	}

	private void exportSchoolEndOfTerm(final MenuButton export) {
		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.HeadTeacherEndOfTermTimeAttendanceReportExport, schoolEndOfTermDTO);

				map.put(NetworkDataUtil.ACTION, RequestConstant.HeadTeacherEndOfTermTimeAttendanceReportExport);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						UtilityManager.getInstance().preview(result.getSystemFeedbackDTO().getMessage(),
								"Preview Report");

					}
				});

			}
		});
	}

	private void exportTeachTimeOntaskReport(final MenuButton export) {
		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.TimeOnTaskReportExport, schoolTimeOntaskDTO);

				map.put(NetworkDataUtil.ACTION, RequestConstant.TimeOnTaskReportExport);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						UtilityManager.getInstance().preview(result.getSystemFeedbackDTO().getMessage(),
								"Preview Report");

					}
				});

			}
		});
	}
	
	
	private void exportHeadTeachSupervisionReport(final MenuButton export) {
		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.HeadTeacherSupervidsionReportExport, headTeacherSupervisionDTO);

				map.put(NetworkDataUtil.ACTION, RequestConstant.HeadTeacherSupervidsionReportExport);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						UtilityManager.getInstance().preview(result.getSystemFeedbackDTO().getMessage(),
								"Preview Report");

					}
				});

			}
		});
	}

}