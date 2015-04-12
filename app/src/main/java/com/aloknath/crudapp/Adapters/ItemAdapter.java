package com.aloknath.crudapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.aloknath.crudapp.Objects.ItemObject;
import com.aloknath.crudapp.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALOKNATH on 4/12/2015.
 */
public class ItemAdapter extends ArrayAdapter<ItemObject> {

    private Context context;
    private List<ItemObject> itemObjects = new ArrayList<>();
    public ItemAdapter(Context context, int resource, List<ItemObject> itemObjects) {
        super(context, resource, itemObjects);
        this.context = context;
        this.itemObjects = itemObjects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemObject itemObject = itemObjects.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.items_list_display, null);
        ViewHolderItem viewHolderItem = new ViewHolderItem();
        viewHolderItem.item = (TextView)convertView.findViewById(R.id.category_item);
        convertView.setTag(viewHolderItem);

        ViewHolderItem holderItem = (ViewHolderItem) convertView.getTag();
        holderItem.item.setText(itemObject.getName());

        return convertView;
    }

    private static class ViewHolderItem{
        private TextView item;
    }
}
