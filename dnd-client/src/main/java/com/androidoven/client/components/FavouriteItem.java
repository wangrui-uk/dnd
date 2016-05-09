package com.androidoven.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FavouriteItem extends Composite {

	interface FavouriteItemUiBinder extends UiBinder<Widget, FavouriteItem> {
	}
	private static FavouriteItemUiBinder uiBinder = GWT.create(FavouriteItemUiBinder.class);
	@UiField
	public Label cookName;
	@UiField
	public Label close;
	public long id = -1;
	
	
	public FavouriteItem() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.close.setText("\uf00d");
	}

}