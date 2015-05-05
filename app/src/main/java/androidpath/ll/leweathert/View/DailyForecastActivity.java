package androidpath.ll.leweathert.View;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import androidpath.ll.leweathert.Model.BackgroundColor;
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
        //update background
        BackgroundColor mBackgroundColor = ((BackgroundColor) getApplicationContext());
        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                mBackgroundColor.getColors());
        container.setBackground(bg);

        //update city
        mLocation.setText(intent.getStringExtra(MainActivity.LOCATION));
        //update list
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String dayOfTheWeek = mDays[position].getDayOfTheWeek();
        String conditions = mDays[position].getSummary();
        String highTemperature = mDays[position].getTemperatureMax() + "";
        String msg = String.format("On %s the high will be %s"+ getString(R.string.degree) +" and it will be %s",
                dayOfTheWeek,
                highTemperature,
                conditions);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
