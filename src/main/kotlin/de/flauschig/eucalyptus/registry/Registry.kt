/*
 * Copyright (C) 2024 FlauschigDings, FooFieOwO, NekosAreKawaii and contributors
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
 *
 */

package de.flauschig.eucalyptus.registry

import de.flauschig.eucalyptus.Event
import de.flauschig.eucalyptus.handler.EventListener

open class Registry(
    val events: Array<Class<out Event>>,
) {
    val listenerHooks: ListenerHooks = ListenerHooks(arrayOf(Event::class.java, *events))

    /**
     * Subscribes one or more event listeners to receive events.
     *
     * @param newListeners the event listeners to be subscribed.
     *
     * This function subscribes the provided event listeners to receive events by invoking the `subscribe` method
     * of the `listenerHooks` object for each listener.
     */
    fun subscribe(vararg newListeners: EventListener) = newListeners.forEach(listenerHooks::subscribe)

    /**
     * Unsubscribes one or more event listeners, stopping them from receiving events.
     *
     * @param removeListeners the event listeners to be unsubscribed.
     *
     * This function unsubscribes the provided event listeners by invoking the `unsubscribe` method
     * of the `listenerHooks` object for each listener.
     */
    fun unsubscribe(vararg removeListeners: EventListener) = removeListeners.forEach(listenerHooks::unsubscribe)

    /**
     * Adds one or more events, triggering event handling for each added event.
     *
     * @param events the events to be added.
     *
     * This function adds the specified events and triggers event handling for each added event
     * by calling the `addEventSingle` method.
     */
    @Throws
    fun addEvent(vararg events: Event) = events.forEach(this::addEventSingle)

    /**
     * Adds a single event and triggers event handling for it.
     *
     * @param event the event to be added.
     *
     * This function adds the specified event and triggers event handling for it by calling the
     * `callEvent` method if event hooks are found for the event.
     */
    @Throws
    private fun addEventSingle(event: Event) {
        this.listenerHooks.hook(event).let { callEvent(event, it) }
        event.eventClasses.forEach { target ->
            this.listenerHooks.hook(Indexer.register(target)).let { callEvent(event, it) }
        }
    }

    /**
     * Calls the event handler methods associated with the specified event.
     *
     * @param event the event for which event handlers should be called.
     * @param events the event hooks associated with the specified event.
     *
     * This function iterates over the event hooks associated with the specified event and calls
     * the event handler methods for each hook.
     */
    @Throws
    private inline fun callEvent(event: Event, events: MutableList<EventHook<*>>) = events.forEach { it.callEvent(event) }
}