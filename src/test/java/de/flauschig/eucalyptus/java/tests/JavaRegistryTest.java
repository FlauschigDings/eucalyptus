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

package de.flauschig.eucalyptus.java.tests;

import de.flauschig.eucalyptus.kotlin.events.ClickEvent;
import de.flauschig.eucalyptus.kotlin.events.CustomPayload;
import de.flauschig.eucalyptus.kotlin.listener.OnClickListener;
import de.flauschig.eucalyptus.kotlin.listener.OnCustomPayload;
import de.flauschig.eucalyptus.kotlin.listener.OnMultiListener;
import de.flauschig.eucalyptus.registry.Registry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaRegistryTest {

    private final Registry registry = new Registry(new Class[]{
            ClickEvent.class,
            CustomPayload.class,
    });

    @Test
    void subscribe() {
        OnClickListener listener = new OnClickListener(clickEvent -> null);

        registry.subscribe(listener);
        assertEquals(3, registry.getListenerHooks().hookSize());
    }

    @Test
    void subscribeMultiple() {
        ClickEvent clickEvent = new ClickEvent(10, 10);
        CustomPayload customPayload = new CustomPayload("xxxx");
        OnClickListener listener = new OnClickListener(clickEvent1 -> null);
        OnClickListener listener2 = new OnClickListener(clickEvent1 -> null);
        OnClickListener listener3 = new OnClickListener(clickEvent1 -> null);
        OnCustomPayload listener4 = new OnCustomPayload(payload -> null);

        registry.subscribe(listener);
        registry.subscribe(listener2);
        registry.subscribe(listener3);
        registry.subscribe(listener4);

        assertEquals(3, registry.getListenerHooks().hookSize());
        assertEquals(3, registry.getListenerHooks().hook(clickEvent).size());
        assertEquals(1, registry.getListenerHooks().hook(customPayload).size());
        assertEquals(4, registry.getListenerHooks().getListeners().size());
    }

    @Test
    void unsubscribe() {
        OnClickListener listener = new OnClickListener(clickEvent -> null);

        registry.subscribe(listener);
        registry.unsubscribe(listener);
        assertEquals(0, registry.getListenerHooks().getListeners().size());
    }

    @Test
    void unsubscribeMultiple() {
        ClickEvent clickEvent = new ClickEvent(10, 10);
        CustomPayload customPayload = new CustomPayload("xxxx");
        OnClickListener listener = new OnClickListener(clickEvent1 -> null);
        OnClickListener listener2 = new OnClickListener(clickEvent1 -> null);
        OnClickListener listener3 = new OnClickListener(clickEvent1 -> null);
        OnCustomPayload listener4 = new OnCustomPayload(payload -> null);
        OnMultiListener listener5 = new OnMultiListener(clickEvent1 -> null, payload -> null);

        registry.subscribe(listener);
        registry.subscribe(listener2);
        registry.subscribe(listener3);
        registry.subscribe(listener4);
        registry.subscribe(listener5);

        assertEquals(3, registry.getListenerHooks().hookSize());
        assertEquals(4, registry.getListenerHooks().hook(clickEvent).size());
        assertEquals(2, registry.getListenerHooks().hook(customPayload).size());
        assertEquals(5, registry.getListenerHooks().getListeners().size());

        registry.unsubscribe(listener2);
        assertEquals(3, registry.getListenerHooks().hook(clickEvent).size());
        assertEquals(4, registry.getListenerHooks().getListeners().size());
        registry.unsubscribe(listener4);
        registry.unsubscribe(listener5);
        assertEquals(0, registry.getListenerHooks().hook(customPayload).size());
        assertEquals(2, registry.getListenerHooks().getListeners().size());
    }

    @Test
    void addEvent() {
        ClickEvent event = new ClickEvent(10, 10);
        OnClickListener listener = new OnClickListener(clickEvent -> {
            assertEquals(event, clickEvent);
            return null;
        });

        registry.subscribe(listener);
        assertEquals(3, registry.getListenerHooks().hookSize());

        registry.addEvent(event);

        registry.unsubscribe(listener);
        assertEquals(0, registry.getListenerHooks().getListeners().size());
    }
}
