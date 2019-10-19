package com.example.afe.task.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.afe.task.R;
import com.example.afe.task.model.AppDatabase;
import com.example.afe.task.model.Task;
import java.sql.Date;
import java.util.Calendar;

public class Form_tacheCreate extends AppCompatActivity  {

    public static final int REQUEST_CODE = 0;
    private Button tacheDebut_bt;
    private Button tachefin_bt;
    private Button ajouter_bt;
    private EditText nom_Tache_txt;
    private EditText desc_Tache_txt;
    private TextView tacheDebut;
    private TextView tacheFin;
    private Calendar debut_date=Calendar.getInstance();
    private Calendar calendRef=Calendar.getInstance();
    private Calendar rappelRef=Calendar.getInstance();
    private Spinner priorite;
    int minD;
    int minF;
    int hD;
    int hF;
    int jD;
    int jF;
    int moisD;
    int moisF;
    int anneD;
    int anneF;
    String choix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tache_create);
        Calendar c=Calendar.getInstance();
        priorite = (Spinner)findViewById(R.id.spinner);
        tacheDebut_bt=(Button)findViewById(R.id.debut_bt);
        tachefin_bt=(Button)findViewById(R.id.fin_bt);
        ajouter_bt=(Button)findViewById(R.id.ajouter_tache);
        tacheDebut=(TextView)findViewById(R.id.heure_txt);
        nom_Tache_txt= (EditText)findViewById(R.id.nom_tache);
        desc_Tache_txt=(EditText)findViewById(R.id.desc_tache);
        tacheFin=(TextView)findViewById(R.id.calendrier_txt);
        minD=c.get(Calendar.MINUTE);
        minF=c.get(Calendar.MINUTE);
        hD=c.get(Calendar.HOUR_OF_DAY);
        hF=c.get(Calendar.HOUR_OF_DAY);
        jD=c.get(Calendar.DAY_OF_MONTH);
        jF=c.get(Calendar.DAY_OF_MONTH);

        moisD=c.get(Calendar.MONTH)+1;
        moisF=c.get(Calendar.MONTH)+1;
        anneD= c.get(Calendar.YEAR);
        anneF= c.get(Calendar.YEAR);
        tacheFin.setText(jF+"/" +moisF+"/"+anneF+" "+hF+" h :"+minF+" m");
        tacheDebut.setText(jD+"/" +moisD+"/"+anneD+" "+hD+" h :"+minD+" m");
        choix="";
        debut_date=Calendar.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE:
                if (resultCode==CalendHourChoice.RESULT_OK){
                    choix=data.getStringExtra("choix");
                    long l;
                    if(choix.equals("debut")) {
                        l=data.getLongExtra("calendrier",0);
                        debut_date.setTimeInMillis(l);
                        minD=debut_date.get(Calendar.MINUTE);
                        hD=debut_date.get(Calendar.HOUR_OF_DAY);
                        jD=debut_date.get(Calendar.DAY_OF_MONTH);
                        moisD=debut_date.get(Calendar.MONTH)+1;
                        anneD= debut_date.get(Calendar.YEAR);
                        tacheDebut=(TextView)findViewById(R.id.heure_txt);
                        tacheDebut.setText(jD+"/" +moisD+"/"+anneD+" "+hD+" h :"+minD+" m");
                    }
                    if(choix.equals("fin")){
                        l=data.getLongExtra("calendrier",0);
                        long l2= l-1800000;
                        calendRef.setTimeInMillis(l);
                        rappelRef.setTimeInMillis(l2);
                        minF=calendRef.get(Calendar.MINUTE);
                        hF=calendRef.get(Calendar.HOUR_OF_DAY);
                        jF=calendRef.get(Calendar.DAY_OF_MONTH);
                        moisF=calendRef.get(Calendar.MONTH)+1;
                        anneF= calendRef.get(Calendar.YEAR);
                        tacheFin=(TextView)findViewById(R.id.calendrier_txt);
                        tacheFin.setText(jF+"/" +moisF+"/"+anneF+" "+hF+" h :"+minF+" m");
                    }
                }
                break;
            default:
                break;
        }
    }

    public void goDebut(View v){
        Intent intent=new Intent(this, CalendHourChoice.class);
        intent.putExtra("choix","debut");
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void goFin(View v ){
        Intent intent=new Intent(this, CalendHourChoice.class);
        intent.putExtra("choix","fin");
        startActivityForResult(intent,REQUEST_CODE);
    }

    private void startAlarm(Calendar c, Calendar d){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        Intent intentRappel = new Intent(this,AlertReceiverRappel.class);
        PendingIntent pendingIntenta = PendingIntent.getBroadcast(this, 1, intent, 0);
        PendingIntent pendingIntentb = PendingIntent.getBroadcast(this, 2, intentRappel, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntenta);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, d.getTimeInMillis(),pendingIntentb);
    }

    public void goAjouter(View v){
        Date dstart = new java.sql.Date(debut_date.getTimeInMillis());
        Date end    = new java.sql.Date(calendRef.getTimeInMillis());
        String nom=nom_Tache_txt.getText().toString();
        String description=desc_Tache_txt.getText().toString();
        String spinner_text = priorite.getSelectedItem().toString();
        int num_priorite = 3;
        if(spinner_text.equals("High")){
            num_priorite = 1;
        }
        else if(spinner_text.equals("Default")){
            num_priorite = 2;
        }
        if(nom.equals("")){
            Toast.makeText(this,"Le nom de la tâche est vide",Toast.LENGTH_SHORT).show();
        }
        if(debut_date.getTimeInMillis()>calendRef.getTimeInMillis()){
            Toast.makeText(this, "La date de début antérieur à date de fin!",Toast.LENGTH_SHORT).show();
        }
        else {
            Task t = new Task(nom, dstart, end, false, description, num_priorite,false);
            AppDatabase instance = AppDatabase.getInstance(this.getApplicationContext());
            instance.taskDao().insertTask(t);
            startAlarm(calendRef, rappelRef);
            finish();
        }
    }
}