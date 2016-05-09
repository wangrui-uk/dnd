package com.androidoven.resources;

import javax.ws.rs.Path;

import com.androidoven.server.model.CooksListViewPojo;
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
		// TODO Auto-generated method stub
		return null;
	}

}