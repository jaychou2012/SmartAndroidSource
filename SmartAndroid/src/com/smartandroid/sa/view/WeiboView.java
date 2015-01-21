package com.smartandroid.sa.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class WeiboView extends TextView {
	private Context c;
	private static Activity activity;

	public WeiboView(Context context) {
		// TODO Auto-generated method stub
		super(context);
		this.c = context;
	}

	public WeiboView(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		super(context, attrs);
		this.c = context;
	}

	public WeiboView(Context context, AttributeSet attrs, int defStyle) {
		// TODO Auto-generated method stub
		super(context, attrs, defStyle);
		this.c = context;
	}

	public void getVText() {

		// Spannable
		// SpannableString
		// SpannableStringBuilder
		// Spanned
		// SpanWatcher
		// Pattern
		// Patterns
		// PatternMatcher
		// ClickableSpan
		// ForegroundColorSpan
		// ImageSpan

	}

	public static void setJump(Activity Jumpactivity) {
		activity = Jumpactivity;
	}

	public static void setVViewText(Context mContext, WeiboView vview, String content, boolean hasClick) {
		List<TextItem> list = paresString2(content);
		Spannable span = new SpannableString(content);

		for (TextItem ti : list) {
			if (ti.getPrefixType() == 1) {// @
				span.setSpan(new TCSapn(mContext, ti), ti.start, ti.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				// //备用加入表情
				// try {
				// Field f = (Field)
				// R.drawable.class.getDeclaredField(imageName);
				// int eId = f.getInt(R.drawable.class);
				// Drawable drawable = mContext.getResources().getDrawable(eId);
				// if (drawable != null) {
				// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				// drawable.getIntrinsicHeight());
				// ImageSpan imgSpan = new ImageSpan(drawable,
				// ImageSpan.ALIGN_BASELINE);
				// span.setSpan(imgSpan, pi.start, pi.end,
				// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				// } else {
				// span.setSpan(new ForegroundColorSpan(Color.BLUE), pi.start,
				// pi.end,
				// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				// }
				// } catch (Exception e) {
				// // TODO: handle exception
				// span.setSpan(new ForegroundColorSpan(Color.BLUE), pi.start,
				// pi.end,
				// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				// }
				// } else {
				// if (hasClick)
				// span.setSpan(new TextClickSapn(mContext, pi), pi.start,
				// pi.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				// else
				// span.setSpan(new ForegroundColorSpan(Color.BLUE), pi.start,
				// pi.end,
				// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		vview.setText(span);
		if (hasClick)
			vview.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private static class TCSapn extends ClickableSpan {
		private TextItem item;
		private Context mContext;

		public TCSapn(Context context, TextItem item) {
			// TODO Auto-generated constructor stub
			this.item = item;
			this.mContext = context;
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			// TODO Auto-generated method stub
			super.updateDrawState(ds);
			// ds.setColor(ds.linkColor);
			ds.setUnderlineText(false);
		}

		@Override
		public void onClick(View widget) {
			// TODO Auto-generated method stub
			switch (item.getPrefixType()) {
			case 1:// @
				Intent it_person = new Intent(mContext, activity.getClass());
				it_person.putExtra("content", item.getContentWithoutPrefix());
				Log.i("info", item.getContentWithoutPrefix());
				mContext.startActivity(it_person);
				break;
			case 2:
				Intent it_topic = new Intent(mContext, activity.getClass());
				it_topic.putExtra("content", item.getContentWithoutPrefix());
				mContext.startActivity(it_topic);
				break;
			case 3:
				// 直接使用调用浏览器
				// 这个是短链 ，还需要条用微博接口，转成原始连接 才能访问
				// 先使用短链去调用接口，获取长链，再启动浏览器
				Intent intent = new Intent();
				// intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://www.sina.com");
				intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(content_url);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
				break;
			default:
				break;
			}
		}
	}

	public static List<TextItem> paresString(String content) {// 备用
		String regex = "@[^\\s:：《]+([\\s:：《]|$)|#(.+?)#|http://t\\.cn/\\w+|\\[(.*?)\\]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		boolean b = m.find();
		List<TextItem> list = new ArrayList<TextItem>();
		while (b) {
			System.out.println(m.start());
			System.out.println(m.end());
			System.out.println(m.group());
			int start = m.start();
			int end = m.end();
			String str = m.group();
			list.add(new TextItem(start, end, str, content.length()));
			b = m.find(m.end() - 1);
		}
		return list;
	}

	public static List<TextItem> paresString2(String content) {
		String regex = "@[^\\s:：《]+([\\s:：《]|$)|#(.+?)#|http://t\\.cn/\\w+|\\[(.*?)\\]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		boolean b = m.find();
		List<TextItem> list = new ArrayList<TextItem>();
		int count = 0;
		int lastIndex = 0;
		while (b) {
			System.out.println(m.start());
			System.out.println(m.end());
			System.out.println(m.group());
			int start = m.start();
			int end = m.end();
			String str = m.group();
			if (str.startsWith("#")) {
				count++;
				if (count % 2 == 0) {
					b = m.find(lastIndex);
					continue;
				}
			}
			list.add(new TextItem(start, end, str, content.length()));
			b = m.find(m.end() - 1);
			try {
				lastIndex = m.start() + 1;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return list;
	}

	public static class TextItem {
		public int start;
		public int end;
		private int prefixType;
		private String content;
		private int strLenght;

		public TextItem(int start, int end, String content, int strLenght) {
			// TODO Auto-generated constructor stub
			this.start = start;
			this.end = end;
			this.content = content;
			this.strLenght = strLenght;
		}

		public TextItem(int start, int end, String content) {
			// TODO Auto-generated constructor stub
			this.start = start;
			this.end = end;
			this.content = content;
		}

		public String getContent() {
			return content;
		}

		public String getContentWithoutPrefix() {
			switch (getPrefixType()) {
			case 1:
				if (end == strLenght)
					return content.substring(1, strLenght);
				return content.substring(1, content.length() - 1);
			case 2:
				return content.substring(1, content.length() - 1);
			case 3:
				return content;
			default:
				return content;
			}
		}

		/**
		 * 1 @ 人物 2 # 话题 3 http://t.cn/ 短链 4 [ 表情
		 * 
		 * @return
		 */
		public int getPrefixType() {
			if (content.startsWith("@"))
				return 1;
			if (content.startsWith("#"))
				return 2;
			if (content.startsWith("http://"))
				return 3;
			if (content.startsWith("["))
				return 4;
			return -1;
		}
	}
}
