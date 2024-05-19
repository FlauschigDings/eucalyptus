package de.flauschig.eucalyptus.example;

import de.flauschig.eucalyptus.Event;
import de.flauschig.eucalyptus.example.events.CustomPayloadEvent;
import de.flauschig.eucalyptus.example.events.PacketEvent;
import de.flauschig.eucalyptus.example.handler.GroupHandler;
import de.flauschig.eucalyptus.example.handler.Handler;
import de.flauschig.eucalyptus.registry.Registry;

public class Main {

    static final Class<? extends Event>[] events = new Class[]{
            PacketEvent.class,
            PacketEvent.Receive.class,
            PacketEvent.Send.class,
            CustomPayloadEvent.class,
    };
    static final Registry registry = new Registry(events);

    public static void main(String[] args) {
        final GroupHandler groupHandler = new GroupHandler();
        final Handler handler = new Handler();
        registry.subscribe(groupHandler);
        registry.subscribe(handler);
        registry.addEvent(new PacketEvent.Send("Rainbowdash"));
        registry.addEvent(new PacketEvent.Receive("AppleJack"));
        registry.addEvent(new CustomPayloadEvent("Fluttershy"));
    }
}
