package de.s2d_advgui.demo.cases.box2dlights;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_Box2DLights extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    static final int RAYS_PER_BALL = 128;
    static final int BALLSNUM = 5;
    static final float LIGHT_DISTANCE = 16f;
    static final float RADIUS = 1f;

    // -------------------------------------------------------------------------------------------------------------------------
    static final float viewportWidth = 48;
    static final float viewportHeight = 32;

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * Type of lights to use: 0 - PointLight 1 - ConeLight 2 - ChainLight 3 -
     * DirectionalLight
     */
    int lightsType = 3;

    // -------------------------------------------------------------------------------------------------------------------------
    private final static int MAX_FPS = 30;
    private final static int MIN_FPS = 15;
    public final static float TIME_STEP = 1f / MAX_FPS;
    private final static float MAX_STEPS = 1f + MAX_FPS / MIN_FPS;
    private final static float MAX_TIME_PER_FRAME = TIME_STEP * MAX_STEPS;
    private final static int VELOCITY_ITERS = 6;
    private final static int POSITION_ITERS = 2;

    // -------------------------------------------------------------------------------------------------------------------------
    float physicsTimeLeft;
    long aika;
    int times;

    // -------------------------------------------------------------------------------------------------------------------------
    World world;
    ArrayList<Body> balls = new ArrayList<>(BALLSNUM);
    Body groundBody;
    RayHandler rayHandler;
    ArrayList<Light> lights = new ArrayList<>(BALLSNUM);
    float sunDirection = -90f;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_Box2DLights(ISwtWidget<? extends Group> pParent,
            SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);

        this.createPhysicsWorld();

        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        this.rayHandler = new RayHandler(this.world);
        this.rayHandler.setAmbientLight(0f, 0f, 0f, .5f);
        this.rayHandler.setBlurNum(3);

        // this.initPointLights();
        // this.initChainLights();
        // this.initDirectionalLight();
        this.initConeLights();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        super._drawIt(dimensions);

        if (lightsType == 3) {
            sunDirection += Gdx.graphics.getDeltaTime() * 4f;
            lights.get(0).setDirection(sunDirection);
        }

        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
        cam.zoom = .1f;
        // cam.rotate(.5f);
        cam.up.set(0, 1, 0);
        cam.direction.set(0, 0, -1);
        cam.update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);
        bb.disableBlending();

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        Rectangle pDims = dimensions;
        DemoResourceManager rm = this.drawerManager.getResourceManager();
        TextureRegion rras = rm.getColorTextureRegion(Color.WHITE);
        TextureRegion jj = rm.getTextureRegion("/icons/128/angel.png");
        TextureRegion ball2d = rm.getTextureRegion("/icons/128/cow.png");

        boolean stepped = fixedStep(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        try (SwtDrawer_Batch<DemoResourceManager> bds = this.drawerManager.startBatchDrawer()) {
            Batch batch = bds.getBatch();
            batch.draw(jj, -viewportWidth / 2f, 0, viewportWidth, viewportHeight);
            batch.enableBlending();
            for (int i = 0; i < BALLSNUM; i++) {
                Body ball = this.balls.get(i);
                Vector2 position = ball.getPosition();
                float angle = MathUtils.radiansToDegrees * ball.getAngle();
                batch.draw(
                        ball2d,
                        position.x - RADIUS, position.y - RADIUS,
                        RADIUS, RADIUS,
                        RADIUS * 2, RADIUS * 2,
                        1f, 1f,
                        angle);
            }
        }
        this.rayHandler.setCombinedMatrix(cam);
        if (stepped) this.rayHandler.update();
        this.rayHandler.render();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void clearLights() {
        if (this.lights.size() > 0) {
            for (Light light : this.lights) {
                light.remove();
            }
            this.lights.clear();
        }
        this.groundBody.setActive(true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void initPointLights() {
        clearLights();
        for (int i = 0; i < BALLSNUM; i++) {
            PointLight light = new PointLight(
                    this.rayHandler,
                    RAYS_PER_BALL,
                    null,
                    LIGHT_DISTANCE,
                    0f,
                    0f
                    );
            light.attachToBody(this.balls.get(i), RADIUS / 2f, RADIUS / 2f);
            light.setColor(
                    MathUtils.random(),
                    MathUtils.random(),
                    MathUtils.random(),
                    1f);
            this.lights.add(light);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void initConeLights() {
        clearLights();
        for (int i = 0; i < BALLSNUM; i++) {
            ConeLight light = new ConeLight(
                    this.rayHandler, RAYS_PER_BALL, null, LIGHT_DISTANCE,
                    0, 0, 0f, 45f);
            light.attachToBody(
                    this.balls.get(i),
                    RADIUS / 2f, RADIUS / 2f, MathUtils.random(0f, 360f));
            light.setColor(
                    1f,
                    0f,
                    1f,
                    1f);
            this.lights.add(light);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void initChainLights() {
        clearLights();
        for (int i = 0; i < BALLSNUM; i++) {
            ChainLight light = new ChainLight(
                    this.rayHandler, RAYS_PER_BALL, null, LIGHT_DISTANCE, 1,
                    new float[] { -5, 0, 0, 3, 5, 0 });
            light.attachToBody(
                    this.balls.get(i),
                    MathUtils.random(0f, 360f));
            light.setColor(
                    MathUtils.random(),
                    MathUtils.random(),
                    MathUtils.random(),
                    1f);
            this.lights.add(light);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void initDirectionalLight() {
        this.clearLights();
        this.groundBody.setActive(false);
        this.sunDirection = MathUtils.random(0f, 360f);
        DirectionalLight light = new DirectionalLight(
                this.rayHandler, 4 * RAYS_PER_BALL, null, this.sunDirection);
        this.lights.add(light);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private boolean fixedStep(float delta) {
        this.physicsTimeLeft += delta;
        if (this.physicsTimeLeft > MAX_TIME_PER_FRAME)
            this.physicsTimeLeft = MAX_TIME_PER_FRAME;

        boolean stepped = false;
        while (this.physicsTimeLeft >= TIME_STEP) {
            this.world.step(TIME_STEP, VELOCITY_ITERS, POSITION_ITERS);
            this.physicsTimeLeft -= TIME_STEP;
            stepped = true;
        }
        return stepped;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void createPhysicsWorld() {
        this.world = new World(new Vector2(0, 0), true);
        float halfWidth = viewportWidth / 2f;
        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new Vector2[] {
                new Vector2(-halfWidth, 0f),
                new Vector2(halfWidth, 0f),
                new Vector2(halfWidth, viewportHeight),
                new Vector2(-halfWidth, viewportHeight) });
        BodyDef chainBodyDef = new BodyDef();
        chainBodyDef.type = BodyType.StaticBody;
        this.groundBody = this.world.createBody(chainBodyDef);
        this.groundBody.createFixture(chainShape, 0);
        chainShape.dispose();
        this.createBoxes();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void createBoxes() {
        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(RADIUS);

        FixtureDef def = new FixtureDef();
        def.restitution = 0.9f;
        def.friction = 0.01f;
        def.shape = ballShape;
        def.density = 1f;
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyType.DynamicBody;

        for (int i = 0; i < BALLSNUM; i++) {
            boxBodyDef.position.x = -20 + (float) (Math.random() * 40);
            boxBodyDef.position.y = 10 + (float) (Math.random() * 15);
            Body boxBody = this.world.createBody(boxBodyDef);
            boxBody.createFixture(def);
            this.balls.add(boxBody);
        }
        ballShape.dispose();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    Vector3 testPoint = new Vector3();

    // -------------------------------------------------------------------------------------------------------------------------
    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.getBody() == SwtCanvas_Box2DLights.this.groundBody)
                return true;

            if (fixture.testPoint(SwtCanvas_Box2DLights.this.testPoint.x, SwtCanvas_Box2DLights.this.testPoint.y)) {
                // hitBody = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

    // -------------------------------------------------------------------------------------------------------------------------
}
