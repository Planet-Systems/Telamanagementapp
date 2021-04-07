package com.planetsystems.tela.managementapp.client.presenter.timeattendancesupervision;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class TimeAttendanceSupervisionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(TimeAttendanceSupervisionPresenter.class, TimeAttendanceSupervisionPresenter.MyView.class, TimeAttendanceSupervisionView.class, TimeAttendanceSupervisionPresenter.MyProxy.class);
    }
}