package icu.nslog.dns;

import org.xbill.DNS.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @className: DnsTunnel
 * @description: TODO
 * @author: cookun
 * @date: 1/14/22
 **/
public class Tunnel {
    public static final int PACKET_MAX_SIZE = 512;
    public static final int DEFAULT_DNS_SERVER_PORT = 5553;
    private InetAddress address;
    private int port;
    private DatagramSocket socket;

    public Tunnel() throws SocketException {
        this.socket = new DatagramSocket();
    }

    public Tunnel(InetAddress address) throws SocketException {
        this.address = address;
        this.port = DEFAULT_DNS_SERVER_PORT;
        this.socket = new DatagramSocket();
    }

    public Tunnel(InetAddress address, int port) throws SocketException {
        this.address = address;
        this.port = port;
        this.socket = new DatagramSocket();
    }

    public Tunnel(DatagramSocket socket, InetAddress address, int port) throws SocketException {
        this.address = address;
        this.port = port;
        this.socket = socket;
    }

    public void listen(InetAddress address, int port) throws SocketException {
        this.socket = new DatagramSocket(port, this.address);
    }

    public void send(Message message) throws IOException {
        byte[] data = message.toWire();
        this.socket.send(new DatagramPacket(data, data.length, this.address, this.port));
    }

    public Message receive() throws IOException {
        DatagramPacket packet = this.allocate();
        socket.receive(packet);
        return new Message(packet.getData());
    }

    public Message receive(DatagramPacket packet) throws IOException {
        socket.receive(packet);
        return new Message(packet.getData());
    }


    public DatagramPacket allocate() {
        byte[] data = new byte[PACKET_MAX_SIZE];
        return new DatagramPacket(data, data.length);
    }

    public DatagramPacket allocate(int size) {
        byte[] data = new byte[size];
        return new DatagramPacket(data, data.length);
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }
}
