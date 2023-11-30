package com.example.nhom13_appbanhaisan;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String SHARED_PREF_NAME = "my_shared_preferences";
    private static final String KEY_SHARED_VALUE = "key_shared_value";

    private static SharedPreferenceManager instance;
    private SharedPreferences sharedPreferences;

    private SharedPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public String getSharedValue() {
        return sharedPreferences.getString(KEY_SHARED_VALUE, "");
    }

    public void setSharedValue(String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SHARED_VALUE, value);
        editor.apply();

        // Thông báo cho các lắng nghe (listeners) về sự thay đổi giá trị
        notifyListeners();
    }

    // Các phần liên quan đến lắng nghe sự kiện thay đổi giá trị
    interface OnSharedValueChangeListener {
        void onSharedValueChange(String newValue);
    }

    private OnSharedValueChangeListener listener;

    public void setOnSharedValueChangeListener(OnSharedValueChangeListener listener) {
        this.listener = listener;
    }

    private void notifyListeners() {
        if (listener != null) {
            listener.onSharedValueChange(getSharedValue());
        }
    }
}
