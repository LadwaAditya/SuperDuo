package barqsoft.footballscores.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by Aditya on 04-Mar-16.
 */
public class UpdateWidgetService extends IntentService {

    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_MATCHTIME = 2;

    public static final String TAG = UpdateWidgetService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateWidgetService(String name) {
        super(name);
    }

    public UpdateWidgetService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (int i = 0; i < appWidgetIds.length; i++) {
            int id = appWidgetIds[i];

            Intent inten = new Intent(this.getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, inten, 0);

            RemoteViews views = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.widget_frame);

            try {
                Cursor cursor = getContentResolver().query(DatabaseContract.BASE_CONTENT_URI, null, null, null, null);

                Log.d("Widget", String.valueOf(cursor.getCount()));
                cursor.moveToFirst();
                assert cursor != null;
                String score = Utilies.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS));

                views.setTextViewText(R.id.home_name, cursor.getString(COL_HOME));
                views.setTextViewText(R.id.score_textview, score);
                views.setTextViewText(R.id.away_name, cursor.getString(COL_AWAY));
                views.setTextViewText(R.id.data_textview, cursor.getString(COL_MATCHTIME));

                views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                        cursor.getString(COL_HOME)));
                views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(
                        cursor.getString(COL_HOME)));
            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            }

            views.setOnClickPendingIntent(R.id.frame_widget, pendingIntent);

            appWidgetManager.updateAppWidget(id, views);

        }
    }
}
