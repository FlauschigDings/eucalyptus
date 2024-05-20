/*
 * Copyright (C) 2024 FlauschigDings, NekosAreKawaii, FooFieOwO and contributors
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

package de.flauschig.eucalyptus.benchmark;

import de.flauschig.eucalyptus.Event;
import de.flauschig.eucalyptus.handler.EventHandler;
import de.flauschig.eucalyptus.handler.EventListener;
import de.flauschig.eucalyptus.registry.Registry;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 4, time = 5)
@Measurement(iterations = 4, time = 5)
public class ListenerHooksBenchmark implements EventListener {

    private static final int ITERATIONS = 100_000;

    static final Class<? extends Event>[] events = new Class[]{
            BenchmarkEvent.class,
    };
    static final Registry registry = new Registry(events);


    @Setup(Level.Trial)
    public void setup() {
        registry.subscribe(this);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    public void callEvent(final Blackhole blackhole) {
        for (int i = 0; i < ITERATIONS; i++) {
            registry.addEvent(new BenchmarkEvent(blackhole));
        }
    }

    @EventHandler
    public void listener(final BenchmarkEvent event) {
    }
}

 // 2792270,927
//  552417,145