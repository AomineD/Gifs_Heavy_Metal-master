package com.heavymetal.giphy.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.heavymetal.giphy.manager.PrefManager;
import com.heavymetal.giphy.ui.CategoryActivity;
import com.heavymetal.giphy.ui.GifActivity;
import com.heavymetal.giphy.ui.MainActivity;
import com.heavymetal.giphy.ui.UserActivity;
import com.heavymetal.giphy.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hsn on 26/10/2017.
 */

public class NotifFirebaseMessagingService  extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    Bitmap bitmap;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String type = remoteMessage.getData().get("type");
        String id = remoteMessage.getData().get("id");
        String title = remoteMessage.getData().get("title");
        String image = remoteMessage.getData().get("image");
        String icon = remoteMessage.getData().get("icon");
        String message = remoteMessage.getData().get("message");
        PrefManager prf = new PrefManager(getApplicationContext());
        if (!prf.getString("notifications").equals("false")) {
            if (type.equals("image")) {
                String image_id =remoteMessage.getData().get("id") ;
                String image_title = remoteMessage.getData().get("image_title") ;
                String image_review = remoteMessage.getData().get("image_review");
                String image_trusted = remoteMessage.getData().get("image_trusted") ;
                String image_comment = remoteMessage.getData().get("image_comment") ;
                String image_comments = remoteMessage.getData().get("image_comments");
                String image_downloads =remoteMessage.getData().get("image_downloads") ;
                String image_user = remoteMessage.getData().get("image_user") ;
                String image_userid = remoteMessage.getData().get("image_userid") ;
                String image_type = remoteMessage.getData().get("image_type") ;
                String image_extension =remoteMessage.getData().get("image_extension") ;
                String image_thumbnail =remoteMessage.getData().get("image_thumbnail");
                String image_image =remoteMessage.getData().get("image_image") ;
                String image_original = remoteMessage.getData().get("image_original") ;
                String image_userimage = remoteMessage.getData().get("image_userimage") ;
                String image_created = remoteMessage.getData().get("image_created") ;
                String image_tags = remoteMessage.getData().get("image_tags") ;
                String image_like = remoteMessage.getData().get("image_like") ;
                String image_love = remoteMessage.getData().get("image_love");
                String image_woow = remoteMessage.getData().get("image_woow") ;
                String image_angry = remoteMessage.getData().get("image_angry") ;
                String image_sad = remoteMessage.getData().get("image_sad");
                String image_haha = remoteMessage.getData().get("image_haha") ;

                sendStatusNotification(
                    id,
                    title,
                    image,
                    icon,
                    message,
                    image_id ,
                    image_title  ,
                    image_review ,
                    image_trusted  ,
                    image_comment  ,
                    image_comments ,
                    image_downloads ,
                    image_user  ,
                    image_userid  ,
                    image_type  ,
                    image_extension ,
                    image_thumbnail,
                    image_image ,
                    image_original  ,
                    image_userimage  ,
                    image_created  ,
                    image_tags  ,
                    image_like  ,
                    image_love ,
                    image_woow  ,
                    image_angry  ,
                    image_sad ,
                    image_haha
            );
            }else if (type.equals("link")) {
                    String link = remoteMessage.getData().get("link");
                    sendNotificationUrl(
                            id,
                            title,
                            image,
                            icon,
                            message,
                            link
                    );
            }else if(type.equals("category")){
                String category_title = remoteMessage.getData().get("title_category");
                String category_image = remoteMessage.getData().get("image_category");

                sendNotificationCategory(
                        id,
                        title,
                        image,
                        icon,
                        message,
                        category_title,
                        category_image);
            }else if(type.equals("user")){
                String name_user = remoteMessage.getData().get("name_user");
                String image_user = remoteMessage.getData().get("image_user");
                String trusted_user = remoteMessage.getData().get("trusted_user");

                sendNotificationUser(
                        id,
                        title,
                        image,
                        icon,
                        message,
                        name_user,
                        image_user,
                        trusted_user);
            }
        }
    }
    private void sendNotificationUser(
            String id,
            String title,
            String imageUri,
            String iconUrl,
            String message,
            String name_user,
            String image_user,
            String trusted_user
    ) {


        Bitmap image = getBitmapfromUrl(imageUri);
        Bitmap icon = getBitmapfromUrl(iconUrl);
        Intent intent = new Intent(this, UserActivity.class);
        intent.setAction(Long.toString(System.currentTimeMillis()));

        Boolean trusted = false;
        if (trusted_user.equals("true")){
            trusted=true;
        }

        intent.putExtra("id", Integer.parseInt(id));
        intent.putExtra("name",name_user);
        intent.putExtra("image",image_user);
        intent.putExtra("trusted",trusted);
        intent.putExtra("from", "notification");




        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        int NOTIFICATION_ID = Integer.parseInt(id);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String Description = "This is my channel";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message);

        if (icon!=null){
            builder.setLargeIcon(icon);

        }else{
            builder.setLargeIcon(largeIcon);
        }
        if (image!=null){
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image));
        }


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }
    private void sendNotificationCategory(
            String id,
            String title,
            String imageUri,
            String iconUrl,
            String message,
            String category_title,
            String category_image
    ) {


        Bitmap image = getBitmapfromUrl(imageUri);
        Bitmap icon = getBitmapfromUrl(iconUrl);
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.setAction(Long.toString(System.currentTimeMillis()));


        intent.putExtra("id", Integer.parseInt(id));
        intent.putExtra("title",category_title);
        intent.putExtra("image",category_image);
        intent.putExtra("from", "notification");




        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        int NOTIFICATION_ID = Integer.parseInt(id);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String Description = "This is my channel";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_w)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message);

        if (icon!=null){
            builder.setLargeIcon(icon);

        }else{
            builder.setLargeIcon(largeIcon);
        }
        if (image!=null){
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image));
        }


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }
    private void sendNotificationUrl(
            String id,
            String title,
            String imageUri,
            String iconUrl,
            String message,
            String url

    ) {


        Bitmap image = getBitmapfromUrl(imageUri);
        Bitmap icon = getBitmapfromUrl(iconUrl);


        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(url));
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        int NOTIFICATION_ID = Integer.parseInt(id);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String Description = "This is my channel";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_w)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message);

        if (icon!=null){
            builder.setLargeIcon(icon);

        }else{
            builder.setLargeIcon(largeIcon);
        }
        if (image!=null){
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image));
        }


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }


    private void sendStatusNotification(
            String id,
            String title,
            String imageUri,
            String iconUrl,
            String message,
            String   image_id ,
            String   image_title  ,
            String   image_review ,
            String   image_comment  ,
            String   image_trusted ,
            String   image_comments ,
            String   image_downloads ,
            String   image_user  ,
            String   image_userid  ,
            String   image_type  ,
            String   image_extension ,
            String   image_thumbnail,
            String   image_image ,
            String   image_original  ,
            String   image_userimage  ,
            String   image_created  ,
            String   image_tags  ,
            String   image_like  ,
            String   image_love ,
            String   image_woow  ,
            String   image_angry  ,
            String   image_sad ,
            String   image_haha

    ){
        Bitmap image = getBitmapfromUrl(imageUri);
        Bitmap icon = getBitmapfromUrl(iconUrl);
        Intent intent = new Intent(this, GifActivity.class);
        intent.setAction(Long.toString(System.currentTimeMillis()));

       Boolean comment = false;
        if (image_comment=="true"){
            comment=true;
        }
        Boolean trusted = false;
        if (image_trusted=="true"){
            trusted=true;
        }
        intent.putExtra("from","notification");

        intent.putExtra("id", Integer.parseInt(image_id));
        intent.putExtra("title", image_title);
        intent.putExtra("thumbnail", image_thumbnail);
        intent.putExtra("userid", Integer.parseInt(image_userid));
        intent.putExtra("user", image_user);
        intent.putExtra("userimage", image_userimage);
        intent.putExtra("type", image_type);
        intent.putExtra("original", image_original);
        intent.putExtra("image", image_image);
        intent.putExtra("extension", image_extension);
        intent.putExtra("trusted", trusted);
        intent.putExtra("comment", comment);
        intent.putExtra("downloads",Integer.parseInt( image_downloads));
        intent.putExtra("tags", image_tags);
        intent.putExtra("review", image_review);
        intent.putExtra("comments",Integer.parseInt( image_comments));
        intent.putExtra("created", image_created);
        intent.putExtra("woow", Integer.parseInt(image_woow));
        intent.putExtra("like", Integer.parseInt(image_like));
        intent.putExtra("love", Integer.parseInt(image_love));
        intent.putExtra("angry", Integer.parseInt(image_angry));
        intent.putExtra("sad", Integer.parseInt(image_sad));
        intent.putExtra("haha", Integer.parseInt(image_haha));



        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        int NOTIFICATION_ID = Integer.parseInt(id);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String Description = "This is my channel";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_w)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message);

        if (icon!=null){
            builder.setLargeIcon(icon);

        }else{
            builder.setLargeIcon(largeIcon);
        }
        if (image!=null){
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image));
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }




    /*
    *To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}