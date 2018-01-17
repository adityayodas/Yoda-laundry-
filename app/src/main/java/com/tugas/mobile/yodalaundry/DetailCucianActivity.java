package com.tugas.mobile.yodalaundry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class DetailCucianActivity extends AppCompatActivity{

    int ID;
    DatabaseHandler dbHandler = new DatabaseHandler(this);
    TextView tv_nama, tv_alamat, tv_telp, tv_berat, tv_biaya, tv_jenis, tv_proses, tv_status;
    String s_nama, s_alamat, s_telp, s_berat, s_biaya, s_jenis, s_proses, s_status;
    Button btn_update;

    LinearLayout btn;
    View after;

    Contact c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pelanggan);

        //menapilkan tombol back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_nama = (TextView)findViewById(R.id.detail_nama);
        tv_alamat = (TextView)findViewById(R.id.detail_alamat);
        tv_telp = (TextView)findViewById(R.id.detail_telp);
        tv_berat = (TextView)findViewById(R.id.detail_berat);
        tv_biaya = (TextView)findViewById(R.id.detail_biaya);
        tv_jenis = (TextView)findViewById(R.id.detail_jenis_pengambilan);
        tv_proses = (TextView)findViewById(R.id.detail_proses);
        tv_status = (TextView)findViewById(R.id.detail_status);

        btn_update = (Button)findViewById(R.id.hapus);
        btn_update.setText("Sudah dicuci");

        after = (View)findViewById(R.id.after_button);
        btn = (LinearLayout) findViewById(R.id.button);

        after.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);

        //mengambil ID yang sudah dikirim dari halaman sebelumnya
        ID = Integer.parseInt(getIntent().getStringExtra("t_id"));

        c = dbHandler.Get_Contact(ID);

        //meng set data dari database ke TextView
        tv_nama.setText(c.getNamaLengkap());
        tv_alamat.setText(c.getAlamat());
        tv_telp.setText(c.getNoHP());
        tv_berat.setText(String.valueOf(c.getBerat())+" Kg");
        tv_biaya.setText("Rp. "+String.valueOf(c.getBiaya()));
        tv_jenis.setText(c.getJenisPengambilan());
        tv_proses.setText(c.getProses());
        tv_status.setText(c.getStatus());

        //action utnuk button meng update bahwa cucian sudah dicuci
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s_nama = tv_nama.getText().toString();
                s_alamat = tv_alamat.getText().toString();
                s_telp = tv_telp.getText().toString();
                s_jenis = tv_jenis.getText().toString();
                s_proses = "Sudah dicuci";
                if(c.getJenisPengambilan().equals("Diambil")){
                    s_status = "Sudah Clear";
                }else{
                    s_status = c.getStatus();
                }

                //mengaktifkan SQL update dari DatabaseHandler
                dbHandler.Update_Contact(new Contact(ID, s_nama, s_alamat, s_telp, c.getBerat(), c.getBiaya(),
                        s_jenis, s_proses, s_status));
                dbHandler.close();
                Intent i = new Intent(DetailCucianActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
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