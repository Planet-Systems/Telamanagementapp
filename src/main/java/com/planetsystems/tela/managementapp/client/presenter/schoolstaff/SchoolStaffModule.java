package com.planetsystems.tela.managementapp.client.presenter.schoolstaff;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
@Deprecated
public class SchoolStaffModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SchoolStaffPresenter.class, SchoolStaffPresenter.MyView.class, SchoolStaffView.class, SchoolStaffPresenter.MyProxy.class);
    }
}