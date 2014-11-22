package com.tandong.sa.verifi;

import java.util.regex.Pattern;

import android.content.Context;


public class HexVerifior extends AbstractVerifior {

    /**
     * This is Hex Pattern to verify value.
     */
    private static final Pattern mPattern = Pattern.compile("^(#|)[0-9A-Fa-f]+$");

    private int mErrorMessage;

    public HexVerifior(Context c) {
        super(c);
        mErrorMessage=c.getResources().getIdentifier("validator_alnum", "string",
				c.getPackageName());
    }

    public HexVerifior(Context c, int errorMessage) {
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
