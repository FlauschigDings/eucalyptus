package de.flauschig.eucalyptus.example;

import de.flauschig.eucalyptus.example.events.ExampleEvent
import de.flauschig.eucalyptus.example.handler.ExampleListener
import de.flauschig.eucalyptus.registry.Registry

class Example {
    fun example() {
        val registry = Registry(
            arrayOf(
                ExampleEvent::class.java
            )
        )

        val listener = ExampleListener()

        registry.subscribe(listener)
        registry.addEvent(ExampleEvent("Rainbowdash"))
        registry.unsubscribe(listener)
    }
}