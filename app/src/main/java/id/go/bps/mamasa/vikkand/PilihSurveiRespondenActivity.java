package id.go.bps.mamasa.vikkand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.go.bps.mamasa.vikkand.Asset.Database;
import id.go.bps.mamasa.vikkand.Asset.GetData;
import id.go.bps.mamasa.vikkand.Asset.PilihSurveiAdaapter;
import id.go.bps.mamasa.vikkand.Asset.PilihSurveiRespondenAdapter;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurvei;
import id.go.bps.mamasa.vikkand.Entity.ObjectUser;

public class PilihSurveiRespondenActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PilihSurveiRespondenAdapter pilihSurveiRespondenAdapter;


    private ProgressDialog progressDialog;
    private GetData getData;
    private ObjectUser objectUser;
    private static final String TAG = "PilihSurveiActivity";

    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_survei);

        Log.e(TAG, "masuk");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(PilihSurveiRespondenActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        pilihSurveiRespondenAdapter = new PilihSurveiRespondenAdapter(PilihSurveiRespondenActivity.this);
        mRecyclerView.setAdapter(pilihSurveiRespondenAdapter);

        objectUser = Database.getInstance(PilihSurveiRespondenActivity.this).getWhoIam();

        getData = new GetData(PilihSurveiRespondenActivity.this);
        progressDialog = new ProgressDialog(PilihSurveiRespondenActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.dismiss();

        toolbar = getSupportActionBar();
        setupActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if(pilihSurveiRespondenAdapter.getItemCount()==0){
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_survei, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_logout){
            startLogout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getData.getListSurvei(objectUser.getJwtToken(), objectUser.getIsPetugas().equals("1"));
    }

    public void successGetListSurvei(int idSuccess){
        mSwipeRefreshLayout.setRefreshing(false);
        pilihSurveiRespondenAdapter.refresh();
        progressDialog.dismiss();
        Toast.makeText(this,"Berhasil mendapatkan survei baru", Toast.LENGTH_LONG)
                .show();
    }

    public void failGetListSurvei(int idFail){
        mSwipeRefreshLayout.setRefreshing(false);
        progressDialog.dismiss();
        Toast.makeText(this,"Gagal mendapatkan survei baru", Toast.LENGTH_LONG)
                .show();
    }

    public void startDownload(ObjectSurvei objectSurvei){
        progressDialog.setTitle("Mengunduh");
        progressDialog.setMessage("Mengunduh Survei Triwulan "+objectSurvei.getBulan()+" Tahun "+objectSurvei.getTahun());
        progressDialog.show();
        getData.downloadSurvei(objectUser.getJwtToken(), objectUser.getIsPetugas().equals("1"), objectSurvei);
    }
    public void successDownloadSurvei(ObjectSurvei objectSurvei, int idSuccess){
        pilihSurveiRespondenAdapter.refresh();
        progressDialog.dismiss();
        Toast.makeText(this,"Berhasil mengunduh survei baru", Toast.LENGTH_LONG)
                .show();
    }

    public void failDownloadSurvei(ObjectSurvei objectSurvei, int idFail){
        progressDialog.dismiss();
        Toast.makeText(this,"Gagal mengunduh survei baru", Toast.LENGTH_LONG)
                .show();
    }

    public void startSubmit(ObjectSurvei objectSurvei){
        progressDialog.setTitle("Mengunggah data");
        progressDialog.setMessage("Mengunggah semua data pada survei Triwulan "+objectSurvei.getBulan()+" Tahun "+objectSurvei.getTahun());
        progressDialog.show();
        getData.submitSurvei(objectUser.getJwtToken(), objectUser.getIsPetugas().equals("1"), objectSurvei);
    }
    public void successSubmit(ObjectSurvei objectSurvei, int idSuccess){
        pilihSurveiRespondenAdapter.refresh();
        progressDialog.dismiss();
        Toast.makeText(this,"Berhasil mengunduh survei baru", Toast.LENGTH_LONG)
                .show();
    }

    public void failSubmit(ObjectSurvei objectSurvei, int idFail){
        progressDialog.dismiss();
        Toast.makeText(this,"Gagal mengunduh survei baru", Toast.LENGTH_LONG)
                .show();
    }

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
        startActivity(new Intent(PilihSurveiRespondenActivity.this, LoginActivity.class));
    }
    public void failLogout(){
        progressDialog.dismiss();
        Toast.makeText(this,"Gagal login", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pilihSurveiRespondenAdapter.refresh();
    }

    public void setupActionBar(){

        Typeface face = Typeface.createFromAsset(getAssets(),
                "Calibri.ttf");

        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(20l);
        tv.setText("Daftar Survei");

        tv.setTypeface(face);
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolbar.setCustomView(tv);
    }
}
