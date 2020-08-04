package com.example.iatest.demo.model;

import java.util.Objects;

public class Notification {
    private String notificationId;
    private String notificationName;

    public Notification(
            String notificationId,
            String notificationName
    ) {
        this.notificationId = notificationId;
        this.notificationName = notificationName;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getNotificationName() {
        return notificationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return Objects.equals(getNotificationId(), that.getNotificationId()) &&
                Objects.equals(getNotificationName(), that.getNotificationName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNotificationId(), getNotificationName());
    }
}
