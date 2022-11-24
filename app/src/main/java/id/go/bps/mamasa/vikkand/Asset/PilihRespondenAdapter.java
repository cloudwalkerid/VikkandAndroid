package id.go.bps.mamasa.vikkand.Asset;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.go.bps.mamasa.vikkand.Entity.ObjectRespondenHelper;
import id.go.bps.mamasa.vikkand.Entity.ObjectUser;
import id.go.bps.mamasa.vikkand.PilihKualitas;
import id.go.bps.mamasa.vikkand.R;

/**
 * Created by ASUS on 28/06/2018.
 */

public class PilihRespondenAdapter extends RecyclerView.Adapter<PilihRespondenAdapter.ViewHolder> {

    private Activity myActivity;
    private String uuid_Survei;
    private String tipe;
    private ArrayList<ObjectRespondenHelper> mDataset;
    private GetData getData;
    private ObjectUser objectUser;

    public PilihRespondenAdapter(Activity myActivity, String uuid_Survei, String tipe){
        this.myActivity = myActivity;
        this.uuid_Survei = uuid_Survei;
        this.tipe = tipe;
        this.mDataset = Database.getInstance(myActivity).getAllRespondenFromSurveiByTipe(uuid_Survei
                , tipe);
        if(this.mDataset==null){
            this.mDataset = new ArrayList<>();
        }
//        Toast.makeText(myActivity, "Dapat "+mDataset.size(), Toast.LENGTH_SHORT).show();

        this.objectUser = Database.getInstance(myActivity).getWhoIam();
        getData = new GetData(myActivity);
        notifyDataSetChanged();
    }
    public void refresh(){
        this.mDataset = Database.getInstance(myActivity).getAllRespondenFromSurveiByTipe(uuid_Survei
                , tipe);
        if(this.mDataset==null){
            this.mDataset = new ArrayList<>();
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_responden, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectRespondenHelper item = this.mDataset.get(position);
        holder.nama.setText(item.getObjectResponden().getNama());
        holder.status.setText((item.getEditBenar()+item.getSudahDikirim()+item.getSubmit())+"/"+item.getAll());
        if(item.getObjectResponden().getUrlPhoto()!= null){
            if(!item.getObjectResponden().getUrlPhoto().trim().equals("")){
                Glide.with(myActivity)
                        .load(URLPath.getUrlPhoto(false)+item.getObjectResponden().getUrlPhoto())
                        .apply(new RequestOptions().placeholder(R.drawable.user)
                                .error(R.drawable.user))
                        .into(holder.photo);
            }
        }
        if(item.getObjectResponden().getStatus().equals("1")){
            holder.submitWrapper.setVisibility(View.GONE);
            holder.cardView.setCardBackgroundColor(myActivity.getResources().getColor(R.color.greyLightCariJadwal));
        }else {
            if(item.getAll()==(item.getEditBenar()+item.getSudahDikirim()+item.getSubmit())){
                holder.submitWrapper.setVisibility(View.VISIBLE);
            }else{
                holder.submitWrapper.setVisibility(View.GONE);
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
        private CircleImageView photo, submit;
        private TextView nama;
        private TextView status;
        private LinearLayout submitWrapper;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_responden);
            photo = (CircleImageView) itemView.findViewById(R.id.photo);
            submit = (CircleImageView) itemView.findViewById(R.id.submit);
            nama = (TextView) itemView.findViewById(R.id.item_name);
            status = (TextView) itemView.findViewById(R.id.item_status);
            submitWrapper = (LinearLayout) itemView.findViewById(R.id.submitWrap);

            cardView.setOnClickListener(this);
            submit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==cardView){
                if(!mDataset.get(getLayoutPosition()).getObjectResponden().getStatus().equals("1")){
                    Intent intent = new Intent(myActivity, PilihKualitas.class);
                    intent.putExtra(Database.K_UID_SURVEI, uuid_Survei);
                    intent.putExtra(Database.K_TIPE_RESPONDEN, tipe);
                    intent.putExtra(Database.K_ID_RESPONDEN, mDataset.get(getLayoutPosition()).getObjectResponden().getId());
                    myActivity.startActivity(intent);
                }else{
                    Toast.makeText(myActivity, "Responden "+mDataset.get(getLayoutPosition()).getObjectResponden().getNama()+" telah dikirim", Toast.LENGTH_SHORT).show();
                }
            }else if(v==submit){
                submitResponden(getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {

            return false;
        }
    }

    public void submitResponden(int position){
        ObjectRespondenHelper item = mDataset.get(position);
        getData.submitResponden(this.objectUser.getJwtToken(), this.objectUser.getIsPetugas().equals("1"), item.getObjectResponden());
    }
}
