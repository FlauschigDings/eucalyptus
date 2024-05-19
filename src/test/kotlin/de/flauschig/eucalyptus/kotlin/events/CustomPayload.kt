package de.flauschig.eucalyptus.kotlin.events

import de.flauschig.eucalyptus.Event

data class CustomPayload(val string: String) : Event()