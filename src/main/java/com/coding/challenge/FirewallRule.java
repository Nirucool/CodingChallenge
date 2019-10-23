package com.coding.challenge;
/**
 @author Nirupama Sharma
 Firewall Rule class with different constructors according to the data types of the inputs
 */
public class FirewallRule {
    private String direction;
    private String protocol;
    private int port;
    private long ipAddress;
    // Contructor overloading for distinguishing between single port and range of ports and long and string ipaddress
    /**
     * String ip address and long port
     * @param direction
     * @param protocol
     * @param port
     * @param ipAddress
     */
    public FirewallRule(String direction, String protocol, String port, long ipAddress)
    {
        this.port = Integer.parseInt(port);
        this.direction = direction;
        this.protocol = protocol;
        this.ipAddress = ipAddress;
    }

    /**
     * int port single and long ipaddress
     * @param direction
     * @param protocol
     * @param port
     * @param ipAddress
     */
    public FirewallRule(String direction, String protocol, int port, long ipAddress) {
        this.direction = direction;
        this.protocol = protocol;
        this.port = port;
        this.ipAddress = ipAddress;
    }
    /**
     * String port-port range and String ipAddress
     *
     * @param direction
     * @param protocol
     * @param port
     * @param ipAddress
     */
    public FirewallRule(String direction, String protocol, String port, String ipAddress) {
        this.ipAddress = Long.parseLong(ipAddress.replaceAll("\\.", ""));
        this.direction = direction;
        this.protocol = protocol;
        this.port = Integer.parseInt(port);

    }

    /**
     * String port-port range and String ipAddress
     * @param direction
     * @param protocol
     * @param port
     * @param ipAddress
     */
    public FirewallRule(String direction, String protocol, int port, String ipAddress) {
        this.ipAddress = Long.parseLong(ipAddress.replaceAll("\\.", ""));
        this.direction = direction;
        this.protocol = protocol;
        this.port = port;

    }
    /**
     * Overridden toString() method
     * @return
     */
    @Override
    public String toString() {
        return this.direction + ", " + this.protocol + ", " + this.port + ", " + this.ipAddress;
    }

    /**
     * hashcode method
     * @return hashcode
     */
    public int hashCode() {
        long hash = 31 * (this.direction.hashCode() + this.protocol.hashCode()+this.ipAddress + this.port);
        return Long.valueOf(hash).hashCode();
    }
    /**
     * Override equals to check that two network rules are same if ip address, port, direction and protocol are same.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FirewallRule))
        {
            return false;
        }
        FirewallRule rule = (FirewallRule) o;
        return direction.equalsIgnoreCase(rule.direction) && protocol.equalsIgnoreCase(rule.protocol)
                && port == rule.port && ipAddress == rule.ipAddress;
    }

}

