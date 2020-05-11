package de.s2d_advgui.demo.cases.box2dlights;

import java.util.LinkedHashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.leo.spheres.core.chunksystem.CoordConsumerShort;
import com.leo.spheres.core.chunksystem.PlanetChunkSystem;
import com.leo.spheres.core.chunksystem.PlanetGenerator;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.animations.IAnimationListener_Close;
import de.s2d_advgui.animations.impl.Animation_Sleep;
import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.geom.animations.IAnimationListener_ChangeBounds;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.core.utils.AffineHelper;
import de.s2d_advgui.core.utils.ShapeUtils;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_Box2DLights2
        extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
//    private PointLight ligth;
//    private PointLight ligth2;
    private Set<PointLight> lights = new LinkedHashSet<>();

    private BodyDef bodyDef;
    private Body theBody;
    final Rectangle lastDims = new Rectangle();
    // -------------------------------------------------------------------------------------------------------------------------
    // private Vector2 curLight = new Vector2();

    // -------------------------------------------------------------------------------------------------------------------------
    private RayHandler rayHandler;
    private World world;
    private DirectionalLight dl;

    float sunDirection = -90f;

    static class UserDataHolder {
        ICollider collider;
        public int type;

        public UserDataHolder(ICollider blockCol) {
            this.collider = blockCol;
        }

    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_Box2DLights2(ISwtWidget<? extends Group> pParent,
            LightsDrawer ldra, SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);

        this.world = ldra.getWorld();
        this.rayHandler = ldra.getRayHandler();

        for (int i = 0; i < 10; i++) {
            lights.add(new PointLight(this.rayHandler, 1000));
        }

        dl = new DirectionalLight(this.rayHandler, 1024, new Color(1f, 1f, 0f, .8f), 270 - 10);

        PlanetChunkSystem gen1 = PlanetGenerator.doGenerate();

        RandomXS128 xj = new RandomXS128();
        {
            gen1.forEachChunk(chunk -> {
                if (!chunk.outofatmos) {
                    gen1.forEach(chunk, PlanetChunkSystem.CUSTOM_FIELD_SOURCE, new CoordConsumerShort() {
                        @Override
                        public void accept(int x, int y, short i) {
                            Rectangle rect = new Rectangle(0, 0, 1, 1);
                            Collider blockCol = new Collider(rect);

                            PolygonShape gear = new PolygonShape();
                            Polygon poly = ShapeUtils.byRect(rect);
                            gear.set(poly.getTransformedVertices());

                            FixtureDef def = new FixtureDef();
                            def.restitution = 0.0f;
                            def.friction = 0.0f;
                            def.shape = gear;
                            def.density = 1f;

                            BodyDef bodyDef = new BodyDef();
                            bodyDef.position.set(x, y);
                            bodyDef.type = BodyType.StaticBody;

                            Body ground = world.createBody(bodyDef);
                            ground.createFixture(def);
                            UserDataHolder ush = new UserDataHolder(blockCol);
                            ush.type = i;
                            ground.setUserData(ush);

                            gear.dispose();
                        }
                    });
                }
            });
        }
        for (int i = 0; i < 10; i++) {
            CircleShape ballShape = new CircleShape();
            ballShape.setRadius(1);

            FixtureDef def = new FixtureDef();
            def.shape = ballShape;
            def.restitution = .5f;
            def.friction = .5f;
            def.density = 1f;

            this.bodyDef = new BodyDef();
            this.bodyDef.position.set(xj.nextInt(10) - 5, xj.nextInt(10) - 5);
            // this.bodyDef.type = BodyType.StaticBody;
            this.bodyDef.type = BodyType.DynamicBody;

            this.theBody = this.world.createBody(this.bodyDef);
            this.theBody.createFixture(def);

            UserDataHolder ush = new UserDataHolder(new Collider(new Circle(0, 0, 1)));
            ush.type = -1;
            this.theBody.setUserData(ush);

            ballShape.dispose();
        }

        this.resetBalls();
        for (PointLight a : this.lights) {
            this.startAnim(a);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void resetBalls() {
        RandomXS128 xj = new RandomXS128();
        Array<Body> bodies = new Array<>();
        this.world.getBodies(bodies);
        for (Body body : bodies) {
            Object ud = body.getUserData();
            if (ud instanceof UserDataHolder) {
                if (body.getType() == BodyType.DynamicBody) {
                    body.setTransform(new Vector2(xj.nextInt(10) - 5, xj.nextInt(10) + 60), 0f);
                } else {
                    // body.setTransform(new Vector2(xj.nextInt(30) - 15, xj.nextInt(3) - 8), 0f);
                }
                body.setAwake(true);
            }
        }
        Animation_Sleep anim = new Animation_Sleep(0f, 15000f, () -> {
            resetBalls();
        });
        AnimationManager.getInstance().startAnimation(anim);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void startAnim(PointLight theLight) {

        Rectangle src = new Rectangle(theLight.getPosition().x, theLight.getPosition().y, 1, 1);
        int lim = 100;
        float newX = (float) ((Math.random() * lim) - lim / 2f);
        float newY = (float) ((Math.random() * lim) - lim / 2f);
        if (newX < -lim) newX = -lim;
        if (newX > lim) newX = lim;
        if (newY < -lim) newY = -lim;
        if (newY > lim) newY = lim;
        Rectangle dst = new Rectangle(newX, newY + 60, 1, 1);
        Animation_ChangeBounds pAnimation = new Animation_ChangeBounds(0f, 10000f, src, dst,
                new IAnimationListener_ChangeBounds() {
                    @Override
                    public void onChange(Rectangle pRect) {
                        theLight.setPosition(pRect.x, pRect.y);
                    }
                });
        pAnimation.setCloseListener(new IAnimationListener_Close() {
            @Override
            public void onAnimationClosed() throws Exception {
                startAnim(theLight);
            }
        });
        AnimationManager.getInstance().startAnimation(pAnimation);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        super._drawIt(dimensions);

        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
        cameraHolder.setWantedZoom(.05f);
//        cam.zoom = .05f;
//        cam.rotate(.5f);
        // cam.settranslate(0, 100);
//        cam.up.set(0, 1, 0);
//        cam.position.x = 0;
        cam.position.y = 50;
        // cam.direction.set(1, 1, 0);
//        cam.update();

//        OrthographicCamera camera = new OrthographicCamera(48, 48);
//        camera.update();

//        System.err.println(this.actor.getWidth() + "x" + this.actor.getHeight());

        for (PointLight light : this.lights) {
            light.setColor(new Color(0f, 0f, 1f, 1f));
            light.setSoft(false);
            // light.setPosition(this.curLight.x, this.curLight.y);
            light.setDistance(20);
            light.setStaticLight(false);
        }

        this.rayHandler.setAmbientLight(.5f, .5f, 1f, .1f);
        this.rayHandler.setShadows(true);
        this.rayHandler.setBlur(true);
        this.rayHandler.setBlurNum(3);

        dl.setSoft(true);
        dl.setSoftnessLength(10);
//        dl.setStaticLight(true);
        dl.setColor(.5f, .9f, 1f, 0.9f);
        sunDirection += Gdx.graphics.getDeltaTime() * 32;
        dl.setDirection(sunDirection);

//        this.theBody.setTransform(new Vector2(0, 2), 0f);
//        this.theBody.setAwake(true);

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        try (SwtDrawer_Batch<?> sdr = this.drawerManager.startBatchDrawer()) {
            sdr.setColor(Color.WHITE);

            for (PointLight a : this.lights) {
                Collider colbo1 = new Collider(new Circle(a.getPosition().x, a.getPosition().y, .1f));
                // sdr.drawCollider(colbo1);
            }

            TextureRegion blck = this.drawerManager.getResourceManager().getColorTextureRegion(Color.WHITE);
            TextureRegion squ = this.drawerManager.getResourceManager().getTextureRegion("/icons/128/yinyang.png");

            Color[] colors = new Color[32];
            colors[PlanetGenerator.BEDROCK] = Color.DARK_GRAY;
            colors[PlanetGenerator.COAL_ORE] = Color.DARK_GRAY;
            colors[PlanetGenerator.DIRT] = Color.BROWN;
            colors[PlanetGenerator.GOLD_ORE] = Color.YELLOW;
            colors[PlanetGenerator.STONE] = Color.GRAY;

            Array<Body> bodies = new Array<>();
            this.world.getBodies(bodies);
            for (Body body : bodies) {
                Vector2 pos = body.getPosition();
                Object ud = body.getUserData();
                if (ud instanceof UserDataHolder) {
                    UserDataHolder ush = (UserDataHolder) ud;
                    ICollider jj = ush.collider.getTransformed(AffineHelper.getTranslate(pos.x, pos.y));
                    if (ush.type > 0) {
                        sdr.setColor(colors[ush.type]);
                        sdr.draw(blck, pos.x, pos.y, 1, 1);
                    } else {
                        sdr.setColor(Color.WHITE);
                        float angle = body.getAngle();
                        sdr.getBatch().draw(squ,
                                pos.x - 1, pos.y - 1,
                                1f, 1f, // originX, originY,
                                2f, 2f, // width, height,
                                1f, 1f, // scaleX, scaleY,
                                angle / MathUtils.degRad, // rotation,
                                false // clockwise
                        );
                        // sdr.draw(squ, pos.x-1, pos.y-1, 2, 2);
                    }
                }
            }
        }

        if (TOldCompatibilityCode.TRUE) {
            try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
                sdr.setColor(Color.WHITE);
                for (PointLight a : this.lights) {
                    Collider colbo1 = new Collider(new Circle(a.getPosition().x, a.getPosition().y, .1f));
                    sdr.drawCollider(colbo1);
                }
                Array<Body> bodies = new Array<>();
                this.world.getBodies(bodies);
                for (Body body : bodies) {
                    Vector2 pos = body.getPosition();
                    Object ud = body.getUserData();
                    if (ud instanceof UserDataHolder) {
                        ICollider jj = ((UserDataHolder) ud).collider
                                .getTransformed(AffineHelper.getTranslate(pos.x, pos.y));
                        sdr.drawCollider(jj);
                    }
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
