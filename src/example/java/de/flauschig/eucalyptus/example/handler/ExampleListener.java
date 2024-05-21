package de.flauschig.eucalyptus.example.handler;

import de.flauschig.eucalyptus.handler.EventHandler;
import de.flauschig.eucalyptus.handler.EventListener;
import de.flauschig.eucalyptus.example.events.ExampleEvent;

public class ExampleListener implements EventListener {
    @EventHandler
    public void exampleEvent(ExampleEvent exampleEvent) {
        System.out.println(exampleEvent.getText());
    }
}