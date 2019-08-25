/********************************************************************************
 *  * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *  *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *  *
 *  * Contributors:
 *  *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *  *     - initial API and implementation and/or initial documentation
 *  *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

public class PropertiesItemProvider extends TransientLibraryElementItemProvider{

	public PropertiesItemProvider(AdapterFactory adapterFactory,
			LibraryElement libraryElement) {
		super(adapterFactory, libraryElement);
	}
	
	@Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object){
      if (childrenFeatures == null)
      {
        super.getChildrenFeatures(object);
        childrenFeatures.add(LibraryElementPackage.Literals.LIBRARY_ELEMENT__IDENTIFICATION);
        childrenFeatures.add(LibraryElementPackage.Literals.LIBRARY_ELEMENT__VERSION_INFO);
        if(object instanceof CompilableType){
        	childrenFeatures.add(LibraryElementPackage.Literals.COMPILABLE_TYPE__COMPILER_INFO);
        }
      }
      return childrenFeatures;
    }

    @Override
    public String getText(Object object){
      return "Properties";
    }
    
    @Override
    public Object getImage(Object object) {
      return overlayImage(object, FordiacImage.ICON_PROPERTIES.getImage());      
    }

    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
    {
	  super.collectNewChildDescriptors(newChildDescriptors, object);
	  newChildDescriptors.add
		(createChildParameter
			(LibraryElementPackage.Literals.LIBRARY_ELEMENT__VERSION_INFO,
			 LibraryElementFactory.eINSTANCE.createVersionInfo()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.LIBRARY_ELEMENT__IDENTIFICATION,
				 LibraryElementFactory.eINSTANCE.createIdentification()));
		
		newChildDescriptors.add
		(createChildParameter
			(LibraryElementPackage.Literals.COMPILABLE_TYPE__COMPILER_INFO,
			 LibraryElementFactory.eINSTANCE.createCompilerInfo()));
    }
   
}
