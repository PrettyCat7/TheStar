package com.example.thestar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
   public void gotoLoginfragment(){

       FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
       ft.replace(R.id.frameLayout,new LoginFragment());
       ft.commit();

   }
}