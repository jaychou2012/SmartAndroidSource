package com.tandong.sa.verifi;

import android.content.Context;
import android.widget.TextView;

/**
 * Validator class to validate if the fields are empty fields of 2 or not.
 * If one of them is null, no error.
 * If both are nulls: Error
 * @author throrin19
 *
 */
public class OrTwoRequiredVerifi extends AbstractVerifi {

	private TextView _field1;
	private TextView _field2;
	private Context mContext;
	
	public OrTwoRequiredVerifi(TextView field1, TextView field2){
		this._field1 = field1;
		this._field2 = field2;
		source = _field1;
		mContext = field1.getContext();
	}
	
	private TextView source;
	
	private String _errorMessage;

	@Override
	public boolean isValid(String value) {
		Verifi field1Validator = new Verifi(_field1);
		field1Validator.addValidator(new NotEmptyVerifior(mContext));
		
		Verifi field2Validator = new Verifi(_field2);
		field2Validator.addValidator(new NotEmptyVerifior(mContext));
		
		if(field1Validator.isValid(_field1.getText().toString()) || field2Validator.isValid(_field2.getText().toString())){
			return true;
		}else{
			_errorMessage = field1Validator.getMessages();
			return false;
		}
	}


	@Override
	public String getMessages() {
		// TODO Auto-generated method stub
		return _errorMessage;
	}


	@Override
	public void addValidator(AbstractVerifior validator) {
	}

	@Override
	public TextView getSource() {
		return source;
	}
	
	
}
