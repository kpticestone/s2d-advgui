package de.s2d_advgui.core.application;

import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.SplitScreenArrangement;
import de.s2d_advgui.core.screens.SwtScreen;
import de.s2d_advgui.core.screens.SwtScreenDescriptor;
import de.s2d_advgui.core.stage.ASwtStage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ASwtApplication_150_ScreenStages<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>>
        extends ASwtApplication_000_Ground<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    protected final Map<String, SwtScreenDescriptor<STAGE, ? extends SwtScreen<RM, DM>>> registeredScreens = new LinkedHashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final List<STAGE> stages = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final STAGE zero;

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public ASwtApplication_150_ScreenStages(DM pDrawerManager) {
        super(pDrawerManager);
        AnimationManager.initWithoutThread();
        this.zero = (STAGE) new ASwtStage<RM, DM>(new ISwtApplicationController<>() {
            @Override
            public void registerLoadReady() {
                ASwtApplication_150_ScreenStages.this.registerLoadReady();
            }

            @Override
            public SwtScreen<RM, DM> activateScreen(String id) throws Exception {
                return ASwtApplication_150_ScreenStages.this.activateScreen(-1, id);
            }

            @Override
            public DM getDrawerManager() {
                return pDrawerManager;
            }

        }) {
            @Override
            public void onInit() {
                // DON
            }
        };
        this.enableStageAnims(this.zero);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract STAGE _createStage(ISwtApplicationController<RM, DM> pApplicationController);

    // -------------------------------------------------------------------------------------------------------------------------
    public final void registerScreen(SwtScreenDescriptor<STAGE, ? extends SwtScreen<RM, DM>> desc) {
        this.registeredScreens.put(desc.getID(), desc);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtScreen<RM, DM> activateScreen(int stageNr, String id) throws Exception {
        STAGE someStage = stageNr == -1 ? this.zero : this.stages.get(stageNr);
        SwtScreenDescriptor<STAGE, ? extends SwtScreen<RM, DM>> screenDescriptor = this.registeredScreens.get(id);
        SwtScreen<RM, DM> theScreen = screenDescriptor.createScreen(someStage);
        someStage.onSetCurrentScreen(theScreen);
        return theScreen;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected final STAGE createStage() throws Exception {
        int stageNr = this.stages.size();
        if (stageNr >= SplitScreenArrangement.MAX - 1) {
            throw new IllegalStateException("Maximum Splitscreens reached");
        }
        STAGE someStage = this._createStage(new ISwtApplicationController<>() {
            @Override
            public SwtScreen<RM, DM> activateScreen(String id) throws Exception {
                return ASwtApplication_150_ScreenStages.this.activateScreen(stageNr, id);
            }

            @Override
            public void registerLoadReady() {
                ASwtApplication_150_ScreenStages.this.registerLoadReady();
            }

            @Override
            public DM getDrawerManager() {
                return ASwtApplication_150_ScreenStages.this.drawerManager;
            }

//          @Override
//          public SwtWindow activateWindow(String id) {
//              return actWin(id);
//          }
        });
        enableStageAnims(someStage);
        this.stages.add(someStage);
        this.updateSplitScreenArrangements(this.stages.size());
        someStage.onInit();
        someStage.addDisposeListener(() -> {
            this.stages.remove(someStage);
            this.updateSplitScreenArrangements(this.stages.size());
        });
        return someStage;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void enableStageAnims(STAGE someStage) {
        someStage.addUpdateHandler(new IMyWidgetUpdateHandler() {
            @Override
            public void act(float delta) {
                try {
//              long t1 = System.currentTimeMillis();
                    AnimationManager.getInstance().doUpdate();
//              System.err.println("anim cost: " + (System.currentTimeMillis()-t1) + "msec");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//  // -------------------------------------------------------------------------------------------------------------------------
//    SwtWindow actWin(String pWindowDescriptorId) {
//       SingStar ss = new SingStar();
//      return null;
//  }

    // -------------------------------------------------------------------------------------------------------------------------
    public final STAGE getStage(int nr) {
        if (this.stages.size() > nr) {
            return this.stages.get(nr);
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected void updateScreenSplit() {
        int cur = 0;
        for (STAGE stage : this.stages) {
            this.splitscreenarrangement.calc(cur++, this.width, this.height, stage::setBounds);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
