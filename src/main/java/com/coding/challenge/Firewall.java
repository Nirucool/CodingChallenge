package com.coding.challenge;


import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Nirupama Sharma
 * Firewall class
 */
public class Firewall {
    private static Set<FirewallRule> rulesList = new HashSet<>();

    public Firewall(String fileName) {
        String line;
        FirewallRule fRule;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            while ((line = br.readLine()) != null) {

                String[] networkRules = line.split(",");
                    /**
                     * Case-1 single port-multiple ip addresses
                     */

                if (networkRules[3].contains("-") && !networkRules[2].contains("-")) {
                    String[] ipAddressRange = networkRules[3].split("-");
                    long ipAddressMin = Long.parseLong(ipAddressRange[0].replaceAll("\\.", ""));
                    long ipAddressMax = Long.parseLong(ipAddressRange[1].replaceAll("\\.", ""));
                    long ipRange = ipAddressMax - ipAddressMin;
                    for (int i = 0; i <= ipRange; i++)
                    {
                        fRule = new FirewallRule(networkRules[0], networkRules[1], networkRules[2], ipAddressMin + i);
                        rulesList.add(fRule);
                    }
                }
                /**
                 * Case 2- If there is a range of ports-multiple ports
                 */
                else if (networkRules[2].contains("-")) {

                    String[] portRanges = networkRules[2].split("-");
                    int minPortValue = Integer.parseInt(portRanges[0]);
                    int maxPortValue = Integer.parseInt(portRanges[1]);
                    int rangeOfPort = maxPortValue - minPortValue;
                    /**
                     * multiple ip address
                     */
                    if (networkRules[3].contains("-")) {
                        String[] ipAddressRange = networkRules[3].split("-");
                        long minIpAddress = Long.parseLong(ipAddressRange[0].replaceAll("\\.", ""));
                        long maxIpAddress = Long.parseLong(ipAddressRange[1].replaceAll("\\.", ""));
                        long ipRange = maxIpAddress - minIpAddress;

                        //add all ips and ports to hashset
                        for (int i = 0; i <= rangeOfPort; i++) {
                            for (int j = 0; j <= ipRange; j++) {
                                fRule = new FirewallRule(networkRules[0], networkRules[1], rangeOfPort + i, minIpAddress + j);
                                rulesList.add(fRule);
                            }
                        }
                        //add all ips and ports to hashset
                        for (int i = 0; i <= rangeOfPort; i++) {
                            for (int j = 0; j <= ipRange; j++) {
                                fRule = new FirewallRule(networkRules[0], networkRules[1], minPortValue + i, minIpAddress + j);
                                rulesList.add(fRule);
                            }
                        }
                    } else {
                        for (int i = 0; i <= rangeOfPort; i++) {
                            fRule = new FirewallRule(networkRules[0], networkRules[1], minPortValue + i, networkRules[3]);
                            rulesList.add(fRule);
                        }
                    }
                }
                else {
                    /**
                     * Case 3-single port single ip address
                     */
                    fRule = new FirewallRule(networkRules[0], networkRules[1], networkRules[2], networkRules[3]);
                    rulesList.add(fRule);
                }



            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function accepts or rejects a packet depending on the rules
     *
     * @param direction
     * @param protocol
     * @param port
     * @param ipAddress
     * @return
     */
    public boolean acceptPacket(String direction, String protocol, int port, String ipAddress) {
        FirewallRule rule = new FirewallRule(direction, protocol, port, ipAddress);
        if (rulesList.contains(rule)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * driver method...just for testing...for detailed test cases refer to FirewallTest.java for Junit test cases
     */
    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        Firewall f = new Firewall(currentDirectory + "\\src\\main\\java\\com\\coding\\challenge\\fw.csv");
        boolean test = f.acceptPacket("inbound", "tcp", 80, "192.168.1.2");
        System.out.println(test);

    }
}

