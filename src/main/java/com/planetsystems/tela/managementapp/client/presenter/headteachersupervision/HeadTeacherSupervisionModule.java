package com.planetsystems.tela.managementapp.client.presenter.headteachersupervision;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class HeadTeacherSupervisionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(HeadTeacherSupervisionPresenter.class, HeadTeacherSupervisionPresenter.MyView.class, HeadTeacherSupervisionView.class, HeadTeacherSupervisionPresenter.MyProxy.class);
    }
}