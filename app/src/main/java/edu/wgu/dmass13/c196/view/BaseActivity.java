package edu.wgu.dmass13.c196.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.wgu.dmass13.c196.R;
import edu.wgu.dmass13.c196.globals.Helpers;

public class BaseActivity extends AppCompatActivity {

    protected android.support.v7.widget.Toolbar _toolbar = null;
    protected android.support.v7.app.ActionBar _ab = null;


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected int getContentView() {
        return -9999;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        // Find the toolbar view inside the activity layout
        _toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(_toolbar);
        // Get a support ActionBar corresponding to this toolbar
        _ab = getSupportActionBar();
        // Enable the Up button
        _ab.setDisplayHomeAsUpEnabled(true);
    }

    protected String GetStringValueFromEditText(EditText et) {
        return (et == null ? null : et.getText().toString());
    }

    protected Date GetDateValueFromEditText(EditText et) {
        if (et.getText() != null) {

            return Helpers.ConvertStringToDate(et.getText().toString());

        } else {
            return null;
        }
    }

    protected class myDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

        //https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext

        EditText _editText;
        Context _context;
        protected Calendar _calendar = Calendar.getInstance();

        public myDatePicker(EditText x, Context context) {
            _context = context;
            _editText = x;
            _editText.setOnClickListener(this);
        }


        protected void updateDateLabel(EditText editText) {
            editText.setText(Helpers.ConvertDateToString(_calendar.getTime()));
        }

        @Override
        public void onClick(View v) {
            DatePickerDialog dialog = new DatePickerDialog(_context, this,
                    _calendar.get(Calendar.YEAR), _calendar.get(Calendar.MONTH),
                    _calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        }

        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            _calendar.set(Calendar.YEAR, year);
            _calendar.set(Calendar.MONTH, monthOfYear);
            _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel(_editText);
        }
    }

}
