package com.planetsystems.tela.managementapp.client.presenter.region;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class RegionModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(RegionPresenter.class, RegionPresenter.MyView.class, RegionView.class, RegionPresenter.MyProxy.class);
    }
}