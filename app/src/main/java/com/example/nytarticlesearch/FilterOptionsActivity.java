package com.example.nytarticlesearch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class FilterOptionsActivity extends AppCompatActivity {

    EditText mEtDatePicker;
    Button mSaveButton;
    Spinner mSortOrder;
    CheckBox mCbArts;
    CheckBox mCbSports;
    CheckBox mCbFashion;
    boolean mArtsFilter;
    boolean mSportsFilter;
    boolean mFashionFilter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_options);

        final DatePicker dpDatePicker =(DatePicker) findViewById(R.id.dpDatePicker);
        dpDatePicker.setVisibility(View.GONE);

        mEtDatePicker = (EditText) findViewById(R.id.etDatePicker);
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
                updateEtDatePicker(i,i1,i2);
            }
        });
        setupCheckboxes();
        mSortOrder =(Spinner) findViewById(R.id.spSortOrderPicker);
        mSortOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                System.out.println("spinner selected item "+parent.getItemAtPosition(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortorder_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSortOrder.setAdapter(adapter);
        mSaveButton= (Button) findViewById(R.id.btSave);
    }

    public void saveFilterOptions(View view){
        Intent filtersData = new Intent();
        System.out.println("date selected"+mEtDatePicker.getText());
        filtersData.putExtra("start_date",mEtDatePicker.getText().toString());
        filtersData.putExtra("arts",mArtsFilter);
        filtersData.putExtra("fashion",mFashionFilter);
        filtersData.putExtra("sports",mSportsFilter);
        setResult(RESULT_OK,filtersData);
        System.out.println("sending data:- date"+filtersData.getStringExtra("start_date")+
                ", arts: "+filtersData.getBooleanExtra("arts",false)
                );
        finish();
    }
    public void updateEtDatePicker(int year,int month,int date){
        mEtDatePicker.setText(String.format("%s/%s/%s",month,date,year));
    }
    public void setupCheckboxes(){
        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                switch (compoundButton.getId()){
                    case (R.id.cbArts):
                        mArtsFilter=checked;
                        break;
                    case (R.id.cbFashion):
                        mFashionFilter=checked;
                        break;
                    case (R.id.cbSports):
                        mSportsFilter=checked;
                        break;
                    default:
                        break;
                }
                //Toast.makeText(this.getContext(),compoundButton.getText(),Toast.LENGTH_SHORT).show();
            }
        };
        mCbArts = (CheckBox) findViewById(R.id.cbArts);
        mCbSports =(CheckBox) findViewById(R.id.cbSports);
        mCbFashion = (CheckBox) findViewById(R.id.cbFashion);
        mCbArts.setOnCheckedChangeListener(checkListener);
        mCbSports.setOnCheckedChangeListener(checkListener);
        mCbFashion.setOnCheckedChangeListener(checkListener);
    }
}
