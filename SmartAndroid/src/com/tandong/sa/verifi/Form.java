package com.tandong.sa.verifi;

import java.util.ArrayList;
import java.util.Iterator;

import android.widget.TextView;

/**
 * Form Validation Class
 *
 * Immediately, only works with EditText
 * 
 * @author throrin19
 * 
 * @version 1.0
 *
 */
public class Form {

	protected ArrayList<AbstractVerifi> _validates = new ArrayList<AbstractVerifi>();
	
	/**
	 * Function adding Validates to our form
	 * @param validate
     *   {@link AbstractVerifi} Validate to add
	 */
	public void addValidates(AbstractVerifi validate){
		this._validates.add(validate);
		return;
	}
	
	/**
	 * Called to validate our form.
     * If an error is found, it will be displayed in the corresponding field.
	 * @return
	 * 		boolean :   true if the form is valid
     *                  false if the form is invalid
	 */
	public boolean validate(){
		boolean result = true;
		Iterator<AbstractVerifi> it = this._validates.iterator();
		while(it.hasNext()){
			AbstractVerifi validator = it.next();
			TextView field = validator.getSource();
			field.setError(null);
			if(!validator.isValid(field.getText().toString())){
				result = false;
				field.setError(validator.getMessages());
			}
		}
		return result;
	}
}
