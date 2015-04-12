package com.aloknath.crudapp.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.aloknath.crudapp.Activities.MainActivity;
import com.aloknath.crudapp.Adapters.ItemAdapter;
import com.aloknath.crudapp.Objects.ItemObject;
import com.aloknath.crudapp.R;
import java.util.List;

/**
 * Created by ALOKNATH on 4/12/2015.
 */
public class CategoryFragment extends Fragment {

    private List<ItemObject> itemObjects;
    private ItemAdapter adapter;
    private ListView itemListView;
    private onChildEvent childEventListener;

    public static CategoryFragment newInstance(String category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("Category", category);
        fragment.setArguments(args);
        return fragment;
    }

    public interface onChildEvent{
        void itemPassed(String category, String itemName, int id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            childEventListener = (onChildEvent)activity;
        } catch(ClassCastException e)
        {
            Log.e("ClassCastException in ChildFragment ", activity.toString() + " must implement onChildEvent");
            e.printStackTrace();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, null);
        itemObjects = MainActivity.categoryMap.get((String)getArguments().get("Category"));
        TextView textView = (TextView)view.findViewById(R.id.category);
        textView.setText((String) getArguments().get("Category"));
        Log.i(" The Fragment Name: ", (String) getArguments().get("Category"));
        for (ItemObject itemObject : itemObjects) {
            Log.i(" The Items in Fragment: ", itemObject.getCategory());
        }
        adapter = new ItemAdapter(getActivity(), R.layout.items_list_display, itemObjects);
        itemListView = (ListView)view.findViewById(R.id.itemListView);
        itemListView.setAdapter(adapter);
        itemListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setListViewHeightBasedOnChildren(itemListView);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("ItemObject Clicked: ", itemObjects.get(position).getName());
                // Pass the ItemClicked Details to the UnderLying activity.
                ItemObject itemObject = itemObjects.get(position);
                childEventListener.itemPassed(itemObject.getCategory(), itemObject.getName(), itemObject.getId());
            }
        });

        return view;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
