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

package ead.engine.core.factories;

import ead.common.model.elements.scenes.EAdSceneElement;
import ead.engine.core.gameobjects.sceneelements.SceneElementGO;

public interface SceneElementGOFactory extends
		GameObjectFactory<EAdSceneElement, SceneElementGO> {

	/**
	 * Gets and element represented by the given id, if the element is already
	 * contained in the cache. Returns null if there is no element with such id
	 * 
	 * @param id
	 */
	SceneElementGO get(String id);

	/**
	 * Removes an element from the cache
	 * 
	 * @param element
	 *            the element to be removed
	 */
	void remove(EAdSceneElement element);

	/**
	 * Remove all elements from the cache
	 */
	void clean();

}
