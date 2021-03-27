package com.example.INGSW.Controllers;

import android.app.Activity;
import android.widget.ImageButton;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.NotifyPopUp;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class NotifyUpdater extends TimerTask {
    private static ArrayList<Notify> notify = new ArrayList<>();
    private static ImageButton bell;
    private static Timer timer;
    private static Activity activity;

    public NotifyUpdater(Timer timer, ImageButton bell, Activity activity){
        NotifyUpdater.bell = bell;
        NotifyUpdater.timer = timer;
        NotifyUpdater.activity = activity;
    }

    public ArrayList<Notify> getNotify(){
        try {
            notify = new ArrayList<Notify>((List<Notify>) getJsonToDecode(String.valueOf(new NotifyTestController().execute("idUser="+((ToolBarActivity)activity).getUid()).get()),Notify.class));
            return notify;
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public void run() {
        try {
            notify = new ArrayList<Notify>((List<Notify>) getJsonToDecode(String.valueOf(new NotifyTestController().execute("idUser="+((ToolBarActivity)activity).getUid()).get()),Notify.class));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (notify.size() == 0) {
                    bell.setImageResource(R.drawable.icons8_notification_30px_1);
                } else {
                    boolean allSeen=true;
                    for (Notify not : notify)
                        if (not.getState().equals("PENDING")) {
                            allSeen = false;
                            break;
                        }
                    if (!allSeen)
                        bell.setImageResource(R.drawable.icons8_notification_30px_1_active);
                    else{
                        bell.setImageResource(R.drawable.icons8_notification_30px_1);
                    }
                }
            }
        });
        timer.schedule(new NotifyUpdater(timer, bell, activity),30000);
    }

    public static void newUpdate(){
        timer.schedule(new NotifyUpdater(timer, bell, activity),1);
    }
}