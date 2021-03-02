package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class StaffEnrollmentModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(StaffEnrollmentPresenter.class, StaffEnrollmentPresenter.MyView.class, StaffEnrollmentView.class, StaffEnrollmentPresenter.MyProxy.class);
    }
}