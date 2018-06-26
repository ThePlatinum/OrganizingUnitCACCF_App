package innovations.tech.platinum.organizingunitcaccf;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PLATINUM
 * Date 4/18/2018
 * Time 4:29 PM
 * Package innovations.tech.platinum.organizingunitcaccf
 * Project OrganizingUnitCACCF
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mRecordTitle;
        private TextView mRecordDate , rcordedBy , pcUsed;
        private ImageView mRecordImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecordTitle = (TextView)itemView.findViewById(R.id.title);
            mRecordDate = (TextView)itemView.findViewById(R.id.date);
            mRecordImage = (ImageView)itemView.findViewById(R.id.imageEdited);
            rcordedBy = (TextView)itemView.findViewById(R.id.rcordedBy);
            pcUsed = (TextView)itemView.findViewById(R.id.pcUsed);
        }
    }
    private List<Records> RecordsListSet;

    public MyAdapter(List<Records> RecordsListSet) {
        this.RecordsListSet = RecordsListSet;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View)LayoutInflater.from(parent.getContext()).inflate(R.layout.record_card_design,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Records record = RecordsListSet.get(position);
        holder.mRecordTitle.setText(record.getRec_Title());
        holder.mRecordDate.setText(record.getRec_Date());
        holder.rcordedBy.setText(record.getRec_RecordedBy());
        holder.pcUsed.setText(record.getRec_PcUsed());
        /*String hasEdited = record.getRec_Edited().trim();
        if(hasEdited.equals("true")){
            holder.mRecordImage.setBackgroundColor(4863159);
        }
        else {
            holder.mRecordImage.setBackgroundColor(000000);
        }*/
    }
    @Override
    public int getItemCount() {
        return RecordsListSet.size();
    }
}
