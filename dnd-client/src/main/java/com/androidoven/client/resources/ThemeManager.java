package com.androidoven.client.resources;

import com.androidoven.client.resources.bundles.LandingBundle;
import com.androidoven.client.resources.styles.LandingStyle;
import com.google.gwt.core.shared.GWT;

public class ThemeManager {
	
	public static final ThemeManager I = new ThemeManager();
	private LandingBundle landingBundle = null;
	
	public LandingBundle getLandingBundle() {
		if (null == this.landingBundle) {
			this.landingBundle = GWT.create(LandingBundle.class);
		}
		return this.landingBundle;
	}
	
	public LandingStyle getLandingStyle() {
		this.getLandingBundle().style().ensureInjected();
		return this.getLandingBundle().style();
	}
	
}