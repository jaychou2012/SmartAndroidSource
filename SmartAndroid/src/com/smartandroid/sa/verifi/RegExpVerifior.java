package com.smartandroid.sa.verifi;

import java.util.regex.Pattern;

import android.content.Context;

/**
 * This validator test value with custom Regex Pattern.
 */
public class RegExpVerifior extends AbstractVerifior {

	private Pattern mPattern;

	private int mErrorMessage;

	public RegExpVerifior(Context c) {
		super(c);
		mErrorMessage = c.getResources().getIdentifier("validator_regexp",
				"string", c.getPackageName());
	}

	public RegExpVerifior(Context c, int errorMessage) {
		super(c);
		mErrorMessage = errorMessage;
	}

	public void setPattern(String pattern) {
		mPattern = Pattern.compile(pattern);
	}

	public void setPattern(Pattern pattern) {
		mPattern = pattern;
	}

	@Override
	public boolean isValid(String value) throws VerifiorException {
		if (mPattern != null) {
			return mPattern.matcher(value).matches();
		} else {
			throw new VerifiorException("You can set Regexp Pattern first");
		}
	}

	@Override
	public String getMessage() {
		return mContext.getString(mErrorMessage);
	}
}
