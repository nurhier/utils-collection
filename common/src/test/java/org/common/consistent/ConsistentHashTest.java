package org.common.consistent;

import org.junit.Test;

import java.util.Arrays;

/**
 * consistent hash test
 *
 * @author nurhier
 * @date 2020/4/4
 **/
public class ConsistentHashTest {
    @Test
    public void testConsistentHash() {
        //initialize 4 service node
        Node<Void> node1 = new Node<>(String.format("%s-%s-%s", "IDC1", "127.0.0.1", 8081));
        Node<Void> node2 = new Node<>(String.format("%s-%s-%s", "IDC1", "127.0.0.1", 8082));
        Node<Void> node3 = new Node<>(String.format("%s-%s-%s", "IDC1", "127.0.0.1", 8083));
        Node<Void> node4 = new Node<>(String.format("%s-%s-%s", "IDC1", "127.0.0.1", 8084));

        //hash them to hash ring, 10 virtual node
        int virtualNodeCount = 50;
        ConsistentHash<Void> consistentHashRouter = new ConsistentHash<>(Arrays.asList(node1, node2, node3, node4), virtualNodeCount);

        // we are trying them to route to one service node
        String requestIPString = "192.168.0.";
        int count = 10;
        String[] requestIp = new String[count];
        for (int i = 0; i < 10; i++) {
            requestIp[i] = requestIPString + i;
        }
        goRoute(consistentHashRouter, requestIp);
        //put new service online
        Node<Void> node5 = new Node<>(String.format("%s-%s-%s", "IDC2", "127.0.0.1", 8085));
        System.out.println("-------------putting new node online " + node5.getKey() + "------------");
        consistentHashRouter.addNode(node5, virtualNodeCount);

        goRoute(consistentHashRouter, requestIp);

        consistentHashRouter.removeNode(node3);
        System.out.println("-------------remove node online " + node3.getKey() + "------------");
        goRoute(consistentHashRouter, requestIp);
    }

    private static void goRoute(ConsistentHash<?> consistentHashRouter, String... requestIps) {
        for (String requestIp : requestIps) {
            System.out.println(requestIp + " is route to " + consistentHashRouter.route(requestIp).getKey());
        }
    }
}
