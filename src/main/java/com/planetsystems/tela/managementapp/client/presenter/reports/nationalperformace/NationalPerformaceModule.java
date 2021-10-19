package com.planetsystems.tela.managementapp.client.presenter.reports.nationalperformace;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class NationalPerformaceModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(NationalPerformacePresenter.class, NationalPerformacePresenter.MyView.class, NationalPerformaceView.class, NationalPerformacePresenter.MyProxy.class);
    }
}