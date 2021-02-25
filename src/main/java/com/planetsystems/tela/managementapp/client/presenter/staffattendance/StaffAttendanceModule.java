package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class StaffAttendanceModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(StaffAttendancePresenter.class, StaffAttendancePresenter.MyView.class, StaffAttendanceView.class, StaffAttendancePresenter.MyProxy.class);
    }
}