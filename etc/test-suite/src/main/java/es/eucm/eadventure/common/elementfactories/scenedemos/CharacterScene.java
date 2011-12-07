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

package es.eucm.eadventure.common.elementfactories.scenedemos;

import es.eucm.eadventure.common.elementfactories.EAdElementsFactory;
import es.eucm.eadventure.common.elementfactories.StringFactory;
import es.eucm.eadventure.common.elementfactories.scenedemos.normalguy.NgCommon;
import es.eucm.eadventure.common.interfaces.features.enums.Orientation;
import es.eucm.eadventure.common.model.effects.EAdEffect;
import es.eucm.eadventure.common.model.elements.enums.CommonStates;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.events.EAdSceneElementEvent;
import es.eucm.eadventure.common.model.events.enums.SceneElementEventType;
import es.eucm.eadventure.common.model.events.impl.EAdSceneElementEventImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdKeyEventImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.model.variables.impl.EAdFieldImpl;
import es.eucm.eadventure.common.model.variables.impl.operations.ValueOperation;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl.Corner;
import es.eucm.eadventure.common.predef.model.sceneelements.EAdButton;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.ImageImpl;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.animation.FramesAnimation;
import es.eucm.eadventure.common.resources.assets.drawable.compounds.OrientedDrawable;
import es.eucm.eadventure.common.resources.assets.drawable.compounds.StateDrawable;
import es.eucm.eadventure.common.resources.assets.drawable.compounds.impl.OrientedDrawableImpl;
import es.eucm.eadventure.common.resources.assets.drawable.compounds.impl.StateDrawableImpl;

public class CharacterScene extends EmptyScene {

	private static String standUris[] = new String[] {
			"@drawable/stand_up.png", "@drawable/red_stand_right.png",
			"@drawable/stand_down.png", "@drawable/red_stand_left.png" };

	private static String talkDownUris[] = new String[] {
			"@drawable/stand_down.png", "@drawable/stand_down_talking.png" };

	private static String talkRightUris[] = new String[] {
			"@drawable/red_stand_right.png",
			"@drawable/red_stand_right_talking.png" };

	private static String talkLeftUris[] = new String[] {
			"@drawable/red_stand_left.png",
			"@drawable/red_stand_left_talking.png" };

	private static String walkDownUris[] = new String[] {
			"@drawable/walking_down.png", "@drawable/walking_down_2.png" };

	private static String walkUpUris[] = new String[] {
			"@drawable/walking_up_1.png", "@drawable/walking_up_2.png" };

	private static String walkRightUris[] = new String[] {
			"@drawable/walking_right_1.png", "@drawable/walking_right_2.png" };

	private static String walkLeftUris[] = new String[] {
			"@drawable/walking_left_1.png", "@drawable/walking_left_2.png" };

	public CharacterScene() {

//		EAdBasicSceneElement element = EAdElementsFactory.getInstance()
//				.getSceneElementFactory()
//				.createSceneElement(getStateDrawable(), 100, 300);
		
		NgCommon.init();
		EAdBasicSceneElement element = new EAdBasicSceneElement( NgCommon.getMainCharacter() );
		element.setPosition(Corner.CENTER, 400, 300);
		

		EAdSceneElementEvent event = new EAdSceneElementEventImpl();
		event.setId("makeActive");

		event.addEffect(SceneElementEventType.ADDED_TO_SCENE, EAdElementsFactory
				.getInstance().getEffectFactory().getMakeActiveElement(element));

		element.getEvents().add(event);

		this.getComponents().add(element);

		EAdEffect goUpEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new EAdFieldImpl<Orientation>(element,
								EAdBasicSceneElement.VAR_ORIENTATION),
						new ValueOperation( Orientation.N));
		EAdBasicSceneElement goUpArrow = EAdElementsFactory
				.getInstance()
				.getSceneElementFactory()
				.createSceneElement(new ImageImpl("@drawable/arrow_up.png"),
						100, 210, goUpEffect);
		this.getComponents().add(goUpArrow);

		element.addBehavior(EAdKeyEventImpl.KEY_ARROW_UP, goUpEffect);

		EAdEffect goDownEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new EAdFieldImpl<Orientation>(element,
								EAdBasicSceneElement.VAR_ORIENTATION),
						new ValueOperation(Orientation.S));
		EAdBasicSceneElement goDownArrow = EAdElementsFactory
				.getInstance()
				.getSceneElementFactory()
				.createSceneElement(new ImageImpl("@drawable/arrow_down.png"),
						100, 320, goDownEffect);
		this.getComponents().add(goDownArrow);

		element.addBehavior(EAdKeyEventImpl.KEY_ARROW_DOWN, goDownEffect);

		EAdEffect goLeftEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new EAdFieldImpl<Orientation>(element,
								EAdBasicSceneElement.VAR_ORIENTATION),
						new ValueOperation( Orientation.W));
		EAdBasicSceneElement goLeftArrow = EAdElementsFactory
				.getInstance()
				.getSceneElementFactory()
				.createSceneElement(new ImageImpl("@drawable/arrow_left.png"),
						0, 260, goLeftEffect);
		this.getComponents().add(goLeftArrow);

		element.addBehavior(EAdKeyEventImpl.KEY_ARROW_LEFT, goLeftEffect);

		EAdEffect goRightEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new EAdFieldImpl<Orientation>(element,
								EAdBasicSceneElement.VAR_ORIENTATION),
						new ValueOperation( Orientation.E));
		EAdBasicSceneElement goRightArrow = EAdElementsFactory
				.getInstance()
				.getSceneElementFactory()
				.createSceneElement(new ImageImpl("@drawable/arrow_right.png"),
						200, 260, goRightEffect);
		this.getComponents().add(goRightArrow);

		element.addBehavior(EAdKeyEventImpl.KEY_ARROW_RIGHT, goRightEffect);

		// Change state buttons
		EAdEffect standEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new EAdFieldImpl<String>(element,
								EAdBasicSceneElement.VAR_STATE),
						new ValueOperation(
								CommonStates.EAD_STATE_DEFAULT.toString()));
//		EAdBasicSceneElement stand = EAdElementsFactory.getInstance()
//				.getSceneElementFactory()
//				.createSceneElement("Stand", 300, 10, standEffect);
		StringFactory sf = EAdElementsFactory.getInstance().getStringFactory();
		EAdButton stand = new EAdButton( );
		sf.setString(stand.getLabel(), "Stand");
		stand.addBehavior(EAdMouseEventImpl.MOUSE_LEFT_PRESSED, standEffect);
		stand.setPosition(Corner.CENTER, 600, 250);
		getComponents().add(stand);

		EAdEffect talkEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new EAdFieldImpl<String>(element,
								EAdBasicSceneElement.VAR_STATE),
						new ValueOperation(
								CommonStates.EAD_STATE_TALKING.toString()));
		EAdButton talk = new EAdButton( );
		sf.setString(talk.getLabel(), "Talk");
		talk.addBehavior(EAdMouseEventImpl.MOUSE_LEFT_PRESSED, talkEffect);
		talk.setPosition(Corner.CENTER, 600, 290);
		getComponents().add(talk);

		EAdEffect walkEffect = EAdElementsFactory
				.getInstance()
				.getEffectFactory()
				.getChangeVarValueEffect(
						new EAdFieldImpl<String>(element,
								EAdBasicSceneElement.VAR_STATE),
						new ValueOperation(
								CommonStates.EAD_STATE_WALKING.toString()));
		EAdButton walk = new EAdButton( );
		sf.setString(walk.getLabel(), "Walk");
		walk.addBehavior(EAdMouseEventImpl.MOUSE_LEFT_PRESSED, walkEffect);
		walk.setPosition(Corner.CENTER, 600, 330);
		getComponents().add(walk);
	}

	private static OrientedDrawable getTalkDrawable() {
		OrientedDrawableImpl oriented = new OrientedDrawableImpl();
		oriented.setDrawable(Orientation.N, new ImageImpl(
				"@drawable/stand_up.png"));

		FramesAnimation right = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(talkRightUris, 500);
		oriented.setDrawable(Orientation.E, right);

		FramesAnimation left = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(talkLeftUris, 500);
		oriented.setDrawable(Orientation.W, left);

		FramesAnimation down = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(talkDownUris, 500);
		oriented.setDrawable(Orientation.S, down);

		return oriented;
	}

	private static OrientedDrawable getWalkDrawable() {
		OrientedDrawableImpl oriented = new OrientedDrawableImpl();

		FramesAnimation up = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(walkUpUris, 500);
		oriented.setDrawable(Orientation.N, up);

		FramesAnimation right = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(walkRightUris, 500);
		oriented.setDrawable(Orientation.E, right);

		FramesAnimation left = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(walkLeftUris, 500);
		oriented.setDrawable(Orientation.W, left);

		FramesAnimation down = EAdElementsFactory.getInstance()
				.getDrawableFactory().getFramesAnimation(walkDownUris, 500);
		oriented.setDrawable(Orientation.S, down);

		return oriented;
	}

	public static StateDrawable getStateDrawable() {
		OrientedDrawable stand = EAdElementsFactory.getInstance()
				.getDrawableFactory().getOrientedDrawable(standUris);

		StateDrawableImpl stateDrawable = new StateDrawableImpl();
		stateDrawable.addDrawable(CommonStates.EAD_STATE_DEFAULT.toString(),
				stand);
		stateDrawable.addDrawable(CommonStates.EAD_STATE_TALKING.toString(),
				getTalkDrawable());
		stateDrawable.addDrawable(CommonStates.EAD_STATE_WALKING.toString(),
				getWalkDrawable());

		return stateDrawable;
	}

	@Override
	public String getSceneDescription() {
		return "A scene with a character, with orientation and different states. Press the buttons to control the character.";
	}

	public String getDemoName() {
		return "Character Scene";
	}

}
