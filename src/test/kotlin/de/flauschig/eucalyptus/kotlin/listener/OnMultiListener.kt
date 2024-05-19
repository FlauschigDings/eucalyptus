package de.flauschig.eucalyptus.kotlin.listener

import de.flauschig.eucalyptus.handler.EventHandler
import de.flauschig.eucalyptus.handler.EventListener
import de.flauschig.eucalyptus.kotlin.events.ClickEvent
import de.flauschig.eucalyptus.kotlin.events.CustomPayload

class OnMultiListener(
    private val hook: (customPayload: CustomPayload) -> Unit,
    private val hook2: (clickEvent: ClickEvent) -> Unit,
) : EventListener {

    @EventHandler
    fun onCustomPayload(customPayload: CustomPayload) {
        this.hook(customPayload)
    }

    @EventHandler
    fun onClickEvent(clickEvent: ClickEvent) {
        this.hook2(clickEvent)
    }
}