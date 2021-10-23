package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DailyAttendanceDashoardModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(DailyAttendanceDashoardPresenter.class, DailyAttendanceDashoardPresenter.MyView.class, DailyAttendanceDashoardView.class, DailyAttendanceDashoardPresenter.MyProxy.class);
    }
}