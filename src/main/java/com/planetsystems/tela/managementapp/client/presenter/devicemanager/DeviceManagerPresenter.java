package com.planetsystems.tela.managementapp.client.presenter.devicemanager;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
    import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
public class DeviceManagerPresenter extends Presenter<DeviceManagerPresenter.MyView, DeviceManagerPresenter.MyProxy>  {
    interface MyView extends View  {
    }
    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_DeviceManager = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.devicemanager)
    @ProxyCodeSplit
    @NoGatekeeper
    interface MyProxy extends ProxyPlace<DeviceManagerPresenter> {
    }

    @Inject
    DeviceManagerPresenter(
            EventBus eventBus,
            MyView view, 
            MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
        
    }
    
    protected void onBind() {
        super.onBind();
    }
    
}