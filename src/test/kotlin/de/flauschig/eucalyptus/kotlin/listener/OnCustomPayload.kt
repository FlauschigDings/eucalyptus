package de.flauschig.eucalyptus.kotlin.listener

import de.flauschig.eucalyptus.handler.EventHandler
import de.flauschig.eucalyptus.handler.EventListener
import de.flauschig.eucalyptus.kotlin.events.CustomPayload

class OnCustomPayload(
    private val hook: (customPayload: CustomPayload) -> Unit
) : EventListener {

    @EventHandler
    fun onCustomPayload(customPayload: CustomPayload) {
        this.hook(customPayload)
    }
}