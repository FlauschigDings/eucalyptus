/*
 * Copyright (C) 2024 FlauschigDings, NekosAreKawaii, FooFieOwO and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.flauschig.eucalyptus.kotlin.tests

import de.flauschig.eucalyptus.kotlin.events.ClickEvent
import de.flauschig.eucalyptus.kotlin.events.CustomPayload
import de.flauschig.eucalyptus.kotlin.listener.OnClickListener
import de.flauschig.eucalyptus.kotlin.listener.OnCustomPayload
import de.flauschig.eucalyptus.kotlin.listener.OnMultiListener
import de.flauschig.eucalyptus.registry.EventHook
import de.flauschig.eucalyptus.registry.Registry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KotlinRegistryTest {

    private val registry = Registry(
        arrayOf(
            ClickEvent::class.java,
            CustomPayload::class.java,
        )
    )

    @Test
    fun subscribe() {
        val listener = OnClickListener { }

        registry.subscribe(listener)
        assertEquals(registry.listenerHooks.hookSize(), 3)
    }

    @Test
    fun subscribeMultiple() {
        val clickEvent = ClickEvent(10, 10)
        val customPayload = CustomPayload("xxxx")
        val listener = OnClickListener { }
        val listener2 = OnClickListener { }
        val listener3 = OnClickListener { }
        val listener4 = OnCustomPayload { }

        registry.subscribe(listener)
        registry.subscribe(listener2)
        registry.subscribe(listener3)
        registry.subscribe(listener4)

        assertEquals(registry.listenerHooks.hookSize(), 3)
        assertEquals(registry.listenerHooks.hook(clickEvent).size, 3)
        assertEquals(registry.listenerHooks.hook(customPayload).size, 1)
        assertEquals(registry.listenerHooks.listeners.size, 4)
    }

    @Test
    fun unsubscribe() {
        val listener = OnClickListener { }

        registry.subscribe(listener)
        registry.unsubscribe(listener)
        assertEquals(registry.listenerHooks.listeners.size, 0)
    }

    @Test
    fun unsubscribeMultiple() {
        val clickEvent = ClickEvent(10, 10)
        val customPayload = CustomPayload("xxxx")
        val listener = OnClickListener { }
        val listener2 = OnClickListener { }
        val listener3 = OnClickListener { }
        val listener4 = OnCustomPayload { }
        val listener5 = OnMultiListener({ }, { })

        registry.subscribe(listener)
        registry.subscribe(listener2)
        registry.subscribe(listener3)
        registry.subscribe(listener4)
        registry.subscribe(listener5)

        assertEquals(registry.listenerHooks.hookSize(), 3)
        assertEquals(registry.listenerHooks.hook(clickEvent).size, 4)
        assertEquals(registry.listenerHooks.hook(customPayload).size, 2)
        assertEquals(registry.listenerHooks.listeners.size, 5)

        registry.unsubscribe(listener2)
        assertEquals(registry.listenerHooks.hook(clickEvent).size, 3)
        assertEquals(registry.listenerHooks.listeners.size, 4)
        registry.unsubscribe(listener4)
        registry.unsubscribe(listener5)
        assertEquals(registry.listenerHooks.hook(customPayload), mutableListOf<EventHook<*>>())
        assertEquals(registry.listenerHooks.listeners.size, 2)
    }

    @Test
    fun addEvent() {
        val event = ClickEvent(10, 10)
        val listener = OnClickListener { clickEvent ->
            assertEquals(clickEvent, event)
        }

        registry.subscribe(listener)
        assertEquals(registry.listenerHooks.hookSize(), 3)

        registry.addEvent(event)

        registry.unsubscribe(listener)
        assertEquals(registry.listenerHooks.listeners.size, 0)
    }
}