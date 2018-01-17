package com.tugas.mobile.yodalaundry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;



//class untuk membuat tabel database, membuat data, meng update data, meng hapus data
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_DPT = "data_pelanggan";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAMA_LENGKAP = "nama_lengkap";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_NO_HP = "no_hp";
    private static final String KEY_BERAT = "berat";
    private static final String KEY_BIAYA = "biaya";
    private static final String KEY_JENIS_PENGAMBILAN = "jenis_pengambilan";
    private static final String KEY_PROSES = "proses";
    private static final String KEY_STATUS = "status";
    private final ArrayList<Contact> contact_list = new ArrayList<Contact>();

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Membuat tabel database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DPT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAMA_LENGKAP + " TEXT,"
                + KEY_ALAMAT + " TEXT," + KEY_NO_HP + " TEXT," + KEY_BERAT + " INTEGER,"
                + KEY_BIAYA + " INTEGER," + KEY_JENIS_PENGAMBILAN + " TEXT," + KEY_PROSES + " TEXT," + KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // ketika ada tabel baru yg berbeda maka tabel diganti
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DPT);
        Log.e("" , ""+TABLE_DPT);

        // Create tables again
        onCreate(db);
    }

    // Menambahkan data
    public void Add_Contact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAMA_LENGKAP, contact.getNamaLengkap());
        values.put(KEY_ALAMAT, contact.getAlamat());
        values.put(KEY_NO_HP, contact.getNoHP());
        values.put(KEY_BERAT, contact.getBerat());
        values.put(KEY_BIAYA, contact.getBiaya());
        values.put(KEY_JENIS_PENGAMBILAN, contact.getJenisPengambilan());
        values.put(KEY_PROSES, contact.getProses());
        values.put(KEY_STATUS, contact.getStatus());
        // Inserting Row
        db.insert(TABLE_DPT, null, values);
        db.close(); // Closing database connection
    }

    // Mengambil data berdasarkan ID
    public Contact Get_Contact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DPT, new String[] { KEY_ID, KEY_NAMA_LENGKAP, KEY_ALAMAT, KEY_NO_HP,
                        KEY_BERAT, KEY_BIAYA, KEY_JENIS_PENGAMBILAN, KEY_PROSES, KEY_STATUS}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)), cursor.getString(6), cursor.getString(7), cursor.getString(8));
        // return contact
        cursor.close();
        db.close();

        return contact;
    }

    // Mengambil semua data
    public ArrayList<Contact> Get_Contacts() {
        try {
            contact_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_DPT;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setNamaLengkap(cursor.getString(1));
                    contact.setAlamat(cursor.getString(2));
                    contact.setNoHP(cursor.getString(3));
                    contact.setBerat(Integer.parseInt(cursor.getString(4)));
                    contact.setBiaya(Integer.parseInt(cursor.getString(5)));
                    contact.setJenisPengambilan(cursor.getString(6));
                    contact.setProses(cursor.getString(7));
                    contact.setStatus(cursor.getString(8));
                    // Adding contact to list
                    contact_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return contact_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return contact_list;
    }

    // Meng update / mengubah data
    public int Update_Contact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMA_LENGKAP, contact.getNamaLengkap());
        values.put(KEY_ALAMAT, contact.getAlamat());
        values.put(KEY_NO_HP, contact.getNoHP());
        values.put(KEY_BERAT, contact.getBerat());
        values.put(KEY_BIAYA, contact.getBiaya());
        values.put(KEY_JENIS_PENGAMBILAN, contact.getJenisPengambilan());
        values.put(KEY_PROSES, contact.getProses());
        values.put(KEY_STATUS, contact.getStatus());

        // updating row
        return db.update(TABLE_DPT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Menghapus data berdasarkan ID
    public void Delete_Contact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DPT, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

}

