package com.message.aim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.message.aim.model.PreSmsData;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

  // All Static variables
  // Database Version
  private static final int DATABASE_VERSION = 1;

  // Database Name
  private static final String DATABASE_NAME = "SMS";

  // Contacts table name
  private static final String TABLE_SMS = "PRE_SMS";

  // Contacts Table Columns names
  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_CODE = "code";

  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  // Creating Tables
  @Override public void onCreate(SQLiteDatabase db) {
    String CREATE_CONTACTS_TABLE = "CREATE TABLE "
        + TABLE_SMS
        + "("
        + KEY_ID
        + " TEXT PRIMARY KEY,"
        + KEY_NAME
        + " TEXT,"
        + KEY_CODE
        + " TEXT"
        + ")";
    db.execSQL(CREATE_CONTACTS_TABLE);
  }

  // Upgrading database
  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);

    // Create tables again
    onCreate(db);
  }

  public void addSms(PreSmsData contact) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_NAME, contact.getName()); // Contact Name
    values.put(KEY_CODE, contact.getCode()); // Contact Phone

    // Inserting Row
    db.insert(TABLE_SMS, null, values);
    db.close(); // Closing database connection
  }

  // Getting single contact
  PreSmsData getContact(int id) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_SMS, new String[] {
        KEY_ID, KEY_NAME, KEY_CODE
    }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
    if (cursor != null) cursor.moveToFirst();

    PreSmsData contact = new PreSmsData(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
        cursor.getString(2));
    // return contact
    return contact;
  }

  // Getting All Contacts
  public List<PreSmsData> getAllSms() {
    List<PreSmsData> contactList = new ArrayList<PreSmsData>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_SMS;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
      do {
        PreSmsData smsData = new PreSmsData();
        smsData.setId(Integer.parseInt(cursor.getString(0)));
        smsData.setName(cursor.getString(1));
        smsData.setPhoneNumber(cursor.getString(2));
        // Adding contact to list
        contactList.add(smsData);
      } while (cursor.moveToNext());
    }

    // return contact list
    return contactList;
  }

  // Updating single contact
  public int updateSms(PreSmsData preSmsData) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_NAME, preSmsData.getName());
    values.put(KEY_CODE, preSmsData.getCode());

    // updating row
    return db.update(TABLE_SMS, values, KEY_ID + " = ?",
        new String[] { String.valueOf(preSmsData.getId()) });
  }

  // Deleting single contact
  public void deleteSms(PreSmsData preSmsData) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_SMS, KEY_ID + " = ?", new String[] { String.valueOf(preSmsData.getId()) });
    db.close();
  }

  // Getting contacts Count
  public int getSmsCount() {
    String countQuery = "SELECT  * FROM " + TABLE_SMS;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    cursor.close();

    // return count
    return cursor.getCount();
  }
}