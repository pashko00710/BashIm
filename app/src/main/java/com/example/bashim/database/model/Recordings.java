package com.example.bashim.database.model;

import com.example.bashim.database.BashImDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.annotation.UniqueGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = BashImDataBase.class, uniqueColumnGroups = {@UniqueGroup(groupNumber = 1,
        uniqueConflict = ConflictAction.IGNORE)})
public class Recordings extends BaseModel {
    @PrimaryKey(autoincrement = true)
    long id;

    @Unique(unique = false, uniqueGroups = 1)
    @Column
    String html;

    @Unique(unique = false, uniqueGroups = 1)
    @Column
    boolean favorites = false;

    public static List<Recordings> getAllRecordings(int count) {

        return SQLite.select()
                .from(Recordings.class)
                .where(Recordings_Table.favorites.is(false))
                .orderBy(Recordings_Table.id, false)
                .limit(count)
                .queryList();
    }

    public static List<Recordings> getAllFavoriteRecordings(int count) {

        return SQLite.select()
                .from(Recordings.class)
                .where(Recordings_Table.favorites.is(true))
                .limit(count)
                .queryList();
    }

    public static void addToFavorites(long id) {

        SQLite.update(Recordings.class)
                .set(
                        Recordings_Table.favorites.eq(true)
                )
                .where(Recordings_Table.id.is(id)).query();
    }

    public static void removeFavorites(long id) {
        SQLite.update(Recordings.class)
                .set(Recordings_Table.favorites.eq(false))
                .where(Recordings_Table.id.is(id)).query();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }
}
