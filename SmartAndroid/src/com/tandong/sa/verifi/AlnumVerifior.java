package com.tandong.sa.verifi;

import java.util.regex.Pattern;

import android.content.Context;

/**
 * Validator to check if a field contains only numbers and letters. Avoids
 * having special characters like accents.
 */
public class AlnumVerifior extends AbstractVerifior {

	/**
	 * This si Alnum Pattern to verify value.
	 */
	private static final Pattern mPattern = Pattern.compile("^[A-Za-z0-9]+$");

	private int mErrorMessage;

	public AlnumVerifior(Context c) {
		super(c);
		mErrorMessage = c.getResources().getIdentifier("validator_alnum",
				"string", c.getPackageName());
	}

	public AlnumVerifior(Context c, int errorMessage) {
		super(c);
		mErrorMessage = errorMessage;
	}

	@Override
	public boolean isValid(String value) {
		return mPattern.matcher(value).matches();
	}

	@Override
	public String getMessage() {
		return mContext.getString(mErrorMessage);
	}
}
