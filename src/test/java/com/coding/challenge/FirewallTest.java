package com.coding.challenge;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Nirupama Sharma
 */
public class FirewallTest {

    static Firewall file = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        String currentDirectory = System.getProperty("user.dir");
        file = new Firewall(currentDirectory + "\\src\\main\\java\\com\\coding\\challenge\\fw.csv");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAcceptPacket() {
        // positive test case
        boolean acceptPacket = file.acceptPacket("inbound", "tcp", 3500, "192.168.10.12");
        assertEquals("The packet should be accepted", true, acceptPacket);
        //negative test case
        boolean rejectPacket = file.acceptPacket("outbound", "tcp", 20100, "192.168.10.11");
        assertEquals("The packet should be rejected", false, rejectPacket);
        //lower edge test case
        boolean lowerEdgeCase = file.acceptPacket("outbound", "udp", 1000, "52.12.48.92");
        assertEquals("The packet should be accepted", true, lowerEdgeCase);
        //upper edge test case
        boolean upperEdgeCase = file.acceptPacket("outbound", "udp", 2000, "52.12.48.92");
        assertEquals("The packet should be accepted", true, upperEdgeCase);
        //single port test case
        boolean singlePortCase = file.acceptPacket("inbound", "udp", 53, "192.168.1.1");
        assertEquals("The packet should be accepted", true, singlePortCase);

    }

}