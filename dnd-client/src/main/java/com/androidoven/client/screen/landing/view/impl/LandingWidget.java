package com.androidoven.client.screen.landing.view.impl;

import java.util.List;

import com.androidoven.client.components.CookWidget;
import com.androidoven.client.components.LabelledPasswordField;
import com.androidoven.client.components.LabelledTextField;
import com.androidoven.client.components.SwitchBubble;
import com.androidoven.client.resources.ThemeManager;
import com.androidoven.client.screen.landing.presenter.LandingPresenter;
import com.androidoven.client.screen.landing.view.LandingView;
import com.androidoven.transport.xsd.common.CookView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LandingWidget extends ResizeComposite
		implements ClickHandler, LandingView, ResizeHandler, MouseDownHandler, MouseUpHandler, NativePreviewHandler {

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
	private List<CookView> cooksList = null;
	private int cookViewLeft = 0;
	private int cookBarLeft = 0;
	private HandlerRegistration scrollEvent = null;
	private int scrollX = -1;
	private int cookBarW = 0;
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
	LayoutPanel cookListBase;
	@UiField
	LayoutPanel cookListContent;
	@UiField
	LayoutPanel scrollBase;
	@UiField
	SimplePanel scrollBar;
	
	
	
	@UiField
	LabelledTextField customerUsernameField;
	@UiField
	LabelledPasswordField customerPasswordField;
	@UiField
	Button customerSigninBut;
	@UiField
	Label customerAuthMsg;
	
	public LandingWidget() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.initial();
	}

	private void processImage(SimplePanel frame, Image image, ImageResource resource) {
		image.setWidth(resource.getWidth() * frame.getOffsetHeight() / resource.getHeight() + Unit.PX.getType());
		image.setHeight(frame.getOffsetHeight() + Unit.PX.getType());
	}

	private void resetCooksList() {
		this.cookListContent.clear();
		this.cookListBase.setWidgetLeftWidth(this.cookListContent, 0, Unit.PX, 0, Unit.PX);
		this.scrollBase.setWidgetLeftWidth(this.scrollBar, 0, Unit.PX, 0, Unit.PX);
	}

	private void updateCooksList() {
		this.resetCooksList();
		if (null != this.cooksList && this.cooksList.size() > 0) {
			for (int i = 0; i < this.cooksList.size(); i++) {
				CookWidget cw = new CookWidget();
				cw.setCook(this.cooksList.get(i));
				this.cookListContent.add(cw);
				this.cookListContent.setWidgetTopBottom(cw, 0, Unit.PX, 0, Unit.PX);
				this.cookListContent.setWidgetLeftWidth(cw, i * 410, Unit.PX, 400, Unit.PX);
			}
			int cookListW = this.cooksList.size() * 410;
			this.cookBarW = this.cookListBase.getOffsetWidth() * this.scrollBase.getOffsetWidth() / cookListW;
			this.cookListBase.setWidgetLeftWidth(this.cookListContent, this.cookViewLeft, Unit.PX, cookListW, Unit.PX);
			this.scrollBase.setWidgetLeftWidth(this.scrollBar, this.cookBarLeft, Unit.PX, this.cookBarW, Unit.PX);
		}
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
				} else if (status.equals(STATUS.COOK_SIGNIN)) {
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

		this.scrollBar.addDomHandler(this, MouseDownEvent.getType());
		this.scrollBar.addDomHandler(this, MouseUpEvent.getType());

		this.customerUsernameField.title.setText("Username");
		this.customerPasswordField.title.setText("Password");
		this.customerSigninBut.addClickHandler(this);

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
		if (null != this.cooksList && this.cooksList.size() > 0) {
			int cookListW = this.cooksList.size() * 410;
			this.cookBarW = this.cookListBase.getOffsetWidth() * this.scrollBase.getOffsetWidth() / cookListW;
			this.cookListBase.setWidgetLeftWidth(this.cookListContent, this.cookViewLeft, Unit.PX, cookListW, Unit.PX);
			this.scrollBase.setWidgetLeftWidth(this.scrollBar, this.cookBarLeft, Unit.PX, this.cookBarW, Unit.PX);
		}
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
			} else if (status.equals(STATUS.COOK_SIGNIN)) {
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
		} else if (source.equals(this.customerSigninBut)) {
			String user = this.customerUsernameField.getText();
			String password = this.customerPasswordField.getText();
			if (null == user || "".equals(user)) {
				this.customerAuthMsg.setVisible(true);
				this.customerAuthMsg.setText("Username please?");
				this.customerUsernameField.textbox.setFocus(true);
				return;
			}
			if (null == password || "".equals(password)) {
				this.customerAuthMsg.setVisible(true);
				this.customerAuthMsg.setText("Password please?");
				this.customerPasswordField.textbox.setFocus(true);
				return;
			}
			if (null != this.presenter) {
				this.presenter.onSigninCustomer(user, password);
			}
		}
	}

	@Override
	public void loadCooksList(List<CookView> list) {
		this.cooksList = list;
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				updateCooksList();
			}

		});
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if (null != this.scrollEvent) {
			this.scrollEvent.removeHandler();
			this.scrollEvent = null;
		}
		this.scrollX = -1;
	}

	@Override
	public void onPreviewNativeEvent(NativePreviewEvent event) {
		Event nativeEvent = Event.as(event.getNativeEvent());
		int type = nativeEvent.getTypeInt();
		switch (type) {
		case Event.ONMOUSEMOVE: {
			int screenX = nativeEvent.getScreenX();
			this.cookBarLeft += screenX - this.scrollX;
			this.scrollX = screenX;
			if (this.cookBarLeft<0) {
				this.cookBarLeft = 0;
			}else if (this.cookBarLeft+this.cookBarW > this.scrollBase.getOffsetWidth()) {
				this.cookBarLeft = this.scrollBase.getOffsetWidth() - this.cookBarW;
			}
			int cookListW = this.cooksList.size() * 410;
			this.cookViewLeft = -this.cookBarLeft*cookListW/this.scrollBase.getOffsetWidth();
			this.cookListBase.setWidgetLeftWidth(this.cookListContent, this.cookViewLeft, Unit.PX, cookListW, Unit.PX);
			this.scrollBase.setWidgetLeftWidth(this.scrollBar, this.cookBarLeft, Unit.PX, this.cookBarW, Unit.PX);
			break;
		}
		case Event.ONMOUSEUP: {
			if (null != this.scrollEvent) {
				this.scrollEvent.removeHandler();
				this.scrollEvent = null;
			}
			this.scrollX = -1;
			break;
		}
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				EventTarget target = event.getNativeEvent().getEventTarget();
				if (!target.equals(RootLayoutPanel.get().getElement())) {
					event.getNativeEvent().preventDefault();
				}
			}
		});
		this.scrollEvent = Event.addNativePreviewHandler(this);
		this.scrollX = event.getScreenX();
	}

}