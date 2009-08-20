
package edu.ualberta.med.biosamplescan.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.dialogs.DecodeDialog;
import edu.ualberta.med.biosamplescan.model.ConfigSettings;
import edu.ualberta.med.biosamplescan.model.Pallet;
import edu.ualberta.med.biosamplescan.model.PalletScanCoordinates;
import edu.ualberta.med.biosamplescan.model.PalletSet;

public class PalletSetWidget extends ScrolledComposite {

    private Button reScanPlateBtn;
    private Button scanPlateBtn;
    private Button clearPlateBtn;
    private PalletBarcodesWidget palletBarcodesWidget;
    private PalletWidget [] palletWidgets;

    public PalletSetWidget(Composite parent, int style) {
        super(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        setExpandHorizontal(true);
        setExpandVertical(true);
        this.getVerticalBar().setIncrement(10);
        this.getHorizontalBar().setIncrement(10);

        Composite top = new Composite(this, SWT.NONE);
        top.setLayout(new GridLayout(1, false));
        top.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createTopButtonsSection(top);
        palletBarcodesWidget = new PalletBarcodesWidget(top, SWT.NONE);

        palletWidgets = new PalletWidget [ConfigSettings.PALLET_NUM];

        for (int table = 0; table < ConfigSettings.PALLET_NUM; table++) {
            palletWidgets[table] = new PalletWidget(top, SWT.NONE, table);
        }

        setPlateCount();
        top.layout();
        top.pack();
        setContent(top);

        setMinWidth(top.getBounds().width);
        setMinHeight(top.getBounds().height);
    }

    public boolean setFocus() {
        return true;
    }

    private void createTopButtonsSection(Composite parent) {
        Composite section = new Composite(parent, SWT.NONE);
        section.setLayout(new GridLayout(4, false));
        section.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

        clearPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        clearPlateBtn.setText("Clear Selected");
        // clearPlateBtn.setBounds(488, 6, 90, 40);
        clearPlateBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
                clearPalletBtnWidgetSelected(evt);
            }
        });

        reScanPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        reScanPlateBtn.setText("Re-Scan Selected");
        // reScanPlateBtn.setBounds(596, 6, 90, 40);
        reScanPlateBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
                scanPalletBtnWidgetSelected(evt, true);
            }
        });

        scanPlateBtn = new Button(section, SWT.PUSH | SWT.CENTER);
        scanPlateBtn.setText("Scan Selected");
        scanPlateBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent evt) {
                scanPalletBtnWidgetSelected(evt, false);
            }
        });

    }

    private boolean confirmMsg(String title, String msg) {
        return MessageDialog.openConfirm(getShell(), title, msg);
    }

    private void errorMsg(String Identifier, int code) {
        if (code != 0) {
            MessageDialog.openError(getShell(), "Error", String.format(
                "%s\nReturned Error Code: %d\n", Identifier, code));
        }
        else {
            MessageDialog.openError(getShell(), "Error", Identifier);
        }
    }

    public void clearPalletBtnWidgetSelected(SelectionEvent evt) {
        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();
        if (confirmMsg("Clear Pallets",
            "Do you want to clear the selected pallets?")) {
            for (int p = 0; p < ConfigSettings.PALLET_NUM; p++) {
                palletSet.clearTable(p);
                palletBarcodesWidget.clearText();
                updatePalletModel(p);
            }
            ConfigSettings.getInstance().setLastSaveDir("");
        }
    }

    public void scanPalletBtnWidgetSelected(SelectionEvent evt, boolean rescan) {
        List<Integer> selected = new ArrayList<Integer>();

        for (int i = 0; i < ConfigSettings.PALLET_NUM; i++) {
            if (palletBarcodesWidget.isSelected(i)) {
                selected.add(i + 1);
            }
        }

        if (selected.size() == 0) {
            errorMsg("No Pallets Selected", 0);
            return;
        }

        ConfigSettings configSettings = ConfigSettings.getInstance();
        Map<Integer, String> palletsToDecode = new HashMap<Integer, String>();

        for (Integer pallet : selected) {

            PalletScanCoordinates coords = configSettings.getPallet(pallet);

            if (coords == null) {
                errorMsg("Pallete " + pallet + " not configured", 0);
                return;
            }

            if (coords.left + coords.top + coords.right + coords.bottom > 0) {
                palletsToDecode.put(pallet,
                    palletBarcodesWidget.getPalletBarcode(pallet - 1));
            }
        }

        if (palletsToDecode.size() > 0) {
            new DecodeDialog(palletsToDecode, rescan);
        }
    }

    public void updatePalletModel(int palletNum) {
        Assert.isTrue((palletNum >= 0)
            && (palletNum < ConfigSettings.PALLET_NUM),
            "invalid pallet number: " + palletNum);
        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();
        Pallet pallet = palletSet.getPallet(palletNum);
        if (pallet != null) palletWidgets[palletNum].setPalletBarcodes(pallet);
    }

    public void clearPallets() {
        PalletSet palletSet = BioSampleScanPlugin.getDefault().getPalletSet();
        for (int p = 0; p < ConfigSettings.PALLET_NUM; p++) {
            Pallet pallet = palletSet.getPallet(p);
            if (pallet == null) continue;
            pallet.clear();
            updatePalletModel(p);
        }
    }

    public void setPlateCount() {
        int platecount = ConfigSettings.getInstance().getPalletCount();
        boolean set = false;
        for (int table = 0; table < ConfigSettings.PALLET_NUM; table++) {
            set = (table < platecount);
            palletWidgets[table].setEnabled(set);
        }
    }

    public boolean getPalletSelected(int platenum) {
        return palletBarcodesWidget.isSelected(platenum);
    }
}