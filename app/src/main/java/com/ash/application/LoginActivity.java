package com.ash.application;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import com.firebase.client.AuthData;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    private Firebase myFirebaseRef;
    private User user;
    private EditText email;
    private EditText password;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myFirebaseRef = new Firebase("https://listplusplus-jps.firebaseio.com/");

    }

    @Override
    protected void onStart() {
        super.onStart();
        email = (EditText) findViewById(R.id.edit_text_email_id);
        password = (EditText) findViewById(R.id.edit_text_password);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_login);
        checkUserLogin();
    }

    protected void setUpUser() {
        user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
    }

    public void onSignUpClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onLoginClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);
        setUpUser();
        aunthenticateUserLogin();
    }


    private void checkUserLogin() {
        //getAuth Returns the current authentication state of the Firebase client. If the client is unauthenticated, this method will return null.
        // Otherwise, the return value will be an object containing at least the fields such as uid,provider,token,expires,auth
        // https://www.firebase.com/docs/web/api/firebase/getauth.html,
        if (myFirebaseRef.getAuth() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            String uid = myFirebaseRef.getAuth().getUid();
            intent.putExtra("user_id", uid);
            startActivity(intent);
            finish();
        }
    }


    private void aunthenticateUserLogin() {
        //authWithPassword method attempts to authenticate to Firebase with the given credentials.
        //Paramters Are :
        // email - The email for the user to authenticate
        // password - The password for the account
        // handler - A handler which will be called with the result of the authentication attempt
        myFirebaseRef.authWithPassword(
                user.getEmail(),
                user.getPassword(),
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //Log.i("TOKEN",authData.getToken());
                        //Log.i("PROVIDER",authData.getProvider());
                        //Log.i("UID",authData.getUid());
                        //Log.i("AUTH_MAP",authData.getAuth().toString());
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        String uid = myFirebaseRef.getAuth().getUid();
                        intent.putExtra("user_id", uid);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
    }

}