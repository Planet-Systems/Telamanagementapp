package com.planetsystems.tela.managementapp.client.presenter.timetableupload;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class TimetableUploadModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(TimetableUploadPresenter.class, TimetableUploadPresenter.MyView.class, TimetableUploadView.class, TimetableUploadPresenter.MyProxy.class);
    }
}