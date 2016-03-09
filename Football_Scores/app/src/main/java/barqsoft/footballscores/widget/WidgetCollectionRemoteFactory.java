package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by Aditya on 05-Mar-16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetCollectionRemoteFactory implements RemoteViewsService.RemoteViewsFactory {


    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_MATCHTIME = 2;
    public static final String TAG = WidgetCollectionRemoteFactory.class.getSimpleName();

    private Cursor mCursor;
    private Context mContext = null;

    public WidgetCollectionRemoteFactory(Context context, Intent intent) {
        this.mContext = context;

    }

    @Override
    public void onCreate() {
        mCursor = mContext.getContentResolver().query(DatabaseContract.BASE_CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_frame);
        Log.d("REmoteCursor", "Yes");

        try {


            Log.d("Widget", String.valueOf(mCursor.getCount()));
            if (position < getCount()) {
                mCursor.move(position);
                String score = Utilies.getScores(mCursor.getInt(COL_HOME_GOALS), mCursor.getInt(COL_AWAY_GOALS));

                views.setTextViewText(R.id.home_name, mCursor.getString(COL_HOME));

                views.setTextViewText(R.id.score_textview, score);
                views.setTextViewText(R.id.away_name, mCursor.getString(COL_AWAY));
                views.setTextViewText(R.id.data_textview, mCursor.getString(COL_MATCHTIME));
                views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                        mCursor.getString(COL_HOME)));
                views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(
                        mCursor.getString(COL_HOME)));


            }
        } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
            Log.d(TAG, "Exception");
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
