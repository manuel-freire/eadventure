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

package es.eucm.ead.model.interfaces.features.enums;

import com.gwtent.reflection.client.Reflectable;

/**
 * 
 * Enum representing all eight possible orientation in eAdventure
 * 
 */
@Reflectable
public enum Orientation {
	/**
	 * North
	 */
	N,

	/**
	 * Northeast
	 */
	NE,
	/**
	 * East
	 */
	E,
	/**
	 * Southeast
	 */
	SE,
	/**
	 * South
	 */
	S,
	/**
	 * Southwest
	 */
	SW,
	/**
	 * West
	 */
	W,
	/**
	 * Northwest
	 */
	NW;

	public static Orientation parse(String o) {
		if (o.equals("N")) {
			return N;
		} else if (o.equals("S")) {
			return S;
		} else if (o.equals("W")) {
			return W;
		} else if (o.equals("E")) {
			return E;
		} else if (o.equals("NW")) {
			return NW;
		} else if (o.equals("NE")) {
			return NE;
		} else if (o.equals("SW")) {
			return SW;
		} else if (o.equals("SE")) {
			return SE;
		}
		return S;
	}
};
