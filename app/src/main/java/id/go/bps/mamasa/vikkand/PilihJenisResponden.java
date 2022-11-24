package id.go.bps.mamasa.vikkand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import id.go.bps.mamasa.vikkand.Asset.Database;
import id.go.bps.mamasa.vikkand.Asset.GetData;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurvei;
import id.go.bps.mamasa.vikkand.Entity.ObjectTipeHelper;
import id.go.bps.mamasa.vikkand.Entity.ObjectUser;

public class PilihJenisResponden extends AppCompatActivity implements View.OnClickListener{

    private CardView tokoBangunan;
    private CardView bahanMaterial;
    private CardView kayuKuseng;
    private CardView kaca;
    private CardView alatBerat;
    private CardView alumunium;
    private CardView upahJasa;

    private ImageView listSurvei;
    private ImageView logout;
    private GetData getData;

    private ProgressDialog progressDialog;

    private ObjectSurvei lastSurvei;
    private ObjectUser objectUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_jenis_responden);

//        Toast.makeText(this,"Berhasil mendapatkan survei baru", Toast.LENGTH_LONG)
//                .show();

        tokoBangunan = (CardView) findViewById(R.id.tokoBangunan);
        bahanMaterial = (CardView) findViewById(R.id.bahanMaterial);
        kayuKuseng = (CardView) findViewById(R.id.kayuKusang);
        kaca = (CardView) findViewById(R.id.kaca);
        alatBerat = (CardView) findViewById(R.id.alatBerat);
        alumunium = (CardView) findViewById(R.id.alumunium);
        upahJasa = (CardView) findViewById(R.id.upahJasa);
        listSurvei = (ImageView) findViewById(R.id.listSurvei);
        logout = (ImageView) findViewById(R.id.logout);

//        tokoBangunan.setOnClickListener(this);
//        bahanMaterial.setOnClickListener(this);
//        kayuKuseng.setOnClickListener(this);
//        kaca.setOnClickListener(this);
//        alatBerat.setOnClickListener(this);
//        alumunium.setOnClickListener(this);
//        upahJasa.setOnClickListener(this);
//
        listSurvei.setOnClickListener(this);
        logout.setOnClickListener(this);
        if(savedInstanceState!=null){
            String uid_survei = savedInstanceState.getString(Database.K_UID_SURVEI);
            lastSurvei = Database.getInstance(PilihJenisResponden.this).getSurveiByUUID(uid_survei);
        }else{
            if(!getIntent().hasExtra(Database.K_UID_SURVEI)){
                lastSurvei = Database.getInstance(PilihJenisResponden.this).getLastSurvei();
                if(lastSurvei==null){
                    finish();
                    startActivity(new Intent(PilihJenisResponden.this, PilihSurveiActivity.class));
                }
                getIntent().putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
            }else {
                String uid_survei = getIntent().getStringExtra(Database.K_UID_SURVEI);
                lastSurvei = Database.getInstance(PilihJenisResponden.this).getSurveiByUUID(uid_survei);
            }
        }
        objectUser = Database.getInstance(PilihJenisResponden.this).getWhoIam();
        getData = new GetData(PilihJenisResponden.this);
        progressDialog = new ProgressDialog(PilihJenisResponden.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.dismiss();
        refreshView();
    }

    @Override
    public void onClick(View view) {
        if(view==tokoBangunan){
            Intent intent = new Intent(PilihJenisResponden.this, PilihResponden.class);
            intent.putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
            intent.putExtra(Database.K_TIPE, 1);
            startActivity(intent);
        }else if(view==bahanMaterial){
            Intent intent = new Intent(PilihJenisResponden.this, PilihResponden.class);
            intent.putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
            intent.putExtra(Database.K_TIPE, 2);
            startActivity(intent);
        }else if(view==kayuKuseng){
            Intent intent = new Intent(PilihJenisResponden.this, PilihResponden.class);
            intent.putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
            intent.putExtra(Database.K_TIPE, 3);
            startActivity(intent);
        }else if(view==kaca){
            Intent intent = new Intent(PilihJenisResponden.this, PilihResponden.class);
            intent.putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
            intent.putExtra(Database.K_TIPE, 4);
            startActivity(intent);
        }else if(view==alatBerat){
            Intent intent = new Intent(PilihJenisResponden.this, PilihResponden.class);
            intent.putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
            intent.putExtra(Database.K_TIPE, 5);
            startActivity(intent);
        }else if(view==alumunium){
            Intent intent = new Intent(PilihJenisResponden.this, PilihResponden.class);
            intent.putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
            intent.putExtra(Database.K_TIPE, 6);
            startActivity(intent);
        }else if(view==upahJasa){
            Intent intent = new Intent(PilihJenisResponden.this, PilihResponden.class);
            intent.putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
            intent.putExtra(Database.K_TIPE, 7);
            startActivity(intent);
        }else if(view==listSurvei){
            finish();
            startActivity(new Intent(PilihJenisResponden.this, PilihSurveiActivity.class));
        }else if(view==logout){
            startLogout();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState);
        if(lastSurvei!=null){
            outState.putString(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
        }
    }

    public void refreshView(){
        if(lastSurvei!=null){
            ArrayList<ObjectTipeHelper> objectTipeHelpers = Database.getInstance(PilihJenisResponden.this)
                    .getAllTipe(lastSurvei.getUid_survei());
            if(objectTipeHelpers==null){
//                Toast.makeText(this,"Dapat null ", Toast.LENGTH_LONG)
//                        .show();
                return;
            }
//            Toast.makeText(this,"Dapat "+objectTipeHelpers.size(), Toast.LENGTH_LONG)
//                    .show();
            for(ObjectTipeHelper item : objectTipeHelpers){
                if(item.getTipe().equals("1")){
                    if(item.getAll()==0){
                        tokoBangunan.setCardBackgroundColor(getResources().getColor(R.color.greyLightCariJadwal));
                        tokoBangunan.setOnClickListener(null);
                    }else{
                        tokoBangunan.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tokoBangunan.setOnClickListener(this);
                    }
                }else if(item.getTipe().equals("2")){
                    if(item.getAll()==0){
                        bahanMaterial.setCardBackgroundColor(getResources().getColor(R.color.greyLightCariJadwal));
                        bahanMaterial.setOnClickListener(null);
                    }else{
                        bahanMaterial.setCardBackgroundColor(getResources().getColor(R.color.white));
                        bahanMaterial.setOnClickListener(this);
                    }
                }else if(item.getTipe().equals("3")){
                    if(item.getAll()==0){
                        kayuKuseng.setCardBackgroundColor(getResources().getColor(R.color.greyLightCariJadwal));
                        kayuKuseng.setOnClickListener(null);
                    }else{
                        kayuKuseng.setCardBackgroundColor(getResources().getColor(R.color.white));
                        kayuKuseng.setOnClickListener(this);
                    }
                }else if(item.getTipe().equals("4")){
                    if(item.getAll()==0){
                        kaca.setCardBackgroundColor(getResources().getColor(R.color.greyLightCariJadwal));
                        kaca.setOnClickListener(null);
                    }else{
                        kaca.setCardBackgroundColor(getResources().getColor(R.color.white));
                        kaca.setOnClickListener(this);
                    }
                }else if(item.getTipe().equals("5")){
                    if(item.getAll()==0){
                        alatBerat.setCardBackgroundColor(getResources().getColor(R.color.greyLightCariJadwal));
                        alatBerat.setOnClickListener(null);
                    }else{
                        alatBerat.setCardBackgroundColor(getResources().getColor(R.color.white));
                        alatBerat.setOnClickListener(this);
                    }
                }else if(item.getTipe().equals("6")){
                    if(item.getAll()==0){
                        alumunium.setCardBackgroundColor(getResources().getColor(R.color.greyLightCariJadwal));
                        alumunium.setOnClickListener(null);
                    }else{
                        alumunium.setCardBackgroundColor(getResources().getColor(R.color.white));
                        alumunium.setOnClickListener(this);
                    }
                }else if(item.getTipe().equals("7")){
                    if(item.getAll()==0){
                        upahJasa.setCardBackgroundColor(getResources().getColor(R.color.greyLightCariJadwal));
                        upahJasa.setOnClickListener(null);
                    }else{
                        upahJasa.setCardBackgroundColor(getResources().getColor(R.color.white));
                        upahJasa.setOnClickListener(this);
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!getIntent().hasExtra(Database.K_UID_SURVEI)){
            lastSurvei = Database.getInstance(PilihJenisResponden.this).getLastSurvei();
            if(lastSurvei==null){
                finish();
                startActivity(new Intent(PilihJenisResponden.this, PilihSurveiActivity.class));
            }
            getIntent().putExtra(Database.K_UID_SURVEI, lastSurvei.getUid_survei());
        }else {
            String uid_survei = getIntent().getStringExtra(Database.K_UID_SURVEI);
            lastSurvei = Database.getInstance(PilihJenisResponden.this).getSurveiByUUID(uid_survei);
        }
        refreshView();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        refreshView();
//    }

    public void startLogout(){
        progressDialog.setTitle("Log Out");
        progressDialog.setMessage("Mencoba keluar dari applikasi");
        progressDialog.show();
        getData.getLogout(objectUser.getJwtToken(), objectUser.getIsPetugas().equals("1"));
    }
    public void successLogout(){
        progressDialog.dismiss();
        Toast.makeText(this,"Berhasil logout", Toast.LENGTH_LONG)
                .show();
        finish();
        startActivity(new Intent(PilihJenisResponden.this, LoginActivity.class));
    }
    public void failLogout(){
        progressDialog.dismiss();
        Toast.makeText(this,"Gagal login", Toast.LENGTH_LONG)
                .show();
    }
}
