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
import android.widget.TextView;
import android.widget.Toast;


public class DetailPelangganActivity extends AppCompatActivity{

    int ID;
    DatabaseHandler dbHandler = new DatabaseHandler(this);
    TextView tv_nama, tv_alamat, tv_telp, tv_berat, tv_biaya, tv_jenis, tv_proses, tv_status;
    String s_alamat, s_hp;
    Button btn_call, btn_sms, btn_hapus;

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

        btn_call = (Button)findViewById(R.id.btn_call);
        btn_sms = (Button)findViewById(R.id.btn_sms);
        btn_hapus = (Button)findViewById(R.id.hapus);

        ID = Integer.parseInt(getIntent().getStringExtra("t_id"));

        Contact c = dbHandler.Get_Contact(ID);

        tv_nama.setText(c.getNamaLengkap());
        tv_alamat.setText(c.getAlamat());
        tv_telp.setText(c.getNoHP());
        tv_berat.setText(String.valueOf(c.getBerat())+" Kg");
        tv_biaya.setText("Rp. "+String.valueOf(c.getBiaya()));
        tv_jenis.setText(c.getJenisPengambilan());
        tv_proses.setText(c.getProses());
        tv_status.setText(c.getStatus());

        s_hp = tv_telp.getText().toString();
        s_alamat = tv_alamat.getText().toString();

        //button untuk melakukan panggilan
        btn_call.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View arg0) {
                try{
                    //fungsi memanggil / menelpon
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+s_hp));
                    startActivity(callIntent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        //button untuk melakukan sms
        btn_sms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try{
                    //fungsi untuk melakukan sms
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", s_hp, null)));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

        //button untuk menghapus member
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler dBHandler = new DatabaseHandler(
                        DetailPelangganActivity.this);
                dBHandler.Delete_Contact(ID);

                Toast.makeText(DetailPelangganActivity.this, "Data Berhasil di Delete", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DetailPelangganActivity.this, MainActivity.class);
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
        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?q="+s_alamat);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}