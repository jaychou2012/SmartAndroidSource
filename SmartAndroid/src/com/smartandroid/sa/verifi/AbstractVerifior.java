package com.smartandroid.sa.verifi;

import android.content.Context;

/**
 * Class for creating new Validators
 * @author throrin19
 *
 */
public abstract class AbstractVerifior {

	protected Context mContext;
	
	public AbstractVerifior(Context c){
		mContext = c;
	}
	
	/**
	 * Can check if the value passed in parameter is valid or not.
	 * @param value
	 * 		{@link String} : the value to validate
	 * @return
	 * 		boolean : true if valid, false otherwise.
	 */
	public abstract boolean isValid(String value) throws VerifiorException;
	
	/**
	 * Used to retrieve the error message corresponding to the validator.
	 * @return
	 * 		String : the error message
	 */
	public abstract String getMessage();
}
