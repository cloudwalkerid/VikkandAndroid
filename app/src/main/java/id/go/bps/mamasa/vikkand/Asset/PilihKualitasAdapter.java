package id.go.bps.mamasa.vikkand.Asset;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import id.go.bps.mamasa.vikkand.Entity.ObjectKualitas;
import id.go.bps.mamasa.vikkand.Entity.ObjectKualitasTipe1;
import id.go.bps.mamasa.vikkand.Entity.ObjectKualitasTipe2;
import id.go.bps.mamasa.vikkand.Entity.ObjectResponden;
import id.go.bps.mamasa.vikkand.R;
import rm.com.longpresspopup.LongPressPopup;
import rm.com.longpresspopup.LongPressPopupBuilder;

/**
 * Created by ASUS on 28/06/2018.
 */

public class PilihKualitasAdapter extends RecyclerView.Adapter<PilihKualitasAdapter.ViewHolder> implements Filterable{

    private Activity myActivity;
    private ObjectResponden objectResponden;
    private ArrayList<ObjectKualitas> objectKualitas;
    private KualitasFilter kualitasFilter;
    private final String TAG = "pilihkualitasadapter";

    public PilihKualitasAdapter(Activity myActivity, ObjectResponden objectResponden){
        this.myActivity = myActivity;
        this.objectResponden = objectResponden;
        //Toast.makeText(myActivity, "Dapat ", Toast.LENGTH_SHORT).show();
        objectKualitas = Database.getInstance(myActivity).getKualitas(objectResponden.getUid_survei()
                , objectResponden.getId(), objectResponden.getTipeResponden());
        if(objectKualitas==null){
            objectKualitas = new ArrayList<>();
        }
        //Toast.makeText(myActivity, "Dapat "+objectKualitas.size(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "11. "+objectKualitas.size());
        notifyDataSetChanged();
    }

    public void refresh(){
        objectKualitas = new ArrayList<>();
        objectKualitas = Database.getInstance(myActivity).getKualitas(objectResponden.getUid_survei()
                , objectResponden.getId(), objectResponden.getTipeResponden());
        if(objectKualitas==null){
            objectKualitas = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kualitas, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectKualitas item = objectKualitas.get(position);
        holder.barangName.setText(item.getBarang());
        holder.kualitasName.setText(item.getKualitas());
        if(item.getStatus().equals("0")){
            holder.cardView.setCardBackgroundColor(myActivity.getResources().getColor(R.color.white));
        }else if(item.getStatus().equals("2") || item.getStatus().equals("3") || item.getStatus().equals("4")){
            holder.cardView.setCardBackgroundColor(myActivity.getResources().getColor(R.color.status_green));
        }else if(item.getStatus().equals("1")){
            holder.cardView.setCardBackgroundColor(myActivity.getResources().getColor(R.color.error_1));
        }

        LongPressPopup popup = new LongPressPopupBuilder(myActivity)
                .setTarget(holder.cardView)
                .setPopupView(showInfo(position))
                .setAnimationType(LongPressPopup.ANIMATION_TYPE_FROM_CENTER)
                .build();

        popup.register();
    }

    @Override
    public int getItemCount() {
        return objectKualitas.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private CardView cardView;
        private TextView barangName;
        private TextView kualitasName;
        private ImageView edit;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_kualias);
            barangName = (TextView) itemView.findViewById(R.id.item_barang_nama);
            kualitasName = (TextView) itemView.findViewById(R.id.item_kualias_nama);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            edit.setOnClickListener(this);
            //cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==edit){
                editKualitas(getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            showInfo(getLayoutPosition());
            return false;
        }
    }

    public View showInfo(int position){
        ObjectKualitas item = objectKualitas.get(position);
        if(!item.getTipe().equals("5") && !item.getTipe().equals("7")){
            item = (ObjectKualitasTipe1) item;
            LayoutInflater inflater = (LayoutInflater) myActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.see_kualitas_1, null);

            final TextView title, barang, kualitas, satuanStandar, merk, satuanSetempat, panjang, lebar, tinggi, berat
                    , konversi, hargaSetempat, hargaStandar, keterangan;
            title = (TextView) view.findViewById(R.id.title);
            barang = (TextView) view.findViewById(R.id.barang);
            kualitas = (TextView) view.findViewById(R.id.kualitas);
            satuanStandar = (TextView) view.findViewById(R.id.satuanStandar);
            merk = (TextView) view.findViewById(R.id.merk);
            satuanSetempat = (TextView) view.findViewById(R.id.satuanSetempat);
            panjang = (TextView) view.findViewById(R.id.panjang);
            lebar = (TextView) view.findViewById(R.id.lebar);
            tinggi = (TextView) view.findViewById(R.id.tinggi);
            berat = (TextView) view.findViewById(R.id.berat);
            konversi = (TextView) view.findViewById(R.id.konversi);
            hargaSetempat = (TextView) view.findViewById(R.id.hargaSetempat);
            hargaStandar = (TextView) view.findViewById(R.id.hargaStandar);
            keterangan = (TextView) view.findViewById(R.id.keterangan);

            title.setText(item.getKualitas());
            if(item.getBarang().equals("000-000")){
                barang.setBackgroundResource(R.drawable.row_disable);
                barang.setText("Jenis barang : -");
            }else{
                barang.setText("Jenis barang : "+item.getBarang());
            }
            if(item.getKualitas().equals("000-000")){
                kualitas.setBackgroundResource(R.drawable.row_disable);
                kualitas.setText("Jenis kualiatas : - ");
            }else{
                kualitas.setText("Jenis kualiatas : "+item.getKualitas());
            }
            if(((ObjectKualitasTipe1) item).getSatuan_standar().equals("000-000")){
                satuanStandar.setBackgroundResource(R.drawable.row_disable);
                satuanStandar.setText("Satuan standar : - ");
            }else{
                satuanStandar.setText("Satuan standar : "+((ObjectKualitasTipe1) item).getSatuan_standar());
            }
            if(((ObjectKualitasTipe1) item).getMerk().equals("000-000")){
                merk.setBackgroundResource(R.drawable.row_disable);
                merk.setText("Merk : -");
            }else{
                merk.setText("Merk : "+((ObjectKualitasTipe1) item).getMerk());
            }
            if(((ObjectKualitasTipe1) item).getSatuan_setempat().equals("000-000")){
                satuanSetempat.setBackgroundResource(R.drawable.row_disable);
                satuanSetempat.setText("Satuan setempat : - ");
            }else{
                satuanSetempat.setText("Satuan setempat : "+((ObjectKualitasTipe1) item).getSatuan_setempat());
            }
            if(((ObjectKualitasTipe1) item).getUkuran_panjang().equals("000-000")){
                panjang.setBackgroundResource(R.drawable.row_disable);
                panjang.setText("Panjang : - ");
            }else{
                panjang.setText("Panjang : "+((ObjectKualitasTipe1) item).getUkuran_panjang());
            }
            if(((ObjectKualitasTipe1) item).getUkuran_lebar().equals("000-000")){
                lebar.setBackgroundResource(R.drawable.row_disable);
                lebar.setText("Lebar : - ");
            }else{
                lebar.setText("Lebar : "+((ObjectKualitasTipe1) item).getUkuran_lebar());
            }
            if(((ObjectKualitasTipe1) item).getUkuran_tinggi().equals("000-000")){
                tinggi.setBackgroundResource(R.drawable.row_disable);
                tinggi.setText("Tinggi : - ");
            }else{
                tinggi.setText("Tinggi : "+((ObjectKualitasTipe1) item).getUkuran_tinggi());
            }
            if(((ObjectKualitasTipe1) item).getUkuran_berat().equals("000-000")){
                berat.setBackgroundResource(R.drawable.row_disable);
                berat.setText("Berat : - ");
            }else{
                berat.setText("Berat : "+((ObjectKualitasTipe1) item).getUkuran_berat());
            }
            if(((ObjectKualitasTipe1) item).getKonversi_setempat().equals("000-000")){
                konversi.setBackgroundResource(R.drawable.row_disable);
                konversi.setText("Konversi : - ");
            }else{
                konversi.setText("Konversi : "+((ObjectKualitasTipe1) item).getKonversi_setempat());
            }
            if(((ObjectKualitasTipe1) item).getHarga_satuan_setempat().equals("000-000")){
                hargaSetempat.setBackgroundResource(R.drawable.row_disable);
                hargaSetempat.setText("Harga setempat : - ");
            }else{
                hargaSetempat.setText("Harga setempat : "+((ObjectKualitasTipe1) item).getHarga_satuan_setempat());
            }
            if(((ObjectKualitasTipe1) item).getHarga_satuan_standar().equals("000-000")){
                hargaStandar.setBackgroundResource(R.drawable.row_disable);
                hargaStandar.setText("Harga standar : - ");
            }else{
                hargaStandar.setText("Harga standar : "+((ObjectKualitasTipe1) item).getHarga_satuan_standar());
            }
            if(item.getKeterangan().equals("000-000")){
                keterangan.setBackgroundResource(R.drawable.row_disable);
                keterangan.setText("Keterangan : - ");
            }else{
                keterangan.setText("Keterangan : "+item.getKeterangan());
            }
            return view;
        }else{
            item = (ObjectKualitasTipe2) item;
            LayoutInflater inflater = (LayoutInflater) myActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.see_kualitas_2, null);

            final TextView title, barang, kualitas, satuanUnit, nilaiSewa, keterangan;
            title = (TextView) view.findViewById(R.id.title);
            barang = (TextView) view.findViewById(R.id.barang);
            kualitas = (TextView) view.findViewById(R.id.kualitas);
            satuanUnit = (TextView) view.findViewById(R.id.satuanUnit);
            nilaiSewa = (TextView) view.findViewById(R.id.nilaiSewa);
            keterangan = (TextView) view.findViewById(R.id.keterangan);

            title.setText(item.getKualitas());
            if(item.getBarang().equals("000-000")){
                barang.setBackgroundResource(R.drawable.row_disable);
                barang.setText("Jenis barang : -");
            }else{
                barang.setText("Jenis barang : "+item.getBarang());
            }
            if(item.getKualitas().equals("000-000")){
                kualitas.setBackgroundResource(R.drawable.row_disable);
                kualitas.setText("Jenis kualiatas : - ");
            }else{
                kualitas.setText("Jenis kualiatas : "+item.getKualitas());
            }
            if(((ObjectKualitasTipe2) item).getSatuan_unit().equals("000-000")){
                satuanUnit.setBackgroundResource(R.drawable.row_disable);
                satuanUnit.setText("Satuan unit : -");
            }else{
                satuanUnit.setText("Satuan unit : "+((ObjectKualitasTipe2) item).getSatuan_unit());
            }
            if(((ObjectKualitasTipe2) item).getNilai_sewa().equals("000-000")){
                nilaiSewa.setBackgroundResource(R.drawable.row_disable);
                nilaiSewa.setText("Nilai sewa : - ");
            }else{
                nilaiSewa.setText("Nilai sewa : "+((ObjectKualitasTipe2) item).getNilai_sewa());
            }
            if(item.getKeterangan().equals("000-000")){
                keterangan.setBackgroundResource(R.drawable.row_disable);
                keterangan.setText("Keterangan : - ");
            }else{
                keterangan.setText("Keterangan : "+item.getKeterangan());
            }
            return view;
        }
    }

    public void editKualitas(final int position){
        final ObjectKualitas item = objectKualitas.get(position);
        if(!item.getTipe().equals("5") && !item.getTipe().equals("7")){
            //item = (ObjectKualitasTipe1) item;
            final AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);
            builder.setTitle("Ubah Kualitas");
            LayoutInflater inflater = (LayoutInflater) myActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.edit_kualitas_1, null);

            final TextInputLayout barangWrapper, kualitasWrapper, satuanStandarWrapper, merkWrapper, satuanSetempatWrapper
                    , panjangWrapper, lebarWrapper, tinggiWrapper, beratWrapper, konversiWrapper
                    , hargaSetempatWrapper, hargaStandarWrapper, keteranganWrapper;
            final EditText barang, kualitas, satuanStandar, merk, satuanSetempat, panjang, lebar, tinggi, berat, konversi
                    , hargaSetempat, hargaStandar, keterangan;
            barang = (EditText) view.findViewById(R.id.barang);
            kualitas = (EditText) view.findViewById(R.id.kualitas);
            satuanStandar = (EditText) view.findViewById(R.id.satuanStandar);
            merk = (EditText) view.findViewById(R.id.merk);
            satuanSetempat = (EditText) view.findViewById(R.id.satuanSetempat);
            panjang = (EditText) view.findViewById(R.id.panjang);
            lebar = (EditText) view.findViewById(R.id.lebar);
            tinggi = (EditText) view.findViewById(R.id.tinggi);
            berat = (EditText) view.findViewById(R.id.berat);
            konversi = (EditText) view.findViewById(R.id.konversi);
            hargaSetempat = (EditText) view.findViewById(R.id.hargaSetempat);
            hargaStandar = (EditText) view.findViewById(R.id.hargaStandar);
            keterangan = (EditText) view.findViewById(R.id.keterangan);

            barangWrapper = (TextInputLayout) view.findViewById(R.id.barangWrapper);
            kualitasWrapper = (TextInputLayout) view.findViewById(R.id.kualitasWrapper);
            satuanStandarWrapper = (TextInputLayout) view.findViewById(R.id.satuanStandarWrapper);
            merkWrapper = (TextInputLayout) view.findViewById(R.id.merkWrapper);
            satuanSetempatWrapper = (TextInputLayout) view.findViewById(R.id.satuanSetempatWrapper);
            panjangWrapper = (TextInputLayout) view.findViewById(R.id.panjangWrapper);
            lebarWrapper = (TextInputLayout) view.findViewById(R.id.lebarWrapper);
            tinggiWrapper = (TextInputLayout) view.findViewById(R.id.tinggiWrapper);
            beratWrapper = (TextInputLayout) view.findViewById(R.id.beratWrapper);
            konversiWrapper = (TextInputLayout) view.findViewById(R.id.konversiWrapper);
            hargaSetempatWrapper = (TextInputLayout) view.findViewById(R.id.hargaSetempatWrapper);
            hargaStandarWrapper = (TextInputLayout) view.findViewById(R.id.hargaStandarWrapper);
            keteranganWrapper = (TextInputLayout) view.findViewById(R.id.keteranganWrapper);

            barangWrapper.setBackgroundResource(R.drawable.row_disable);
            barang.setEnabled(false);
            barang.setText(item.getBarang());
            kualitasWrapper.setBackgroundResource(R.drawable.row_disable);
            kualitas.setEnabled(false);
            kualitas.setText(item.getKualitas());

            if(((ObjectKualitasTipe1) item).getSatuan_standar().equals("000-000")){
                satuanStandarWrapper.setBackgroundResource(R.drawable.row_disable);
                satuanStandar.setEnabled(false);
                satuanStandarWrapper.setVisibility(View.GONE);
            }else{
                satuanStandar.setText(((ObjectKualitasTipe1) item).getSatuan_standar());
            }
            if(((ObjectKualitasTipe1) item).getMerk().equals("000-000")){
                merkWrapper.setBackgroundResource(R.drawable.row_disable);
                merk.setEnabled(false);
                merkWrapper.setVisibility(View.GONE);
            }else{
                merk.setText(((ObjectKualitasTipe1) item).getMerk());
            }
            if(((ObjectKualitasTipe1) item).getSatuan_setempat().equals("000-000")){
                satuanSetempatWrapper.setBackgroundResource(R.drawable.row_disable);
                satuanSetempat.setEnabled(false);
                satuanSetempatWrapper.setVisibility(View.GONE);
            }else{
                satuanSetempat.setText(((ObjectKualitasTipe1) item).getSatuan_setempat());
            }
            if(((ObjectKualitasTipe1) item).getUkuran_panjang().equals("000-000")){
                panjangWrapper.setBackgroundResource(R.drawable.row_disable);
                panjang.setEnabled(false);
                panjangWrapper.setVisibility(View.GONE);
            }else{
                panjang.setText(((ObjectKualitasTipe1) item).getUkuran_panjang());
            }
            if(((ObjectKualitasTipe1) item).getUkuran_lebar().equals("000-000")){
                lebarWrapper.setBackgroundResource(R.drawable.row_disable);
                lebar.setEnabled(false);
                lebarWrapper.setVisibility(View.GONE);
            }else{
                lebar.setText(((ObjectKualitasTipe1) item).getUkuran_lebar());
            }
            if(((ObjectKualitasTipe1) item).getUkuran_tinggi().equals("000-000")){
                tinggiWrapper.setBackgroundResource(R.drawable.row_disable);
                tinggi.setEnabled(false);
                tinggiWrapper.setVisibility(View.GONE);
            }else{
                tinggi.setText(((ObjectKualitasTipe1) item).getUkuran_tinggi());
            }
            if(((ObjectKualitasTipe1) item).getUkuran_berat().equals("000-000")){
                beratWrapper.setBackgroundResource(R.drawable.row_disable);
                berat.setEnabled(false);
                beratWrapper.setVisibility(View.GONE);
            }else{
                berat.setText(((ObjectKualitasTipe1) item).getUkuran_berat());
            }
            if(((ObjectKualitasTipe1) item).getKonversi_setempat().equals("000-000")){
                konversiWrapper.setBackgroundResource(R.drawable.row_disable);
                konversi.setEnabled(false);
                konversiWrapper.setVisibility(View.GONE);
            }else{
                konversi.setText(((ObjectKualitasTipe1) item).getKonversi_setempat());
            }
            if(((ObjectKualitasTipe1) item).getHarga_satuan_setempat().equals("000-000")){
                hargaSetempatWrapper.setBackgroundResource(R.drawable.row_disable);
                hargaSetempat.setEnabled(false);
                hargaSetempatWrapper.setVisibility(View.GONE);
            }else{
                hargaSetempat.setText(((ObjectKualitasTipe1) item).getHarga_satuan_setempat());
            }
            if(((ObjectKualitasTipe1) item).getHarga_satuan_standar().equals("000-000")){
                hargaStandarWrapper.setBackgroundResource(R.drawable.row_disable);
                hargaStandar.setEnabled(false);
                hargaStandarWrapper.setVisibility(View.GONE);
            }else{
                hargaStandar.setText(((ObjectKualitasTipe1) item).getHarga_satuan_standar());
            }
            if(item.getKeterangan().equals("000-000")){
                keteranganWrapper.setBackgroundResource(R.drawable.row_disable);
                keterangan.setEnabled(false);
                keteranganWrapper.setVisibility(View.GONE);
            }else{
                keterangan.setText(item.getKeterangan());
            }

            builder.setView(view);
            builder.setPositiveButton("Simpan", null);
            builder.setNegativeButton("Tutup",null);
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialoga) {
                    Button positiveButton = (Button) dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectKualitasTipe1 itemEdit = new ObjectKualitasTipe1(item.getUid_survei(), item.getId_responden()
                                    , item.getUid_barang(), item.getBarang(), item.getUid_kualitas(), item.getKualitas()
                                    , item.getTipe() , satuanStandar.getText().toString(), merk.getText().toString(), satuanSetempat.getText().toString()
                                    , panjang.getText().toString(), lebar.getText().toString(), tinggi.getText().toString(), berat.getText().toString()
                                    , konversi.getText().toString(), hargaSetempat.getText().toString(), hargaStandar.getText().toString()
                                    , keterangan.getText().toString(), "2");
                            setStatus(objectKualitas.get(position), itemEdit);
                            Database.getInstance(myActivity).updateKualitas(itemEdit);
                            refresh();
                            dialog.dismiss();
                        }
                    });
                    Button negativeButton = (Button) dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            dialog.show();
        }else{
            //item = (ObjectKualitasTipe2) item;
            final AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);
            builder.setTitle("Ubah Kualitas");
            LayoutInflater inflater = (LayoutInflater) myActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.edit_kualitas_2, null);

            final EditText barang, kualitas, satuanUnit, nilaiSewa, keterangan;
            final TextInputLayout barangWrapper, kualitasWrapper, satuanUnitWrapper
                    , nilaiSewaWrapper, keteranganWrapper;

            barang = (EditText) view.findViewById(R.id.barang);
            kualitas = (EditText) view.findViewById(R.id.kualitas);
            satuanUnit = (EditText) view.findViewById(R.id.satuanUnit);
            nilaiSewa = (EditText) view.findViewById(R.id.nilaiSewa);
            keterangan = (EditText) view.findViewById(R.id.keterangan);

            barangWrapper = (TextInputLayout) view.findViewById(R.id.barangWrapper);
            kualitasWrapper = (TextInputLayout) view.findViewById(R.id.kualitasWrapper);
            satuanUnitWrapper = (TextInputLayout) view.findViewById(R.id.satuanUnitWrapper);
            nilaiSewaWrapper = (TextInputLayout) view.findViewById(R.id.nilaiSewaWrapper);
            keteranganWrapper = (TextInputLayout) view.findViewById(R.id.keteranganWrapper);

            barangWrapper.setBackgroundResource(R.drawable.row_disable);
            barang.setEnabled(false);
            barang.setText(item.getBarang());
            kualitasWrapper.setBackgroundResource(R.drawable.row_disable);
            kualitas.setEnabled(false);
            kualitas.setText(item.getKualitas());

            if(((ObjectKualitasTipe2) item).getSatuan_unit().equals("000-000")){
                satuanUnitWrapper.setBackgroundResource(R.drawable.row_disable);
                satuanUnit.setEnabled(false);
                satuanUnitWrapper.setVisibility(View.GONE);
            }else{
                satuanUnit.setText(((ObjectKualitasTipe2) item).getSatuan_unit());
            }
            if(((ObjectKualitasTipe2) item).getNilai_sewa().equals("000-000")){
                nilaiSewaWrapper.setBackgroundResource(R.drawable.row_disable);
                nilaiSewa.setEnabled(false);
                nilaiSewaWrapper.setVisibility(View.GONE);
            }else{
                nilaiSewa.setText(((ObjectKualitasTipe2) item).getNilai_sewa());
            }
            if(item.getKeterangan().equals("000-000")){
                keteranganWrapper.setBackgroundResource(R.drawable.row_disable);
                keterangan.setEnabled(false);
                keteranganWrapper.setVisibility(View.GONE);
            }else{
                keterangan.setText(item.getKeterangan());
            }

            builder.setView(view);
            builder.setPositiveButton("Simpan", null);
            builder.setNegativeButton("Tutup",null);
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialoga) {
                    Button positiveButton = (Button) dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectKualitasTipe2 itemEdit = new ObjectKualitasTipe2(item.getUid_survei(), item.getId_responden()
                                    , item.getUid_barang(), item.getBarang(), item.getUid_kualitas(), item.getKualitas()
                                    , item.getTipe() , satuanUnit.getText().toString(), nilaiSewa.getText().toString()
                                    , keterangan.getText().toString(), "2");
                            setStatus(objectKualitas.get(position), itemEdit);
                            Database.getInstance(myActivity).updateKualitas(itemEdit);
                            refresh();
                            dialog.dismiss();
                        }
                    });
                    Button negativeButton = (Button) dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            dialog.show();
        }
    }

    public void setStatus(ObjectKualitas item, ObjectKualitas baru){
        baru.setStatus("2");
        if(!item.getTipe().equals("5") && !item.getTipe().equals("7")){
            baru = (ObjectKualitasTipe1) baru;
            if(!((ObjectKualitasTipe1) item).getSatuan_standar().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getSatuan_standar().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setSatuan_standar("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getMerk().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getMerk().trim().equals("")){
                    baru.setStatus("1");
                }
            }else {
                ((ObjectKualitasTipe1) baru).setMerk("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getSatuan_setempat().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getSatuan_setempat().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setSatuan_setempat("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getUkuran_panjang().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getUkuran_panjang().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setUkuran_panjang("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getUkuran_lebar().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getUkuran_lebar().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setUkuran_lebar("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getUkuran_tinggi().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getUkuran_tinggi().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setUkuran_tinggi("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getUkuran_berat().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getUkuran_berat().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setUkuran_berat("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getKonversi_setempat().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getKonversi_setempat().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setKonversi_setempat("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getHarga_satuan_setempat().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getHarga_satuan_setempat().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setHarga_satuan_setempat("000-000");
            }
            if(!((ObjectKualitasTipe1) item).getHarga_satuan_standar().equals("000-000")){
                if(((ObjectKualitasTipe1) baru).getHarga_satuan_standar().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe1) baru).setHarga_satuan_standar("000-000");
            }
            if(item.getKeterangan().equals("000-000")){
                baru.setKeterangan("000-000");
            }
        }else {
            baru = (ObjectKualitasTipe2) baru;
            if(!((ObjectKualitasTipe2) item).getSatuan_unit().equals("000-000")){
                if(((ObjectKualitasTipe2) item).getSatuan_unit().trim().equals("")){
                    baru.setStatus("1");
                }
            }else{
                ((ObjectKualitasTipe2) baru).setSatuan_unit("000-000");
            }
            if(!((ObjectKualitasTipe2) item).getNilai_sewa().equals("000-000")){
                if(((ObjectKualitasTipe2) item).getNilai_sewa().trim().equals("")){
                    baru.setStatus("1");
                }
            }else {
                ((ObjectKualitasTipe2) baru).setNilai_sewa("000-000");
            }
            if(item.getKeterangan().equals("000-000")){
                baru.setKeterangan("000-000");
            }
        }
    }

    @Override
    public Filter getFilter() {
        if(kualitasFilter==null){
            kualitasFilter = new KualitasFilter();
        }
        return kualitasFilter;
    }

    public void closeFilter(){
        objectKualitas = new ArrayList<>();
        objectKualitas = Database.getInstance(myActivity).getKualitas(objectResponden.getUid_survei()
                , objectResponden.getId(), objectResponden.getTipeResponden());
        notifyDataSetChanged();
    }

    private class KualitasFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            ArrayList<ObjectKualitas> objectKualitasLocal = new ArrayList<>();
            objectKualitasLocal = Database.getInstance(myActivity).getKualitasFilter(objectResponden.getUid_survei()
                    , objectResponden.getId(), objectResponden.getTipeResponden(), charSequence.toString());
            if(objectKualitasLocal==null){
                Toast.makeText(myActivity,"Tidak ada item", Toast.LENGTH_LONG)
                        .show();
                return null;
            }else{
                filterResults.values = objectKualitasLocal;
                filterResults.count = objectKualitasLocal.size();
                return filterResults;
            }
            //notifyDataSetChanged();
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults!=null){
                objectKualitas = (ArrayList<ObjectKualitas>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }
}
