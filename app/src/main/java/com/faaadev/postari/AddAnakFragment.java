package com.faaadev.postari;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;


public class AddAnakFragment extends BottomSheetDialogFragment {
    private EditText birthdate;
    private int mYear, mMonth, mDay;
    private String month, day;
    private final Calendar c = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_add_anak, container, false);

         _init(root);

        return root;
    }

    private void _init (View root){
        birthdate = root.findViewById(R.id.birthdate);

        _implement();
    }

    private void _implement(){
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        if (mMonth < 10){
            month = "0"+(mMonth+1);
        } else {
            month = ""+(mMonth+1);
        }
        if (mDay < 10){
            day = "0"+mDay;
        } else {
            day = ""+mDay;
        }
        birthdate.setText(day + "/" + month + "/" + mYear);

        birthdate.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if (monthOfYear+1 < 10){
                                month = "0"+(monthOfYear+1);
                            } else {
                                month = ""+(monthOfYear+1);
                            }
                            if (dayOfMonth < 10){
                                day = "0"+dayOfMonth;
                            } else {
                                day = ""+dayOfMonth;
                            }
                            birthdate.setText(day + "/" + month + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }
}