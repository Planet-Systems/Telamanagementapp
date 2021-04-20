package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DistrictPerformaceReportModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(DistrictPerformaceReportPresenter.class, DistrictPerformaceReportPresenter.MyView.class, DistrictPerformaceReportView.class, DistrictPerformaceReportPresenter.MyProxy.class);
    }
}