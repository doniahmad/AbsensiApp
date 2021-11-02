package com.example.absensiapp;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm){
        this.realm = realm;
    }

    // untuk menyimpan data
    public void save(final SiswaModel siswaModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Number currentIdNum = realm.where(SiswaModel.class).max("id");
                int nextId;
                if (currentIdNum == null){
                    nextId = 0;
                }else {
                    nextId = currentIdNum.intValue() + 1;
                }
                siswaModel.setId(nextId);

                SiswaModel model = realm.copyToRealm(siswaModel);
            }
        });
    }

    // untuk memanggil semua data
    public List<SiswaModel> getAllMahasiswa(){
        RealmResults<SiswaModel> results = realm.where(SiswaModel.class).findAll();
        return results;
    }

    // untuk menghapus data
    public void delete(Integer id){
        final RealmResults<SiswaModel> model = realm.where(SiswaModel.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteAllFromRealm();
            }
        });
    }

}
