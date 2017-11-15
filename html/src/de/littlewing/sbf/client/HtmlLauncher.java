package de.littlewing.sbf.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import de.littlewing.sbf.PaxBritannica;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1024, 550);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new PaxBritannica();
        }
}
