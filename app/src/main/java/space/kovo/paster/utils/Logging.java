package space.kovo.paster.utils;

import android.util.Log;

public class Logging {

    public static void log(String description, String message) {
        Log.d(String.format("----- %s: ", description), message);
    }
}
