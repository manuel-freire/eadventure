package es.eucm.eadventure.common.impl.importer.subimporters.chapter.scene;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.eucm.eadventure.common.EAdElementImporter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.common.model.effects.impl.variables.EAdChangeFieldValueEffect;
import es.eucm.eadventure.common.model.elements.EAdCondition;
import es.eucm.eadventure.common.model.elements.EAdSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdSceneElementDefImpl;
import es.eucm.eadventure.common.model.events.EAdConditionEvent;
import es.eucm.eadventure.common.model.events.enums.ConditionedEventType;
import es.eucm.eadventure.common.model.events.impl.EAdConditionEventImpl;
import es.eucm.eadventure.common.model.trajectories.impl.NodeTrajectoryDefinition;
import es.eucm.eadventure.common.model.variables.EAdField;
import es.eucm.eadventure.common.model.variables.impl.EAdFieldImpl;
import es.eucm.eadventure.common.model.variables.impl.operations.BooleanOperation;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl.Corner;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.RectangleShape;

@Singleton
public class BarrierImporter implements
		EAdElementImporter<Barrier, EAdSceneElement> {

	private EAdElementImporter<Conditions, EAdCondition> conditionsImporter;

	@Inject
	public BarrierImporter(
			EAdElementImporter<Conditions, EAdCondition> conditionsImporter) {
		this.conditionsImporter = conditionsImporter;
	}

	@Override
	public EAdSceneElement init(Barrier oldObject) {
		EAdSceneElement element = new EAdBasicSceneElement();
		element.setId(oldObject.getId());
		return element;
	}

	@Override
	public EAdSceneElement convert(Barrier oldObject, Object newElement) {
		EAdBasicSceneElement barrier = (EAdBasicSceneElement) newElement;

		if (oldObject.getConditions() != null) {
			EAdCondition condition = conditionsImporter.init(oldObject
					.getConditions());
			condition = conditionsImporter.convert(oldObject.getConditions(),
					condition);
			EAdConditionEvent event = new EAdConditionEventImpl();
			event.setId("barrierCondition");
			event.setCondition(condition);
			EAdField<Boolean> barrierOn = new EAdFieldImpl<Boolean>(barrier,
					NodeTrajectoryDefinition.VAR_BARRIER_ON);
			event.addEffect(ConditionedEventType.CONDITIONS_MET,
					new EAdChangeFieldValueEffect(barrierOn,
							BooleanOperation.TRUE_OP));
			event.addEffect(ConditionedEventType.CONDITIONS_UNMET,
					new EAdChangeFieldValueEffect(barrierOn,
							BooleanOperation.FALSE_OP));

			barrier.getEvents().add(event);
		}

		RectangleShape rectangle = new RectangleShape( oldObject.getWidth(), oldObject.getHeight() );
		barrier.getDefinition()
				.getResources()
				.addAsset(barrier.getDefinition().getInitialBundle(),
						EAdSceneElementDefImpl.appearance, rectangle);
		barrier.setPosition(new EAdPositionImpl(Corner.TOP_LEFT, oldObject
				.getX(), oldObject.getY()));

		return barrier;
	}

}
