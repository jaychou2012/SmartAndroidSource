package com.smartandroid.sa.verifi;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Represent a bar with 2 buttons
 * 
 * @author Castorflex
 * 
 */
public class OkCancelBar extends LinearLayout {
	private Button okButton;
	private Button cancelButton;

	public OkCancelBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater.from(context).inflate(
				context.getResources().getIdentifier("widget_ok_cancel_bar",
						"layout", context.getPackageName()), this, true);

		TypedArray array = context.obtainStyledAttributes(
				attrs,
				new int[] { getResources().getIdentifier("OkCancelBar",
						"styleable", context.getPackageName()) }, 0, 0);

		String text = array.getString(getResources().getIdentifier(
				"OkCancelBar_okLabel", "styleable", context.getPackageName()));

		if (text == null)
			text = context.getString(getResources().getIdentifier(
					"global_accept", "string", context.getPackageName()));
		okButton = (Button) findViewById(getResources().getIdentifier(
				"widget_okcancelbar_ok", "id", context.getPackageName()));
		okButton.setText(text);

		text = array.getString(getResources().getIdentifier(
				"OkCancelBar_cancelLabel", "styleable",
				context.getPackageName()));
		if (text == null)
			text = context.getString(getResources().getIdentifier(
					"global_cancel", "string", context.getPackageName()));

		cancelButton = (Button) findViewById(getResources().getIdentifier(
				"widget_okcancelbar_cancel", "id", context.getPackageName()));
		cancelButton.setText(text);
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}
}