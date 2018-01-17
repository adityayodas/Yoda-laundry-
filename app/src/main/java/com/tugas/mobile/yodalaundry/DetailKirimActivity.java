package com.tugas.mobile.yodalaundry;

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


public class DetailKirimActivity extends AppCompatActivity{
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
        btn_update.setText("Sudah dikirim");

        after = (View)findViewById(R.id.after_button);
        btn = (LinearLayout) findViewById(R.id.button);

        after.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);

        ID = Integer.parseInt(getIntent().getStringExtra("t_id"));

        c = dbHandler.Get_Contact(ID);

        tv_nama.setText(c.getNamaLengkap());
        tv_alamat.setText(c.getAlamat());
        tv_telp.setText(c.getNoHP());
        tv_berat.setText(String.valueOf(c.getBerat())+" Kg");
        tv_biaya.setText("Rp. "+String.valueOf(c.getBiaya()));
        tv_jenis.setText(c.getJenisPengambilan());
        tv_proses.setText(c.getProses());
        tv_status.setText(c.getStatus());

        //action utnuk button meng update bahwa cucian sudah dikirim
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s_nama = tv_nama.getText().toString();
                s_alamat = tv_alamat.getText().toString();
                s_telp = tv_telp.getText().toString();
                s_jenis = tv_jenis.getText().toString();
                s_proses = "Sudah dicuci";
                s_status = "Sudah Clear";

                //mengaktifkan SQL update dari DatabaseHandler
                dbHandler.Update_Contact(new Contact(ID, s_nama, s_alamat, s_telp, c.getBerat(), c.getBiaya(),
                        s_jenis, s_proses, s_status));
                dbHandler.close();
                Intent i = new Intent(DetailKirimActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    //fungsi untuk menampilkan tombol lokasi
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lokasi, menu);//Menu Resource, Menu
        return true;
    }

    //fungsi untuk tombol back dan lokasi
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.lokasi:
                cekLokasi();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //fungsi untuk membuka map dengan alamat
    private void cekLokasi() {
        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?q="+c.getAlamat());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}