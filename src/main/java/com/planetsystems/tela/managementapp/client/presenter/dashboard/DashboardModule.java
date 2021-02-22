package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DashboardModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(DashboardPresenter.class, DashboardPresenter.MyView.class, DashboardView.class, DashboardPresenter.MyProxy.class);
    }
}