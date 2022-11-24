package id.go.bps.mamasa.vikkand;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.go.bps.mamasa.vikkand.Asset.Database;
import id.go.bps.mamasa.vikkand.Asset.GetData;
import id.go.bps.mamasa.vikkand.Asset.PilihRespondenAdapter;
import id.go.bps.mamasa.vikkand.Asset.PilihSurveiAdaapter;
import id.go.bps.mamasa.vikkand.Entity.ObjectResponden;
import id.go.bps.mamasa.vikkand.Entity.ObjectRespondenHelper;

public class PilihResponden extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PilihRespondenAdapter pilihRespondenAdapter;

    private ProgressDialog progressDialog;
    private ActionBar toolbar;

    private final String TAG = "pilihresponden";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_responden);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(PilihResponden.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String uid_survei = getIntent().getStringExtra(Database.K_UID_SURVEI);
        int tipe = getIntent().getIntExtra(Database.K_TIPE, 0);

        toolbar = getSupportActionBar();
        setupActionBar(tipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Log.d(TAG, "masuk");

        pilihRespondenAdapter = new PilihRespondenAdapter(PilihResponden.this, uid_survei, ""+tipe);
        mRecyclerView.setAdapter(pilihRespondenAdapter);

        progressDialog = new ProgressDialog(PilihResponden.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pilihRespondenAdapter.refresh();
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//    }


    public void startSubmit(ObjectResponden objectResponden){
        progressDialog.setTitle("Mensubmit data");
        progressDialog.setMessage("Mengunduh data "+objectResponden.getNama()+" yang telah diubah");
        progressDialog.show();
    }

    public void successSubmit(ObjectResponden objectResponden, int success){
        pilihRespondenAdapter.refresh();
        progressDialog.dismiss();
        Toast.makeText(this,"Berhasil mensubmit "+objectResponden.getNama(), Toast.LENGTH_LONG)
                .show();
    }

    public void failSubmit(int fail){
        progressDialog.dismiss();
        Toast.makeText(this,"Gagal mensubmit data", Toast.LENGTH_LONG)
                .show();
    }

    public void setupActionBar(int tipe){

        Typeface face = Typeface.createFromAsset(getAssets(),
                "Calibri.ttf");

        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(20l);
        switch (tipe){
            case 1:
                tv.setText("Responden Toko Bangunan");
                break;
            case 2:
                tv.setText("Responden Toko Bahan Material");
                break;
            case 3:
                tv.setText("Responden Toko Kayu/ Kuseng");
                break;
            case 4:
                tv.setText("Responden Toko Kaca");
                break;
            case 5:
                tv.setText("Responden Sewa Alat Berat");
                break;
            case 6:
                tv.setText("Responden Toko Alumunium");
                break;
            case 7:
                tv.setText("Responden Upah Jasa");
                break;
        }

        tv.setTypeface(face);
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolbar.setCustomView(tv);
    }
}
