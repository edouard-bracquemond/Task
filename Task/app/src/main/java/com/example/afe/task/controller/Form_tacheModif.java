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

public class Form_tacheModif extends AppCompatActivity  {
    private Task t;
    public static final int REQUEST_CODE = 0;
    private Button tacheDebut_bt;
    private Button tachefin_bt;
    private Button ajouter_bt;
    private EditText nom_Tache_txt;
    private EditText desc_Tache_txt;
    private TextView tacheDebut;
    private TextView tacheFin;
    private Calendar debut_date = Calendar.getInstance();
    private Calendar calendRef =Calendar.getInstance();
    private Calendar rappelRef=Calendar.getInstance();
    private Spinner priorite_spinner;
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
        Intent intent = getIntent();
        int idIntent = intent .getIntExtra("id_select_task",0);
        AppDatabase instance = AppDatabase.getInstance(this.getApplicationContext());
        t = instance.taskDao().getTaskById(idIntent);
        setContentView(R.layout.activity_form_tache_modif);
        Date dated = new Date(t.getDateDebut().getTime());
        debut_date.setTime(dated);
        Date datef = new Date(t.getDateFin().getTime());
        calendRef.setTime(datef);
        priorite_spinner = (Spinner)findViewById(R.id.spinner);
        priorite_spinner.setSelection(t.getPriorite()-1);
        tacheDebut_bt=(Button)findViewById(R.id.debut_bt);
        tachefin_bt=(Button)findViewById(R.id.fin_bt);
        ajouter_bt=(Button)findViewById(R.id.ajouter_tache);
        tacheDebut=(TextView)findViewById(R.id.heure_txt);
        nom_Tache_txt= (EditText)findViewById(R.id.nom_tache);
        desc_Tache_txt=(EditText)findViewById(R.id.desc_tache);
        tacheFin=(TextView)findViewById(R.id.calendrier_txt);
        minD=debut_date.get(Calendar.MINUTE);
        minF=calendRef.get(Calendar.MINUTE);
        hD=debut_date.get(Calendar.HOUR_OF_DAY);
        hF=calendRef.get(Calendar.HOUR_OF_DAY);
        jD=debut_date.get(Calendar.DAY_OF_MONTH);
        jF=calendRef.get(Calendar.DAY_OF_MONTH);
        moisD=debut_date.get(Calendar.MONTH)+1;
        moisF=calendRef.get(Calendar.MONTH)+1;
        anneD= debut_date.get(Calendar.YEAR);
        anneF= calendRef.get(Calendar.YEAR);
        nom_Tache_txt.setText(t.getName());
        desc_Tache_txt.setText(t.getDescription());
        tacheFin.setText(jF+"/" +moisF+"/"+anneF+" "+hF+" h :"+minF+" m");
        tacheDebut.setText(jD+"/" +moisD+"/"+anneD+" "+hD+" h :"+minD+" m");
        choix="";
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
        PendingIntent pendingIntenta = PendingIntent.getBroadcast(this, 1, intent, 0);
        PendingIntent pendingIntentb = PendingIntent.getBroadcast(this, 2, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntenta);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, d.getTimeInMillis(),pendingIntentb);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntenta = PendingIntent.getBroadcast(this, 1, intent, 0);
        PendingIntent pendingIntentb = PendingIntent.getBroadcast(this, 2, intent, 0);
        alarmManager.cancel(pendingIntenta);
        alarmManager.cancel(pendingIntentb);
    }

    public void goSupprimer(View v){
        AppDatabase instance = AppDatabase.getInstance(this.getApplicationContext());
        instance.taskDao().deleteTask(t);
        Intent intent = new Intent();
        intent.putExtra("choix_action",1);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void goTerminer(View v){
        AppDatabase instance = AppDatabase.getInstance(this.getApplicationContext());
        if(t.getTermine()==false){
            t.setTermine(true);
            if(t.getDateFin().getTime()> System.currentTimeMillis()){
                t.setAtemps(true);
            }
        }
        else{
            t.setTermine(false);
            t.setAtemps(false);
        }
        cancelAlarm();
        instance.taskDao().updateTask(t);
        Intent intent = new Intent();
        intent.putExtra("choix_action",2);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void goModifier(View v){
        Date dstart=new java.sql.Date(debut_date.getTimeInMillis());
        Date end=new java.sql.Date(calendRef.getTimeInMillis());
        String nom=nom_Tache_txt.getText().toString();
        String description=desc_Tache_txt.getText().toString();
        int num_priorite = 3;
        if(priorite_spinner.getSelectedItem().toString().equals("High")){
            num_priorite = 1;
        }
        else if(priorite_spinner.getSelectedItem().toString().equals("Default")){
            num_priorite = 2;
        }
        if(nom.equals("")){
            Toast.makeText(this,"Le nom de la tâche est vide",Toast.LENGTH_SHORT).show();
        }
        if(debut_date.getTimeInMillis()>calendRef.getTimeInMillis()){
            Toast.makeText(this, "La date de début antérieur à date de fin!",Toast.LENGTH_SHORT).show();
        }
        else {
            t.setName(nom);
            t.setDescription(description);
            t.setDateDebut(dstart);
            t.setDateFin(end);
            t.setPriorite(num_priorite);
            t.setAtemps(t.isAtemps());
            t.setTermine(t.getTermine());
            AppDatabase instance = AppDatabase.getInstance(this.getApplicationContext());
            instance.taskDao().updateTask(t);
            startAlarm(calendRef,rappelRef);
            Intent intent = new Intent();
            intent.putExtra("choix_action",3);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
