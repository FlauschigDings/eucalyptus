package de.flauschig.eucalyptus.example.events;

import de.flauschig.eucalyptus.Event;

public class CustomPayloadEvent extends Event {
    private final String payload;

    public CustomPayloadEvent(final String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
