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

package ead.engine.core.platform.assets;

import com.google.inject.Inject;

import ead.common.resources.assets.drawable.Drawable;
import ead.common.resources.assets.drawable.basics.ImageImpl;
import ead.engine.core.platform.AssetHandler;
import ead.engine.core.platform.DrawableAsset;

/**
 * Represents a runtime engine image, associated with an {@link AssetDescritpor}
 * 
 */
public abstract class RuntimeImage<GraphicContext> extends AbstractRuntimeAsset<ImageImpl> implements DrawableAsset<ImageImpl, GraphicContext> {

	/**
	 * The asset handler
	 */
	protected AssetHandler assetHandler;
	
	@Inject 
	public RuntimeImage(AssetHandler assetHandler ){
		this.assetHandler = assetHandler;
	}

	@Override
	public void update() {

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S extends Drawable> DrawableAsset<S, GraphicContext> getDrawable() {
		return (DrawableAsset<S, GraphicContext>) this;
	}
		
	public boolean contains( int x, int y ){
		// TODO process image alpha
		return x > 0 && y > 0 && x < getWidth() && y < getHeight();
	}

}