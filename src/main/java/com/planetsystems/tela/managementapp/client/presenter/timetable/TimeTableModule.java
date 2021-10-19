package com.planetsystems.tela.managementapp.client.presenter.timetable;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule; 

public class TimeTableModule extends AbstractPresenterModule {
    @Override
    protected void configure() { 
		bindPresenter(TimeTablePresenter.class, TimeTablePresenter.MyView.class, TimeTableView.class, TimeTablePresenter.MyProxy.class);
    }
}