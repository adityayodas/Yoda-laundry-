package com.tugas.mobile.yodalaundry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class InputDataActivity extends AppCompatActivity{

    EditText et_nama, et_alamat, et_telp, et_berat, et_biaya;
    Spinner spn_kembali;

    String s_nama, s_alamat, s_telp, s_berat, s_biaya, s_kembali, s_status;

    Button btn_simpan, btn_hitung;

    int berat, harga;

    DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //menapilkan tombol back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_nama = (EditText)findViewById(R.id.input_nama);
        et_alamat = (EditText)findViewById(R.id.input_alamat);
        et_telp = (EditText)findViewById(R.id.input_telp);
        et_berat = (EditText)findViewById(R.id.input_berat);
        et_biaya = (EditText)findViewById(R.id.input_biaya);

        spn_kembali = (Spinner)findViewById(R.id.jenis_kembali);

        btn_hitung = (Button) findViewById(R.id.hitung_biaya);
        btn_simpan = (Button) findViewById(R.id.simpan);

        //acion untuk menghitung biaya
        btn_hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                berat = Integer.parseInt(et_berat.getText().toString());

                harga = 5000*berat;

                et_biaya.setText("Rp. "+harga);
            }
        });

        //action untuk menyimpan data
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_nama = et_nama.getText().toString();
                s_alamat = et_alamat.getText().toString();
                s_telp = et_telp.getText().toString();
                s_berat = et_berat.getText().toString();

                if(spn_kembali.getSelectedItemPosition() == 0){
                    s_kembali = "Diambil";
                }else{
                    s_kembali = "Dikirim";
                }

                if(s_nama != null){
                    if(s_alamat != null){
                        if(s_telp != null){
                            if(s_berat != null){
                                //fungsi / action untuk menambahkan data di database local android
                                dbHandler.Add_Contact(new Contact(s_nama, s_alamat, s_telp, Integer.parseInt(s_berat), harga, s_kembali, "Masih dicuci", "Belum Clear"));
                                Toast.makeText(InputDataActivity.this, "Sukses !", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(InputDataActivity.this, MainActivity.class);
                                startActivity(i);
                            }else{
                                et_berat.setError("Berat harus diisi !");
                            }
                        }else{
                            et_telp.setError("Nomor telp harus diisi !");
                        }
                    }else{
                        et_alamat.setError("Alamat harus diisi !");
                    }
                }else{
                    et_nama.setError("Nama harus diisi !");
                }
            }
        });
    }

    //membuat fungsi di tombol back
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
