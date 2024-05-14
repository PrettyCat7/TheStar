package com.example.thestar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.Stack;

import Database.FirebaseServices;
import fragments.AllStoriesFragment;
import fragments.LoginFragment;
import fragments.StoriesDetails;

public class MainActivity extends AppCompatActivity {
    private Stack<Fragment> fragmentStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseServices fbs = FirebaseServices.getInstance();
      if (fbs.getAuth().getCurrentUser() == null) {
            gotoLoginfragment();
                        pushFragment(new LoginFragment());


        } else go();


        //gotodeatails();
      //  gotoLoginfragment();
    }

    private void gotodeatails() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new StoriesDetails());
        ft.commit();
    }

    public void gotoLoginfragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new LoginFragment());
        ft.commit();

    }

    public void go() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new AllStoriesFragment());
        ft.commit();

    }

    public void pushFragment(Fragment fragment) {
        fragmentStack.push(fragment);
        /*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit(); */
    }
}