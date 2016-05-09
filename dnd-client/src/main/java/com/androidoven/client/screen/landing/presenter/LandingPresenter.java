package com.androidoven.client.screen.landing.presenter;

import com.androidoven.client.api.Presenter;
import com.androidoven.client.screen.landing.view.LandingView;
import com.androidoven.transport.xsd.common.Customer;

public interface LandingPresenter extends Presenter<LandingView> {

	void onSigninCustomer(String user, String password);

	void onUpdateCustomerFavourite(Customer customer);

	void onSigninCook(String user, String password);

}