package com.androidoven.client.resources.bundles;

import com.androidoven.client.resources.styles.LandingStyle;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface LandingBundle extends ClientBundle {

	static String IMG_DIR = "com/androidoven/client/resources/imgs/";
	static String CSS_DIR = "com/androidoven/client/resources/csses/";
	
	@Source(IMG_DIR + "customer_bg.jpg")
	@ImageOptions(repeatStyle = RepeatStyle.None)
	ImageResource customerImg();
	
	@Source(IMG_DIR + "cook01.jpg")
	@ImageOptions(repeatStyle = RepeatStyle.None)
	ImageResource cook01();
	
	@Source(IMG_DIR + "cook02.jpg")
	@ImageOptions(repeatStyle = RepeatStyle.None)
	ImageResource cook02();
	
	@Source(IMG_DIR + "cook03.jpg")
	@ImageOptions(repeatStyle = RepeatStyle.None)
	ImageResource cook03();
	
	@Source(CSS_DIR + "LandingStyle.css")
	LandingStyle style();
	
}