package com.message.aim.model;

import android.net.Uri;

/**
 * Created by harshikesh.kumar on 10/09/16.
 */
public class SmsObject {

  private String subject = null;
  private String threadId = null;
  private String date = null;
  private String body = null;
  private String type = null;
  private String seen = null;

  public SmsObject(String sub, String bod, String dat, String s) {
    subject = sub;
    body = bod;
    date = dat;
    seen = s;
  }

  public String getSubject() {
    return subject;
  }

  public String getDate() {
    return date;
  }

  public String getMsg() {
    return body;
  }

  public String getSeen() {
    return seen;
  }
}
