package icu.nslog.dns;

import com.fasterxml.jackson.databind.ObjectMapper;
import icu.nslog.api.bean.DnsRecord;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.xbill.DNS.Message;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * @className: Arching
 * @description: TODO
 * @author: cookun
 * @date: 1/14/22
 **/
public class Arching {
    private final String basicAuthorization;
    private RecordEncoder encoder = new AESEncoder();

    @Autowired
    private String archingApi = "http://127.0.0.1:8083/openapi/arching";

    public Arching(String appId, String secret) {
        byte[] encodedAuth = Base64.encodeBase64(
                String.format("%s:%s", appId, secret).getBytes(StandardCharsets.ISO_8859_1));
        basicAuthorization = "Basic " + new String(encodedAuth);
    }

    public void arching(Message message, String source) throws IOException, URISyntaxException, InterruptedException {
        String domain = message.getQuestion().getName().toString(true);
        ObjectMapper objectMapper = new ObjectMapper();
//        String body = objectMapper.writeValueAsString(new DnsRecord(domain, source));

        String body = String.format("{\"domain\":\"%s\", \"source\",\"%s\"}", domain, encoder.decrypt(source));

        //xxx.com --> message.getQuestion().getName().toString(true);
        HttpRequest request = HttpRequest.newBuilder(new URI(archingApi))
                .header("Content-Type", "application/json")
                .header(HttpHeaders.AUTHORIZATION, basicAuthorization)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public String encoder(){
        return "";
    }

    public RecordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(RecordEncoder encoder) {
        this.encoder = encoder;
    }
}
