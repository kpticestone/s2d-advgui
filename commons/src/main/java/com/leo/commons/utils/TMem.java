package com.leo.commons.utils;

public class TMem {
    // -------------------------------------------------------------------------------------------------------------------------
    private static String makeSizeRead(long wert) {
        return TMath.makeReadableByteValue(wert);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static String getMemoryInformationString() {
//         Runtime.getRuntime().gc();

        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;

        // int sel = (int)(100.0f / (float)maxMemory * (float)usedMemory);

        StringBuilder pr = new StringBuilder();
        pr.append("MEM: (USED: "); //$NON-NLS-1$
        pr.append(makeSizeRead(usedMemory));
        pr.append("; FREE: "); //$NON-NLS-1$
        pr.append(makeSizeRead(freeMemory));
        pr.append("; RESERVED: "); //$NON-NLS-1$
        pr.append(makeSizeRead(totalMemory));
        pr.append("; MAX: "); //$NON-NLS-1$
        pr.append(makeSizeRead(maxMemory));
        pr.append(";)"); //$NON-NLS-1$
        return pr.toString();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
