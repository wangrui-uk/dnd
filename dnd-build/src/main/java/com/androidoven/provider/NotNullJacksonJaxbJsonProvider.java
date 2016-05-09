package com.androidoven.provider;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Provider
@Produces("application/json")
public class NotNullJacksonJaxbJsonProvider extends JacksonJaxbJsonProvider {
	
	public NotNullJacksonJaxbJsonProvider() {
		super();
		_mapperConfig.getDefaultMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

}