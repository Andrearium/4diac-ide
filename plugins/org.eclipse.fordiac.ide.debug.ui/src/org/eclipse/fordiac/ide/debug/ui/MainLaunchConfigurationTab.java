/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable;
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public abstract class MainLaunchConfigurationTab extends AbstractLaunchConfigurationTab {
	private Text resourceText;
	private Button stopOnFirstLineCheckbox;
	private TreeViewer argumentsTable;

	private List<Variable<?>> arguments;

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		setControl(comp);

		final Composite resourceComponent = createResourceComponent(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(resourceComponent);

		final Composite optionsComponent = createOptionsComponent(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(optionsComponent);
	}

	protected Composite createResourceComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Target"); //$NON-NLS-1$

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		final Label resourceLabel = new Label(comp, SWT.NONE);
		resourceLabel.setText("Location:"); //$NON-NLS-1$
		GridDataFactory.swtDefaults().applyTo(resourceLabel);

		resourceText = new Text(comp, SWT.BORDER);
		resourceText.setEnabled(false);
		resourceText.setMessage("Location"); //$NON-NLS-1$
		resourceText.addModifyListener(e -> scheduleUpdateJob());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(resourceText);

		final Button resourceButton = new Button(comp, SWT.BORDER);
		resourceButton.setText("Browse..."); //$NON-NLS-1$
		resourceButton.addSelectionListener(widgetSelectedAdapter(e -> handleResourceButtonSelected()));
		GridDataFactory.swtDefaults().applyTo(resourceButton);
		return group;
	}

	protected Composite createOptionsComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Options"); //$NON-NLS-1$

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		stopOnFirstLineCheckbox = new Button(comp, SWT.CHECK);
		stopOnFirstLineCheckbox.setText("Stop on first line"); //$NON-NLS-1$
		stopOnFirstLineCheckbox.addSelectionListener(widgetSelectedAdapter(e -> updateLaunchConfigurationDialog()));
		GridDataFactory.fillDefaults().applyTo(stopOnFirstLineCheckbox);
		return group;
	}

	protected Composite createArgumentsComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Arguments"); //$NON-NLS-1$

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		argumentsTable = new TreeViewer(comp,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		final Tree tree = argumentsTable.getTree();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		argumentsTable.setContentProvider(new ArgumentsContentProvider());

		final TreeViewerColumn nameColumn = new TreeViewerColumn(argumentsTable, SWT.NONE);
		nameColumn.getColumn().setText("Name"); //$NON-NLS-1$
		nameColumn.getColumn().setWidth(300);
		nameColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(new ArgumentsNameLabelProvider()));

		final TreeViewerColumn valueColumn = new TreeViewerColumn(argumentsTable, SWT.NONE);
		valueColumn.getColumn().setText("Value"); //$NON-NLS-1$
		valueColumn.getColumn().setWidth(300);
		valueColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(new ArgumentsValueLabelProvider()));
		valueColumn.setEditingSupport(new ArgumentsValueEditingSupport(argumentsTable));

		return group;
	}

	private void handleResourceButtonSelected() {
		IResource resource = getResource();
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
				new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		if (resource != null) {
			dialog.setInitialSelection(resource);
		}
		dialog.setAllowMultiple(false);
		dialog.addFilter(new ViewerFilter() {

			@Override
			public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
				final IResource resource = Adapters.adapt(element, IResource.class);
				try {
					return filterTargetResource(resource);
				} catch (final CoreException e) {
					return false;
				}
			}
		});
		dialog.open();
		final Object[] result = dialog.getResult();
		if (result != null && result.length > 0 && result[0] instanceof IResource) {
			resource = (IResource) result[0];
			final String resourceString = resource.getFullPath().toString();
			resourceText.setText(resourceString);
			handleResourceUpdated();
		}
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.removeAttribute(LaunchConfigurationAttributes.RESOURCE);
		configuration.removeAttribute(LaunchConfigurationAttributes.ARGUMENTS);
		configuration.removeAttribute(LaunchConfigurationAttributes.STOP_ON_FIRST_LINE);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			final String resourceAttribute = configuration.getAttribute(LaunchConfigurationAttributes.RESOURCE, ""); //$NON-NLS-1$
			resourceText.setText(resourceAttribute);
			stopOnFirstLineCheckbox.setSelection(LaunchConfigurationAttributes.isStopOnFirstLine(configuration));
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	protected void initializeArgumentsFrom(final ILaunchConfiguration configuration) {
		try {
			arguments = LaunchConfigurationAttributes.getArguments(configuration, getDefaultArguments());
			argumentsTable.setInput(arguments);
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		final String resourceString = resourceText.getText();
		configuration.setAttribute(LaunchConfigurationAttributes.RESOURCE, resourceString);
		if (arguments != null) {
			configuration.setAttribute(LaunchConfigurationAttributes.ARGUMENTS, arguments.stream()
					.collect(Collectors.toMap(Variable::getName, variable -> variable.getValue().toString())));
		} else {
			configuration.removeAttribute(LaunchConfigurationAttributes.ARGUMENTS);
		}
		configuration.setAttribute(LaunchConfigurationAttributes.STOP_ON_FIRST_LINE,
				stopOnFirstLineCheckbox.getSelection());
	}

	protected void handleResourceUpdated() {
		updateLaunchConfigurationDialog();
	}

	protected void updateArguments() {
		final var oldArguments = arguments;
		arguments = getDefaultArguments();
		if (oldArguments != null && arguments != null) {
			arguments.forEach(variable -> oldArguments.stream()
					.filter(arg -> Objects.equals(arg.getName(), variable.getName())).findFirst().ifPresent(arg -> {
						try {
							variable.setValue(arg.getValue().toString());
						} catch (final Exception e) {
							FordiacLogHelper.logWarning(e.getMessage(), e);
						}
					}));
		}
		argumentsTable.setInput(arguments);
		updateLaunchConfigurationDialog();
	}

	protected abstract List<Variable<?>> getDefaultArguments();

	protected boolean filterTargetResource(final IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			return false;
		} else if (resource instanceof IContainer) {
			for (final IResource child : ((IContainer) resource).members()) {
				if (filterTargetResource(child)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "Main"; //$NON-NLS-1$
	}

	protected IResource getResource() {
		final String resourceString = resourceText.getText();
		if (resourceString != null && !resourceString.isEmpty()) {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(resourceString));
		}
		return null;
	}

	public static class ArgumentsContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(final Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof ArrayVariable) {
				return ((ArrayVariable) parentElement).getElements().toArray();
			} else if (parentElement instanceof StructVariable) {
				return ((StructVariable) parentElement).getMembers().values().toArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			return element instanceof ArrayVariable || element instanceof StructVariable;
		}
	}

	private static class ArgumentsNameLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof Variable<?>) {
				final Variable<?> variable = (Variable<?>) element;
				return new StyledString(variable.getName());
			}
			return null;
		}

	}

	private static class ArgumentsValueLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof Variable<?>) {
				final Variable<?> variable = (Variable<?>) element;
				return new StyledString(variable.getValue().toString());
			}
			return null;
		}

	}

	private final class ArgumentsValueEditingSupport extends EditingSupport {
		private final CellEditor editor;

		private ArgumentsValueEditingSupport(final TreeViewer viewer) {
			super(viewer);
			this.editor = new TextCellEditor(viewer.getTree());
		}

		@Override
		protected CellEditor getCellEditor(final Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(final Object element) {
			return element instanceof Variable;
		}

		@Override
		protected Object getValue(final Object element) {
			if (element instanceof Variable<?>) {
				return ((Variable<?>) element).getValue().toString();
			}
			return null;
		}

		@Override
		protected void setValue(final Object element, final Object value) {
			if (element instanceof Variable<?>) {
				final Variable<?> variable = (Variable<?>) element;
				try {
					variable.setValue(value.toString());
				} catch (final Exception e) {
					FordiacLogHelper.logWarning(e.getMessage(), e);
					MessageDialog.openError(getViewer().getControl().getShell(), "Invalid Value", //$NON-NLS-1$
							String.format("'%s' is not a valid value for variable %s with type %s", value.toString(), //$NON-NLS-1$
									variable.getName(), variable.getType().getName()));
				}
				// update element itself
				getViewer().update(element, null);
				// update child elements
				if (variable instanceof ArrayVariable) {
					((ArrayVariable) variable).getElements().forEach(child -> getViewer().update(child, null));
				} else if (variable instanceof StructVariable) {
					((StructVariable) variable).getMembers().values().forEach(child -> getViewer().update(child, null));
				}
				// update parent element (if exists)
				MainLaunchConfigurationTab.this.arguments.stream().filter(
						arg -> (arg instanceof ArrayVariable && ((ArrayVariable) arg).getElements().contains(element))
						|| (arg instanceof StructVariable
								&& ((StructVariable) arg).getMembers().values().contains(element)))
				.forEach(container -> getViewer().update(container, null));
				MainLaunchConfigurationTab.this.updateLaunchConfigurationDialog();
			}
		}
	}
}
