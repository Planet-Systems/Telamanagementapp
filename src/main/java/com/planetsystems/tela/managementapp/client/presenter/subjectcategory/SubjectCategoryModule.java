package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SubjectCategoryModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SubjectCategoryPresenter.class, SubjectCategoryPresenter.MyView.class, SubjectCategoryView.class, SubjectCategoryPresenter.MyProxy.class);
    }
}