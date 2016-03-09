package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by Aditya on 05-Mar-16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetCollectionService extends RemoteViewsService {
    public static final String TAG = WidgetCollectionService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "YES");
        return new WidgetCollectionRemoteFactory(getApplicationContext(), intent);
    }
}
