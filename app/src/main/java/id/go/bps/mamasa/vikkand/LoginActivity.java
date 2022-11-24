package id.go.bps.mamasa.vikkand;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.go.bps.mamasa.vikkand.Asset.Database;
import id.go.bps.mamasa.vikkand.Asset.GetData;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurvei;
import id.go.bps.mamasa.vikkand.Entity.ObjectUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userName;
    private EditText password;
    private EditText nip;
    private TextInputLayout nipWrapper, userNameWrapper, passwordWrapper;
    private TextView errorText;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private GetData getData;
    private Spinner spinner;
    private ImageView setting;
    private LinearLayout loginHolder, label;

    private static final String TAG = "LoginActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.username);
        nip = (EditText) findViewById(R.id.nip);
        password = (EditText) findViewById(R.id.password);
        errorText = (TextView) findViewById(R.id.error_message);
        loginButton = (Button) findViewById(R.id.sign_in_button);
        spinner = (Spinner) findViewById(R.id.spinner);

        userNameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        nipWrapper = (TextInputLayout) findViewById(R.id.nipWrapper);
        loginHolder = (LinearLayout) findViewById(R.id.login_holder);
        label = (LinearLayout) findViewById(R.id.label);
//        setting = (ImageView) findViewById(R.id.setting);

        loginButton.setOnClickListener(this);

        List<String> flowers = new ArrayList<>();
        flowers.add("RESPONDEN");
        flowers.add("PETUGAS");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,flowers){
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(18f);
                return tv;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                // Cast the drop down items (popup items) as text view
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);
                tv.setTextSize(15f);
                tv.setTextColor(Color.BLACK);
//                if(position == mSelectedIndex){
//                    // Set spinner selected popup item's text color
//                    tv.setTextColor(Color.BLUE);
//                }

                return tv;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                errorText.setVisibility(View.GONE);
                if(i==0){
                    userNameWrapper.setVisibility(View.VISIBLE);
                    nipWrapper.setVisibility(View.GONE);
                }else{
                    userNameWrapper.setVisibility(View.GONE);
                    nipWrapper.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                errorText.setVisibility(View.GONE);
            }
        });

        getData = new GetData(LoginActivity.this);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.dismiss();

        label.animate().alpha(1).setDuration(500);

        Thread Next = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {

                } finally {
                    if(Database.getInstance(LoginActivity.this).getWhoIam()!=null){
                        if(!Database.getInstance(LoginActivity.this).getWhoIam().getIsPetugas().equals("1")){
                            finish();
                            startActivity(new Intent(LoginActivity.this, PilihSurveiRespondenActivity.class));
                        }else{
                            ObjectSurvei lastSurvei = Database.getInstance(LoginActivity.this).getLastSurvei();
                            if(lastSurvei==null){
                                startActivity(new Intent(LoginActivity.this, PilihSurveiActivity.class));
                                finish();
                            }else{
                                startActivity(new Intent(LoginActivity.this, PilihJenisResponden.class));
                                finish();
                            }
                        }
                    }
                    animate();
                }
            }
        };
        Next.start();
    }

    @Override
    public void onClick(View v) {
        if(v==loginButton){
            if(spinner.getSelectedItemPosition()==0){
                String userNameString = userName.getText().toString();
                String passWordString = password.getText().toString();
                if(userNameString.trim().length()==0 && passWordString.trim().length()==0){
                    errorText.setText("*email dan password harus diisi");
                    errorText.setVisibility(View.VISIBLE);
                }else if(userNameString.trim().length()==0){
                    errorText.setText("*email harus diisi");
                    errorText.setVisibility(View.VISIBLE);
                }else if(passWordString.trim().length()==0){
                    errorText.setText("*password harus diisi");
                    errorText.setVisibility(View.VISIBLE);
                }else{
                    errorText.setVisibility(View.GONE);
                    progressDialog.setTitle("Login");
                    progressDialog.setMessage("Mencoba untuk login ...");
                    progressDialog.show();
                    getData.getLogin(spinner.getSelectedItemPosition()==1,userNameString, passWordString);
                }
            }else{
                String nipString = nip.getText().toString();
                String passWordString = password.getText().toString();
                if(nipString.trim().length()==0 && passWordString.trim().length()==0){
                    errorText.setText("*nip dan password harus diisi");
                    errorText.setVisibility(View.VISIBLE);
                }else if(nipString.trim().length()==0){
                    errorText.setText("*nip harus diisi");
                    errorText.setVisibility(View.VISIBLE);
                }else if(passWordString.trim().length()==0){
                    errorText.setText("*password harus diisi");
                    errorText.setVisibility(View.VISIBLE);
                }else{
                    errorText.setVisibility(View.GONE);
                    progressDialog.setTitle("Login");
                    progressDialog.setMessage("Mencoba untuk login ...");
                    progressDialog.show();
                    getData.getLogin(spinner.getSelectedItemPosition()==1,nipString, passWordString);
                }
            }
        }
    }
    public void failLogin(int id){
        progressDialog.dismiss();
        switch (id){
            case 0:
                errorText.setText("*terjadi kesalahan");
                errorText.setVisibility(View.VISIBLE);
                Toast.makeText(this,"Gagal login", Toast.LENGTH_LONG)
                        .show();
                break;
            case 1:
                errorText.setText("*email dan password tidak sesuai");
                errorText.setVisibility(View.VISIBLE);
                Toast.makeText(this,"Gagal login", Toast.LENGTH_LONG)
                        .show();
                break;
            case 2:
                errorText.setText("*terjadi kesalahan");
                errorText.setVisibility(View.VISIBLE);
                Toast.makeText(this,"Gagal login", Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }
    public void successLogin(ObjectUser objectUser){
        progressDialog.dismiss();
        Database database = Database.getInstance(LoginActivity.this);
        database.insertUser(objectUser, false);
        if(objectUser.getIsPetugas().equals("1")){
            finish();
            startActivity(new Intent(LoginActivity.this, PilihSurveiActivity.class));
            Log.d(TAG,"1");
        }else{
            finish();
            startActivity(new Intent(LoginActivity.this, PilihSurveiRespondenActivity.class));
            Log.d(TAG,"1");
        }
    }
    private void animate(){
        label.animate()
                .alpha(0.0f).setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        label.setVisibility(View.GONE);

                        loginHolder.setVisibility(View.VISIBLE);
                        loginHolder.setAlpha(0);
                        loginHolder.animate().alpha(1.0f).setDuration(500);
                    }
                });
    }
}
