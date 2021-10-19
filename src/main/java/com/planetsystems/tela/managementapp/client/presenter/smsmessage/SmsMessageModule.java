package com.planetsystems.tela.managementapp.client.presenter.smsmessage;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SmsMessageModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
		bindPresenter(SmsMessagePresenter.class, SmsMessagePresenter.MyView.class, SmsMessageView.class, SmsMessagePresenter.MyProxy.class);
    }
}