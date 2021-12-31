package com.ctk43.doancoso.Library;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.JobAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Extension {


    public static boolean isEmpty(Context context, String value, String name, boolean isDefault) {
        if (value.isEmpty() || isDefault) {
            Toast.makeText(context, "Không được để " + name + " trống, vui lòng nhập " + name + "!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentWeek() {
        return getWeek(Calendar.getInstance().getTime());
    }

    public static int Last_Week(int week) {
        if (week == 1)
            return 52;
        return week - 1;
    }

    public static int Next_Week(int week) {
        if (week == 52)
            return 1;
        return week + 1;
    }

    public static Date StartOfWeek(Date date) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.setFirstDayOfWeek(Calendar.SUNDAY);
        return Cal.getTime();
    }

    public static boolean isEmty(Context context, String value, String name, boolean isdefaut) {
        if (value.isEmpty() || isdefaut) {
            Toast.makeText(context, "Không được để " + name + " trống, vui lòng nhập " + name + "!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static int CheckStatus(Job job) {
        if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(),job.getStartDate() ) > 0) {
            return GeneralData.STATUS_COMING;
        } else if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate()) >= 0 && job.getProgress() != 1) {
            long test= CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate());
            Log.e("Test", "CheckStatus: "+test);
            return GeneralData.STATUS_ON_GOING;
        } else if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate()) < 0 && job.getProgress() != 1) {
            return GeneralData.STATUS_OVER;
        } else if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate()) >= 0 && job.getProgress() == 1) {
            return GeneralData.STATUS_FINISH;
        } else {
            return GeneralData.STATUS_FINISH_LATE;
        }
    }

    public static boolean statusIsChange(Job job) {
        int status = job.getStatus();
        job.setStatus(CheckStatus(job));
        if (status != job.getStatus())
            return true;
        return false;
    }

    public static List<Job> getJobsChange(List<Job> jobList) {
        List<Job> jobs = new ArrayList<>();
        for (Job job : jobList) {
            if (statusIsChange(job)) {
                jobs.add(job);
            }
        }
        return jobs;
    }
    public static boolean canCheck(Context context,CheckBox checkBox, Job job){
        if(job.getStatus() == GeneralData.STATUS_COMING){
            Toast.makeText(context,R.string.toast_can_not_do_that,Toast.LENGTH_SHORT).show();
            checkBox.setChecked(false);
            return false;
        }
        return true;

    }


}
