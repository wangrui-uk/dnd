package com.androidoven.client.api;

public interface View<T extends Presenter<?>> extends Container {
	
	void initial();
	
	void setPresenter(T presenter);
	
}