package sg.edu.rp.c346.reservation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName, etTelephone, etSize, dateEt, timeEt;
    CheckBox checkBox;
    Button btReserve, btReset, saveBtn, retrieveBtn;
    String time, date;

    DatePickerDialog dateDialog;
    TimePickerDialog timeDialog;

    int day, month, year, hour, min;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("P11 Reservation Enhanced");

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);
        dateEt = findViewById(R.id.dateEt);
        timeEt = findViewById(R.id.timeEt);

        saveBtn = findViewById(R.id.saveBtn);
        retrieveBtn = findViewById(R.id.retrieveBtn);

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                } else {
                    isSmoke = "non-smoking";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //View previewDialog = getLayoutInflater().inflate(R.layout.preview, null);

                builder.setTitle("Confirm Your Order");

                builder.setMessage("New Reservation \nName:" + etName.getText().toString() + "\nSmoking: "
                        + isSmoke + "\nSize: "
                        +  etSize.getText().toString() + "\nDate: " + date + "\nTime: " + time);

                builder.setPositiveButton("CONFIRM", null);
                builder.setNeutralButton("CANCEL", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                editor = sharedPreferences.edit();

                editor.putString("date", date);
                editor.putString("time", time);
                editor.putString("name", etName.getText().toString());
                editor.putString("size", etSize.getText().toString());
                editor.putString("num", etTelephone.getText().toString());
                editor.putBoolean("smoke", checkBox.isChecked());

                editor.commit();
            }
        });

        retrieveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                String date = sharedPreferences.getString("date", "");
                String time = sharedPreferences.getString("time", "");
                String name = sharedPreferences.getString("name", "");
                String size = sharedPreferences.getString("size", "");
                String num = sharedPreferences.getString("num", "");
                boolean smoke = sharedPreferences.getBoolean("smoke", false);

                dateEt.setText(date);
                timeEt.setText(time);
                etName.setText(name);
                etSize.setText(size);
                etTelephone.setText(num);
                checkBox.setChecked(smoke);
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText(null);
                etTelephone.setText(null);
                etSize.setText(null);
                checkBox.setChecked(false);
                dateEt.setText(null);
                timeEt.setText(null);
                dateDialog.updateDate(year, month, day);
                timeDialog.updateTime(hour, min);
            }
        });

        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "/" + (month+1) + "/" + year;
                        dateEt.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                };

                dateDialog = new DatePickerDialog(MainActivity.this, dateListener, year, month, day);
                dateDialog.show();

            }
        });

        timeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time = hourOfDay + ":" + minute;
                        timeEt.setText(hourOfDay + ":" + minute);
                    }
                };

                timeDialog = new TimePickerDialog(MainActivity.this, timeListener, hour, min, false);
                timeDialog.show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        String date = sharedPreferences.getString("date", "");
        String time = sharedPreferences.getString("time", "");
        String name = sharedPreferences.getString("name", "");
        String size = sharedPreferences.getString("size", "");
        String num = sharedPreferences.getString("num", "");
        boolean smoke = sharedPreferences.getBoolean("smoke", false);

        dateEt.setText(date);
        timeEt.setText(time);
        etName.setText(name);
        etSize.setText(size);
        etTelephone.setText(num);
        checkBox.setChecked(smoke);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = sharedPreferences.edit();

        editor.putString("date", date);
        editor.putString("time", time);
        editor.putString("name", etName.getText().toString());
        editor.putString("size", etSize.getText().toString());
        editor.putString("num", etTelephone.getText().toString());
        editor.putBoolean("smoke", checkBox.isChecked());

        editor.commit();
    }
}