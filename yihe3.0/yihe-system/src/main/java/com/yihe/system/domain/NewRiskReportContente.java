// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NewRiskReportContent.java

package com.yihe.system.domain;


public class NewRiskReportContente
{

	private Integer con_id;
	private String conKey;
	private String conValue;
	private Integer pos;

	public Integer getCon_id()
	{
		return con_id;
	}

	public void setCon_id(Integer con_id)
	{
		this.con_id = con_id;
	}

	public String getConKey()
	{
		return conKey;
	}

	public void setConKey(String conKey)
	{
		this.conKey = conKey;
	}

	public String getConValue()
	{
		return conValue;
	}

	public void setConValue(String conValue)
	{
		this.conValue = conValue;
	}

	public Integer getPos()
	{
		return pos;
	}

	public void setPos(Integer pos)
	{
		this.pos = pos;
	}

	public NewRiskReportContente(Integer con_id, String conKey, String conValue, Integer pos)
	{
		this.conKey = "";
		this.conValue = "";
		this.con_id = con_id;
		this.conKey = conKey;
		this.conValue = conValue;
		this.pos = pos;
	}

	public NewRiskReportContente()
	{
		conKey = "";
		conValue = "";
	}
}
