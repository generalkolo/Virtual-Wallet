package com.semanienterprise.virtualwallet.local.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.semanienterprise.virtualwallet.local.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.semanienterprise.virtualwallet.local.utils.Constants.DB_NAME;

/**
 * Created by edetebenezer on 05/12/2019
 **/
@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

    private static UserDatabase noteDB;

    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserDatabase getInstance(Context context) {
        if (null == noteDB) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static UserDatabase buildDatabaseInstance(Context context) {
        if (noteDB == null) {
            noteDB = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return noteDB;
    }

    public static void destroyDb() {
        noteDB = null;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                UserDao dao = noteDB.getUserDao();

                User userOne = new User();
                userOne.accountBalance = 200.0;
                userOne.password = "1234";
                userOne.userName = "userOne";

                User userTwo = new User();
                userTwo.accountBalance = 600.0;
                userTwo.password = "4321";
                userTwo.userName = "userTwo";

                dao.insertUser(userOne);
                dao.insertUser(userTwo);
            });
        }
    };
}
