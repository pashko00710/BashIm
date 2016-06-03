package com.example.bashim.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = BashImDataBase.NAME, version = BashImDataBase.VERSION)
public class BashImDataBase {
    public static final String NAME = "Recordings";
    public static final int VERSION = 1;
}
