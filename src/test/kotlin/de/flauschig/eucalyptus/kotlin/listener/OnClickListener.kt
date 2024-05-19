package de.flauschig.eucalyptus.kotlin.listener

import de.flauschig.eucalyptus.handler.EventHandler
import de.flauschig.eucalyptus.handler.EventListener
import de.flauschig.eucalyptus.kotlin.events.ClickEvent

class OnClickListener(
    private val hook: (clickEvent: ClickEvent) -> Unit,
) : EventListener {

    @EventHandler
    fun onClickEvent(clickEvent: ClickEvent) {
        this.hook(clickEvent)
    }
}