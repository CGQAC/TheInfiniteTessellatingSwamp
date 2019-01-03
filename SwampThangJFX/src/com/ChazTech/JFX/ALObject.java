package com.ChazTech.JFX;

import java.util.ArrayList;

public class ALObject {
	ArrayList<String> myAL = new ArrayList<String>();
	
	public ALObject (ArrayList<String> myAL) {
		this.myAL = myAL;
	}
	public void setALObject (ArrayList<String> myAL) {
		this.myAL = myAL;
	}
	public ArrayList<String> getALObject () {
		return myAL;
	}
}