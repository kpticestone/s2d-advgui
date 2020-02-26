package de.s2d_advgui.core.application;

import com.badlogic.gdx.ApplicationListener;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;

public abstract class SwtApplicationListener<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>, APP extends SwtApplication<RM, DM, STAGE>> implements ApplicationListener
{
    // -------------------------------------------------------------------------------------------------------------------------
    private APP application;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtApplicationListener()
    {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract APP createApplication() throws Exception;

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void create()
    {
        try
        {
            this.application = this.createApplication();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public APP getApplication()
    {
        return this.application;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void render()
    {
        try
        {
            this.application.render();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void pause()
    {
        this.application.pause();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void resume()
    {
        this.application.resume();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void resize(int width, int height)
    {
        this.application.resize(width, height);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void dispose()
    {
        this.application.dispose();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
