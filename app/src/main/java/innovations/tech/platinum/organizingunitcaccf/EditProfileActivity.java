package innovations.tech.platinum.organizingunitcaccf;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity{

    TextView UserName , UserPhone , UserEmail , UserHostelAddress;
    Button UserDOB;
    String _UserName ,_UserPhone , _UserEmail , _UserDOB , _UserHostelAddress;
    LinearLayout editProfileButtons;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    ImageView ProfileAvater;
    FirebaseDatabase firebaseDatabase;
    Uri profilePics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();

        editProfileButtons = (LinearLayout)findViewById(R.id.editProfileButtons);
        UserName = (TextView)findViewById(R.id.UserName);
        UserPhone = (TextView)findViewById(R.id.UserPhone);
        UserEmail = (TextView)findViewById(R.id.UserEmail);
        UserDOB = (Button)findViewById(R.id.UserDOB);
        UserHostelAddress = (TextView)findViewById(R.id.UserHostelAddress);
        ProfileAvater = (ImageView)findViewById(R.id.profileAvatar);
        Active();
        FillFields();
    }

    int requestCodeChoosePics = 100;
    public void selectProPics(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        startActivityForResult(intent,requestCodeChoosePics);
    }

    public void editProfile(View view) {
        if(editProfileButtons.getVisibility() != View.VISIBLE){
            editProfileButtons.setVisibility(View.VISIBLE);
            Active();
        }
        else{
            editProfileButtons.setVisibility(View.INVISIBLE);
            Active();
        }
    }

    private void Active(){
        if(editProfileButtons.getVisibility() != View.VISIBLE){
            editProfileButtons.setEnabled(false);
            UserName.setEnabled(false);
            UserPhone.setEnabled(false);
            UserEmail.setEnabled(false);
            UserDOB.setClickable(false);
            ProfileAvater.setClickable(false);
            UserHostelAddress.setEnabled(false);
        }else{
            editProfileButtons.setEnabled(true);
            UserName.setEnabled(true);
            UserPhone.setEnabled(true);
            UserEmail.setEnabled(false);
            UserDOB.setClickable(true);
            ProfileAvater.setClickable(true);
            UserHostelAddress.setEnabled(true);

        }
    }

    public void SaveProfile(View view) {
        editProfileButtons.setVisibility(View.INVISIBLE);
        Active();

        _UserName = UserName.getText().toString();
        _UserPhone = UserPhone.getText().toString();
        _UserEmail = UserEmail.getText().toString();
        _UserDOB = UserDOB.getText().toString();
        _UserHostelAddress = UserHostelAddress.getText().toString();

        DatabaseReference User = firebaseDatabase.getReference("Users").child(firebaseUser.getUid());
        Map<String, Object> MapUser = new HashMap<>();
        MapUser.put("Name",_UserName);
        MapUser.put("Phone",_UserPhone);
        MapUser.put("Email",_UserEmail);
        MapUser.put("DateOfBirth",_UserDOB);
        MapUser.put("HostelAddress",_UserHostelAddress);
        MapUser.put("uid",firebaseUser.getUid());

        User.setValue(MapUser);
    }

    public void CancelSaveProfile(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel")
                .setMessage(" Sure to Cancel?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editProfileButtons.setVisibility(View.INVISIBLE);
                        Active();
                        FillFields();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                });
        builder.show();
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
                    ProfileAvater.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseReference NameDatabaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/Name");
        NameDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.getValue(String.class);
                UserName.setText(Name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference PhoneDatabaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/Phone");
        PhoneDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Phone = dataSnapshot.getValue(String.class);
                UserPhone.setText(Phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference EmailDatabaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/Email");
        EmailDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Email = dataSnapshot.getValue(String.class);
                UserEmail.setText(Email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference DateOfBirthDatabaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/DateOfBirth");
        DateOfBirthDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String DateOfBirth = dataSnapshot.getValue(String.class);
                UserDOB.setText(DateOfBirth);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference HostelAddressDatabaseReference = firebaseDatabase.getReference("Users/"+firebaseUser.getUid()+"/HostelAddress");
        HostelAddressDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String HostelAddress = dataSnapshot.getValue(String.class);
                UserHostelAddress.setText(HostelAddress);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCodeChoosePics && resultCode == RESULT_OK){
            Uri uri = data.getData();
            profilePics = uri;
            ProfileAvater.setImageURI(uri);
            StorageReference profilepicspath = storageReference.child("Profile Pictures").child(firebaseUser.getUid()).child(uri.getLastPathSegment());
            profilepicspath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"Profile Picture Changed",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Couldn't save picture, try again",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void PickDate(View view) {
        DialogFragment dialogFragment = new mDatePikerDialog();
        dialogFragment.show(getFragmentManager(),"Pick Birth Date");
    }


    public static class mDatePikerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calender = Calendar.getInstance();
            int year = calender.get(Calendar.YEAR);
            int month = calender.get(Calendar.MONTH);
            int day = calender.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(), this , year , month , day );
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = dayOfMonth + ":" + (month + 1) + ":" + year ;
            Button bDate = (Button)getActivity().findViewById(R.id.UserDOB);
            bDate.setText(date);
        }
    }
}
