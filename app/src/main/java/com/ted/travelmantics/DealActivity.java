package com.ted.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.InternalTokenResult;

public class DealActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText textTitle;
    EditText textDescription;
    EditText textPrice;
    TravelDeal deal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        FirebaseUtil.openFbReference("traveldeals");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        textTitle = (EditText) findViewById(R.id.text_title);
        textDescription =(EditText) findViewById(R.id.text_description);
        textPrice = (EditText) findViewById(R.id.text_price);
        Intent intent = getIntent();
        TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");
        if (deal == null) {
            deal = new TravelDeal();

        }
        this.deal = deal;
        textTitle.setText(deal.getTitle());
        textDescription.setText(deal.getDescription());
        textPrice.setText(deal.getPrice());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;
            case R.id.delete_deal:
                deleteDeal();
                Toast.makeText(this, "Deal deleted sucessfully", Toast.LENGTH_LONG).show();
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void clean() {
        textTitle.setText("");
        textDescription.setText("");
        textPrice.setText("");
        textTitle.requestFocus();

    }

    private void saveDeal() {
        deal.setTitle(textTitle.getText().toString());
        deal.setDescription(textDescription.getText().toString());
        deal.setPrice(textPrice.getText().toString());
        if (deal.getId() == null) {
            mDatabaseReference.push().setValue(deal);
        }
        else {
            mDatabaseReference.child(deal.getId()).setValue(deal);

        }

    }
    private void deleteDeal() {
        if (deal == null) {
            Toast.makeText(this, "Please save the deal before deleting", Toast.LENGTH_SHORT).show();
            return;

        }
        mDatabaseReference.child(deal.getId()).removeValue();
    }
    private void backToList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }
}
