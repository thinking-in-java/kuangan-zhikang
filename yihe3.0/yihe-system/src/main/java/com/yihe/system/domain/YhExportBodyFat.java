package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportBodyFat implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Org_Name;
	private String name;
	private Integer gender;
	private String User_Code;
	private Float fat_rate;
	private Float muscle;
	private Float bones;
	private Float visceral_level;
	private Float water;
	private Float heat;
	private Float protein;
	private Float basal_met;
	private String Entry_Time;
	public String getOrg_Name() {
		return Org_Name;
	}
	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getUser_Code() {
		return User_Code;
	}
	public void setUser_Code(String user_Code) {
		User_Code = user_Code;
	}
	public Float getFat_rate() {
		return fat_rate;
	}
	public void setFat_rate(Float fat_rate) {
		this.fat_rate = fat_rate;
	}
	public Float getMuscle() {
		return muscle;
	}
	public void setMuscle(Float muscle) {
		this.muscle = muscle;
	}
	public Float getBones() {
		return bones;
	}
	public void setBones(Float bones) {
		this.bones = bones;
	}
	public Float getVisceral_level() {
		return visceral_level;
	}
	public void setVisceral_level(Float visceral_level) {
		this.visceral_level = visceral_level;
	}
	public Float getWater() {
		return water;
	}
	public void setWater(Float water) {
		this.water = water;
	}
	public Float getHeat() {
		return heat;
	}
	public void setHeat(Float heat) {
		this.heat = heat;
	}
	public Float getProtein() {
		return protein;
	}
	public void setProtein(Float protein) {
		this.protein = protein;
	}
	public Float getBasal_met() {
		return basal_met;
	}
	public void setBasal_met(Float basal_met) {
		this.basal_met = basal_met;
	}
	public String getEntry_Time() {
		return Entry_Time;
	}
	public void setEntry_Time(String entry_Time) {
		Entry_Time = entry_Time;
	}
	@Override
	public String toString() {
		return "YhExportBodyFat [Org_Name=" + Org_Name + ", name=" + name + ", gender=" + gender + ", User_Code="
				+ User_Code + ", fat_rate=" + fat_rate + ", muscle=" + muscle + ", bones=" + bones + ", visceral_level="
				+ visceral_level + ", water=" + water + ", heat=" + heat + ", protein=" + protein + ", basal_met="
				+ basal_met + ", Entry_Time=" + Entry_Time + "]";
	}
	
}
