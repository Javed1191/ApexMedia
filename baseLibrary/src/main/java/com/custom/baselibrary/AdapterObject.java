package com.custom.baselibrary;

public class AdapterObject 
{
	public String mObjName;
	public String mObjCode;
	
	public AdapterObject(String pObjName)
	{
		this.mObjName = pObjName;
	}
	
	public AdapterObject(String pObjCode, String pObjName)
	{
		this.mObjCode = pObjCode;
		this.mObjName = pObjName;
	}
}
