package id.go.bps.mamasa.vikkand.Entity;

import java.util.ArrayList;

/**
 * Created by ASUS on 26/06/2018.
 */

public class ObjectResponden {

    private int id;
    private String username;
    private String nama;
    private String uid_survei;
    private String tipeResponden;
    private String isSelfEnum;
    private String nipPetugas;
    private String urlPhoto;
    private String status;
    private byte[] dataPhotoBlob;
    private ArrayList<ObjectKualitas> objectKualitas;

    public ObjectResponden(){
        setObjectKualitas(new ArrayList<ObjectKualitas>());
    }

    public ObjectResponden(int id, String username, String nama, String uid_survei
            , String tipeResponden, String isSelfEnum, String nipPetugas, String urlPhoto, String status){
        this.id = id;
        this.username = username;
        this.setNama(nama);
        this.uid_survei = uid_survei;
        this.tipeResponden = tipeResponden;
        this.isSelfEnum = isSelfEnum;
        this.nipPetugas = nipPetugas;
        this.urlPhoto = urlPhoto;
        this.status = status;
        setObjectKualitas(new ArrayList<ObjectKualitas>());
    }
    public ObjectResponden(int id, String username, String nama, String uid_survei
            , String tipeResponden, String isSelfEnum, String nipPetugas
            , String urlPhoto, String status, byte[] dataPhotoBlob){
        this.id = id;
        this.username = username;
        this.setNama(nama);
        this.uid_survei = uid_survei;
        this.tipeResponden = tipeResponden;
        this.isSelfEnum = isSelfEnum;
        this.nipPetugas = nipPetugas;
        this.urlPhoto = urlPhoto;
        this.status = status;
        this.dataPhotoBlob = dataPhotoBlob;
        setObjectKualitas(new ArrayList<ObjectKualitas>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid_survei() {
        return uid_survei;
    }

    public void setUid_survei(String uid_survei) {
        this.uid_survei = uid_survei;
    }

    public String getTipeResponden() {
        return tipeResponden;
    }

    public void setTipeResponden(String tipeResponden) {
        this.tipeResponden = tipeResponden;
    }

    public String getIsSelfEnum() {
        return isSelfEnum;
    }

    public void setIsSelfEnum(String isSelfEnum) {
        this.isSelfEnum = isSelfEnum;
    }

    public String getNipPetugas() {
        return nipPetugas;
    }

    public void setNipPetugas(String nipPetugas) {
        this.nipPetugas = nipPetugas;
    }

    public ArrayList<ObjectKualitas> getObjectKualitas() {
        return objectKualitas;
    }

    public void setObjectKualitas(ArrayList<ObjectKualitas> objectKualitas) {
        this.objectKualitas = objectKualitas;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public byte[] getDataPhotoBlob() {
        return dataPhotoBlob;
    }

    public void setDataPhotoBlob(byte[] dataPhotoBlob) {
        this.dataPhotoBlob = dataPhotoBlob;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
