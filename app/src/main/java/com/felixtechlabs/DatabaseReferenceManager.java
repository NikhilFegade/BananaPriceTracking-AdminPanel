package com.felixtechlabs;

import android.os.Build;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseReferenceManager {

    private static DatabaseReferenceManager instance;
    private  final String DATABASE_ROOT;

    public static DatabaseReferenceManager getInstance(){
        if(instance == null){
            instance = new DatabaseReferenceManager();
        }
        return instance;
    }

    private DatabaseReferenceManager(){
        if(BuildConfig.DEBUG){
            DATABASE_ROOT = ReferenceKeys.DEVELOPMENT;
        } else {
            DATABASE_ROOT = ReferenceKeys.PRODUCTION;
        }
    }

    public DatabaseReference getRoot(){
        return FirebaseDatabase.getInstance().getReference().child(DATABASE_ROOT);
    }

    public DatabaseReference getBananaRatesRef(){
        return getRoot().child(ReferenceKeys.BANANA_RATES);
    }








}
