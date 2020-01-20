package org.common.network;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author nurhier
 * @date 2020/1/20
 */
public class NetWorkUtilsTest {
    @Test
    public void localIpTest() {
        String localIp = NetworkUtils.getLocalHostIp();
        System.out.println(localIp);
        Assert.assertNotNull(localIp);
        String localName = NetworkUtils.getLocalHostname();
        System.out.println(localName);
        Assert.assertNotNull(localName);
    }

    @Test
    public void parseIpTest() {
        String ipString = NetworkUtils.LOCALHOST;
        long ip = NetworkUtils.encodeIp(ipString);
        System.out.println(ip);
        String ipStr = NetworkUtils.decodeIp(ip);
        System.out.println(ipStr);
        Assert.assertEquals(ipString, ipStr);
        Assert.assertEquals(0, NetworkUtils.encodeIp(null));
    }
}
