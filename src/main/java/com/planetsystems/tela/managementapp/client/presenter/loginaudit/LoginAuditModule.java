package com.planetsystems.tela.managementapp.client.presenter.loginaudit;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LoginAuditModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(LoginAuditPresenter.class, LoginAuditPresenter.MyView.class, LoginAuditView.class, LoginAuditPresenter.MyProxy.class);
    }
}