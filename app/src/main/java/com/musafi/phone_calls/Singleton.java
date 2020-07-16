package com.musafi.phone_calls;

import android.content.Context;

public class Singleton {
    private static Singleton single_instance = null;

    private Singleton(Context context)
    {
        Manage_contents.readContacts(context);
    }

    public static Singleton getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new Singleton(context);

        return single_instance;
    }
}
