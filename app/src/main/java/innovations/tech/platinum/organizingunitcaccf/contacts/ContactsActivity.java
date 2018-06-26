package innovations.tech.platinum.organizingunitcaccf.contacts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import innovations.tech.platinum.organizingunitcaccf.R;

public class ContactsActivity extends AppCompatActivity {

    List<Contacts> ContactsListSet = new ArrayList<>();
    String Contact_ImageUri , Contact_Name , Contact_Phone , Contact_Email;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        databaseReference = FirebaseDatabase.getInstance().getReference("Contacts");

        RecyclerView contactsList = (RecyclerView) findViewById(R.id.contactsListView);
        contactsList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        contactsList.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new ContactsAdapter(ContactsListSet);
        mAdapter.notifyDataSetChanged();
        contactsList.setAdapter(mAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Contact_Name = childSnapshot.getKey();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Contacts contacts = new Contacts("","Adesina Emmanuel","09033567019","platinumemirate@gmail.com");
        ContactsListSet.add(contacts);

        Contacts contacts2 = new Contacts("","Platinum","09082787893","adedayo6192@gmail.com");
        ContactsListSet.add(contacts2);
    }

    public void chat_with_contact(View view) {
    }

    public void send_message_to_contact(View view) {
        try{
            TextView email = (TextView)findViewById(R.id.contact_email);
            String C_mail = email.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SEND).putExtra(Intent.EXTRA_EMAIL,C_mail);
            startActivity(Intent.createChooser(intent,"Send Mail"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Call Contact
    public void call_contact(View view) {
        try{
            TextView number = (TextView)findViewById(R.id.contact_number);
            String C_number = number.getText().toString();

            Uri numb = Uri.parse("tel:" + C_number);
            Intent intent = new Intent(Intent.ACTION_DIAL,numb);
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
