package androidpath.ll.leweathert.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import androidpath.ll.leweathert.R;

/**
 * Created by Le on 2015/4/27.
 */
public class Current {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    public String getIcon() {
        return mIcon;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    private String mLocation;

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId() {
//        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
//        int iconId = R.drawable.clear_day; //by default
//
//        switch (mIcon) {
//            case "clear-night":
//                iconId = R.drawable.clear_night;
//                break;
//            case "rain":
//                iconId = R.drawable.rain;
//                break;
//            case "snow":
//                iconId = R.drawable.snow;
//                break;
//            case "sleet":
//                iconId = R.drawable.sleet;
//                break;
//            case "wind":
//                iconId = R.drawable.wind;
//                break;
//            case "fog":
//                iconId = R.drawable.fog;
//                break;
//            case "cloudy":
//                iconId = R.drawable.cloudy;
//                break;
//            case "partly-cloudy-day":
//                iconId = R.drawable.partly_cloudy;
//                break;
//            case "partly-cloudy-night":
//                iconId = R.drawable.cloudy_night;
//                break;
//        }
//
//        return iconId;
        return Forecast.getIconId(mIcon);
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateTime = new Date(getTime() * 1000);
        String formattedTime = formatter.format(dateTime);
        return formattedTime;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return (int) Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }


    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }
}


