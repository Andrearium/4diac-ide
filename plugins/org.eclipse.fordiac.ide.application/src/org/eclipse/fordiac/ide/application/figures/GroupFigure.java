/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.gef.figures.AbstractShadowBorder;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;

public class GroupFigure extends Figure {

	private final RoundedRectangle mainFigure = new BorderedRoundedRectangle();
	private InstanceCommentFigure commentFigure;
	private RoundedRectangle nameFigure;

	public GroupFigure() {
		createFigure();
	}

	protected void createFigure() {
		createMainFigure();
		createCommentFigure();

		mainFigure.add(commentFigure, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

		final GridLayout rootLayout = new GridLayout(1, false);
		rootLayout.verticalSpacing = 0;
		rootLayout.horizontalSpacing = 0;
		setLayoutManager(rootLayout);
		add(mainFigure, new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));

		createNameFigure();
		add(nameFigure, 0);

		setBorder(new GroupShadowBorder());
	}

	private void createMainFigure() {
		mainFigure.setOutline(false);
		mainFigure.setCornerDimensions(new Dimension(DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM));
		mainFigure.setFillXOR(false);
		mainFigure.setOpaque(false);

		final GridLayout mainLayout = new GridLayout(1, true);
		mainLayout.verticalSpacing = 0;
		mainLayout.horizontalSpacing = 0;
		mainFigure.setLayoutManager(mainLayout);
	}

	private void createCommentFigure() {
		commentFigure = new InstanceCommentFigure();
		commentFigure.setCursor(Cursors.SIZEALL);
		final AdvancedLineBorder commentBorder = new AdvancedLineBorder(PositionConstants.SOUTH);
		commentFigure.setBorder(commentBorder);
	}

	private void createNameFigure() {
		final GridLayout nameLayout = new GridLayout(1, false);
		nameLayout.verticalSpacing = 0;
		nameLayout.horizontalSpacing = 0;
		nameFigure = new BorderedRoundedRectangle();
		nameFigure.setCornerDimensions(new Dimension(DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM));
		nameFigure.setOutline(false);
		nameFigure.setLayoutManager(nameLayout);
	}

	public InstanceCommentFigure getCommentFigure() {
		return commentFigure;
	}

	public void setCommentFigure(final InstanceCommentFigure commentFigure) {
		this.commentFigure = commentFigure;
	}

	public RoundedRectangle getNameFigure() {
		return nameFigure;
	}

	public RoundedRectangle getMainFigure() {
		return mainFigure;
	}

	private static class GroupShadowBorder extends AbstractShadowBorder {

		@Override
		public void paintBackground(final IFigure figure, final Graphics graphics, final Insets insets) {
			Assert.isTrue(figure instanceof GroupFigure);
			final GroupFigure groupFigure = (GroupFigure) figure;

			graphics.pushState();
			graphics.setBackgroundColor(figure.getForegroundColor());

			final Rectangle nameShadowRect = groupFigure.getNameFigure().getBounds().getExpanded(2, 2);
			final Rectangle mainShadowRect = groupFigure.getMainFigure().getBounds().getExpanded(2, 2);

			final Rectangle clipRect = nameShadowRect.getCopy();
			clipRect.union(mainShadowRect);
			clipRect.width += SHADOW_SIZE;
			clipRect.height += SHADOW_SIZE;
			graphics.setClip(clipRect);

			drawShadowHalo(graphics, nameShadowRect, mainShadowRect);

			drawDropShadow(graphics, nameShadowRect, mainShadowRect);

			graphics.popState();

		}

		private static void drawShadowHalo(final Graphics graphics, final Rectangle nameShadowRect,
				final Rectangle mainShadowRect) {
			graphics.setAlpha(SHADOW_ALPHA);
			drawShadowFigure(graphics, nameShadowRect, mainShadowRect);
			nameShadowRect.shrink(1, 1);
			mainShadowRect.shrink(1, 1);
			graphics.setAlpha(2 * SHADOW_ALPHA);
			drawShadowFigure(graphics, nameShadowRect, mainShadowRect);
		}

		private static void drawDropShadow(final Graphics graphics, final Rectangle topShadowRect,
				final Rectangle middleShadowRect) {
			graphics.setAlpha(SHADOW_ALPHA);
			final double horInc = 0.7; // emulate a roughly 30° shadow angle
			double horI = 0;
			for (int i = 0; i < SHADOW_SIZE; i++) {
				horI += horInc;
				topShadowRect.translate((int) horI, 1);
				middleShadowRect.translate((int) horI, 1);
				drawShadowFigure(graphics, topShadowRect, middleShadowRect);
				if (horI >= 1.0) {
					horI -= 1.0;
				}
			}
		}

		private static void drawShadowFigure(final Graphics graphics, final Rectangle topShadowRect,
				final Rectangle middleShadowRect) {
			graphics.fillRoundRectangle(topShadowRect, SHADOW_CORNER_RADIUS, SHADOW_CORNER_RADIUS);
			graphics.fillRoundRectangle(middleShadowRect, SHADOW_CORNER_RADIUS, SHADOW_CORNER_RADIUS);
		}

	}

}
