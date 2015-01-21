package com.smartandroid.sa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class SmartGridView extends GridView {
	public SmartGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SmartGridView(Context context) {
		super(context);
	}

	public SmartGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
