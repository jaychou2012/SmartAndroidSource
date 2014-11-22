package com.tandong.sa.verifi;

import java.util.ArrayList;
import java.util.Iterator;

import android.widget.TextView;


public class Verifi extends AbstractVerifi{

	/**
     * Validator chain
     */
    protected ArrayList<AbstractVerifior> _validators = new ArrayList<AbstractVerifior>();
    
    /**
     * Validation failure messages
     */
    protected String _message = new String();
    
    /**
     * 
     */
    protected TextView _source;
    
    
    public Verifi(TextView source){
    	this._source = source;
    }

    /**
     * Adds a validator to the end of the chain
     *
     * @param validator
     */
    public void addValidator(AbstractVerifior validator)
    {
    	this._validators.add(validator);
    	return;
    }
    
    public boolean isValid(String value){
    	boolean result = true;
    	this._message = new String();
    	
    	Iterator<AbstractVerifior> it = this._validators.iterator();
    	while(it.hasNext()){
    		AbstractVerifior validator = it.next();
            try{
                if(!validator.isValid(value)){
                    this._message = validator.getMessage();
                    result = false;
                    break;
                }
            }catch(VerifiorException e){
                System.err.println(e.getMessage());
                System.err.println(e.getStackTrace());
                this._message = e.getMessage();
                result = false;
                break;
            }
    	}
    	
    	return result;
    }
    
    public String getMessages(){
    	return this._message;
    }
    
    public TextView getSource(){
    	return this._source;
    }
    
}
