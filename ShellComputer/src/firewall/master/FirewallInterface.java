/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package firewall.master;

/**
 *
 * @author nihar
 */
public interface FirewallInterface {
    public boolean accept_packet(String direction, String protocol, int port, String ip_address);
}
