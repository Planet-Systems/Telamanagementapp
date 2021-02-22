package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SchoolCategoryModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SchoolCategoryPresenter.class, SchoolCategoryPresenter.MyView.class, SchoolCategoryView.class, SchoolCategoryPresenter.MyProxy.class);
    }
}