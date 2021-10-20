package com.planetsystems.tela.managementapp.client.presenter.useraccountapproval;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UserAccountApprovalModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(UserAccountApprovalPresenter.class, UserAccountApprovalPresenter.MyView.class, UserAccountApprovalView.class, UserAccountApprovalPresenter.MyProxy.class);
    }
}