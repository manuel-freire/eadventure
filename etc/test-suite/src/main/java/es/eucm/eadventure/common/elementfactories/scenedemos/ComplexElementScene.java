package es.eucm.eadventure.common.elementfactories.scenedemos;

import es.eucm.eadventure.common.elementfactories.EAdElementsFactory;
import es.eucm.eadventure.common.model.effects.impl.EAdInterpolationEffect;
import es.eucm.eadventure.common.model.effects.impl.enums.InterpolationLoopType;
import es.eucm.eadventure.common.model.effects.impl.enums.InterpolationType;
import es.eucm.eadventure.common.model.effects.impl.variables.EAdChangeFieldValueEffect;
import es.eucm.eadventure.common.model.elements.impl.EAdBasicSceneElement;
import es.eucm.eadventure.common.model.elements.impl.EAdComplexElementImpl;
import es.eucm.eadventure.common.model.events.EAdSceneElementEvent;
import es.eucm.eadventure.common.model.events.enums.SceneElementEventType;
import es.eucm.eadventure.common.model.events.impl.EAdSceneElementEventImpl;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.model.variables.EAdField;
import es.eucm.eadventure.common.model.variables.impl.EAdFieldImpl;
import es.eucm.eadventure.common.model.variables.impl.operations.ValueOperation;
import es.eucm.eadventure.common.params.fills.impl.EAdColor;
import es.eucm.eadventure.common.params.fills.impl.EAdPaintImpl;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl;
import es.eucm.eadventure.common.params.geom.impl.EAdPositionImpl.Corner;
import es.eucm.eadventure.common.resources.assets.drawable.basics.impl.shapes.RectangleShape;

public class ComplexElementScene extends EmptyScene {

	public ComplexElementScene() {
		RectangleShape rectangle = new RectangleShape(400, 400);
		rectangle.setPaint(EAdPaintImpl.BLACK_ON_WHITE);
		EAdComplexElementImpl complex = new EAdComplexElementImpl(rectangle);
		complex.setId("complex");
		complex.setBounds(400, 400);
		complex.setPosition(new EAdPositionImpl(Corner.CENTER, 400, 300));

		EAdBasicSceneElement e = EAdElementsFactory
				.getInstance()
				.getSceneElementFactory()
				.createSceneElement(new RectangleShape(400, 400, EAdColor.BLUE),
						new RectangleShape(400, 400, EAdColor.RED), 40, 40);

		e.setScale(0.1f);
		e.setVarInitialValue(EAdBasicSceneElement.VAR_ROTATION,
				(float) Math.PI / 6);
		e.setPosition(new EAdPositionImpl(Corner.CENTER, 50, 50));

		complex.getComponents().add(e);

		getComponents().add(complex);

		EAdField<Float> rotation = new EAdFieldImpl<Float>(complex,
				EAdBasicSceneElement.VAR_ROTATION);

		EAdInterpolationEffect effect = new EAdInterpolationEffect(rotation, 0,
				2 * (float) Math.PI, 10000, InterpolationLoopType.RESTART,
				InterpolationType.LINEAR);

		EAdSceneElementEvent event = new EAdSceneElementEventImpl();
		event.addEffect(SceneElementEventType.ADDED_TO_SCENE, effect);

		complex.getEvents().add(event);

		EAdField<Float> rotation2 = new EAdFieldImpl<Float>(e,
				EAdBasicSceneElement.VAR_ROTATION);

		e.addBehavior(EAdMouseEventImpl.MOUSE_RIGHT_CLICK,
				new EAdChangeFieldValueEffect(rotation,
						new ValueOperation((float) 0.1f)));

		EAdInterpolationEffect effect2 = new EAdInterpolationEffect(rotation2,
				0, 2 * (float) Math.PI, 1000, InterpolationLoopType.RESTART,
				InterpolationType.LINEAR);

		EAdSceneElementEvent event2 = new EAdSceneElementEventImpl();
		event2.addEffect(SceneElementEventType.ADDED_TO_SCENE, effect2);

		e.getEvents().add(event2);

		EAdField<Float> scale = new EAdFieldImpl<Float>(complex,
				EAdBasicSceneElement.VAR_SCALE);

		complex.setScale(0.5f);
		EAdInterpolationEffect effect3 = new EAdInterpolationEffect(scale,
				0.0f, 1.5f, 5000, InterpolationLoopType.REVERSE, InterpolationType.LINEAR);

		event2.addEffect(SceneElementEventType.ADDED_TO_SCENE, effect3);

	}

	@Override
	public String getSceneDescription() {
		return "A scene a show complex elements with some animaitons.";
	}

	public String getDemoName() {
		return "Complex Element Scene";
	}

}
