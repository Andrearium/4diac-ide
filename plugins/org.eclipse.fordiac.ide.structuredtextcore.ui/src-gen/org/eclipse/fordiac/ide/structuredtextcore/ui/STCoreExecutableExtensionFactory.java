/*
 * generated by Xtext 2.26.0
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.structuredtextcore.ui.internal.StructuredtextcoreActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class STCoreExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(StructuredtextcoreActivator.class);
	}
	
	@Override
	protected Injector getInjector() {
		StructuredtextcoreActivator activator = StructuredtextcoreActivator.getInstance();
		return activator != null ? activator.getInjector(StructuredtextcoreActivator.ORG_ECLIPSE_FORDIAC_IDE_STRUCTUREDTEXTCORE_STCORE) : null;
	}

}
