package de.Luca;

import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {
   private final String ipAddress;
   private final int serverPort;

   private ServerAddress(String serverIP, int serverPort) {
      this.ipAddress = serverIP;
      this.serverPort = serverPort;
   }

   public String getIP() {
      return IDN.toASCII(this.ipAddress);
   }

   public int getPort() {
      return this.serverPort;
   }

   public static ServerAddress resolveAddress(String ip) {
      if(ip == null) {
         return null;
      } else {
         String[] var1 = ip.split(":");
         if(ip.startsWith("[")) {
            int var5 = ip.indexOf("]");
            if(var5 > 0) {
               String var6 = ip.substring(1, var5);
               String var7 = ip.substring(var5 + 1).trim();
               if(var7.startsWith(":") && var7.length() > 0) {
                  var7 = var7.substring(1);
                  var1 = new String[]{var6, var7};
               } else {
                  var1 = new String[]{var6};
               }
            }
         }

         if(var1.length > 2) {
            var1 = new String[]{ip};
         }

         String var51 = var1[0];
         int var61 = var1.length > 1?parseIntWithDefault(var1[1], 25565):25565;
         if(var61 == 25565) {
            String[] var71 = getServerAddress(var51);
            var51 = var71[0];
            var61 = parseIntWithDefault(var71[1], 25565);
         }

         return new ServerAddress(var51, var61);
      }
   }

   private static String[] getServerAddress(String p_78863_0_) {
      try {
         String var6 = "com.sun.jndi.dns.DnsContextFactory";
         Class.forName("com.sun.jndi.dns.DnsContextFactory");
         Hashtable var2 = new Hashtable();
         var2.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         var2.put("java.naming.provider.url", "dns:");
         var2.put("com.sun.jndi.dns.timeout.retries", "1");
         InitialDirContext var3 = new InitialDirContext(var2);
         Attributes var4 = var3.getAttributes("_minecraft._tcp." + p_78863_0_, new String[]{"SRV"});
         String[] var5 = var4.get("srv").get().toString().split(" ", 4);
         return new String[]{var5[3], var5[2]};
      } catch (Throwable var61) {
         return new String[]{p_78863_0_, Integer.toString(25565)};
      }
   }

   private static int parseIntWithDefault(String p_78862_0_, int p_78862_1_) {
      try {
         return Integer.parseInt(p_78862_0_.trim());
      } catch (Exception var3) {
         return p_78862_1_;
      }
   }
}
