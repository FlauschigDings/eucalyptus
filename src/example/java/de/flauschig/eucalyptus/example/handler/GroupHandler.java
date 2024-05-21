package de.flauschig.eucalyptus.example.handler;

import de.flauschig.eucalyptus.Event;
import de.flauschig.eucalyptus.example.events.PacketEvent;
import de.flauschig.eucalyptus.handler.EventHandler;
import de.flauschig.eucalyptus.handler.EventListener;

public class GroupHandler implements EventListener {
    @EventHandler
    public void sendPacket(PacketEvent.Send send) {
        System.out.println("-- Called by sendPacket --");
        System.out.println(send.getName());
        System.out.println("-- Called by sendPacket --");
    }

    @EventHandler
    public void receivePacket(PacketEvent.Receive receive) {
        System.out.println("-- Called by receivePacket --");
        System.out.println(receive.getName());
        System.out.println("-- Called by receivePacket --");
    }

    @EventHandler
    public void packet(PacketEvent packetEvent) {
        System.out.println("-- Called by packet --");
        System.out.println(packetEvent.getName());
        System.out.println("-- Called by packet --");
    }

    @EventHandler
    public void event(Event event) {
        System.out.println("-- Called by event --");
        System.out.println(event.getId());
        System.out.println("-- Called by event --");
    }
}