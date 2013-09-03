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

package es.eucm.ead.tools;

import es.eucm.ead.model.params.text.EAdString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StringHandlerImpl implements StringHandler {

	static private Logger logger = LoggerFactory
			.getLogger(StringHandlerImpl.class);

	private Map<String, Map<EAdString, String>> loadedStrings;

	private Map<EAdString, String> strings;

	private Map<EAdString, String> defaultStrings;

	private String language;

	public StringHandlerImpl() {
		defaultStrings = new HashMap<EAdString, String>();
		loadedStrings = new HashMap<String, Map<EAdString, String>>();
		strings = defaultStrings;
		language = "";
	}

	public void addLanguage(String language) {
		if (!loadedStrings.containsKey(language))
			loadedStrings.put(language, new HashMap<EAdString, String>());
	}

	public void setLanguage(String language) {
		if (!language.equals(this.language)) {
			this.language = language;
			if (loadedStrings.containsKey(language)) {
				strings = loadedStrings.get(language);
			} else {
				strings = loadedStrings.get("");
			}
		}
	}

	@Override
	public String getString(EAdString string) {
		if (string.toString().startsWith(TEXTUAL_STRING_PREFIX)) {
			return string.toString().substring(TEXTUAL_STRING_PREFIX.length());
		}

		String value = strings.get(string);
		if (value == null) {
			value = defaultStrings.get(string);
		}
		return value == null ? string.toString() : value;
	}

	@Override
	public void setString(EAdString eAdString, String string) {
		logger.debug("Add string '{}': '{}'", eAdString.toString(), string);
		if (string == null) {
			logger
					.warn(
							"A null string has been set for {}. It is not necessary to do that.",
							eAdString);
		}
		strings.put(eAdString, string);
	}

	@Override
	public void setStrings(Map<EAdString, String> strings) {
		this.strings = strings;
	}

	@Override
	public void addStrings(Map<EAdString, String> strings) {
		if (strings != null) {
			for (Entry<EAdString, String> entry : strings.entrySet()) {
				this.strings.put(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public Map<EAdString, String> getStrings() {
		return strings;
	}

	public boolean isLanguageLoaded(String language) {
		return loadedStrings.containsKey(language);
	}

	@Override
	public EAdString generateNewString() {
		EAdString s = new EAdString("generatedString"
				+ (int) (Math.random() * 1000000000));
		while (strings.containsKey(s)) {
			s = new EAdString("generatedString"
					+ (int) (Math.random() * 1000000000));
		}
		return s;
	}

	@Override
	public void clear() {
		loadedStrings.clear();
		defaultStrings.clear();
	}
}
