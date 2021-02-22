package com.planetsystems.tela.managementapp.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class HighlightActiveLinkEvent extends GwtEvent<HighlightActiveLinkEvent.HighlightActiveLinkHandler> {
    private static Type<HighlightActiveLinkHandler> TYPE = new Type<HighlightActiveLinkHandler>();
    
    public interface HighlightActiveLinkHandler extends EventHandler {
        void onHighlightActiveLink(HighlightActiveLinkEvent event);
    }
    
    public interface HighlightActiveLinkHandlers extends HasHandlers {
        HandlerRegistration addHandlerRegistration(HighlightActiveLinkHandler handler);
    }
    
    
    private final String message;
   
    public HighlightActiveLinkEvent(final String message) {
        this.message = message;
    }

    public static Type<HighlightActiveLinkHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final HighlightActiveLinkHandler handler) {
        handler.onHighlightActiveLink(this);
    }

    @Override
    public Type<HighlightActiveLinkHandler> getAssociatedType() {
        return TYPE;
    }
    
    
    public String getMessage() {
        return this.message;
    }
}