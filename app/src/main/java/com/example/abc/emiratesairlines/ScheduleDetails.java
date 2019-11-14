package com.example.abc.emiratesairlines;

/**
 * Created by ABC on 30-Oct-17.
 */


public class ScheduleDetails  {

    String Flight_Number, Flight_date, Flight_Time, From, To, Time,key;

    public ScheduleDetails() {

    }

    public ScheduleDetails(String number, String date ) {
        Flight_Number = number;
        Flight_date = date;

    }

    public ScheduleDetails(String number, String date, String time, String from, String to) {
        Flight_Number = number;
        Flight_date = date;
        Flight_Time = time;
        From = from;
        To = to;

    }

    public String getFlight_Number() {
        return Flight_Number;
    }

    public void setFlight_Number(String flight_Number) {
        Flight_Number = flight_Number;
    }

    public String getFlight_date() {
        return Flight_date;
    }

    public void setFlight_date(String flight_date) {
        Flight_date = flight_date;
    }

    public String getFlight_Time() {
        return Flight_Time;
    }

    public void setFlight_Time(String flight_Time) {
        Flight_Time = flight_Time;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ScheduleDetails{" +
                "Flight Number='" + Flight_Number + '\'' +
                ", Flight Date='" + Flight_date + '\'' +
                ", Flight Time='" + Flight_Time + '\'' +
                ", From='" + From + '\'' +
                ", To='" + To + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        ScheduleDetails sd = (ScheduleDetails) obj;
        return (sd.key.equals(key));
    }


}
