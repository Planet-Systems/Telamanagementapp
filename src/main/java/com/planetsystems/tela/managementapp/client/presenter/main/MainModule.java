package com.planetsystems.tela.managementapp.client.presenter.main;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.AcademicYearModule;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.DashboardModule;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionModule;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.SchoolCategoryModule;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.SubjectCategoryModule;

public class MainModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
     
    	  install(new DashboardModule());
          install(new AcademicYearModule());
          install(new RegionModule());
          install(new SchoolCategoryModule());
          install(new SubjectCategoryModule());
    	bindPresenter(MainPresenter.class, MainPresenter.MyView.class, MainView.class, MainPresenter.MyProxy.class);
     
    }
}