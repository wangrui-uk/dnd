package com.androidoven.client.screen.landing.view;

import java.util.List;

import com.androidoven.client.api.View;
import com.androidoven.client.screen.landing.presenter.LandingPresenter;
import com.androidoven.transport.xsd.common.Cook;
import com.androidoven.transport.xsd.common.CookView;
import com.androidoven.transport.xsd.customerservice.CooksListViewWithCustomer;

public interface LandingView extends View<LandingPresenter> {

	void loadCooksList(List<CookView> list);

	void signinCustomer(CooksListViewWithCustomer response);

	void updateCustomer(CooksListViewWithCustomer response);

	void signinCook(Cook response);

}