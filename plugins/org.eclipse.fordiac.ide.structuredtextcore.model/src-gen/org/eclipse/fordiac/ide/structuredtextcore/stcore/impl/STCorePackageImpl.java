/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fordiac.ide.model.Palette.PalettePackage;

import org.eclipse.fordiac.ide.model.data.DataPackage;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STString;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class STCorePackageImpl extends EPackageImpl implements STCorePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stCoreSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stVarDeclarationBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stVarPlainDeclarationBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stVarInputDeclarationBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stVarOutputDeclarationBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stVarTempDeclarationBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stInitializerExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stElementaryInitializerExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stArrayInitializerExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stArrayInitElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stAssignmentStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stCallStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stCallArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stCallUnnamedArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stCallNamedInputArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stCallNamedOutputArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stIfStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stElseIfPartEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stCaseStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stCaseCasesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stElsePartEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stForStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stWhileStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stRepeatStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stNumericLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stDateLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stTimeLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stTimeOfDayLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stDateAndTimeLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stStringLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stVarDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stReturnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stContinueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stExitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stNopEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stBinaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stUnaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stMemberAccessExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stArrayAccessExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stFeatureExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stMultibitPartialExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stBinaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stUnaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stMultiBitAccessSpecifierEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stDateEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stTimeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stTimeOfDayEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stDateAndTimeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stStringEDataType = null;

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
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private STCorePackageImpl() {
		super(eNS_URI, STCoreFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link STCorePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static STCorePackage init() {
		if (isInited) return (STCorePackage)EPackage.Registry.INSTANCE.getEPackage(STCorePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSTCorePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		STCorePackageImpl theSTCorePackage = registeredSTCorePackage instanceof STCorePackageImpl ? (STCorePackageImpl)registeredSTCorePackage : new STCorePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		DataPackage.eINSTANCE.eClass();
		LibraryElementPackage.eINSTANCE.eClass();
		PalettePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSTCorePackage.createPackageContents();

		// Initialize created meta-data
		theSTCorePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSTCorePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(STCorePackage.eNS_URI, theSTCorePackage);
		return theSTCorePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTSource() {
		return stSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTCoreSource() {
		return stCoreSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCoreSource_Statements() {
		return (EReference)stCoreSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTVarDeclarationBlock() {
		return stVarDeclarationBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTVarDeclarationBlock_Constant() {
		return (EAttribute)stVarDeclarationBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTVarDeclarationBlock_VarDeclarations() {
		return (EReference)stVarDeclarationBlockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTVarPlainDeclarationBlock() {
		return stVarPlainDeclarationBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTVarInputDeclarationBlock() {
		return stVarInputDeclarationBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTVarOutputDeclarationBlock() {
		return stVarOutputDeclarationBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTVarTempDeclarationBlock() {
		return stVarTempDeclarationBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTInitializerExpression() {
		return stInitializerExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTElementaryInitializerExpression() {
		return stElementaryInitializerExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTElementaryInitializerExpression_Value() {
		return (EReference)stElementaryInitializerExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTArrayInitializerExpression() {
		return stArrayInitializerExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTArrayInitializerExpression_Values() {
		return (EReference)stArrayInitializerExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTArrayInitElement() {
		return stArrayInitElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTArrayInitElement_IndexOrInitExpression() {
		return (EReference)stArrayInitElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTArrayInitElement_InitExpressions() {
		return (EReference)stArrayInitElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTStatement() {
		return stStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTAssignmentStatement() {
		return stAssignmentStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTAssignmentStatement_Left() {
		return (EReference)stAssignmentStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTAssignmentStatement_Right() {
		return (EReference)stAssignmentStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTCallStatement() {
		return stCallStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCallStatement_Call() {
		return (EReference)stCallStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTCallArgument() {
		return stCallArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTCallUnnamedArgument() {
		return stCallUnnamedArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCallUnnamedArgument_Arg() {
		return (EReference)stCallUnnamedArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTCallNamedInputArgument() {
		return stCallNamedInputArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCallNamedInputArgument_Target() {
		return (EReference)stCallNamedInputArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCallNamedInputArgument_Source() {
		return (EReference)stCallNamedInputArgumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTCallNamedOutputArgument() {
		return stCallNamedOutputArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTCallNamedOutputArgument_Not() {
		return (EAttribute)stCallNamedOutputArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCallNamedOutputArgument_Source() {
		return (EReference)stCallNamedOutputArgumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCallNamedOutputArgument_Target() {
		return (EReference)stCallNamedOutputArgumentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTIfStatement() {
		return stIfStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTIfStatement_Condition() {
		return (EReference)stIfStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTIfStatement_Statements() {
		return (EReference)stIfStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTIfStatement_Elseifs() {
		return (EReference)stIfStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTIfStatement_Else() {
		return (EReference)stIfStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTElseIfPart() {
		return stElseIfPartEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTElseIfPart_Condition() {
		return (EReference)stElseIfPartEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTElseIfPart_Statements() {
		return (EReference)stElseIfPartEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTCaseStatement() {
		return stCaseStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCaseStatement_Selector() {
		return (EReference)stCaseStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCaseStatement_Cases() {
		return (EReference)stCaseStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCaseStatement_Else() {
		return (EReference)stCaseStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTCaseCases() {
		return stCaseCasesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCaseCases_Conditions() {
		return (EReference)stCaseCasesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTCaseCases_Statements() {
		return (EReference)stCaseCasesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTElsePart() {
		return stElsePartEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTElsePart_Statements() {
		return (EReference)stElsePartEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTForStatement() {
		return stForStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTForStatement_Variable() {
		return (EReference)stForStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTForStatement_From() {
		return (EReference)stForStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTForStatement_To() {
		return (EReference)stForStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTForStatement_By() {
		return (EReference)stForStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTForStatement_Statements() {
		return (EReference)stForStatementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTWhileStatement() {
		return stWhileStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTWhileStatement_Condition() {
		return (EReference)stWhileStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTWhileStatement_Statements() {
		return (EReference)stWhileStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTRepeatStatement() {
		return stRepeatStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTRepeatStatement_Statements() {
		return (EReference)stRepeatStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTRepeatStatement_Condition() {
		return (EReference)stRepeatStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTExpression() {
		return stExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTNumericLiteral() {
		return stNumericLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTNumericLiteral_Type() {
		return (EReference)stNumericLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTNumericLiteral_Value() {
		return (EAttribute)stNumericLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTDateLiteral() {
		return stDateLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTDateLiteral_Type() {
		return (EReference)stDateLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTDateLiteral_Value() {
		return (EAttribute)stDateLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTTimeLiteral() {
		return stTimeLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTTimeLiteral_Type() {
		return (EReference)stTimeLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTTimeLiteral_Value() {
		return (EAttribute)stTimeLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTTimeOfDayLiteral() {
		return stTimeOfDayLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTTimeOfDayLiteral_Type() {
		return (EReference)stTimeOfDayLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTTimeOfDayLiteral_Value() {
		return (EAttribute)stTimeOfDayLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTDateAndTimeLiteral() {
		return stDateAndTimeLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTDateAndTimeLiteral_Type() {
		return (EReference)stDateAndTimeLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTDateAndTimeLiteral_Value() {
		return (EAttribute)stDateAndTimeLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTStringLiteral() {
		return stStringLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTStringLiteral_Type() {
		return (EReference)stStringLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTStringLiteral_Value() {
		return (EAttribute)stStringLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTVarDeclaration() {
		return stVarDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTVarDeclaration_LocatedAt() {
		return (EReference)stVarDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTVarDeclaration_Array() {
		return (EAttribute)stVarDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTVarDeclaration_Ranges() {
		return (EReference)stVarDeclarationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTVarDeclaration_Count() {
		return (EAttribute)stVarDeclarationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTVarDeclaration_Type() {
		return (EReference)stVarDeclarationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTVarDeclaration_MaxLength() {
		return (EReference)stVarDeclarationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTVarDeclaration_DefaultValue() {
		return (EReference)stVarDeclarationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTReturn() {
		return stReturnEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTContinue() {
		return stContinueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTExit() {
		return stExitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTNop() {
		return stNopEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTBinaryExpression() {
		return stBinaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTBinaryExpression_Left() {
		return (EReference)stBinaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTBinaryExpression_Op() {
		return (EAttribute)stBinaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTBinaryExpression_Right() {
		return (EReference)stBinaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTUnaryExpression() {
		return stUnaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTUnaryExpression_Op() {
		return (EAttribute)stUnaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTUnaryExpression_Expression() {
		return (EReference)stUnaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTMemberAccessExpression() {
		return stMemberAccessExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTMemberAccessExpression_Receiver() {
		return (EReference)stMemberAccessExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTMemberAccessExpression_Member() {
		return (EReference)stMemberAccessExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTArrayAccessExpression() {
		return stArrayAccessExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTArrayAccessExpression_Receiver() {
		return (EReference)stArrayAccessExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTArrayAccessExpression_Index() {
		return (EReference)stArrayAccessExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTFeatureExpression() {
		return stFeatureExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTFeatureExpression_Feature() {
		return (EReference)stFeatureExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTFeatureExpression_Call() {
		return (EAttribute)stFeatureExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTFeatureExpression_Parameters() {
		return (EReference)stFeatureExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTMultibitPartialExpression() {
		return stMultibitPartialExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTMultibitPartialExpression_Specifier() {
		return (EAttribute)stMultibitPartialExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTMultibitPartialExpression_Index() {
		return (EAttribute)stMultibitPartialExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTMultibitPartialExpression_Expression() {
		return (EReference)stMultibitPartialExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSTBinaryOperator() {
		return stBinaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSTUnaryOperator() {
		return stUnaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSTMultiBitAccessSpecifier() {
		return stMultiBitAccessSpecifierEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSTDate() {
		return stDateEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSTTime() {
		return stTimeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSTTimeOfDay() {
		return stTimeOfDayEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSTDateAndTime() {
		return stDateAndTimeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSTString() {
		return stStringEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCoreFactory getSTCoreFactory() {
		return (STCoreFactory)getEFactoryInstance();
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
		stSourceEClass = createEClass(ST_SOURCE);

		stCoreSourceEClass = createEClass(ST_CORE_SOURCE);
		createEReference(stCoreSourceEClass, ST_CORE_SOURCE__STATEMENTS);

		stVarDeclarationBlockEClass = createEClass(ST_VAR_DECLARATION_BLOCK);
		createEAttribute(stVarDeclarationBlockEClass, ST_VAR_DECLARATION_BLOCK__CONSTANT);
		createEReference(stVarDeclarationBlockEClass, ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS);

		stVarPlainDeclarationBlockEClass = createEClass(ST_VAR_PLAIN_DECLARATION_BLOCK);

		stVarInputDeclarationBlockEClass = createEClass(ST_VAR_INPUT_DECLARATION_BLOCK);

		stVarOutputDeclarationBlockEClass = createEClass(ST_VAR_OUTPUT_DECLARATION_BLOCK);

		stVarTempDeclarationBlockEClass = createEClass(ST_VAR_TEMP_DECLARATION_BLOCK);

		stInitializerExpressionEClass = createEClass(ST_INITIALIZER_EXPRESSION);

		stElementaryInitializerExpressionEClass = createEClass(ST_ELEMENTARY_INITIALIZER_EXPRESSION);
		createEReference(stElementaryInitializerExpressionEClass, ST_ELEMENTARY_INITIALIZER_EXPRESSION__VALUE);

		stArrayInitializerExpressionEClass = createEClass(ST_ARRAY_INITIALIZER_EXPRESSION);
		createEReference(stArrayInitializerExpressionEClass, ST_ARRAY_INITIALIZER_EXPRESSION__VALUES);

		stArrayInitElementEClass = createEClass(ST_ARRAY_INIT_ELEMENT);
		createEReference(stArrayInitElementEClass, ST_ARRAY_INIT_ELEMENT__INDEX_OR_INIT_EXPRESSION);
		createEReference(stArrayInitElementEClass, ST_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS);

		stStatementEClass = createEClass(ST_STATEMENT);

		stAssignmentStatementEClass = createEClass(ST_ASSIGNMENT_STATEMENT);
		createEReference(stAssignmentStatementEClass, ST_ASSIGNMENT_STATEMENT__LEFT);
		createEReference(stAssignmentStatementEClass, ST_ASSIGNMENT_STATEMENT__RIGHT);

		stCallStatementEClass = createEClass(ST_CALL_STATEMENT);
		createEReference(stCallStatementEClass, ST_CALL_STATEMENT__CALL);

		stCallArgumentEClass = createEClass(ST_CALL_ARGUMENT);

		stCallUnnamedArgumentEClass = createEClass(ST_CALL_UNNAMED_ARGUMENT);
		createEReference(stCallUnnamedArgumentEClass, ST_CALL_UNNAMED_ARGUMENT__ARG);

		stCallNamedInputArgumentEClass = createEClass(ST_CALL_NAMED_INPUT_ARGUMENT);
		createEReference(stCallNamedInputArgumentEClass, ST_CALL_NAMED_INPUT_ARGUMENT__TARGET);
		createEReference(stCallNamedInputArgumentEClass, ST_CALL_NAMED_INPUT_ARGUMENT__SOURCE);

		stCallNamedOutputArgumentEClass = createEClass(ST_CALL_NAMED_OUTPUT_ARGUMENT);
		createEAttribute(stCallNamedOutputArgumentEClass, ST_CALL_NAMED_OUTPUT_ARGUMENT__NOT);
		createEReference(stCallNamedOutputArgumentEClass, ST_CALL_NAMED_OUTPUT_ARGUMENT__SOURCE);
		createEReference(stCallNamedOutputArgumentEClass, ST_CALL_NAMED_OUTPUT_ARGUMENT__TARGET);

		stIfStatementEClass = createEClass(ST_IF_STATEMENT);
		createEReference(stIfStatementEClass, ST_IF_STATEMENT__CONDITION);
		createEReference(stIfStatementEClass, ST_IF_STATEMENT__STATEMENTS);
		createEReference(stIfStatementEClass, ST_IF_STATEMENT__ELSEIFS);
		createEReference(stIfStatementEClass, ST_IF_STATEMENT__ELSE);

		stElseIfPartEClass = createEClass(ST_ELSE_IF_PART);
		createEReference(stElseIfPartEClass, ST_ELSE_IF_PART__CONDITION);
		createEReference(stElseIfPartEClass, ST_ELSE_IF_PART__STATEMENTS);

		stCaseStatementEClass = createEClass(ST_CASE_STATEMENT);
		createEReference(stCaseStatementEClass, ST_CASE_STATEMENT__SELECTOR);
		createEReference(stCaseStatementEClass, ST_CASE_STATEMENT__CASES);
		createEReference(stCaseStatementEClass, ST_CASE_STATEMENT__ELSE);

		stCaseCasesEClass = createEClass(ST_CASE_CASES);
		createEReference(stCaseCasesEClass, ST_CASE_CASES__CONDITIONS);
		createEReference(stCaseCasesEClass, ST_CASE_CASES__STATEMENTS);

		stElsePartEClass = createEClass(ST_ELSE_PART);
		createEReference(stElsePartEClass, ST_ELSE_PART__STATEMENTS);

		stForStatementEClass = createEClass(ST_FOR_STATEMENT);
		createEReference(stForStatementEClass, ST_FOR_STATEMENT__VARIABLE);
		createEReference(stForStatementEClass, ST_FOR_STATEMENT__FROM);
		createEReference(stForStatementEClass, ST_FOR_STATEMENT__TO);
		createEReference(stForStatementEClass, ST_FOR_STATEMENT__BY);
		createEReference(stForStatementEClass, ST_FOR_STATEMENT__STATEMENTS);

		stWhileStatementEClass = createEClass(ST_WHILE_STATEMENT);
		createEReference(stWhileStatementEClass, ST_WHILE_STATEMENT__CONDITION);
		createEReference(stWhileStatementEClass, ST_WHILE_STATEMENT__STATEMENTS);

		stRepeatStatementEClass = createEClass(ST_REPEAT_STATEMENT);
		createEReference(stRepeatStatementEClass, ST_REPEAT_STATEMENT__STATEMENTS);
		createEReference(stRepeatStatementEClass, ST_REPEAT_STATEMENT__CONDITION);

		stExpressionEClass = createEClass(ST_EXPRESSION);

		stNumericLiteralEClass = createEClass(ST_NUMERIC_LITERAL);
		createEReference(stNumericLiteralEClass, ST_NUMERIC_LITERAL__TYPE);
		createEAttribute(stNumericLiteralEClass, ST_NUMERIC_LITERAL__VALUE);

		stDateLiteralEClass = createEClass(ST_DATE_LITERAL);
		createEReference(stDateLiteralEClass, ST_DATE_LITERAL__TYPE);
		createEAttribute(stDateLiteralEClass, ST_DATE_LITERAL__VALUE);

		stTimeLiteralEClass = createEClass(ST_TIME_LITERAL);
		createEReference(stTimeLiteralEClass, ST_TIME_LITERAL__TYPE);
		createEAttribute(stTimeLiteralEClass, ST_TIME_LITERAL__VALUE);

		stTimeOfDayLiteralEClass = createEClass(ST_TIME_OF_DAY_LITERAL);
		createEReference(stTimeOfDayLiteralEClass, ST_TIME_OF_DAY_LITERAL__TYPE);
		createEAttribute(stTimeOfDayLiteralEClass, ST_TIME_OF_DAY_LITERAL__VALUE);

		stDateAndTimeLiteralEClass = createEClass(ST_DATE_AND_TIME_LITERAL);
		createEReference(stDateAndTimeLiteralEClass, ST_DATE_AND_TIME_LITERAL__TYPE);
		createEAttribute(stDateAndTimeLiteralEClass, ST_DATE_AND_TIME_LITERAL__VALUE);

		stStringLiteralEClass = createEClass(ST_STRING_LITERAL);
		createEReference(stStringLiteralEClass, ST_STRING_LITERAL__TYPE);
		createEAttribute(stStringLiteralEClass, ST_STRING_LITERAL__VALUE);

		stVarDeclarationEClass = createEClass(ST_VAR_DECLARATION);
		createEReference(stVarDeclarationEClass, ST_VAR_DECLARATION__LOCATED_AT);
		createEAttribute(stVarDeclarationEClass, ST_VAR_DECLARATION__ARRAY);
		createEReference(stVarDeclarationEClass, ST_VAR_DECLARATION__RANGES);
		createEAttribute(stVarDeclarationEClass, ST_VAR_DECLARATION__COUNT);
		createEReference(stVarDeclarationEClass, ST_VAR_DECLARATION__TYPE);
		createEReference(stVarDeclarationEClass, ST_VAR_DECLARATION__MAX_LENGTH);
		createEReference(stVarDeclarationEClass, ST_VAR_DECLARATION__DEFAULT_VALUE);

		stReturnEClass = createEClass(ST_RETURN);

		stContinueEClass = createEClass(ST_CONTINUE);

		stExitEClass = createEClass(ST_EXIT);

		stNopEClass = createEClass(ST_NOP);

		stBinaryExpressionEClass = createEClass(ST_BINARY_EXPRESSION);
		createEReference(stBinaryExpressionEClass, ST_BINARY_EXPRESSION__LEFT);
		createEAttribute(stBinaryExpressionEClass, ST_BINARY_EXPRESSION__OP);
		createEReference(stBinaryExpressionEClass, ST_BINARY_EXPRESSION__RIGHT);

		stUnaryExpressionEClass = createEClass(ST_UNARY_EXPRESSION);
		createEAttribute(stUnaryExpressionEClass, ST_UNARY_EXPRESSION__OP);
		createEReference(stUnaryExpressionEClass, ST_UNARY_EXPRESSION__EXPRESSION);

		stMemberAccessExpressionEClass = createEClass(ST_MEMBER_ACCESS_EXPRESSION);
		createEReference(stMemberAccessExpressionEClass, ST_MEMBER_ACCESS_EXPRESSION__RECEIVER);
		createEReference(stMemberAccessExpressionEClass, ST_MEMBER_ACCESS_EXPRESSION__MEMBER);

		stArrayAccessExpressionEClass = createEClass(ST_ARRAY_ACCESS_EXPRESSION);
		createEReference(stArrayAccessExpressionEClass, ST_ARRAY_ACCESS_EXPRESSION__RECEIVER);
		createEReference(stArrayAccessExpressionEClass, ST_ARRAY_ACCESS_EXPRESSION__INDEX);

		stFeatureExpressionEClass = createEClass(ST_FEATURE_EXPRESSION);
		createEReference(stFeatureExpressionEClass, ST_FEATURE_EXPRESSION__FEATURE);
		createEAttribute(stFeatureExpressionEClass, ST_FEATURE_EXPRESSION__CALL);
		createEReference(stFeatureExpressionEClass, ST_FEATURE_EXPRESSION__PARAMETERS);

		stMultibitPartialExpressionEClass = createEClass(ST_MULTIBIT_PARTIAL_EXPRESSION);
		createEAttribute(stMultibitPartialExpressionEClass, ST_MULTIBIT_PARTIAL_EXPRESSION__SPECIFIER);
		createEAttribute(stMultibitPartialExpressionEClass, ST_MULTIBIT_PARTIAL_EXPRESSION__INDEX);
		createEReference(stMultibitPartialExpressionEClass, ST_MULTIBIT_PARTIAL_EXPRESSION__EXPRESSION);

		// Create enums
		stBinaryOperatorEEnum = createEEnum(ST_BINARY_OPERATOR);
		stUnaryOperatorEEnum = createEEnum(ST_UNARY_OPERATOR);
		stMultiBitAccessSpecifierEEnum = createEEnum(ST_MULTI_BIT_ACCESS_SPECIFIER);

		// Create data types
		stDateEDataType = createEDataType(ST_DATE);
		stTimeEDataType = createEDataType(ST_TIME);
		stTimeOfDayEDataType = createEDataType(ST_TIME_OF_DAY);
		stDateAndTimeEDataType = createEDataType(ST_DATE_AND_TIME);
		stStringEDataType = createEDataType(ST_STRING);
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
		DataPackage theDataPackage = (DataPackage)EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		stCoreSourceEClass.getESuperTypes().add(this.getSTSource());
		stVarPlainDeclarationBlockEClass.getESuperTypes().add(this.getSTVarDeclarationBlock());
		stVarInputDeclarationBlockEClass.getESuperTypes().add(this.getSTVarDeclarationBlock());
		stVarOutputDeclarationBlockEClass.getESuperTypes().add(this.getSTVarDeclarationBlock());
		stVarTempDeclarationBlockEClass.getESuperTypes().add(this.getSTVarDeclarationBlock());
		stElementaryInitializerExpressionEClass.getESuperTypes().add(this.getSTInitializerExpression());
		stArrayInitializerExpressionEClass.getESuperTypes().add(this.getSTInitializerExpression());
		stAssignmentStatementEClass.getESuperTypes().add(this.getSTStatement());
		stCallStatementEClass.getESuperTypes().add(this.getSTStatement());
		stCallUnnamedArgumentEClass.getESuperTypes().add(this.getSTCallArgument());
		stCallNamedInputArgumentEClass.getESuperTypes().add(this.getSTCallArgument());
		stCallNamedOutputArgumentEClass.getESuperTypes().add(this.getSTCallArgument());
		stIfStatementEClass.getESuperTypes().add(this.getSTStatement());
		stCaseStatementEClass.getESuperTypes().add(this.getSTStatement());
		stForStatementEClass.getESuperTypes().add(this.getSTStatement());
		stWhileStatementEClass.getESuperTypes().add(this.getSTStatement());
		stRepeatStatementEClass.getESuperTypes().add(this.getSTStatement());
		stNumericLiteralEClass.getESuperTypes().add(this.getSTExpression());
		stDateLiteralEClass.getESuperTypes().add(this.getSTExpression());
		stTimeLiteralEClass.getESuperTypes().add(this.getSTExpression());
		stTimeOfDayLiteralEClass.getESuperTypes().add(this.getSTExpression());
		stDateAndTimeLiteralEClass.getESuperTypes().add(this.getSTExpression());
		stStringLiteralEClass.getESuperTypes().add(this.getSTExpression());
		stVarDeclarationEClass.getESuperTypes().add(theLibraryElementPackage.getINamedElement());
		stReturnEClass.getESuperTypes().add(this.getSTStatement());
		stContinueEClass.getESuperTypes().add(this.getSTStatement());
		stExitEClass.getESuperTypes().add(this.getSTStatement());
		stNopEClass.getESuperTypes().add(this.getSTStatement());
		stBinaryExpressionEClass.getESuperTypes().add(this.getSTExpression());
		stUnaryExpressionEClass.getESuperTypes().add(this.getSTExpression());
		stMemberAccessExpressionEClass.getESuperTypes().add(this.getSTExpression());
		stArrayAccessExpressionEClass.getESuperTypes().add(this.getSTExpression());
		stFeatureExpressionEClass.getESuperTypes().add(this.getSTExpression());
		stMultibitPartialExpressionEClass.getESuperTypes().add(this.getSTExpression());

		// Initialize classes and features; add operations and parameters
		initEClass(stSourceEClass, STSource.class, "STSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stCoreSourceEClass, STCoreSource.class, "STCoreSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTCoreSource_Statements(), this.getSTStatement(), null, "statements", null, 0, -1, STCoreSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stVarDeclarationBlockEClass, STVarDeclarationBlock.class, "STVarDeclarationBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSTVarDeclarationBlock_Constant(), ecorePackage.getEBoolean(), "constant", null, 0, 1, STVarDeclarationBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTVarDeclarationBlock_VarDeclarations(), this.getSTVarDeclaration(), null, "varDeclarations", null, 0, -1, STVarDeclarationBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stVarPlainDeclarationBlockEClass, STVarPlainDeclarationBlock.class, "STVarPlainDeclarationBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stVarInputDeclarationBlockEClass, STVarInputDeclarationBlock.class, "STVarInputDeclarationBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stVarOutputDeclarationBlockEClass, STVarOutputDeclarationBlock.class, "STVarOutputDeclarationBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stVarTempDeclarationBlockEClass, STVarTempDeclarationBlock.class, "STVarTempDeclarationBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stInitializerExpressionEClass, STInitializerExpression.class, "STInitializerExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stElementaryInitializerExpressionEClass, STElementaryInitializerExpression.class, "STElementaryInitializerExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTElementaryInitializerExpression_Value(), this.getSTExpression(), null, "value", null, 0, 1, STElementaryInitializerExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stArrayInitializerExpressionEClass, STArrayInitializerExpression.class, "STArrayInitializerExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTArrayInitializerExpression_Values(), this.getSTArrayInitElement(), null, "values", null, 0, -1, STArrayInitializerExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stArrayInitElementEClass, STArrayInitElement.class, "STArrayInitElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTArrayInitElement_IndexOrInitExpression(), this.getSTExpression(), null, "indexOrInitExpression", null, 0, 1, STArrayInitElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTArrayInitElement_InitExpressions(), this.getSTExpression(), null, "initExpressions", null, 0, -1, STArrayInitElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stStatementEClass, STStatement.class, "STStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stAssignmentStatementEClass, STAssignmentStatement.class, "STAssignmentStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTAssignmentStatement_Left(), this.getSTExpression(), null, "left", null, 0, 1, STAssignmentStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTAssignmentStatement_Right(), this.getSTExpression(), null, "right", null, 0, 1, STAssignmentStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stCallStatementEClass, STCallStatement.class, "STCallStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTCallStatement_Call(), this.getSTExpression(), null, "call", null, 0, 1, STCallStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stCallArgumentEClass, STCallArgument.class, "STCallArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stCallUnnamedArgumentEClass, STCallUnnamedArgument.class, "STCallUnnamedArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTCallUnnamedArgument_Arg(), this.getSTExpression(), null, "arg", null, 0, 1, STCallUnnamedArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stCallNamedInputArgumentEClass, STCallNamedInputArgument.class, "STCallNamedInputArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTCallNamedInputArgument_Target(), theLibraryElementPackage.getINamedElement(), null, "target", null, 0, 1, STCallNamedInputArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTCallNamedInputArgument_Source(), this.getSTExpression(), null, "source", null, 0, 1, STCallNamedInputArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stCallNamedOutputArgumentEClass, STCallNamedOutputArgument.class, "STCallNamedOutputArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSTCallNamedOutputArgument_Not(), ecorePackage.getEBoolean(), "not", null, 0, 1, STCallNamedOutputArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTCallNamedOutputArgument_Source(), theLibraryElementPackage.getINamedElement(), null, "source", null, 0, 1, STCallNamedOutputArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTCallNamedOutputArgument_Target(), theLibraryElementPackage.getINamedElement(), null, "target", null, 0, 1, STCallNamedOutputArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stIfStatementEClass, STIfStatement.class, "STIfStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTIfStatement_Condition(), this.getSTExpression(), null, "condition", null, 0, 1, STIfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTIfStatement_Statements(), this.getSTStatement(), null, "statements", null, 0, -1, STIfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTIfStatement_Elseifs(), this.getSTElseIfPart(), null, "elseifs", null, 0, -1, STIfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTIfStatement_Else(), this.getSTElsePart(), null, "else", null, 0, 1, STIfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stElseIfPartEClass, STElseIfPart.class, "STElseIfPart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTElseIfPart_Condition(), this.getSTExpression(), null, "condition", null, 0, 1, STElseIfPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTElseIfPart_Statements(), this.getSTStatement(), null, "statements", null, 0, -1, STElseIfPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stCaseStatementEClass, STCaseStatement.class, "STCaseStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTCaseStatement_Selector(), this.getSTExpression(), null, "selector", null, 0, 1, STCaseStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTCaseStatement_Cases(), this.getSTCaseCases(), null, "cases", null, 0, -1, STCaseStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTCaseStatement_Else(), this.getSTElsePart(), null, "else", null, 0, 1, STCaseStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stCaseCasesEClass, STCaseCases.class, "STCaseCases", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTCaseCases_Conditions(), this.getSTExpression(), null, "conditions", null, 0, -1, STCaseCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTCaseCases_Statements(), this.getSTStatement(), null, "statements", null, 0, -1, STCaseCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stElsePartEClass, STElsePart.class, "STElsePart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTElsePart_Statements(), this.getSTStatement(), null, "statements", null, 0, -1, STElsePart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stForStatementEClass, STForStatement.class, "STForStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTForStatement_Variable(), this.getSTVarDeclaration(), null, "variable", null, 0, 1, STForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTForStatement_From(), this.getSTExpression(), null, "from", null, 0, 1, STForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTForStatement_To(), this.getSTExpression(), null, "to", null, 0, 1, STForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTForStatement_By(), this.getSTExpression(), null, "by", null, 0, 1, STForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTForStatement_Statements(), this.getSTStatement(), null, "statements", null, 0, -1, STForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stWhileStatementEClass, STWhileStatement.class, "STWhileStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTWhileStatement_Condition(), this.getSTExpression(), null, "condition", null, 0, 1, STWhileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTWhileStatement_Statements(), this.getSTStatement(), null, "statements", null, 0, -1, STWhileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stRepeatStatementEClass, STRepeatStatement.class, "STRepeatStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTRepeatStatement_Statements(), this.getSTStatement(), null, "statements", null, 0, -1, STRepeatStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTRepeatStatement_Condition(), this.getSTExpression(), null, "condition", null, 0, 1, STRepeatStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stExpressionEClass, STExpression.class, "STExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(stExpressionEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stNumericLiteralEClass, STNumericLiteral.class, "STNumericLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTNumericLiteral_Type(), theDataPackage.getDataType(), null, "type", null, 0, 1, STNumericLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTNumericLiteral_Value(), ecorePackage.getEBigDecimal(), "value", null, 0, 1, STNumericLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stNumericLiteralEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stDateLiteralEClass, STDateLiteral.class, "STDateLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTDateLiteral_Type(), theDataPackage.getDataType(), null, "type", null, 0, 1, STDateLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTDateLiteral_Value(), this.getSTDate(), "value", null, 0, 1, STDateLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stDateLiteralEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stTimeLiteralEClass, STTimeLiteral.class, "STTimeLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTTimeLiteral_Type(), theDataPackage.getDataType(), null, "type", null, 0, 1, STTimeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTTimeLiteral_Value(), this.getSTTime(), "value", null, 0, 1, STTimeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stTimeLiteralEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stTimeOfDayLiteralEClass, STTimeOfDayLiteral.class, "STTimeOfDayLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTTimeOfDayLiteral_Type(), theDataPackage.getDataType(), null, "type", null, 0, 1, STTimeOfDayLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTTimeOfDayLiteral_Value(), this.getSTTimeOfDay(), "value", null, 0, 1, STTimeOfDayLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stTimeOfDayLiteralEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stDateAndTimeLiteralEClass, STDateAndTimeLiteral.class, "STDateAndTimeLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTDateAndTimeLiteral_Type(), theDataPackage.getDataType(), null, "type", null, 0, 1, STDateAndTimeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTDateAndTimeLiteral_Value(), this.getSTDateAndTime(), "value", null, 0, 1, STDateAndTimeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stDateAndTimeLiteralEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stStringLiteralEClass, STStringLiteral.class, "STStringLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTStringLiteral_Type(), theDataPackage.getDataType(), null, "type", null, 0, 1, STStringLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTStringLiteral_Value(), this.getSTString(), "value", null, 0, 1, STStringLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stStringLiteralEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stVarDeclarationEClass, STVarDeclaration.class, "STVarDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTVarDeclaration_LocatedAt(), theLibraryElementPackage.getINamedElement(), null, "locatedAt", null, 0, 1, STVarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTVarDeclaration_Array(), ecorePackage.getEBoolean(), "array", null, 0, 1, STVarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTVarDeclaration_Ranges(), this.getSTExpression(), null, "ranges", null, 0, -1, STVarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTVarDeclaration_Count(), ecorePackage.getEString(), "count", null, 0, -1, STVarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTVarDeclaration_Type(), theLibraryElementPackage.getINamedElement(), null, "type", null, 0, 1, STVarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTVarDeclaration_MaxLength(), this.getSTExpression(), null, "maxLength", null, 0, 1, STVarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTVarDeclaration_DefaultValue(), this.getSTInitializerExpression(), null, "defaultValue", null, 0, 1, STVarDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stReturnEClass, STReturn.class, "STReturn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stContinueEClass, STContinue.class, "STContinue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stExitEClass, STExit.class, "STExit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stNopEClass, STNop.class, "STNop", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stBinaryExpressionEClass, STBinaryExpression.class, "STBinaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTBinaryExpression_Left(), this.getSTExpression(), null, "left", null, 0, 1, STBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTBinaryExpression_Op(), this.getSTBinaryOperator(), "op", null, 0, 1, STBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTBinaryExpression_Right(), this.getSTExpression(), null, "right", null, 0, 1, STBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stBinaryExpressionEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stUnaryExpressionEClass, STUnaryExpression.class, "STUnaryExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSTUnaryExpression_Op(), this.getSTUnaryOperator(), "op", null, 0, 1, STUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTUnaryExpression_Expression(), this.getSTExpression(), null, "expression", null, 0, 1, STUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stUnaryExpressionEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stMemberAccessExpressionEClass, STMemberAccessExpression.class, "STMemberAccessExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTMemberAccessExpression_Receiver(), this.getSTExpression(), null, "receiver", null, 0, 1, STMemberAccessExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTMemberAccessExpression_Member(), this.getSTExpression(), null, "member", null, 0, 1, STMemberAccessExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stMemberAccessExpressionEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stArrayAccessExpressionEClass, STArrayAccessExpression.class, "STArrayAccessExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTArrayAccessExpression_Receiver(), this.getSTExpression(), null, "receiver", null, 0, 1, STArrayAccessExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTArrayAccessExpression_Index(), this.getSTExpression(), null, "index", null, 0, -1, STArrayAccessExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stArrayAccessExpressionEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stFeatureExpressionEClass, STFeatureExpression.class, "STFeatureExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTFeatureExpression_Feature(), theLibraryElementPackage.getINamedElement(), null, "feature", null, 0, 1, STFeatureExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTFeatureExpression_Call(), ecorePackage.getEBoolean(), "call", null, 0, 1, STFeatureExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTFeatureExpression_Parameters(), this.getSTCallArgument(), null, "parameters", null, 0, -1, STFeatureExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stFeatureExpressionEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		EOperation op = addEOperation(stFeatureExpressionEClass, null, "getMappedInputArguments", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType(theLibraryElementPackage.getINamedElement());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getSTExpression());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = addEOperation(stFeatureExpressionEClass, null, "getMappedOutputArguments", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(theLibraryElementPackage.getINamedElement());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theLibraryElementPackage.getINamedElement());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(stMultibitPartialExpressionEClass, STMultibitPartialExpression.class, "STMultibitPartialExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSTMultibitPartialExpression_Specifier(), this.getSTMultiBitAccessSpecifier(), "specifier", null, 0, 1, STMultibitPartialExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSTMultibitPartialExpression_Index(), ecorePackage.getEBigInteger(), "index", null, 0, 1, STMultibitPartialExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTMultibitPartialExpression_Expression(), this.getSTExpression(), null, "expression", null, 0, 1, STMultibitPartialExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stMultibitPartialExpressionEClass, theLibraryElementPackage.getINamedElement(), "getResultType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(stBinaryOperatorEEnum, STBinaryOperator.class, "STBinaryOperator"); //$NON-NLS-1$
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.RANGE);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.OR);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.XOR);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.AND);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.AMPERSAND);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.EQ);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.NE);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.LT);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.LE);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.GT);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.GE);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.ADD);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.SUB);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.MUL);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.DIV);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.MOD);
		addEEnumLiteral(stBinaryOperatorEEnum, STBinaryOperator.POWER);

		initEEnum(stUnaryOperatorEEnum, STUnaryOperator.class, "STUnaryOperator"); //$NON-NLS-1$
		addEEnumLiteral(stUnaryOperatorEEnum, STUnaryOperator.MINUS);
		addEEnumLiteral(stUnaryOperatorEEnum, STUnaryOperator.PLUS);
		addEEnumLiteral(stUnaryOperatorEEnum, STUnaryOperator.NOT);

		initEEnum(stMultiBitAccessSpecifierEEnum, STMultiBitAccessSpecifier.class, "STMultiBitAccessSpecifier"); //$NON-NLS-1$
		addEEnumLiteral(stMultiBitAccessSpecifierEEnum, STMultiBitAccessSpecifier.X);
		addEEnumLiteral(stMultiBitAccessSpecifierEEnum, STMultiBitAccessSpecifier.B);
		addEEnumLiteral(stMultiBitAccessSpecifierEEnum, STMultiBitAccessSpecifier.W);
		addEEnumLiteral(stMultiBitAccessSpecifierEEnum, STMultiBitAccessSpecifier.D);
		addEEnumLiteral(stMultiBitAccessSpecifierEEnum, STMultiBitAccessSpecifier.L);

		// Initialize data types
		initEDataType(stDateEDataType, LocalDate.class, "STDate", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(stTimeEDataType, Duration.class, "STTime", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(stTimeOfDayEDataType, LocalTime.class, "STTimeOfDay", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(stDateAndTimeEDataType, LocalDateTime.class, "STDateAndTime", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(stStringEDataType, STString.class, "STString", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //STCorePackageImpl
