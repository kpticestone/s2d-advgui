package de.s2d_advgui.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TMath {
    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("nls")
    final static String[] forms = new String[] { "Byte", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB" };

    // -------------------------------------------------------------------------------------------------------------------------
    final static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    
    // -------------------------------------------------------------------------------------------------------------------------
    final static int radix = 1 << 4;

    // -------------------------------------------------------------------------------------------------------------------------
    public final static double round(double x, int pre) {
        return BigDecimal.valueOf(x).setScale(pre, RoundingMode.HALF_UP).doubleValue();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final static Object[] _makeReadableByteValue(long byteValue) {
        double currentByteValue = byteValue;
        for (String form : forms) {
            if (currentByteValue < 1024) {
                Object[] back = new Object[2];
                back[0] = currentByteValue;
                back[1] = form;
                return back;
            }
            currentByteValue = currentByteValue / 1024d;
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final static String makeReadableByteValue(long byteValue) {
        Object[] res = _makeReadableByteValue(byteValue);
        if (res == null) return "byteValue to big to makeReadable: '" + byteValue + "'"; //$NON-NLS-1$ //$NON-NLS-2$
        return round(((Double) res[0]).doubleValue(), 2) + " " + res[1]; //$NON-NLS-1$
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("nls")
    public final static String makeReadableByteValueRange(long byteValue1, long byteValue2, String del) {
        Object[] res1 = _makeReadableByteValue(byteValue1);
        Object[] res2 = _makeReadableByteValue(byteValue2);
        // if( res == null ) return "byteValue to big to makeReadable: '" + byteValue +
        // "'";
        // return round((Double)res[0], 2) + " " + res[1];
        String back = "";
        back += round(((Double) res1[0]).doubleValue(), 2);
        if (res1[1] != res2[1]) // Ja es sind Strings, doch sind beide aus der selben String-Array-Struktur.
        {
            back += " " + res1[1];
        }
        back += del;
        back += round(((Double) res2[0]).doubleValue(), 2);
        back += " ";
        back += res2[1];

        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final static char[] toHexString(int pI) {
        int i = pI;
        char[] buf = new char[8];
        int charPos = 8;
        int mask = radix - 1;
        do {
            buf[--charPos] = hexDigits[i & mask];
            i >>>= 4;
        } while (i != 0);
        while (charPos-- > 0) {
            buf[charPos] = '0';
        }
        return buf;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
