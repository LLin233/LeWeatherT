package androidpath.ll.leweathert.View;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidpath.ll.leweathert.Model.BackgroundColor;
import androidpath.ll.leweathert.Model.Current;
import androidpath.ll.leweathert.Model.Day;
import androidpath.ll.leweathert.Model.Forecast;
import androidpath.ll.leweathert.Model.Hour;
import androidpath.ll.leweathert.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String LOCATION = "LOCATION";
    public static final String API_HEADER = "https://api.forecast.io/forecast/";

    //private Current mCurrent;
    private Forecast mForecast;
    private BackgroundColor mBackgroundColor;
    //variable geo location
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private String cityName;

    //define views
    @InjectView(R.id.temperature_label)
    TextView mTemperatureLabel;
    @InjectView(R.id.timeLabel)
    TextView mTimeLabel;
    @InjectView(R.id.humidityValue)
    TextView mHumidityValue;
    @InjectView(R.id.precipValue)
    TextView mPrecipValue;
    @InjectView(R.id.summaryLabel)
    TextView mSummaryLabel;
    @InjectView(R.id.iconImageView)
    ImageView mIconImageView;
    @InjectView(R.id.refreshImageView)
    ImageView mRefreshImageView;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.container)
    RelativeLayout container;
    @InjectView(R.id.locationLabel)
    TextView mlocationLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mProgressBar.setVisibility(View.INVISIBLE);
        // init
        mBackgroundColor = new BackgroundColor();
        buildGoogleApiClient();
        initLocationRequest();


        final double latitude = 37;
        final double longitude = -121.9668;
        cityName = "";

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastLocation != null) {
                    changeRandomBGColor();
                    requestLocationUpdates();
                    getForecast(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                } else {
                    requestLocationUpdates();
                    alertUserAboutError();
                }
            }
        });
        changeRandomBGColor();
        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude) {

        String forecastUrl = API_HEADER + getString(R.string.api_keys) + "/" + latitude + "," + longitude;


        if (isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {

                        if (response.isSuccessful()) {
                            String jsonData = response.body().string();
                            //mCurrent = getCurrentDetails(jsonData);
                            mForecast = parseForecastDetails(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.network_no_available), Toast.LENGTH_SHORT).show();
        }

    }

    private void changeRandomBGColor() {
        int[] androidColors = getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor1 = androidColors[new Random().nextInt(androidColors.length)];
        int randomAndroidColor2 = androidColors[new Random().nextInt(androidColors.length)];

        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{randomAndroidColor1, randomAndroidColor2});
        container.setBackground(bg);
        //container.setBackgroundColor(randomAndroidColor);
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }


    }

    private void updateDisplay() {
        Current mCurrent = mForecast.getCurrent();

        mTemperatureLabel.setText(mCurrent.getTemperature() + "");
        mTimeLabel.setText("At " + mCurrent.getFormattedTime() + " it will be");
        mHumidityValue.setText(mCurrent.getHumidity() + "");
        mPrecipValue.setText(mCurrent.getPrecipChance() + "%");
        mSummaryLabel.setText(mCurrent.getSummary());
        Drawable drawable = getResources().getDrawable(mCurrent.getIconId());
        mIconImageView.setImageDrawable(drawable);
        mlocationLabel.setText(mCurrent.getLocation());

    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));
        return forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        //create hour array from json data
        Day[] days = new Day[data.length()];
        for (int i = 0; i < data.length(); i++) {
            JSONObject dayJson = data.getJSONObject(i);
            Day day = new Day();
            day.setSummary(dayJson.getString("summary"));
            day.setTemperatureMax(dayJson.getDouble("temperatureMax"));
            day.setTemperatureMin(dayJson.getDouble("temperatureMin"));
            day.setIcon(dayJson.getString("icon"));
            day.setTime(dayJson.getLong("time"));
            day.setTimezone(timeZone);
            days[i] = day;
        }


        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        //create hour array from json data
        Hour[] hours = new Hour[data.length()];
        for (int i = 0; i < data.length(); i++) {
            JSONObject hourJson = data.getJSONObject(i);
            Hour hour = new Hour();
            hour.setSummary(hourJson.getString("summary"));
            hour.setTemperature(hourJson.getDouble("temperature"));
            hour.setIcon(hourJson.getString("icon"));
            hour.setTime(hourJson.getLong("time"));
            hour.setTimezone(timeZone);
            hours[i] = hour;
        }

        return hours;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        Current current = new Current();
        current.setLocation(cityName);
        String timeZone = forecast.getString("timezone");
        JSONObject currently = forecast.getJSONObject("currently");
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(forecast.getString("timezone"));

        Log.d(TAG, "From JSON : " + timeZone + current.getFormattedTime());
        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation == null) {
            Log.i(TAG, "request for new location.");
            requestLocationUpdates();
        } else {
            Log.i(TAG, mLastLocation.toString());
            handleNewLocation(mLastLocation);
        }

    }

    private void requestLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        double nlatitude = location.getLatitude();
        double nlongitude = location.getLongitude();
        cityName = this.getCityName(nlatitude, nlongitude);
        getForecast(nlatitude, nlongitude);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }


    //get CityName from Geo Infomation.

    private String getCityName(double latitude, double longitude) {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
            String cityName = builder.append(address.get(0).getLocality()).append(", ").append(address.get(0).getCountryName()).toString();
            Log.i(TAG, cityName);
            return cityName;

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    private void initLocationRequest() {
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds
    }


    @OnClick(R.id.btn_daily)
    void goToDailyActivity() {
        Intent intent = new Intent(MainActivity.this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        intent.putExtra(LOCATION, cityName);
        startActivity(intent);
    }
}

