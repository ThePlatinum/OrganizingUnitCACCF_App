package innovations.tech.platinum.organizingunitcaccf;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewRecordingActivity extends AppCompatActivity {

    DatabaseReference mDatabaseReference;
    EditText RecordedBy, Title, pcOwner, preacher, event, dateDay, dateMonth, dateYear;
    CheckBox Edited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recording);

        RecordedBy = (EditText) findViewById(R.id.RecordedBy);
        Title = (EditText) findViewById(R.id.Title);
        pcOwner = (EditText) findViewById(R.id.pcOwner);
        preacher = (EditText) findViewById(R.id.preacher);
        event = (EditText) findViewById(R.id.event);

        dateDay = (EditText) findViewById(R.id.dateDay);
        dateMonth = (EditText) findViewById(R.id.dateMonth);
        dateYear = (EditText) findViewById(R.id.dateYear);

        Edited = (CheckBox) findViewById(R.id.edited);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public void onClickSave(View v) {
        String mRecordedBy = RecordedBy.getText().toString();
        String mTitle = Title.getText().toString();
        String mpcOwner = pcOwner.getText().toString();
        String mpreacher = preacher.getText().toString();
        String mevent = event.getText().toString();

        String date = dateDay.getText().toString() + ":" + dateMonth.getText().toString() + ":" + dateYear.getText().toString();

        String mEdited;
        if (Edited.isChecked()) {
            mEdited = "true";
        } else {
            mEdited = "false";
        }

        Boolean Validated = validate(mRecordedBy, mTitle, mpcOwner, mpreacher, mevent, date);
        if (Validated) {
            Map<String, Object> Record = new HashMap<>();
            Record.put("Name Of Recorder", mRecordedBy);
            Record.put("Title", mTitle);
            Record.put("PC Owner", mpcOwner);
            Record.put("Preacher", mpreacher);
            Record.put("Program", mevent);
            Record.put("Date", date);
            Record.put("Edited", mEdited);

            mDatabaseReference = FirebaseDatabase.getInstance().getReference("Recordings");
            DatabaseReference RecordingsDatabaseReference = mDatabaseReference.child(mTitle);
            RecordingsDatabaseReference.setValue(Record);


            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else {
            TextView validateText = (TextView)findViewById(R.id.validateText);
            validateText.setText("Invalid Inputs!\n All fields must be correctly filled, Please check and try again.");
        }
    }

    private Boolean validate(String mRecordedBy, String mTitle, String mpcOwner, String mpreacher, String mevent, String date) {
        return !(mRecordedBy.length() < 2 || mTitle.length() < 2 || mpcOwner.length() < 2 || mpreacher.length() < 2 || mevent.length() < 2) && !(date.equals(" : : ") || date.equals("::"));
    }


    public void onClickCancel(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel")
                .setMessage(" Sure to Cancel?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}