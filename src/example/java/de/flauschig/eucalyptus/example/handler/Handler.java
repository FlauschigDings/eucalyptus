package de.flauschig.eucalyptus.example.handler;

import de.flauschig.eucalyptus.example.events.CustomPayloadEvent;
import de.flauschig.eucalyptus.handler.EventHandler;
import de.flauschig.eucalyptus.handler.EventListener;

public class Handler implements EventListener {

    @EventHandler
    public void customPayload(final CustomPayloadEvent payloadEvent) {
        System.out.println("-- Called by customPayload --");
        System.out.println(payloadEvent.getPayload());
        System.out.println("-- Called by customPayload --");
    }
}
