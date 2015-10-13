package com.unrc.app;

import org.javalite.activejdbc.Model;

public class User extends Model {
   	static {
   		validatePresenceOf("nickId");
 	}

   @Override
   	public String toString (){
    	return this.getString("nickId");
	}   
   	public String toString1 (){
    	return this.getString("nameUs");
	}   

	public String toString2(){
 		return this.getString("id");
 	}
}
