package androidpath.ll.leweathert.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import androidpath.ll.leweathert.Model.Hour;
import androidpath.ll.leweathert.R;
import androidpath.ll.leweathert.adapters.HourAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class HourlyForecastActivity extends ActionBarActivity {
    private Hour[] mHours;

    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.inject(this);


        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);

        HourAdapter adapter = new HourAdapter(mHours);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //for better performance
        mRecyclerView.setHasFixedSize(true);
    }

}
