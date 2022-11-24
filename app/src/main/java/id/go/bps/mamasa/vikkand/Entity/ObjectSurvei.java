package id.go.bps.mamasa.vikkand.Entity;

import java.util.ArrayList;

/**
 * Created by ASUS on 26/06/2018.
 */

public class ObjectSurvei {

    private String uid_survei;
    private String tahun;
    private String bulan;
    private String mulai;
    private String akhir;
    private String status;
    private ArrayList<ObjectResponden> objectRespondens;

    public ObjectSurvei(){
        this.objectRespondens = new ArrayList<>();
    }

    public ObjectSurvei(String uid_survei, String tahun, String bulan, String mulai
            , String akhir, String status){
        this.uid_survei = uid_survei;
        this.tahun = tahun;
        this.bulan = bulan;
        this.mulai = mulai;
        this.akhir = akhir;
        this.status = status;
        this.objectRespondens = new ArrayList<>();
    }

    public String getUid_survei() {
        return uid_survei;
    }

    public void setUid_survei(String uid_survei) {
        this.uid_survei = uid_survei;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ObjectResponden> getObjectRespondens() {
        return objectRespondens;
    }

    public void setObjectRespondens(ArrayList<ObjectResponden> objectRespondens) {
        this.objectRespondens = objectRespondens;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getAkhir() {
        return akhir;
    }

    public void setAkhir(String akhir) {
        this.akhir = akhir;
    }
}
