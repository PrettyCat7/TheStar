package com.example.thestar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private Stack<Fragment> fragmentStack = new Stack<>();
    // TODO: add fbs propety

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* TODO: connect fbs
            * check if alrady logged in
            * if (fbs.getAuth().getCurrenUser() == null)
                        gotoLoginfragment();
              else
                .. goto all stories
         */
        gotoLoginfragment();
        // go();
        //gotodeatails();
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