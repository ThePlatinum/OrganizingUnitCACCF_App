package innovations.tech.platinum.organizingunitcaccf;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.internal.zzbke;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    public EditText mEmailView;
    public EditText mPasswordView;
    public ProgressBar mProgressView;
    public TextView loginMessage;

    private FirebaseAuth mFirebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (EditText)findViewById(R.id.email);
        mPasswordView = (EditText)findViewById(R.id.password);
        mProgressView = (ProgressBar)findViewById(R.id.login_progress);

        mFirebaseAuth = FirebaseAuth.getInstance();

    }
    public void LoginButtonPressed(View view){
        String Email = mEmailView.getText().toString();
        String Password = mPasswordView.getText().toString();
        if (Email.isEmpty() || Email.equals("")){
            mEmailView.setHint("Field can't be empty");
            mEmailView.setHintTextColor(990099);
        }
        if (Password.isEmpty() || Password.equals("")){
            mPasswordView.setHint("Field can't be empty");
            mPasswordView.setHintTextColor(990099);
        }
        else{
            mProgressView.setVisibility(View.VISIBLE);
            ActionLogin(Email , Password);
        }
    }
    public void ActionLogin(String Email , String Password){
        mFirebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(),NewRecordingActivity.class);
                            startActivity(intent);
                        }
                        else{
                            mProgressView.setVisibility(View.GONE);
                            String e = "";
                            try{
                                e = ((FirebaseAuthException)task.getException()).getErrorCode();
                            }
                            catch (NullPointerException nullException){
                                nullException.printStackTrace();
                            }
                            switch (e){
                                case "ERROR_INVALID_EMAIL":
                                    loginMessage = (TextView)findViewById(R.id.loginMessage);
                                    loginMessage.setText("Please Check the Email and try again\n");
                                    break;
                                case "ERROR_WRONG_PASSWORD":
                                    loginMessage = (TextView)findViewById(R.id.loginMessage);
                                    loginMessage.setText("Wrong password, Please check and try again\n");
                                    break;
                                default:
                                    loginMessage = (TextView)findViewById(R.id.loginMessage);
                                    loginMessage.setText("Network Connection Error\n Please Check your Connection and try again");
                            }
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
