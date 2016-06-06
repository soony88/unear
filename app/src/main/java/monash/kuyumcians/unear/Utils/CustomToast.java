package monash.kuyumcians.unear.Utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by kuyumcians on 11/05/2016.
 */
public class CustomToast {

    public static void displayToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
