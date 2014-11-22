package com.tandong.sa.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SmartDialog extends Dialog {
	public int layoutRes;// 布局文件
	Context context;

	private PopupWindow popupWindow;
	public TextView tv_username;

	public SmartDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 自定义布局的构造方法
	 * 
	 * @param context
	 * @param resLayout
	 */
	public SmartDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 自定义主题及布局的构造方法
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public SmartDialog(Context context, int theme, int resLayout) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layoutRes);
	}
}
