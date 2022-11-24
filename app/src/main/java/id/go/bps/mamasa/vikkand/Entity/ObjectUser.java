package id.go.bps.mamasa.vikkand.Entity;

/**
 * Created by ASUS on 27/06/2018.
 */

public class ObjectUser {
    private String nip;
    private String username;
    private String isPetugas;
    private String nama;
    private String jwtToken;
    private String urlPhoto;
    private byte[] dataPhotoBlob;

    public ObjectUser(){

    }

    public ObjectUser(String nip, String username, String isPetugas, String nama, String jwtToken, String urlPhoto){
        this.nip = nip;
        this.username = username;
        this.isPetugas = isPetugas;
        this.nama = nama;
        this.jwtToken = jwtToken;
        this.urlPhoto = urlPhoto;
    }

    public ObjectUser(String nip, String username, String isPetugas, String nama
            , String jwtToken, String urlPhoto, byte[] dataPhotoBlob){
        this.nip = nip;
        this.username = username;
        this.isPetugas = isPetugas;
        this.nama = nama;
        this.jwtToken = jwtToken;
        this.urlPhoto = urlPhoto;
        this.dataPhotoBlob = dataPhotoBlob;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsPetugas() {
        return isPetugas;
    }

    public void setIsPetugas(String isPetugas) {
        this.isPetugas = isPetugas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
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
}
