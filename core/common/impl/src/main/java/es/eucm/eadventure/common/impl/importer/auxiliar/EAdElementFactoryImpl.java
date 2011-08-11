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

package es.eucm.eadventure.common.impl.importer.auxiliar;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import es.eucm.eadventure.common.EAdElementImporter;
import es.eucm.eadventure.common.GenericImporter;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.impl.importer.interfaces.EAdElementFactory;
import es.eucm.eadventure.common.model.EAdElement;
import es.eucm.eadventure.common.model.effects.EAdEffect;
import es.eucm.eadventure.common.model.elements.EAdChapter;
import es.eucm.eadventure.common.model.elements.EAdCondition;
import es.eucm.eadventure.common.model.variables.EAdVar;
import es.eucm.eadventure.common.model.variables.impl.vars.BooleanVar;
import es.eucm.eadventure.common.model.variables.impl.vars.IntegerVar;

@Singleton
public class EAdElementFactoryImpl implements EAdElementFactory {

	private Map<String, Map<String, EAdElement>> elements;
	private Map<String, Map<String, EAdVar<?>>> chapterVars;
	private Map<String, Map<String, EAdCondition>> chapterGlobalStates;
	
	
	private Map<String, Object> oldType;
	
	private EAdChapter currentChapter;
	
	private Chapter currentOldChapter;

	private AdventureData model;

	private EAdElementImporter<Conditions, EAdCondition> conditionImporter;

	private Injector injector;
	
	public static Map<Class<?>, Class<? extends GenericImporter<?, ?>>> importerMap = new HashMap<Class<?>, Class<? extends GenericImporter<?, ?>>>();
	
	@Inject
	public EAdElementFactoryImpl(
			EAdElementImporter<Conditions, EAdCondition> conditionImporter,
			EAdElementImporter<Conversation, EAdEffect> conversationImporter,
			Injector injector) {
		this.conditionImporter = conditionImporter;
		elements = new HashMap<String, Map<String, EAdElement>>();
		chapterVars = new HashMap<String, Map<String, EAdVar<?>>>();
		chapterGlobalStates = new HashMap<String, Map<String, EAdCondition>>();
		oldType = new HashMap<String, Object>();
		this.injector = injector;
	}
	
	@Override
	public void registerOldElement(String id, Object oldElement) {
		oldType.put(id, oldElement);
	}

	@Override
	public EAdElement getElementById(String id) {
		Map<String, EAdElement> chapterElements = getChapterElements();

		EAdElement element = chapterElements.get(id);
		if (element == null) {
			element = getElement(id, oldType.get(id));
		}
		return element;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S> EAdElement getElement(String id, S oldElement) {
		EAdElementImporter<S, EAdElement> importer = (EAdElementImporter<S, EAdElement>) findElementImporter(oldElement.getClass());
		EAdElement newElement = importer.init(oldElement);
		getChapterElements().put(id, newElement);
		newElement = importer.convert(oldElement, newElement);
		return newElement;
	}
	
	private Map<String, EAdElement> getChapterElements() {
		Map<String, EAdElement> chapterElements = elements.get(currentChapter.getId());

		if (chapterElements == null) {
			chapterElements = new HashMap<String, EAdElement>();
			elements.put(currentChapter.getId(), chapterElements);
		}
		
		return chapterElements;
	}
	
	@SuppressWarnings("unchecked")
	private <S, I extends EAdElement> EAdElementImporter<S, I> findElementImporter(Class<S> clazz) {
		return (EAdElementImporter<S, I>) findGenericImporter(clazz);
	}
	
	@SuppressWarnings("unchecked")
	private <S, I> GenericImporter<S, I> findGenericImporter(Class<S> clazz) {
		Class<? extends GenericImporter<?, ?>> importerClass = importerMap.get(clazz);
		GenericImporter<?, ?> genericImporter = injector.getInstance(importerClass);
		return (GenericImporter<S, I>) genericImporter;
	}
	
	
	@Override
	public EAdChapter getCurrentChapterModel() {
		return currentChapter;
	}

	@Override
	public void setCurrentChapterModel(EAdChapter chapter, Chapter oldChapter ) {
		this.currentChapter = chapter;
		this.currentOldChapter = oldChapter;

	}

	/**
	 * Sets the old data model
	 * 
	 * @param data
	 *            the old data model
	 */
	public void setOldDataModel(AdventureData data) {
		this.model = data;
	}

	@Override
	public boolean isFirstPerson() {
		return model.getPlayerMode() == DescriptorData.MODE_PLAYER_1STPERSON;
	}

	public EAdVar<?> getVarByOldId(String id, int type) {
		Map<String, EAdVar<?>> vars = chapterVars.get(currentChapter.getId());

		if (vars == null) {
			vars = new HashMap<String, EAdVar<?>>();
			chapterVars.put(currentChapter.getId(), vars);
		}

		EAdVar<?> var = vars.get(id);
		if (var == null) {
			if (type == Condition.FLAG_CONDITION)
				var = new BooleanVar(id);
			else
				var = new IntegerVar(id);
			vars.put(id, var);
		}
		return var;
	}

	@Override
	public EAdCondition getGlobalStateCondition(String id) {
		Map<String, EAdCondition> globalStates = chapterGlobalStates.get(currentChapter.getId());

		if (globalStates == null) {
			globalStates = new HashMap<String, EAdCondition>();
			chapterGlobalStates.put(currentChapter.getId(), globalStates);
		}

		EAdCondition condition = globalStates.get(id);
		if (condition == null) {
			condition = conditionImporter.init(currentOldChapter.getGlobalState(id));
			condition = conditionImporter.convert(currentOldChapter.getGlobalState(id), condition);
			if ( condition != null )
				globalStates.put(id, condition);
		}
		return condition;
	}

	@Override
	public Chapter getCurrentOldChapterModel() {
		return currentOldChapter;
	}

}
