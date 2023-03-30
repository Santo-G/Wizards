package com.santog.wizards.utils

import android.content.Context
import android.content.SharedPreferences

class WizardsSharedPreferences(context: Context) {

    companion object {
        const val LAST_NETWORK_LOOKUP: String = "last_network_lookup"
        const val CHARACTERS_CACHE_TIME: String = "characters_cache_time"
    }

    private val mContext = context.applicationContext
    private val mSharedPreferences: SharedPreferences =
        context.getSharedPreferences("WIZARDS_CHARACTERS_PREF", Context.MODE_PRIVATE)
    private val mEditor: SharedPreferences.Editor = mSharedPreferences.edit()


    fun getStringPreference(key: String): String? {
        return mSharedPreferences.getString(key, null)
    }

    fun setStringPreference(key: String, value: String) {
        mEditor.putString(key, value)
        mEditor.commit()
    }

    fun getLongPreference(key: String): Long {
        return mSharedPreferences.getLong(key, -1)
    }

    fun setLongPreference(key: String, value: Long) {
        mEditor.putLong(key, value)
        mEditor.commit()
    }
}
