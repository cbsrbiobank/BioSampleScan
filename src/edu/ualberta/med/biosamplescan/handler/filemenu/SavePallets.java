package edu.ualberta.med.biosamplescan.handler.filemenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;
import edu.ualberta.med.biosamplescan.model.Pallet;
import edu.ualberta.med.biosamplescan.model.PalletSet;

/**
 * Called to save the decoded bar codes for all pallets.
 */
public class SavePallets extends AbstractHandler implements IHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        PalletSet palletSet = BioSampleScanPlugin.getDefault()
            .getPalletSetEditor().getPalletSet();

        if (singlePalletSave())
            return null;

        String saveDir = dirDialog().open();
        List<String> filenames = new ArrayList<String>();

        if (saveDir != null) {
            BioSampleScanPlugin.getDefault().setLastSaveDir(saveDir);
            for (int i = 0, n = BioSampleScanPlugin.getDefault()
                .getPalletsMax(); i < n; ++i) {
                Pallet pallet = palletSet.getPallet(i);
                if (pallet == null)
                    continue;
                filenames.add(new File(palletSet.savePalletToDir(saveDir,
                    pallet)).getName());
            }

            String msg = new String();

            if (filenames.size() == 1) {
                msg = "File " + filenames.get(0) + " saved.";
            } else if (filenames.size() > 1) {
                msg = "Files " + StringUtils.join(filenames, ", ") + " saved.";
            } else {
                Assert.isTrue(false, "no files saved");
            }

            BioSampleScanPlugin.getDefault().updateStatusBar(msg);
        }
        return null;
    }

    public static DirectoryDialog dirDialog() {
        DirectoryDialog dlg = new DirectoryDialog(PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getShell(), SWT.SAVE);
        dlg.setText("Directory to save pallet decode information");
        dlg.setMessage("Select a directory");
        dlg.setFilterPath(BioSampleScanPlugin.getDefault().getLastSaveDir());
        return dlg;
    }

    public static boolean singlePalletSave() {
        PalletSet palletSet = BioSampleScanPlugin.getDefault()
            .getPalletSetEditor().getPalletSet();
        String filename = BioSampleScanPlugin.getDefault().getSaveFileName();

        if ((filename == null) || (filename.length() == 0))
            return false;

        Pallet pallet = palletSet.getPallet(0);
        Assert.isNotNull(pallet, "pallet is null");
        palletSet.savePalletToFile(filename, pallet);

        String msg = "File " + filename + " saved.";
        BioSampleScanPlugin.getDefault().updateStatusBar(msg);
        return true;
    }

}
