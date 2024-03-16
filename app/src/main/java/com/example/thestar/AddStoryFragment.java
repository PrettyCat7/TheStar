package com.example.thestar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStoryFragment extends Fragment {

    private TextView tvAdd;
    private EditText etnameAdd,etdesAdd,etgenreAdd,etRating;
    private FirebaseServices fbs;
    private Button btnAdd;
    private Utlis utlis;
    private static final int GALLERY_REQUEST_CODE = 123;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddStoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStoryFragment newInstance(String param1, String param2) {
        AddStoryFragment fragment = new AddStoryFragment();
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
        return inflater.inflate(R.layout.fragment_add_story, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }

    private void connectComponents() {
        fbs=FirebaseServices.getInstance();
        etnameAdd=getView().findViewById(R.id.etnameadd);
        etdesAdd=getView().findViewById(R.id.etdesadd);
        etgenreAdd=getView().findViewById(R.id.etgenadd);
        etRating=getView().findViewById(R.id.etrateadd);
        btnAdd=getView().findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = etnameAdd.getText().toString();
                String Description = etdesAdd.getText().toString();
                String Genre = etgenreAdd.getText().toString();
                String Rating = etRating.getText().toString();

                if (Name.trim().isEmpty()||Description.trim().isEmpty()||Genre.trim().isEmpty()||Rating.trim().isEmpty() ){

                    Toast.makeText(getActivity(), "Some Of Your Fields Are Empty ):", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                      Story str = new Story(Name,Description,Genre, Rating);
                      fbs.getFire().collection("Stories").add(str).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                          @Override
                          public void onSuccess(DocumentReference documentReference) {
                              Toast.makeText(getActivity(), "Successfully Added Your Story (: ", Toast.LENGTH_SHORT).show();
                              FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                              ft.replace(R.id.frameLayout,new AllStoriesFragment());
                              ft.commit();
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Log.e("Fail Add Story :",e.getMessage());
                          }
                      });
                }
            }
            private void openGallery() {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }


        });






    }
}