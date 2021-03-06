package edu.ualberta.med.biosamplescan.handler.filemenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import edu.ualberta.med.biosamplescan.BioSampleScanPlugin;

public class SaveBarcodesFromTable5 extends AbstractHandler {

    @Override
    public boolean isEnabled() {
        return BioSampleScanPlugin.getDefault().getPalletEnabled(5);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        SaveBarcodesFromTableX.execute(5);
        return null;
    }

}