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
package org.eclipse.fordiac.ide.model.data.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyCharType;
import org.eclipse.fordiac.ide.model.data.AnyCharsType;
import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.AnyElementaryType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnySignedType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.DerivedType;
import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.ElementaryType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.data.LdateType;
import org.eclipse.fordiac.ide.model.data.LdtType;
import org.eclipse.fordiac.ide.model.data.LintType;
import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.data.LtimeType;
import org.eclipse.fordiac.ide.model.data.LtodType;
import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.data.SintType;
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.Subrange;
import org.eclipse.fordiac.ide.model.data.SubrangeType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.TimeType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.data.ValueType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.data.WstringType;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

import org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DataPackageImpl extends EPackageImpl implements DataPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyDerivedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass directlyDerivedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumeratedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumeratedValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subrangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subrangeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementaryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass derivedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyElementaryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyMagnitudeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyNumTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyRealTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass realTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lrealTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyIntTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyUnsignedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass usintTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uintTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass udintTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ulintTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anySignedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sintTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dintTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lintTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyDurationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ltimeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyBitTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass boolTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass byteTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass wordTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dwordTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lwordTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyCharsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyStringTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass wstringTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyCharTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass charTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass wcharTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyDateTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateAndTimeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ldtTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeOfDayTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ltodTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ldateTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum baseType1EEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DataPackageImpl() {
		super(eNS_URI, DataFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link DataPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DataPackage init() {
		if (isInited) return (DataPackage)EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDataPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DataPackageImpl theDataPackage = registeredDataPackage instanceof DataPackageImpl ? (DataPackageImpl)registeredDataPackage : new DataPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		LibraryElementPackageImpl theLibraryElementPackage = (LibraryElementPackageImpl)(registeredPackage instanceof LibraryElementPackageImpl ? registeredPackage : LibraryElementPackage.eINSTANCE);

		// Create package meta-data objects
		theDataPackage.createPackageContents();
		theLibraryElementPackage.createPackageContents();

		// Initialize created meta-data
		theDataPackage.initializePackageContents();
		theLibraryElementPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDataPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DataPackage.eNS_URI, theDataPackage);
		return theDataPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyDerivedType() {
		return anyDerivedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnyDerivedType_CompilerInfo() {
		return (EReference)anyDerivedTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrayType() {
		return arrayTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayType_Subranges() {
		return (EReference)arrayTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getArrayType_InitialValues() {
		return (EAttribute)arrayTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayType_BaseType() {
		return (EReference)arrayTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDataType() {
		return dataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDirectlyDerivedType() {
		return directlyDerivedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumeratedType() {
		return enumeratedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEnumeratedType_EnumeratedValue() {
		return (EReference)enumeratedTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumeratedValue() {
		return enumeratedValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnumeratedValue_Comment() {
		return (EAttribute)enumeratedValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnumeratedValue_Name() {
		return (EAttribute)enumeratedValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructuredType() {
		return structuredTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuredType_MemberVariables() {
		return (EReference)structuredTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubrange() {
		return subrangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubrange_LowerLimit() {
		return (EAttribute)subrangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubrange_UpperLimit() {
		return (EAttribute)subrangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubrangeType() {
		return subrangeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubrangeType_Subrange() {
		return (EReference)subrangeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValueType() {
		return valueTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getElementaryType() {
		return elementaryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDerivedType() {
		return derivedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDerivedType_BaseType() {
		return (EReference)derivedTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEventType() {
		return eventTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyType() {
		return anyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyElementaryType() {
		return anyElementaryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyMagnitudeType() {
		return anyMagnitudeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyNumType() {
		return anyNumTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyRealType() {
		return anyRealTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRealType() {
		return realTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLrealType() {
		return lrealTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyIntType() {
		return anyIntTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyUnsignedType() {
		return anyUnsignedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUsintType() {
		return usintTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUintType() {
		return uintTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUdintType() {
		return udintTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUlintType() {
		return ulintTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnySignedType() {
		return anySignedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSintType() {
		return sintTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIntType() {
		return intTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDintType() {
		return dintTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLintType() {
		return lintTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyDurationType() {
		return anyDurationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTimeType() {
		return timeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLtimeType() {
		return ltimeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyBitType() {
		return anyBitTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBoolType() {
		return boolTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getByteType() {
		return byteTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWordType() {
		return wordTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDwordType() {
		return dwordTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLwordType() {
		return lwordTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyCharsType() {
		return anyCharsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyStringType() {
		return anyStringTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringType() {
		return stringTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWstringType() {
		return wstringTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyCharType() {
		return anyCharTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCharType() {
		return charTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWcharType() {
		return wcharTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyDateType() {
		return anyDateTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDateAndTimeType() {
		return dateAndTimeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLdtType() {
		return ldtTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDateType() {
		return dateTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTimeOfDayType() {
		return timeOfDayTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLtodType() {
		return ltodTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLdateType() {
		return ldateTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getBaseType1() {
		return baseType1EEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataFactory getDataFactory() {
		return (DataFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		anyDerivedTypeEClass = createEClass(ANY_DERIVED_TYPE);
		createEReference(anyDerivedTypeEClass, ANY_DERIVED_TYPE__COMPILER_INFO);

		arrayTypeEClass = createEClass(ARRAY_TYPE);
		createEReference(arrayTypeEClass, ARRAY_TYPE__SUBRANGES);
		createEAttribute(arrayTypeEClass, ARRAY_TYPE__INITIAL_VALUES);
		createEReference(arrayTypeEClass, ARRAY_TYPE__BASE_TYPE);

		dataTypeEClass = createEClass(DATA_TYPE);

		directlyDerivedTypeEClass = createEClass(DIRECTLY_DERIVED_TYPE);

		enumeratedTypeEClass = createEClass(ENUMERATED_TYPE);
		createEReference(enumeratedTypeEClass, ENUMERATED_TYPE__ENUMERATED_VALUE);

		enumeratedValueEClass = createEClass(ENUMERATED_VALUE);
		createEAttribute(enumeratedValueEClass, ENUMERATED_VALUE__COMMENT);
		createEAttribute(enumeratedValueEClass, ENUMERATED_VALUE__NAME);

		structuredTypeEClass = createEClass(STRUCTURED_TYPE);
		createEReference(structuredTypeEClass, STRUCTURED_TYPE__MEMBER_VARIABLES);

		subrangeEClass = createEClass(SUBRANGE);
		createEAttribute(subrangeEClass, SUBRANGE__LOWER_LIMIT);
		createEAttribute(subrangeEClass, SUBRANGE__UPPER_LIMIT);

		subrangeTypeEClass = createEClass(SUBRANGE_TYPE);
		createEReference(subrangeTypeEClass, SUBRANGE_TYPE__SUBRANGE);

		valueTypeEClass = createEClass(VALUE_TYPE);

		elementaryTypeEClass = createEClass(ELEMENTARY_TYPE);

		derivedTypeEClass = createEClass(DERIVED_TYPE);
		createEReference(derivedTypeEClass, DERIVED_TYPE__BASE_TYPE);

		eventTypeEClass = createEClass(EVENT_TYPE);

		anyTypeEClass = createEClass(ANY_TYPE);

		anyElementaryTypeEClass = createEClass(ANY_ELEMENTARY_TYPE);

		anyMagnitudeTypeEClass = createEClass(ANY_MAGNITUDE_TYPE);

		anyNumTypeEClass = createEClass(ANY_NUM_TYPE);

		anyRealTypeEClass = createEClass(ANY_REAL_TYPE);

		realTypeEClass = createEClass(REAL_TYPE);

		lrealTypeEClass = createEClass(LREAL_TYPE);

		anyIntTypeEClass = createEClass(ANY_INT_TYPE);

		anyUnsignedTypeEClass = createEClass(ANY_UNSIGNED_TYPE);

		usintTypeEClass = createEClass(USINT_TYPE);

		uintTypeEClass = createEClass(UINT_TYPE);

		udintTypeEClass = createEClass(UDINT_TYPE);

		ulintTypeEClass = createEClass(ULINT_TYPE);

		anySignedTypeEClass = createEClass(ANY_SIGNED_TYPE);

		sintTypeEClass = createEClass(SINT_TYPE);

		intTypeEClass = createEClass(INT_TYPE);

		dintTypeEClass = createEClass(DINT_TYPE);

		lintTypeEClass = createEClass(LINT_TYPE);

		anyDurationTypeEClass = createEClass(ANY_DURATION_TYPE);

		timeTypeEClass = createEClass(TIME_TYPE);

		ltimeTypeEClass = createEClass(LTIME_TYPE);

		anyBitTypeEClass = createEClass(ANY_BIT_TYPE);

		boolTypeEClass = createEClass(BOOL_TYPE);

		byteTypeEClass = createEClass(BYTE_TYPE);

		wordTypeEClass = createEClass(WORD_TYPE);

		dwordTypeEClass = createEClass(DWORD_TYPE);

		lwordTypeEClass = createEClass(LWORD_TYPE);

		anyCharsTypeEClass = createEClass(ANY_CHARS_TYPE);

		anyStringTypeEClass = createEClass(ANY_STRING_TYPE);

		stringTypeEClass = createEClass(STRING_TYPE);

		wstringTypeEClass = createEClass(WSTRING_TYPE);

		anyCharTypeEClass = createEClass(ANY_CHAR_TYPE);

		charTypeEClass = createEClass(CHAR_TYPE);

		wcharTypeEClass = createEClass(WCHAR_TYPE);

		anyDateTypeEClass = createEClass(ANY_DATE_TYPE);

		dateAndTimeTypeEClass = createEClass(DATE_AND_TIME_TYPE);

		ldtTypeEClass = createEClass(LDT_TYPE);

		dateTypeEClass = createEClass(DATE_TYPE);

		timeOfDayTypeEClass = createEClass(TIME_OF_DAY_TYPE);

		ltodTypeEClass = createEClass(LTOD_TYPE);

		ldateTypeEClass = createEClass(LDATE_TYPE);

		// Create enums
		baseType1EEnum = createEEnum(BASE_TYPE1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage)EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		anyDerivedTypeEClass.getESuperTypes().add(this.getAnyType());
		arrayTypeEClass.getESuperTypes().add(this.getAnyDerivedType());
		dataTypeEClass.getESuperTypes().add(theLibraryElementPackage.getLibraryElement());
		directlyDerivedTypeEClass.getESuperTypes().add(this.getDerivedType());
		enumeratedTypeEClass.getESuperTypes().add(this.getValueType());
		structuredTypeEClass.getESuperTypes().add(this.getAnyDerivedType());
		subrangeTypeEClass.getESuperTypes().add(this.getDerivedType());
		valueTypeEClass.getESuperTypes().add(this.getDataType());
		elementaryTypeEClass.getESuperTypes().add(this.getValueType());
		derivedTypeEClass.getESuperTypes().add(this.getValueType());
		eventTypeEClass.getESuperTypes().add(this.getDataType());
		anyTypeEClass.getESuperTypes().add(this.getDataType());
		anyElementaryTypeEClass.getESuperTypes().add(this.getAnyType());
		anyMagnitudeTypeEClass.getESuperTypes().add(this.getAnyElementaryType());
		anyNumTypeEClass.getESuperTypes().add(this.getAnyMagnitudeType());
		anyRealTypeEClass.getESuperTypes().add(this.getAnyNumType());
		realTypeEClass.getESuperTypes().add(this.getAnyRealType());
		lrealTypeEClass.getESuperTypes().add(this.getAnyRealType());
		anyIntTypeEClass.getESuperTypes().add(this.getAnyNumType());
		anyUnsignedTypeEClass.getESuperTypes().add(this.getAnyIntType());
		usintTypeEClass.getESuperTypes().add(this.getAnyUnsignedType());
		uintTypeEClass.getESuperTypes().add(this.getAnyUnsignedType());
		udintTypeEClass.getESuperTypes().add(this.getAnyUnsignedType());
		ulintTypeEClass.getESuperTypes().add(this.getAnyUnsignedType());
		anySignedTypeEClass.getESuperTypes().add(this.getAnyIntType());
		sintTypeEClass.getESuperTypes().add(this.getAnySignedType());
		intTypeEClass.getESuperTypes().add(this.getAnySignedType());
		dintTypeEClass.getESuperTypes().add(this.getAnySignedType());
		lintTypeEClass.getESuperTypes().add(this.getAnySignedType());
		anyDurationTypeEClass.getESuperTypes().add(this.getAnyMagnitudeType());
		timeTypeEClass.getESuperTypes().add(this.getAnyDurationType());
		ltimeTypeEClass.getESuperTypes().add(this.getAnyDurationType());
		anyBitTypeEClass.getESuperTypes().add(this.getAnyElementaryType());
		boolTypeEClass.getESuperTypes().add(this.getAnyBitType());
		byteTypeEClass.getESuperTypes().add(this.getAnyBitType());
		wordTypeEClass.getESuperTypes().add(this.getAnyBitType());
		dwordTypeEClass.getESuperTypes().add(this.getAnyBitType());
		lwordTypeEClass.getESuperTypes().add(this.getAnyBitType());
		anyCharsTypeEClass.getESuperTypes().add(this.getAnyElementaryType());
		anyStringTypeEClass.getESuperTypes().add(this.getAnyCharsType());
		stringTypeEClass.getESuperTypes().add(this.getAnyStringType());
		wstringTypeEClass.getESuperTypes().add(this.getAnyStringType());
		anyCharTypeEClass.getESuperTypes().add(this.getAnyCharsType());
		charTypeEClass.getESuperTypes().add(this.getAnyCharType());
		wcharTypeEClass.getESuperTypes().add(this.getAnyCharType());
		anyDateTypeEClass.getESuperTypes().add(this.getAnyElementaryType());
		dateAndTimeTypeEClass.getESuperTypes().add(this.getAnyDateType());
		ldtTypeEClass.getESuperTypes().add(this.getAnyDateType());
		dateTypeEClass.getESuperTypes().add(this.getAnyDateType());
		timeOfDayTypeEClass.getESuperTypes().add(this.getAnyDateType());
		ltodTypeEClass.getESuperTypes().add(this.getAnyDateType());
		ldateTypeEClass.getESuperTypes().add(this.getAnyDateType());

		// Initialize classes and features; add operations and parameters
		initEClass(anyDerivedTypeEClass, AnyDerivedType.class, "AnyDerivedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAnyDerivedType_CompilerInfo(), theLibraryElementPackage.getCompilerInfo(), null, "compilerInfo", null, 0, 1, AnyDerivedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(arrayTypeEClass, ArrayType.class, "ArrayType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getArrayType_Subranges(), this.getSubrange(), null, "subranges", null, 1, -1, ArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getArrayType_InitialValues(), theXMLTypePackage.getString(), "initialValues", null, 0, 1, ArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getArrayType_BaseType(), this.getDataType(), null, "baseType", null, 0, 1, ArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(dataTypeEClass, DataType.class, "DataType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		EOperation op = addEOperation(dataTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(directlyDerivedTypeEClass, DirectlyDerivedType.class, "DirectlyDerivedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(enumeratedTypeEClass, EnumeratedType.class, "EnumeratedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEnumeratedType_EnumeratedValue(), this.getEnumeratedValue(), null, "enumeratedValue", null, 1, -1, EnumeratedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(enumeratedValueEClass, EnumeratedValue.class, "EnumeratedValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getEnumeratedValue_Comment(), theXMLTypePackage.getString(), "comment", null, 0, 1, EnumeratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getEnumeratedValue_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, EnumeratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(structuredTypeEClass, StructuredType.class, "StructuredType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStructuredType_MemberVariables(), theLibraryElementPackage.getVarDeclaration(), null, "memberVariables", null, 0, -1, StructuredType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(structuredTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(subrangeEClass, Subrange.class, "Subrange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSubrange_LowerLimit(), theXMLTypePackage.getInt(), "lowerLimit", null, 1, 1, Subrange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSubrange_UpperLimit(), theXMLTypePackage.getInt(), "upperLimit", null, 1, 1, Subrange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(subrangeTypeEClass, SubrangeType.class, "SubrangeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSubrangeType_Subrange(), this.getSubrange(), null, "subrange", null, 1, 1, SubrangeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(valueTypeEClass, ValueType.class, "ValueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(elementaryTypeEClass, ElementaryType.class, "ElementaryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(derivedTypeEClass, DerivedType.class, "DerivedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDerivedType_BaseType(), this.getElementaryType(), null, "baseType", null, 1, 1, DerivedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eventTypeEClass, EventType.class, "EventType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(eventTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyTypeEClass, AnyType.class, "AnyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyElementaryTypeEClass, AnyElementaryType.class, "AnyElementaryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyElementaryTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyMagnitudeTypeEClass, AnyMagnitudeType.class, "AnyMagnitudeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyMagnitudeTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyNumTypeEClass, AnyNumType.class, "AnyNumType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyNumTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyRealTypeEClass, AnyRealType.class, "AnyRealType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyRealTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(realTypeEClass, RealType.class, "RealType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(realTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(lrealTypeEClass, LrealType.class, "LrealType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(lrealTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyIntTypeEClass, AnyIntType.class, "AnyIntType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyIntTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyUnsignedTypeEClass, AnyUnsignedType.class, "AnyUnsignedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(usintTypeEClass, UsintType.class, "UsintType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(usintTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(uintTypeEClass, UintType.class, "UintType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(uintTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(udintTypeEClass, UdintType.class, "UdintType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(udintTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(ulintTypeEClass, UlintType.class, "UlintType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(ulintTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anySignedTypeEClass, AnySignedType.class, "AnySignedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(sintTypeEClass, SintType.class, "SintType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(sintTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(intTypeEClass, IntType.class, "IntType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(intTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(dintTypeEClass, DintType.class, "DintType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(dintTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(lintTypeEClass, LintType.class, "LintType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(lintTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyDurationTypeEClass, AnyDurationType.class, "AnyDurationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(timeTypeEClass, TimeType.class, "TimeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(timeTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(ltimeTypeEClass, LtimeType.class, "LtimeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(ltimeTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyBitTypeEClass, AnyBitType.class, "AnyBitType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyBitTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(boolTypeEClass, BoolType.class, "BoolType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(boolTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(byteTypeEClass, ByteType.class, "ByteType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(byteTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(wordTypeEClass, WordType.class, "WordType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(wordTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(dwordTypeEClass, DwordType.class, "DwordType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(dwordTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(lwordTypeEClass, LwordType.class, "LwordType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(lwordTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyCharsTypeEClass, AnyCharsType.class, "AnyCharsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyCharsTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyStringTypeEClass, AnyStringType.class, "AnyStringType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyStringTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stringTypeEClass, StringType.class, "StringType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(stringTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(wstringTypeEClass, WstringType.class, "WstringType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(wstringTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyCharTypeEClass, AnyCharType.class, "AnyCharType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyCharTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(charTypeEClass, CharType.class, "CharType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(charTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(wcharTypeEClass, WcharType.class, "WcharType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(wcharTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyDateTypeEClass, AnyDateType.class, "AnyDateType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(anyDateTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(dateAndTimeTypeEClass, DateAndTimeType.class, "DateAndTimeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(dateAndTimeTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(ldtTypeEClass, LdtType.class, "LdtType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(ldtTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(dateTypeEClass, DateType.class, "DateType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(dateTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(timeOfDayTypeEClass, TimeOfDayType.class, "TimeOfDayType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(timeOfDayTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(ltodTypeEClass, LtodType.class, "LtodType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(ltodTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(ldateTypeEClass, LdateType.class, "LdateType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(ldateTypeEClass, theXMLTypePackage.getBoolean(), "isCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getDataType(), "other", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(baseType1EEnum, BaseType1.class, "BaseType1"); //$NON-NLS-1$
		addEEnumLiteral(baseType1EEnum, BaseType1.DATEANDTIME);
		addEEnumLiteral(baseType1EEnum, BaseType1.BYTE);
		addEEnumLiteral(baseType1EEnum, BaseType1.SINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.USINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.LWORD);
		addEEnumLiteral(baseType1EEnum, BaseType1.TIME);
		addEEnumLiteral(baseType1EEnum, BaseType1.WORD);
		addEEnumLiteral(baseType1EEnum, BaseType1.STRING);
		addEEnumLiteral(baseType1EEnum, BaseType1.BOOL);
		addEEnumLiteral(baseType1EEnum, BaseType1.LREAL);
		addEEnumLiteral(baseType1EEnum, BaseType1.REAL);
		addEEnumLiteral(baseType1EEnum, BaseType1.LINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.ULINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.UINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.DATE);
		addEEnumLiteral(baseType1EEnum, BaseType1.DWORD);
		addEEnumLiteral(baseType1EEnum, BaseType1.INT);
		addEEnumLiteral(baseType1EEnum, BaseType1.TIMEOFDAY);
		addEEnumLiteral(baseType1EEnum, BaseType1.WSTRING);
		addEEnumLiteral(baseType1EEnum, BaseType1.DINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.UDINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.ANY);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
		// http://www.obeo.fr/dsl/dnc/archetype
		createArchetypeAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
		addAnnotation
		  (getArrayType_Subranges(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Subrange", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getArrayType_InitialValues(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "InitialValues" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getEnumeratedType_EnumeratedValue(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "EnumeratedValue", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getEnumeratedValue_Comment(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Comment" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getEnumeratedValue_Name(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getStructuredType_MemberVariables(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "http://www.fordiac.org/61499/lib" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSubrange_LowerLimit(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "LowerLimit" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSubrange_UpperLimit(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "UpperLimit" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSubrangeType_Subrange(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Subrange", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.obeo.fr/dsl/dnc/archetype</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createArchetypeAnnotations() {
		String source = "http://www.obeo.fr/dsl/dnc/archetype"; //$NON-NLS-1$
		addAnnotation
		  (subrangeEClass,
		   source,
		   new String[] {
			   "archetype", "Description" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} //DataPackageImpl
