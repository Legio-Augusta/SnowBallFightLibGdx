package com.littlewing.sbf.app;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class SnowBallFightLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new SbfSplashScreenGame(), config);
		initialize(new SnowBallFight(), config);
//		initialize(new AnimationScreen1(), config);
	}
}
