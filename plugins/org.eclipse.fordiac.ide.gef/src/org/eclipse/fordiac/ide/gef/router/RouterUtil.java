/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2014, 2015 Profactor GmbH, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.router;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/**
 * The Class RouterUtil.
 */
public final class RouterUtil {

	/**
	 * Gets the connection router.
	 *
	 * @param container the container
	 *
	 * @return the connection router
	 */
	public static ConnectionRouter getConnectionRouter(final IFigure container) {
		return getConnectionRouterFactory(container).getConnectionRouter(container);
	}

	/**
	 * Gets the connection router factory
	 *
	 * @param container the container
	 *
	 * @return the connection router
	 */
	public static IConnectionRouterFactory getConnectionRouterFactory(final IFigure container) {
		final Map<String, IConnectionRouterFactory> connectionRouter = new HashMap<>();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor(Activator.PLUGIN_ID,
				"ConnectionRouterProvider"); //$NON-NLS-1$
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				final String name = element.getAttribute("name"); //$NON-NLS-1$
				if (object instanceof IConnectionRouterFactory) {
					final IConnectionRouterFactory routerFactory = (IConnectionRouterFactory) object;
					connectionRouter.put(name, routerFactory);
				}
			} catch (final CoreException corex) {
				FordiacLogHelper.logError("Error loading ConnectionRouter", corex); //$NON-NLS-1$
			}
		}
		final String router = Activator.getDefault().getPreferenceStore().getString(DiagramPreferences.CONNECTION_ROUTER);

		IConnectionRouterFactory factory = connectionRouter.get(router);
		if (factory == null) { // the prefered router does not exist - use default
			// one
			factory = new AdjustableConnectionRouterNoJumplinksFactory();
		}
		return factory;
	}

	private RouterUtil() {
		throw new UnsupportedOperationException("RouterUtil utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
