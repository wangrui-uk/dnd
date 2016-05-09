package com.androidoven.client.screen.landing.view.impl;

import java.util.List;

import com.androidoven.client.components.CookWidget;
import com.androidoven.client.components.FavouriteItem;
import com.androidoven.client.components.LabelledPasswordField;
import com.androidoven.client.components.LabelledTextField;
import com.androidoven.client.components.SwitchBubble;
import com.androidoven.client.resources.ThemeManager;
import com.androidoven.client.screen.landing.presenter.LandingPresenter;
import com.androidoven.client.screen.landing.view.LandingView;
import com.androidoven.transport.xsd.common.CookView;
import com.androidoven.transport.xsd.common.Customer;
import com.androidoven.transport.xsd.customerservice.CooksListViewWithCustomer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
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
		
		CUSTOMER,
		
		COOK,

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
	private HandlerRegistration maskEvent = null;
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
	
	@UiField
	LayoutPanel switchBase;
	
	
	@UiField
	LayoutPanel favouriteBase;
	@UiField
	Label favouriteIconBase;
	
	@UiField
	LayoutPanel favouriteListBase;
	
	private Customer customer;
	private boolean favouriteExpand = false;
	
	public LandingWidget() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.initial();
	}

	private void processImage(SimplePanel frame, Image image, ImageResource resource) {
		if (frame.getOffsetWidth() > resource.getWidth()) {
			image.setWidth(frame.getOffsetWidth() + Unit.PX.getType());
			image.setHeight(resource.getHeight()*frame.getOffsetWidth()/resource.getWidth() + Unit.PX.getType());
		}else{
			image.setWidth(resource.getWidth() * frame.getOffsetHeight() / resource.getHeight() + Unit.PX.getType());
			image.setHeight(frame.getOffsetHeight() + Unit.PX.getType());
		}
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
				CookWidget cw = new CookWidget(new CookWidget.Handler() {
					
					@Override
					public void onFavourite(CookWidget source, long id, boolean like) {
						if (null != customer) {
							if (like && !customer.getFavouriteCooksList().contains(id)) {
								customer.getFavouriteCooksList().add(id);
							}else if (!like && customer.getFavouriteCooksList().contains(id)) {
								customer.getFavouriteCooksList().remove(id);
							}
							presenter.onUpdateCustomerFavourite(customer);
						}
					}
					
				});
				cw.setCook(this.cooksList.get(i));
				if (0 == i) {
					cw.setImage(ThemeManager.I.getLandingBundle().cook01());
				}else if (1 == i) {
					cw.setImage(ThemeManager.I.getLandingBundle().cook02());
				}else if (2 == i) {
					cw.setImage(ThemeManager.I.getLandingBundle().cook03());
				}
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
		this.customerPasswordField.textbox.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (13 == event.getCharCode()) {
					submitCustomer();
				}
			}
			
		});
		this.customerSigninBut.addClickHandler(this);

		this.favouriteIconBase.setText("\uf08a");
		this.favouriteIconBase.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				favouriteExpand = !favouriteExpand;
				if (favouriteExpand) {
					frame.setWidgetLeftWidth(leftPanel, 0, Unit.PX, 250, Unit.PX);
					frame.setWidgetLeftRight(rightPanel, 250, Unit.PX, 0, Unit.PX);
					
					frame.setWidgetLeftWidth(switchBase, 200, Unit.PX, 100, Unit.PX);
				}else{
					frame.setWidgetLeftWidth(leftPanel, 0, Unit.PX, 50, Unit.PX);
					frame.setWidgetLeftRight(rightPanel, 50, Unit.PX, 0, Unit.PX);
					
					frame.setWidgetLeftWidth(switchBase, 0, Unit.PX, 100, Unit.PX);
				}
				frame.animate(200);
			}
		});
	}

	@Override
	public void setPresenter(LandingPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void dispose() {}

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
			} else if (status.equals(STATUS.CUSTOMER)) {
				this.customer = null;
				for (int i=0; i<this.cookListContent.getWidgetCount(); i++) {
					CookWidget cookWidget = (CookWidget)this.cookListContent.getWidget(i);
					cookWidget.likeButton.setVisible(false);
				}
				this.status = STATUS.CUSTOMER_SIGNIN;
				this.switchButton.setText("\uf06d");
				
				this.customerSigninPanel.setVisible(true);
				this.frame.setWidgetLeftWidth(this.leftPanel, 0, Unit.PX, 50, Unit.PCT);
				this.frame.setWidgetRightWidth(this.rightPanel, 0, Unit.PX, 50, Unit.PCT);
				
				this.frame.setWidgetLeftRight(this.switchBase, 45, Unit.PCT, 45, Unit.PCT);
				this.frame.setWidgetTopBottom(this.switchBase, 40, Unit.PCT, 40, Unit.PCT);
				
				this.frame.animate(200, new AnimationCallback() {
					
					@Override
					public void onLayout(Layer layer, double progress) {}
					
					@Override
					public void onAnimationComplete() {
						refreshScreen();
					}
					
				});
				this.favouriteBase.setVisible(false);
				this.favouriteExpand = false;
				this.favouriteListBase.setVisible(false);
				this.leftPanel.setWidgetLeftWidth(this.favouriteListBase, 50, Unit.PX, 0, Unit.PX);
			}
		} else if (source.equals(this.customerSigninBut)) {
			this.submitCustomer();
		}
	}
	
	private void submitCustomer() {
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
	
	private void createFavouriteItem(CookView cookView) {
		final FavouriteItem fi = new FavouriteItem();
		fi.cookName.setText(cookView.getName());
		fi.id = cookView.getId();
		fi.close.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for (int i=0; i<cookListContent.getWidgetCount(); i++) {
					CookWidget cw = (CookWidget)cookListContent.getWidget(i);
					if (cw.cookView.getId() == fi.id) {
						if (null != customer) {
							customer.getFavouriteCooksList().remove(fi.id);
							presenter.onUpdateCustomerFavourite(customer);
						}
						break;
					}
				}
			}
		});
		this.favouriteListBase.add(fi);
		this.favouriteListBase.setWidgetLeftRight(fi, 0, Unit.PX, 0, Unit.PX);
		this.favouriteListBase.setWidgetTopHeight(fi, (this.favouriteListBase.getWidgetCount()-1)*40, Unit.PX, 40, Unit.PX);
	}
	
	@Override
	public void signinCustomer(CooksListViewWithCustomer response) {
		if (null != response.getCustomer().getId()) {
			this.customer = response.getCustomer();
			this.customerAuthMsg.setVisible(false);
			this.customerUsernameField.reset();
			this.customerPasswordField.reset();
			this.favouriteListBase.clear();
			for (int i=0; i<this.cookListContent.getWidgetCount(); i++) {
				CookWidget cookWidget = (CookWidget)this.cookListContent.getWidget(i);
				cookWidget.likeButton.setVisible(true);
				boolean like = this.customer.getFavouriteCooksList().contains(cookWidget.cookView.getId());
				cookWidget.setLike(like);
				if (like) {
					this.createFavouriteItem(cookWidget.cookView);
				}
			}
			this.status = STATUS.CUSTOMER;
			this.switchButton.setText("\uf08b");
			
			this.customerSigninPanel.setVisible(false);
			this.frame.setWidgetLeftWidth(this.leftPanel, 0, Unit.PX, 50, Unit.PX);
			this.frame.setWidgetLeftRight(this.rightPanel, 50, Unit.PX, 0, Unit.PX);
			
			this.frame.setWidgetLeftWidth(this.switchBase, 0, Unit.PX, 100, Unit.PX);
			this.frame.setWidgetBottomHeight(this.switchBase, 0, Unit.PX, 100, Unit.PX);
			
			this.frame.animate(200, new AnimationCallback() {
				
				@Override
				public void onLayout(Layer layer, double progress) {}
				
				@Override
				public void onAnimationComplete() {
					refreshScreen();
				}
				
			});
			this.favouriteBase.setVisible(true);
			this.favouriteListBase.setVisible(true);
			this.leftPanel.setWidgetLeftWidth(this.favouriteListBase, 50, Unit.PX, 200, Unit.PX);
			
		}else{
			this.customerAuthMsg.setVisible(true);
			this.customerAuthMsg.setText("Oops, shall we try again?");
		}
	}
	
	private void refreshScreen() {
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
	public void updateCustomer(CooksListViewWithCustomer response) {
		if (null != response.getCustomer().getId()) {
			this.customer = response.getCustomer();
			this.favouriteListBase.clear();
			for (int i=0; i<this.cookListContent.getWidgetCount(); i++) {
				CookWidget cookWidget = (CookWidget)this.cookListContent.getWidget(i);
				cookWidget.likeButton.setVisible(true);
				boolean like = this.customer.getFavouriteCooksList().contains(cookWidget.cookView.getId());
				cookWidget.setLike(like);
				if (like) {
					if (like) {
						this.createFavouriteItem(cookWidget.cookView);
					}
				}
			}
		}else{
			this.customerAuthMsg.setVisible(true);
			this.customerAuthMsg.setText("Oops, shall we try again?");
		}
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if (null != this.scrollEvent) {
			this.scrollEvent.removeHandler();
			this.scrollEvent = null;
		}
		if (null != this.maskEvent) {
			this.maskEvent.removeHandler();
			this.maskEvent = null;
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
			if (null != this.maskEvent) {
				this.maskEvent.removeHandler();
				this.maskEvent = null;
			}
			this.scrollX = -1;
			break;
		}
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		this.maskEvent = Event.addNativePreviewHandler(new NativePreviewHandler() {
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