package de.s2d_advgui.demo.cases.box2dlights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Group;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.core.utils.ShapeUtils;
import de.s2d_advgui.demo.DemoResourceManager;

public final class SwtCanvas_Box2DLights extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    private World world;
    private RayHandler rayHandler;
    private PointLight ligth;
    private PointLight ligth2;
    private BodyDef bodyDef;
    private Body theBody;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_Box2DLights(ISwtWidget<? extends Group> pParent,
            SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);

        this.world = new World(new Vector2(0, -10), true);
        this.rayHandler = new RayHandler(this.world);
        this.ligth = new PointLight(this.rayHandler, 32);
        this.ligth2 = new PointLight(this.rayHandler, 32);

        {
            Polygon hshs = ShapeUtils.createGear(3, 2, 3);
            // gear.set(hshs.getVertices());

            Rectangle rect = new Rectangle(-100, -10, 200, 5);

            PolygonShape gear = new PolygonShape();
            // gear.set(new Vector2[] { new Vector2(0, 0), new Vector2(100, 0), new
            // Vector2(100, 5), new Vector2(0, 5) });
            Polygon poly = ShapeUtils.byRect(rect);
            gear.set(poly.getTransformedVertices());

            FixtureDef def = new FixtureDef();
            def.restitution = 0.0f;
            def.friction = 0.0f;
            def.shape = gear;
            def.density = 1f;

            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(0, 0);
            bodyDef.type = BodyType.StaticBody;

            Body ground = this.world.createBody(bodyDef);
            ground.createFixture(def);

            gear.dispose();
        }
        {
            CircleShape ballShape = new CircleShape();
            ballShape.setRadius(1);

            FixtureDef def = new FixtureDef();
            def.restitution = 0.1f;
            def.friction = 0.01f;
            def.shape = ballShape;
            def.density = 1f;

            this.bodyDef = new BodyDef();
            this.bodyDef.position.set(0, 2);
            // this.bodyDef.type = BodyType.StaticBody;
            this.bodyDef.type = BodyType.DynamicBody;

            this.theBody = this.world.createBody(this.bodyDef);
            this.theBody.createFixture(def);

            ballShape.dispose();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        super._drawIt(dimensions);

        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
        cam.zoom = .03f;
        // cam.rotate(.5f);
//        cam.up.set(0, 1, 0);
        // cam.direction.set(1, 1, 0);
        cam.update();

//        OrthographicCamera camera = new OrthographicCamera(48, 48);
//        camera.update();

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        if (TOldCompatibilityCode.FALSE) {
            // bb.disableBlending();

            Rectangle pDims = dimensions;
            DemoResourceManager rm = this.drawerManager.getResourceManager();
            TextureRegion rras = rm.getColorTextureRegion(Color.WHITE);
            TextureRegion jj = rm.getTextureRegion("/splashscreens/s2d-advgui.jpg");
            TextureRegion ball2d = rm.getTextureRegion("/icons/128/cow.png");

            try (SwtDrawer_Batch<DemoResourceManager> bds = this.drawerManager.startBatchDrawer()) {
            }
        }

        try (SwtDrawer_Shapes sdr = this.drawerManager.startShapesDrawer()) {
            sdr.setColor(Color.WHITE);
            Rectangle rect = new Rectangle(-100, -10, 200, 5);
            Collider collider = new Collider(rect);
            sdr.drawCollider(collider);

            Vector2 po = this.theBody.getPosition();
            Collider colbo = new Collider(new Circle(po.x, po.y, 1));
            sdr.drawCollider(colbo);

            Collider colbo1 = new Collider(new Circle(2, -4, .1f));
            sdr.drawCollider(colbo1);

            Collider colbo2 = new Collider(new Circle(-6, 3, .1f));
            sdr.drawCollider(colbo2);
        }

        this.ligth.setColor(Color.BLUE);
        this.ligth.setSoft(true);
        this.ligth.setPosition(2, -4);
        this.ligth.setDistance(20);
        this.ligth.setStaticLight(false);
        // ligth.setXray(false);

        this.ligth2.setColor(Color.RED);
        this.ligth2.setPosition(-6, 3);
        this.ligth2.setDistance(20);
        this.ligth2.setSoft(false);
        this.ligth2.setStaticLight(true);

//        this.theBody.setTransform(new Vector2(0, 2), 0f);
//        this.theBody.setAwake(true);

        if (TOldCompatibilityCode.FALSE) {
//        bb.disableBlending();
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
        this.world.step(Gdx.graphics.getDeltaTime(), 8, 3);
        Rectangle ld = cameraHolder.getLastDims();
//        this.rayHandler.setCulling(false);
        this.rayHandler.setBlurNum(0);
//        this.rayHandler.setCombinedMatrix(cam.combined, 0, 0, dimensions.width, dimensions.height);
        this.rayHandler.setCombinedMatrix(cam.combined, 0, 0, 1, 1);
//        this.rayHandler.useCustomViewport((int) ld.x, (int) ld.y, (int) ld.width, (int) ld.height);
        this.rayHandler.useCustomViewport((int) ld.x, (int) ld.y, (int) ld.width, (int) ld.height);
        this.rayHandler.resizeFBO((int) ld.width, (int) ld.height);
        this.rayHandler.setAmbientLight(Color.BLACK);
        if (TOldCompatibilityCode.FALSE) {
        }
        this.rayHandler.updateAndRender();
        bb.flush();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
