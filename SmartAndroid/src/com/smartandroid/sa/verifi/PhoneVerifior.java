package com.smartandroid.sa.verifi;

import java.util.regex.Pattern;

import android.content.Context;
import android.util.Patterns;

/**
 * Validator to check if Phone number is correct. Created by throrin19 on
 * 13/06/13.
 */
public class PhoneVerifior extends AbstractVerifior {

	private static final Pattern mPattern = Patterns.PHONE;

	private int mErrorMessage;

	public PhoneVerifior(Context c) {
		super(c);
		mErrorMessage = c.getResources().getIdentifier("validator_phone",
				"string", c.getPackageName());
	}

	public PhoneVerifior(Context c, int errorMessage) {
		super(c);
		mErrorMessage = errorMessage;
	}

	@Override
	public boolean isValid(String value) throws VerifiorException {
		return mPattern.matcher(value).matches();
	}

	@Override
	public String getMessage() {
		return mContext.getString(mErrorMessage);
	}
}
