package de.s2d_advgui.demo.cases.box2dlightsblend;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
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
import com.leo.spheres.core.chunksystem.PlanetChunkSystem;
import com.leo.spheres.core.chunksystem.PlanetGenerator;
import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.animations.IAnimationListener_Close;
import de.s2d_advgui.animations.impl.Animation_Sleep;
import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.canvas.LightsDrawer;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.geom.animations.IAnimationListener_ChangeBounds;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.core.utils.AffineHelper;
import de.s2d_advgui.core.utils.RectangleMerger;
import de.s2d_advgui.core.utils.ShapeUtils;
import de.s2d_advgui.demo.DemoResourceManager;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class SwtCanvas_Box2DLightsBlend
        extends SwtCanvas<DemoResourceManager, SwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    private World world;
    private RayHandler rayHandler;

    // -------------------------------------------------------------------------------------------------------------------------
    float sunDirection = -90f;
    private DirectionalLight dl;
    private Set<PointLight> lights = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    static class UserDataHolder {
        // -------------------------------------------------------------------------------------------------------------------------
        ICollider collider;

        // -------------------------------------------------------------------------------------------------------------------------
        public int type;

        // -------------------------------------------------------------------------------------------------------------------------
        public UserDataHolder(ICollider blockCol) {
            this.collider = blockCol;
        }

        // -------------------------------------------------------------------------------------------------------------------------
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas_Box2DLightsBlend(ISwtWidget<? extends Group> pParent,
                                  LightsDrawer ldra, SwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pParent, pDrawerManager);

        this.world = ldra.getWorld();
        this.rayHandler = ldra.getRayHandler();

        for (int i = 0; i < 10; i++) {
            lights.add(new PointLight(this.rayHandler, 1000));
        }

        dl = new DirectionalLight(this.rayHandler, 1024, new Color(1f, 1f, 0f, .8f), 270 - 10);

        for (int i = 0; i < 10; i++) {
            CircleShape ballShape = new CircleShape();
            ballShape.setRadius(1);

            FixtureDef def = new FixtureDef();
            def.shape = ballShape;
            def.restitution = 1f;
            def.friction = 0f;
            def.density = 1f;

            BodyDef bodyDef = new BodyDef();
            bodyDef.bullet = true;
            // bodyDef.position.set(xj.nextInt(10) - 5, xj.nextInt(10) - 5);
            // bodyDef.type = BodyType.StaticBody;
            bodyDef.type = BodyType.StaticBody;

            Body theBody = this.world.createBody(bodyDef);
            theBody.createFixture(def);

            UserDataHolder ush = new UserDataHolder(new Collider(new Circle(0, 0, 1)));
            ush.type = -1;
            theBody.setUserData(ush);

            ballShape.dispose();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _drawIt(Rectangle dimensions) {
        super._drawIt(dimensions);

        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        OrthographicCamera cam = cameraHolder.getCamera();
//         cameraHolder.setWantedZoom(.05f);
        cameraHolder.setWantedZoom(.05f);
//        cam.zoom = .05f;
//        cam.rotate(.5f);
        // cam.settranslate(0, 100);
//        cam.up.set(0, 1, 0);
//        cam.position.x = 0;
//        cam.position.y = 0;
        // cam.direction.set(1, 1, 0);
//        cam.update();

//        OrthographicCamera camera = new OrthographicCamera(48, 48);
//        camera.update();

//        System.err.println(this.actor.getWidth() + "x" + this.actor.getHeight());

        for (PointLight light : this.lights) {
            light.setColor(new Color(0f, 0f, 1f, 0f));
            light.setSoft(false);
            // light.setPosition(this.curLight.x, this.curLight.y);
            light.setDistance(50);
            light.setStaticLight(false);
        }
        
        PointLight fi = this.lights.iterator().next();
        fi.setColor(new Color(0,0,1,.75f));
        fi.setPosition(-3, 0);
        fi.setStaticLight(false);
        fi.setDistance(2);
        fi.setSoft(true);
        fi.setSoftnessLength(1);

        this.rayHandler.setAmbientLight(.9f, .8f, 1f, .0f);
        this.rayHandler.setShadows(true);
        this.rayHandler.setBlur(true);
        this.rayHandler.setBlurNum(1);

        sunDirection += Gdx.graphics.getDeltaTime() * 32;

        dl.setSoft(true);
        dl.setSoftnessLength(1);
//        dl.setStaticLight(true);
        dl.setColor(1f, 1f, 1f, .1f);
        dl.setDirection(sunDirection);

//        this.theBody.setTransform(new Vector2(0, 2), 0f);
//        this.theBody.setAwake(true);

        Batch bb = this.drawerManager.getBatch();
        bb.setProjectionMatrix(cam.combined);

        ShapeRenderer sr = this.drawerManager.getShapeRenderer();
        sr.setProjectionMatrix(cam.combined);
        
        TextureRegion squ = this.drawerManager.getResourceManager().getTextureRegion("/icons/128/yinyang.png");
        TextureRegion as = this.drawerManager.getResourceManager().getTextureRegion("/ui/multicolorbackground1.png");
        TextureRegion a2s = this.drawerManager.getResourceManager().getColorTextureRegion(Color.WHITE);
        try (SwtDrawer_Batch<?> sdr = this.drawerManager.startBatchDrawer()) {
            sdr.setColor(Color.WHITE);
            sdr.getBatch().draw(as,
                    -4, -4, //
                    1f, 1f, // originX, originY,
                    8f, 4f, // width, height,
                    1f, 1f, // scaleX, scaleY,
                    0, // MathUtils.degRad, // rotation,
                    false // clockwise
            );
            sdr.getBatch().draw(a2s,
                    -4, 0, //
                    1f, 1f, // originX, originY,
                    8f, 4f, // width, height,
                    1f, 1f, // scaleX, scaleY,
                    0, // MathUtils.degRad, // rotation,
                    false // clockwise
            );
            sdr.getBatch().draw(squ,
                    -1, -1, //
                    1f, 1f, // originX, originY,
                    2f, 2f, // width, height,
                    1f, 1f, // scaleX, scaleY,
                    0, // MathUtils.degRad, // rotation,
                    false // clockwise
            );
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
