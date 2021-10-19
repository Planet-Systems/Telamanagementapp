package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SchoolPerformaceReportModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SchoolPerformaceReportPresenter.class, SchoolPerformaceReportPresenter.MyView.class, SchoolPerformaceReportView.class, SchoolPerformaceReportPresenter.MyProxy.class);
    }
}