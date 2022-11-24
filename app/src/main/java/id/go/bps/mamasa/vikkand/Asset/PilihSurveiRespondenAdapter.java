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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.go.bps.mamasa.vikkand.Entity.ObjectRespondenHelper;
import id.go.bps.mamasa.vikkand.Entity.ObjectSurveiHelper;
import id.go.bps.mamasa.vikkand.Entity.ObjectUser;
import id.go.bps.mamasa.vikkand.PilihKualitas;
import id.go.bps.mamasa.vikkand.PilihKualitasResponden;
import id.go.bps.mamasa.vikkand.PilihSurveiRespondenActivity;
import id.go.bps.mamasa.vikkand.R;

/**
 * Created by ASUS on 01/07/2018.
 */

public class PilihSurveiRespondenAdapter  extends RecyclerView.Adapter<PilihSurveiRespondenAdapter.ViewHolder> {

    private Activity myActivity;
    private ArrayList<ObjectSurveiHelper> mDataset;

    public PilihSurveiRespondenAdapter(Activity myActivity){
        this.myActivity = myActivity;
        this.mDataset = Database.getInstance(myActivity).getAllSurveiHelper();
        if(this.mDataset==null){
            this.mDataset = new ArrayList<>();
        }
//        Toast.makeText(myActivity, "Dapat "+mDataset.size(), Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }
    public void refresh(){
        this.mDataset = Database.getInstance(myActivity).getAllSurveiHelper();
        if(this.mDataset==null){
            this.mDataset = new ArrayList<>();
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survei_responden, parent, false);
       ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectSurveiHelper item = this.mDataset.get(position);
        holder.nama.setText("Triwulan "+item.getObjectSurvei().getBulan()+" Tahun "+item.getObjectSurvei().getTahun());
        holder.status.setText((item.getEditBenar()+item.getSudahDikirim()+item.getSubmit())+"/"+item.getAll());
        if(item.getObjectSurvei().getStatus().equals("0")){
            holder.submitWrap.setVisibility(View.GONE);
            holder.downloadWrap.setVisibility(View.VISIBLE);
        }else if(item.getSubmit() == item.getAll()){
            holder.cardView.setCardBackgroundColor(myActivity.getResources().getColor(R.color.greyLightCariJadwal));
            holder.submitWrap.setVisibility(View.GONE);
            holder.downloadWrap.setVisibility(View.GONE);
        }else {
            if(item.getAll()==(item.getEditBenar()+item.getSudahDikirim())){
                holder.submitWrap.setVisibility(View.VISIBLE);
                holder.downloadWrap.setVisibility(View.GONE);
            }else{
                holder.submitWrap.setVisibility(View.GONE);
                holder.downloadWrap.setVisibility(View.GONE);
            }
        }

//        if(position==0){
//            ((View)holder.cardView).getLayoutParams()..
//        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private CardView cardView;
        private ImageView download, submit;
        private LinearLayout downloadWrap, submitWrap;
        private TextView nama;
        private TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_survei);
            download = (ImageView) itemView.findViewById(R.id.download);
            submit = (ImageView) itemView.findViewById(R.id.upload);
            nama = (TextView) itemView.findViewById(R.id.item_name);
            status = (TextView) itemView.findViewById(R.id.item_jadwal);
            downloadWrap = (LinearLayout) itemView.findViewById(R.id.downloadWrap);
            submitWrap = (LinearLayout) itemView.findViewById(R.id.uploadWrap);
            //submitWrapper = (LinearLayout) itemView.findViewById(R.id.submitWrap);

            cardView.setOnClickListener(this);
            submit.setOnClickListener(this);
            download.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==cardView){
                if(mDataset.get(getLayoutPosition()).getSubmit()!=mDataset.get(getLayoutPosition()).getAll()){
                    Intent intent = new Intent(myActivity, PilihKualitasResponden.class);
                    intent.putExtra(Database.K_UID_SURVEI, mDataset.get(getLayoutPosition()).getObjectSurvei().getUid_survei());
                    myActivity.startActivity(intent);
                }else{
                    Toast.makeText(myActivity, "Survei "+mDataset.get(getLayoutPosition()).getObjectSurvei().getUid_survei()+" telah dikirim", Toast.LENGTH_SHORT).show();
                }
            }else if(v==submit){
                submitSurvei(getLayoutPosition());
            }else if(v==download){
                downloadSurvei(getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {

            return false;
        }
    }

    public void downloadSurvei(int position){
        ObjectSurveiHelper item = mDataset.get(position);
        ((PilihSurveiRespondenActivity)myActivity).startDownload(item.getObjectSurvei());
        //getData.submitResponden(this.objectUser.getJwtToken(), this.objectUser.getIsPetugas().equals("1"), item.getObjectResponden());
    }


    public void submitSurvei(int position){
        ObjectSurveiHelper item = mDataset.get(position);
        ((PilihSurveiRespondenActivity)myActivity).startSubmit(item.getObjectSurvei());
        //getData.submitResponden(this.objectUser.getJwtToken(), this.objectUser.getIsPetugas().equals("1"), item.getObjectResponden());
    }
}
