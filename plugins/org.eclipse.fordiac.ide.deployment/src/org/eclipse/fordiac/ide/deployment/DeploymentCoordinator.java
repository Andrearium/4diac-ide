/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH,
 * 							 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked deployment to detect if monitoring was enabled
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.deployment.data.DeviceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

public final class DeploymentCoordinator {

	private static final String OUTPUT_VIEW_ID = "org.eclipse.fordiac.ide.deployment.ui.views.Output"; //$NON-NLS-1$

	public static void printUnsupportedDeviceProfileMessageBox(final Device device, final Resource res) {
		Display.getDefault().asyncExec(() -> {
			final MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					SWT.ICON_ERROR | SWT.OK);
			final String resName = (null != res) ? res.getName() : ""; //$NON-NLS-1$

			if (null != device.getProfile() && !device.getProfile().isEmpty()) {
				messageBox.setMessage(
						MessageFormat.format(Messages.DeploymentCoordinator_MESSAGE_DefinedProfileNotSupported,
								device.getProfile(), device.getName(), resName));
			} else {
				messageBox.setMessage(MessageFormat.format(Messages.DeploymentCoordinator_MESSAGE_ProfileNotSet,
						device.getName(), resName));
			}
			messageBox.open();
		});
	}

	/**
	 * Perform deployment.
	 *
	 * @param selection                 the selection
	 * @param overrideDevMgmCommHandler if not null this device management
	 *                                  communication should be used instead the one
	 *                                  derived from the device profile.
	 */
	public static void performDeployment(final Object[] selection,
			final IDeviceManagementCommunicationHandler overrideDevMgmCommHandler, final String profile) {
		final IDeploymentListener outputView = (IDeploymentListener) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(OUTPUT_VIEW_ID);
		final Shell shell = Display.getDefault().getActiveShell();
		try {
			final DownloadRunnable download = new DownloadRunnable(createDeploymentdata(selection),
					overrideDevMgmCommHandler, outputView, profile);
			new ProgressMonitorDialog(shell).run(true, true, download);
		} catch (final DeploymentException | InvocationTargetException ex) {
			MessageDialog.openError(shell, Messages.DeploymentCoordinator_DepoymentError, ex.getMessage());
		} catch (final InterruptedException ex) {
			Thread.currentThread().interrupt();  // mark interruption
			MessageDialog.openInformation(shell, Messages.DeploymentCoordinator_LABEL_DownloadAborted, ex.getMessage());
		}
	}

	public static void performDeployment(final Object[] selection) {
		performDeployment(selection, null, null);
	}

	/**
	 * Enable output.
	 *
	 * @param executor the executor
	 */
	public static void enableOutput(final IDeviceManagementInteractor interactor) {
		final IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(OUTPUT_VIEW_ID);
		if (null != view) {
			interactor.addDeploymentListener((IDeploymentListener) view);
		}
	}

	/**
	 * Disable output.
	 *
	 * @param executor the executor
	 */
	public static void disableOutput(final IDeviceManagementInteractor interactor) {
		final IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(OUTPUT_VIEW_ID);
		if (null != view) {
			interactor.removeDeploymentListener((IDeploymentListener) view);
		}
	}

	public static List<DeviceDeploymentData> createDeploymentdata(final Object[] selection) throws DeploymentException {
		final List<DeviceDeploymentData> data = new ArrayList<>();
		for (final Object object : selection) {
			if (object instanceof Resource) {
				final Resource res = (Resource) object;
				final DeviceDeploymentData devData = getDevData(data, res.getDevice());
				devData.addResourceData(new ResourceDeploymentData(res));
			} else if (object instanceof Device) {
				final Device dev = (Device) object;
				final DeviceDeploymentData devData = getDevData(data, dev);
				devData.setSeltectedDevParams(dev.getVarDeclarations().stream()
						.filter(devVar -> !DeploymentHelper.MGR_ID.equalsIgnoreCase(devVar.getName()))
						.collect(Collectors.toList()));
			}
		}
		return data;
	}

	private static DeviceDeploymentData getDevData(final List<DeviceDeploymentData> data, final Device device) {
		DeviceDeploymentData retVal = null;
		for (final DeviceDeploymentData devData : data) {
			if (device == devData.getDevice()) {
				retVal = devData;
				break;
			}
		}
		if (null == retVal) {
			retVal = new DeviceDeploymentData(device);
			data.add(retVal);
		}
		return retVal;
	}

	private DeploymentCoordinator() {
		throw new UnsupportedOperationException("Utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
