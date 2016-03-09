package barqsoft.footballscores;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.widget.WidgetCollectionService;

/**
 * Created by Aditya on 05-Mar-16.
 */
public class WidgetCollectionProvider extends AppWidgetProvider {
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        for (int i = 0; i < appWidgetIds.length; i++) {
            int id = appWidgetIds[i];
            Log.d("Collection", "yes");

            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainActivityIntent, 0);

            RemoteViews views = new RemoteViews(context.getApplicationContext().getPackageName(), R.layout.widget_collection);

            Intent intentWidget = new Intent(context, WidgetCollectionService.class);
            intentWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            intentWidget.setData(Uri.parse(intentWidget.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.widgetListviewCollection, intentWidget);

            views.setOnClickPendingIntent(R.id.frame_widget, pendingIntent);

            appWidgetManager.updateAppWidget(id, views);
        }

    }
}