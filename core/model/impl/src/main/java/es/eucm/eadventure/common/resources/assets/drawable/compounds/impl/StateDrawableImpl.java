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

package es.eucm.eadventure.common.resources.assets.drawable.compounds.impl;

import java.util.Set;

import es.eucm.eadventure.common.model.extra.EAdMap;
import es.eucm.eadventure.common.model.extra.impl.EAdMapImpl;
import es.eucm.eadventure.common.resources.assets.drawable.Drawable;
import es.eucm.eadventure.common.resources.assets.drawable.compounds.StateDrawable;

/**
 * Basic implementation for a {@link StateDrawable}
 * 
 * @author anserran
 * 
 */
public class StateDrawableImpl implements StateDrawable {

	private EAdMap<String, Drawable> drawables;

	/**
	 * Constructs an empty bundle of drawables
	 */
	public StateDrawableImpl() {
		drawables = new EAdMapImpl<String, Drawable>(String.class,
				Drawable.class);
	}

	@Override
	public boolean addDrawable(String stateName, Drawable drawable) {
		if (drawables.containsKey(stateName))
			return false;
		else {
			drawables.put(stateName, drawable);
			return true;
		}
	}

	@Override
	public Set<String> getStates() {
		return drawables.keySet();
	}

	@Override
	public Drawable getDrawable(String stateName) {
		return drawables.get(stateName);
	}

}
