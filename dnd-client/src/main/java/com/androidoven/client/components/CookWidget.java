package com.androidoven.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class CookWidget extends ResizeComposite implements MouseOutHandler, MouseMoveHandler {

	interface CookWidgetUiBinder extends UiBinder<Widget, CookWidget> {
	}
	private static CookWidgetUiBinder uiBinder = GWT.create(CookWidgetUiBinder.class);
	private boolean up = false;
	@UiField
	LayoutPanel frame;
	@UiField
	LayoutPanel cookContentBase;
	@UiField
	LayoutPanel cookHeader;
	@UiField
	Button likeButton;
	@UiField
	Label cookIcon;
	@UiField
	Image cookImg;
	@UiField
	Label cookname;

	public CookWidget() {
		this.initWidget(uiBinder.createAndBindUi(this));

		this.cookIcon.setText("\uf007");
		this.likeButton.setText("\uf08a");

		this.frame.addDomHandler(this, MouseOutEvent.getType());
		this.frame.addDomHandler(this, MouseMoveEvent.getType());
		
//		this.likeButton.setVisible(false);
	}

	public void setImage(ImageResource resource) {
		this.cookImg.setResource(resource);
		this.cookImg.setWidth("380px");
		this.cookImg.setHeight("300px");
	}
	
	public void setName(String name) {
		this.cookname.setText(name);
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		if (up) {
			up = !up;
			this.frame.setWidgetTopHeight(this.cookHeader, 80, Unit.PX, 300, Unit.PX);
			this.frame.animate(200);
		}
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		int y = event.getRelativeY(this.frame.getElement());
		if (y < 330) {
			if (!up) {
				up = !up;
				this.frame.setWidgetTopHeight(this.cookHeader, 0, Unit.PX, 300, Unit.PX);
				this.frame.animate(200);
			}
		} else {
			if (up) {
				up = !up;
				this.frame.setWidgetTopHeight(this.cookHeader, 80, Unit.PX, 300, Unit.PX);
				this.frame.animate(200);
			}
		}
	}

}
