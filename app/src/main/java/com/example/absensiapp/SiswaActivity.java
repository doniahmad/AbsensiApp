package com.example.absensiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class SiswaActivity extends AppCompatActivity {

    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    SiswaAdapter siswaAdapter;
    List<SiswaModel> siswaModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa);

        // no title bar
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // setup realm
        realm=Realm.getDefaultInstance();
            realm.init(this);
            realmHelper = new RealmHelper(realm);
            siswaModelList = new ArrayList<>();

        siswaModelList = realmHelper.getAllMahasiswa();

        show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        siswaAdapter.notifyDataSetChanged();
        show();
    }

    public void show(){
        siswaAdapter = new SiswaAdapter(this, siswaModelList, realmHelper);
        recyclerView.setAdapter(siswaAdapter);
    }

}