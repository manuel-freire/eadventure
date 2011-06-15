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

package es.eucm.eadventure.engine.core.gameobjects;

import java.util.List;

import es.eucm.eadventure.common.model.params.EAdPosition;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.MouseState;
import es.eucm.eadventure.engine.core.guiactions.GUIAction;
import es.eucm.eadventure.engine.core.platform.RuntimeAsset;

/**
 * A game object
 * 
 * @param <T>
 *            The type of element represented by the game object
 */
public interface GameObject<T> {

	/**
	 * Process the action in the graphic interface (click, etc.)
	 * 
	 * @param action
	 *            the action to process
	 * @return {@code true} if he action has been processed by the object
	 */
	boolean processAction(GUIAction action);

	/**
	 * Set the element of the game object
	 * 
	 * @param element
	 */
	void setElement(T element);

	/**
	 * The the draggable element
	 * 
	 * @param mouseState
	 *            The mouse state
	 * @return The game object that is draggable
	 */
	GameObject<?> getDraggableElement(MouseState mouseState);

	/**
	 * Layout out the child game objects of this game objects
	 */
	void doLayout(int offsetX, int offsetY);

	/**
	 * Updates game object. Usually used for animation
	 * 
	 * @param state
	 *            Current game state
	 */
	void update(GameState state);

	/**
	 * @return The position of the game object. The position can be null if the
	 *         object does not have one
	 */
	EAdPosition getPosition();

	/**
	 * Returns the represented element by this game object
	 * 
	 * @return the represented element by this game object
	 */
	T getElement();

	/**
	 * <p>Adds the assets used by this game object to the list and returns it</p>
	 * <p>This method is used to manage memory consumed by assets, allowing the releasing or
	 * pre-caching of assets as required.</p>
	 * 
	 * @param assetList The list where to add the assets
	 * @param allAssets If true all assets are added, if false only required ones are
	 * @return The list of assets with the ones of this game object added
	 */
	List<RuntimeAsset<?>> getAssets(List<RuntimeAsset<?>> assetList, boolean allAssets);


}