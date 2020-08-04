package com.example.iatest.demo;

import com.example.iatest.demo.model.Device;
import com.example.iatest.demo.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;


@RestController
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(DemoController.class.getCanonicalName());
    private RequestHelper requestHelper;

    @Autowired
    public DemoController(
            RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    @RequestMapping(value = "/registerDevice", method = RequestMethod.POST)
    public CompletableFuture<ResponseEntity<String>> registerDevice(@RequestBody Device device) {
        logger.info("Sending a request to alert service for device with ID: {}",
                device.getDeviceId());
        return requestHelper.sendRequest(
                device.getDeviceId(),
                device.getDeviceToken()
        );
    }

    @RequestMapping(value = "/ackNotification", method = RequestMethod.POST)
    public ResponseEntity<String> ackNotification(@RequestBody Notification notification) {
    logger.info("Received an acknowledgement that notification was received with id: {}",
                notification.getNotificationId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
