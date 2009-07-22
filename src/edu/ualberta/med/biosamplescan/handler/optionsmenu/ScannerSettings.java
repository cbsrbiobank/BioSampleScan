package edu.ualberta.med.biosamplescan.handler.optionsmenu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

import edu.ualberta.med.biosamplescan.View;
import edu.ualberta.med.biosamplescan.gui.ConfigDialog;
import edu.ualberta.med.biosamplescan.gui.ViewComposite;

public class ScannerSettings extends AbstractHandler implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewComposite viewComposite = ((View) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart())
				.getMain();

		ConfigDialog configDialog = new ConfigDialog(viewComposite.getShell(), SWT.NONE);
		configDialog.open();
		return null;
	}

}
