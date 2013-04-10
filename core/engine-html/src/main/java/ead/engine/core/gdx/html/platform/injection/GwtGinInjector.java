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

package ead.engine.core.gdx.html.platform.injection;

import com.badlogic.gdx.ApplicationListener;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import ead.engine.core.assets.drawables.RuntimeCaption;
import ead.engine.core.assets.drawables.RuntimeComposedDrawable;
import ead.engine.core.assets.drawables.RuntimeFilteredDrawable;
import ead.engine.core.assets.drawables.RuntimeFramesAnimation;
import ead.engine.core.assets.drawables.RuntimeImage;
import ead.engine.core.assets.drawables.RuntimeNinePatchImage;
import ead.engine.core.assets.drawables.RuntimeStateDrawable;
import ead.engine.core.assets.drawables.shapes.GdxBezierShape;
import ead.engine.core.assets.drawables.shapes.GdxCircleShape;
import ead.engine.core.assets.drawables.shapes.GdxRectangleShape;
import ead.engine.core.assets.fonts.RuntimeFont;
import ead.engine.core.assets.multimedia.RuntimeSound;
import ead.engine.core.game.interfaces.Game;
import ead.engine.core.gameobjects.debuggers.FieldsDebuggerGO;
import ead.engine.core.gameobjects.debuggers.GhostDebuggerGO;
import ead.engine.core.gameobjects.debuggers.ProfilerDebuggerGO;
import ead.engine.core.gameobjects.debuggers.TrajectoryDebuggerGO;
import ead.engine.core.gameobjects.effects.ActorActionsGO;
import ead.engine.core.gameobjects.effects.AddActorReferenceGO;
import ead.engine.core.gameobjects.effects.AddChildGO;
import ead.engine.core.gameobjects.effects.ApplyForceGO;
import ead.engine.core.gameobjects.effects.ChangeFieldGO;
import ead.engine.core.gameobjects.effects.ChangeSceneGO;
import ead.engine.core.gameobjects.effects.HighlightSceneElementGO;
import ead.engine.core.gameobjects.effects.InterpolationGO;
import ead.engine.core.gameobjects.effects.ModifyHudGO;
import ead.engine.core.gameobjects.effects.ModifyInventoryGO;
import ead.engine.core.gameobjects.effects.MoveSceneElementGO;
import ead.engine.core.gameobjects.effects.PhysicsEffectGO;
import ead.engine.core.gameobjects.effects.PlaySoundGO;
import ead.engine.core.gameobjects.effects.QuestionGO;
import ead.engine.core.gameobjects.effects.QuitGameGO;
import ead.engine.core.gameobjects.effects.RandomGO;
import ead.engine.core.gameobjects.effects.RemoveGO;
import ead.engine.core.gameobjects.effects.ShowSceneElementGO;
import ead.engine.core.gameobjects.effects.SpeakGO;
import ead.engine.core.gameobjects.effects.ToggleSoundGO;
import ead.engine.core.gameobjects.effects.TriggerMacroGO;
import ead.engine.core.gameobjects.effects.WaitGO;
import ead.engine.core.gameobjects.effects.WaitUntilGO;
import ead.engine.core.gameobjects.effects.sceneelement.ChangeColorGO;
import ead.engine.core.gameobjects.events.ConditionEvGO;
import ead.engine.core.gameobjects.events.SceneElementEvGO;
import ead.engine.core.gameobjects.events.TimedEvGO;
import ead.engine.core.gameobjects.events.WatchFieldEvGO;
import ead.engine.core.gameobjects.sceneelements.GhostElementGO;
import ead.engine.core.gameobjects.sceneelements.GroupElementGO;
import ead.engine.core.gameobjects.sceneelements.SceneElementGO;
import ead.engine.core.gameobjects.sceneelements.SceneGO;
import ead.engine.core.gameobjects.sceneelements.VideoSceneGO;
import ead.engine.core.gameobjects.sceneelements.huds.MouseHudGO;
import ead.engine.core.gameobjects.sceneelements.transitions.DisplaceTransitionGO;
import ead.engine.core.gameobjects.sceneelements.transitions.EmptyTransitionGO;
import ead.engine.core.gameobjects.sceneelements.transitions.FadeInTransitionGO;
import ead.engine.core.gameobjects.sceneelements.transitions.MaskTransitionGO;
import ead.engine.core.gameobjects.sceneelements.transitions.ScaleTransitionGO;
import ead.engine.core.gameobjects.trajectories.dijkstra.NodeTrajectoryGO;
import ead.engine.core.gameobjects.trajectories.polygon.PolygonTrajectoryGO;
import ead.engine.core.gameobjects.trajectories.simple.SimpleTrajectoryGO;
import ead.engine.core.gameobjects.widgets.TextAreaGO;
import ead.engine.core.gdx.html.platform.module.GwtModule;
import ead.plugins.engine.bubbledescription.BubbleNameGO;
import ead.tools.GenericInjector;
import ead.tools.gwt.GWTToolsModule;

@GinModules( { GwtModule.class, GWTToolsModule.class })
public interface GwtGinInjector extends Ginjector {

	// Platform
	ApplicationListener getEngine();

	GenericInjector getGenericInjector();

	Game getGame();

	// Effects
	HighlightSceneElementGO getHighlightSceneElementGO();

	SpeakGO getSpeakGO();

	ModifyHudGO getModifyHudGO();

	MoveSceneElementGO getMoveSceneElementGO();

	ActorActionsGO getActorActionsGO();

	ModifyInventoryGO getModifyInventoryGO();

	AddChildGO getAddChildGO();

	WaitUntilGO getWaitUntilGO();

	ShowSceneElementGO getShowSceneElementGO();

	ChangeSceneGO getChangeSceneGO();

	QuestionGO getQuestionGO();

	RandomGO getRandomGO();

	PlaySoundGO getPlaySoundGO();

	RemoveGO getRemoveGO();

	ChangeFieldGO getChangeFieldGO();

	AddActorReferenceGO getAddActorReferenceGO();

	WaitGO getWaitGO();

	PhysicsEffectGO getPhysicsEffectGO();

	ChangeColorGO getChangeColorGO();

	QuitGameGO getQuitGameGO();

	InterpolationGO getInterpolationGO();

	ApplyForceGO getApplyForceGO();

	ToggleSoundGO getToggleSoundGO();

	TriggerMacroGO getTriggerMacroGO();

	// Events
	TimedEvGO getTimedEvGO();

	SceneElementEvGO getSceneElementEvGO();

	WatchFieldEvGO getWatchFieldEvGO();

	ConditionEvGO getConditionEvGO();

	// SeneElements
	TextAreaGO getTextAreaGO();

	DisplaceTransitionGO getDisplaceTransitionGO();

	VideoSceneGO getVideoSceneGO();

	GroupElementGO getGroupElementGO();

	SceneGO getSceneGO();

	FadeInTransitionGO getFadeInTransitionGO();

	FieldsDebuggerGO getFieldsDebuggerGO();

	EmptyTransitionGO getEmptyTransitionGO();

	MaskTransitionGO getMaskTransitionGO();

	GhostElementGO getGhostElementGO();

	ScaleTransitionGO getScaleTransitionGO();

	SceneElementGO getSceneElementGO();

	GhostDebuggerGO getGhostDebuggerGO();

	TrajectoryDebuggerGO getTrajectoryDebuggerGO();

	ProfilerDebuggerGO getProfilerDebuggerGO();

	MouseHudGO getMouseHudGO();

	// Trajectories
	NodeTrajectoryGO getNodeTrajectoryGO();

	SimpleTrajectoryGO getSimpleTrajectoryGO();

	PolygonTrajectoryGO getPolygonTrajectoryGO();

	// Assets
	GdxCircleShape getGdxCircleShape();

	RuntimeStateDrawable getRuntimeStateDrawable();

	GdxRectangleShape getGdxRectangleShape();

	RuntimeFramesAnimation getRuntimeFramesAnimation();

	RuntimeFont getRuntimeFont();

	RuntimeFilteredDrawable getRuntimeFilteredDrawable();

	GdxBezierShape getGdxBezierShape();

	RuntimeSound getRuntimeSound();

	RuntimeNinePatchImage getRuntimeNinePatchImage();

	RuntimeImage getRuntimeImage();

	RuntimeComposedDrawable getRuntimeComposedDrawable();

	RuntimeCaption getRuntimeCaption();

	// Plugins
	// FIXME This CAN NOT be here
	BubbleNameGO getBubbleNameGO();

}
