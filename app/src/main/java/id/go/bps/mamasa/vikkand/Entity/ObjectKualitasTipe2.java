package id.go.bps.mamasa.vikkand.Entity;

import org.json.JSONObject;

/**
 * Created by ASUS on 27/06/2018.
 */

public class ObjectKualitasTipe2 extends ObjectKualitas{

    private String satuan_unit;
    private String nilai_sewa;

    public ObjectKualitasTipe2(){

    }

    public ObjectKualitasTipe2(String uid_survei, int id_responden, String uid_barang, String barang, String uid_kualitas
            , String kualitas, String tipe, String satuan_unit, String nilai_sewa, String keterangan, String status){
        this.uid_survei = uid_survei;
        this.id_responden = id_responden;
        this.uid_barang = uid_barang;
        this.barang = barang;
        this.uid_kualitas = uid_kualitas;
        this.kualitas = kualitas;
        this.tipe = tipe;
        this.satuan_unit = satuan_unit;
        this.nilai_sewa = nilai_sewa;
        this.keterangan = keterangan;
        this.status = status;
    }

    public String getSatuan_unit() {
        return satuan_unit;
    }

    public void setSatuan_unit(String satuan_unit) {
        this.satuan_unit = satuan_unit;
    }

    public String getNilai_sewa() {
        return nilai_sewa;
    }

    public void setNilai_sewa(String nilai_sewa) {
        this.nilai_sewa = nilai_sewa;
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
            returnValue.put("satuan_unit", satuan_unit);
            returnValue.put("nilai_sewa", nilai_sewa);
            returnValue.put("keterangan", keterangan);
            returnValue.put("status", status);
            return returnValue;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
