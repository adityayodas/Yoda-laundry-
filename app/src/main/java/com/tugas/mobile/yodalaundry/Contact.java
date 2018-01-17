package com.tugas.mobile.yodalaundry;


//class constructor untuk mengeset data untuk di Database
public class Contact {

    public int _id;
    public String _nama_lengkap;
    public String _alamat;
    public String _no_hp;
    public int _berat;
    public int _biaya;
    public String _jenis_pengambilan;
    public String _proses;
    public String _status;

    public Contact() {

    }

    // constructor
    public Contact(int id, String _nama_lengkap, String _alamat, String _no_hp, int _berat, int _biaya,
                   String _jenis_pengambilan, String _proses, String _status) {
        this._id = id;
        this._nama_lengkap = _nama_lengkap;
        this._alamat = _alamat;
        this._no_hp = _no_hp;
        this._berat = _berat;
        this._biaya = _biaya;
        this._jenis_pengambilan = _jenis_pengambilan;
        this._proses = _proses;
        this._status = _status;
    }

    // constructor
    public Contact(String _nama_lengkap, String _alamat, String _no_hp, int _berat, int _biaya,
                   String _jenis_pengambilan, String _proses, String _status) {

        this._nama_lengkap = _nama_lengkap;
        this._alamat = _alamat;
        this._no_hp = _no_hp;
        this._berat = _berat;
        this._biaya = _biaya;
        this._jenis_pengambilan = _jenis_pengambilan;
        this._proses = _proses;
        this._status = _status;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    public String getNamaLengkap() {
        return this._nama_lengkap;
    }

    public void setNamaLengkap(String nama_lengkap) {
        this._nama_lengkap = nama_lengkap;
    }

    public String getAlamat() {
        return this._alamat;
    }

    public void setAlamat(String alamat) {
        this._alamat = alamat;
    }

    public String getNoHP() {
        return this._no_hp;
    }

    public void setNoHP(String no_hp) {
        this._no_hp = no_hp;
    }

    public int getBerat() {
        return this._berat;
    }

    public void setBerat(int berat) {
        this._berat = berat;
    }

    public int getBiaya() {
        return this._biaya;
    }

    public void setBiaya(int biaya) {
        this._biaya = biaya;
    }

    public String getJenisPengambilan() {
        return this._jenis_pengambilan;
    }

    public void setJenisPengambilan(String jenis_pengambilan) {
        this._status = jenis_pengambilan;
    }

    public String getProses() {
        return this._proses;
    }

    public void setProses(String proses) {
        this._proses = proses;
    }

    public String getStatus() {
        return this._status;
    }

    public void setStatus(String status) {
        this._status = status;
    }
}