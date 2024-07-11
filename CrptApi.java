import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CrptApi extends Thread{

    private TimeUnit timeUnit;

    private int requestLimit;

    private Semaphore sem= new Semaphore(requestLimit);

    private String jsonString = "{\"description\": { \"participantInn\": \"string\" }, \"doc_id\": \"string\", \"doc_status\": \"string\", \"doc_type\": \"LP_INTRODUCE_GOODS\", 109 \"importRequest\": true," +
            "\"owner_inn\": \"string\", \"participant_inn\": \"string\", \"producer_inn\": \"string\", \"production_date\": \"2020-01-23\", \"production_type\": \"string\"," +
            "\"products\": [ { \"certificate_document\": \"string\", " +
            "\"certificate_document_date\": \"2020-01-23\", \"certificate_document_number\": \"string\", \"owner_inn\": \"string\", \"producer_inn\": \"string\", \"production_date\": \"2020-01-23\"," +
            "\"tnved_code\": \"string\", \"uit_code\": \"string\", \"uitu_code\": \"string\" } ], \"reg_date\": \"2020-01-23\", \"reg_number\": \"string\"}";

    public void run(){

        try {
            sem.tryAcquire(2,timeUnit);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://ismp.crpt.ru/api/v3/lk/documents/create"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                    .header("Content-Type", "application/json")
                    .build();
            sem.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public CrptApi(TimeUnit timeUnit, int requestLimit){
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
    }
}
