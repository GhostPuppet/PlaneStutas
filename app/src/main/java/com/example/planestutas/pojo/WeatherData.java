package com.example.planestutas.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class WeatherData implements Parcelable{
    private String date;
    private String dayPictureUrl;
    private String nightPictureUrl;
    private String weather;
    private String wind;
    private String temperature;
    private String dayPicPath;
    private String nightPicPath;

    public WeatherData(){}

    protected WeatherData(Parcel in) {
        date = in.readString();
        dayPictureUrl = in.readString();
        nightPictureUrl = in.readString();
        weather = in.readString();
        wind = in.readString();
        temperature = in.readString();
        dayPicPath = in.readString();
        nightPicPath = in.readString();
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayPictureUrl() {
        return dayPictureUrl;
    }

    public void setDayPictureUrl(String dayPictureUrl) {
        this.dayPictureUrl = dayPictureUrl;
    }

    public String getNightPictureUrl() {
        return nightPictureUrl;
    }

    public void setNightPictureUrl(String nightPictureUrl) {
        this.nightPictureUrl = nightPictureUrl;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(dayPictureUrl);
        dest.writeString(nightPictureUrl);
        dest.writeString(weather);
        dest.writeString(wind);
        dest.writeString(temperature);
        dest.writeString(dayPicPath);
        dest.writeString(nightPicPath);
    }

    public String getDayPicPath() {
        return dayPicPath;
    }

    public void setDayPicPath(String dayPicPath) {
        this.dayPicPath = dayPicPath;
    }

    public String getNightPicPath() {
        return nightPicPath;
    }

    public void setNightPicPath(String nightPicPath) {
        this.nightPicPath = nightPicPath;
    }
}
