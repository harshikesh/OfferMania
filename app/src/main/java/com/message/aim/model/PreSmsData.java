package com.message.aim.model;

public class PreSmsData {

  //private variables
  int id;
  String name;
  String code;
  private int mId;
  private String mName;
  private String mPhoneNumber;

  // Empty constructor
  public PreSmsData() {

  }

  // constructor
  public PreSmsData(int id, String name, String code) {
    this.id = id;
    this.name = name;
    this.code = code;
  }

  // constructor
  public PreSmsData(String name, String code) {
    this.name = name;
    this.code = code;
  }

  public int getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    mId = id;
  }

  public void setName(String name) {
    mName = name;
  }

  public void setPhoneNumber(String phoneNumber) {
    mPhoneNumber = phoneNumber;
  }
}