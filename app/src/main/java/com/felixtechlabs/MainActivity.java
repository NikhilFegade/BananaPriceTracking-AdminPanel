package com.felixtechlabs;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Boolean permission = false;
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 99;

    FirebaseDatabase database;
    DatabaseReference rootRef;
    Context context;
    final List<BananaPriceModel> pricelist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference();

        //context=getApplicationContext();

        saveSmsToFirebase();


        DatabaseReferenceManager.getInstance().getBananaRatesRef().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                BananaPriceModel bananaPriceModel2 = dataSnapshot.getValue(BananaPriceModel.class);

                BananaPriceAdapter bananaPriceAdapter = new BananaPriceAdapter(pricelist, getBaseContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recyclerView.setAdapter(bananaPriceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast toast = new Toast(MainActivity.this,"Something is wrong..!!",Toast.LENGTH_SHORT).show();

            }

        });


    }

    public boolean saveSmsToFirebase() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = checkSmsPermission();
            if (permission) {

                BananaPriceModel bananaPriceModel;
                final String num = "+919359144831";
                Uri uriSms = Uri.parse("content://sms/inbox");
                final Cursor cursor = getContentResolver().query(uriSms,
                        new String[]{"_id", "address", "date", "body"}, null, null, null);

                while (cursor.moveToNext()) {
                    String address = cursor.getString(1);
                    if (address.equals(num)) {
                        String msg = cursor.getString(3);
                        String smsDate = cursor.getString(2);
                        bananaPriceModel = new BananaPriceModel();
                        bananaPriceModel.setBody(msg);
                        bananaPriceModel.setSmsDate(smsDate);
                        bananaPriceModel.setCreatedAt(System.currentTimeMillis());
                        bananaPriceModel.setSortCreatedAt(-System.currentTimeMillis());
                        pricelist.add(bananaPriceModel);


                        DatabaseReferenceManager.getInstance().getBananaRatesRef().child(String.valueOf(smsDate)).setValue(bananaPriceModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                }
            }
        }


//  BananaPriceModel bananaPriceModel1 =  pricelist.get(pricelist.size() - 1);
    return true;
    }

    public boolean checkSmsPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_SMS)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.READ_SMS)
                            == PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
