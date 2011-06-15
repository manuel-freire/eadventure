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

package es.eucm.eadventure.common.impl.importer.subimporters.chapter.scene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import es.eucm.eadventure.common.Importer;
import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.common.impl.importer.interfaces.EAdElementFactory;
import es.eucm.eadventure.common.impl.importer.interfaces.ResourceImporter;
import es.eucm.eadventure.common.model.EAdChapter;
import es.eucm.eadventure.common.model.elements.EAdActor;
import es.eucm.eadventure.common.model.elements.EAdActorReference;
import es.eucm.eadventure.common.model.elements.EAdCondition;
import es.eucm.eadventure.common.model.elements.EAdSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdActorReferenceImpl;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdSceneImpl;
import es.eucm.eadventure.common.model.params.EAdPosition;
import es.eucm.eadventure.common.resources.EAdString;
import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.common.resources.assets.drawable.impl.ImageImpl;
import es.eucm.eadventure.common.resources.assets.multimedia.Sound;
import es.eucm.eadventure.common.resources.assets.multimedia.impl.SoundImpl;

/**
 * Scenes importer
 * 
 */
public class SceneImporter implements Importer<Scene, EAdSceneImpl> {

	/**
	 * String handler
	 */
	private StringHandler stringHandler;

	/**
	 * References importer
	 */
	private Importer<ElementReference, EAdActorReference> referencesImporter;

	/**
	 * Resources importer
	 */
	private ResourceImporter resourceImporter;
	
	/**
	 * Exits importer
	 */
	private Importer<Exit, EAdSceneElement> exitsImporter;
	
	/**
	 * Active areas importer
	 */
	private Importer<ActiveArea, EAdSceneElement> activeAreasImporter;

	private EAdElementFactory factory;

	@Inject
	public SceneImporter(StringHandler stringHandler,
			Importer<Conditions, EAdCondition> conditionsImporter,
			ResourceImporter resourceImporter,
			Importer<ElementReference, EAdActorReference> referencesImporter,
			Importer<ActiveArea, EAdSceneElement> activeAreasImporter,
			EAdElementFactory factory, Importer<Exit, EAdSceneElement> exitsImporter) {
		this.stringHandler = stringHandler;
		this.resourceImporter = resourceImporter;
		this.exitsImporter = exitsImporter;
		this.referencesImporter = referencesImporter;
		this.activeAreasImporter = activeAreasImporter;
		this.factory = factory;
	}

	@Override
	public EAdSceneImpl convert(Scene oldScene) {
		EAdChapter chapter = factory.getCurrentChapterModel();
		EAdSceneImpl space = (EAdSceneImpl) factory.getSceneByOldId(oldScene
				.getId());

		importDocumentation(space, oldScene);
		importResources(space, oldScene, chapter);
		importSceneElements(space, oldScene, chapter);

		return space;
	}

	private void importSceneElements(EAdSceneImpl space, Scene oldScene,
			EAdChapter chapter) {
		importExits(space, oldScene.getExits());
		importAciveAreas(space, oldScene.getActiveAreas());
		importBarriers(space, oldScene.getBarriers());
		importTrajectory(space, oldScene.getTrajectory());
		importReferences(space, oldScene.getItemReferences(), chapter);
		importReferences(space, oldScene.getAtrezzoReferences(), chapter);
		importReferences(space, oldScene.getCharacterReferences(), chapter);
		addPlayer(space, oldScene, chapter);

	}

	private void addPlayer(EAdSceneImpl space, Scene oldScene,
			EAdChapter chapter) {
		if (factory.isFirstPerson()) {
			
		} else {
			EAdActor player = factory.getActorByOldId(Player.IDENTIFIER);
			EAdActorReferenceImpl playerReference = new EAdActorReferenceImpl(
					player.getId() + "_reference");
			playerReference.setReferencedActor(player);
			EAdPosition p = new EAdPosition(EAdPosition.Corner.BOTTOM_CENTER,
					oldScene.getPositionX(), oldScene.getPositionY());
			playerReference.setPosition(p);
			playerReference.setScale(oldScene.getPlayerScale());

			space.getSceneElements().add(playerReference);
		}

	}

	private void importTrajectory(EAdSceneImpl space, Trajectory trajectory) {
		// TODO Import trajectory

	}

	private void importBarriers(EAdSceneImpl space, List<Barrier> barriers) {
		// TODO Import barriers

	}

	private void importAciveAreas(EAdSceneImpl space, List<ActiveArea> list) {
		for ( ActiveArea a: list ){
			EAdSceneElement se = activeAreasImporter.convert(a);
			if ( se != null )
				space.getSceneElements().add(se);
		}

	}

	private void importExits(EAdSceneImpl space, List<Exit> list) {
		for ( Exit e: list ){
			EAdSceneElement se = exitsImporter.convert(e);
			if ( se != null )
				space.getSceneElements().add(se);
		}

	}

	private void importReferences(EAdSceneImpl space,
			List<ElementReference> references, EAdChapter chapter) {
		for (ElementReference oldRef : references) {
			EAdActorReference newRef = referencesImporter.convert(oldRef);
			space.getSceneElements().add(newRef);
		}

	}

	private void importResources(EAdSceneImpl space, Scene oldScene,
			EAdChapter chapter) {

		// FIXME Scene.RESOURCE_TYPE_FOREGROUND, Scene.RESOURCE_TYPE_HARDMAP
		// are ignored

		Map<String, String> resourcesStrings = new HashMap<String, String>();
		resourcesStrings.put(Scene.RESOURCE_TYPE_BACKGROUND,
				EAdBasicSceneElement.appearance);

		Map<String, Class<?>> resourcesClasses = new HashMap<String, Class<?>>();
		resourcesClasses.put(Scene.RESOURCE_TYPE_BACKGROUND, ImageImpl.class);

		resourceImporter.importResources(space.getBackground(),
				oldScene.getResources(), resourcesStrings, resourcesClasses);

		for (Resources r : oldScene.getResources()) {

			// FIXME Music is imported to chapter level. So, the chapter will
			// remain with the last sound track appeared in the scenes. Maybe a
			// "ChangeSoundEffect" should be added
			String musicPath = r.getAssetPath(Scene.RESOURCE_TYPE_MUSIC);

			if (musicPath != null) {
				Sound sound = new SoundImpl(musicPath);
				chapter.getResources().addAsset(chapter.getInitialBundle(),
						EAdChapter.music, sound);
			}
		}

	}

	private void importDocumentation(EAdSceneImpl space, Scene oldScene) {
		space.setName(new EAdString(stringHandler.getUniqueId()));
		stringHandler.addString(space.getName(), oldScene.getName());

		space.setDocumentation(new EAdString(stringHandler.getUniqueId()));
		stringHandler.addString(space.getDocumentation(),
				oldScene.getDocumentation());
	}

	@Override
	public boolean equals(Scene oldObject, EAdSceneImpl newObject) {
		// FIXME Implement equals
		return false;
	}

}