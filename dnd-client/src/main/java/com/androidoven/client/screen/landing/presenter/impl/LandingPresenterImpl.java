package com.androidoven.client.screen.landing.presenter.impl;

import com.androidoven.client.screen.landing.presenter.LandingPresenter;
import com.androidoven.client.screen.landing.view.LandingView;

public class LandingPresenterImpl implements LandingPresenter {
	
	private LandingView display = null;
	
	public LandingPresenterImpl(LandingView display) {
		this.display = display;
	}

	@Override
	public void go() {
		this.display.setPresenter(this);
	}

	@Override
	public LandingView getDisplay() {
		return this.display;
	}

}