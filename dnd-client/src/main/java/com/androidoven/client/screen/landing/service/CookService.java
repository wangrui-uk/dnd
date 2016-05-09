package com.androidoven.client.screen.landing.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.androidoven.transport.xsd.common.Cook;
import com.google.gwt.core.client.GWT;

@Path("CookService")
public interface CookService extends RestService {
	
	public static final CookService I = GWT.create(CookService.class);
	
	@POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/signinCook")
    void signinCook(Cook cook, MethodCallback<Cook> callback);

}