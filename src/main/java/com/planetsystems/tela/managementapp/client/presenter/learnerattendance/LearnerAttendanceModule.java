package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LearnerAttendanceModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(LearnerAttendancePresenter.class, LearnerAttendancePresenter.MyView.class, LearnerAttendanceView.class, LearnerAttendancePresenter.MyProxy.class);
    }
}