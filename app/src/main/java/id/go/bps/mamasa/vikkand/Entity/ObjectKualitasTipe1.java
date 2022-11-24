package id.go.bps.mamasa.vikkand.Entity;

import org.json.JSONObject;

/**
 * Created by ASUS on 27/06/2018.
 */

public class ObjectKualitasTipe1 extends ObjectKualitas{

    private String satuan_standar;
    private String merk;
    private String satuan_setempat;
    private String ukuran_panjang;
    private String ukuran_lebar;
    private String ukuran_tinggi;
    private String ukuran_berat;
    private String konversi_setempat;
    private String harga_satuan_setempat;
    private String harga_satuan_standar;

    public ObjectKualitasTipe1(){

    }

    public ObjectKualitasTipe1(String uid_survei, int id_responden, String uid_barang, String barang, String uid_kualitas
            , String kualitas, String tipe, String satuan_standar, String merk, String satuan_setempat, String ukuran_panjang
            , String ukuran_lebar, String ukuran_tinggi, String ukuran_berat, String konversi_setempat, String harga_satuan_setempat
            , String harga_satuan_standar, String keterangan, String status){
        this.uid_survei = uid_survei;
        this.id_responden = id_responden;
        this.uid_barang = uid_barang;
        this.barang = barang;
        this.uid_kualitas = uid_kualitas;
        this.kualitas = kualitas;
        this.tipe = tipe;
        this.satuan_standar = satuan_standar;
        this.merk = merk;
        this.satuan_setempat = satuan_setempat;
        this.ukuran_panjang = ukuran_panjang;
        this.ukuran_lebar = ukuran_lebar;
        this.ukuran_tinggi = ukuran_tinggi;
        this.ukuran_berat = ukuran_berat;
        this.konversi_setempat = konversi_setempat;
        this.harga_satuan_setempat = harga_satuan_setempat;
        this.harga_satuan_standar = harga_satuan_standar;
        this.keterangan = keterangan;
        this.status = status;
    }

    public String getSatuan_standar() {
        return satuan_standar;
    }

    public void setSatuan_standar(String satuan_standar) {
        this.satuan_standar = satuan_standar;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getSatuan_setempat() {
        return satuan_setempat;
    }

    public void setSatuan_setempat(String satuan_setempat) {
        this.satuan_setempat = satuan_setempat;
    }

    public String getUkuran_panjang() {
        return ukuran_panjang;
    }

    public void setUkuran_panjang(String ukuran_panjang) {
        this.ukuran_panjang = ukuran_panjang;
    }

    public String getUkuran_lebar() {
        return ukuran_lebar;
    }

    public void setUkuran_lebar(String ukuran_lebar) {
        this.ukuran_lebar = ukuran_lebar;
    }

    public String getUkuran_tinggi() {
        return ukuran_tinggi;
    }

    public void setUkuran_tinggi(String ukuran_tinggi) {
        this.ukuran_tinggi = ukuran_tinggi;
    }

    public String getUkuran_berat() {
        return ukuran_berat;
    }

    public void setUkuran_berat(String ukuran_berat) {
        this.ukuran_berat = ukuran_berat;
    }

    public String getKonversi_setempat() {
        return konversi_setempat;
    }

    public void setKonversi_setempat(String konversi_setempat) {
        this.konversi_setempat = konversi_setempat;
    }

    public String getHarga_satuan_setempat() {
        return harga_satuan_setempat;
    }

    public void setHarga_satuan_setempat(String harga_satuan_setempat) {
        this.harga_satuan_setempat = harga_satuan_setempat;
    }

    public String getHarga_satuan_standar() {
        return harga_satuan_standar;
    }

    public void setHarga_satuan_standar(String harga_satuan_standar) {
        this.harga_satuan_standar = harga_satuan_standar;
    }

    public JSONObject getJSONObject (){
        try{
            JSONObject returnValue = new JSONObject();
            returnValue.put("uid_survei", uid_survei);
            returnValue.put("id_responden", id_responden);
            returnValue.put("uid_barang", uid_barang);
            returnValue.put("barang", barang);
            returnValue.put("uid_kualitas", uid_kualitas);
            returnValue.put("kualitas", kualitas);
            returnValue.put("tipe", tipe);
            returnValue.put("satuan_standar", satuan_standar);
            returnValue.put("merk", merk);
            returnValue.put("satuan_setempat", satuan_setempat);
            returnValue.put("ukuran_panjang", ukuran_panjang);
            returnValue.put("ukuran_lebar", ukuran_lebar);
            returnValue.put("ukuran_tinggi", ukuran_tinggi);
            returnValue.put("ukuran_berat", ukuran_berat);
            returnValue.put("konversi_setempat", konversi_setempat);
            returnValue.put("harga_satuan_setempat", harga_satuan_setempat);
            returnValue.put("harga_satuan_standar", harga_satuan_standar);
            returnValue.put("keterangan", keterangan);
            returnValue.put("status", status);
            return returnValue;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
