package com.example.tha_204112F.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tha_204112F.database.models.Item;
import com.example.tha_204112F.database.models.ItemDAO;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


@SuppressWarnings("ALL")
@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class THA_DB extends RoomDatabase {

    private static THA_DB database;
    public abstract ItemDAO DAO();

    private static final String DB_NAME = "THA_204112F_DATABASE";



    public static synchronized THA_DB getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    THA_DB.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDbCallback)
                    .build();
        }
        Logger.d("Database Created");
        return database;

    }



    private static final RoomDatabase.Callback roomDbCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(database).execute();
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        PopulateDbAsyncTask(THA_DB instance){
            ItemDAO itemDAO = instance.DAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
