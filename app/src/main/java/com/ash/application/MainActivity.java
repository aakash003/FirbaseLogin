package com.ash.application;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Firebase myFirebaseRef;
    private ActionBar actionBar;
    private String[] tabs={"Inbox","Friends"};
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        //Creates a reference for  your Firebase database
        myFirebaseRef = new Firebase("https://listplusplus-jps.firebaseio.com/");


        //create the adapter that will return a fragment for each of the two
        mSectionsPagerAdapter = new SectionsPagerAdapter(this,getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        actionBar.setDisplayHomeAsUpEnabled(false);

        //Adding Tabs
        for(String tab_name : tabs)
        {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            public void onPageScrollStateChanged(int arg0){

            }
        });



    }




    @Override
    protected void onStart() {
        super.onStart();

        //Get the uid for the currently logged in User from intent data passed to this activity
        String uid = getIntent().getExtras().getString("user_id");
        //Reffering to the name of the User who has logged in currently and adding a valueChangeListener
        myFirebaseRef.child("users").child(uid).child("name").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //A firebase reference to the welcomeText can be created in following ways :
        // You can use this :
        //Firebase myAnotherFirebaseRefForWelcomeText=new Firebase("https://androidbashfirebase.firebaseio.com/welcomeText");*/
        //OR

        myFirebaseRef.child("welcomeText").addValueEventListener(new ValueEventListener() {
            //onDataChange is called every time the data changes in your Firebase Database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Inside onDataChange we can get the data as an Object from the dataSnapshot
                //getValue returns an Object. We can specify the type by passing the type expected as a parameter
                String data = dataSnapshot.getValue(String.class);
            }

            //onCancelled is called in case of any error
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_edit_friends) {
            Intent intent= new Intent(this,EditFriendsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    /**
         * Returns a new instance of this fragment for the given section
         * number.
         */

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */


