package innovations.tech.platinum.organizingunitcaccf;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfRecordsActivity extends AppCompatActivity {

    DatabaseReference mDatabaseReference ;
    List<Records> RecordsListSet = new ArrayList<>();
    String date,edited,pcUsed,recordBy;
    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_records);
        progressDialog  = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Records List");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try{
            new RecordsTask().execute();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void receive(String _title , String type , String value){
        switch (type){
            case "Date":
                date = value;
                break;
            case "Pc Owner":
                pcUsed = value;
                break;
            case "Name Of Recorder":
                recordBy = value;
                break;
            case "Edited":
                edited = value;
                break;
        }
        Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
        Records records = new Records(_title,edited,date,pcUsed,recordBy);
        RecordsListSet.add(records);
    }


    private class RecordsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("Recordings");
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                        final String title = childSnapshot.getKey();

                        DatabaseReference childDatabaseReference = FirebaseDatabase.getInstance().getReference("Recordings/"+title);
                        childDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                                    if(childSnapshot.getKey().equals("Date")){
                                        DatabaseReference childChildDatabaseReference = FirebaseDatabase.getInstance().getReference("Recordings/"+title.trim()+"/Date");
                                        childChildDatabaseReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String Date = dataSnapshot.getValue(String.class);
                                                receive(title,"Date",Date);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    if(childSnapshot.getKey().equals("Pc Owner")){
                                        DatabaseReference childChildDatabaseReference = FirebaseDatabase.getInstance().getReference("Recordings/"+title+"/Pc Owner");
                                        childChildDatabaseReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String PcUsed = dataSnapshot.getValue(String.class);
                                                receive(title,"Pc Owner",PcUsed);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    if(childSnapshot.getKey().equals("Name Of Recorder")){
                                        DatabaseReference childChildDatabaseReference = FirebaseDatabase.getInstance().getReference("Recordings/"+title+"/Name Of Recorder");
                                        childChildDatabaseReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String RecordBy = dataSnapshot.getValue(String.class);
                                                receive(title,"Name Of Recorder",RecordBy);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    if(childSnapshot.getKey().equals("Edited")){
                                        DatabaseReference childChildDatabaseReference = FirebaseDatabase.getInstance().getReference("Recordings/"+title+"/Edited");
                                        childChildDatabaseReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String Edited = dataSnapshot.getValue(String.class);
                                                receive(title,"Edited",Edited);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try{
                progressDialog.cancel();
                progressDialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }

            RecyclerView recordsList = (RecyclerView) findViewById(R.id.RecordsListView);
            recordsList.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recordsList.setLayoutManager(mLayoutManager);

            RecyclerView.Adapter mAdapter = new MyAdapter(RecordsListSet);
            mAdapter.notifyDataSetChanged();
            recordsList.setAdapter(mAdapter);
        }
    }

}
