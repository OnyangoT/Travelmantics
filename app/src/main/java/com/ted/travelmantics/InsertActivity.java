package com.ted.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    EditText textTitle;
    EditText textPrice;
    EditText textDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        FirebaseUtil.openFbReference("traveldeals");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        textTitle = (EditText) findViewById(R.id.text_title);
        textPrice = (EditText) findViewById(R.id.text_price);
        textDescription = (EditText) findViewById(R.id.text_description);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
                clean();
                return true;
             default:
                 return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }
    public void saveDeal () {
        String title = textTitle.getText().toString();
        String price = textPrice.getText().toString();
        String description = textDescription.getText().toString();
        TravelDeal deal = new TravelDeal(title, price, description, "");
        mDatabaseReference.push().setValue(deal);
    }
    public void clean () {
        textTitle.setText("");
        textPrice.setText("");
        textDescription.setText("");
        textTitle.requestFocus();
    }
}
