package id.go.bps.mamasa.vikkand.Asset;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.go.bps.mamasa.vikkand.Entity.ObjectKualitasTipe1;
import id.go.bps.mamasa.vikkand.Entity.ObjectKualitasTipe2;
import id.go.bps.mamasa.vikkand.Entity.ObjectResponden;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurvei;
import id.go.bps.mamasa.vikkand.Entity.ObjectUser;
import id.go.bps.mamasa.vikkand.LoginActivity;
import id.go.bps.mamasa.vikkand.PilihJenisResponden;
import id.go.bps.mamasa.vikkand.PilihKualitas;
import id.go.bps.mamasa.vikkand.PilihKualitasResponden;
import id.go.bps.mamasa.vikkand.PilihResponden;
import id.go.bps.mamasa.vikkand.PilihSurveiActivity;
import id.go.bps.mamasa.vikkand.PilihSurveiRespondenActivity;

/**
 * Created by ASUS on 28/06/2018.
 */

public class GetData {

    private Activity myActivity;
    private static final String TAG = "GetDataTAG";
    public GetData(Activity myActivity){
        this.myActivity = myActivity;
    }

    public void getLogin(final boolean isPetugas, final String username, final String password){
        StringRequest strReq = new StringRequest(Request.Method.POST, URLPath.getUrlAuthLogin(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-LOGIN", response.toString());
                try{
                    JSONObject hasil = new JSONObject(response);
                    getMe(hasil.getString("access_token"), isPetugas);
                }catch (Exception ex){
                    ex.printStackTrace();
                    ((LoginActivity)myActivity).failLogin(0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, error.getMessage());
                if(error.networkResponse==null){
                    ((LoginActivity)myActivity).failLogin(0);
                }else{
                    if(error.networkResponse.statusCode==401){
                        ((LoginActivity)myActivity).failLogin(1);
                    }else{
                        ((LoginActivity)myActivity).failLogin(0);
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                if(isPetugas){
                    params.put("nip", username);
                }else{
                    params.put("username", username);
                }
                params.put("password", password);

                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }
    public void getMe(final String token, final boolean isPetugas){
        StringRequest strReq = new StringRequest(Request.Method.POST,URLPath.getUrlAuthme(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-ME", response.toString());
                try{
                    JSONObject hasil = new JSONObject(response);
                    if(isPetugas){
                        ObjectUser objectUser = new ObjectUser(hasil.getString("nip"), null
                                , "1", hasil.getString("nama")
                                , token, hasil.getString("photo") );
                        Database.getInstance(myActivity).insertUser(objectUser, isPetugas);
                        ((LoginActivity)myActivity).successLogin(objectUser);
                    }else{
                        ObjectUser objectUser = new ObjectUser(null, hasil.getString("username")
                                , "0", hasil.getString("nama")
                                , token, hasil.getString("photo") );
                        Database.getInstance(myActivity).insertUser(objectUser, isPetugas);
                        ((LoginActivity)myActivity).successLogin(objectUser);
                    }
                }catch (Exception ex){
                    Log.e(TAG,""+ex);
                    ((LoginActivity)myActivity).failLogin(0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, error.getMessage());
                if(error.networkResponse==null){
                    ((LoginActivity)myActivity).failLogin(0);
                }else{
                    if(error.networkResponse.statusCode==401){
                        ((LoginActivity)myActivity).failLogin(2);
                    }else{
                        ((LoginActivity)myActivity).failLogin(0);
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }

    public void getLogout(final String token, final boolean isPetugas){
        StringRequest strReq = new StringRequest(Request.Method.POST,URLPath.getUrlAuthLogout(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-LOGOUT", response.toString());
                try{
                    Database.getInstance(myActivity).deleteAllData();
                    if(myActivity instanceof PilihSurveiActivity){
                        ((PilihSurveiActivity)myActivity).successLogout();
                    }else if(myActivity instanceof PilihJenisResponden){
                        ((PilihJenisResponden)myActivity).successLogout();
                    }else if(myActivity instanceof PilihSurveiRespondenActivity){
                        ((PilihSurveiRespondenActivity)myActivity).successLogout();
                    }
                }catch (Exception ex){
                    Log.e(TAG,""+ex);
                    if(myActivity instanceof PilihSurveiActivity){
                        ((PilihSurveiActivity)myActivity).failLogout();
                    }else if(myActivity instanceof PilihJenisResponden){
                        ((PilihJenisResponden)myActivity).failLogout();
                    }else if(myActivity instanceof PilihSurveiRespondenActivity){
                        ((PilihSurveiRespondenActivity)myActivity).failLogout();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(myActivity instanceof PilihSurveiActivity){
                    ((PilihSurveiActivity)myActivity).failLogout();
                }else if(myActivity instanceof PilihJenisResponden){
                    ((PilihJenisResponden)myActivity).failLogout();
                }else if(myActivity instanceof PilihSurveiRespondenActivity){
                    ((PilihSurveiRespondenActivity)myActivity).failLogout();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }

    public void getListSurvei(final String token, final boolean isPetugas){
        StringRequest strReq = new StringRequest(Request.Method.POST,URLPath.getUrlGetSurvei(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-GET LIST", response.toString());
                try{
                    JSONArray hasil = new JSONArray(response);
                    ArrayList<ObjectSurvei> objectSurveis = new ArrayList<>();
                    for(int i=0; i<hasil.length(); i++){
                        JSONObject item = hasil.getJSONObject(i);
                        ObjectSurvei objectSurvei = new ObjectSurvei(item.getString("uid"), item.getString("tahun")
                                ,item.getString("bulan"), item.getString("mulai"),item.getString("akhir")
                                ,"0");
                        objectSurveis.add(objectSurvei);
                    }
                    ArrayList<ObjectSurvei> lamass = Database.getInstance(myActivity).getAllSurvei();
                    ArrayList<String> deleteUuid = new ArrayList<>();
                    if(lamass!=null){
                        for(int i=0; i<lamass.size(); i++){
                            boolean ada = false;
                            for(int j=0; j<objectSurveis.size(); j++){
                                if(lamass.get(i).getUid_survei().equals(objectSurveis.get(j).getUid_survei())){
                                    ada = true;
                                    break;
                                }
                            }
                            if(!ada){
                                deleteUuid.add(lamass.get(i).getUid_survei());
                            }
                        }
                    }

                    Database.getInstance(myActivity).insertSurveis(objectSurveis);
                    Database.getInstance(myActivity).deleteSurveis(deleteUuid);
                    if(myActivity instanceof PilihSurveiActivity){
                        ((PilihSurveiActivity)myActivity).successGetListSurvei(0);
                    }else if(myActivity instanceof PilihSurveiRespondenActivity){
                        ((PilihSurveiRespondenActivity)myActivity).successGetListSurvei(0);
                    }
                }catch (Exception ex){
                    Log.e(TAG,""+ex);
                    if(myActivity instanceof PilihSurveiActivity){
                        ((PilihSurveiActivity)myActivity).failGetListSurvei(0);
                    }else if(myActivity instanceof PilihSurveiRespondenActivity){
                        ((PilihSurveiRespondenActivity)myActivity).failGetListSurvei(0);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse==null){
                    if(myActivity instanceof PilihSurveiActivity){
                        ((PilihSurveiActivity)myActivity).failGetListSurvei(0);
                    }else if(myActivity instanceof PilihSurveiRespondenActivity){
                        ((PilihSurveiRespondenActivity)myActivity).failGetListSurvei(0);
                    }
                }else{
                    if(error.networkResponse.statusCode==401){
                        if(myActivity instanceof PilihSurveiActivity){
                            ((PilihSurveiActivity)myActivity).failGetListSurvei(2);
                        }else if(myActivity instanceof PilihSurveiRespondenActivity) {
                            ((PilihSurveiRespondenActivity)myActivity).failGetListSurvei(2);
                        }
                    }else{
                        if(myActivity instanceof PilihSurveiActivity){
                            ((PilihSurveiActivity)myActivity).failGetListSurvei(0);
                        }else if(myActivity instanceof PilihSurveiRespondenActivity){
                            ((PilihSurveiRespondenActivity)myActivity).failGetListSurvei(0);
                        }
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }

    public void downloadSurvei(final String token, final boolean isPetugas, final ObjectSurvei objectSurvei){
        //((PilihSurveiActivity)myActivity).startDownload(objectSurvei, objectSurvei.getBulan(), objectSurvei.getTahun());
        StringRequest strReq = new StringRequest(Request.Method.POST,URLPath.getUrlDownloadSurvei(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-GET SURVEI", response.toString());
                try{
                    JSONArray hasil = new JSONArray(response);
                    if(isPetugas){
                        for (int i=0; i<hasil.length(); i++){
                            JSONObject itemRes = hasil.getJSONObject(i);
                            ObjectResponden objectResponden = new ObjectResponden(itemRes.getInt("id"), itemRes.getString("username")
                                    , itemRes.getString("nama"), itemRes.getString("uid_survei"), itemRes.getString("tipeResponden")
                                    , itemRes.getString("isSelfEnum"), itemRes.getString("nipPetugas"), itemRes.getString("photo")
                                    , itemRes.getString("status"));
                            for(int j=0; j<itemRes.getJSONArray("items").length(); j++){
                                JSONObject itemKua = itemRes.getJSONArray("items").getJSONObject(j);
                                if(!itemRes.getString("tipeResponden").equals("5") && !itemRes.getString("tipeResponden").equals("7")){
                                    ObjectKualitasTipe1 itemKualitas = new ObjectKualitasTipe1(itemKua.getString("uid_survei"), itemKua.getInt("id_responden")
                                            , itemKua.getString("uid_barang"), itemKua.getString("barang"), itemKua.getString("uid_kualitas")
                                            , itemKua.getString("kualitas"), itemKua.getString("tipe"), itemKua.getString("satuan_standar")
                                            , itemKua.getString("merk"), itemKua.getString("satuan_setempat"), itemKua.getString("ukuran_panjang")
                                            , itemKua.getString("ukuran_lebar"), itemKua.getString("ukuran_tinggi"), itemKua.getString("ukuran_berat")
                                            , itemKua.getString("konversi_setempat"), itemKua.getString("harga_satuan_setempat"),itemKua.getString("harga_satuan_standar")
                                            , itemKua.getString("keterangan"), itemKua.getString("status"));
                                    objectResponden.getObjectKualitas().add(itemKualitas);
                                }else{
                                    ObjectKualitasTipe2 itemKualitas = new ObjectKualitasTipe2(itemKua.getString("uid_survei"), itemKua.getInt("id_responden")
                                            , itemKua.getString("uid_barang"), itemKua.getString("barang"), itemKua.getString("uid_kualitas")
                                            , itemKua.getString("kualitas"), itemKua.getString("tipe"), itemKua.getString("satuan_unit")
                                            , itemKua.getString("nilai_sewa"), itemKua.getString("keterangan"), itemKua.getString("status"));
                                    objectResponden.getObjectKualitas().add(itemKualitas);
                                }
                            }
                            objectSurvei.getObjectRespondens().add(objectResponden);
                        }
                        Database.getInstance(myActivity).insertSurvei(objectSurvei);
                        ((PilihSurveiActivity)myActivity).successDownloadSurvei(objectSurvei, 0);
                    }else{
                        for (int i=0; i<hasil.length(); i++){
                            JSONObject itemRes = hasil.getJSONObject(i);
                            ObjectResponden objectResponden = new ObjectResponden(itemRes.getInt("id"), itemRes.getString("username")
                                    , itemRes.getString("nama"), itemRes.getString("uid_survei"), itemRes.getString("tipeResponden")
                                    , itemRes.getString("isSelfEnum"), itemRes.getString("nipPetugas"), itemRes.getString("photo")
                                    , itemRes.getString("status"));
                            for(int j=0; j<itemRes.getJSONArray("items").length(); j++){
                                JSONObject itemKua = itemRes.getJSONArray("items").getJSONObject(j);
                                if(!itemRes.getString("tipeResponden").equals("5") && !itemRes.getString("tipeResponden").equals("7")){
                                    ObjectKualitasTipe1 itemKualitas = new ObjectKualitasTipe1(itemKua.getString("uid_survei"), itemKua.getInt("id_responden")
                                            , itemKua.getString("uid_barang"), itemKua.getString("barang"), itemKua.getString("uid_kualitas")
                                            , itemKua.getString("kualitas"), itemKua.getString("tipe"), itemKua.getString("satuan_standar")
                                            , itemKua.getString("merk"), itemKua.getString("satuan_setempat"), itemKua.getString("ukuran_panjang")
                                            , itemKua.getString("ukuran_lebar"), itemKua.getString("ukuran_tinggi"), itemKua.getString("ukuran_berat")
                                            , itemKua.getString("konversi_setempat"), itemKua.getString("harga_satuan_setempat"),itemKua.getString("harga_satuan_standar")
                                            , itemKua.getString("keterangan"), itemKua.getString("status"));
                                    objectResponden.getObjectKualitas().add(itemKualitas);
                                }else{
                                    ObjectKualitasTipe2 itemKualitas = new ObjectKualitasTipe2(itemKua.getString("uid_survei"), itemKua.getInt("id_responden")
                                            , itemKua.getString("uid_barang"), itemKua.getString("barang"), itemKua.getString("uid_kualitas")
                                            , itemKua.getString("kualitas"), itemKua.getString("tipe"), itemKua.getString("satuan_unit")
                                            , itemKua.getString("nilai_sewa"), itemKua.getString("keterangan"), itemKua.getString("status"));
                                    objectResponden.getObjectKualitas().add(itemKualitas);
                                }
                            }
                            objectSurvei.getObjectRespondens().add(objectResponden);
                        }
                        Database.getInstance(myActivity).insertSurvei(objectSurvei);
                        ((PilihSurveiRespondenActivity)myActivity).successDownloadSurvei(objectSurvei, 0);
                    }
                }catch (Exception ex){
                    Log.e(TAG,""+ex);
                    if(myActivity instanceof PilihSurveiActivity){
                        ((PilihSurveiActivity)myActivity).failDownloadSurvei(objectSurvei,0);
                    }else if(myActivity instanceof PilihSurveiRespondenActivity){
                        ((PilihSurveiRespondenActivity)myActivity).failDownloadSurvei(objectSurvei,0);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse==null){
                    if(myActivity instanceof PilihSurveiActivity){
                        ((PilihSurveiActivity)myActivity).failDownloadSurvei(objectSurvei,0);
                    }else if(myActivity instanceof PilihSurveiRespondenActivity){
                        ((PilihSurveiRespondenActivity)myActivity).failDownloadSurvei(objectSurvei,0);
                    }
                }else{
                    if(error.networkResponse.statusCode==401){
                        if(myActivity instanceof PilihSurveiActivity){
                            ((PilihSurveiActivity)myActivity).failDownloadSurvei(objectSurvei,2);
                        }else if(myActivity instanceof PilihSurveiRespondenActivity){
                            ((PilihSurveiRespondenActivity)myActivity).failDownloadSurvei(objectSurvei,2);
                        }
                    }else{
                        if(myActivity instanceof PilihSurveiActivity){
                            ((PilihSurveiActivity)myActivity).failDownloadSurvei(objectSurvei,0);
                        }else if(myActivity instanceof PilihSurveiRespondenActivity){
                            ((PilihSurveiRespondenActivity)myActivity).failDownloadSurvei(objectSurvei,0);
                        }
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("token", token);
                params.put("uid_survei", objectSurvei.getUid_survei());
                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }
    public void refreshResponden(final String token, final boolean isPetugas, final ObjectResponden objectResponden){
        //((PilihResponden)myActivity).startRefresh(objectResponden);
        StringRequest strReq = new StringRequest(Request.Method.POST,URLPath.getUrlUpdate(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-REFRESH", response.toString());
                if(response.equals("1")){
                    Database.getInstance(myActivity).updateKualitasRefresh(objectResponden.getUid_survei(), objectResponden.getId());
                    ((PilihKualitas)myActivity).successRefresh(objectResponden, 0);
                }else{
                    ((PilihKualitas)myActivity).failRefresh(0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG+"-REFRESH", ""+error);
                if(error.networkResponse==null){
                    ((PilihKualitas)myActivity).failRefresh(0);
                }else{
                    if(error.networkResponse.statusCode==401){
                        ((PilihKualitas)myActivity).failRefresh(2);
                    }else{
                        ((PilihKualitas)myActivity).failRefresh(0);
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Log.d(TAG+"-REFRESH", ""+Database.getInstance(myActivity).getEditedKualitasResponden(objectResponden).toString());
                Map<String, String>  params = new HashMap<>();
                params.put("token", token);
                params.put("id_responden", ""+objectResponden.getId());
                params.put("survei", Database.getInstance(myActivity).getEditedKualitasResponden(objectResponden).toString());
                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }
    public void submitResponden(final String token, final boolean isPetugas, final ObjectResponden objectResponden){
        ((PilihResponden)myActivity).startSubmit(objectResponden);
        StringRequest strReq = new StringRequest(Request.Method.POST,URLPath.getUrlSubmit(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-SUBMIT", response.toString());
                if(response.equals("1")){
                    Database.getInstance(myActivity).updateKualitasSubmit(objectResponden.getUid_survei(), objectResponden.getId());
                    ((PilihResponden)myActivity).successSubmit(objectResponden, 0);
                }else{
                    ((PilihResponden)myActivity).failSubmit(0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG+"-SEBMIT", ""+error);
                if(error.networkResponse==null){
                    ((PilihResponden)myActivity).failSubmit(0);
                }else{
                    if(error.networkResponse.statusCode==401){
                        ((PilihResponden)myActivity).failSubmit(2);
                    }else{
                        ((PilihResponden)myActivity).failSubmit(0);
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Log.d(TAG+"-SUBMIT", ""+Database.getInstance(myActivity).getEditedKualitasResponden(objectResponden).toString());
                Map<String, String>  params = new HashMap<>();
                params.put("token", token);
                params.put("id_responden", ""+objectResponden.getId());
                params.put("survei", Database.getInstance(myActivity).getEditedKualitasResponden(objectResponden).toString());
                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }
    public void refreshSurvei(final String token, final boolean isPetugas, final ObjectSurvei objectSurvei){
        //((PilihResponden)myActivity).startRefresh(objectResponden);
        StringRequest strReq = new StringRequest(Request.Method.POST,URLPath.getUrlUpdate(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-REFRESH", response.toString());
                if(response.equals("1")){
                    Database.getInstance(myActivity).updateKualitasRefreshSurvei(objectSurvei.getUid_survei());
                    ((PilihKualitasResponden)myActivity).successRefresh(objectSurvei, 0);
                }else{
                    ((PilihKualitasResponden)myActivity).failRefresh(0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG+"-REFRESH", ""+error);
                if(error.networkResponse==null){
                    ((PilihKualitasResponden)myActivity).failRefresh(0);
                }else{
                    if(error.networkResponse.statusCode==401){
                        ((PilihKualitasResponden)myActivity).failRefresh(2);
                    }else{
                        ((PilihKualitasResponden)myActivity).failRefresh(0);
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Log.d(TAG+"-REFRESH", ""+Database.getInstance(myActivity).getEditedKualitasSurvei(objectSurvei).toString());
                Map<String, String>  params = new HashMap<>();
                params.put("token", token);
                params.put("survei", Database.getInstance(myActivity).getEditedKualitasSurvei(objectSurvei).toString());
                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }
    public void submitSurvei(final String token, final boolean isPetugas, final ObjectSurvei objectSurvei){
        StringRequest strReq = new StringRequest(Request.Method.POST,URLPath.getUrlSubmit(isPetugas), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG+"-SUBMIT", response.toString());
                if(response.equals("1")){
                    Database.getInstance(myActivity).updateKualitasSubmitSurvei(objectSurvei.getUid_survei());
                    ((PilihSurveiRespondenActivity)myActivity).successSubmit(objectSurvei, 0);
                }else{
                    ((PilihSurveiRespondenActivity)myActivity).failSubmit(objectSurvei, 0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG+"-SEBMIT", ""+error);
                if(error.networkResponse==null){
                    ((PilihSurveiRespondenActivity)myActivity).failSubmit(objectSurvei,0);
                }else{
                    if(error.networkResponse.statusCode==401){
                        ((PilihSurveiRespondenActivity)myActivity).failSubmit(objectSurvei,2);
                    }else{
                        ((PilihSurveiRespondenActivity)myActivity).failSubmit(objectSurvei,0);
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Log.d(TAG+"-SUBMIT", ""+Database.getInstance(myActivity).getEditedKualitasSurvei(objectSurvei).toString());
                Map<String, String>  params = new HashMap<>();
                params.put("token", token);
                params.put("uid_survei", objectSurvei.getUid_survei());
                params.put("survei", Database.getInstance(myActivity).getEditedKualitasSurvei(objectSurvei).toString());
                return params;
            }
        };
        // Adding String request to request queue
        AppController.getInstance(myActivity).addToRequestQueue(strReq);
    }
}
