package com.aloknath.crudapp.Activities;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.aloknath.crudapp.Fragments.CategoryFragment;
import com.aloknath.crudapp.HttpManager.HttpManager;
import com.aloknath.crudapp.Objects.ItemObject;
import com.aloknath.crudapp.Parser.JSONParser;
import com.aloknath.crudapp.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActionBarActivity implements CategoryFragment.onChildEvent{

    private ProgressDialog progressDialog;
    private List<ItemObject> itemObjects;
    public static Map<String, List<ItemObject>> categoryMap = new HashMap<>();
    private HashSet<String> categoryNames = new HashSet<>();
    public static int ACTIVITY_RESULT_CODE = 1001;
    public static int ADD_ITEM_CODE = 1002;
    private Toolbar toolbar;
    private SwipeRefreshLayout mSwipeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isOnline()) {
            progressDialog = new ProgressDialog(MainActivity.this);
            toolbar = (Toolbar) findViewById(R.id.include);
            setSupportActionBar(toolbar);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8C000000")));
            getSupportActionBar().setTitle("CRUD Application");
            refreshDisplay();

            mSwipeView = (SwipeRefreshLayout)findViewById(R.id.swipe_view);

            mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override public void onRefresh() {
                    refreshDisplay();
                }
            });

        }else{
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void refreshDisplay() {
        GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask();
        getDataAsyncTask.execute();
    }

    @Override
    public void itemPassed(String category, String itemName, int id) {
        Log.i("The ItemObject Passed Back: ", category + ":" + itemName);
        // Allow the ItemObject to be edited, i.e,
        Bundle b = new Bundle();
        b.putString("Category", category);
        b.putString("Name", itemName);
        b.putInt("id", id);
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtras(b);
        startActivityForResult(intent, ACTIVITY_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b;
        if (requestCode == ACTIVITY_RESULT_CODE && resultCode == RESULT_OK) {

            b = data.getExtras();
            String category = b.getString("Category");
            String itemName = b.getString("Name");
            int id = b.getInt("id");
            String action = b.getString("action");
            Log.i("The Updated Category and ItemName: ", category + ":" + itemName);
            // Call the PutDataAsyncTask
            if(action.equals("update")) {
                PutDataAsyncTask putDataAsyncTask = new PutDataAsyncTask(category, itemName, id);
                putDataAsyncTask.execute();
            }else{
                DeleteDataAsyncTask putDataAsyncTask = new DeleteDataAsyncTask(id);
                putDataAsyncTask.execute();
            }
        }else if(requestCode == ADD_ITEM_CODE && resultCode == RESULT_OK){

            b = data.getExtras();
            String category = b.getString("Category");
            String itemName = b.getString("Name");
            PostDataAsyncTask postDataAsyncTask = new PostDataAsyncTask(category, itemName);
            postDataAsyncTask.execute();
        }

    }

    private class DeleteDataAsyncTask extends AsyncTask<Void,Void, Void>{

       private final int id;

        public DeleteDataAsyncTask(int id){
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Fetching Data and Creating Objects");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpManager.deleteData(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            refreshDisplay();
        }

    }

    private class PostDataAsyncTask extends AsyncTask<Void,Void, Void>{

        private final String category;
        private final String itemName;


        public PostDataAsyncTask(String category, String itemName){
            this.category = category;
            this.itemName = itemName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Fetching Data and Creating Objects");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpManager.postData(category, itemName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            refreshDisplay();
        }

    }


    private class PutDataAsyncTask extends AsyncTask<Void,Void, Void>{

        private final String category;
        private final String itemName;
        private final int id;

        public PutDataAsyncTask(String category, String itemName, int id){
            this.category = category;
            this.itemName = itemName;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Fetching Data and Creating Objects");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String content = HttpManager.putData(category, itemName, id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            refreshDisplay();
        }

    }

    private class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Fetching Data and Creating Objects");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String content = HttpManager.getData("https://czshopper.herokuapp.com/items.json");
            itemObjects = JSONParser.parseFeed(content);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();

            String previousCategory = "";
            List<ItemObject> list = new ArrayList<>();
            int itemsCount = itemObjects.size();
            int i = 1;

            for(ItemObject itemObject : itemObjects){
                Log.i(" ItemObject: ", itemObject.toString());
                //To get Unique category Names
                categoryNames.add(itemObject.getCategory());
                i++;
                // Group the Items into their respective categories
                // Decorations, Hardware, Repairs, Snacks, Beverages
                // Use a hashMap <String, List<ItemObject>> = <Category Name, Category Items>
                // For a particular Category, update the list Items

                // Check the itemObject category
                if(itemObject.getCategory().equals(previousCategory)){
                    // Add it to the particular Category List of Items
                    list.add(itemObject);
                    if(i == itemsCount){
                        categoryMap.put(previousCategory, list);
                    }
                }else{
                    if(previousCategory.equals("")){
                        // Do Nothing
                    }else{
                        categoryMap.put(previousCategory, list);
                    }
                    previousCategory = itemObject.getCategory();
                    list = new ArrayList<>();
                    list.add(itemObject);

                    if(i == itemsCount){
                        categoryMap.put(previousCategory, list);
                    }
                }
            }

            // Check if the HashMap is getting populated correctly

            Iterator<String> iterator = categoryNames.iterator();
            String category;

            while(iterator.hasNext()){
                category = iterator.next();
                Log.i("The Category Name: ", category);
                if(categoryMap.size()>0) {
                    list = categoryMap.get(category);
                    if (list.size() > 0) {
                        for (ItemObject itemObject : list) {
                            Log.i(" The Category Name in Map List: ", itemObject.getCategory());
                        }
                    } else {
                        Log.i("The map is not getting populated correctly", "");
                    }
                }else{
                    Log.i("The map size is null", "");
                }
            }

            // Pass The List Items for a particular Category to the respective fragments.

            iterator = categoryNames.iterator();

            while(iterator.hasNext()) {
                category = iterator.next();

                android.app.FragmentManager fm = getFragmentManager();
                CategoryFragment fragmentCategory = new CategoryFragment();
                Fragment fragment = fm.findFragmentByTag(category);

                if(fragment == null){
                    fm.beginTransaction().add(R.id.content, fragmentCategory.newInstance(category), category).commit();

                }else{
                    fm.beginTransaction().remove(fragment).commit();
                    fm.beginTransaction().add(R.id.content,fragmentCategory.newInstance(category), category ).commit();
                }

            }
            mSwipeView.setRefreshing(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds itemObjects to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_item) {
            Intent intent = new Intent(this, AddItemActivity.class);
            startActivityForResult(intent, ADD_ITEM_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
