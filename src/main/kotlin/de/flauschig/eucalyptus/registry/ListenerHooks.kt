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
import de.flauschig.eucalyptus.handler.EventInvoker
import de.flauschig.eucalyptus.handler.EventListener
import java.lang.reflect.Method

class ListenerHooks(
    val events: Array<Class<out Event>>,
) {

    val listeners: MutableSet<EventListener> = mutableSetOf()
    val hooks: Array<MutableList<EventHook<*>>> = events.map { mutableListOf<EventHook<*>>() }.toTypedArray()

    fun hookSize() = hooks.size

    /**
     * Retrieves a mapping of event types to corresponding methods in an event listener class.
     *
     * @param listener the event listener whose methods are to be retrieved.
     * @return a map where the key is the hash code of the event class and the value is the corresponding method
     *         in the event listener class.
     *
     * This function internally calls `EventInvoker.getEventMethods` to obtain the mapping of event types to methods
     * in the event listener class. It is intended to be used internally for event handling.
     */
    @Throws
    private fun <T : EventListener> eventsMethod(listener: T): Map<Int, Method> =
        EventInvoker.getEventMethods(listener.javaClass, *events)

    /**
     * Retrieves the list of event hooks associated with a specific event type.
     *
     * @param event the event is the hashcode from the element.
     * @return a mutable list of event hooks associated with the specified event type, or null if no hooks are found.
     *
     * This function returns the list of event hooks associated with the specified event type. If no hooks are found,
     * it returns null. It selects the hook by the id.
     */
    inline fun hook(code: Int): MutableList<EventHook<*>> =
        this.hooks[code]

    /**
     * Retrieves the list of event hooks associated with a specific event type.
     *
     * @param event the event whose associated hooks are to be retrieved.
     * @return a mutable list of event hooks associated with the specified event type, or null if no hooks are found.
     *
     * This function returns the list of event hooks associated with the specified event type. If no hooks are found,
     * it returns null. Event hooks are stored internally in a map where the key is the hash code of the event class.
     */
    fun hook(event: Event): MutableList<EventHook<*>> =
        this.hook(event.id)

    /**
     * Subscribes an event listener to receive events and associates its methods with corresponding event types.
     *
     * @param listener the event listener to be subscribed.
     *
     * This function adds the provided event listener to the internal list of listeners. It then retrieves the
     * methods of the listener associated with event types and creates event hooks, associating them with the
     * appropriate event types.
     */
    fun <T : EventListener> subscribe(listener: T) {
        if (!listeners.add(listener)) {
            return
        }
        val listenerMethods = this.eventsMethod(listener)
        listenerMethods.forEach { (eventType, method) ->
            hooks[eventType].add(EventHook(listener, method))
        }
    }

    /**
     * Unsubscribes an event listener, removing its associated event hooks and cleaning up internal state.
     *
     * @param listener the event listener to be unsubscribed.
     *
     * This function removes the provided event listener from the internal list of listeners. It then removes
     * all event hooks associated with the listener and cleans up any empty entries in the internal hooks map.
     */
    fun <T : EventListener> unsubscribe(listener: T) {
        if (!listeners.remove(listener)) {
            return
        }
        hooks.forEach { list ->
            list.removeIf { it.listener == listener }
        }
        // hooks[.entries.removeIf { it.value.isEmpty() }
    }
}
