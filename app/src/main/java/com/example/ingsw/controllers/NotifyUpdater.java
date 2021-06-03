package com.example.ingsw.controllers;

import android.app.Activity;
import android.widget.ImageButton;

import com.example.ingsw.R;
import com.example.ingsw.ToolBarActivity;
import com.example.ingsw.component.db.classes.Notify;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import teaspoon.annotations.OnUi;

public class NotifyUpdater extends TimerTask implements RetrofitListInterface {
    private static List<?> notify = new ArrayList<>();
    private static ImageButton bell;
    private static Timer timer;
    private static Activity activity;
    private static Boolean timerStatus = true;

    public NotifyUpdater(Timer timer, ImageButton bell, Activity activity, List<?> list) {
        NotifyUpdater.bell = bell;
        NotifyUpdater.timer = timer;
        NotifyUpdater.activity = activity;
        NotifyUpdater.notify = list;
    }

    public NotifyUpdater(Timer timer, ImageButton bell, Activity activity) {
        new NotifyUpdater(timer, bell, activity, new ArrayList<>());
    }

    public static void newUpdate() {
        if (!timerStatus) {
            timer.purge();
            timer = new Timer();
            timerStatus = true;
        }
        timer.schedule(new NotifyUpdater(timer, bell, activity, notify), 1);
    }

    public static void stopUpdate() {
        timer.cancel();
        timerStatus = false;
    }

    public synchronized List<?> getNotify() {
        newUpdate();
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return notify;
    }

    @OnUi
    public void run() {
        if (timerStatus) {
            RetrofitResponse.getResponse(((ToolBarActivity) activity).getUid(), this, activity, "getNotify");
            timer.schedule(new NotifyUpdater(timer, bell, activity, notify), 30000);
        }
    }

    @Override
    @OnUi
    public synchronized void setList(List<?> newList) {
        notify = newList;
        if (notify.size() == 0) {
            bell.setImageResource(R.drawable.icons8_notification_30px_1);
        } else {
            boolean allSeen = true;
            for (Object not : notify)
                if (((Notify) not).getState().equals("PENDING")) {
                    allSeen = false;
                    break;
                }
            if (!allSeen) {
                bell.setImageResource(R.drawable.icons8_notification_30px_1_active);
                notifyAll();
            }
            else {
                bell.setImageResource(R.drawable.icons8_notification_30px_1);
            }
        }
    }

}
