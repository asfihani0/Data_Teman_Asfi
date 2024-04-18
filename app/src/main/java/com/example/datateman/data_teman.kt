package com.example.datateman

class data_teman {
    //deklarasi var
    var nama: String? = null
    var alamat: String? = null
    var no_hp: String? = null
    var key: String? = null

    //Membuat konstruktor kosong untuk membaca data snapshot
    constructor()

    //konstruksi dengan beberapa parameter, untuk mendapatkan input dari user
    constructor(nama: String?, alamat: String?, no_hp: String?){
        this.nama = nama
        this.alamat = alamat
        this.no_hp = no_hp
    }
}