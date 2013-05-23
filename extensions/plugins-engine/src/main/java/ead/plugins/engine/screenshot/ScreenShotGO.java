package ead.plugins.engine.screenshot;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import ead.engine.core.game.GameImpl;
import ead.engine.core.game.enginefilters.EngineHook;
import ead.engine.core.game.interfaces.GUI;
import ead.engine.core.game.interfaces.Game;
import ead.engine.core.game.interfaces.GameState;
import ead.engine.core.gameobjects.effects.AbstractEffectGO;
import ead.tools.StringHandler;

/**
 * @author anserran Date: 20/05/13 Time: 11:29
 */
public class ScreenShotGO extends AbstractEffectGO<ScreenShotEf> implements
		EngineHook {

	private static final Logger logger = LoggerFactory.getLogger("ScreenShot");

	private static JFileChooser fileChooser;

	private StringHandler stringHandler;

	private File file;

	private boolean finished;

	private Game game;

	@Inject
	public ScreenShotGO(Game game, GameState gameState,
			StringHandler stringHandler) {
		super(gameState);
		this.game = game;
		this.stringHandler = stringHandler;
	}

	public void initialize() {
		super.initialize();
		file = null;
		finished = false;
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser
					.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}

		fileChooser.setDialogTitle(stringHandler.getString(effect
				.getDialogTitle()));

		File f = new File(fileChooser.getCurrentDirectory(), stringHandler
				.getString(effect.getFileName()));
		fileChooser.setSelectedFile(f);

		new Thread(new Runnable() {

			@Override
			public void run() {
				boolean goOn = false;

				while (!goOn) {
					if (fileChooser.showSaveDialog((Component) null) == JFileChooser.APPROVE_OPTION) {
						try {
							File f = fileChooser.getSelectedFile();
							if (!f.getCanonicalPath().endsWith(".png")) {
								f = new File(f.getParent(), f.getName()
										+ ".png");
							}

							if (f.exists()) {
								goOn = (JOptionPane
										.showConfirmDialog(
												null,
												"El archivo ya existe. �Desea sobreescribir?",
												"�Sobreescribir?",
												JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
							} else {
								goOn = true;
							}

							synchronized (ScreenShotGO.this) {
								file = f;
								finished = true;
							}

						} catch (IOException e) {
						}
					}
				}
			}

		}).start();
	}

	public boolean isFinished() {
		synchronized (this) {
			return finished;
		}
	}

	public void finish() {
		game.addHook(GameImpl.HOOK_AFTER_RENDER, this);
		super.finish();
	}

	public boolean isQueueable() {
		return true;
	}

	@Override
	public int compareTo(EngineHook arg0) {
		return 0;
	}

	@Override
	public void execute(Game game, GameState gameState, GUI gui) {
		game.removeHook(GameImpl.HOOK_AFTER_RENDER, this);
		try {
			ScreenshotSaver.saveScreenshot(file);
		} catch (IOException e) {
			logger.error("{}", e);
		}
	}

}
