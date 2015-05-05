package androidpath.ll.leweathert.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidpath.ll.leweathert.Model.Hour;
import androidpath.ll.leweathert.R;

/**
 * Created by Le on 2015/5/5.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    private Hour[] mHours;

    public HourAdapter(Hour[] hours) {
        mHours = hours;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hourly_list_item, viewGroup, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder hourViewHolder, int i) {
        hourViewHolder.bindHour(mHours[i]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {
        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTemperateureLabel;
        public ImageView mIcon;


        public HourViewHolder(View itemView) {
            super(itemView);
            mTimeLabel = (TextView) itemView.findViewById(R.id.hourly_time_label);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.hourly_summary_label);
            mTemperateureLabel = (TextView) itemView.findViewById(R.id.hourly_temperature_label);
            mIcon = (ImageView) itemView.findViewById(R.id.hourly_icon_imageview);
        }

        public void bindHour(Hour hour) {
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperateureLabel.setText(hour.getTemperature() + "");
            mIcon.setImageResource(hour.getIcon());

        }
    }
}
