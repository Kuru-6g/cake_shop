package com.example.tha_204112F.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tha_204112F.database.models.Item;
import com.example.tha_204112F.database.models.ItemDAO;

import java.util.List;

@SuppressWarnings("ALL")
public class ItemRepository {

    private final ItemDAO itemDao;
    private final LiveData<List<Item>> itemList;

    // Constructor initializing the database and DAO
    public ItemRepository(Application application){
        THA_DB database = THA_DB.getInstance(application);
        itemDao = database.DAO();
        itemList = itemDao.getItems();
    }

    // Getter for LiveData item list
    public LiveData<List<Item>> getItemList() {
        return itemList;
    }

    // Method to insert an item into the database
    public void insertItem(Item item){
        new InsertItem(itemDao).execute(item);
    }

    // Method to update an item in the database
    public void updateItem(Item item){
        new UpdateItem(itemDao).execute(item);
    }

    // Method to delete an item from the database
    public void deleteItem(Item item){
        new DeleteItem(itemDao).execute(item);
    }

    // AsyncTask to insert an item asynchronously
    private static class InsertItem extends AsyncTask<Item, Void, Void>{
        private final ItemDAO itemDao;

        private InsertItem(ItemDAO itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.addItem(items[0]); // Insert the item
            return null;
        }
    }

    // AsyncTask to update an item asynchronously
    private static class UpdateItem extends AsyncTask<Item, Void, Void>{
        private ItemDAO itemDao;

        private UpdateItem(ItemDAO itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.updateItem(items[0]); // Update the item
            return null;
        }
    }

    // AsyncTask to delete an item asynchronously
    private static class DeleteItem extends AsyncTask<Item, Void, Void>{
        private ItemDAO itemDao;

        private DeleteItem(ItemDAO itemDao){
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.deleteItem(items[0]); // Delete the item
            return null;
        }
    }
}
