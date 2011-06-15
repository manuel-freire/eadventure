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

package es.eucm.eadventure.engine.core.platform.impl;

import com.google.inject.Singleton;

import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.platform.PlatformConfiguration;

/**
 * <p>
 * Desktop implementation of the platform configuration.
 * </p>
 * 
 * @see PlatformConfiguration
 */
@Singleton
public class DesktopPlatformConfiguration implements PlatformConfiguration {

	/**
	 * Default window width for desktop games
	 */
	public static final int DEFAULT_WIDTH = 480;

	/**
	 * Default window height for desktop games
	 */
	public static final int DEFAULT_HEIGHT = 360;

	/**
	 * The width of the game window
	 */
	private int width = DEFAULT_WIDTH;

	/**
	 * The height of the game window
	 */
	private int height = DEFAULT_HEIGHT;

	/**
	 * Full screen games are scaled to cover the whole screen real state
	 */
	private boolean fullscreen = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.PlatformConfiguration#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.PlatformConfiguration#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.PlatformConfiguration#setWidth
	 * (int)
	 */
	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.PlatformConfiguration#setHeight
	 * (int)
	 */
	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.PlatformConfiguration#isFullscreen
	 * ()
	 */
	@Override
	public boolean isFullscreen() {
		return fullscreen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.PlatformConfiguration#getScale()
	 */
	@Override
	public double getScale() {
		return (double) getHeight() / GUI.VIRTUAL_HEIGHT;
	}

}