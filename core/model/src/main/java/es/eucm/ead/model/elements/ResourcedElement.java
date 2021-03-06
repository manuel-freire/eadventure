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

import es.eucm.ead.model.interfaces.Param;
import es.eucm.ead.model.interfaces.features.Resourced;
import es.eucm.ead.model.assets.AssetDescriptor;
import es.eucm.ead.model.elements.extra.EAdMap;

/**
 * Abstract {@link BasicElement} implementation, with resources
 */
public abstract class ResourcedElement extends AbstractElementWithBehavior
		implements Resourced {

	public static final String INITIAL_BUNDLE = "initialBundle";
	/**
	 * Resources of the eAdElement
	 */
	@Param
	private EAdMap<EAdMap<AssetDescriptor>> resources;

	public ResourcedElement() {
		super();
		resources = new EAdMap<EAdMap<AssetDescriptor>>();
	}

	@Override
	public void addAsset(String id, AssetDescriptor a) {
		addAsset(INITIAL_BUNDLE, id, a);
	}

	@Override
	public void addAsset(String bundleId, String id, AssetDescriptor a) {
		EAdMap<AssetDescriptor> map = resources.get(bundleId);
		if (map == null) {
			map = new EAdMap<AssetDescriptor>();
			resources.put(bundleId, map);
		}
		map.put(id, a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.common.model.EAdElement#getResources()
	 */
	@Override
	public EAdMap<EAdMap<AssetDescriptor>> getResources() {
		return resources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.common.model.EAdElement#getAsset(java.lang.String)
	 */
	@Override
	public AssetDescriptor getAsset(String id) {
		return getAsset(INITIAL_BUNDLE, id);
	}

	@Override
	public AssetDescriptor getAsset(String bundleId, String id) {
		EAdMap<AssetDescriptor> map = resources.get(bundleId);
		if (map != null) {
			return map.get(id);
		}
		return null;
	}

	public void setResources(EAdMap<EAdMap<AssetDescriptor>> resources) {
		this.resources = resources;
	}

}
