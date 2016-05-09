package com.androidoven.server.model;

import com.androidoven.transport.xsd.common.Cook;

public class CookPojo {
	
	private static CookPojo I = null;
	private Cook cooks[] = null;
	
	private CookPojo() {}
	
	private void setupCooks() {
		this.cooks = new Cook[5];
		
		this.cooks[0] = new Cook();
		this.cooks[0].setId("tom");
		this.cooks[0].setName("Tom");
		this.cooks[0].setPassword("Password123");
		this.cooks[0].setFavouriteNum(0);
		
		this.cooks[1] = new Cook();
		this.cooks[1].setId("jerry");
		this.cooks[1].setName("Jerry");
		this.cooks[1].setPassword("Password123");
		this.cooks[1].setFavouriteNum(0);
		
		this.cooks[2] = new Cook();
		this.cooks[2].setId("george");
		this.cooks[2].setName("George");
		this.cooks[2].setPassword("Password123");
		this.cooks[2].setFavouriteNum(0);
		
		this.cooks[3] = new Cook();
		this.cooks[3].setId("william");
		this.cooks[3].setName("William");
		this.cooks[3].setPassword("Password123");
		this.cooks[3].setFavouriteNum(0);
		
		this.cooks[4] = new Cook();
		this.cooks[4].setId("jacob");
		this.cooks[4].setName("Jacob");
		this.cooks[4].setPassword("Password123");
		this.cooks[4].setFavouriteNum(0);	
	}
	
	public Cook[] getCooks() {
		return this.cooks;
	}
	
	public static CookPojo getInstance() {
		if (null == I) {
			I = new CookPojo();
			I.setupCooks();
		}
		return I;
	}

	public Cook verifyCustomer(Cook cook) {
		for (int i=0; i<this.cooks.length; i++) {
			if (cook.getId().equals(this.cooks[i].getId()) && cook.getPassword().equals(this.cooks[i].getPassword())) {
				return this.cooks[i];
			}
		}
		return null;
	}

}