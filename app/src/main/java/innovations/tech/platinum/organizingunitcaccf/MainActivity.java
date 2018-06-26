package innovations.tech.platinum.organizingunitcaccf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import innovations.tech.platinum.organizingunitcaccf.contacts.ContactsActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference firebaseDatabase;
    private TextView power;
    private TextView sound;
    private TextView projection;
    private TextView audioVideo;
    String ValPower , ValSound , ValProjection ,ValAudioVideo;
    FirebaseUser firebaseUser;
    TextView mainName;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    FirebaseDatabase mFirebaseDatabase;
    ImageView profilePic;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        profilePic = (ImageView) findViewById(R.id.ProfilePics);
        power = (TextView)findViewById(R.id.power);
        sound = (TextView)findViewById(R.id.sound);
        projection = (TextView)findViewById(R.id.projection);
        audioVideo = (TextView)findViewById(R.id.audioVideo);
        mainName = (TextView) findViewById(R.id.mainName);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            FillFields();
        }catch (Exception e){
            e.printStackTrace();
        }

        FillFields();

        if((firebaseUser.getUid().isEmpty())){
            mainName.setText(R.string.clickToLogin);
            mainName.setTextColor(999999);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference ValPowerRef = firebaseDatabase.child("Power");
        DatabaseReference ValSoundRef = firebaseDatabase.child("Sound");
        DatabaseReference ValProjectionRef = firebaseDatabase.child("Projection");
        DatabaseReference ValAudioVideoRef = firebaseDatabase.child("AudioVideo");

        ValAudioVideoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ValAudioVideo = dataSnapshot.getValue(String.class);
                audioVideo.setText(ValAudioVideo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ValProjectionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ValProjection = dataSnapshot.getValue(String.class);
                projection.setText(ValProjection);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ValSoundRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ValSound = dataSnapshot.getValue(String.class);
                sound.setText(ValSound);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ValPowerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ValPower = dataSnapshot.getValue(String.class);
                power.setText(ValPower);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_addRecording:
                Intent intentAdd = new Intent(this , LoginActivity.class);
                startActivity(intentAdd);
                break;
            case R.id.nav_mission_vision:
                Intent intentMandV = new Intent(this,MisionAndVisionActivity.class);
                startActivity(intentMandV);
                break;
            case R.id.nav_Recording:
                startActivity(new Intent(this,ListOfRecordsActivity.class));
                break;
            case R.id.nav_learn:
                startActivity(new Intent(this,ChooseLearnActivity.class));
                break;
            case R.id.nav_contacts_list:
                startActivity(new Intent(this,ContactsActivity.class));
                break;
            case R.id.nav_gallery:
                startActivity(new Intent(this,GalleryActivity.class));
            case R.id.nav_exit:
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void profile(View view) {
        startActivity(new Intent(this,EditProfileActivity.class));
    }

    public void FillFields(){
        storageReference = FirebaseStorage.getInstance().getReference("Profile Pictures/"+firebaseUser.getUid());
        String imageName = storageReference.getName();

        try {
            final File template = File.createTempFile("Profile Pics","jpg");
            storageReference.child(imageName).getFile(template).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(template.getAbsolutePath());
                    profilePic.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseReference NameDatabaseReference = mFirebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/Name");
        NameDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.getValue(String.class);
                mainName.setText(Name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
