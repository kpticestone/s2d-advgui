package de.s2d_advgui.demo.cases.planettests;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.leo.spheres.core.chunksystem.AChunkSystem;
import com.leo.spheres.core.chunksystem.Chunk;
import com.leo.spheres.core.chunksystem.PlanetChunkSystem;
import com.leo.spheres.core.chunksystem.PlanetGenerator;
import com.leo.spheres.core.chunksystem.PlanetLightMapCalculator;
import com.leo.spheres.core.chunksystem.RayBlockHandler;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.geom.collider.IntersectionFactory;
import de.s2d_advgui.core.geom.collider.IntersectionListener;
import de.s2d_advgui.core.geom.collider.ints.Intersector_RayRect;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_PlanetTests
        extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    float sunDirection = -90f;
    private PlanetChunkSystem gen1;
    public String xtext = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_PlanetTests(ISwtWidget<? extends Group> pParent,
            SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);
        this.gen1 = PlanetGenerator.doGenerate(100);

        this.addDrawerForeground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                //                FrameBuffer frbuff = new FrameBuffer(Format.RGBA8888, (int)pDims.width, (int)pDims.height, false);
                //                frbuff.begin();
                //                for(float x=pDims.x;x<=pDims.width;x++)
                //                {
                //                    for(float y=pDims.y;y<=pDims.height;y++)
                //                    {
                //                        // pBatch.draw(blck, x, y, 1, 1);
                //                        frbuff.getColorBufferTexture().draw(Pixmap.Format., x, y);
                //                    }
                //                }
                //                frbuff.end();

                Vector2 in = new Vector2();
                Vector2 out = new Vector2();
                if (TOldCompatibilityCode.FALSE) {
                    TextureRegion blck = getResourceManager().getColorTextureRegion(new Color(1, 1, 1, 0.5f));
                    for (float x = 0; x <= pDims.width; x += 1) {
                        RayBlockHandler rbh = new RayBlockHandler(gen1.getPlanetSize() * 4, sunDirection);
                        rbh.setSrc(x, 0);
                        AX: for (int[] a : rbh) {
                            in.set(a[0] + pDims.x + 200, a[1] + pDims.y);
                            coord123(in, out);
                            short ja = gen1.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, (int) out.x, (int) out.y);
                            if (ja > 0) {
                                pBatch.setColor(Color.MAGENTA);
                                break AX;
                            } else {
                                pBatch.setColor(Color.GREEN);
                            }
                            pBatch.draw(blck, pDims.x + a[0], pDims.y + pDims.height - a[1], 1, 1);
                        }

                        for (float y = 0; y <= pDims.height; y++) {
                            if (TOldCompatibilityCode.FALSE) {
                                in.set(x + pDims.x + 200, y + pDims.y);
                                coord123(in, out);
                                short ja = gen1.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, (int) out.x, (int) out.y);
                                if (ja > 0) {
                                    pBatch.draw(blck, pDims.x + x, pDims.y + pDims.height - y, 1, 1);
                                }
                            }
                        }
                    }
                }

                if( TOldCompatibilityCode.TRUE )
                {
                float dist = gen1.getPlanetSize();
                float distH = dist / 2f;
                Vector2 p0 = new Vector2(distH, 0);
                Vector2 greencenter = new Vector2(p0);
                greencenter.rotate(sunDirection);
                Vector2 left = new Vector2(p0);
                left.sub(0, distH);
                left.rotate(sunDirection);
                Vector2 left2 = new Vector2(p0);
                left2.sub(dist, distH);
                left2.rotate(sunDirection);
                Vector2 right = new Vector2(p0);
                right.add(0, distH);
                right.rotate(sunDirection);
                Vector2 right2 = new Vector2(p0);
                right2.add(-dist, distH);
                right2.rotate(sunDirection);

                Vector2 onScreen = new Vector2();
                Vector2 canvasCoords = new Vector2();

                TextureRegion blck = getResourceManager().getColorTextureRegion(new Color(1, 1, 1, 0.5f));
                RayBlockHandler rbh = new RayBlockHandler(
                        gen1.getPlanetSize() / drawerManager.getCameraHolder().getCamera().zoom, -sunDirection + 180);

                Vector2 ooa = new Vector2();
                drawerManager.getCameraHolder().screenCoordsToSceneCoords(new Vector2(0, 0), ooa);
                Vector2 oob = new Vector2();
                drawerManager.getCameraHolder().screenCoordsToSceneCoords(new Vector2(pDims.width, pDims.height), oob);
                System.err.println(ooa + " - " + oob);
                // Rectangle rect = new Rectangle(ooa.x, ooa.y, oob.x, oob.y);
                Rectangle rect = new Rectangle(pDims);

                drawerManager.getCameraHolder().sceneCoordsToScreenCoords(left, onScreen);
                screenCoordsToLocalCoords(onScreen, canvasCoords);
                RayBlockHandler rbh0 = new RayBlockHandler(canvasCoords,
                        gen1.getPlanetSize() / drawerManager.getCameraHolder().getCamera().zoom,
                        -sunDirection + 180 + 90);
                //Set<float[]> rbh0 = new HashSet<>();
                // rbh0.add(new float[] {canvasCoords.x, canvasCoords.y});
                IntersectionContext pContext = new IntersectionContext(true, new IntersectionListener() {

                    @Override
                    public void intersects(IntersectionContext pContext, float x, float y) {
                        // TODO Auto-generated method stub

                    }

                });
                pBatch.setColor(Color.WHITE);
                int jj = 0;
                for (int[] someLeftToRightPoint : rbh0) {
//                    if (jj++ % 5 == 0) 
                    {
                        greencenter.set(someLeftToRightPoint[0], someLeftToRightPoint[1]);
                        drawerManager.getCameraHolder().sceneCoordsToScreenCoords(greencenter, onScreen);
                        screenCoordsToLocalCoords(onScreen, canvasCoords);
                        rbh.setSrc(someLeftToRightPoint[0], someLeftToRightPoint[1]);
                        if (Intersector_RayRect.getInstance().calculateOverlapping(pContext, rbh.getRay(), rect)) {
                            int ii = 0;
                            AX: for (int[] somePointOnCanvas : rbh) {
//                                if (ii++ % 5 == 0)
                                {

                                    localCoordsToScreenCoords(new Vector2(somePointOnCanvas[0], somePointOnCanvas[1]),
                                            in);
                                    drawerManager.getCameraHolder().screenCoordsToSceneCoords(in, out);

                                    short ja = gen1.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, (int) out.x,
                                            (int) out.y);
                                    if (ja > 0) {
                                        break AX;
                                    } else {
                                    }
                                    // if( TOldCompatibilityCode.FALSE )
                                    pBatch.draw(blck, pDims.x + somePointOnCanvas[0], pDims.y + pDims.height - somePointOnCanvas[1], 1, 1);
                                }
                            }
                        }
                    }
                }
                }
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
        cameraHolder.setWantedZoom(.1f);
        //        cam.zoom = .05f;
        // cam.rotate(.2f);
        // cam.settranslate(0, 100);
        //        cam.up.set(0, 1, 0);
        cam.position.x = this.gen1.getPlanetSize() / 3.5f;
        cam.position.y = this.gen1.getPlanetSize() / 3.5f;
        // cam.position.set(0, 0, 0);
        // cam.direction.set(1, 1, 0);
        //        cam.update();

        //        OrthographicCamera camera = new OrthographicCamera(48, 48);
        //        camera.update();

        //        System.err.println(this.actor.getWidth() + "x" + this.actor.getHeight());

        this.sunDirection += Gdx.graphics.getDeltaTime() * 6;
        if (this.sunDirection > 90) this.sunDirection = 0;
        //        this.sunDirection = this.sunDirection % 90 + 90;
        this.sunDirection = 46;

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        try (SwtDrawer_Batch<?> sdr = this.drawerManager.startBatchDrawer()) {
            sdr.setColor(Color.WHITE);

//             sdr.draw(this.getResourceManager().getColorTextureRegion(new Color(.5f, .7f, 1f, .8f)), -1000, -1000, 2000, 2000);

            if (TOldCompatibilityCode.TRUE) {
                TextureRegion blck = this.drawerManager.getResourceManager().getColorTextureRegion(Color.WHITE);
                //                TextureRegion icon = this.drawerManager.getResourceManager().getTextureRegion("/icons/128/yinyang.png");
                TextureRegion squ = this.drawerManager.getResourceManager().getTextureRegion("/icons/128/yinyang.png");

                Color[] colors = new Color[32];
                colors[PlanetGenerator.BEDROCK] = Color.DARK_GRAY;
                colors[PlanetGenerator.COAL_ORE] = Color.DARK_GRAY;
                colors[PlanetGenerator.DIRT] = Color.BROWN;
                colors[PlanetGenerator.GOLD_ORE] = Color.YELLOW;
                colors[PlanetGenerator.STONE] = Color.GRAY;
                colors[8] = Color.WHITE;
                colors[9] = Color.BLACK;

                if (TOldCompatibilityCode.TRUE) {
                    this.gen1.forEach(PlanetChunkSystem.CUSTOM_FIELD_VISIBLE, (x, y, i) -> {
                        if (i > 0) {
                            sdr.setColor(colors[i]);
                            // sdr.setColor(Color.WHITE);
                            sdr.draw(squ, x, y, 1, 1);
                        }
                    });
                }
                if (TOldCompatibilityCode.FALSE) {
                    this.gen1.forEach(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, (x, y, i) -> {
                        if (i > 1) {
                            sdr.setColor(new Color(.1f, .9f, 1f, 1f));
                            sdr.draw(blck, new Rectangle(x + .25f, y + .25f, .5f, .5f));
                        }
                    });
                }
                if (TOldCompatibilityCode.TRUE) {
                    this.gen1.forEach(PlanetChunkSystem.CUSTOM_FIELD_LIGHTMAP, (x, y, i) -> {
                        if (i > 0) {
                            if (i == 128) {
                                // sdr.setColor(new Color(0f, 0f, 1f, .5f + (i/255f*2f)));
                                sdr.setColor(new Color(0f, 0f, 1f, .5f));
                            } else if (i == 255) {
                                sdr.setColor(new Color(1f, 1f, 0f, .5f));
                            } else {
                                sdr.setColor(Color.MAGENTA);
                            }
                            float su = .0f;
                            sdr.draw(blck, new Rectangle(x + su, y + su, 1f - su * 2, 1f - su * 2));
                        } else {
                            float su = .0f;
                            sdr.setColor(new Color(0, 0, 0, 0.5f));
                            sdr.draw(blck, new Rectangle(x + su, y + su, 1f - su * 2, 1f - su * 2));
                        }
                    });
                }
            }
        }

        if( TOldCompatibilityCode.TRUE )
        try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
            this.xtext = PlanetLightMapCalculator.calcLightsBySun(this.gen1, this.sunDirection, true, true, sdr);
        }

        if (TOldCompatibilityCode.FALSE) {
            try (SwtDrawer_Batch<?> sdr = this.drawerManager.startBatchDrawer()) {
                sdr.setColor(Color.WHITE);
                int cc = gen1.getChunkCount();
                for (int x = 0; x < cc; x++) {
                    for (int y = 0; y < cc; y++) {
                        Chunk ch = gen1.getNChunk(x, y);
                        int drax = ch.getX() * AChunkSystem.CHUNKSIZE;
                        int dray = ch.getY() * AChunkSystem.CHUNKSIZE;
                        Rectangle re1 = new Rectangle(drax, dray, AChunkSystem.CHUNKSIZE, AChunkSystem.CHUNKSIZE);
                        sdr.drawText(x + "," + y + "\n" + ch.getRev(), re1, Align.center, .5f, false, Color.WHITE);
                    }
                }
            }
        }

        try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
            sdr.setColor(Color.WHITE);
            int cc = gen1.getChunkCount();
            for (int x = 0; x < cc; x++) {
                for (int y = 0; y < cc; y++) {
                    Chunk ch = gen1.getNChunk(x, y);
                    int drax = ch.getX() * AChunkSystem.CHUNKSIZE;
                    int dray = ch.getY() * AChunkSystem.CHUNKSIZE;
                    if (ch.outofatmos) {
                        sdr.setColor(Color.MAGENTA);
                        Rectangle re = new Rectangle(drax, dray, AChunkSystem.CHUNKSIZE - 1,
                                AChunkSystem.CHUNKSIZE - 1);
                        sdr.drawRect(re);
                    } else {
                        sdr.setColor(Color.BLUE);
                        sdr.drawRay(new Ray2D(
                                drax + AChunkSystem.CHUNKSIZE - 1,
                                dray,
                                drax + AChunkSystem.CHUNKSIZE - 1,
                                dray + AChunkSystem.CHUNKSIZE - 2));
                        sdr.drawRay(new Ray2D(
                                drax,
                                dray + AChunkSystem.CHUNKSIZE - 1,
                                drax + AChunkSystem.CHUNKSIZE - 2,
                                dray + AChunkSystem.CHUNKSIZE - 1));
                    }
                }
            }
        }

        if (TOldCompatibilityCode.FALSE)
            try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
                float dist = gen1.getPlanetSize();
                float distH = dist / 2f;

                Vector2 p0 = new Vector2(distH, 0);

                Vector2 greencenter = new Vector2(p0);
                greencenter.rotate(sunDirection);

                Vector2 left = new Vector2(p0);
                left.sub(0, distH);
                left.rotate(sunDirection);
                Vector2 left2 = new Vector2(p0);
                left2.sub(dist, distH);
                left2.rotate(sunDirection);

                Vector2 right = new Vector2(p0);
                right.add(0, distH);
                right.rotate(sunDirection);
                Vector2 right2 = new Vector2(p0);
                right2.add(-dist, distH);
                right2.rotate(sunDirection);

                {
                    sdr.setColor(new Color(0, 128, 0, 255));
                    sdr.draw(new Ray2D(0, 0, greencenter.x, greencenter.y));
                    sdr.draw(new Circle(greencenter.x, greencenter.y, 5));

                    sdr.setColor(Color.RED);
                    sdr.draw(new Ray2D(left2, left));

                    sdr.setColor(Color.BLUE);
                    sdr.draw(new Ray2D(right2, right));
                }
            }

    }

    // -------------------------------------------------------------------------------------------------------------------------
}
