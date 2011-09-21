package es.eucm.eadventure.engine.core.trajectories.impl;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.model.trajectories.impl.SimpleTrajectoryDefinition;
import es.eucm.eadventure.common.params.geom.EAdPosition;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.engine.core.gameobjects.SceneElementGO;
import es.eucm.eadventure.engine.core.trajectories.Path;
import es.eucm.eadventure.engine.core.trajectories.TrajectoryGenerator;
import es.eucm.eadventure.engine.core.trajectories.impl.extra.SimplePathImpl;

public class SimpleTrajectoryGenerator implements
		TrajectoryGenerator<SimpleTrajectoryDefinition> {

	@Override
	public Path getTrajectory(
			SimpleTrajectoryDefinition trajectoryDefinition,
			EAdPosition currentPosition, int x, int y) {

		List<EAdPosition> list = new ArrayList<EAdPosition>();
		if (trajectoryDefinition.isOnlyHoriztonal()) {
			list.add(new EAdPositionImpl(x, currentPosition.getY()));
		} else {
			list.add(new EAdPositionImpl(x, y));
		}

		return new SimplePathImpl(list);
	}

	@Override
	public Path getTrajectory(
			SimpleTrajectoryDefinition trajectoryDefinition,
			EAdPosition currentPosition, int x, int y, SceneElementGO<?> sceneElement) {

		List<EAdPosition> list = new ArrayList<EAdPosition>();
		if (trajectoryDefinition.isOnlyHoriztonal()) {
			list.add(new EAdPositionImpl(x, currentPosition.getY()));
		} else {
			list.add(new EAdPositionImpl(x, y));
		}

		return new SimplePathImpl(list);
	}

	@Override
	public boolean canGetTo(SimpleTrajectoryDefinition trajectoryDefinition,
			EAdPosition currentPosition, SceneElementGO<?> sceneElement) {
		//TODO check barriers?
		return false;
	}

}
