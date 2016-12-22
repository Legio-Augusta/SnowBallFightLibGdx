package de.swagner.sbf.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.swagner.sbf2.SnowBallFight;

public class AndroidLauncher2 extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new SbfSplashScreenGame(), config);
		initialize(new SnowBallFight(), config);
//		initialize(new AnimationScreen1(), config);
	}
}
