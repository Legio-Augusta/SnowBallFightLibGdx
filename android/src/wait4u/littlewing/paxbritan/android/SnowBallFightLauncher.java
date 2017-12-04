package wait4u.littlewing.paxbritan.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class SnowBallFightLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new SbfSplashScreenGame(), config);
		initialize(new wait4u.littlewing.snowballfight.SnowBallFight(), config);
//		initialize(new AnimationScreen1(), config);
	}
}
