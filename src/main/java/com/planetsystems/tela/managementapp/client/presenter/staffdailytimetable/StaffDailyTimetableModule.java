package com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class StaffDailyTimetableModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(StaffDailyTimtablePresenter.class, StaffDailyTimtablePresenter.MyView.class, StaffDailyTimetableView.class, StaffDailyTimtablePresenter.MyProxy.class);
    }
}