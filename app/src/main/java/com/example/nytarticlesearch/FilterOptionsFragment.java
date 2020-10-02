package com.example.nytarticlesearch;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class FilterOptionsFragment extends DialogFragment {

    EditText mEtDatePicker;
    public FilterOptionsFragment(){
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterOptionsFragment newInstance(String title) {
        FilterOptionsFragment filterOptions = new FilterOptionsFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        filterOptions.setArguments(args);
        return filterOptions;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_filter_options,container);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        final DatePicker dpDatePicker =(DatePicker) view.findViewById(R.id.dpDatePicker);
        dpDatePicker.setVisibility(View.GONE);

        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        mEtDatePicker = (EditText) view.findViewById(R.id.etDatePicker);
        mEtDatePicker.setShowSoftInputOnFocus(false);
        mEtDatePicker.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                dpDatePicker.setVisibility(View.VISIBLE);
            }
        });

        dpDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                dpDatePicker.setVisibility(View.INVISIBLE);
                updateDatePicker(i,i1,i2);
            }
        });
        setupCheckboxes(view);
    }

    public void updateDatePicker(int year,int month,int date){
        mEtDatePicker.setText(String.format("%s/%s/%s",month,date,year));
    }
    public void setupCheckboxes(final View view){
        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Toast.makeText(view.getContext(),compoundButton.getText(),Toast.LENGTH_SHORT).show();
            }
        };
        CheckBox cbArts = (CheckBox) view.findViewById(R.id.cbArts);
        CheckBox cbSports =(CheckBox) view.findViewById(R.id.cbSports);
        CheckBox cbFashion = (CheckBox) view.findViewById(R.id.cbFashion);
        cbArts.setOnCheckedChangeListener(checkListener);
        cbSports.setOnCheckedChangeListener(checkListener);
        cbFashion.setOnCheckedChangeListener(checkListener);
    }
}
