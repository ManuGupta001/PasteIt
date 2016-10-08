package io.github.nickdex.pasteit;

/**
 * Created by nick on 8/10/16.
 */

public class ClipItem {
    private String text;
    private String deviceName;
    private String deviceType;


    public ClipItem() {
    }

    public ClipItem(String text, String deviceName) {
        this.text = text;
        this.deviceName = deviceName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}

