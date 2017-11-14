package cos.premy.mines;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by premy on 14.11.2017.
 */

public class ReviewReminder {
    private SharedPreferences sharedPref;
    private final Activity activity;

    private ReviewReminder(Activity activity){
        this.activity = activity;
        sharedPref = activity.getSharedPreferences("cos.premy.mines.review_reminder", Context.MODE_PRIVATE);
    }

    public static void setReadyForReview(Activity activity){
        new ReviewReminder(activity).setReadyForReviewPrivate();
    }

    private void setReadyForReviewPrivate(){
        sharedPref.edit().putBoolean("ready", true).commit();
    }

    private boolean getReadyForReview(){
        return sharedPref.getBoolean("ready", false);
    }

    private void setReviewed(){
        sharedPref.edit().putBoolean("reviewed", true).commit();
    }

    private boolean getReviewed(){
        return sharedPref.getBoolean("reviewed", false);
    }

    public static void startPotentialReviewReminding(Activity activity){
        new ReviewReminder(activity).startPotentialReviewRemindingPrivate();
    }

    private void startPotentialReviewRemindingPrivate(){
        if(!getReviewed() && getReadyForReview()){
            final Dialog dialog = new Dialog(activity);
            dialog.setTitle("Rating");

            LinearLayout ll = new LinearLayout(activity);
            ll.setOrientation(LinearLayout.VERTICAL);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;



            TextView tv = new TextView(activity);
            tv.setText("If you enjoy using Mines3D, please take a moment to rate it. Thanks for your support!");
            tv.setWidth(width/2*3);
            tv.setPadding(20, 20, 20, 10);
            ll.addView(tv);

            Button b1 = new Button(activity);
            b1.setText("Rating");
            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setReviewed();
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=cos.premy.mines")));
                    dialog.dismiss();
                }
            });
            b1.setPadding(20, 20, 20, 10);
            ll.addView(b1);

            Button b2 = new Button(activity);
            b2.setText("Remind me later");
            b2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            b2.setPadding(20, 20, 20, 10);
            ll.addView(b2);

            Button b3 = new Button(activity);
            b3.setText("No, thanks");
            b3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setReviewed();
                    dialog.dismiss();
                }
            });
            b3.setPadding(20, 20, 20, 10);
            ll.addView(b3);

            dialog.setContentView(ll);
            dialog.show();
        }

    }

}
