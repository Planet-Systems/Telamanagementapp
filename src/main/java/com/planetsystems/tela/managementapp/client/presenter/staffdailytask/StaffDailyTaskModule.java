package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class StaffDailyTaskModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(StaffDailyTaskPresenter.class, StaffDailyTaskPresenter.MyView.class, StaffDailyTaskView.class, StaffDailyTaskPresenter.MyProxy.class);
    }
}