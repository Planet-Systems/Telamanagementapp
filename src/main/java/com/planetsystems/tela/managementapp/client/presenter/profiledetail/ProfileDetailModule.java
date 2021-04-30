package com.planetsystems.tela.managementapp.client.presenter.profiledetail;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ProfileDetailModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(ProfileDetailPresenter.class, ProfileDetailPresenter.MyView.class, ProfileDetailView.class, ProfileDetailPresenter.MyProxy.class);
    }
}