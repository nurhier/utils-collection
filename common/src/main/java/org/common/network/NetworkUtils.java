package org.common.network;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author nurhier
 * @date 2020/1/14
 */
public class NetworkUtils {

    private NetworkUtils() {}

    public static final String LOCALHOST = "127.0.0.1";

    public static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    public static String getLocalHostname() {
        InetAddress address;
        String hostname;
        try {
            address = InetAddress.getLocalHost();
            hostname = address.getHostName();
            if (StringUtils.isEmpty(hostname)) {
                hostname = address.toString();
            }
        } catch (UnknownHostException noIpAddrException) {
            hostname = LOCALHOST;
        }
        return hostname;
    }

    public static String getLocalHostIp() {
        InetAddress address;
        String hostAddress;
        try {
            address = InetAddress.getLocalHost();
            hostAddress = address.getHostAddress();
            if (StringUtils.isEmpty(hostAddress)) {
                hostAddress = address.toString();
            }
        } catch (UnknownHostException noIpAddrException) {
            hostAddress = LOCALHOST;
        }
        return hostAddress;
    }

    /**
     * string类型的ip转换为number类型
     *
     * @param ipString xxx.xxx.xxx.xxx
     * @return long 3663452325
     */
    public static long encodeIp(String ipString) {
        long ret = 0;
        if (ipString == null) {
            return ret;
        }
        String[] segs = ipString.split("\\.");

        for (int i = 0; i < segs.length; i++) {
            long seg = Long.parseLong(segs[i]);
            ret += (seg << ((3 - i) * 8));
        }

        return ret;
    }

    /**
     * number类型的ip转换为string类型
     *
     * @param ipLong 3663452325
     * @return String xxx.xxx.xxx.xxx
     */
    public static String decodeIp(long ipLong) {
        String dot = ".";
        StringBuilder ip = new StringBuilder();
        ip.append(ipLong >> 24).append(dot);
        ip.append(((ipLong & 16711680) >> 16)).append(dot);
        ip.append(((ipLong & 65280) >> 8)).append(dot);
        ip.append((ipLong & 255));
        return ip.toString();
    }
}
