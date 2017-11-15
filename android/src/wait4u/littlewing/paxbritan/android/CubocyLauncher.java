package wait4u.littlewing.paxbritan.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Created by nickfarow on 05/10/2016.
 */
public class CubocyLauncher extends AndroidApplication {
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new wait4u.littlewing.cubocy.Cubocy(), config);
    }

}
