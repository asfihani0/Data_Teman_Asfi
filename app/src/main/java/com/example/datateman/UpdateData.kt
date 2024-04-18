package com.example.datateman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.datateman.databinding.ActivityUpdateDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNama: String? = null
    private var cekAlamat: String? = null
    private var cekNoHP: String? = null
    private lateinit var binding : ActivityUpdateDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar (findViewById(R.id.my_toolbarx))
        Log.d("UpdateData", "UpdateData activity is launched")
        supportActionBar!!.title = "Update Data"

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        //memanggil method "data"
        binding.update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //Mendapatkan Data Mahasiswa yang akan dicek
                cekNama = binding.newNama.getText().toString()
                cekAlamat = binding.newAlamat.getText().toString()
                cekNoHP = binding.newNohp.getText().toString()

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if (isEmpty(cekNama!!) || isEmpty(cekAlamat!!) || isEmpty(cekNoHP!!)) {
                    Toast.makeText(this@UpdateData, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
                } else {
                    /*Menjalankan proses update data.Method Setter digunakan untuk mendapakan data baru yang diinputkan User.*/
                    val setTeman = data_teman()
                    setTeman.nama = binding.newNama.getText().toString()
                    setTeman.alamat = binding.newAlamat.getText().toString()
                    setTeman.no_hp = binding.newNohp.getText().toString()
                    UpdateTeman(setTeman)
                }
            }
        })
    }
    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }
    //Menampilkan data yang akan di update
    private val data: Unit
        private get () {
            //Menampilkan data dari item yang dipilih sebelumnya
            val getNama = intent.extras!!.getString("dataNama")
            val getAlamat = intent.extras!!.getString("dataAlamat")
            val getNoHP = intent.extras!!.getString("dataNoHP")
            binding.newNama!!.setText(getNama)
            binding.newAlamat!!.setText(getAlamat)
            binding.newNohp!!.setText(getNoHP)
        }
    //Proses Update data yang sudah ditentukan
    private fun UpdateTeman(teman: data_teman) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID.toString())
            .child("DataTeman")
            .child(getKey!!)
            .setValue(teman)
            .addOnSuccessListener {
                binding.newNama!!.setText("")
                binding.newAlamat!!.setText("")
                binding.newNohp!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil diubah", Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}