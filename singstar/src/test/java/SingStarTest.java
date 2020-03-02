import de.s2d_advgui.singstar.ASingStarDescriptorString;
import de.s2d_advgui.singstar.IC_ClazzRegisterObjectContainer;
import de.s2d_advgui.singstar.SingStar;

public class SingStarTest {
    public static void main(String[] args) {
        try {
            IC_ClazzRegisterObjectContainer<ASingStarDescriptorString> onx = SingStar.getInstance()
                    .getObjectContainer(ASingStarDescriptorString.class);
            for (int i = 0; i < 2; i++) {
                for (ASingStarDescriptorString a : onx.values()) {
                    System.err.println("run[" + i + "] " + a.getID() + " -> " + a.hashCode());
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
