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
 *
 */

package de.flauschig.eucalyptus.handler

import de.flauschig.eucalyptus.Event
import de.flauschig.eucalyptus.registry.Indexer
import java.lang.reflect.Method
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.javaType

object EventInvoker {

    /*
    @Deprecated("use a empty map for init")
    fun listenerCaller(
        listeners: MutableSet<EventListener>,
        events: Array<Class<out Event>>
    ): MutableMap<Int, MutableList<EventHook<EventListener>>> =
        listeners.flatMap { listener ->
            this.getEventMethods(listener.javaClass, *events)
                .map { (eventType, method) -> eventType to EventHook(listener, method.first, method.second) }
        }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.toMutableList() }
            .toMutableMap()
     */

    /**
     * Retrieves a mapping of event types to corresponding methods in a listener class that are annotated
     * with `@EventHandler` and accept the event type as a parameter.
     *
     * @param listenerClass the class of the listener containing event handler methods.
     * @param event the event classes to filter the methods for.
     * @return a map where the key is the hash code of the event class and the value is the corresponding method
     *         in the listener class.
     *
     * This function scans the provided listener class for methods that are annotated with `@EventHandler`
     * and have a single parameter matching one of the specified event classes. It then returns a map
     * where each event class's hash code is mapped to its corresponding method.
     *
     * Usage example:
     * ```
     * class MyListener implements EventListener {
     *     @EventHandler
     *     fun onMyEvent(event: MyEvent) {
     *         // Handle the event
     *     }
     * }
     *
     * val eventMethods = getEventMethods(MyListener::class.java, MyEvent::class.java)
     * val myEventMethod = eventMethods[MyEvent::class.java.hashCode()]
     * ```
     *
     * @throws IllegalArgumentException if the listener class or event classes are not valid.
     */
    @OptIn(ExperimentalStdlibApi::class)
    @Throws
    fun getEventMethodsKotlin(listenerClass: KClass<*>, vararg event: Class<out Event>): Map<Int, Pair<KCallable<*>, Int>> {
        val methods = listenerClass.members
        val eventMethods= mutableMapOf<Int, Pair<KCallable<*>, Int>>()

        methods.forEach { method ->
            val methodAnnotation = method.annotations.find { it is EventHandler } as EventHandler?
            if (methodAnnotation != null && method.parameters.size == 2) {
                println(method.parameters[1].type.javaType)
                println(event[0])
                val parameterType = method.parameters[1].type
                event.forEach {
                    if (it == parameterType.javaType) {
                        eventMethods[Indexer.register(it)] = Pair(method, methodAnnotation.priority)
                    }
                }
            }
        }

        return eventMethods
    }

    /**
     * Retrieves a mapping of event types to corresponding methods in a listener class that are annotated
     * with `@EventHandler` and accept the event type as a parameter.
     *
     * @param listenerClass the class of the listener containing event handler methods.
     * @param event the event classes to filter the methods for.
     * @return a map where the key is the hash code of the event class and the value is the corresponding method
     *         in the listener class.
     *
     * This function scans the provided listener class for methods that are annotated with `@EventHandler`
     * and have a single parameter matching one of the specified event classes. It then returns a map
     * where each event class's hash code is mapped to its corresponding method.
     *
     * Usage example:
     * ```
     * class MyListener implements EventListener {
     *     @EventHandler
     *     fun onMyEvent(event: MyEvent) {
     *         // Handle the event
     *     }
     * }
     *
     * val eventMethods = getEventMethods(MyListener::class.java, MyEvent::class.java)
     * val myEventMethod = eventMethods[MyEvent::class.java.hashCode()]
     * ```
     *
     * @throws IllegalArgumentException if the listener class or event classes are not valid.
     */
    @Throws
    fun getEventMethods(listenerClass: KClass<*>, vararg event: Class<out Event>): Map<Int, Pair<Method, Int>> {
        val methods = listenerClass.java.declaredMethods
        val eventMethods= mutableMapOf<Int, Pair<Method, Int>>()

        listenerClass.java.declaredMethods.forEach { method ->
            val methodAnnotation = method.getAnnotation(EventHandler::class.java)
            if (methodAnnotation != null && method.parameterCount == 1) {
                val parameterType = method.parameterTypes[0]
                if (event.any { it == parameterType }) {
                    eventMethods[Indexer.register(parameterType.asSubclass(Event::class.java))] = Pair(method, methodAnnotation.priority)
                }
            }
        }
        return eventMethods
    }
}