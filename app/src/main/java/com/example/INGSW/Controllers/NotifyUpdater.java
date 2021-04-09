package com.example.INGSW.Controllers;

import android.app.Activity;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.home.HomepageScreen;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import teaspoon.annotations.OnBackground;
import teaspoon.annotations.OnUi;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class NotifyUpdater extends TimerTask {
    private static ArrayList<Notify> notify = new ArrayList<>();
    private static Timer timer;
    private static ToolBarActivity activity;

    public NotifyUpdater(Timer timer, Activity activity) {
        NotifyUpdater.timer = timer;
        NotifyUpdater.activity = (ToolBarActivity) activity;
    }

    @OnBackground
    public static void newUpdate() {
        timer.schedule(new NotifyUpdater(timer, activity), 1);
    }

    @OnBackground
    public ArrayList<Notify> getNotify() {
        updateList();
        return notify;
    }

    @OnBackground
    private void updateList() {
        try {
            if (activity.getActiveFragment().getClass().equals(HomepageScreen.class)) notify = new ArrayList<>((List<Notify>) getJsonToDecode(String.valueOf(new NotifyTestController(activity).execute("idUser=" + ((ToolBarActivity) activity).getUid()).get()), Notify.class));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @OnUi
    public void run() {
        updateList();
        activity.runOnUiThread(() -> {
            if (notify.size() == 0) {
                HomepageScreen.getBell().setImageResource(R.drawable.icons8_notification_30px_1);
            } else {
                boolean allSeen = true;
                for (Notify not : notify)
                    if (not.getState().equals("PENDING")) {
                        allSeen = false;
                        break;
                    }
                if (!allSeen)
                    HomepageScreen.getBell().setImageResource(R.drawable.icons8_notification_30px_1_active);
                else {
                    HomepageScreen.getBell().setImageResource(R.drawable.icons8_notification_30px_1);
                }
            }
            timer.schedule(new NotifyUpdater(timer, activity), 30000);
        });
    }

    public static void setList(List newList){
        if (newList.equals(newList))
            notify= (ArrayList<Notify>) newList;
    }
}