package com.s2dwt.core.application;

import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.SwtScreen;
import com.s2dwt.core.screens.SwtScreenDescriptor;
import com.s2dwt.core.stage.ASwtStage;
import com.s2dwt.core.window.SwtWindow;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ASwtApplication_150_ScreenStages<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>> extends ASwtApplication_000_Ground<RM, DM, STAGE> {
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
        this.zero = (STAGE) new ASwtStage<RM, DM>(new ISwtApplicationController<>() {
            @Override
            public SwtScreen<RM, DM> activateScreen(String id) {
                return ASwtApplication_150_ScreenStages.this.activateScreen(-1, id);
            }

            @Override
            public DM getDrawerManager() {
                return pDrawerManager;
            }

//          @Override
//          public SwtWindow activateWindow(String id) {
//              return actWin(id);
//          }
        }) {
            @Override
            public void onInit() {
                // DON
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract STAGE _createStage(ISwtApplicationController<RM, DM> pApplicationController);

    // -------------------------------------------------------------------------------------------------------------------------
    public final void registerScreen(SwtScreenDescriptor<STAGE, ? extends SwtScreen<RM, DM>> desc) {
        this.registeredScreens.put(desc.getID(), desc);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtScreen<RM, DM> activateScreen(int stageNr, String id) {
        STAGE someStage = stageNr == -1 ? this.zero : this.stages.get(stageNr);
        SwtScreenDescriptor<STAGE, ? extends SwtScreen<RM, DM>> screenDescriptor = this.registeredScreens.get(id);
        SwtScreen<RM, DM> theScreen = screenDescriptor.createScreen(someStage);
        someStage.onSetCurrentScreen(theScreen);
        return theScreen;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected final STAGE createStage() {
        int stageNr = this.stages.size();
        STAGE someStage = this._createStage(new ISwtApplicationController<>() {
            @Override
            public SwtScreen<RM, DM> activateScreen(String id) {
                return ASwtApplication_150_ScreenStages.this.activateScreen(stageNr, id);
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
        this.stages.add(someStage);
        this.updateSplitScreenArrangements(this.stages.size());
        someStage.onInit();
        someStage.addDisposeListener(() -> {
            this.stages.remove(someStage);
            this.updateSplitScreenArrangements(this.stages.size());
        });
        return someStage;
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
