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

package es.eucm.eadventure.engine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.google.inject.Guice;
import com.google.inject.Injector;

import es.eucm.eadventure.common.elementfactories.EAdElementsFactory;
import es.eucm.eadventure.common.model.elements.EAdAdventureModel;
import es.eucm.eadventure.common.model.elements.EAdScene;
import es.eucm.eadventure.common.model.elements.impl.EAdSceneImpl;
import es.eucm.eadventure.common.model.impl.EAdAdventureModelImpl;
import es.eucm.eadventure.common.model.impl.EAdChapterImpl;
import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.engine.assets.specialassetrenderers.AndroidVideoRenderer;
import es.eucm.eadventure.engine.assets.specialassetrenderers.RockPlayerAndroidVideoRenderer;
import es.eucm.eadventure.engine.core.Game;
import es.eucm.eadventure.engine.core.GameController;
import es.eucm.eadventure.engine.core.MouseState;
import es.eucm.eadventure.engine.core.impl.modules.BasicGameModule;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.platform.PlatformConfiguration;
import es.eucm.eadventure.engine.extra.AndroidAssetHandlerModule;
import es.eucm.eadventure.engine.extra.AndroidModule;
import es.eucm.eadventure.engine.extra.EAdventureSurfaceView;

public class EAdventureEngineActivity extends Activity {
	
	static final int ANDROID_PLAYER_RESULT = 0;
	
	static final int ROCKPLAYER_RESULT = 1;	

	private GameController gameController;

	private DisplayMetrics dm;

	private Injector injector;

	private EAdventureSurfaceView surfaceView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		injector = Guice.createInjector(new AndroidAssetHandlerModule(), new AndroidModule(),
				new BasicGameModule());

		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		AndroidPlatformConfiguration config = (AndroidPlatformConfiguration) injector
				.getInstance(PlatformConfiguration.class);
		
		int height, width;
		height = dm.heightPixels;
		width = dm.widthPixels;
		
		//In case wrong display metrics like Samsung GT 10.1
		if (height > width){
			config.setWidth(height);
			config.setHeight(width);
		}
		else {
			config.setWidth(width);
			config.setHeight(height);
		}
		config.setFullscreen(true);

		// TODO fix this
		AndroidAssetHandler aah = (AndroidAssetHandler) injector
				.getInstance(AssetHandler.class);
		aah.setResources(getResources());
		aah.setContext(this);

		@SuppressWarnings("unchecked")
		Class<? extends EAdScene> demoClass = (Class<? extends EAdScene>) getIntent()
				.getExtras().getSerializable("demo");

		EAdSceneImpl sceneImpl = (EAdSceneImpl) injector.getInstance(demoClass);

		StringHandler sh = injector.getInstance(StringHandler.class);
		sh.addStrings(EAdElementsFactory.getInstance().getStringFactory()
				.getStrings());
		
		EAdAdventureModel model = new EAdAdventureModelImpl();
		EAdChapterImpl c1 = new EAdChapterImpl( "chapter1" );
		c1.getScenes().add(sceneImpl);
		c1.setInitialScene(sceneImpl);
		model.getChapters().add(c1);
		Game g = injector.getInstance(Game.class);
		g.setGame(model, c1);

		surfaceView = new EAdventureSurfaceView(this);
		setContentView(surfaceView);
		surfaceView.start(injector.getInstance(GUI.class), config,
				injector.getInstance(MouseState.class));

		gameController = injector.getInstance(GameController.class);
		gameController.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		gameController.pause();
		surfaceView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		gameController.resume();
		surfaceView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		gameController.stop();

		System.gc();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ANDROID_PLAYER_RESULT) {
        	AndroidVideoRenderer.finished = true;
        }
        else if (resultCode == ROCKPLAYER_RESULT){
        	RockPlayerAndroidVideoRenderer.finished = true;
        }
 
    }

}