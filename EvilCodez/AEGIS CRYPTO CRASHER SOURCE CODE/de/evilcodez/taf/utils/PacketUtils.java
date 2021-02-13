package de.evilcodez.taf.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketUtils {
   public static void writeVarInt(DataOutputStream out, int value) throws IOException {
      while((value & -128) != 0) {
         out.writeByte(value & 127 | 128);
         value >>>= 7;
      }

      out.writeByte(value);
   }

   public static void writeString(DataOutputStream out, String value) throws IOException {
      byte[] data = value.getBytes(StandardCharsets.UTF_8);
      byte[] after = new byte[data.length];
      writeVarInt(out, data.length);
      out.write(data, 0, data.length);
   }

   public static void writeStringC(DataOutputStream out, String value) throws IOException {
      byte[] data = value.getBytes(StandardCharsets.UTF_8);
      byte[] after = new byte[data.length];

      for(int i = 0; i < data.length; ++i) {
         after[i] = data[i];
      }

      writeVarInt(out, after.length);
      out.write(after, 0, after.length);
   }

   public static int readVarInt(DataInputStream in) throws IOException {
      int i = 0;
      int j = 0;

      byte k;
      do {
         k = in.readByte();
         i |= (k & 127) << j++ * 7;
         if(j > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while((k & 128) == 128);

      return i;
   }

   public static String readString(DataInputStream in) throws IOException {
      int len = readVarInt(in);
      byte[] data = new byte[len];
      in.readFully(data);
      return new String(data, 0, len, StandardCharsets.UTF_8);
   }

   public static byte[] readByteArray(DataInputStream in) throws IOException {
      int len = readVarInt(in);
      byte[] data = new byte[len];
      in.readFully(data);
      return data;
   }

   public static void writeByteArray(DataOutputStream out, byte[] data) throws IOException {
      writeVarInt(out, data.length);
      out.write(data, 0, data.length);
   }

   public static void writeHandshakePacket(DataOutputStream out, String ip, int port, int protocol, int state) throws IOException {
      writeVarInt(out, 0);
      writeVarInt(out, protocol);
      writeString(out, ip);
      out.writeShort(port);
      writeVarInt(out, state);
   }

   public static void writeQueryRequestPacket(DataOutputStream out) throws IOException {
      writeVarInt(out, 0);
   }

   public static void writePingPacket(DataOutputStream out, long clientTime) throws IOException {
      writeVarInt(out, 1);
      out.writeLong(clientTime);
   }

   public static void writePacket(byte[] packetData, DataOutputStream out) throws IOException {
      writeVarInt(out, packetData.length);
      out.write(packetData);
   }

   public static void writePacketSomeTimes(byte[] packetData, DataOutputStream out, int times) throws IOException {
      ByteArrayOutputStream LoginOutPutStream = null;
      DataOutputStream login = new DataOutputStream(LoginOutPutStream = new ByteArrayOutputStream());
      writeVarInt(login, packetData.length);
      login.write(packetData);
      byte[] bytes = LoginOutPutStream.toByteArray();

      for(int i = 0; i < times; ++i) {
         out.write(bytes);
      }

   }

   public static String readServerDataPacket(DataInputStream in) throws IOException {
      byte id = in.readByte();
      return id != 0?null:readString(in);
   }

   public static long readPongPacket(DataInputStream in) throws IOException {
      byte id = in.readByte();
      return id != 1?-1L:in.readLong();
   }
}
