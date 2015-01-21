package com.smartandroid.sa.bv;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * BelowView
 * 
 * @author TanDong
 * 
 */
public class BelowView {
	private View convertView;
	private Context context;
	private PopupWindow pw;
	private int animationStyle;

	public BelowView(Context c, View convertView) {

		this.context = c;
		this.convertView = convertView;
	}

	public BelowView(Context c, int resource) {

		this.context = c;
		this.convertView = View.inflate(c, resource, null);
	}

	public void showBelowView(View view, boolean CanceledOnTouchOutside, int xoff, int yoff) {
		pw = new PopupWindow(convertView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		pw.setOutsideTouchable(CanceledOnTouchOutside);
		if (animationStyle == 0) {
		} else {
			pw.setAnimationStyle(animationStyle);
		}
		pw.setBackgroundDrawable(new ColorDrawable(0));
		pw.showAsDropDown(view, xoff, yoff);
	}

	public void setAnimation(int animationStyle) {
		this.animationStyle = animationStyle;
	}

	public View getBelowView() {
		return convertView;
	};

	public void dismissBelowView() {
		if (pw != null) {
			pw.dismiss();
		}
	}
}
