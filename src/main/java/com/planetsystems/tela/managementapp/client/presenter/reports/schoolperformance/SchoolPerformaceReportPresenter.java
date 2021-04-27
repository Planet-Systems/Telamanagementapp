package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import org.apache.bcel.generic.NEW;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
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
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.OverallAttendanceImpactDashboardGenerator;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.OverallImpactDashboardGenerator;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.daily.FilterClockInSummaryWindow;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.daily.TeacherClockInSummaryPane;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.monthly.FilterMonthlyAttendanceSummaryWindow;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.monthly.SchoolEndOfMonthTimeAttendancePane;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.termly.SchoolEndOfTermTimeAttendancePane;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.timeontask.SchoolTimeOnTaskSummaryPane;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.weekly.FilterWeeklyAttendanceWindow;
import com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.weekly.SchoolEndOfWeekTimeAttendancePane;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import java_cup.internal_error;

import com.planetsystems.tela.dto.enums.WeekNumber;
import com.planetsystems.tela.dto.enums.monthEnum;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class SchoolPerformaceReportPresenter
		extends Presenter<SchoolPerformaceReportPresenter.MyView, SchoolPerformaceReportPresenter.MyProxy> {

	interface MyView extends View {

		public ControlsPane getControlsPane();

		public VLayout getContentPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SchoolPerformaceReport = new Type<RevealContentHandler<?>>();
	
	@Inject
	PlaceManager placeManager;
	
	@Inject
	private DispatchAsync dispatcher;

	@NameToken(NameTokens.schoolperformance)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SchoolPerformaceReportPresenter> {
	}

	
	TeacherClockInSummaryPane clockInSummaryPane;
	
	@Inject
	SchoolPerformaceReportPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
		clockInSummaryPane = new TeacherClockInSummaryPane();
	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();

		getView().getContentPane().setMembers(clockInSummaryPane);
	}

	private void loadMenuButtons() {
		MenuButton filter = new MenuButton("Filter");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("School Performance", buttons);

		showFilter(filter);

	}

	private void showFilter(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();

				MenuItem item1 = new MenuItem("Teacher Clockin/Clockout Summary");
				MenuItem item2 = new MenuItem("End of Week Time Attendance Reports");
				MenuItem item3 = new MenuItem("End of Month Time Attendance Reports");
				MenuItem item4 = new MenuItem("End of Term Time Attendance Reports");
				MenuItem item5 = new MenuItem("Time On Task Reports");

				menu.setItems(item1, item2, item3, item4, item5);

				menu.showNextTo(button, "bottom");

				item1.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {						
						final FilterClockInSummaryWindow window = new FilterClockInSummaryWindow();
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							 ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(), window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
						window.getRegionCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						window.getSchoolCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							ComboUtil.loadSchoolStaffComboBySchool(window.getSchoolCombo(), window.getSchoolStaffCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						
						
						window.show();
						
//						loadTeacherClockInSummary();

					}
				});

				item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						SchoolEndOfWeekTimeAttendancePane pane = new SchoolEndOfWeekTimeAttendancePane();
						getView().getContentPane().setMembers(pane);
						
						final FilterWeeklyAttendanceWindow window = new FilterWeeklyAttendanceWindow();
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							 ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(), window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
						window.getRegionCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						LinkedHashMap<String, String> monthMap = new LinkedHashMap<>();
						for(int i = 0 ; i < monthEnum.values().length ; i++) {
							String month = monthEnum.values()[i].getMonth();
							monthMap.put(month , month);
						}
						window.getMonthCombo().setValueMap(monthMap);
						
						LinkedHashMap<Integer , String> weekMap = new LinkedHashMap<>();
						for(int i = 0 ; i < WeekNumber.values().length ; i++) {
							weekMap.put(WeekNumber.values()[i].getWeekNumber() , WeekNumber.values()[i].name());
						}
						window.getWeekCombo().setValueMap(weekMap);
						
						
						window.show();
						loadSchoolEndOfWeekTimeAttendance();

					}
				});

				item3.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						SchoolEndOfMonthTimeAttendancePane pane = new SchoolEndOfMonthTimeAttendancePane();
						getView().getContentPane().setMembers(pane);
						
						final FilterMonthlyAttendanceSummaryWindow window = new FilterMonthlyAttendanceSummaryWindow();
						final String defaultValue = null;
						ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
						window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							 ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(), window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
						window.getRegionCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						window.getDistrictCombo().addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
							ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher, placeManager, defaultValue);	
							}
						});
						
						LinkedHashMap<String, String> monthMap = new LinkedHashMap<>();
						for(int i = 0 ; i < monthEnum.values().length ; i++) {
							String month = monthEnum.values()[i].getMonth();
							monthMap.put(month , month);
						}
						window.getMonthCombo().setValueMap(monthMap);
						
						window.show();
						loadSchoolEndOfMonthTimeAttendance();

					}
				});

				item4.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						SchoolEndOfTermTimeAttendance();

					}
				});
				
				item5.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						
						loadSchoolTimeOnTaskSummary();
						
					}
				});

			}
		});
	}

	private void loadTeacherClockInSummary() {
		

	}

	@Deprecated
	private void loadSchoolEndOfWeekTimeAttendance() {
		
	}

	@Deprecated
	private void loadSchoolEndOfMonthTimeAttendance() {
	

	}

	private void SchoolEndOfTermTimeAttendance() {
		SchoolEndOfTermTimeAttendancePane pane = new SchoolEndOfTermTimeAttendancePane();
		getView().getContentPane().setMembers(pane);
	}
	
	private void loadSchoolTimeOnTaskSummary() {
		SchoolTimeOnTaskSummaryPane pane=new SchoolTimeOnTaskSummaryPane();
		getView().getContentPane().setMembers(pane);
	}

}