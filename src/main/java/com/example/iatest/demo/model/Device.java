package com.example.iatest.demo.model;

import java.util.Objects;

public class Device {
    private String deviceId;
    private String deviceToken;

    public Device(
            String deviceId,
            String deviceToken
    ) {
        this.deviceId = deviceId;
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return Objects.equals(getDeviceId(), device.getDeviceId()) &&
                Objects.equals(getDeviceToken(), device.getDeviceToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeviceId(), getDeviceToken());
    }
}
