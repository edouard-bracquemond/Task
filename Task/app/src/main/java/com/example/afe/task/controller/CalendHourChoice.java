package com.example.afe.task.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.afe.task.R;
import com.example.afe.task.view.DatePickerFragment;
import com.example.afe.task.view.TimePickerFragment;
import java.util.Calendar;

public class CalendHourChoice extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button ajouter_bt;
    Button calend;
    Button heure;
    TextView calend_txt;
    Calendar c;
    int min;
    int h;
    int j;
    int mois;
    int anne;
    String choix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c= Calendar.getInstance();
        setContentView(R.layout.activity_calend_hour_choice);
        ajouter_bt= (Button) findViewById(R.id.ajouter_bt) ;
        calend= (Button)findViewById(R.id.calend_bt);
        heure=(Button) findViewById(R.id.heure_bt);
        calend_txt=(TextView) findViewById(R.id.date_txt);
        min=c.get(Calendar.MINUTE);
        h=c.get(Calendar.HOUR_OF_DAY);
        j=c.get(Calendar.DAY_OF_MONTH);
        mois=c.get(Calendar.MONTH)+1;
        anne= c.get(Calendar.YEAR);
        calend_txt.setText(j+"/" +mois+"/"+anne+" "+h+" h :"+min+" m");
        choix="";
        Intent intent=getIntent();
        if (intent!=null){
            choix=intent.getStringExtra("choix");
        }

    }
    public void goDate(View v){
        DialogFragment datepicker= new DatePickerFragment();
        datepicker.show(getSupportFragmentManager(),"date picker");
    }

    public  void  goHeure(View v){
        DialogFragment timepicker= new TimePickerFragment();
        timepicker.show(getSupportFragmentManager(), "time picker");
    }

    public  void  goAjouterDate(View v){
        Intent intent=new Intent();
        intent.putExtra("calendrier",c.getTimeInMillis());
        intent.putExtra("choix",choix);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int anne, int mois, int jour) {
        c.set(Calendar.MONTH, mois);
        c.set(Calendar.DAY_OF_MONTH,jour);
        c.set(Calendar.YEAR, anne);
        this.anne=anne;
        this.mois=mois+1;
        this.j=jour;
        calend_txt.setText(this.j+"/" +this.mois+"/"+this.anne+" "+h+" h :"+min+" m");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int h, int m) {
        c.set(Calendar.MINUTE,m);
        c.set(Calendar.HOUR_OF_DAY,h);
        this.h=h;
        this.min=m;
        calend_txt.setText(this.j+"/" +this.mois+"/"+this.anne+" "+this.h+" h :"+this.min+" m");
    }
}
