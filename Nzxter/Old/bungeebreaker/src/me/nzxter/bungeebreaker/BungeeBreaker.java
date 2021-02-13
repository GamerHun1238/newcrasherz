package me.nzxter.bungeebreaker;

import java.net.Proxy.Type;

import me.nzxter.bungeebreaker.flood.FloodRunner;
import me.nzxter.bungeebreaker.helper.URIHelper;
import me.nzxter.bungeebreaker.option.Options;
import me.nzxter.bungeebreaker.proxy.Proxies;

import java.net.URI;

public class BungeeBreaker {

  private final String version = "1.0 (Public)";

  private final Options options;

  public BungeeBreaker(Options options) {
    this.options = options;
  }

  public void launch() {
    if (!this.options.isOption("host")) {
      System.out.println("No \"host\" option provided!");
      //System.exit(1);
      return;
    }

    final String proxiesType = this.options.getOption("proxiesType", "http");

    final String proxiesFile = this.options.getOption("proxiesFile", "proxies.txt");

    final Proxies proxies = new Proxies();

    try {
      if (proxiesType.equalsIgnoreCase("socks")) {
        proxies.init(proxiesFile, Type.SOCKS);
      } else {
        proxies.init(proxiesFile, Type.HTTP);
      }
    }
    catch (Exception ex) {
      System.out.println("Couldn't init proxies instance!");
      //System.exit(1);
      return;
    }

    //final boolean multi = this.options.getOption("multi", false);

    System.out.println("Enabled BungeeBreaker by NZXTER " + this.version + "\n"
        + "Options: " + this.options.getOptions().toString() + "\n"
        + "Proxies amount: " + proxies.size());


    new FloodRunner(this.options, proxies).run();
  }

}
