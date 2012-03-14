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

package ead.common.importer.subimporters.chapter.cutscene;

import ead.common.EAdElementImporter;
import ead.common.importer.interfaces.EAdElementFactory;
import ead.common.importer.interfaces.EffectsImporterFactory;
import ead.common.importer.interfaces.ResourceImporter;
import ead.common.importer.subimporters.effects.TriggerSceneImporter;
import ead.common.model.elements.EAdEffect;
import ead.common.model.elements.conditions.EmptyCond;
import ead.common.model.elements.effects.ChangeSceneEf;
import ead.common.model.elements.effects.EffectsMacro;
import ead.common.model.elements.effects.QuitGameEf;
import ead.common.model.elements.effects.TriggerMacroEf;
import ead.common.model.elements.scene.EAdScene;
import ead.common.model.elements.transitions.DisplaceTransition;
import ead.common.model.elements.transitions.EAdTransition;
import ead.common.model.elements.transitions.EmptyTransition;
import ead.common.model.elements.transitions.FadeInTransition;
import ead.common.model.elements.transitions.enums.DisplaceTransitionType;
import ead.common.util.StringHandler;
import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapter.scenes.Slidescene;

public abstract class CutsceneImporter<T extends Cutscene> implements
		EAdElementImporter<T, EAdScene> {

	protected StringHandler stringHandler;

	protected EAdElementFactory factory;

	protected EffectsImporterFactory effectsImporter;

	protected ResourceImporter resourceImporter;

	public CutsceneImporter(StringHandler stringHandler,
			EAdElementFactory factory, EffectsImporterFactory effectsImporter,
			ResourceImporter resourceImporter) {
		this.stringHandler = stringHandler;
		this.factory = factory;
		this.effectsImporter = effectsImporter;
		this.resourceImporter = resourceImporter;
	}

	@Override
	public EAdScene convert(T oldObject, Object newElement) {
		EAdScene scene = (EAdScene) newElement;
		scene.setReturnable(false);
		// Appearance
		importResources(oldObject, scene);
		// Configuration
		importConfiguration(scene, getEndEffect(oldObject));
		// Documentation
		importDocumentation(scene, oldObject);
		return scene;
	}

	private void importDocumentation(EAdScene scene, Cutscene oldScene) {
		stringHandler.setString(scene.getDefinition().getDoc(),
				oldScene.getDocumentation());
		stringHandler.setString(scene.getDefinition().getName(),
				oldScene.getName());
	}

	protected EAdEffect getEndEffect(Cutscene cutscene) {
		EAdScene nextScene = null;
		EAdTransition transition = EmptyTransition.instance();
		switch (cutscene.getNext()) {
		case Slidescene.GOBACK:
			nextScene = null;
			break;
		case Slidescene.ENDCHAPTER:
			if (factory.getOldDataModel().getChapters().size() == 1) {
				return new QuitGameEf( );
			} else {
				// FIXME end chapter if there's more than one chapter
			}
			break;
		case Slidescene.NEWSCENE:
			nextScene = (EAdScene) factory.getElementById(cutscene
					.getTargetId());
			transition = TriggerSceneImporter.getTransition(
					cutscene.getTransitionType(), cutscene.getTransitionTime());
			break;
		}
		ChangeSceneEf changeScene = new ChangeSceneEf();
		changeScene.setNextScene(nextScene);
		changeScene.setTransition(transition);

		EffectsMacro macro = effectsImporter.getMacroEffects(cutscene
				.getEffects());
		if (macro != null) {
			TriggerMacroEf triggerMacro = new TriggerMacroEf();
			triggerMacro.putMacro(macro, EmptyCond.TRUE_EMPTY_CONDITION);
			changeScene.getNextEffects().add(triggerMacro);
		}
		return changeScene;
	}

	protected abstract void importConfiguration(EAdScene scene,
			EAdEffect endEffect);

	protected abstract void importResources(T oldCutscene, EAdScene scene);

	/**
	 * Builds a transition from the type and the time
	 * 
	 * @param type
	 *            transition type
	 * @param time
	 *            transition time
	 * @return
	 */
	public static EAdTransition getTransition(int type, int time) {
		switch (type) {
		case Transition.TYPE_FADEIN:
			return new FadeInTransition(time);
		case Transition.TYPE_HORIZONTAL:
			return new DisplaceTransition(time,
					DisplaceTransitionType.HORIZONTAL, true);
		case Transition.TYPE_VERTICAL:
			return new DisplaceTransition(time,
					DisplaceTransitionType.VERTICAL, true);
		default:
			return EmptyTransition.instance();
		}
	}

}