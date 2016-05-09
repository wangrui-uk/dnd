package com.androidoven.client.screen.landing.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.androidoven.transport.xsd.common.Customer;
import com.androidoven.transport.xsd.customerservice.CooksListView;
import com.androidoven.transport.xsd.customerservice.CooksListViewWithCustomer;
import com.google.gwt.core.client.GWT;

@Path("CustomerService")
public interface CustomerService extends RestService {
	
	public static final CustomerService I = GWT.create(CustomerService.class);
	
	@GET
    @Produces("application/json")
    @Path("/cookslist")
    void cookslist(MethodCallback<CooksListView> callback);
	
	@POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/signinCustomer")
    void signinCustomer(Customer customer, MethodCallback<CooksListViewWithCustomer> callback);

}