package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(foreignKeys = {@ForeignKey(entity = Job.class,
        parentColumns = "ID",
        childColumns = "JobID",
        onDelete = ForeignKey.CASCADE)})
    public class JobDetail {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "Name")  @NonNull
    public String Name;

    @ColumnInfo(name = "JobID",index = true)  @NonNull
    public int JobID;

    @ColumnInfo(name = "EstimatedTime")
    public int EstimatedCompletedTime;

    @ColumnInfo(name = "ActualTime")
    public int ActualCompletedTime;

    @ColumnInfo(name = "Description")
    public String Description;

    @ColumnInfo(name = "Priority")
    public Boolean Priority;

    @ColumnInfo(name = "Progress")
    public Double Progress;

    @ColumnInfo(name = "Status")
    public int Status; //0 - on going; -1 - drop; 1 - complete; 2- over

    @ColumnInfo(name = "IDParent")
    public int IDParent;

    public JobDetail(){

    }
    public JobDetail(int ID, int JOBID, String name, int estimatedCompletedTime, int actualCompletedTime, String description, Boolean priority, Double progress, int status) {
        this.ID = ID;
        this.JobID = JOBID;
        Name = name;
        EstimatedCompletedTime = estimatedCompletedTime;
        ActualCompletedTime = actualCompletedTime;
        Description = description;
        Priority = priority;
        Progress = progress;
        Status = status;
    }

    public JobDetail(String name, String description, int estimatedCompletedTime) {
        Name = name;
        EstimatedCompletedTime = estimatedCompletedTime;
        Description = description;
        ActualCompletedTime = 0;
        Priority = false;
        Status = 0;
        Progress = 0.0;
      // IDParent = -1;
    }    public JobDetail(int jobID,String name, String description, int estimatedCompletedTime) {
        Name = name;
        this.JobID = jobID;
        EstimatedCompletedTime = estimatedCompletedTime;
        Description = description;
        ActualCompletedTime = 0;
        Priority = false;
        Status = 0;
        Progress = 0.0;
      // IDParent = -1;
    }
}
