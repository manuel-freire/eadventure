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

package es.eucm.eadventure.engine.core.platform.impl;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.eucm.eadventure.common.resources.assets.drawable.basics.Image;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.KeyboardState;
import es.eucm.eadventure.engine.core.MouseState;
import es.eucm.eadventure.engine.core.ValueMap;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectFactory;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectManager;
import es.eucm.eadventure.engine.core.gameobjects.huds.BasicHUD;
import es.eucm.eadventure.engine.core.guiactions.KeyAction;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.platform.GraphicRendererFactory;
import es.eucm.eadventure.engine.core.platform.PlatformConfiguration;
import es.eucm.eadventure.engine.core.platform.RuntimeAsset;
import es.eucm.eadventure.engine.core.platform.assets.impl.DesktopEngineImage;
import es.eucm.eadventure.engine.core.platform.impl.extra.DesktopInputListener;
import es.eucm.eadventure.utils.swing.SwingExceptionHandler;
import es.eucm.eadventure.utils.swing.SwingUtilities;

/**
 * <p>
 * Desktop implementation of the {@link AbstractGUI} uses awt {@link Graphics2D}
 * as a graphic framework, and works within a Swing {@link JFrame}.
 * </p>
 */
@Singleton
public class DesktopGUI extends AbstractGUI<Graphics2D> implements GUI {

	/**
	 * The class logger
	 */
	private static final Logger logger = Logger.getLogger("DesktopGUI");

	/**
	 * The {@code JFrame} where the game is represented
	 */
	private JFrame frame;

	/**
	 * The {@code Canvas} object where the actual game is drawn
	 */
	private Canvas canvas;

	/**
	 * AWT Robot, used to move the mouse in the screen
	 */
	private Robot robot;

	/**
	 * Represent how many pixels the mouse moves when the arrow keys are pressed
	 */
	private int MOUSE_MOVE = 20;

	/**
	 * A stack to store cursors
	 */
	private Stack<Cursor> cursorsStack;

	/**
	 * Asset handler
	 */
	private AssetHandler assetHandler;

	private Object currentComponent;

	@Inject
	public DesktopGUI(PlatformConfiguration platformConfiguration,
			GraphicRendererFactory<?> assetRendererFactory,
			GameObjectManager gameObjectManager, MouseState mouseState,
			KeyboardState keyboardState, BasicHUD basicDesktopHUD,
			ValueMap valueMap, GameState gameState,
			GameObjectFactory gameObjectFactory, AssetHandler assetHandler) {
		super(platformConfiguration, assetRendererFactory, gameObjectManager,
				mouseState, keyboardState, valueMap, gameState,
				gameObjectFactory);
		this.gameObjects.addHUD(basicDesktopHUD);
		basicDesktopHUD.setGUI(this);
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
		}
		cursorsStack = new Stack<Cursor>();
		this.assetHandler = assetHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.GUI#showSpecialResource(java.
	 * lang.Object, int, int, boolean)
	 */
	@Override
	public void showSpecialResource(final Object resource, int x, int y,
			boolean fullscreen) {
		if (this.currentComponent == resource)
			return;
		if (this.currentComponent != null) {
			SwingUtilities.doInEDTNow(new Runnable() {
				@Override
				public void run() {
					frame.remove((Component) currentComponent);
				}
			});
			currentComponent = null;
		}
		if (this.currentComponent == null) {
			if (resource == null) {
				SwingUtilities.doInEDTNow(new Runnable() {
					@Override
					public void run() {
						frame.add(canvas);
					}
				});
			} else {
				SwingUtilities.doInEDTNow(new Runnable() {
					@Override
					public void run() {
						frame.remove(canvas);
						((Component) resource).setBounds(0, 0,
								frame.getWidth(), frame.getHeight());
						frame.add((Component) resource);
						frame.validate();
					}
				});
				currentComponent = resource;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.platform.GUI#commit(float)
	 */
	@Override
	public void commit(final float interpolation) {
		processInput();

		if (currentComponent != null)
			return;

		SwingUtilities.doInEDTNow(new Runnable() {
			@Override
			public void run() {
				BufferStrategy bs = canvas.getBufferStrategy();
				Graphics2D g = (Graphics2D) bs.getDrawGraphics();
				g.setClip(0, 0, platformConfiguration.getWidth(),
						platformConfiguration.getHeight());

				if (!g.getRenderingHints().containsValue(
						RenderingHints.VALUE_ANTIALIAS_ON))
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
				if (!g.getRenderingHints().containsValue(
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON))
					g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				g.setFont(g.getFont().deriveFont(20.0f));

				render(g, interpolation);

				g.dispose();
			}
		});

		SwingUtilities.doInEDT(new Runnable() {
			@Override
			public void run() {
				BufferStrategy bs = canvas.getBufferStrategy();
				bs.show();
				Toolkit.getDefaultToolkit().sync();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.platform.GUI#commitToImage()
	 */
	@Override
	public RuntimeAsset<Image> commitToImage() {
		int width = platformConfiguration.getWidth() * GUI.VIRTUAL_HEIGHT
				/ platformConfiguration.getHeight();
		int height = GUI.VIRTUAL_HEIGHT;
		DesktopEngineImage image = new DesktopEngineImage(width, height);

		Graphics2D g = (Graphics2D) image.getImage().getGraphics();
		g.setClip(0, 0, width, height);

		setRenderingHints(g);

		g.setFont(g.getFont().deriveFont(20.0f));

		// g.scale(platformConfiguration.getScale(),
		// platformConfiguration.getScale());

		render(g, 0.0f);

		g.dispose();

		return image;
	}

	/**
	 * Set the appropriate rendering hints to get the best graphic results.
	 * 
	 * @param g
	 */
	private void setRenderingHints(Graphics2D g) {
		// TODO test effects, probably should allow disabling
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_NORMALIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.platform.GUI#initilize()
	 */
	@Override
	public void initilize() {
		try {
			SwingUtilities.doInEDTNow(new Runnable() {
				@Override
				public void run() {
					Thread.currentThread().setUncaughtExceptionHandler(
							SwingExceptionHandler.getInstance());
					Thread.setDefaultUncaughtExceptionHandler(SwingExceptionHandler
							.getInstance());

					frame = new JFrame();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setSize(platformConfiguration.getWidth(),
							platformConfiguration.getHeight());
					frame.setUndecorated(true);
					frame.setIgnoreRepaint(true);

					if (platformConfiguration.isFullscreen()) {
						// TODO this might not work in windows
						GraphicsDevice gd = GraphicsEnvironment
								.getLocalGraphicsEnvironment()
								.getDefaultScreenDevice();
						gd.setFullScreenWindow(frame);
						platformConfiguration.setHeight(frame.getHeight());
						platformConfiguration.setWidth(frame.getWidth());
						logger.info("Frame size: " + frame.getWidth() + " x "
								+ frame.getHeight());
					} else {
						// TODO Centers game, might be necessary to change in
						// debug mode
						frame.setLocationRelativeTo(null);
					}

					frame.setVisible(true);

					initializeCanvas();
				}
			});
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		logger.info("Desktop GUI initilized");
	}

	/**
	 * Initialize the {@code Canvas} element where the actual game is drawn
	 */
	private void initializeCanvas() {
		canvas = new Canvas();
		canvas.setSize(frame.getSize());
		canvas.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		frame.add(canvas);

		canvas.setEnabled(true);
		canvas.setVisible(true);
		canvas.setFocusable(true);

		canvas.createBufferStrategy(2);
		BufferStrategy bs = canvas.getBufferStrategy();
		bs.getDrawGraphics().getFontMetrics();

		DesktopInputListener listener = new DesktopInputListener(mouseState,
				keyboardState);
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
		canvas.addKeyListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.platform.impl.AbstractGUI#processKeyAction
	 * (es.eucm.eadventure.engine.core.guiactions.KeyAction)
	 * 
	 * In desktop games, arrow keys are used to move the mouse if not consumed
	 * by a game object
	 */
	@Override
	protected void processKeyAction(KeyAction action) {
		super.processKeyAction(action);
		if (action != null && !action.isConsumed()) {
			if (robot != null) {
				int x = MouseInfo.getPointerInfo().getLocation().x;
				int y = MouseInfo.getPointerInfo().getLocation().y;
				boolean move = false;

				switch (action.getKeyCode()) {
				case ARROW_UP:
					y = y - MOUSE_MOVE;
					move = true;
					break;
				case ARROW_DOWN:
					y = y + MOUSE_MOVE;
					move = true;
					break;
				case ARROW_LEFT:
					x = x - MOUSE_MOVE;
					move = true;
					break;
				case ARROW_RIGHT:
					x = x + MOUSE_MOVE;
					move = true;
					break;
				}
				if (move) {
					x = Math.max(x, frame.getX());
					y = Math.max(y, frame.getY());
					x = Math.min(x, frame.getX() + frame.getWidth());
					y = Math.min(y, frame.getY() + frame.getHeight());
					robot.mouseMove(x, y);
				}

			}
		}
	}

	@Override
	public void changeCursor(Image image) {
		if (image == null && !cursorsStack.isEmpty()) {
			Cursor c = cursorsStack.pop();
			canvas.setCursor(c);
		} else if (image != null) {
			DesktopEngineImage asset = (DesktopEngineImage) assetHandler
					.getRuntimeAsset(image);
			asset.loadAsset();
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(
					asset.getImage(), new Point(0, 0),
					image.getURI().toString());
			cursorsStack.add(canvas.getCursor());
			canvas.setCursor(c);
		}
	}

	@Override
	public void finish() {
		if (frame != null){
			frame.setVisible(false);
		}

	}

}
