package com.leo.commons.utils;

import java.util.LinkedHashSet;
import java.util.Set;

public class TimeTracker {
    // -------------------------------------------------------------------------------------------------------------------------
    Set<Trck> tracks = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public TimeTracker() {
        this.track("start"); //$NON-NLS-1$
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void track(String pPhrase) {
        this.tracks.add(new Trck(pPhrase));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("nls")
    public void report(StringBuilder txt) {
        this.track("fin");
        long t1 = -1;
        for (Trck t : this.tracks) {
            long dif = t1 == -1 ? 0 : (t.t - t1);
            txt.append(t.s);
            txt.append(": ");
            txt.append("+" + (dif / 1000f / 1000f) + " msec\n");
            t1 = t.t;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("nls")
    public void print(String title) {
        StringBuilder bb = new StringBuilder();
        bb.append("-------------------------------------------\n");
        bb.append(title + "\n");
        this.report(bb);
        bb.append("-------------------------------------------\n");
        System.err.println(bb);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    static class Trck {
        long t = System.nanoTime();
        String s;

        public Trck(String pPhrase) {
            this.s = pPhrase;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
