package com.example.absensiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAbsen, btnTampil;
    Realm realm;
    RealmHelper realmHelper;
    SiswaModel siswaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // no title bar
        getSupportActionBar().hide();

        // inisialisasi
        btnAbsen = findViewById(R.id.btn_absen);
        btnTampil = findViewById(R.id.btn_absen_list);

        // setup realm
        realm=Realm.getDefaultInstance();

        btnAbsen.setOnClickListener(this);
        btnTampil.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_absen){
            ShowInsertDialog();
        }else if (view == btnTampil){
            startActivity(new Intent(MainActivity.this, SiswaActivity.class));
        }
    }

    private void ShowInsertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.input_dialog_layout, null);
        alert.setView(view);
        EditText nama, absen;
        Spinner kelas;

        nama = view.findViewById(R.id.nama);
        absen = view.findViewById(R.id.absen);
        kelas = view.findViewById(R.id.kelas);
        Button save = view.findViewById(R.id.save);
        final AlertDialog alertDialog = alert.show();

        nama.setInputType(
                InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        );

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                String sNama = nama.getText().toString();
                Integer sAbsen = Integer.parseInt(absen.getText().toString());
                String sKelas = kelas.getSelectedItem().toString();

                if (!sAbsen.equals("") && !sNama.isEmpty()){
                    siswaModel = new SiswaModel();
                    siswaModel.setNama(sNama);
                    siswaModel.setAbsen(sAbsen);
                    siswaModel.setKelas(sKelas);

                    realmHelper = new RealmHelper(realm);
                    realmHelper.save(siswaModel);

                    Toast.makeText(MainActivity.this, "Berhasil Disimpan!", Toast.LENGTH_SHORT).show();

                    nama.setText("");
                    absen.setText("");
                }
                else if (sAbsen.equals("") || sNama.isEmpty() || !sKelas.isEmpty()){
                    Toast.makeText(MainActivity.this, "Terdapat inputan yang kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Terdapat inputan yang salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}