package com.androidoven.client.entry;

import com.androidoven.client.api.Container;
import com.androidoven.client.factory.ModuleFactory;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class DefaultEntryPoint implements EntryPoint, ClosingHandler {
	
	private class ContainerLabel extends Label implements Container {
		
		private ContainerLabel(String text) {
			super(text);
		}

		@Override
		public void dispose() {}
		
	}

	private void setupBroswer() {
		Window.setMargin("0PX");
		Window.addWindowClosingHandler(this);
		
		RootLayoutPanel.get().getElement().getStyle().setProperty("minWidth", "1024PX");
		RootLayoutPanel.get().getElement().getStyle().setProperty("minHeight", "768PX");
	}
	
	private void setContainer(final Container container) {
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				RootLayoutPanel.get().add(container);
				start();
			}
			
			@Override
			public void onFailure(Throwable reason) {}
			
		});
	}
	
	private Container getContainer() {
		if (Canvas.isSupported()) {
			return ModuleFactory.I.getLandingPresenter().getDisplay();
		}else{
			return new ContainerLabel("This web application required HTML5 Canvas support.");
		}
	}
	
	private void start() {
//		ModuleFactory.I.getDataModel().listen();
		ModuleFactory.I.getLandingPresenter().go();
	}
	
	private void dispose() {
		this.getContainer().dispose();
	}

	@Override
	public void onWindowClosing(ClosingEvent event) {
		this.dispose();
	}

	@Override
	public void onModuleLoad() {
		this.setupBroswer();
		this.setContainer(this.getContainer());
	}
	
}