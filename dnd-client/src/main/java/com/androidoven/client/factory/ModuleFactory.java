package com.androidoven.client.factory;

import com.androidoven.client.screen.landing.presenter.LandingPresenter;
import com.google.gwt.core.shared.GWT;
import com.google.web.bindery.event.shared.EventBus;

public interface ModuleFactory {
	
	public static final ModuleFactory I = GWT.create(ModuleFactory.class);

	EventBus getEventBus();
	
	LandingPresenter getLandingPresenter();
	
}