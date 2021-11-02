package com.example.absensiapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SiswaModel extends RealmObject {

    @PrimaryKey
    private Integer id;
    private Integer absen;
    private String nama;
    private String kelas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAbsen() {
        return absen;
    }

    public void setAbsen(Integer absen) {
        this.absen = absen;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
}
