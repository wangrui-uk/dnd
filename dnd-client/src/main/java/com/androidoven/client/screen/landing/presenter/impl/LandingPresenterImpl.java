package com.androidoven.client.screen.landing.presenter.impl;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.androidoven.client.screen.landing.presenter.LandingPresenter;
import com.androidoven.client.screen.landing.service.CustomerService;
import com.androidoven.client.screen.landing.view.LandingView;
import com.androidoven.transport.xsd.customerservice.CooksListView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class LandingPresenterImpl implements LandingPresenter {
	
	private LandingView display = null;
	
	public LandingPresenterImpl(LandingView display) {
		this.display = display;
	}
	
	private void customerCooksList() {
		Defaults.setServiceRoot(GWT.getHostPageBaseURL()+"api");
		CustomerService.I.cookslist(new MethodCallback<CooksListView>() {
			
			@Override
			public void onSuccess(Method method, CooksListView response) {
				display.loadCooksList(response.getList());
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("Fail to retrieve cooks list");
			}
		});
	}

	@Override
	public void go() {
		this.display.setPresenter(this);
		this.customerCooksList();
		
	}

	@Override
	public LandingView getDisplay() {
		return this.display;
	}

	@Override
	public void onSigninCustomer(String user, String password) {
		// TODO Auto-generated method stub
		
	}

}