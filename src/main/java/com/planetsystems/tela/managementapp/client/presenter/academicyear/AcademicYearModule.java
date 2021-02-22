package com.planetsystems.tela.managementapp.client.presenter.academicyear;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AcademicYearModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(AcademicYearPresenter.class, AcademicYearPresenter.MyView.class, AcademicYearView.class, AcademicYearPresenter.MyProxy.class);
    }
}