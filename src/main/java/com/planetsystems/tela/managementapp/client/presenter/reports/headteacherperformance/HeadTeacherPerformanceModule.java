package com.planetsystems.tela.managementapp.client.presenter.reports.headteacherperformance;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class HeadTeacherPerformanceModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(HeadTeacherPerformancePresenter.class, HeadTeacherPerformancePresenter.MyView.class, HeadTeacherPerformanceView.class, HeadTeacherPerformancePresenter.MyProxy.class);
    }
}