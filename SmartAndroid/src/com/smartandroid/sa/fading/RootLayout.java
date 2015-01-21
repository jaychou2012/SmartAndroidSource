package com.smartandroid.sa.fading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class RootLayout extends FrameLayout {

	private View mHeaderContainer;
	private View mListViewBackground;
	private boolean mInitialized = false;
	private Context c;

	public RootLayout(Context context) {
		super(context);
		this.c = context;
	}

	public RootLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.c = context;
	}

	public RootLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.c = context;
	}

	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// at first find headerViewContainer and listViewBackground
		if (mHeaderContainer == null)
			mHeaderContainer = findViewById(getResources().getIdentifier(
					"fab__header_container", "id", c.getPackageName()));
		// mHeaderContainer = findViewById(R.id.fab__header_container);
		if (mListViewBackground == null)
			mListViewBackground = findViewById(getResources().getIdentifier(
					"fab__listview_background", "id", c.getPackageName()));
		// mListViewBackground = findViewById(R.id.fab__listview_background);

		// if there's no headerViewContainer then fallback to standard
		// FrameLayout
		if (mHeaderContainer == null) {
			super.onLayout(changed, left, top, right, bottom);
			return;
		}

		if (!mInitialized) {
			super.onLayout(changed, left, top, right, bottom);
			// if mListViewBackground not exists or mListViewBackground exists
			// and its top is at headercontainer height then view is initialized
			if (mListViewBackground == null
					|| mListViewBackground.getTop() == mHeaderContainer
							.getHeight())
				mInitialized = true;
			return;
		}

		// get last header and listViewBackground position
		int headerTopPrevious = mHeaderContainer.getTop();
		int listViewBackgroundTopPrevious = mListViewBackground != null ? mListViewBackground
				.getTop() : 0;

		// relayout
		super.onLayout(changed, left, top, right, bottom);

		// revert header top position
		int headerTopCurrent = mHeaderContainer.getTop();
		if (headerTopCurrent != headerTopPrevious) {
			mHeaderContainer.offsetTopAndBottom(headerTopPrevious
					- headerTopCurrent);
		}
		// revert listViewBackground top position
		int listViewBackgroundTopCurrent = mListViewBackground != null ? mListViewBackground
				.getTop() : 0;
		if (listViewBackgroundTopCurrent != listViewBackgroundTopPrevious) {
			mListViewBackground
					.offsetTopAndBottom(listViewBackgroundTopPrevious
							- listViewBackgroundTopCurrent);
		}
	}

}