package de.flauschig.eucalyptus.example;

import de.flauschig.eucalyptus.example.events.ExampleEvent;
import de.flauschig.eucalyptus.example.handler.ExampleListener;
import de.flauschig.eucalyptus.registry.Registry;

public class Example {
    public static void example() {
        Registry registry = new Registry(new Class[]{
            ExampleEvent.class
        });

        ExampleListener listener = new ExampleListener();

        registry.subscribe(listener);
        registry.addEvent(new ExampleEvent("Rainbowdash"));
        registry.unsubscribe(listener);
    }
}