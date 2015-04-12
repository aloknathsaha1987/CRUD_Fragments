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
public class EditItemActivity extends Activity {

    private EditText category;
    private EditText itemName;
    private Button cancel;
    private Button update;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        Bundle b = getIntent().getExtras();

        id = b.getInt("id");

        category = (EditText)findViewById(R.id.edit_category);
        category.setText(b.getString("Category"));

        itemName = (EditText)findViewById(R.id.edit_item);
        itemName.setText(b.getString("Name"));

        cancel = (Button)findViewById(R.id.cancel);
        update = (Button)findViewById(R.id.update);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category.getText().toString().isEmpty() ||  itemName.getText().toString().isEmpty()){
                    Toast.makeText(EditItemActivity.this, "Please Enter Category and ItemName", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("Category", category.getText().toString());
                    bundle.putString("Name", itemName.getText().toString());
                    bundle.putInt("id",id);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
