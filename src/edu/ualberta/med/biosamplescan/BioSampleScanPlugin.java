package edu.ualberta.med.biosamplescan;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class BioSampleScanPlugin extends AbstractUIPlugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "biosamplescan2";

	public static final String IMG_FORM_BG = "formBg";

	// The shared instance
	private static BioSampleScanPlugin plugin;

	/**
	 * The constructor
	 */
	public BioSampleScanPlugin() {
		String osname = System.getProperty("os.name");
		if (osname.startsWith("Windows")) {
			System.loadLibrary("scanlib");
		}

	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry registry) {
		registerImage(registry, IMG_FORM_BG, "form_banner.bmp");
	}

	private void registerImage(ImageRegistry registry, String key,
			String fileName) {
		try {
			IPath path = new Path("icons/" + fileName);
			URL url = FileLocator.find(getBundle(), path, null);
			if (url != null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				registry.put(key, desc);
			}
		} catch (Exception e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static BioSampleScanPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Display an information message
	 */
	public static void openMessage(String title, String message) {
		MessageDialog.openInformation(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), title, message);
	}

	/**
	 * Display an error message
	 */
	public static void openError(String title, String message) {
		MessageDialog.openError(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), title, message);
	}

	/**
	 * Display an error message asynchronously
	 */
	public static void openAsyncError(final String title, final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), title, message);
			}
		});
	}

}
