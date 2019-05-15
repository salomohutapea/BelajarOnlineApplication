package com.example.belajaronlineapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val uid: String,
    val username: String,
    val profileImageUrl: String,
    val password: String,
    val email: String,
    val nama: String,
    val tingkat: String,
    val jurusan: String,
    val kurikulum: String,
    val jenisKelamin: String,
    val noHP: String,
    val alamat: String,
    val provinsi: String,
    val kabupatenKota: String,
    val sekolah: String,
    val namaOrtu: String,
    val noHPOrtu: String,
    val agreeIsChecked: String
) :Parcelable {
    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
}