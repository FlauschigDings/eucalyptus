package de.flauschig.eucalyptus.kotlin.events

import de.flauschig.eucalyptus.Event

data class ClickEvent(
    val x: Int,
    val y: Int,
) : Event()