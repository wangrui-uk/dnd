package com.androidoven.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SwitchBubble extends Composite implements ResizeHandler {

	interface SwitchBubbleUiBinder extends UiBinder<Widget, SwitchBubble> {
	}
	private static SwitchBubbleUiBinder uiBinder = GWT.create(SwitchBubbleUiBinder.class);
	private int width = 200;
	private PopupPanel popup = new PopupPanel(true);
	private UIObject parent = null;
	@UiField
	VerticalPanel base;
	@UiField
	Label label;

	public SwitchBubble() {
		this.initWidget(uiBinder.createAndBindUi(this));

		this.setWidth(this.width);

		this.popup.setStyleName(null);
		this.popup.setAnimationEnabled(false);
		this.popup.setWidget(this);
		Window.addResizeHandler(this);
	}

	private void setLocation(UIObject object) {
		int x = object.getAbsoluteLeft() + (object.getOffsetWidth() / 2) - (this.width / 2);
		int y = object.getAbsoluteTop() + object.getOffsetHeight() + 10;
		this.popup.setPopupPosition(x, y);
	}
	
	public void show(UIObject source, String message) {
		this.label.setText(message);
		this.setLocation(this.parent = source);
		this.popup.show();
	}

	public void hide() {
		this.parent = null;
		this.popup.hide();
	}

	public void setWidth(int width) {
		this.base.setWidth(width + Unit.PX.getType());
	}

	@Override
	public void onResize(ResizeEvent event) {
		if (this.popup.isShowing() && null != this.parent) {
			this.setLocation(this.parent);
		}
	}

}