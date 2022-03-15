package icu.nslog.dns;

import com.fasterxml.jackson.databind.ObjectMapper;
import icu.nslog.api.bean.DnsRecord;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.xbill.DNS.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * @className: DnsResolver
 * @description: TODO
 * @author: cookun
 * @date: 1/14/22
 **/
public class Resolver implements Runnable {
    public final static String DEFAULT_RECORD = "127.0.0.1";
    private final Message message;
    private final Tunnel nslog;
    private final Tunnel proxy;
    private String domain;
    private String record;

    public Resolver(Message message, Tunnel nslog, Tunnel proxy, String domain) {
        this.message = message;
        this.nslog = nslog;
        this.proxy = proxy;
        this.domain = domain;
        this.record = DEFAULT_RECORD;
    }

    public Resolver(Message message, Tunnel nslog, Tunnel proxy, String domain, String record) {
        this.message = message;
        this.nslog = nslog;
        this.proxy = proxy;
        this.domain = domain;
        this.record = record;
    }

    @Override
    public void run() {
        try {
            // xxx.com --> message.getQuestion().getName().toString(true);
            // xxx.com. --> message.getQuestion().getName().toString();
            if (message.getQuestion().getName().subdomain(new Name(domain))) {
                message.addRecord(message.getQuestion(), Section.QUESTION);
                message.addRecord(Record.fromString(Name.root, Type.A, DClass.IN, 86400, record, Name.root), Section.ANSWER);
                this.nslog.send(message);
            } else {
                this.proxy.send(message);
                this.nslog.send(this.proxy.receive());
            }

            arching();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(this).start();
    }

    public void arching() throws IOException, URISyntaxException, InterruptedException {
        String auth =  "292ebb6c0f21429880d28eecadc8f117" + ":" + "xDdATlprQFPAqWaAiERMdmyoujhncYpt";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);

        String domain = message.getQuestion().getName().toString(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(new DnsRecord(domain, this.nslog.getAddress().getHostName(), 1));

        //xxx.com --> message.getQuestion().getName().toString(true);
        HttpRequest request = HttpRequest.newBuilder(new URI("http://127.0.0.1:8083/openapi/arching"))
                .header("Content-Type", "application/json")
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
