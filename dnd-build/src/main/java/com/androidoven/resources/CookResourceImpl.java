package com.androidoven.resources;

import javax.ws.rs.Path;

import com.androidoven.server.model.CookPojo;
import com.androidoven.transport.wadl.CookResource;
import com.androidoven.transport.xsd.common.Cook;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="Cook Service", description="Cook Service provides API for Cook users")
@Path("CookService")
public class CookResourceImpl implements CookResource {

	@ApiOperation(value="Return cook information", notes="Returned cook info", response=Cook.class)
	@ApiResponses(value={@ApiResponse(code=200, message="Successful retrieval of cook information", response=Cook.class)})
	@Override
	public Cook signinCook(Cook cook) {
		Cook cookInfo = CookPojo.getInstance().verifyCustomer(cook);
		if (null == cookInfo) {
			cook.setId(null);
			cook.setName(null);
			cook.setPassword(null);
		}
		return cookInfo;
	}

}