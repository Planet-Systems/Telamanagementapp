package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LearnerEnrollmentModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(LearnerEnrollmentPresenter.class, LearnerEnrollmentPresenter.MyView.class, LearnerEnrollmentView.class, LearnerEnrollmentPresenter.MyProxy.class);
    }
}