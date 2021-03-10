package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SystemUserModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SystemUserPresenter.class, SystemUserPresenter.MyView.class, SystemUserView.class, SystemUserPresenter.MyProxy.class);
    }
}