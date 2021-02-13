package me.nzxter.bungeebreaker;

import me.nzxter.bungeebreaker.option.Options;

public class BungeeBreakerLauncher {

  public static void main(String... args) {
    final BungeeBreaker breaker = new BungeeBreaker(Options.Builder.of(args));
    breaker.launch();
  }

}
