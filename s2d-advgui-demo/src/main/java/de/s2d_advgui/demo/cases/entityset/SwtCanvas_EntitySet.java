package de.s2d_advgui.demo.cases.entityset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.leo.spheres.core.chunksystem.EntitySet;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_EntitySet
        extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    EntitySet<DemoColEnt> entities = new EntitySet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_EntitySet(ISwtWidget<? extends Group> pParent,
            SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);
        RandomXS128 rnd = new RandomXS128(19790319);
        int siz = 10000;
        int ents = 1000000;
        // int ents = 10;
        DemoColEnt[] allEnts = new DemoColEnt[ents];
        for (int i = 0; i < ents; i++) {
            DemoColEnt kaks = new DemoColEnt();
            kaks.direction = new Vector2(rnd.nextFloat() * 50f, 0);
            kaks.direction.rotate(rnd.nextInt(360));
            kaks.x = -siz + rnd.nextInt(siz * 2);
            kaks.y = -siz + rnd.nextInt(siz * 2);
            allEnts[i] = kaks;
            entities.add(kaks);
        }
        this.addUpdateHandler(delta -> {
            for (DemoColEnt en : allEnts) {
                en.x += en.direction.x*delta;
                en.y += en.direction.y*delta;
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        super._drawIt(dimensions);

        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
        //         cameraHolder.setWantedZoom(.05f);
        cameraHolder.setWantedZoom(30f);
        //        cam.zoom = .05f;
        //        cam.rotate(.5f);
        cam.update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

         entities.update();

        try {            
            try (SwtDrawer_Batch<DemoResourceManager> bd = this.drawerManager.startBatchDrawer()) {
                TextureRegion tex = this.drawerManager.getResourceManager().getColorTextureRegion(Color.WHITE);
                int sis = 20;
                for (int x = -sis; x <= sis; x++) {
                    for (int y = -sis; y <= sis; y++) {
                        if( x % 2 == 0 )
                        {
                            if( y % 2 == 0 )
                            {
                                bd.setColor(Color.DARK_GRAY);
                            }
                            else
                            {
                                bd.setColor(Color.BLACK);
                            }
                        }
                        else
                        {
                            if( y % 2 == 0 )
                            {
                                bd.setColor(Color.BLACK);
                            }
                            else
                            {
                                bd.setColor(Color.DARK_GRAY);
                            }
                        }
                        bd.draw(tex, new Rectangle(x*entities.chunkSize, y*entities.chunkSize, entities.chunkSize, entities.chunkSize));
                        bd.setColor(Color.GREEN);
                        entities.forEach(x, y, e -> {
                            bd.draw(tex, e.x-5, e.y-5, 10, 10);
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // -------------------------------------------------------------------------------------------------------------------------
}
