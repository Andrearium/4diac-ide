/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error Marker Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface#getRepairedEndpoint <em>Repaired Endpoint</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getErrorMarkerInterface()
 * @model
 * @generated
 */
public interface ErrorMarkerInterface extends IInterfaceElement, ErrorMarkerRef {
	/**
	 * Returns the value of the '<em><b>Repaired Endpoint</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repaired Endpoint</em>' reference.
	 * @see #setRepairedEndpoint(IInterfaceElement)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getErrorMarkerInterface_RepairedEndpoint()
	 * @model
	 * @generated
	 */
	IInterfaceElement getRepairedEndpoint();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface#getRepairedEndpoint <em>Repaired Endpoint</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repaired Endpoint</em>' reference.
	 * @see #getRepairedEndpoint()
	 * @generated
	 */
	void setRepairedEndpoint(IInterfaceElement value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(Value)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getErrorMarkerInterface_Value()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	Value getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Value value);

} // ErrorMarkerInterface
