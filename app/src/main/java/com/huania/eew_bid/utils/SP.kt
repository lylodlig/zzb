package com.huania.eew_bid.utils

import com.tencent.mmkv.MMKV

object SP {

    fun putString(key: String, value: String?) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun putInt(key: String, value: Int?) {
        if (value == null) return
        MMKV.defaultMMKV().encode(key, value)
    }

    fun putBoolean(key: String, value: Boolean?) {
        if (value == null) return
        MMKV.defaultMMKV().encode(key, value)
    }

    fun putFloat(key: String, value: Float?) {
        if (value == null) return
        MMKV.defaultMMKV().encode(key, value)
    }

    fun putLong(key: String, value: Long?) {
        if (value == null) return
        MMKV.defaultMMKV().encode(key, value)
    }

    fun putStringSet(key: String, value: Set<String>?) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getString(key: String, defaultValue: String): String? {
        val string = MMKV.defaultMMKV().decodeString(key, defaultValue)
        if (string.isNullOrBlank())
            return ""
        return string
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return MMKV.defaultMMKV().decodeInt(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return MMKV.defaultMMKV().decodeBool(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return MMKV.defaultMMKV().decodeFloat(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return MMKV.defaultMMKV().decodeLong(key, defaultValue)
    }

    fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? {
        return MMKV.defaultMMKV().decodeStringSet(key, defaultValue)
    }

    fun getString(key: String): String {
        val string = MMKV.defaultMMKV().decodeString(key)
        if (string.isNullOrBlank())
            return ""
        return string
    }

    fun getInt(key: String): Int {
        return MMKV.defaultMMKV().decodeInt(key)
    }

    fun getBoolean(key: String): Boolean {
        return MMKV.defaultMMKV().decodeBool(key)
    }

    fun getFloat(key: String): Float {
        return MMKV.defaultMMKV().decodeFloat(key)
    }

    fun getLong(key: String): Long {
        return MMKV.defaultMMKV().decodeLong(key)
    }

    fun getStringSet(key: String): Set<String>? {
        return MMKV.defaultMMKV().decodeStringSet(key)
    }

    fun remove(key: String) {
        MMKV.defaultMMKV().removeValueForKey(key)
    }

    fun clear() {
        MMKV.defaultMMKV().clearAll()
    }

}
