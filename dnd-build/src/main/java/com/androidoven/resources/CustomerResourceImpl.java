package com.androidoven.resources;

import javax.ws.rs.Path;

import com.androidoven.server.model.CooksListViewPojo;
import com.androidoven.server.model.CustomerPojo;
import com.androidoven.transport.wadl.CustomerResource;
import com.androidoven.transport.xsd.common.Customer;
import com.androidoven.transport.xsd.customerservice.CooksListView;
import com.androidoven.transport.xsd.customerservice.CooksListViewWithCustomer;


@Path("CustomerService")
public class CustomerResourceImpl implements CustomerResource {

	@Override
	public CooksListView cookslist() {
		return CooksListViewPojo.getInstance().getCooksListView();
	}

	@Override
	public CooksListViewWithCustomer signinCustomer(Customer customer) {
		CooksListViewWithCustomer clvwc = new CooksListViewWithCustomer();
		if (CustomerPojo.getInstance().verifyCustomer(customer)) {
			customer.getFavouriteCooksList().addAll(CustomerPojo.getInstance().getCustomer().getFavouriteCooksList());
			clvwc.setCustomer(customer);
			clvwc.getList().addAll(CooksListViewPojo.getInstance().getCooksListView().getList());
		}else{
			customer.setId(null);
			customer.setName(null);
			customer.setPassword(null);
		}
		clvwc.setCustomer(customer);
		return clvwc;
	}

	@Override
	public CooksListViewWithCustomer updateCustomer(Customer customer) {
		CooksListViewWithCustomer clvwc = new CooksListViewWithCustomer();
		if (CustomerPojo.getInstance().verifyCustomer(customer)) {
			CustomerPojo.getInstance().getCustomer().getFavouriteCooksList().clear();
			CustomerPojo.getInstance().getCustomer().getFavouriteCooksList().addAll(customer.getFavouriteCooksList());
			clvwc.setCustomer(customer);
			clvwc.getList().addAll(CooksListViewPojo.getInstance().getCooksListView().getList());
		}else{
			customer.setId(null);
			customer.setName(null);
			customer.setPassword(null);
		}
		clvwc.setCustomer(customer);
		return clvwc;
	}

}