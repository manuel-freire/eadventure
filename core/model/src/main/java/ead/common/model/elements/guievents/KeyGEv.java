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

package ead.common.model.elements.guievents;

import ead.common.interfaces.Element;
import ead.common.interfaces.Param;
import ead.common.model.elements.BasicElement;
import ead.common.model.elements.guievents.EAdGUIEvent;
import ead.common.model.elements.guievents.enums.KeyGEvCode;
import ead.common.model.elements.guievents.enums.KeyEventType;

@Element
public class KeyGEv extends BasicElement implements EAdGUIEvent {

	public static final KeyGEv KEY_ARROW_DOWN = new KeyGEv(
			KeyEventType.KEY_PRESSED, KeyGEvCode.DOWN);
	public static final KeyGEv KEY_ARROW_LEFT = new KeyGEv(
			KeyEventType.KEY_PRESSED, KeyGEvCode.LEFT);
	public static final KeyGEv KEY_ARROW_RIGHT = new KeyGEv(
			KeyEventType.KEY_PRESSED, KeyGEvCode.RIGHT);
	public static final KeyGEv KEY_ARROW_UP = new KeyGEv(
			KeyEventType.KEY_PRESSED, KeyGEvCode.UP);
	public static final KeyGEv KEY_ESC = new KeyGEv(KeyEventType.KEY_PRESSED,
			KeyGEvCode.ESCAPE);

	@Param("type")
	private KeyEventType type;

	@Param("keyCode")
	private KeyGEvCode keyCode;

	public KeyGEv() {

	}

	public KeyGEv(KeyEventType type, KeyGEvCode keyCode) {
		super();
		this.type = type;
		this.keyCode = keyCode;
	}

	public KeyEventType getType() {
		return type;
	}

	public void setType(KeyEventType type) {
		this.type = type;
	}

	public KeyGEvCode getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(KeyGEvCode keyCode) {
		this.keyCode = keyCode;
	}

	public String toString() {
		return type.toString() + "_" + keyCode.toString();
	}

}
