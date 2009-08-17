
package edu.ualberta.med.biosamplescan.editors;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import edu.ualberta.med.biosamplescan.gui.ViewComposite;

public class PlateSetEditor extends EditorPart {

    private ViewComposite viewComposite;

    public static final String ID = "edu.ualberta.med.biosamplescan.editors.plateset";

    @Override
    public void doSave(IProgressMonitor monitor) {
    // TODO Auto-generated method stub

    }

    @Override
    public void doSaveAs() {
    // TODO Auto-generated method stub

    }

    @Override
    public void init(IEditorSite site, IEditorInput input)
        throws PartInitException {
        setSite(site);
        setInput(input);
        this.setPartName(new SimpleDateFormat("E dd/MM/yyyy HH:mm:ss").format(new Date()));

    }

    @Override
    public boolean isDirty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSaveAsAllowed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void createPartControl(Composite parent) {
        // TODO MOVE ABOVE CODE TO TREE VIEWER VIEW WHEN READY
        viewComposite = new ViewComposite(parent, SWT.BORDER);
    }

    public ViewComposite getViewComposite() {
        return viewComposite;
    }

    @Override
    public void setFocus() {
        viewComposite.setFocus();
    }

}
