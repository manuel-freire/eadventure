package es.eucm.eadventure.common.model.events;


/**
 * An event that it's executed at regular intervals by a scene element
 * 
 * 
 * 
 */
public interface EAdSceneElementTimedEvent extends EAdEvent {
	
	public enum SceneElementTimedEventType {
		START_TIME,
		END_TIME;
	}
	
	/**
	 * Returns time between executions
	 * @return
	 */
	int getTime();

}
