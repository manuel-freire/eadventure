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

package es.eucm.eadventure.editor.impl.conditionspanel;

import java.awt.Component;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import es.eucm.eadventure.common.model.conditions.impl.FlagCondition;
import es.eucm.eadventure.editor.impl.Messages;

/**
 * The renderer class for the FlagCondition type
 * @author Roberto Tornero
 * 
 */
public class FlagConditionRenderer extends ConditionRenderer {


	private static final long serialVersionUID = -8256211110254719972L;

	/**
	 * Constructor for the renderer of the FlagCondition type
	 */	
	public FlagConditionRenderer(ConditionsController controller, FlagCondition fcondition, boolean not){

		super(controller, fcondition, not);	

		try {
			iconLabel.setIcon(new ImageIcon(ImageIO.read(getResourceAsStream("@drawable/conditions/flag16.png"))));

		} catch (IOException e) {
			logger.error(Messages.input_output_exception);
		}		

		Component subjectRender = getSubjectRender();
		if (subjectRender != null)
			renderComponents.add(subjectRender);
		Component verbRender = getVerbRender();
		if (verbRender != null)
			renderComponents.add(verbRender);
		Component directComplementRender = getDirectComplementRender();
		if (directComplementRender != null)
			renderComponents.add(directComplementRender);

		addComponents();

	}

	/**
	 * Private method for adding components to the renderer's panel
	 */
	private void addComponents() {

		// Add items
		//add(iconLabel);
		for(Component component : renderComponents)
			add(component);
		add(buttonsPanel);
	}

	/**
	 * Private method that returns the name of the condition flag as a label component 
	 */
	private Component getSubjectRender() {

		return new JLabel(((FlagCondition)condition).getFlag().getName());
	}

	/**
	 * Private method that returns a label component  
	 */
	private Component getVerbRender() {

		return new JLabel(ConditionMessages.is_message);       
	}

	/**
	 * Private method that returns the value of the flag as a label component 
	 */
	private Component getDirectComplementRender() {

		return new JLabel(""+((FlagCondition)condition).getValue());
	}

}