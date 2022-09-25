package com.planetsystems.tela.managementapp.client.presenter.batchupload;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class BatchUploadModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(BatchUploadPresenter.class, BatchUploadPresenter.MyView.class, BatchUploadView.class, BatchUploadPresenter.MyProxy.class);
    }
}