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

package es.eucm.ead.playground.tests.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import es.eucm.ead.engine.EAdEngine;
import es.eucm.ead.engine.debugger.CommandInterpreter;
import es.eucm.ead.engine.desktop.DesktopGame;
import es.eucm.ead.playground.tests.application.TestGame;
import es.eucm.ead.tools.java.JavaTextFileReader;

import static junit.framework.Assert.assertEquals;

public class RunGameTest {

	private JavaTextFileReader reader;

	public RunGameTest() {
		reader = new JavaTextFileReader();
	}

	public void testGame(String path) {
		final DesktopGame g = new TestGame();
		g.setDebug(true);
		g.setPath(path);
		g.initInjector();
		EAdEngine e = g.getInjector().getInstance(EAdEngine.class);
		Tester tester = new Tester(g.getInjector().getInstance(
				CommandInterpreter.class));
		String commands = reader.read(path + "/test");
		for (String command : commands.split(System.lineSeparator())) {
			String parts[] = command.split(";");
			tester.addCommandTest(new CommandTest(Integer.parseInt(parts[0]),
					parts[1], parts.length == 3 ? parts[2] : null));
		}
		e.addApplicationListener(tester);
		g.start();
	}

	public class Tester implements ApplicationListener {

		private CommandInterpreter interpreter;

		private Array<CommandTest> tests;

		private CommandTest currentTest;

		public Tester(CommandInterpreter interpreter) {
			this.interpreter = interpreter;
			tests = new Array<CommandTest>();
		}

		@Override
		public void create() {
		}

		@Override
		public void resize(int width, int height) {
		}

		@Override
		public void render() {
			if (currentTest == null) {
				if (tests.size > 0) {
					currentTest = tests.removeIndex(0);
				} else {
					Gdx.app.exit();
				}
			}

			if (currentTest != null) {
				currentTest.time -= Gdx.graphics.getDeltaTime() * 1000;
				if (currentTest.time <= 0) {
					if (currentTest.result != null) {
						assertEquals(
								interpreter.interpret(currentTest.command),
								currentTest.result);
					}
					currentTest = null;
					if (tests.size == 0) {
						Gdx.app.exit();
					}
				}
			}

		}

		@Override
		public void pause() {
		}

		@Override
		public void resume() {
		}

		@Override
		public void dispose() {
		}

		public void addCommandTest(CommandTest commandTest) {
			this.tests.add(commandTest);
		}
	}

	public static class CommandTest {
		public float time;
		public String command;
		public String result;

		public CommandTest(int time, String command, String result) {
			this.time = time;
			this.command = command;
			this.result = result;
		}
	}
}
