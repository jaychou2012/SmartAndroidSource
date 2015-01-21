package com.smartandroid.sa.verifi;

import java.util.regex.Pattern;

import android.content.Context;
import android.util.Patterns;

public class UrlVerifior extends AbstractVerifior {

    private static Pattern mPattern = Patterns.WEB_URL;

	private int mErrorMessage;

	public UrlVerifior(Context c) {
		super(c);
		mErrorMessage= c.getResources().getIdentifier("validator_url", "string",
				c.getPackageName());
	}

    public UrlVerifior(Context c, int errorMessage) {
        super(c);
        mErrorMessage = errorMessage;
    }

	@Override
	public boolean isValid(String url) {
		return mPattern.matcher(url).matches();
	}

	@Override
	public String getMessage() {
		return mContext.getString(mErrorMessage);
	}

}
