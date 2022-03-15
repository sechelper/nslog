package icu.nslog;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;
import icu.nslog.dns.Resolver;
import icu.nslog.dns.Tunnel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.xbill.DNS.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.List;

/**
 * @className: NslogLaunch
 * @description: nslog launch
 * @author: cookun
 * @date: 3/4/22
 **/

@EnableScheduling
@SpringBootApplication
public class NslogLaunch {
    private static boolean RUNNING = true;

    @Parameter
    public List<String> parameters = Lists.newArrayList();

    @Parameter(names = "--help", help = true, order = 5)
    private boolean help;

    @Parameter(names = "--agent", help = true)
    private boolean agent;

    @Parameter(names = "-l", description = "Nslog listen address")
    public String nsListenAddress;

    @Parameter(names = "-p", description = "Nslog listen port")
    public int nsListenPort;

    @Parameter(names = "--address", description = "Nslog service api listen address")
    public String serviceAddress = "127.0.0.1";

    @Parameter(names = "--port", description = "Nslog service api listen port")
    public String servicePort = "18080";

    public void run() throws IOException {
        String[] args = new String[]{"--server.address=" + serviceAddress, "--server.port=" + servicePort};

        if(!agent){
            Tunnel service = new Tunnel();
            service.listen(InetAddress.getByName(nsListenAddress), nsListenPort);
            while (RUNNING) {
                try {
                    DatagramPacket packet = service.allocate();
                    Message message = service.receive(packet);
                    //TODO support tunnel list {new Tunnel(InetAddress.getByName("8.8.8.8"), 53), new Tunnel(InetAddress.getByName("114.114.114.114"), 53)}
                    //TODO support domain list {baidu.com., google.com.}
                    Resolver resolver = new Resolver(message, new Tunnel(service.getSocket(), packet.getAddress(), packet.getPort()), new Tunnel(InetAddress.getByName("8.8.8.8"), 53), "baidu.com.");
                    resolver.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //TODO Support two modes agent and service at the same time
        }else{
            // run nslog rest api service
            SpringApplication.run(NslogLaunch.class, args);
        }


    }

}
