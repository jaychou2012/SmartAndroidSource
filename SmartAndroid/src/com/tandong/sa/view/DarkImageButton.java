package com.tandong.sa.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class DarkImageButton extends ImageView {

	public DarkImageButton(Context context) {
		super(context);
		init(context, null);
	}

	public DarkImageButton(Context context, AttributeSet attrs) {
		super(context, attrs, android.R.attr.imageButtonStyle);
		init(context, attrs);
	}

	public DarkImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		setBackgroundDrawable(createStateDrawable(context, getBackground()));
		setFocusable(true);
	}

	@Override
	protected boolean onSetAlpha(int alpha) {
		return false;
	}

	public StateListDrawable createStateDrawable(Context context,
			Drawable normal) {
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(View.PRESSED_ENABLED_STATE_SET,
				createPressDrawable(normal));
		drawable.addState(View.ENABLED_STATE_SET, normal);
		drawable.addState(View.EMPTY_STATE_SET, normal);
		return drawable;
	}

	public Drawable createPressDrawable(Drawable d) {
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap().copy(
				Bitmap.Config.ARGB_8888, true);
		Paint paint = new Paint();
		paint.setColor(0x60000000);
		RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		new Canvas(bitmap).drawRoundRect(rect, 4, 4, paint);
		return new BitmapDrawable(bitmap);
	}
}
