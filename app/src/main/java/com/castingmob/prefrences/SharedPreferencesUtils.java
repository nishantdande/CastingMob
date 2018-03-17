package com.castingmob.prefrences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.castingmob.CastingMob;
import com.castingmob.logger.Logger;

import java.util.Map;

public abstract class SharedPreferencesUtils {

	protected abstract String getFileName();

	Logger logger = new Logger(SharedPreferencesUtils.class.getSimpleName());

//	protected static SharedPreferencesUtils sharedPreferencesUtils;
	protected Editor mEditor = null;
	protected SharedPreferences mSettings = null;

	/**
	 * Init shared preferences
	 *
	 */
	public void initPreferences() {
		if (mSettings == null || mEditor == null) {
			mSettings = CastingMob.getInstance().getContext().getSharedPreferences(getFileName(),
                    Context.MODE_PRIVATE);
			mEditor = mSettings.edit();
		}
	}

	public void setInt(String key, int value) {
		mEditor.putInt(key, value);
		mEditor.commit();
	}

	public void setLong(String key, long value) {
		mEditor.putLong(key, value);
		mEditor.commit();
	}

	public void setString(String key, String value) {
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public String getString(String key, String defValue) {
		return mSettings.getString(key, defValue);
	}

	public int getInt(String key, int defValue) {
		return mSettings.getInt(key, defValue);
	}

	public long getLong(String key, int defValue) {
		return mSettings.getLong(key, defValue);
	}

	public void setBoolean(String key, boolean value) {
		mEditor.putBoolean(key, value);
		mEditor.commit();
	}

	public boolean getBoolean(String key, boolean defValue) {
		return mSettings.getBoolean(key, defValue);
	}

    public void clearKey(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

	public void clearAll() {
		mEditor.clear();
		mEditor.commit();
	}

	public void printAll(){
		Map<String,?> keys = mSettings.getAll();

		for(Map.Entry<String,?> entry : keys.entrySet()){
			logger.debug(getFileName() + "map values " + entry.getKey() + ": " +
					entry.getValue().toString());
		 }		
	}
}
