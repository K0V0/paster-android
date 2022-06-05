package space.kovo.paster.services.sharedPreferencesService;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesServiceImpl implements SharedPreferencesService {

    private static final String PREFERENCES_KEY = "MainPreferences";
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPreferencesServiceImpl(Context context) {
        this.context = context;
        /** 0 = private mode */
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
        this.editor = sharedPreferences.edit();
    }

    @Override
    public void save(String key, String data) {
        editor.putString(key, data);
        editor.apply();
    }

    @Override
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }
}
