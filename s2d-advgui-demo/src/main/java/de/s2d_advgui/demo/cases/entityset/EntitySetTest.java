package de.s2d_advgui.demo.cases.entityset;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.leo.spheres.core.chunksystem.EntitySet;

import de.s2d_advgui.commons.TimeTracker;

public class EntitySetTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {

        TimeTracker tt = new TimeTracker();
        RandomXS128 rnd = new RandomXS128(19790319);
        EntitySet<DemoColEnt> entities = new EntitySet<>();
        long t1 = System.currentTimeMillis();
        int siz = 100000;
        int ents = 1000000;
        // int ents = 10;
        DemoColEnt[] allEnts = new DemoColEnt[ents];
        for (int i = 0; i < ents; i++) {
            DemoColEnt kaks = new DemoColEnt();
            kaks.direction = new Vector2(rnd.nextFloat()*2f, 0);
            kaks.direction.rotate(rnd.nextInt(360));
            kaks.x = -siz + rnd.nextInt(siz * 2);
            kaks.y = -siz + rnd.nextInt(siz * 2);
            allEnts[i] = kaks;
            entities.add(kaks);
        }
        tt.track("fill");

        for (int jj = 0; jj < 10; jj++) {
            for (DemoColEnt en : allEnts) {
                en.x += en.direction.x;
                en.y += en.direction.y;
            }
            tt.track("shuffle " + jj);
            entities.update();
            tt.track("update " + jj);
        }

        entities.forEach(0, 0, e -> {
            System.err.println("element in chunk[] = " + e);
        });

        tt.track("print");

        tt.print("jaja");
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
