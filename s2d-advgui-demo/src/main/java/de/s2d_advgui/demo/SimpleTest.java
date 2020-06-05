package de.s2d_advgui.demo;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.utils.ShapeUtils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class SimpleTest extends ApplicationAdapter {
    /** the camera **/
    OrthographicCamera camera;
    RayHandler rayHandler;
    World world;
    private PointLight ligth;
    private PointLight ligth2;
    private Body theBody;
    private BodyDef bodyDef;

    @Override
    public void create() {
        this.camera = new OrthographicCamera(48, 48);
        this.camera.update();

        this.world = new World(new Vector2(0, -10), true);
        this.rayHandler = new RayHandler(this.world);
        this.ligth = new PointLight(this.rayHandler, 128);
        this.ligth2 = new PointLight(this.rayHandler, 128);

        {
            Polygon hshs = ShapeUtils.createGear(3, 2, 3);
            // gear.set(hshs.getVertices());
            
            Rectangle rect = new Rectangle(-100, -10, 200, 5);

            PolygonShape gear = new PolygonShape();
            // gear.set(new Vector2[] { new Vector2(0, 0), new Vector2(100, 0), new Vector2(100, 5), new Vector2(0, 5) });
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
            this.bodyDef.position.set(2, 2);
            this.bodyDef.type = BodyType.DynamicBody;

            this.theBody = this.world.createBody(this.bodyDef);
            this.theBody.createFixture(def);
            
            ballShape.dispose();
        }
    }

    @Override
    public void render() {
        this.ligth.setColor(Color.BLUE);
        this.ligth.setSoft(true);
        this.ligth.setPosition(6, 0);
        this.ligth.setDistance(20);
        this.ligth.setStaticLight(false);
        // ligth.setXray(false);

        this.ligth2.setColor(Color.RED);
        this.ligth2.setPosition(-6, 0);
        this.ligth2.setDistance(20);
        this.ligth2.setSoft(false);
        this.ligth2.setStaticLight(true);

//        this.theBody.setTransform(new Vector2(0, 4), 0f);
//        this.theBody.setAwake(true);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.world.step(Gdx.graphics.getDeltaTime(), 8, 3);
        this.rayHandler.setCombinedMatrix(this.camera);
        this.rayHandler.updateAndRender();
    }
}