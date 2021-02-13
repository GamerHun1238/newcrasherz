package de.Luca;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class ProxyLoader {
   private static int proxyIterator = 0;
   private static final ArrayList<Proxy> PROXIES = new ArrayList<>();
   static boolean isset = false;

   public static void setProxys(File file) {
      if(!isset) {
         isset = true;
         PROXIES.clear();
         Scanner sc = null;

         try {
            sc = new Scanner(file);
         } catch (FileNotFoundException var5) {
            System.out.println("no proxy file");
            var5.printStackTrace();
            System.exit(1);
         }

         while(sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.contains(":")) {
               String[] parts = line.split(":");
               Proxy proxy = new Proxy(Type.SOCKS, new InetSocketAddress(parts[0], Integer.parseInt(parts[1])));
               PROXIES.add(proxy);
            }
         }

         sc.close();
      }
   }

   public static synchronized Proxy getProxy() {
      if(proxyIterator > PROXIES.size() - 1) {
         proxyIterator = 0;
      }

      return PROXIES.get(proxyIterator++);
   }
}
