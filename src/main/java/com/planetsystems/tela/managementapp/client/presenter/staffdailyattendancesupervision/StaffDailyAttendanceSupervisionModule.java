package com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class StaffDailyAttendanceSupervisionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(StaffDailyAttendanceSupervisionPresenter.class, StaffDailyAttendanceSupervisionPresenter.MyView.class, StaffDailyAttendanceSupervisionView.class, StaffDailyAttendanceSupervisionPresenter.MyProxy.class);
    }
}