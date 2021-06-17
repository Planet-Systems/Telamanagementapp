package com.planetsystems.tela.managementapp.client.presenter.devicemanager;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DeviceManagerModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(DeviceManagerPresenter.class, DeviceManagerPresenter.MyView.class, DeviceManagerView.class, DeviceManagerPresenter.MyProxy.class);
    }
}