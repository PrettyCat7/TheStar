package com.example.thestar.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thestar.Database.FirebaseServices;
import com.example.thestar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private TextView ForgotLink,SignUpLink;
    private EditText usernamel,passwordlogin;
    private Button btnlogin;
    private FirebaseServices fbs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fbs=FirebaseServices.getInstance();
        usernamel=getView().findViewById(R.id.etemaillogin);
        passwordlogin=getView().findViewById(R.id.etpasswordlogin);
        btnlogin=getView().findViewById(R.id.btnlogin);
        ForgotLink=getView().findViewById(R.id.tvForgotLogin);
        SignUpLink=getView().findViewById(R.id.tvSignupoLogin);
        SignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {gotoSignUpFragment();
            }
        });
        ForgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoForgotPasswordFragment();
            }
        });
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username=usernamel.getText().toString();
                    String password=passwordlogin.getText().toString();

                    if(username.trim().isEmpty()||password.trim().isEmpty()){
                        Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                        return;


                    }
                    fbs.getAuth().signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                                gotoAddStoryFragment();
                            }
                            else {
                                Toast.makeText(getActivity(), "check your data", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });
    }

    private void gotoAddStoryFragment() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddStoryFragment());
        ft.commit();
    }

    private void gotoSignUpFragment() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new SignUpFragment());
        ft.commit();
    }

    private void gotoForgotPasswordFragment() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new ForgotPasswordFragment());
        ft.commit();
    }
    private void gotoallstoriesfragment() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AllStoriesFragment());
        ft.commit();
    }
}