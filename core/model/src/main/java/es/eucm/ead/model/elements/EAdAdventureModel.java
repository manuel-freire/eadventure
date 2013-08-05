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

package es.eucm.ead.model.elements;

import java.util.Map;

import es.eucm.ead.model.interfaces.features.Evented;
import es.eucm.ead.model.interfaces.features.Variabled;
import es.eucm.ead.model.elements.extra.EAdList;
import es.eucm.ead.model.params.text.EAdString;

/**
 * Interface for an eAdventure game static model.
 */
public interface EAdAdventureModel extends EAdElement, Variabled, Evented {

	/**
	 * Default width for games
	 */
	public static final int DEFAULT_WIDTH = 800;

	/**
	 * Default height for games
	 */
	public static final int DEFAULT_HEIGHT = 600;

	/**
	 * Returns the chapters of the adventures.
	 * 
	 * @return the chapters in the adventure
	 */
	EAdList<EAdChapter> getChapters();

	/**
	 * @return the description of the adventure
	 */
	EAdString getDescription();

	/**
	 * @return the title of the adventure
	 */
	EAdString getTitle();

	/**
	 * Returns the width for this game
	 * 
	 * @return
	 */
	int getGameWidth();

	/**
	 * Returns the height for this game
	 * 
	 * @return
	 */
	int getGameHeight();

	/**
	 * Returns properties for this adventure (version of the editor, tracker
	 * parameters...). These properties are usually read from the ead.properties
	 * file
	 * 
	 * @return
	 */
	Map<String, String> getProperties();

	/**
	 * Sets the value for a property
	 * 
	 * @param key
	 *            property key
	 * @param value
	 *            property value
	 */
	void setProperty(String key, String value);

}