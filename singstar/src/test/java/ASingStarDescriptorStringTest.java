import javax.annotation.Nonnull;

import de.s2d_advgui.commons.IC_OrderSupport;
import de.s2d_advgui.singstar.ASingStarDescriptorString;

public abstract class ASingStarDescriptorStringTest extends ASingStarDescriptorString implements IC_OrderSupport {
    // -------------------------------------------------------------------------------------------------------------------------
    private final String orderId;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASingStarDescriptorStringTest(@Nonnull String id, @Nonnull String orderId) {
        super(id);
        this.orderId = orderId;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String getOrderId() {
        return this.orderId;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
