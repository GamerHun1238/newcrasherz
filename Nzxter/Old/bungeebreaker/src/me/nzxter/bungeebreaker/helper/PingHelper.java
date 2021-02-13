package me.nzxter.bungeebreaker.helper;

import static me.nzxter.bungeebreaker.helper.VarHelper.createHandshakeMessage;
import static me.nzxter.bungeebreaker.helper.VarHelper.readVarInt;
import static me.nzxter.bungeebreaker.helper.VarHelper.writeVarInt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PingHelper {

  public static void testPing(InetSocketAddress address, String host, int port) {
    try {
      Socket socket = new Socket();
      System.out.println("Connecting...");
      socket.connect(address, 3000);
      System.out.println("Done!");
      System.out.println("Making streams...");
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());
      DataInputStream input = new DataInputStream(socket.getInputStream());

      System.out.println("Done!");
      System.out.println("Attempting handshake... " + address.toString());


      byte [] handshakeMessage = createHandshakeMessage(host, port);

      // C->S : Handshake State=1
      // send packet length and packet
      writeVarInt(output, handshakeMessage.length);
      output.write(handshakeMessage);

      // C->S : Request
      output.writeByte(0x01); //size is only 1 //was 0x01
      output.writeByte(0x00); //packet id for ping //was 0x00

      // S->C : Response
      int size = readVarInt(input);
      int packetId = readVarInt(input);

      if (packetId == -1) {
        throw new IOException("Premature end of stream.");
      }

      if (packetId != 0x00) { //we want a status response
        throw new IOException("Invalid packetID");
      }
      int length = readVarInt(input); //length of json string

      if (length == -1) {
        throw new IOException("Premature end of stream.");
      }

      if (length == 0) {
        throw new IOException("Invalid string length.");
      }

      byte[] in = new byte[length];
      input.readFully(in);  //read json string
      String json = new String(in);


      // C->S : Ping
      long now = System.currentTimeMillis();
      output.writeByte(0x09); //size of packet
      output.writeByte(0x01); //0x01 for ping
      output.writeLong(now); //time!?

      // S->C : Pong
      readVarInt(input);

      System.out.println("length:" + length + ", size: " + size);
      packetId = readVarInt(input);
      if (packetId == -1) {
        throw new IOException("Premature end of stream.");
      }

      if (packetId != 0x01) {
        throw new IOException("Invalid packetID");
      }
      long pingtime = input.readLong(); //read response


      // print out server info
      System.out.println(json);

      System.out.println("Done!");

    }
    catch (Exception ex) {
      ex.printStackTrace();;
    }
  }

}
