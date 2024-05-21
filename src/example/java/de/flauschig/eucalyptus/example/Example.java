package de.flauschig.eucalyptus.example;

import de.flauschig.eucalyptus.example.events.ExampleEvent;
import de.flauschig.eucalyptus.example.events.CustomPayloadEvent;
import de.flauschig.eucalyptus.events.PacketEvent;
import de.flauschig.eucalyptus.example.handler.ExampleListener;
import de.flauschig.eucalyptus.registry.Registry;

public class Example {
    public static void example() {
        final Registry registry = new Registry(new Class[]{
                ExampleEvent.class,
                PacketEvent.Receive.class,
                PacketEvent.Send.class,
                CustomPayloadEvent.class,
        });

        final ExampleListener listener = new ExampleListener();
        registry.subscribe(listener);

        registry.addEvent(new ExampleEvent("Rainbowdash"));
        registry.unsubscribe(listener);

        final GroupHandler groupHandler = new GroupHandler();
        registry.subscribe(groupHandler);

        final Handler handler = new Handler();
        registry.subscribe(handler);

        registry.addEvent(new PacketEvent.Send("Rainbowdash"));
        registry.addEvent(new PacketEvent.Receive("Applejack"));
        registry.addEvent(new CustomPayloadEvent("Fluttershy"));
    }
}