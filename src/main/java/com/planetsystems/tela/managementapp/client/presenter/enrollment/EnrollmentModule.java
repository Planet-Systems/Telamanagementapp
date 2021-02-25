package com.planetsystems.tela.managementapp.client.presenter.enrollment;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EnrollmentModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(EnrollmentPresenter.class, EnrollmentPresenter.MyView.class, EnrollmentView.class, EnrollmentPresenter.MyProxy.class);
    }
}