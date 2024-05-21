package de.flauschig.eucalyptus.example.handler;

import de.flauschig.eucalyptus.handler.EventHandler
import de.flauschig.eucalyptus.handler.EventListener
import de.flauschig.eucalyptus.example.events.ExampleEvent

class ExampleListener : EventListener {
    @EventHandler
    fun exampleEvent(exampleEvent: ExampleEvent) {
        println(exampleEvent.text)
    }
}