package me.nzxter.bungeebreaker.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketHttp extends Socket {

  public SocketHttp(final String targetHost, final int targetPort, final SocketAddress socketAddress, int timeout) throws IOException  {
    this.connect(socketAddress, timeout);
    this.setSoTimeout(timeout);
    this.connectToTarget(targetHost, targetPort);
  }

  private void connectToTarget(final String targetHost, final int targetPort) throws IOException {
    final PrintStream printStream = new PrintStream(this.getOutputStream());
    printStream.println("CONNECT " + targetHost + ":" + targetPort + " HTTP/1.1");
    printStream.println("HOST: " + targetHost + ":" + targetPort);
    printStream.println();
    printStream.flush();
  }

}