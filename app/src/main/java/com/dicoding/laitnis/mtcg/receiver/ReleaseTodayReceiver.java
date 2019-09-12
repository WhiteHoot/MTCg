package com.dicoding.laitnis.mtcg.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.dicoding.laitnis.mtcg.R;
import com.dicoding.laitnis.mtcg.entity.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReleaseTodayReceiver extends BroadcastReceiver {

    private static final String API_KEY = "d2dfaf3ce527b24d57d284fe5bf270f5";
    private final int ID_RELEASE = 101;
    private String title, movTitle;

    public ReleaseTodayReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        title = context.getString(R.string.release_today_message);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Log.e("Date", date);

        loadData(context, date, title);

    }

    private void loadData(final Context context, String currentDate, final String title) {

        final ArrayList<Movie> listMovies = new ArrayList<>();
        final List<String> listMovieTitle = new ArrayList<String>();

        AsyncHttpClient client = new AsyncHttpClient();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + currentDate + "&primary_release_date.lte=" + currentDate;
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);
                        Movie movies = new Movie(movie);
                        listMovies.add(movies);
                    }
                    if (!listMovies.isEmpty()) {
                        for (int i = 0; i < listMovies.size(); i++) {
                            Movie movie = listMovies.get(i);
                            movTitle = movie.getMovTitle();
                            listMovieTitle.add(movTitle);
                            Log.e("Movies", movTitle);
                        }

                        String joined = TextUtils.join(", ", listMovieTitle);
                        showReminderNotification(context, title, joined);

                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    private void showReminderNotification(Context context, String title, String message) {

        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Release Reminder Channel";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(ID_RELEASE, notification);
        }

    }

    public void setReleaseReminder(Context context) {

        Log.e("Release Reminder", "Reminder is set");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context) {

        Log.e("Daily Reminder", "Reminder is cancelled");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
