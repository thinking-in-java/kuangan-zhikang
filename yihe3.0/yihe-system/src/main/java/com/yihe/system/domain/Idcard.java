package com.yihe.system.domain;

public class Idcard {
	 private String address;
	 private String id_card_number;
	 private String race;
	 private String gender;
	 private String side;
	 private String name;
	 private String birthday;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getId_card_number() {
		return id_card_number;
	}
	public void setId_card_number(String id_card_number) {
		this.id_card_number = id_card_number;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "Idcard [address=" + address + ", id_card_number=" + id_card_number + ", race=" + race + ", gender="
				+ gender + ", side=" + side + ", name=" + name + ", birthday=" + birthday + "]";
	}

	 
}
