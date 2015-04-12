package com.aloknath.crudapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aloknath.crudapp.R;

/**
 * Created by ALOKNATH on 4/12/2015.
 */
public class AddItemActivity extends Activity {

    private EditText category;
    private EditText itemName;
    private Button cancel;
    private Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_layout);

        category = (EditText)findViewById(R.id.add_category);

        itemName = (EditText)findViewById(R.id.add_item);

        cancel = (Button)findViewById(R.id.cancel);
        upload = (Button)findViewById(R.id.upload);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category.getText().toString().isEmpty() ||  itemName.getText().toString().isEmpty()){
                    Toast.makeText(AddItemActivity.this, "Please Enter Category and ItemName", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("Category", category.getText().toString());
                    bundle.putString("Name", itemName.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }
}
