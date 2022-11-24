package id.go.bps.mamasa.vikkand.Asset;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.go.bps.mamasa.vikkand.Entity.ObjectRespondenHelper;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurvei;
import id.go.bps.mamasa.vikkand.PilihJenisResponden;
import id.go.bps.mamasa.vikkand.PilihSurveiActivity;
import id.go.bps.mamasa.vikkand.R;

/**
 * Created by ASUS on 29/06/2018.
 */

public class PilihSurveiAdaapter extends RecyclerView.Adapter<PilihSurveiAdaapter.ViewHolder> {

    private Activity myActivity;
    private ArrayList<ObjectSurvei> mDataset;

    public PilihSurveiAdaapter(Activity myActivity){
        this.myActivity = myActivity;
        this.mDataset = Database.getInstance(myActivity).getAllSurvei();
        if(this.mDataset==null){
            this.mDataset = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
    public void refresh(){
        this.mDataset = Database.getInstance(myActivity).getAllSurvei();
        if(this.mDataset==null){
            this.mDataset = new ArrayList<>();
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survei, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectSurvei item = this.mDataset.get(position);
        holder.nama.setText("Triwulan "+item.getBulan()+" "+item.getTahun());
        holder.status.setText(item.getMulai()+" s/d "+item.getAkhir());
        if(item.getStatus().equals("1")){
            holder.download.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private ImageView download;
        private TextView nama;
        private TextView status;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_survei);
            download = (ImageView) itemView.findViewById(R.id.download);
            nama = (TextView) itemView.findViewById(R.id.item_name);
            status = (TextView) itemView.findViewById(R.id.item_jadwal);
            download.setOnClickListener(this);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==download){
                downloadSurvei(getLayoutPosition());
            }else if(v==cardView){
                if(mDataset.get(getLayoutPosition()).getStatus().equals("1")){
                    Intent intent = new Intent(myActivity, PilihJenisResponden.class);
                    intent.putExtra(Database.K_UID_SURVEI, mDataset.get(getLayoutPosition()).getUid_survei());
                    myActivity.finish();
                    myActivity.startActivity(intent);
                }else{
                    Toast.makeText(myActivity,"Silahkan Unduh Survei Terlebih Dahulu", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {

            return false;
        }
    }
    public void downloadSurvei(int position){
        ((PilihSurveiActivity)myActivity).startDownload(mDataset.get(position), mDataset.get(position).getBulan(), mDataset.get(position).getTahun());
    }
}
