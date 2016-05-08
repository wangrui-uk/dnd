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
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class LabelledPasswordField extends ResizeComposite {
	
	public interface Resources extends CssResource {
		
		String label_color();
		
		String label_focus_color();
		
		String label_size();
		
		String label_focus_size();
		
	}

	interface LabelledPasswordFieldUiBinder extends UiBinder<Widget, LabelledPasswordField> {
	}
	private static LabelledPasswordFieldUiBinder uiBinder = GWT.create(LabelledPasswordFieldUiBinder.class);
	@UiField
	LayoutPanel frame;
	@UiField
	public Label title;
	@UiField
	public PasswordTextBox textbox;
	@UiField
	Resources style;

	public LabelledPasswordField() {
		this.initWidget(uiBinder.createAndBindUi(this));
		this.title.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				title.removeStyleName(style.label_color());
				title.addStyleName(style.label_focus_color());
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
				title.removeStyleName(style.label_color());
				title.addStyleName(style.label_focus_color());
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
					title.removeStyleName(style.label_focus_color());
					title.addStyleName(style.label_color());
					title.removeStyleName(style.label_focus_size());
					title.addStyleName(style.label_size());
					frame.setWidgetBottomHeight(title, 0, Unit.PX, 30, Unit.PX);
					frame.animate(200);
					textbox.setFocus(false);
				}
			}
		});
	}

	public String getText() {
		return this.textbox.getText();
	}

}