package de.flauschig.eucalyptus.example.events;

import de.flauschig.eucalyptus.Event;

public class ExampleEvent extends Event {
    private final String text;

    public ExampleEvent(final String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}