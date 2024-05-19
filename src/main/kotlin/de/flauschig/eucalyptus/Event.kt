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

package de.flauschig.eucalyptus

import de.flauschig.eucalyptus.registry.Indexer

/**
 * Represents a base class for events in an event-driven system.
 *
 * @param eventClass the classes of events that this event can represent.
 *
 * This class serves as a base for defining different types of events in an event-driven system.
 * Events are typically defined as subclasses of this class, providing specific details about the event.
 * Each event can have one or more associated event classes, allowing for flexible event handling.
 *
 * Usage example:
 * ```
 * class MyListener implements EventListener {
 *     @EventHandler
 *     fun onMyEvent(event: MyEvent) {
 *         // Handle the event
 *     }
 * }
 * ```
 */
abstract class Event(vararg eventClass: Class<out Event>) {
    val id: Int = Indexer.register(this::class.java)

    /**
     * The classes of events that this event can represent.
     */
    val eventClasses: Array<Class<out Event>> = arrayOf(Event::class.java, *eventClass)

    /**
     * Indicates whether the event has been cancelled.
     */
    var cancelled: Boolean = false

    /**
     * Cancels the event.
     *
     * This method sets the `cancelled` flag of the event to true, indicating that the event has been cancelled
     * and should not be further processed by event handlers.
     *
     * @return the reference to the current event instance.
     */
    fun cancel() = this.apply { this.cancelled = true }
}