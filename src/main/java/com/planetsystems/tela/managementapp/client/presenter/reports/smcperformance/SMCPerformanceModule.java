package com.planetsystems.tela.managementapp.client.presenter.reports.smcperformance;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SMCPerformanceModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SMCPerformancePresenter.class, SMCPerformancePresenter.MyView.class, SMCPerformanceView.class, SMCPerformancePresenter.MyProxy.class);
    }
}