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

package es.eucm.ead.engine.gameobjects.effects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.google.inject.Inject;
import es.eucm.ead.engine.game.Game;
import es.eucm.ead.engine.game.ValueMap;
import es.eucm.ead.engine.game.interfaces.GUI;
import es.eucm.ead.model.elements.effects.enums.PhShape;
import es.eucm.ead.model.elements.effects.enums.PhType;
import es.eucm.ead.model.elements.effects.physics.PhysicsEf;
import es.eucm.ead.model.elements.scenes.Scene;
import es.eucm.ead.model.elements.scenes.SceneElement;

public class PhysicsEffectGO extends AbstractEffectGO<PhysicsEf> {

	public static float WORLD_SCALE = 15.0f;

	private World world;

	private float timeStep;

	private int velocityIterations;

	private int positionIterations;

	private GUI gui;

	public static final String VAR_PH_BODY = "ph_body";

	public static final String VAR_PH_WORLD = "ph_world";

	@Inject
	public PhysicsEffectGO(Game game) {
		super(game);
		this.gui = game.getGUI();
	}

	@Override
	public void initialize() {
		super.initialize();

		// doStep true = not simulate inactive bodies
		world = new World(new Vector2(0.0f, 10.0f), true);
		world.setContinuousPhysics(true);
		world.setWarmStarting(true);
		world.setAutoClearForces(true);
		ValueMap valueMap = game.getGameState();
		valueMap.setValue((String) null, VAR_PH_WORLD, world);

		velocityIterations = 24;
		positionIterations = 8;

		for (SceneElement e : effect.getElements()) {
			createBody(world, e, valueMap);
		}

		for (SceneElement e : effect.getJoints()) {
			createBody(world, e, valueMap);
		}

		RevoluteJointDef jd = new RevoluteJointDef();
		jd.collideConnected = false;

		for (int i = 0; i < effect.getJoints().size() - 1; i += 2) {
			SceneElement e1 = effect.getJoints().get(i);
			SceneElement e2 = effect.getJoints().get(i + 1);
			Body b1 = (Body) valueMap.getValue(e1.getId(), VAR_PH_BODY, null);
			Body b2 = (Body) valueMap.getValue(e2.getId(), VAR_PH_BODY, null);
			jd.initialize(b2, b1, new Vector2(b1.getPosition().x, b1
					.getPosition().y));
			world.createJoint(jd);
		}

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		timeStep = delta / 1000.0f;
		world.step(timeStep, velocityIterations, positionIterations);

		Scene scene = (Scene) gui.getScene().getElement();

		if (scene != null) {
			for (SceneElement e : scene.getSceneElements()) {
				ValueMap valueMap = game.getGameState();
				Body b = (Body) valueMap.getValue(e.getId(), VAR_PH_BODY, null);
				if (b != null) {

					valueMap.setValue(e, SceneElement.VAR_X, (float) (b
							.getWorldCenter().x * WORLD_SCALE));
					valueMap.setValue(e, SceneElement.VAR_Y, (float) (b
							.getWorldCenter().y * WORLD_SCALE));
					valueMap.setValue(e, SceneElement.VAR_ROTATION,
							(float) Math.toDegrees(b.getAngle()));
				}
			}
		} else {
			stop();
		}
	}

	public boolean isQueueable() {
		return true;
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	public static void createBody(World world, SceneElement e, ValueMap valueMap) {
		float x = valueMap.getValue(e, SceneElement.VAR_X, 0f) / WORLD_SCALE;
		float y = valueMap.getValue(e, SceneElement.VAR_Y, 0f) / WORLD_SCALE;
		float width = valueMap.getValue(e, SceneElement.VAR_WIDTH, 1)
				/ WORLD_SCALE;
		float height = valueMap.getValue(e, SceneElement.VAR_HEIGHT, 1)
				/ WORLD_SCALE;

		// TODO what if corner is not center?
		PhType phType = valueMap.getValue(e, PhysicsEf.VAR_PH_TYPE,
				PhType.DYNAMIC);
		PhShape phShape = valueMap.getValue(e, PhysicsEf.VAR_PH_SHAPE,
				PhShape.RECTANGULAR);

		Shape s = null;
		switch (phShape) {
		case CIRCULAR:
			s = new CircleShape();
			s.setRadius(width / 2);
			break;
		default:
			s = new PolygonShape();
			((PolygonShape) s).setAsBox(width / 2, height / 2);

		}

		BodyDef bd = new BodyDef();

		switch (phType) {
		case STATIC:
			bd.type = BodyType.StaticBody;
			break;
		case DYNAMIC:
			bd.type = BodyType.DynamicBody;
			break;
		}

		bd.position.set(x, y);
		bd.angle = valueMap.getValue(e, SceneElement.VAR_ROTATION, 0);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = s;
		fixture.density = valueMap.getValue(e, PhysicsEf.VAR_PH_DENSITY, 1f);
		fixture.friction = valueMap.getValue(e, PhysicsEf.VAR_PH_FRICTION, 1f);
		fixture.restitution = valueMap.getValue(e,
				PhysicsEf.VAR_PH_RESTITUTION, 1f);

		Body body = world.createBody(bd);
		body.createFixture(fixture);

		body.resetMassData();

		valueMap.setValue(e.getId(), VAR_PH_BODY, body);
	}

}
