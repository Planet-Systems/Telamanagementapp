package com.planetsystems.tela.managementapp.client.presenter.emailattachmentdownload;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EmailAttachmentDownloadModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(EmailAttachmentDownloadPresenter.class, EmailAttachmentDownloadPresenter.MyView.class, EmailAttachmentDownloadView.class, EmailAttachmentDownloadPresenter.MyProxy.class);
    }
}