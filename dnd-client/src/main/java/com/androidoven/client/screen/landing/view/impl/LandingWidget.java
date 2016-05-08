package com.androidoven.client.screen.landing.view.impl;

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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LandingWidget extends ResizeComposite implements ClickHandler, LandingView, ResizeHandler {

	private enum STATUS {
		
		CUSTOMER_SIGNIN,
		
		COOK_SIGNIN,
		
		;
		
	}
	
	interface LandingWidgetUiBinder extends UiBinder<Widget, LandingWidget> {
	}
	private static LandingWidgetUiBinder uiBinder = GWT.create(LandingWidgetUiBinder.class);
	private LandingPresenter presenter = null;
	private SwitchBubble switchBubble = new SwitchBubble();
	private STATUS status = STATUS.CUSTOMER_SIGNIN;
	@UiField
	LayoutPanel frame;
	@UiField
	LayoutPanel leftPanel;
	@UiField
	LayoutPanel customerSigninPanel;
	@UiField
	LayoutPanel cookPanel;
	@UiField
	LayoutPanel rightPanel;
	@UiField
	LayoutPanel cookSigninPanel;
	@UiField
	LayoutPanel customerPanel;
	@UiField
	Button switchButton;
	
	
	
	
	@UiField
	SimplePanel customerBgPanel;
	@UiField
	Image customerBgImg;
	@UiField
	SimplePanel cookBgPanel;
	@UiField
	Image cookBgImg;
	
	
	
	@UiField
	LabelledTextField usernameField;
	@UiField
	LabelledPasswordField passwordField;
	@UiField
	Button signinBut;
	
	@UiField
	LayoutPanel cookListBase;
	@UiField
	LayoutPanel cookListContent;
	@UiField
	LayoutPanel scrollBase;
	@UiField
	SimplePanel scrollBar;
	@UiField
	Label authMsg;
	
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
				processImage(cookBgPanel, cookBgImg, ThemeManager.I.getLandingBundle().cookImg());
			}
			
		});
	}

	@Override
	public void initial() {
		Window.addResizeHandler(this);
		
		this.switchBubble.setWidth(200);
		this.switchButton.setText("\uf06d");
		this.switchButton.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (status.equals(STATUS.CUSTOMER_SIGNIN)) {
					switchBubble.show(switchButton, "I want to cook!");
				}else if (status.equals(STATUS.COOK_SIGNIN)) {
					switchBubble.show(switchButton, "I want to eat!");
				}
			}
			
		});
		this.switchButton.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				switchBubble.hide();
			}
			
		});
		this.switchButton.addClickHandler(this);
		
		this.usernameField.title.setText("Username");
		this.passwordField.title.setText("Password");
		this.signinBut.addClickHandler(this);
		
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
		this.processImage(this.cookBgPanel, this.cookBgImg, ThemeManager.I.getLandingBundle().cookImg());
	}

	@Override
	public void onClick(ClickEvent event) {
		Object source = event.getSource();
		if (source.equals(this.switchButton)) {
			if (status.equals(STATUS.CUSTOMER_SIGNIN)) {
				status = STATUS.COOK_SIGNIN;
				switchButton.setText("\uf0fc");
				switchBubble.hide();
				leftPanel.setWidgetLeftWidth(customerSigninPanel, 100, Unit.PCT, 100, Unit.PCT);
				leftPanel.setWidgetLeftWidth(cookPanel, 0, Unit.PCT, 100, Unit.PCT);
				rightPanel.setWidgetLeftWidth(customerPanel, 100, Unit.PCT, 100, Unit.PCT);
				rightPanel.setWidgetLeftWidth(cookSigninPanel, 0, Unit.PCT, 100, Unit.PCT);
				leftPanel.animate(200);
				rightPanel.animate(200);
			}else if (status.equals(STATUS.COOK_SIGNIN)) {
				status = STATUS.CUSTOMER_SIGNIN;
				switchButton.setText("\uf06d");
				switchBubble.hide();
				leftPanel.setWidgetLeftWidth(customerSigninPanel, 0, Unit.PCT, 100, Unit.PCT);
				leftPanel.setWidgetLeftWidth(cookPanel, -100, Unit.PCT, 100, Unit.PCT);
				rightPanel.setWidgetLeftWidth(customerPanel, 0, Unit.PCT, 100, Unit.PCT);
				rightPanel.setWidgetLeftWidth(cookSigninPanel, -100, Unit.PCT, 100, Unit.PCT);
				leftPanel.animate(200);
				rightPanel.animate(200);
			}
		}else
		if (source.equals(this.signinBut)) {
			String user = this.usernameField.getText();
			String password = this.passwordField.getText();
			if (null == user || "".equals(user)) {
				this.authMsg.setVisible(true);
				this.authMsg.setText("Username please?");
				this.usernameField.textbox.setFocus(true);
				return;
			}
			if (null == password || "".equals(password)) {
				this.authMsg.setVisible(true);
				this.authMsg.setText("Password please?");
				this.passwordField.textbox.setFocus(true);
				return;
			}
		}
	}
	
}