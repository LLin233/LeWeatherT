package androidpath.ll.leweathert.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Le on 2015/5/3.
 */
public class Hour implements Parcelable {
    private long mTime;
    private String mSummary;
    private String mIcon;
    private double mTemperature;
    private String mTimezone;

    public Hour() {

    }

    //getter and setter
    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getIcon() {
        return Forecast.getIconId(mIcon);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getTemperature() {
        return (int)Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public String getHour(){
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        Date date = new Date(mTime * 1000);
        return formatter.format(date);

    }

    @Override
    public String toString() {
        return "Hour{" +
                "mSummary='" + mSummary + '\'' +
                ", mTemperature=" + mTemperature +
                ", mTimezone='" + mTimezone + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTemperature);
        dest.writeString(mIcon);
        dest.writeString(mTimezone);
    }

    private Hour(Parcel in) {
        mTime = in.readLong();
        mSummary = in.readString();
        mTemperature = in.readDouble();
        mIcon = in.readString();
        mTimezone = in.readString();
    }

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel source) {
            return new Hour(source);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };
}
