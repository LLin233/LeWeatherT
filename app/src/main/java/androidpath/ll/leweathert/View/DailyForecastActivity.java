package androidpath.ll.leweathert.View;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import androidpath.ll.leweathert.Model.Day;
import androidpath.ll.leweathert.R;
import androidpath.ll.leweathert.adapters.DayAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DailyForecastActivity extends ListActivity {
    private Day[] mDays;

    @InjectView(R.id.DailyContainer)
    RelativeLayout container;
    @InjectView(R.id.daily_location_label)
    TextView mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        //update city
        mLocation.setText(intent.getStringExtra(MainActivity.LOCATION));
        //update list
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);

    }
}
