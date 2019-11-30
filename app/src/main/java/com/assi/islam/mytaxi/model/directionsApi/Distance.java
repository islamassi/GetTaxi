package com.assi.islam.mytaxi.model.directionsApi;

/**
 *  copied from https://stackoverflow.com/questions/19290593/displaying-multiple-routes-using-directions-api-in-android/19329951
 */
public class Distance {
    private String text;
    private long value;

    public Distance(String text, long value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}