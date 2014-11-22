package com.tandong.sa.bv;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * BottomView
 * 
 * @author TanDong
 * 
 */
public class BottomView {
	private View convertView;
	private Context context;
	private int theme;
	private Dialog bv;
	private int animationStyle;
	private boolean isTop = false;

	public BottomView(Context c, int theme, View convertView) {
		this.theme = theme;
		this.context = c;
		this.convertView = convertView;
	}

	public BottomView(Context c, int theme, int resource) {
		this.theme = theme;
		this.context = c;
		this.convertView = View.inflate(c, resource, null);
	}

	public void showBottomView(boolean CanceledOnTouchOutside) {
		if (theme == 0) {
			bv = new Dialog(context);
		} else {
			bv = new Dialog(context, theme);
		}
		bv.setCanceledOnTouchOutside(CanceledOnTouchOutside);
		bv.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		bv.setContentView(convertView);
		Window wm = bv.getWindow();
		WindowManager m = wm.getWindowManager();
		Display d = m.getDefaultDisplay();
		WindowManager.LayoutParams p = wm.getAttributes();
		p.width = (int) (d.getWidth() * 1);
		if (isTop) {
			p.gravity = Gravity.TOP;
		} else {
			p.gravity = Gravity.BOTTOM;
		}
		if (animationStyle == 0) {
		} else {
			wm.setWindowAnimations(animationStyle);
		}
		wm.setAttributes(p);
		bv.show();
	}

	public void setTopIfNecessary() {
		isTop = true;
	}

	public void setAnimation(int animationStyle) {
		this.animationStyle = animationStyle;
	}

	public View getView() {
		return convertView;
	};

	public void dismissBottomView() {
		if (bv != null) {
			bv.dismiss();
		}
	}
}
