
package edu.ualberta.med.biosamplescan.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.Assert;

import edu.ualberta.med.scanlib.ScanCell;

public class Pallet {
    private ScanCell [][] barcodes;
    private String palletBarcode;
    private long timestamp;

    // TODO place all properties into a hash map

    public Pallet() {
        barcodes = null;
    }

    public ScanCell [] getBarcodesRow(int row) {
        Assert.isTrue(((row >= 0) && (row < barcodes.length)), "invalid row: "
            + row);
        return barcodes[row];
    }

    public void setPalleteBarcode(String plateIdText) {
        this.palletBarcode = plateIdText;
    }

    public String getPlateBarcode() {
        return palletBarcode;
    }

    public void setPlateTimestampNOW() {
        timestamp = (new Date()).getTime();
    }

    public long getPlateTimestamp() {
        return timestamp;
    }

    public void clear() {
        for (int r = 0; r < barcodes.length; ++r) {
            for (int c = 0; c < barcodes[0].length; ++c) {
                barcodes[r][c] = null;
            }
        }
    }

    public void loadFromScanlibFile(boolean append) {
        try {
            barcodes = ScanCell.getScanLibResults();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        Assert.isNotNull(barcodes, "barcodes is null");
        String result = new String();
        for (int r = 0; r < barcodes.length; ++r) {
            for (int c = 0; c < barcodes[0].length; ++c) {
                if (barcodes[r][c] == null) continue;

                result.concat(String.format("%s,%s,%d,%s,%s\r\n",
                    palletBarcode, Character.toString((char) ('A' + r)), c,
                    barcodes[r][c], new SimpleDateFormat(
                        "E dd/MM/yyyy HH:mm:ss").format(timestamp)));
            }
        }
        return result;
    }
}
