package monash.kuyumcians.unear;

import android.content.res.Configuration;

import com.firebase.client.Firebase;

/**
 * Created by kuyumcians on 26/05/2016.
 */
public class Unear extends android.app.Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//        Firebase.setAndroidContext(this);
    }
}
