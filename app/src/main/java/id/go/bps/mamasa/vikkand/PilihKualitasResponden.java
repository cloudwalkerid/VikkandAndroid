package id.go.bps.mamasa.vikkand;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.go.bps.mamasa.vikkand.Asset.Database;
import id.go.bps.mamasa.vikkand.Asset.GetData;
import id.go.bps.mamasa.vikkand.Asset.PilihKualitasAdapter;
import id.go.bps.mamasa.vikkand.Asset.PilihKualitasRespondenAdapter;
import id.go.bps.mamasa.vikkand.Entity.ObjectResponden;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurvei;
import id.go.bps.mamasa.vikkand.Entity.ObjectUser;

public class PilihKualitasResponden  extends AppCompatActivity implements SearchView.OnCloseListener,SearchView.OnQueryTextListener{

    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PilihKualitasRespondenAdapter pilihKualitasRespondenAdapter;
    private ObjectSurvei objectSurvei;
    private String searchText;

    private ObjectUser objectUser;
    private GetData getData;
    private ProgressDialog progressDialog;

    private ActionBar toolbar;

    private final String TAG = "pilihkualitas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kualitas);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(PilihKualitasResponden.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Log.d(TAG, "masukkualitas 0");

        String uid_survei = getIntent().getStringExtra(Database.K_UID_SURVEI);
        objectSurvei = Database.getInstance(PilihKualitasResponden.this).getSurveiByUUID(uid_survei);


        Log.d(TAG, "masukkualitas 2 "+(objectSurvei==null) );
        pilihKualitasRespondenAdapter = new PilihKualitasRespondenAdapter(PilihKualitasResponden.this, objectSurvei);

        //Toast.makeText(this, "Dapat "+objectResponden.getId()+"|"+objectResponden.getTipeResponden(), Toast.LENGTH_SHORT).show();
        mRecyclerView.setAdapter(pilihKualitasRespondenAdapter);
        searchText="";

        toolbar = getSupportActionBar();
        setupActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        objectUser = Database.getInstance(PilihKualitasResponden.this).getWhoIam();
        getData = new GetData(PilihKualitasResponden.this);
        progressDialog = new ProgressDialog(PilihKualitasResponden.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pilih_kualitas, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnCloseListener(this);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super();
//        inflater.inflate(R.menu.menu_pilih_kualitas, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setOnCloseListener(this);
//        searchView.setOnQueryTextListener(this);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_upload){
            startRefresh();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        pilihKualitasRespondenAdapter.getFilter().filter(newText);
        if(!searchText.equals("")){
            searchText=newText;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onClose() {
        pilihKualitasRespondenAdapter.closeFilter();
        searchText="";
        return false;
    }

    public void startRefresh(){
        progressDialog.setTitle("Memperbarui data");
        progressDialog.setMessage("Mengunduh data Triwulan "+objectSurvei.getBulan()+" Tahun "+objectSurvei.getTahun()+" yang telah diubah");
        progressDialog.show();
        getData.refreshSurvei(this.objectUser.getJwtToken(), this.objectUser.getIsPetugas().equals("1"), objectSurvei);
    }

    public void successRefresh(ObjectSurvei objectSurvei, int success){
        pilihKualitasRespondenAdapter.refresh();
        progressDialog.dismiss();
        Toast.makeText(this,"Berhasil memperbarui data Triwulan "+objectSurvei.getBulan()+" Tahun "+objectSurvei.getTahun(), Toast.LENGTH_LONG)
                .show();
    }

    public void failRefresh(int fail){
        progressDialog.dismiss();
        Toast.makeText(this,"Gagal memperbarui data ", Toast.LENGTH_LONG)
                .show();
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
        tv.setText("Triwulan "+objectSurvei.getBulan()+" Tahun "+objectSurvei.getTahun());

        tv.setTypeface(face);
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolbar.setCustomView(tv);
    }
}
