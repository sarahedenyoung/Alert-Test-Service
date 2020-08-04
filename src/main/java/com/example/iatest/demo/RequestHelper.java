package com.example.iatest.demo;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class RequestHelper {

    private Logger logger = LoggerFactory.getLogger(RequestHelper.class.getCanonicalName());
    private static final String MEDIA_TYPE = "application/json; charset=utf-8";
    private OkHttpClient httpClient = new OkHttpClient();
    private Executor threadPoolExecutor;

    @Autowired
    public RequestHelper(Executor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    Response response = null;
    Request request;

    public CompletableFuture<ResponseEntity<String>> sendRequest(String deviceId, String deviceToken) {
        createRequest(deviceId, deviceToken);
        return CompletableFuture.supplyAsync(() -> {
            try {
                response = httpClient.newCall(request).execute();
            } catch (IOException e) {
                logger.error("IOException thrown by http request to Alert Service: {}",
                        e.getMessage());
            } finally {
                response.close();
            }
            return new ResponseEntity<>(HttpStatus.valueOf(response.code()));
        }, threadPoolExecutor);
    }

    private void createRequest(String deviceId, String deviceToken) {
        String url = String.format(
                "https://staging-us-01.scrt.sfdc.sh/iaalert/v1/debug/notifications/apns/00Dxx0000006GbLs/IAMessagingAlertServiceTestApp/123/demo@salesforce.com/%s/%s",
                deviceId,
                deviceToken);

        logger.info("Constructed URL: {}",
                url);

        String body = "{\n" +
                        "   \"title\" : \"Demo\",\n" +
                        "   \"body\" : \"Alert Message\",\n" +
                        "   \"senderSubjectId\" : \"demo@salesforce.com\"\n" +
                        "}";

        logger.info("Creating request with body: {}",
                body);

        RequestBody requestBody = RequestBody.create(MediaType.get(MEDIA_TYPE), body);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        logger.info("Created the following request: {}",
                request.toString());

        this.request = request;
    }
}
