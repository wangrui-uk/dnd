package com.androidoven.client.factory.impl;

import com.androidoven.client.factory.ModuleFactory;
import com.androidoven.client.screen.landing.presenter.LandingPresenter;
import com.androidoven.client.screen.landing.presenter.impl.LandingPresenterImpl;
import com.androidoven.client.screen.landing.view.LandingView;
import com.androidoven.client.screen.landing.view.impl.LandingWidget;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.event.shared.EventBus;

public class ModuleFactoryImpl implements ModuleFactory {
	
	private EventBus eventBus = null;
	private LandingPresenter landingPrespenter = null;
	private LandingWidget landingView = null;
	
	public EventBus getEventBus() {
		if (null == this.eventBus) {
			this.eventBus = new SimpleEventBus();
		}
		return this.eventBus;
	}
	
	private LandingView getLandingView() {
		if (null == this.landingView) {
			this.landingView  = new LandingWidget();
		}
		return this.landingView;
	}

	@Override
	public LandingPresenter getLandingPresenter() {
		if (null == this.landingPrespenter) {
			this.landingPrespenter = new LandingPresenterImpl(this.getLandingView());
		}
		return this.landingPrespenter;
	}

}