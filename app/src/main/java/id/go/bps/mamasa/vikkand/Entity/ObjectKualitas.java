package id.go.bps.mamasa.vikkand.Entity;

/**
 * Created by ASUS on 27/06/2018.
 */

public class ObjectKualitas {

    protected String uid_survei;
    protected int id_responden;
    protected String uid_barang;
    protected String barang;
    protected String uid_kualitas;
    protected String kualitas;
    protected String tipe;
    protected String keterangan;
    protected String status;

    public String getUid_survei() {
        return uid_survei;
    }

    public void setUid_survei(String uid_survei) {
        this.uid_survei = uid_survei;
    }

    public int getId_responden() {
        return id_responden;
    }

    public void setId_responden(int id_responden) {
        this.id_responden = id_responden;
    }

    public String getUid_barang() {
        return uid_barang;
    }

    public void setUid_barang(String uid_barang) {
        this.uid_barang = uid_barang;
    }

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public String getUid_kualitas() {
        return uid_kualitas;
    }

    public void setUid_kualitas(String uid_kualitas) {
        this.uid_kualitas = uid_kualitas;
    }

    public String getKualitas() {
        return kualitas;
    }

    public void setKualitas(String kualitas) {
        this.kualitas = kualitas;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
