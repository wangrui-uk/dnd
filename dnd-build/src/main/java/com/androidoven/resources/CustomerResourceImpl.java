package com.androidoven.resources;

import javax.ws.rs.Path;

import com.androidoven.server.model.CooksListViewPojo;
import com.androidoven.server.model.CustomerPojo;
import com.androidoven.transport.wadl.CustomerResource;
import com.androidoven.transport.xsd.common.Customer;
import com.androidoven.transport.xsd.customerservice.CooksListView;
import com.androidoven.transport.xsd.customerservice.CooksListViewWithCustomer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="Customer Service", description="Customer Service provides API for Customer users")
@Path("CustomerService")
public class CustomerResourceImpl implements CustomerResource {

	@ApiOperation(value="Return all cooks list", notes="Returned cook list cannot be modified", response=CooksListView.class)
	@ApiResponses(value={@ApiResponse(code=200, message="Successful retrieval of cooks list", response=CooksListView.class)})
	@Override
	public CooksListView cookslist() {
		return CooksListViewPojo.getInstance().getCooksListView();
	}

	@ApiOperation(value="Return all cooks list with customer information", notes="Returned cook list can be modified", response=CooksListViewWithCustomer.class)
	@ApiResponses(value={@ApiResponse(code=200, message="Successful retrieval of cooks list and customer information", response=CooksListViewWithCustomer.class)})
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

	@ApiOperation(value="Update customer information", notes="Returned cook list can be modified", response=CooksListViewWithCustomer.class)
	@ApiResponses(value={@ApiResponse(code=200, message="Update customer information", response=CooksListViewWithCustomer.class)})
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