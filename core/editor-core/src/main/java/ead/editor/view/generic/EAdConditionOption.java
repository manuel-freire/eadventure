/**
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the
 *    <e-UCM> research group.
 *
 *    Copyright 2005-2010 <e-UCM> research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure, version 2.0
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */

package ead.editor.view.generic;

import ead.common.model.elements.EAdCondition;
import ead.editor.control.CommandManager;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EAdConditionOption extends AbstractOption<EAdCondition> {

	@Override
	public void cleanup(CommandManager manager) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public static enum View {
		DETAILED, BASIC
	}

	private View view;

	public EAdConditionOption(String title, String toolTipText,
			FieldDescriptor<EAdCondition> fieldDescriptor, View view) {
		super(title, toolTipText, fieldDescriptor);
		this.view = view;
	}

	public View getView() {
		return view;
	}

	@Override
	public JComponent getComponent(CommandManager manager) {
		if (getView() == EAdConditionOption.View.DETAILED) {
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createTitledBorder(getTitle()));
			panel.setLayout(new BorderLayout());

			JTextField textField = new JTextField();
			panel.add(textField, BorderLayout.CENTER);
			EAdCondition condition = read(getFieldDescriptor());
			textField.setText(condition.toString());
			textField.setEnabled(false);
			//TODO should update field after condition edition

			JButton button = new JButton("Edit");
			button.setToolTipText(getToolTipText());
			panel.add(button, BorderLayout.EAST);
			//TODO should allow for the edition of conditions

			return panel;
		} else {
			JButton button = new JButton(getTitle());
			//TODO should allow for the edition of conditions
			//TODO should show the icons

			return button;
		}
	}
}
