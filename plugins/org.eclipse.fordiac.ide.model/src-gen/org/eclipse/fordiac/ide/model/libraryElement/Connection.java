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
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isResTypeConnection <em>Res Type Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isBrokenConnection <em>Broken Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDestination <em>Destination</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getRoutingData <em>Routing Data</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection()
 * @model abstract="true"
 * @generated
 */
public interface Connection extends INamedElement, ConfigurableObject, ErrorMarkerRef {
	/**
	 * Returns the value of the '<em><b>Res Type Connection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Res Type Connection</em>' attribute.
	 * @see #setResTypeConnection(boolean)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_ResTypeConnection()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isResTypeConnection();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isResTypeConnection <em>Res Type Connection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Res Type Connection</em>' attribute.
	 * @see #isResTypeConnection()
	 * @generated
	 */
	void setResTypeConnection(boolean value);

	/**
	 * Returns the value of the '<em><b>Broken Connection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Broken Connection</em>' attribute.
	 * @see #setBrokenConnection(boolean)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_BrokenConnection()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isBrokenConnection();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isBrokenConnection <em>Broken Connection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Broken Connection</em>' attribute.
	 * @see #isBrokenConnection()
	 * @generated
	 */
	void setBrokenConnection(boolean value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getOutputConnections <em>Output Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(IInterfaceElement)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_Source()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getOutputConnections
	 * @model opposite="outputConnections"
	 * @generated
	 */
	IInterfaceElement getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(IInterfaceElement value);

	/**
	 * Returns the value of the '<em><b>Destination</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getInputConnections <em>Input Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destination</em>' reference.
	 * @see #setDestination(IInterfaceElement)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_Destination()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getInputConnections
	 * @model opposite="inputConnections"
	 * @generated
	 */
	IInterfaceElement getDestination();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDestination <em>Destination</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination</em>' reference.
	 * @see #getDestination()
	 * @generated
	 */
	void setDestination(IInterfaceElement value);

	/**
	 * Returns the value of the '<em><b>Routing Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Routing Data</em>' containment reference.
	 * @see #setRoutingData(ConnectionRoutingData)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnection_RoutingData()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	ConnectionRoutingData getRoutingData();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getRoutingData <em>Routing Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Routing Data</em>' containment reference.
	 * @see #getRoutingData()
	 * @generated
	 */
	void setRoutingData(ConnectionRoutingData value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	FBNetworkElement getSourceElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	FBNetworkElement getDestinationElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	boolean isResourceConnection();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	FBNetwork getFBNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void checkIfConnectionBroken();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dx1DataType="org.eclipse.emf.ecore.xml.type.Int" dx1Required="true" dyDataType="org.eclipse.emf.ecore.xml.type.Int" dyRequired="true" dx2DataType="org.eclipse.emf.ecore.xml.type.Int" dx2Required="true"
	 * @generated
	 */
	void updateRoutingData(int dx1, int dy, int dx2);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visibleDataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	void setVisible(boolean visible);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isVisible();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	boolean isInterfaceConnection();

} // Connection
