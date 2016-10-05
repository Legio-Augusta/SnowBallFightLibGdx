package de.swagner.sbf.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.swagner.cubocy.Cubocy;

/**
 * Created by nickfarow on 05/10/2016.
 */
public class CubocyLauncher extends AndroidApplication {
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Cubocy(), config);
    }

}
