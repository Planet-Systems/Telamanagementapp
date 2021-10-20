package com.planetsystems.tela.managementapp.client.presenter.useraccountrequest;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UserAccountRequestModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(UserAccountRequestPresenter.class, UserAccountRequestPresenter.MyView.class, UserAccountRequestView.class, UserAccountRequestPresenter.MyProxy.class);
    }
}