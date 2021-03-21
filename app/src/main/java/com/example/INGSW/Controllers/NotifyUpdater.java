package com.example.INGSW.Controllers;

import android.app.Activity;
import android.widget.ImageButton;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class NotifyUpdater extends TimerTask {
    private List<Notify> notify = new ArrayList<>();
    private static ImageButton bell;
    private Timer timer;
    private Activity activity;

    public NotifyUpdater(Timer timer, ImageButton bell, Activity activity){
        this.bell = bell;
        this.timer = timer;
        this.activity = activity;
    }

    public List<Notify> getNotify(){
        return notify;
    }


    public void run() {
        System.out.println("5 minutes passed :)");
        try {
            notify = (List<Notify>) getJsonToDecode(String.valueOf(new NotifyTestController().execute(((ToolBarActivity)activity).getUid()).get()),Notify.class);
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (notify.size() == 0) {
                    bell.setImageResource(R.drawable.icons8_notification_30px_1);
                } else {
                    bell.setImageResource(R.drawable.icons8_notification_30px_1_active);
                }
            }
        });
        timer.schedule(new NotifyUpdater(this.timer,this.bell,this.activity),300000);
    }

}