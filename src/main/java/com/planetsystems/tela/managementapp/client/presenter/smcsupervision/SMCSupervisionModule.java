package com.planetsystems.tela.managementapp.client.presenter.smcsupervision;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SMCSupervisionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SMCSupervisionPresenter.class, SMCSupervisionPresenter.MyView.class, SMCSupervisionView.class, SMCSupervisionPresenter.MyProxy.class);
    }
}