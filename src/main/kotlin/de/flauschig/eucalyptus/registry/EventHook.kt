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
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Represents a hook that associates an event listener with a method to handle events.
 *
 * @property listener the event listener associated with the hook.
 * @property method the method in the event listener responsible for handling events.
 * @property priority the priority of the event hook. Higher priorities are executed before lower priorities.
 *
 * This class encapsulates an event listener along with the method within it that handles events. When an event
 * occurs, the `callEvent` method can be invoked to trigger the event handling process.
 */
data class EventHook<T : EventListener>(
    val listener: T,
    val method: Method,
    val priority: Int,
) {
    /**
     * Calls the method associated with this hook to handle the specified event.
     *
     * @param event the event to be handled.
     * @throws IllegalAccessException if the method cannot be accessed.
     * @throws InvocationTargetException if the method invocation fails.
     *
     * This method invokes the method associated with this hook, passing the specified event as a parameter.
     * Any exceptions thrown during the method invocation are propagated.
     */
    @Throws(IllegalAccessException::class, InvocationTargetException::class)
    fun callEvent(event: Event) = method.invoke(listener, event)
}