package com.androidoven.client.screen.landing.view.impl;

import com.androidoven.client.components.CookWidget;
import com.androidoven.client.components.LabelledPasswordField;
import com.androidoven.client.components.LabelledTextField;
import com.androidoven.client.components.SwitchBubble;
import com.androidoven.client.resources.ThemeManager;
import com.androidoven.client.screen.landing.presenter.LandingPresenter;
import com.androidoven.client.screen.landing.view.LandingView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LandingWidget extends ResizeComposite implements LandingView, ResizeHandler {

	interface LandingWidgetUiBinder extends UiBinder<Widget, LandingWidget> {
	}
	private static LandingWidgetUiBinder uiBinder = GWT.create(LandingWidgetUiBinder.class);
	private LandingPresenter presenter = null;
	private SwitchBubble switchBubble = new SwitchBubble();
	@UiField
	LayoutPanel frame;
	@UiField
	SimplePanel customerBgPanel;
	@UiField
	Image customerBgImg;
	@UiField
	LabelledTextField usernameField;
	@UiField
	LabelledPasswordField passwordField;
	@UiField
	Button switchButton;
	
	public LandingWidget() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.initial();
	}
	
	private void processImage(SimplePanel frame, Image image, ImageResource resource) {
		image.setWidth(resource.getWidth()*frame.getOffsetHeight()/resource.getHeight() + Unit.PX.getType());
		image.setHeight(frame.getOffsetHeight() + Unit.PX.getType());
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				processImage(customerBgPanel, customerBgImg, ThemeManager.I.getLandingBundle().customerImg());
			}
			
		});
	}

	@Override
	public void initial() {
		this.switchButton.setText("\uf06d");
		this.switchBubble.setWidth(200);
		this.switchButton.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				switchBubble.show(switchButton, "I want to cook!");
			}
			
		});
		this.switchButton.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				switchBubble.hide();
			}
			
		});
		this.usernameField.title.setText("Username");
		this.passwordField.title.setText("Password");
		Window.addResizeHandler(this);
	}

	@Override
	public void setPresenter(LandingPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResize(ResizeEvent event) {
		this.processImage(this.customerBgPanel, this.customerBgImg, ThemeManager.I.getLandingBundle().customerImg());
	}
	
}