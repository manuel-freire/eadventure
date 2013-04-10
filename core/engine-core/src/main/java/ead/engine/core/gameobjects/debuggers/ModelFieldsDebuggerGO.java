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

package ead.engine.core.gameobjects.debuggers;

import java.util.Map;

import com.google.inject.Inject;

import ead.common.model.assets.drawable.basics.Caption;
import ead.common.model.elements.operations.BasicField;
import ead.common.model.elements.scenes.GroupElement;
import ead.common.model.elements.scenes.SceneElement;
import ead.common.model.params.fills.ColorFill;
import ead.common.model.params.paint.EAdPaint;
import ead.common.model.params.variables.EAdVarDef;
import ead.engine.core.assets.AssetHandler;
import ead.engine.core.factories.EventGOFactory;
import ead.engine.core.factories.SceneElementGOFactory;
import ead.engine.core.game.interfaces.GUI;
import ead.engine.core.game.interfaces.Game;
import ead.engine.core.game.interfaces.GameState;
import ead.engine.core.gameobjects.sceneelements.SceneElementGO;

@SuppressWarnings( { "unchecked", "rawtypes" })
public class ModelFieldsDebuggerGO extends SceneElementGO {

	private Game game;

	private int currentLength = -1;

	private SceneElementGO container;

	private EAdPaint bubblePaint = ColorFill.WHITE;

	int x = 10;

	@Inject
	public ModelFieldsDebuggerGO(AssetHandler assetHandler,
			SceneElementGOFactory sceneElementFactory, GUI gui,
			GameState gameState, EventGOFactory eventFactory, Game game) {
		super(assetHandler, sceneElementFactory, gui, gameState, eventFactory);
		this.game = game;
	}

	public void act(float delta) {
		super.act(delta);

		Map<EAdVarDef<?>, Object> values = gameState.getElementVars(game
				.getAdventureModel());
		if (values != null) {
			if (values.size() != currentLength) {
				currentLength = values.size();
				int y = 10;
				int marginY = 20;
				if (container != null) {
					container.remove();
					container.free();
					this.getChildren().clear();
				}
				GroupElement fields = new GroupElement();
				fields.setInitialEnable(false);
				for (EAdVarDef<?> var : values.keySet()) {
					Caption c = new Caption(var.getName() + ": [0]");
					c.setPadding(1);
					c.getOperations().add(
							new BasicField(game.getAdventureModel(), var));
					c.setBubblePaint(bubblePaint);
					SceneElement field = new SceneElement(c);
					field.setPosition(x, y);
					y += marginY;
					fields.addSceneElement(field);
				}
				container = this.addSceneElement(fields);
			}
		}
	}

}
