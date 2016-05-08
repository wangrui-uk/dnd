package com.androidoven.client.api;

public interface Presenter<T extends View<?>> {

	void go();
	
	T getDisplay();
	
}