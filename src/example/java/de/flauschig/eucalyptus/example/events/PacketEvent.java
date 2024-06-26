package de.flauschig.eucalyptus.example.events;

import de.flauschig.eucalyptus.Event;

public class PacketEvent extends Event {
    private final String name;
    private final PacketState packetState;

    @SafeVarargs
    public PacketEvent(final String name, PacketState packetState, Class<? extends Event>... args) {
        super(args);
        this.name = name;
        this.packetState = packetState;
    }

    public String getName() {
        return name;
    }

    public PacketState getPacketState() {
        return packetState;
    }

    public static class Send extends PacketEvent {
        public Send(final String name) {
            super(name, PacketState.SEND, PacketEvent.class);
        }
    }

    public static class Receive extends PacketEvent {
        public Receive(final String name) {
            super(name, PacketState.RECEIVE, PacketEvent.class);
        }
    }

    public enum PacketState {
        SEND,
        RECEIVE;
    }
}