package com.planetsystems.tela.managementapp.client.presenter.datauploadstat;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DataUploadStatModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(DataUploadStatPresenter.class, DataUploadStatPresenter.MyView.class, DataUploadStatView.class, DataUploadStatPresenter.MyProxy.class);
    }
}