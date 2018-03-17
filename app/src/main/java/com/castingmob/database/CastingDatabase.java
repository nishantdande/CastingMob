package com.castingmob.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.castingmob.CastingMob;
import com.castingmob.database.impl.ConversationInfo;
import com.castingmob.database.impl.MessageInfo;
import com.castingmob.database.impl.ParticipantsInfo;
import com.castingmob.logger.Logger;

import java.lang.ref.SoftReference;


/**
 * Created by nishant on 13/02/16.
 */
public class CastingDatabase {

    private Logger logger = new Logger(CastingDatabase.class.getSimpleName());

    private final static CastingDatabase CASTING_DATABASE = new CastingDatabase();

    public static CastingDatabase getInstance() {
        return CASTING_DATABASE;
    }

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    // Database Name
    private static final String DATABASE_NAME = "casting.db";
    // Database Version
    private static final int DATABASE_VERSION = 1;


    /**
     * Create Database
     */
    public void createDatabase(){
        if (mDatabaseHelper == null)
            mDatabaseHelper = new DatabaseHelper(CastingMob.getInstance().getContext());
    }

    /**
     * Open database with write permission
     * @return
     * @throws SQLException
     */
    public SQLiteDatabase openDb() throws SQLException {
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        return  mSqLiteDatabase;
    }

    /**
     * Close Database
     */
    public void close() {
        if (databaseStatus()){
            mDatabaseHelper.close();
        }
    }

    /**
     * Return true is database is open
     * @return
     */
    synchronized public boolean databaseStatus(){
        if (mSqLiteDatabase != null){
            return mSqLiteDatabase.isOpen();
        }
        return false;
    }

    /**
     * function used to insert the value in Sqlite tables
     * @param tableName
     * @param contentValues
     * @return
     */
    synchronized public long insertRecords(String tableName,ContentValues contentValues){
        openDb();
        long id = mSqLiteDatabase.insert(tableName, null, contentValues);
        if (id != -1) {
            logger.debug("Row inserted successfully");
            logger.debug("INSERT INTO " + tableName + " VALUES (" + contentValues + ")");
        }
        else
            logger.error(new Exception("Row insertion failed"));
        close();
        return id;
    }

    /**
     * function used to get Records
     * @param tablename - Table from which we have to Fetch data
     * @param fields - selected fields
     * @param condition- mentioned conditions
     * @param orderLimit - limit
     * @return Cursor- Contain data which are fetched
     */
    synchronized public Cursor getRecords(String tablename,String[] fields,String condition,String orderLimit) throws SQLException {
        openDb();
        Cursor mCursor =
                mSqLiteDatabase.query(false, tablename,
                        fields,
                        condition,
                        null,
                        null,
                        null,
                        null,
                        orderLimit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        close();
        return mCursor;
    }

    /**
     * function used to get Records
     * @param tablename - Table from which we have to Fetch data
     * @param fields - selected fields
     * @param condition- mentioned conditions
     * @param orderLimit - limit
     * @return Cursor- Contain data which are fetched
     */
    synchronized public Cursor getRecords(String tablename,String[] fields,String condition,String orderLimit, String orderBy) throws SQLException {
        openDb();
        Cursor mCursor =
                mSqLiteDatabase.query(false, tablename,
                        fields,
                        condition,
                        null,
                        null,
                        null,
                        orderBy,
                        orderLimit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        close();
        return mCursor;
    }

    /**
     * Execute Raw query
     * @param query
     * @param args
     * @return
     */
    synchronized public Cursor executeRawQuery(String query, String[] args){
        openDb();
        Cursor cursor = mSqLiteDatabase.rawQuery(query, args);
        close();
        return cursor;
    }

    /**
     * Execute Raw query
     * @param query
     * @param args
     * @return
     */
    synchronized public boolean isRecordExist(String query, String[] args){
        boolean isExists = false;
        openDb();
        Cursor cursor = mSqLiteDatabase.rawQuery(query, args);
        if (cursor != null){
            if (cursor.getCount()>0){
                isExists = true;
            }
        }
        close();
        return isExists;
    }

    /**
     * Update Record
     * @param tableName
     * @param contentValues
     * @param where
     * @param args
     * @return
     */
    synchronized public long updateRecord(String tableName,ContentValues contentValues, String where, String[] args){
        openDb();
        long id = mSqLiteDatabase.update(tableName, contentValues, where,args);
        if (id != -1) {
            logger.debug("Row updated successfully");
        }
        else
            logger.error(new Exception("Row update failed"));
        close();
        return id;
    }

    /**
     * Database Helper class to create database
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create user table
            db.execSQL(MessageInfo.SQL_CREATE_MESSAGE);
            db.execSQL(ConversationInfo.SQL_CREATE_CONVERSATION);
            db.execSQL(ParticipantsInfo.SQL_CREATE_PARTICIPANTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MessageInfo.TABLE_MESSAGE);
            db.execSQL("DROP TABLE IF EXISTS " + ConversationInfo.TABLE_CONVERSATION);
            db.execSQL("DROP TABLE IF EXISTS " + ParticipantsInfo.SQL_CREATE_PARTICIPANTS);
        }
    }
}
