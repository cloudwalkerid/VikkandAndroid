package id.go.bps.mamasa.vikkand.Asset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import id.go.bps.mamasa.vikkand.Entity.ObjectKualitas;
import id.go.bps.mamasa.vikkand.Entity.ObjectKualitasTipe1;
import id.go.bps.mamasa.vikkand.Entity.ObjectKualitasTipe2;
import id.go.bps.mamasa.vikkand.Entity.ObjectResponden;
import id.go.bps.mamasa.vikkand.Entity.ObjectRespondenHelper;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurvei;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurveiHelper;
import id.go.bps.mamasa.vikkand.Entity.ObjectTipeHelper;
import id.go.bps.mamasa.vikkand.Entity.ObjectUser;

/**
 * Created by ASUS on 04/06/2018.
 */

public class Database  extends SQLiteOpenHelper {
    private Context context;
    private static Database sInstance;

    private String t="Database";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vikkandDB";

    private static final String TABLE_USER = "tableuser";
    private static final String TABLE_SURVEI = "tablesurvei";
    private static final String TABLE_RESPONDEN = "tableresponden";
    private static final String TABLE_KUALITAS_SATU = "tablekualitassatu";
    private static final String TABLE_KUALITAS_DUA = "tablekualitasdua";
    //private static final String TABLE_IDENTITY = "tableidentity";
    public static final String K_ID = "id";
    //for table user
    public static final String K_NAMA = "nama";
    public static final String K_IS_PETUGAS = "isPetugas";
    public static final String K_NIP = "nip";
    public static final String K_USERNAME = "username";
    public static final String K_JWT_TOKEN = "jwtToken";
    public static final String K_IS_SIGN = "isSign";
    public static final String K_URL_PHOTO = "url_photo";
    public static final String K_DATA_PHOTO = "data_photo";
    public static final String K_LAST_UPDATE_TIME = "lastUpdate";

    public static final String K_UID_SURVEI = "uid_survei";
    public static final String K_TAHUN = "tahun";
    public static final String K_BULAN = "bulan";
    public static final String K_MULAI = "mulai";
    public static final String K_AKHIR = "akhir";
    public static final String K_STATUS = "status";

    public static final String K_TIPE_RESPONDEN = "tipeResponden";
    public static final String K_IS_SELF_ENUM = "isSelfEnum";
    public static final String K_NIP_PETUGAS = "nipPetugas";

    public static final String K_ID_RESPONDEN = "idResponden";
    public static final String K_UID_BARANG = "uidBarang";
    public static final String K_BARANG = "barang";
    public static final String K_UID_KUALITAS = "uidKualitas";
    public static final String K_KUALITAS = "kualitas";
    public static final String K_TIPE = "tipe";
    public static final String K_KETERANGAN = "keterangan";

    public static final String K_SATUAN_STANDAR = "satuanStandar";
    public static final String K_MERK = "merk";
    public static final String K_SATUAN_SETEMPAT = "satuanSetempat";
    public static final String K_UKURAN_PANJANG = "ukuranPanjang";
    public static final String K_UKURAN_LEBAR = "ukuranLebar";
    public static final String K_UKURAN_TINGGI = "ukuranTinggi";
    public static final String K_UKURAN_BERAT = "ukuranBerat";
    public static final String K_KONVERSI_SETEMPAT = "konversi";
    public static final String K_HARGA_SETEMPAT = "hargaSetempat";
    public static final String K_HARGA_STANDAR = "hargaStandar";

    public static final String K_SATUAN_UNIT = "satuanUnit";
    public static final String K_NILAI_SEWA = "nilaiSewa";


    public static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_USER+" ("
            +K_ID + " INTEGER PRIMARY KEY,"
            +K_NAMA+" text, "
            +K_IS_PETUGAS+" integer, "
            +K_NIP+" text, "
            +K_USERNAME+" text, "
            +K_URL_PHOTO+" text, "
            +K_DATA_PHOTO+" blob, "
            +K_IS_SIGN+" text, "
            +K_LAST_UPDATE_TIME+" text, "
            +K_JWT_TOKEN+" text) ";

    public static final String CREATE_TABLE_SURVEI = "CREATE TABLE "+TABLE_SURVEI+" ("
            +K_UID_SURVEI + " text PRIMARY KEY,"
            +K_TAHUN+" text, "
            +K_BULAN+" text, "
            +K_MULAI+" text, "
            +K_AKHIR+" text, "
            +K_STATUS+" text ) ";

    public static final String CREATE_TABLE_RESPONDEN = "CREATE TABLE "+TABLE_RESPONDEN+" ("
            +K_ID + " INTEGER PRIMARY KEY,"
            +K_UID_SURVEI+" text, "
            +K_ID_RESPONDEN + " integer, "
            +K_USERNAME+" text, "
            +K_NAMA+" text, "
            +K_TIPE_RESPONDEN+" text, "
            +K_IS_SELF_ENUM+" text, "
            +K_NIP_PETUGAS+" text, "
            +K_URL_PHOTO+" text, "
            +K_STATUS+" text , "
            +K_DATA_PHOTO+" blob) ";

    public static final String CREATE_TABLE_KUALITAS_SATU = "CREATE TABLE "+TABLE_KUALITAS_SATU+" ("
            +K_ID + " INTEGER PRIMARY KEY,"
            +K_UID_SURVEI+" text, "
            +K_ID_RESPONDEN+" integer, "
            +K_UID_BARANG+" text, "
            +K_BARANG+" text, "
            +K_UID_KUALITAS+" text, "
            +K_KUALITAS+" text, "
            +K_TIPE+" text, "
            +K_KETERANGAN+" text, "
            +K_STATUS+" text, "
            +K_SATUAN_STANDAR+" text, "
            +K_MERK+" text, "
            +K_SATUAN_SETEMPAT+" text, "
            +K_UKURAN_PANJANG+" text, "
            +K_UKURAN_LEBAR+" text, "
            +K_UKURAN_TINGGI+" text, "
            +K_UKURAN_BERAT+" text, "
            +K_KONVERSI_SETEMPAT+" text, "
            +K_HARGA_SETEMPAT+" text, "
            +K_HARGA_STANDAR+" text )";

    public static final String CREATE_TABLE_KUALITAS_DUA = "CREATE TABLE "+TABLE_KUALITAS_DUA+" ("
            +K_ID + " INTEGER PRIMARY KEY,"
            +K_UID_SURVEI+" text, "
            +K_ID_RESPONDEN+" integer, "
            +K_UID_BARANG+" text, "
            +K_BARANG+" text, "
            +K_UID_KUALITAS+" text, "
            +K_KUALITAS+" text, "
            +K_TIPE+" text, "
            +K_KETERANGAN+" text, "
            +K_STATUS+" text, "
            +K_SATUAN_UNIT+" text, "
            +K_NILAI_SEWA+" text) ";

    public static synchronized Database getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Database(context);
        }
        return sInstance;
    }

    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SURVEI);
        db.execSQL(CREATE_TABLE_RESPONDEN);
        db.execSQL(CREATE_TABLE_KUALITAS_SATU);
        db.execSQL(CREATE_TABLE_KUALITAS_DUA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS ";
        db.execSQL(drop + TABLE_USER);
        db.execSQL(drop + TABLE_SURVEI);
        db.execSQL(drop + TABLE_RESPONDEN);
        db.execSQL(drop + TABLE_KUALITAS_SATU);
        db.execSQL(drop + TABLE_KUALITAS_DUA);
        //db.execSQL(drop + TABLE_IDENTITY);
        onCreate(db);
    }

    public ObjectUser getWhoIam(){
        String sql = "SELECT * FROM "+TABLE_USER+" LIMIT 1";
        try {
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                return new ObjectUser(cursor.getString(cursor.getColumnIndex(K_NIP))
                        , cursor.getString(cursor.getColumnIndex(K_USERNAME))
                        ,cursor.getString(cursor.getColumnIndex(K_IS_PETUGAS))
                        ,cursor.getString(cursor.getColumnIndex(K_NAMA))
                        ,cursor.getString(cursor.getColumnIndex(K_JWT_TOKEN))
                        ,cursor.getString(cursor.getColumnIndex(K_URL_PHOTO))
                        ,cursor.getBlob(cursor.getColumnIndex(K_DATA_PHOTO)));

            }else{
                return null;
            }
        }catch (Exception e){
            Log.d(t,e.toString());
            return null;
        }finally {

        }
    }

    public boolean updateToken(String token){
        try {
            SQLiteDatabase db = getInstance(context).getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put(K_JWT_TOKEN, token);
            db.update(TABLE_USER, v, null,null);
            db.close();
            return true;
        } catch (Exception e) {
            Log.d(t,e.toString());
            return false;
        }
    }

    public boolean insertUser(ObjectUser objectUser, boolean withBlob){
        try {
            SQLiteDatabase db = getInstance(context).getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put(K_NIP, objectUser.getNip());
            v.put(K_USERNAME, objectUser.getUsername());
            v.put(K_NAMA, objectUser.getNama());
            v.put(K_IS_PETUGAS, objectUser.getIsPetugas());
            v.put(K_JWT_TOKEN, objectUser.getJwtToken());
            v.put(K_URL_PHOTO, objectUser.getUrlPhoto());
            if(withBlob){
                v.put(K_DATA_PHOTO, objectUser.getDataPhotoBlob());
            }
            db.replace(TABLE_USER, null, v);
            db.close();
            return true;
        } catch (Exception e) {
            Log.d(t,e.toString());
            return false;
        }
    }

    public boolean updateUser(ObjectUser objectUser, boolean withBlob){
        try {
            SQLiteDatabase db = getInstance(context).getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put(K_NIP, objectUser.getNip());
            v.put(K_USERNAME, objectUser.getUsername());
            v.put(K_NAMA, objectUser.getNama());
            v.put(K_IS_PETUGAS, objectUser.getIsPetugas());
            v.put(K_JWT_TOKEN, objectUser.getJwtToken());
            v.put(K_URL_PHOTO, objectUser.getUrlPhoto());
            v.put(K_DATA_PHOTO, objectUser.getDataPhotoBlob());
            if(withBlob){
                v.put(K_DATA_PHOTO, objectUser.getDataPhotoBlob());
            }
            db.replace(TABLE_USER, null, v);
            db.close();
            return true;
        } catch (Exception e) {
            Log.d(t,e.toString());
            return false;
        }
    }

    public boolean deleteUser() {
        try {
            SQLiteDatabase db = getInstance(context).getWritableDatabase();
            db.execSQL("DELETE FROM "+TABLE_USER);
            db.close();
            return true;
        } catch (Exception e) {
            Log.d(t,e.toString());
            return false;
        }
    }

    public ObjectSurvei getLastSurvei(){
        String sql = "SELECT * FROM "+TABLE_SURVEI+" WHERE "+K_STATUS+"= '1' ORDER BY "+K_UID_SURVEI+" DESC LIMIT 1";
        try {
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                return new ObjectSurvei(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                        , cursor.getString(cursor.getColumnIndex(K_TAHUN))
                        , cursor.getString(cursor.getColumnIndex(K_BULAN))
                        , cursor.getString(cursor.getColumnIndex(K_MULAI))
                        , cursor.getString(cursor.getColumnIndex(K_AKHIR))
                        , cursor.getString(cursor.getColumnIndex(K_STATUS)));

            }else{
                return null;
            }
        }catch (Exception e){
            Log.d(t,e.toString());
            e.printStackTrace();
            return null;
        }finally {

        }
    }

    public ObjectSurvei getSurveiByUUID(String uuid){
        String sql = "SELECT * FROM "+TABLE_SURVEI+" WHERE "+K_UID_SURVEI+" = '"+uuid+"' LIMIT 1";
        try {
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                return new ObjectSurvei(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                        , cursor.getString(cursor.getColumnIndex(K_TAHUN))
                        , cursor.getString(cursor.getColumnIndex(K_BULAN))
                        , cursor.getString(cursor.getColumnIndex(K_MULAI))
                        , cursor.getString(cursor.getColumnIndex(K_AKHIR))
                        , cursor.getString(cursor.getColumnIndex(K_STATUS)));

            }else{
                return null;
            }
        }catch (Exception e){
            Log.d(t,e.toString());
            return null;
        }finally {

        }
    }

    public ArrayList<ObjectSurvei> getAllSurvei(){
        String sql = "SELECT * FROM "+TABLE_SURVEI+"";
        ArrayList<ObjectSurvei> objectSurveis = new ArrayList<>();
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                do{
                    objectSurveis.add(new ObjectSurvei(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                                , cursor.getString(cursor.getColumnIndex(K_TAHUN))
                                , cursor.getString(cursor.getColumnIndex(K_BULAN))
                                , cursor.getString(cursor.getColumnIndex(K_MULAI))
                                , cursor.getString(cursor.getColumnIndex(K_AKHIR))
                                , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                }while (cursor.moveToNext());
            }else{
                return null;
            }
            return objectSurveis;
        }catch (Exception e){
            Log.d(t,e.toString());
            return null;
        }finally {
            database.close();
            database = null;
        }
    }


    public boolean insertSurveis(ArrayList<ObjectSurvei> objectSurveis){
        for (ObjectSurvei item : objectSurveis){
            SQLiteDatabase db = getInstance(context).getWritableDatabase();
            try{
                ContentValues v = new ContentValues();
                v.put(K_UID_SURVEI, item.getUid_survei());
                v.put(K_TAHUN, item.getTahun());
                v.put(K_BULAN, item.getBulan());
                v.put(K_MULAI, item.getMulai());
                v.put(K_AKHIR, item.getAkhir());
                v.put(K_STATUS, "0");
                db.insert(TABLE_SURVEI, null, v);
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                db.close();
                db = null;
            }
        }
        return true;
    }

    public boolean insertSurvei(ObjectSurvei objectSurvei){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        try{
            db.beginTransaction();
            ContentValues v = new ContentValues();
            v.put(K_STATUS, "1");
            String where = K_UID_SURVEI+" = ? ";
            String [] whereArgs = new String [] {objectSurvei.getUid_survei()};
            db.update(TABLE_SURVEI,v, where, whereArgs);
            for(ObjectResponden itemResponden : objectSurvei.getObjectRespondens()){
                ContentValues vRes = new ContentValues();
                vRes.put(K_ID_RESPONDEN, itemResponden.getId());
                vRes.put(K_USERNAME, itemResponden.getUsername());
                vRes.put(K_NAMA, itemResponden.getNama());
                vRes.put(K_UID_SURVEI, itemResponden.getUid_survei());
                vRes.put(K_TIPE_RESPONDEN, itemResponden.getTipeResponden());
                vRes.put(K_IS_SELF_ENUM, itemResponden.getIsSelfEnum());
                vRes.put(K_NIP_PETUGAS, itemResponden.getNipPetugas());
                vRes.put(K_URL_PHOTO, itemResponden.getUrlPhoto());
                vRes.put(K_STATUS, itemResponden.getStatus());

                db.insert(TABLE_RESPONDEN, null, vRes);
                for(ObjectKualitas itemKualitas : itemResponden.getObjectKualitas()){
                    if(!itemKualitas.getTipe().equals("5") && !itemKualitas.getTipe().equals("7")){
                        ContentValues vKua = new ContentValues();
                        itemKualitas = (ObjectKualitasTipe1) itemKualitas;
                        vKua.put(K_UID_SURVEI, itemKualitas.getUid_survei());
                        vKua.put(K_ID_RESPONDEN, itemKualitas.getId_responden());
                        vKua.put(K_UID_BARANG, itemKualitas.getUid_barang());
                        vKua.put(K_BARANG, itemKualitas.getBarang());
                        vKua.put(K_UID_KUALITAS, itemKualitas.getUid_kualitas());
                        vKua.put(K_KUALITAS, itemKualitas.getKualitas());
                        vKua.put(K_TIPE, itemKualitas.getTipe());
                        vKua.put(K_KETERANGAN, itemKualitas.getKeterangan());
                        vKua.put(K_STATUS, itemKualitas.getStatus());

                        vKua.put(K_SATUAN_STANDAR, ((ObjectKualitasTipe1) itemKualitas).getSatuan_standar());
                        vKua.put(K_MERK, ((ObjectKualitasTipe1) itemKualitas).getMerk());
                        vKua.put(K_SATUAN_SETEMPAT, ((ObjectKualitasTipe1) itemKualitas).getSatuan_setempat());
                        vKua.put(K_UKURAN_PANJANG, ((ObjectKualitasTipe1) itemKualitas).getUkuran_panjang());
                        vKua.put(K_UKURAN_LEBAR, ((ObjectKualitasTipe1) itemKualitas).getUkuran_lebar());
                        vKua.put(K_UKURAN_TINGGI, ((ObjectKualitasTipe1) itemKualitas).getUkuran_tinggi());
                        vKua.put(K_UKURAN_BERAT, ((ObjectKualitasTipe1) itemKualitas).getUkuran_berat());
                        vKua.put(K_KONVERSI_SETEMPAT, ((ObjectKualitasTipe1) itemKualitas).getKonversi_setempat());
                        vKua.put(K_HARGA_SETEMPAT, ((ObjectKualitasTipe1) itemKualitas).getHarga_satuan_setempat());
                        vKua.put(K_HARGA_STANDAR, ((ObjectKualitasTipe1) itemKualitas).getHarga_satuan_standar());
                        db.insert(TABLE_KUALITAS_SATU, null, vKua);
                    }else {
                        ContentValues vKua = new ContentValues();
                        itemKualitas = (ObjectKualitasTipe2) itemKualitas;
                        vKua.put(K_UID_SURVEI, itemKualitas.getUid_survei());
                        vKua.put(K_ID_RESPONDEN, itemKualitas.getId_responden());
                        vKua.put(K_UID_BARANG, itemKualitas.getUid_barang());
                        vKua.put(K_BARANG, itemKualitas.getBarang());
                        vKua.put(K_UID_KUALITAS, itemKualitas.getUid_kualitas());
                        vKua.put(K_KUALITAS, itemKualitas.getKualitas());
                        vKua.put(K_TIPE, itemKualitas.getTipe());
                        vKua.put(K_KETERANGAN, itemKualitas.getKeterangan());
                        vKua.put(K_STATUS, itemKualitas.getStatus());

                        vKua.put(K_SATUAN_UNIT, ((ObjectKualitasTipe2) itemKualitas).getSatuan_unit());
                        vKua.put(K_NILAI_SEWA, ((ObjectKualitasTipe2) itemKualitas).getNilai_sewa());
                        db.insert(TABLE_KUALITAS_DUA, null, vKua);
                    }
                }
            }
            db.setTransactionSuccessful();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return true;
        }finally {
            db.endTransaction();
            db.close();
            db = null;
        }
    }

    public ArrayList<ObjectTipeHelper> getAllTipe(String uid_survei){
        String sql1 = "SELECT COUNT(*), "+K_TIPE_RESPONDEN+" FROM "+TABLE_RESPONDEN+" WHERE "+K_STATUS+" != '0' AND "
                    +K_UID_SURVEI+" = '"+uid_survei+"' GROUP BY "+K_TIPE_RESPONDEN;
        String sql2 = "SELECT COUNT(*), "+K_TIPE_RESPONDEN+" FROM "+TABLE_RESPONDEN+" WHERE "+K_UID_SURVEI+" = '"+uid_survei
                    +"' GROUP BY "+K_TIPE_RESPONDEN;
        ArrayList<ObjectTipeHelper> objectTipeHelpers = new ArrayList<>();
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery(sql2, null);
            if(cursor.moveToFirst()){
                do{
                    objectTipeHelpers.add(new ObjectTipeHelper(cursor.getString(1), 0,cursor.getInt(0)));
                }while (cursor.moveToNext());
            }
            Log.d("getalltipe", "m => "+objectTipeHelpers.size());
            cursor.close();
            cursor = null;
            cursor = database.rawQuery(sql1, null);
            if(cursor.moveToFirst()){
                do{
                    for (ObjectTipeHelper item : objectTipeHelpers){
                        if(item.getTipe().equals(cursor.getString(1))){
                            item.setCounterdone(cursor.getInt(0));
                            break;
                        }
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
            cursor = null;
            return objectTipeHelpers;
        }catch (Exception ex){
            Log.d("getalltipe", "e => "+ex);
            ex.printStackTrace();
            return null;
        }finally {
            database.close();
            database = null;
        }
    }

    public ArrayList<ObjectRespondenHelper> getAllRespondenFromSurveiByTipe(String uid_survei, String tipe){
        String sql = "SELECT * FROM "+TABLE_RESPONDEN+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "+K_TIPE_RESPONDEN+" = '"+tipe+"'";
        String sql1 = "SELECT COUNT(*), "+K_ID_RESPONDEN+", "+K_STATUS+" FROM "+TABLE_KUALITAS_SATU+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "
                +K_TIPE+" = '"+tipe+"' GROUP BY "+K_ID_RESPONDEN+","+K_STATUS;
        String sql2 = "SELECT COUNT(*), "+K_ID_RESPONDEN+", "+K_STATUS+" FROM "+TABLE_KUALITAS_DUA+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "
                +K_TIPE+" = '"+tipe+"' GROUP BY "+K_ID_RESPONDEN+","+K_STATUS;
        ArrayList<ObjectRespondenHelper> objectRespondenHelpers = new ArrayList<>();
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        try{
            Log.d("database",  "m => "+objectRespondenHelpers.size());
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                do{
                    objectRespondenHelpers.add(new ObjectRespondenHelper(new ObjectResponden(cursor.getInt(
                            cursor.getColumnIndex(K_ID_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_USERNAME))
                            , cursor.getString(cursor.getColumnIndex(K_NAMA))
                            , cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getString(cursor.getColumnIndex(K_TIPE_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_IS_SELF_ENUM))
                            , cursor.getString(cursor.getColumnIndex(K_NIP_PETUGAS))
                            , cursor.getString(cursor.getColumnIndex(K_URL_PHOTO))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS))),0,0,0,0,0,0));
                }while (cursor.moveToNext());
            }else{
                return null;
            }
            Log.d("database",  "1 = "+objectRespondenHelpers.size());
            cursor.close();
            cursor = null;
            //if(!tipe)
            cursor = database.rawQuery(sql1, null);
            if(cursor.moveToFirst()){
                do{
                    for(ObjectRespondenHelper item: objectRespondenHelpers){
                        if(item.getObjectResponden().getId() == cursor.getInt(1)){
                            if(cursor.getString(2).equals("0")){
                                item.setNotEdited(item.getNotEdited()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("1")){
                                item.setEditSalah(item.getEditSalah()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("2")){
                                item.setEditBenar(item.getEditBenar()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("3")){
                                item.setSudahDikirim(item.getSudahDikirim()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("4")){
                                item.setSubmit(item.getSubmit()+cursor.getInt(0));
                            }
                            item.setAll(item.getAll()+cursor.getInt(0));
                            break;
                        }
                    }
                }while (cursor.moveToNext());
            }else{
                //return null;
            }
            cursor.close();
            cursor = null;

            cursor = database.rawQuery(sql2, null);
            if(cursor.moveToFirst()){
                do{
                    for(ObjectRespondenHelper item: objectRespondenHelpers){
                        if(item.getObjectResponden().getId() == cursor.getInt(1)){
                            if(cursor.getString(2).equals("0")){
                                item.setNotEdited(item.getNotEdited()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("1")){
                                item.setEditSalah(item.getEditSalah()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("2")){
                                item.setEditBenar(item.getEditBenar()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("3")){
                                item.setSudahDikirim(item.getSudahDikirim()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("4")){
                                item.setSubmit(item.getSubmit()+cursor.getInt(0));
                            }
                            item.setAll(item.getAll()+cursor.getInt(0));
                            break;
                        }
                    }
                }while (cursor.moveToNext());
            }else{
                //return null;
            }
            cursor.close();
            cursor = null;
            Log.d("database",  "2 = "+objectRespondenHelpers.size());
            return objectRespondenHelpers;
        }catch (Exception ex){
            Log.d("database",""+ex);
            //ex.printStackTrace();
            return null;
        }finally {
            database.close();
            database = null;
        }
    }

    public ArrayList<ObjectSurveiHelper> getAllSurveiHelper(){
        String sql = "SELECT * FROM "+TABLE_SURVEI+" ";
        String sql1 = "SELECT COUNT(*), "+K_UID_SURVEI+", "+K_STATUS+" FROM "+TABLE_KUALITAS_SATU+" GROUP BY "+K_UID_SURVEI+","+K_STATUS;
        String sql2 = "SELECT COUNT(*), "+K_UID_SURVEI+", "+K_STATUS+" FROM "+TABLE_KUALITAS_DUA+" GROUP BY "+K_UID_SURVEI+","+K_STATUS;
        ArrayList<ObjectSurveiHelper> objectSurveiHelpers = new ArrayList<>();
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        try{
            Log.d("database",  "m => "+objectSurveiHelpers.size());
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                do{
                    objectSurveiHelpers.add(new ObjectSurveiHelper(new ObjectSurvei(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getString(cursor.getColumnIndex(K_TAHUN))
                            , cursor.getString(cursor.getColumnIndex(K_BULAN))
                            , cursor.getString(cursor.getColumnIndex(K_MULAI))
                            , cursor.getString(cursor.getColumnIndex(K_AKHIR))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS)))
                            ,0,0,0,0,0,0));
                }while (cursor.moveToNext());
            }else{
                return null;
            }
            Log.d("database",  "1 = "+objectSurveiHelpers.size());
            cursor.close();
            cursor = null;
            //if(!tipe)
            cursor = database.rawQuery(sql1, null);
            if(cursor.moveToFirst()){
                do{
                    for(ObjectSurveiHelper item: objectSurveiHelpers){
                        if(item.getObjectSurvei().getUid_survei().equals(cursor.getString(1))){
                            if(cursor.getString(2).equals("0")){
                                item.setNotEdited(item.getNotEdited()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("1")){
                                item.setEditSalah(item.getEditSalah()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("2")){
                                item.setEditBenar(item.getEditBenar()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("3")){
                                item.setSudahDikirim(item.getSudahDikirim()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("4")){
                                item.setSubmit(item.getSubmit()+cursor.getInt(0));
                            }
                            item.setAll(item.getAll()+cursor.getInt(0));
                            break;
                        }
                    }
                }while (cursor.moveToNext());
            }else{
                //return null;
            }
            cursor.close();
            cursor = null;

            cursor = database.rawQuery(sql2, null);
            if(cursor.moveToFirst()){
                do{
                    for(ObjectSurveiHelper item: objectSurveiHelpers){
                        if(item.getObjectSurvei().getUid_survei().equals(cursor.getString(1))){
                            if(cursor.getString(2).equals("0")){
                                item.setNotEdited(item.getNotEdited()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("1")){
                                item.setEditSalah(item.getEditSalah()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("2")){
                                item.setEditBenar(item.getEditBenar()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("3")){
                                item.setSudahDikirim(item.getSudahDikirim()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("4")){
                                item.setSubmit(item.getSubmit()+cursor.getInt(0));
                            }
                            item.setAll(item.getAll()+cursor.getInt(0));
                            break;
                        }
                    }
                }while (cursor.moveToNext());
            }else{
                //return null;
            }
            cursor.close();
            cursor = null;
            Log.d("database",  "2 = "+objectSurveiHelpers.size());
            return objectSurveiHelpers;
        }catch (Exception ex){
            Log.d("database",""+ex);
            //ex.printStackTrace();
            return null;
        }finally {
            database.close();
            database = null;
        }
    }

    public ArrayList<ObjectRespondenHelper> getOneRespondenFromSurveiByTipe(String uid_survei, String tipe, String idResponden){
        String sql = "SELECT * FROM "+TABLE_RESPONDEN+" WHERE "+K_UID_SURVEI+" = '"+uid_survei
                +"' AND "+K_TIPE_RESPONDEN+" = '"+tipe+"' AND "+K_ID_RESPONDEN+" = "+idResponden+"";
        String sql1 = "SELECT COUNT(*), "+K_ID_RESPONDEN+", "+K_STATUS+" FROM "+TABLE_KUALITAS_SATU+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "
                +K_TIPE_RESPONDEN+" = '"+tipe+"' AND "+K_ID_RESPONDEN+" = "+idResponden+" GROUP BY "+K_STATUS;
        String sql2 = "SELECT COUNT(*), "+K_ID_RESPONDEN+", "+K_STATUS+" FROM "+TABLE_KUALITAS_DUA+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "
                +K_TIPE_RESPONDEN+" = '"+tipe+"' AND "+K_ID_RESPONDEN+" = "+idResponden+" GROUP BY "+K_STATUS;
        ArrayList<ObjectRespondenHelper> objectRespondenHelpers = new ArrayList<>();
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                do{
                    objectRespondenHelpers.add(new ObjectRespondenHelper(new ObjectResponden(cursor.getInt(
                            cursor.getColumnIndex(K_ID_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_USERNAME))
                            , cursor.getString(cursor.getColumnIndex(K_NAMA))
                            , cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getString(cursor.getColumnIndex(K_TIPE_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_IS_SELF_ENUM))
                            , cursor.getString(cursor.getColumnIndex(K_NIP_PETUGAS))
                            , cursor.getString(cursor.getColumnIndex(K_URL_PHOTO))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS))),0,0,0,0,0,0));
                }while (cursor.moveToNext());
            }else{
                return null;
            }
            cursor.close();
            cursor = null;

            cursor = database.rawQuery(sql1, null);
            if(cursor.moveToFirst()){
                do{
                    for(ObjectRespondenHelper item: objectRespondenHelpers){
                        if(item.getObjectResponden().getId() == cursor.getInt(1)){
                            if(cursor.getString(2).equals("0")){
                                item.setNotEdited(item.getNotEdited()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("1")){
                                item.setEditSalah(item.getEditSalah()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("2")){
                                item.setEditBenar(item.getEditBenar()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("3")){
                                item.setSudahDikirim(item.getSudahDikirim()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("4")){
                                item.setSubmit(item.getSubmit()+cursor.getInt(0));
                            }
                            item.setAll(item.getAll()+cursor.getInt(0));
                            break;
                        }
                    }
                }while (cursor.moveToNext());
            }else{

            }
            cursor.close();
            cursor = null;

            cursor = database.rawQuery(sql2, null);
            if(cursor.moveToFirst()){
                do{
                    for(ObjectRespondenHelper item: objectRespondenHelpers){
                        if(item.getObjectResponden().getId() == cursor.getInt(1)){
                            if(cursor.getString(2).equals("0")){
                                item.setNotEdited(item.getNotEdited()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("1")){
                                item.setEditSalah(item.getEditSalah()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("2")){
                                item.setEditBenar(item.getEditBenar()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("3")){
                                item.setSudahDikirim(item.getSudahDikirim()+cursor.getInt(0));
                            }else if(cursor.getString(2).equals("4")){
                                item.setSubmit(item.getSubmit()+cursor.getInt(0));
                            }
                            item.setAll(item.getAll()+cursor.getInt(0));
                            break;
                        }
                    }
                }while (cursor.moveToNext());
            }else{
            }
            cursor.close();
            cursor = null;
            return objectRespondenHelpers;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
            database.close();
            database = null;
        }
    }

    public ObjectResponden getOneResponden(String uid_survei, String tipe, int idResponden){
        String sql = "SELECT * FROM "+TABLE_RESPONDEN+" WHERE "+K_UID_SURVEI+" = '"+uid_survei
                +"' AND "+K_TIPE_RESPONDEN+" = '"+tipe+"' AND "+K_ID_RESPONDEN+" = "+idResponden+"";
        //ArrayList<ObjectRespondenHelper> objectRespondenHelpers = new ArrayList<>();
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                return new ObjectResponden(cursor.getInt(
                        cursor.getColumnIndex(K_ID_RESPONDEN))
                        , cursor.getString(cursor.getColumnIndex(K_USERNAME))
                        , cursor.getString(cursor.getColumnIndex(K_NAMA))
                        , cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                        , cursor.getString(cursor.getColumnIndex(K_TIPE_RESPONDEN))
                        , cursor.getString(cursor.getColumnIndex(K_IS_SELF_ENUM))
                        , cursor.getString(cursor.getColumnIndex(K_NIP_PETUGAS))
                        , cursor.getString(cursor.getColumnIndex(K_URL_PHOTO))
                        , cursor.getString(cursor.getColumnIndex(K_STATUS)));
            }else{
                return null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
            database.close();
            database = null;
        }
    }

    public ArrayList<ObjectKualitas> getKualitas(String uid_survei, int id_responden, String tipe){
        if(!tipe.equals("5") && !tipe.equals("7")){
            String sql = "SELECT * FROM "+TABLE_KUALITAS_SATU+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "+K_ID_RESPONDEN+" = "+id_responden
                    +" AND "+K_TIPE+" = '"+tipe+"'";

            ArrayList<ObjectKualitas> objectKualitas = new ArrayList<>();
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            try{
                Cursor cursor = database.rawQuery(sql, null);
                if(cursor.moveToFirst()){
                    do{
                        objectKualitas.add(new ObjectKualitasTipe1(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                                , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                                , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_TIPE))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_STANDAR))
                                , cursor.getString(cursor.getColumnIndex(K_MERK))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_PANJANG))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_LEBAR))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_TINGGI))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_BERAT))
                                , cursor.getString(cursor.getColumnIndex(K_KONVERSI_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_HARGA_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_HARGA_STANDAR))
                                , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                                , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                    }while (cursor.moveToNext());
                }else{
                    return null;
                }
                cursor.close();
                cursor = null;
                return objectKualitas;
            }catch (Exception ex){
                Log.e("database", ""+ex);
                //ex.printStackTrace();
                return null;
            }finally {
                database.close();
                database = null;
            }
        }else{
            String sql = "SELECT * FROM "+TABLE_KUALITAS_DUA+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "+K_ID_RESPONDEN+" = "+id_responden
                    +" AND "+K_TIPE+" = '"+tipe+"'";

            ArrayList<ObjectKualitas> objectKualitas = new ArrayList<>();
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            try{
                Cursor cursor = database.rawQuery(sql, null);
                if(cursor.moveToFirst()){
                    do{
                        objectKualitas.add(new ObjectKualitasTipe2(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                                , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                                , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_TIPE))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_UNIT))
                                , cursor.getString(cursor.getColumnIndex(K_NILAI_SEWA))
                                , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                                , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                    }while (cursor.moveToNext());
                }else{
                    return null;
                }
                cursor.close();
                cursor = null;
                return objectKualitas;
            }catch (Exception ex){
//                ex.printStackTrace();
                Log.e("database", ""+ex);
                return null;
            }finally {
                database.close();
                database = null;
            }
        }
    }

    public ArrayList<ObjectKualitas> getKualitasFilter(String uid_survei, int id_responden, String tipe, String search){
        if(!tipe.equals("5") && !tipe.equals("7")){
            String sql = "SELECT * FROM "+TABLE_KUALITAS_SATU+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "+K_ID_RESPONDEN+" = "+id_responden
                    +" AND "+K_TIPE+" = "+tipe+" AND ("+K_BARANG+" LIKE '%"+search+"%' OR "+K_KUALITAS+" LIKE '%"+search+"%')";

            ArrayList<ObjectKualitas> objectKualitas = new ArrayList<>();
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            try{
                Cursor cursor = database.rawQuery(sql, null);
                if(cursor.moveToFirst()){
                    do{
                        objectKualitas.add(new ObjectKualitasTipe1(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                                , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                                , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_TIPE))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_STANDAR))
                                , cursor.getString(cursor.getColumnIndex(K_MERK))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_PANJANG))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_LEBAR))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_TINGGI))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_BERAT))
                                , cursor.getString(cursor.getColumnIndex(K_KONVERSI_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_HARGA_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_HARGA_STANDAR))
                                , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                                , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                    }while (cursor.moveToNext());
                }else{
                    return null;
                }
                cursor.close();
                cursor = null;
                return objectKualitas;
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }finally {
                database.close();
                database = null;
            }
        }else{
            String sql = "SELECT * FROM "+TABLE_KUALITAS_DUA+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' AND "+K_ID_RESPONDEN+" = "+id_responden
                    +" AND "+K_TIPE+" = "+tipe+" AND ("+K_BARANG+" LIKE '%"+search+"%' OR "+K_KUALITAS+" LIKE '%"+search+"%')";

            ArrayList<ObjectKualitas> objectKualitas = new ArrayList<>();
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            try{
                Cursor cursor = database.rawQuery(sql, null);
                if(cursor.moveToFirst()){
                    do{
                        objectKualitas.add(new ObjectKualitasTipe2(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                                , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                                , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_TIPE))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_UNIT))
                                , cursor.getString(cursor.getColumnIndex(K_NILAI_SEWA))
                                , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                                , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                    }while (cursor.moveToNext());
                }else{
                    return null;
                }
                cursor.close();
                cursor = null;
                return objectKualitas;
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }finally {
                database.close();
                database = null;
            }
        }
    }

    public boolean updateKualitas(ObjectKualitas itemKualitas){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        try{
            db.beginTransaction();
            if(!itemKualitas.getTipe().equals("5") && !itemKualitas.getTipe().equals("7")){
                String where = K_UID_SURVEI+ " = ? AND "+K_ID_RESPONDEN+" = ? AND "
                        +K_UID_BARANG+" = ? AND "+K_UID_KUALITAS+" = ? ";
                String [] whereAgs = new String[] {itemKualitas.getUid_survei(), ""+itemKualitas.getId_responden()
                        , itemKualitas.getUid_barang(), itemKualitas.getUid_kualitas()};
                itemKualitas = (ObjectKualitasTipe1) itemKualitas;

                ContentValues vKua = new ContentValues();
                vKua.put(K_SATUAN_STANDAR, ((ObjectKualitasTipe1) itemKualitas).getSatuan_standar());
                vKua.put(K_MERK, ((ObjectKualitasTipe1) itemKualitas).getMerk());
                vKua.put(K_SATUAN_SETEMPAT, ((ObjectKualitasTipe1) itemKualitas).getSatuan_setempat());
                vKua.put(K_UKURAN_PANJANG, ((ObjectKualitasTipe1) itemKualitas).getUkuran_panjang());
                vKua.put(K_UKURAN_LEBAR, ((ObjectKualitasTipe1) itemKualitas).getUkuran_lebar());
                vKua.put(K_UKURAN_TINGGI, ((ObjectKualitasTipe1) itemKualitas).getUkuran_tinggi());
                vKua.put(K_UKURAN_BERAT, ((ObjectKualitasTipe1) itemKualitas).getUkuran_berat());
                vKua.put(K_KONVERSI_SETEMPAT, ((ObjectKualitasTipe1) itemKualitas).getKonversi_setempat());
                vKua.put(K_HARGA_SETEMPAT, ((ObjectKualitasTipe1) itemKualitas).getHarga_satuan_setempat());
                vKua.put(K_HARGA_STANDAR, ((ObjectKualitasTipe1) itemKualitas).getHarga_satuan_standar());
                vKua.put(K_KETERANGAN, itemKualitas.getKeterangan());
                vKua.put(K_STATUS, itemKualitas.getStatus());
                db.update(TABLE_KUALITAS_SATU, vKua, where, whereAgs);
            }else {

                String where = K_UID_SURVEI+ " = ? AND "+K_ID_RESPONDEN+" = ? AND "
                        +K_UID_BARANG+" = ? AND "+K_UID_KUALITAS+" = ? ";
                String [] whereAgs = new String[] {itemKualitas.getUid_survei(), ""+itemKualitas.getId_responden()
                        , itemKualitas.getUid_barang(), itemKualitas.getUid_kualitas()};

                ContentValues vKua = new ContentValues();
                itemKualitas = (ObjectKualitasTipe2) itemKualitas;
                vKua.put(K_SATUAN_UNIT, ((ObjectKualitasTipe2) itemKualitas).getSatuan_unit());
                vKua.put(K_NILAI_SEWA, ((ObjectKualitasTipe2) itemKualitas).getNilai_sewa());
                vKua.put(K_KETERANGAN, itemKualitas.getKeterangan());
                vKua.put(K_STATUS, itemKualitas.getStatus());
                db.update(TABLE_KUALITAS_DUA, vKua, where, whereAgs);
            }
            db.setTransactionSuccessful();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            db.endTransaction();
            db.close();
            db = null;
        }
    }

    public JSONArray getEditedKualitasResponden(ObjectResponden objectResponden){
        if(!objectResponden.getTipeResponden().equals("5") && !objectResponden.getTipeResponden().equals("7")){
            String sql = "SELECT * FROM "+TABLE_KUALITAS_SATU+" WHERE "+K_UID_SURVEI+" = '"+objectResponden.getUid_survei()+"' AND "+K_ID_RESPONDEN+" = "+objectResponden.getId()
                    +" AND "+K_STATUS+" = '2' ";

            JSONArray editedArray = new JSONArray();
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            try{
                Cursor cursor = database.rawQuery(sql, null);
                if(cursor.moveToFirst()){
                    do{
                        ObjectKualitasTipe1 objectKualitasTipe1 = new ObjectKualitasTipe1(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                                , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                                , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_TIPE))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_STANDAR))
                                , cursor.getString(cursor.getColumnIndex(K_MERK))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_PANJANG))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_LEBAR))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_TINGGI))
                                , cursor.getString(cursor.getColumnIndex(K_UKURAN_BERAT))
                                , cursor.getString(cursor.getColumnIndex(K_KONVERSI_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_HARGA_SETEMPAT))
                                , cursor.getString(cursor.getColumnIndex(K_HARGA_STANDAR))
                                , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                                , cursor.getString(cursor.getColumnIndex(K_STATUS)));
                        JSONObject objectKualitasTipe1JSON = objectKualitasTipe1.getJSONObject();
                        if(objectKualitasTipe1JSON!=null){
                            editedArray.put(objectKualitasTipe1JSON);
                        }
                    }while (cursor.moveToNext());
                }else{
                    return editedArray;
                }
                cursor.close();
                cursor = null;
                return editedArray;
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }finally {
                database.close();
                database = null;
            }
        }else{
            String sql = "SELECT * FROM "+TABLE_KUALITAS_DUA+" WHERE "+K_UID_SURVEI+" = '"+objectResponden.getUid_survei()+"' AND "+K_ID_RESPONDEN+" = "+objectResponden.getId()
                    +" AND "+K_STATUS+" = '2' ";

            JSONArray editedArray = new JSONArray();
            SQLiteDatabase database = getInstance(context).getReadableDatabase();
            try{
                Cursor cursor = database.rawQuery(sql, null);
                if(cursor.moveToFirst()){
                    do{
                        ObjectKualitasTipe2 objectKualitasTipe2 = new ObjectKualitasTipe2(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                                , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                                , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_BARANG))
                                , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                                , cursor.getString(cursor.getColumnIndex(K_TIPE))
                                , cursor.getString(cursor.getColumnIndex(K_SATUAN_UNIT))
                                , cursor.getString(cursor.getColumnIndex(K_NILAI_SEWA))
                                , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                                , cursor.getString(cursor.getColumnIndex(K_STATUS)));
                        JSONObject objectKualitasTipe2JSON = objectKualitasTipe2.getJSONObject();
                        if(objectKualitasTipe2JSON!=null){
                            editedArray.put(objectKualitasTipe2JSON);
                        }
                    }while (cursor.moveToNext());
                }else{
                    return editedArray;
                }
                cursor.close();
                cursor = null;
                return editedArray;
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }finally {
                database.close();
                database = null;
            }
        }
    }

    public boolean updateKualitasRefresh(String uid_survei, int id_responden){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        try{
            db.beginTransaction();
            String where = K_UID_SURVEI+ " = ? AND "+K_ID_RESPONDEN+" = ? AND "+K_STATUS+" = ?";
            String [] whereAgs = new String[] {uid_survei, ""+id_responden, "2"};

            ContentValues vKua = new ContentValues();
            vKua.put(K_STATUS, "3");
            db.update(TABLE_KUALITAS_SATU, vKua, where, whereAgs);
            db.update(TABLE_KUALITAS_DUA, vKua, where, whereAgs);
            db.setTransactionSuccessful();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            db.endTransaction();
        }
    }

    public boolean updateKualitasSubmit(String uid_survei, int id_responden){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        try{
            db.beginTransaction();
            String where = K_UID_SURVEI+ " = ? AND "+K_ID_RESPONDEN+" = ? ";
            String [] whereAgs = new String[] {uid_survei, ""+id_responden};

            ContentValues vKua = new ContentValues();
            vKua.put(K_STATUS, "4");
            db.update(TABLE_KUALITAS_SATU, vKua, where, whereAgs);
            db.update(TABLE_KUALITAS_DUA, vKua, where, whereAgs);

            ContentValues vRes = new ContentValues();
            vRes.put(K_STATUS, "1");
            String where2 = K_UID_SURVEI+ " = ? AND "+K_ID_RESPONDEN+" = ? ";
            String [] whereAgs2 = new String[] {uid_survei, ""+id_responden};
            db.update(TABLE_RESPONDEN, vRes, where2, whereAgs2);
            db.setTransactionSuccessful();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            db.endTransaction();
        }
    }

    public JSONArray getEditedKualitasSurvei(ObjectSurvei objectSurvei){
        JSONArray editedArray = new JSONArray();
        String sql = "SELECT * FROM "+TABLE_KUALITAS_SATU+" WHERE "+K_UID_SURVEI+" = '"+objectSurvei.getUid_survei()
                +"' AND "+K_STATUS+" = '2' ";
        String sql1 = "SELECT * FROM "+TABLE_KUALITAS_DUA+" WHERE "+K_UID_SURVEI+" = '"+objectSurvei.getUid_survei()
                +"' AND "+K_STATUS+" = '2' ";

        SQLiteDatabase database = getInstance(context).getReadableDatabase();

        try{
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                do{
                    ObjectKualitasTipe1 objectKualitasTipe1 = new ObjectKualitasTipe1(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_TIPE))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_STANDAR))
                            , cursor.getString(cursor.getColumnIndex(K_MERK))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_PANJANG))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_LEBAR))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_TINGGI))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_BERAT))
                            , cursor.getString(cursor.getColumnIndex(K_KONVERSI_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_HARGA_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_HARGA_STANDAR))
                            , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS)));
                    JSONObject objectKualitasTipe1JSON = objectKualitasTipe1.getJSONObject();
                    if(objectKualitasTipe1JSON!=null){
                        editedArray.put(objectKualitasTipe1JSON);
                    }
                }while (cursor.moveToNext());
            }

            cursor = database.rawQuery(sql1, null);
            if(cursor.moveToFirst()){
                do{
                    ObjectKualitasTipe2 objectKualitasTipe2 = new ObjectKualitasTipe2(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_TIPE))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_UNIT))
                            , cursor.getString(cursor.getColumnIndex(K_NILAI_SEWA))
                            , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS)));
                    JSONObject objectKualitasTipe2JSON = objectKualitasTipe2.getJSONObject();
                    if(objectKualitasTipe2JSON!=null){
                        editedArray.put(objectKualitasTipe2JSON);
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
            cursor = null;
            return editedArray;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
            database.close();
            database = null;
        }
    }

    public boolean updateKualitasRefreshSurvei(String uid_survei){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        try{
            db.beginTransaction();
            String where = K_UID_SURVEI+ " = ?  AND "+K_STATUS+" = ?";
            String [] whereAgs = new String[] {uid_survei, "2"};

            ContentValues vKua = new ContentValues();
            vKua.put(K_STATUS, "3");
            db.update(TABLE_KUALITAS_SATU, vKua, where, whereAgs);
            db.update(TABLE_KUALITAS_DUA, vKua, where, whereAgs);
            db.setTransactionSuccessful();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            db.endTransaction();
        }
    }

    public boolean updateKualitasSubmitSurvei(String uid_survei){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        try{
            db.beginTransaction();
            String where = K_UID_SURVEI+ " = ?  ";
            String [] whereAgs = new String[] {uid_survei};
            ContentValues vKua = new ContentValues();
            vKua.put(K_STATUS, "4");
            db.update(TABLE_KUALITAS_SATU, vKua, where, whereAgs);
            db.update(TABLE_KUALITAS_DUA, vKua, where, whereAgs);

            ContentValues vRes = new ContentValues();
            vRes.put(K_STATUS, "1");
            String where2 = K_UID_SURVEI+ " = ? ";
            String [] whereAgs2 = new String[] {uid_survei};
            db.update(TABLE_RESPONDEN, vRes, where2, whereAgs2);
            db.setTransactionSuccessful();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            db.endTransaction();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM "+TABLE_SURVEI);
            db.execSQL("DELETE FROM "+TABLE_USER);
            db.execSQL("DELETE FROM "+TABLE_RESPONDEN);
            db.execSQL("DELETE FROM "+TABLE_KUALITAS_SATU);
            db.execSQL("DELETE FROM "+TABLE_KUALITAS_DUA);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DEBUG",e.toString());
            e.printStackTrace();
        }finally {
            db.endTransaction();
            db.close();
            db = null;
        }
    }

    public ArrayList<ObjectKualitas> getKualitasSurvei(String uid_survei){
        String sql = "SELECT * FROM "+TABLE_KUALITAS_SATU+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' ";

        String sql1 = "SELECT * FROM "+TABLE_KUALITAS_DUA+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' ";

        ArrayList<ObjectKualitas> objectKualitas = new ArrayList<>();
        SQLiteDatabase database = getInstance(context).getReadableDatabase();

        try{
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                do{
                    objectKualitas.add(new ObjectKualitasTipe1(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_TIPE))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_STANDAR))
                            , cursor.getString(cursor.getColumnIndex(K_MERK))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_PANJANG))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_LEBAR))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_TINGGI))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_BERAT))
                            , cursor.getString(cursor.getColumnIndex(K_KONVERSI_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_HARGA_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_HARGA_STANDAR))
                            , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                }while (cursor.moveToNext());
            }
            cursor.close();
            cursor = null;

            cursor = database.rawQuery(sql1, null);
            if(cursor.moveToFirst()){
                do{
                    objectKualitas.add(new ObjectKualitasTipe2(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_TIPE))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_UNIT))
                            , cursor.getString(cursor.getColumnIndex(K_NILAI_SEWA))
                            , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                }while (cursor.moveToNext());
            }
            cursor.close();
            cursor = null;

            return objectKualitas;
        }catch (Exception ex){
            Log.e("database", ""+ex);
            //ex.printStackTrace();
            return null;
        }finally {
            database.close();
            database = null;
        }
    }

    public ArrayList<ObjectKualitas> getKualitasSurveiFilter(String uid_survei, String search){
        String sql = "SELECT * FROM "+TABLE_KUALITAS_SATU+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' "
                +" AND ("+K_BARANG+" LIKE '%"+search+"%' OR "+K_KUALITAS+" LIKE '%"+search+"%')";
        String sql1 = "SELECT * FROM "+TABLE_KUALITAS_DUA+" WHERE "+K_UID_SURVEI+" = '"+uid_survei+"' "
                +" AND ("+K_BARANG+" LIKE '%"+search+"%' OR "+K_KUALITAS+" LIKE '%"+search+"%')";

        ArrayList<ObjectKualitas> objectKualitas = new ArrayList<>();
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery(sql, null);
            if(cursor.moveToFirst()){
                do{
                    objectKualitas.add(new ObjectKualitasTipe1(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_TIPE))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_STANDAR))
                            , cursor.getString(cursor.getColumnIndex(K_MERK))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_PANJANG))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_LEBAR))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_TINGGI))
                            , cursor.getString(cursor.getColumnIndex(K_UKURAN_BERAT))
                            , cursor.getString(cursor.getColumnIndex(K_KONVERSI_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_HARGA_SETEMPAT))
                            , cursor.getString(cursor.getColumnIndex(K_HARGA_STANDAR))
                            , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                }while (cursor.moveToNext());
            }
            cursor.close();
            cursor = null;

            cursor = database.rawQuery(sql1, null);
            if(cursor.moveToFirst()){
                do{
                    objectKualitas.add(new ObjectKualitasTipe2(cursor.getString(cursor.getColumnIndex(K_UID_SURVEI))
                            , cursor.getInt(cursor.getColumnIndex(K_ID_RESPONDEN))
                            , cursor.getString(cursor.getColumnIndex(K_UID_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_BARANG))
                            , cursor.getString(cursor.getColumnIndex(K_UID_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_KUALITAS))
                            , cursor.getString(cursor.getColumnIndex(K_TIPE))
                            , cursor.getString(cursor.getColumnIndex(K_SATUAN_UNIT))
                            , cursor.getString(cursor.getColumnIndex(K_NILAI_SEWA))
                            , cursor.getString(cursor.getColumnIndex(K_KETERANGAN))
                            , cursor.getString(cursor.getColumnIndex(K_STATUS))));
                }while (cursor.moveToNext());
            }
            cursor.close();
            cursor = null;
            return objectKualitas;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
            database.close();
            database = null;
        }
    }

    public void deleteSurveis(ArrayList<String> uuids){
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        try {
            db.beginTransaction();
            for(int i=0; i<uuids.size(); i++){
                String where = K_UID_SURVEI+" = ?";
                String [] whereArgs = new String[]{uuids.get(i)};
                db.delete(TABLE_SURVEI,where, whereArgs);
                db.delete(TABLE_RESPONDEN,where, whereArgs);
                db.delete(TABLE_KUALITAS_SATU,where, whereArgs);
                db.delete(TABLE_KUALITAS_DUA,where, whereArgs);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DEBUG",e.toString());
            e.printStackTrace();
        }finally {
            db.endTransaction();
            db.close();
            db = null;
        }
    }

}
