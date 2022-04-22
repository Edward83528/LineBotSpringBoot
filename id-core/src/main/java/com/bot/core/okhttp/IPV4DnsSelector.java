package com.bot.core.okhttp;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Dns;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class IPV4DnsSelector implements Dns {
    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        Log.info("{}", hostname);
        return Dns.SYSTEM.lookup(hostname).stream().filter(a -> Inet4Address.class.isInstance(a)).collect(Collectors.toList());
    }

}