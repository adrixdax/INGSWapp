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
    private static List<Notify> notify = new ArrayList<>();
    private static ImageButton bell;
    private Timer timer;
    private Activity activity;

    public NotifyUpdater(Timer timer, ImageButton bell, Activity activity){
        NotifyUpdater.bell = bell;
        this.timer = timer;
        this.activity = activity;
    }

    public List<Notify> getNotify(){
        return notify;
    }


    public void run() {
        try {
            notify = (List<Notify>) getJsonToDecode(String.valueOf(new NotifyTestController().execute("idUser="+((ToolBarActivity)activity).getUid()).get()),Notify.class);
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
        timer.schedule(new NotifyUpdater(this.timer, bell,this.activity),30000);
    }

}