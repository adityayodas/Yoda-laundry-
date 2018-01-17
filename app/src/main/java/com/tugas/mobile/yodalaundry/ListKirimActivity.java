package com.tugas.mobile.yodalaundry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListKirimActivity extends AppCompatActivity{

    ListView Contact_listview;
    ArrayList<Contact> contact_data = new ArrayList<Contact>();
    Contact_Adapter cAdapter;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            Contact_listview = (ListView) findViewById(R.id.list);
            Contact_listview.setItemsCanFocus(false);

            Set_Referash_Data();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }
    }

    //fungsi untuk memanggil semua data dari Database
    public void Set_Referash_Data() {
        contact_data.clear();
        db = new DatabaseHandler(this);
        final ArrayList<Contact> contact_array_from_db = db.Get_Contacts();

        //looping untuk meng set data ke listview
        for (int i = 0; i < contact_array_from_db.size(); i++) {

            int tidno = contact_array_from_db.get(i).getID();
            String nama = contact_array_from_db.get(i).getNamaLengkap();
            String telp = contact_array_from_db.get(i).getNoHP();
            String proses = contact_array_from_db.get(i).getProses();
            String status = contact_array_from_db.get(i).getStatus();
            Contact cnt = new Contact();
            cnt.setID(tidno);
            cnt.setNamaLengkap(nama);
            cnt.setNoHP(telp);
            cnt.setProses(proses);
            cnt.setStatus(status);

            contact_data.add(cnt);
        }
        db.close();
        cAdapter = new Contact_Adapter(ListKirimActivity.this, R.layout.listview_row, contact_data);
        Contact_listview.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();

        //fungsi clickable ketika data yang di list di klik
        Contact_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idi;
                String k_id;

                idi = contact_array_from_db.get(position).getID();

                k_id = String.valueOf(idi);

                Intent kirim = new Intent(ListKirimActivity.this, DetailKirimActivity.class);

                kirim.putExtra("t_id", k_id);
                startActivity(kirim);
            }
        });
    }

    //constructor untuk tampilan di list
    public class Contact_Adapter extends ArrayAdapter<Contact> {
        Activity activity;
        int layoutResourceId;
        Contact user;
        ArrayList<Contact> data = new ArrayList<Contact>();

        public Contact_Adapter(Activity act, int layoutResourceId,
                               ArrayList<Contact> data) {
            super(act, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.activity = act;
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ListKirimActivity.Contact_Adapter.UserHolderReport holder_report = null;

            user = data.get(position);

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResourceId, parent, false);
                holder_report = new ListKirimActivity.Contact_Adapter.UserHolderReport();
                holder_report.name = (TextView) row.findViewById(R.id.user_name_txt);
                holder_report.number = (TextView) row.findViewById(R.id.user_mob_txt);
                holder_report.edit = (TextView) row.findViewById(R.id.edit);

                row.setTag(holder_report);
            } else {
                holder_report = (ListKirimActivity.Contact_Adapter.UserHolderReport) row.getTag();
            }

            //fungsi untuk memberi keterangan cucian yg dikirim
            if(user.getProses().equals("Masih dicuci")){
                holder_report.name.setText(user.getNamaLengkap());
                holder_report.number.setText(user.getNoHP());
                holder_report.edit.setText("Masih Dicuci !");
            }else{
                if(user.getStatus().equals("Belum Clear")){
                    holder_report.name.setText(user.getNamaLengkap());
                    holder_report.number.setText(user.getNoHP());
                    holder_report.edit.setText("Belum Dikirim !");
                }else{
                    holder_report.name.setText(user.getNamaLengkap());
                    holder_report.number.setText(user.getNoHP());
                    holder_report.edit.setText("Sudah Dikirim !");
                }
            }

            return row;

        }

        class UserHolderReport {
            TextView name;
            TextView number;
            TextView edit;
        }
    }

    //fungsi untuk tombol back
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}