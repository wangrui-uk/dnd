package com.androidoven.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LabelledTextField extends ResizeComposite {
	
	public interface Resources extends CssResource {
		
		String textbox_cook();
		
		String label_color_cook();
		
		String label_focus_color_cook();
		
		String textbox_customer();
		
		String label_color_customer();
		
		String label_focus_color_customer();
		
		String label_size();
		
		String label_focus_size();
		
	}

	interface LabelledTextFieldUiBinder extends UiBinder<Widget, LabelledTextField> {
	}
	private static LabelledTextFieldUiBinder uiBinder = GWT.create(LabelledTextFieldUiBinder.class);
	@UiField
	LayoutPanel frame;
	@UiField
	public Label title;
	@UiField
	public TextBox textbox;
	@UiField
	Resources style;
	private boolean cook = false;

	public LabelledTextField() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.title.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (cook) {
					title.removeStyleName(style.label_color_cook());
					title.addStyleName(style.label_focus_color_cook());
				}else{
					title.removeStyleName(style.label_color_customer());
					title.addStyleName(style.label_focus_color_customer());
				}
				title.removeStyleName(style.label_size());
				title.addStyleName(style.label_focus_size());
				frame.setWidgetTopHeight(title, 0, Unit.PX, 30, Unit.PX);
				frame.animate(200);
				textbox.setFocus(true);
			}
		});
		this.textbox.addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				if (cook) {
					title.removeStyleName(style.label_color_cook());
					title.addStyleName(style.label_focus_color_cook());
				}else{
					title.removeStyleName(style.label_color_customer());
					title.addStyleName(style.label_focus_color_customer());
				}
				title.removeStyleName(style.label_size());
				title.addStyleName(style.label_focus_size());
				frame.setWidgetTopHeight(title, 0, Unit.PX, 30, Unit.PX);
				frame.animate(200);
			}
		});
		this.textbox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				String text = textbox.getText();
				if (null == text || "".equals(text)) {
					if (cook) {
						title.removeStyleName(style.label_focus_color_cook());
						title.addStyleName(style.label_color_cook());
					}else{
						title.removeStyleName(style.label_focus_color_customer());
						title.addStyleName(style.label_color_customer());
					}
					title.removeStyleName(style.label_focus_size());
					title.addStyleName(style.label_size());
					frame.setWidgetBottomHeight(title, 0, Unit.PX, 30, Unit.PX);
					frame.animate(200);
				}
			}
		});
	}
	
	public void applyCookStyle() {
		this.textbox.removeStyleName(this.style.textbox_customer());
		this.textbox.addStyleName(this.style.textbox_cook());
		this.title.removeStyleName(this.style.label_color_customer());
		this.title.addStyleName(this.style.label_color_cook());
		this.cook = true;
	}

	public String getText() {
		return this.textbox.getText();
	}

	public void reset() {
		this.textbox.setText(null);
		if (cook) {
			title.removeStyleName(style.label_focus_color_cook());
			title.addStyleName(style.label_color_cook());
		}else{
			title.removeStyleName(style.label_focus_color_customer());
			title.addStyleName(style.label_color_customer());
		}
		title.removeStyleName(style.label_focus_size());
		title.addStyleName(style.label_size());
		frame.setWidgetBottomHeight(title, 0, Unit.PX, 30, Unit.PX);
		frame.animate(200);
	}

}