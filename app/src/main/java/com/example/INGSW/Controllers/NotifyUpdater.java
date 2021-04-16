package com.example.INGSW.Controllers;

import android.app.Activity;
import android.widget.ImageButton;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import teaspoon.annotations.OnUi;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class NotifyUpdater extends TimerTask implements RetrofitListInterface {
    private static List<?> notify = new ArrayList<>();
    private static ImageButton bell;
    private static Timer timer;
    private static Activity activity;

    public NotifyUpdater(Timer timer, ImageButton bell, Activity activity,List<?> list){
        NotifyUpdater.bell = bell;
        NotifyUpdater.timer = timer;
        NotifyUpdater.activity = activity;
        NotifyUpdater.notify = list;
    }

    public NotifyUpdater(Timer timer, ImageButton bell, Activity activity){
        new NotifyUpdater(timer,bell,activity,new ArrayList<>());
    }

    public List<?> getNotify(){
        return notify;
    }

    @OnUi
    public void run() {
        RetrofitResponse.getResponse(((ToolBarActivity) activity).getUid(),this,activity,"getNotify");
        timer.schedule(new NotifyUpdater(timer, bell, activity,notify), 30000);
    }

    public static void newUpdate(){
        timer.schedule(new NotifyUpdater(timer, bell, activity,notify),1);
    }

    @Override
    @OnUi
    public void setList(List<?> newList) {
        notify=newList;
        if (notify.size() == 0) {
            bell.setImageResource(R.drawable.icons8_notification_30px_1);
        } else {
            boolean allSeen = true;
            for (Object not : notify)
                if (((Notify)not).getState().equals("PENDING")) {
                    allSeen = false;
                    break;
                }
            if (!allSeen)
                bell.setImageResource(R.drawable.icons8_notification_30px_1_active);
            else {
                bell.setImageResource(R.drawable.icons8_notification_30px_1);
            }
        }
    }
}
