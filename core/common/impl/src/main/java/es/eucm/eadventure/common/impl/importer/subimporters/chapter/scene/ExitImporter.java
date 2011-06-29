package es.eucm.eadventure.common.impl.importer.subimporters.chapter.scene;

import java.awt.Point;

import com.google.inject.Inject;

import es.eucm.eadventure.common.EAdElementImporter;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.impl.importer.interfaces.EAdElementFactory;
import es.eucm.eadventure.common.model.conditions.impl.EmptyCondition;
import es.eucm.eadventure.common.model.effects.impl.EAdChangeScene;
import es.eucm.eadventure.common.model.effects.impl.variables.EAdChangeVarValueEffect;
import es.eucm.eadventure.common.model.elements.EAdCondition;
import es.eucm.eadventure.common.model.elements.EAdScene;
import es.eucm.eadventure.common.model.elements.EAdSceneElement;
import es.eucm.eadventure.common.model.elements.EAdTransition;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.events.EAdConditionEvent;
import es.eucm.eadventure.common.model.events.impl.EAdConditionEventImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.model.params.EAdBorderedColor;
import es.eucm.eadventure.common.model.params.EAdPosition;
import es.eucm.eadventure.common.model.variables.impl.operations.BooleanOperation;
import es.eucm.eadventure.common.resources.assets.drawable.Shape;
import es.eucm.eadventure.common.resources.assets.drawable.impl.BezierShape;
import es.eucm.eadventure.common.resources.assets.drawable.impl.RectangleShape;

public class ExitImporter implements EAdElementImporter<Exit, EAdSceneElement> {

	private static int ID_GENERATOR = 0;
	private EAdElementImporter<Conditions, EAdCondition> conditionsImporter;
	private EAdElementFactory factory;

	@Inject
	public ExitImporter(EAdElementImporter<Conditions, EAdCondition> conditionsImporter,
			EAdElementFactory factory) {
		this.conditionsImporter = conditionsImporter;
		this.factory = factory;
	}

	public EAdSceneElement init(Exit oldObject) {
		EAdBasicSceneElement newExit = new EAdBasicSceneElement("exit" + ID_GENERATOR++);
		return newExit;
	}
	@Override
	public EAdSceneElement convert(Exit oldObject, Object object) {
		EAdBasicSceneElement newExit = (EAdBasicSceneElement) object;

		Shape shape = null;

		if (oldObject.isRectangular()) {
			int xLeft = oldObject.getX0();
			int xRight = oldObject.getX1();
			int yTop = oldObject.getY0();
			int yBottom = oldObject.getY1();
			shape = new RectangleShape(yBottom - yTop, xRight - xLeft);
			newExit.setPosition(new EAdPosition(EAdPosition.Corner.TOP_LEFT,
					xLeft, yTop));
			// FIXME deleted when exits were working
			((RectangleShape) shape).setColor(EAdBorderedColor.BLACK_ON_WHITE);
		} else {
			shape = null;
			int i = 0;
			for (Point p : oldObject.getPoints()) {
				if ( i == 0 )
					shape = new BezierShape(p.x, p.y);
				else
					((BezierShape) shape).lineTo(p.x, p.y);
			}
			((BezierShape) shape).close();
			// FIXME deleted when exits were working
			((BezierShape) shape).setColor(EAdBorderedColor.BLACK_ON_WHITE);
			newExit.setPosition(new EAdPosition(EAdPosition.Corner.TOP_LEFT, 0,
					0));
		}

		newExit.getResources().addAsset(newExit.getInitialBundle(),
				EAdBasicSceneElement.appearance, shape);

		EAdScene scene = (EAdScene) factory.getElementById(oldObject.getNextSceneId());
		EAdChangeScene effect = new EAdChangeScene("change_screen_"
				+ newExit.getId(), scene, EAdTransition.BASIC);

		newExit.addBehavior(EAdMouseEventImpl.MOUSE_LEFT_CLICK, effect);
		
		// Event to show (or not) the exit
		EAdCondition condition = conditionsImporter.init(oldObject
				.getConditions());
		condition = conditionsImporter.convert(oldObject
				.getConditions(), condition);
		
		EAdConditionEventImpl event = new EAdConditionEventImpl( newExit.getId() + "_VisibleEvent" );
		event.setCondition(condition);
		
		EAdChangeVarValueEffect visibleVar = new EAdChangeVarValueEffect( newExit.getId() + "_visibleEffect" );
		visibleVar.addVar(newExit.visibleVar());
		BooleanOperation op = new BooleanOperation( "booleanOpTrue" );
		op.setCondition(EmptyCondition.TRUE_EMPTY_CONDITION);
		visibleVar.setOperation( op );
		event.addEffect(EAdConditionEvent.ConditionedEvent.CONDITIONS_MET, visibleVar);
		
		EAdChangeVarValueEffect notVisibleVar = new EAdChangeVarValueEffect( newExit.getId() + "_notVisibleEffect" );
		notVisibleVar.addVar(newExit.visibleVar());
		op = new BooleanOperation( "booleanOpFalse" );
		op.setCondition(EmptyCondition.FALSE_EMPTY_CONDITION);
		notVisibleVar.setOperation( op );
		event.addEffect(EAdConditionEvent.ConditionedEvent.CONDITIONS_UNMET, notVisibleVar);
		
		newExit.getEvents().add(event);

		return newExit;
	}

}
