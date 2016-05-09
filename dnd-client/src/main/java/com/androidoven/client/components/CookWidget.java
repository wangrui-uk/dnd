package com.androidoven.client.components;

import com.androidoven.transport.xsd.common.CookView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	
	public interface Handler {
		
		void onFavourite(CookWidget source, String id, boolean like);
		
	}

	interface CookWidgetUiBinder extends UiBinder<Widget, CookWidget> {
	}
	private static CookWidgetUiBinder uiBinder = GWT.create(CookWidgetUiBinder.class);
	private boolean up = false;
	private boolean like = false;
	public CookView cookView = null;
	@UiField
	LayoutPanel frame;
	@UiField
	LayoutPanel cookContentBase;
	@UiField
	LayoutPanel cookHeader;
	@UiField
	public Button likeButton;
	@UiField
	Label cookIcon;
	@UiField
	Image cookImg;
	@UiField
	Label cookname;
	@UiField
	Label dishName1;
	@UiField
	Label dishName2;
	@UiField
	Label dishName3;
	@UiField
	Label dishName4;
	@UiField
	Label dishName5;
	@UiField
	Label dishValue1;
	@UiField
	Label dishValue2;
	@UiField
	Label dishValue3;
	@UiField
	Label dishValue4;
	@UiField
	Label dishValue5;
	
	public CookWidget(final Handler handler) {
		this.initWidget(uiBinder.createAndBindUi(this));

		this.cookIcon.setText("\uf007");
		this.likeButton.setText("\uf08a");
		this.likeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				like = !like;
				handler.onFavourite(CookWidget.this, cookView.getId(), like);
			}
		});

		this.frame.addDomHandler(this, MouseOutEvent.getType());
		this.frame.addDomHandler(this, MouseMoveEvent.getType());
		
		this.likeButton.setVisible(false);
		
		this.cookHeader.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (likeButton.isVisible()) {
					like = !like;
					handler.onFavourite(CookWidget.this, cookView.getId(), like);
				}
			}
		}, ClickEvent.getType());
	}

	public void setLike(boolean like) {
		if (this.like = like) {
			this.likeButton.setText("\uf004");
		}else{
			this.likeButton.setText("\uf08a");
		}
	}
	
	public void setImage(ImageResource resource) {
		this.cookImg.setResource(resource);
		this.cookImg.setWidth("380px");
		this.cookImg.setHeight("300px");
	}
	
	public void setName(String name) {
		this.cookname.setText(name);
	}
	
	public void setCook(CookView cookView) {
		this.cookView = cookView;
		this.cookname.setText(this.cookView.getName());
		
		this.dishName1.setText(cookView.getDishes().get(0).getName());
		this.dishValue1.setText("£"+cookView.getDishes().get(0).getPrice());
		
		this.dishName2.setText(cookView.getDishes().get(1).getName());
		this.dishValue2.setText("£"+cookView.getDishes().get(1).getPrice());
		
		this.dishName3.setText(cookView.getDishes().get(2).getName());
		this.dishValue3.setText("£"+cookView.getDishes().get(2).getPrice());
		
		this.dishName4.setText(cookView.getDishes().get(3).getName());
		this.dishValue4.setText("£"+cookView.getDishes().get(3).getPrice());
		
		this.dishName5.setText(cookView.getDishes().get(4).getName());
		this.dishValue5.setText("£"+cookView.getDishes().get(4).getPrice());
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		if (this.likeButton.isVisible()) {
			if (up) {
				up = !up;
				this.frame.setWidgetTopHeight(this.cookHeader, 80, Unit.PX, 300, Unit.PX);
				this.frame.animate(200);
			}
		}
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (this.likeButton.isVisible()) {
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
	
}