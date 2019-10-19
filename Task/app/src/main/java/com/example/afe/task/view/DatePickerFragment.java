package com.example.afe.task.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int jour= c.get(Calendar.DAY_OF_MONTH);
        int mois=c.get(Calendar.MONTH);
        int anne= c.get(Calendar.YEAR);
        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(), anne, mois, jour);
    }
}
