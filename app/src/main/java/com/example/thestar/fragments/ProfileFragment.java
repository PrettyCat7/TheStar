package com.example.thestar.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thestar.Database.FirebaseServices;
import com.example.thestar.Database.User1;
import com.example.thestar.Utilites.Utlis;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import com.example.thestar.R;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 123;
    private ImageView ivUser;
    ActivityResultLauncher<Intent> resultLauncher;
    EditText etFirstName, etLastName;
    private Button btnUpdate;
    private FirebaseServices fbs;
    private Utlis utils;
    private String imageStr;
    private boolean flagAlreadyFilled = false;
    private Button btnback;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        fbs = FirebaseServices.getInstance();
        etFirstName = getView().findViewById(R.id.etFirstnameUserDetailsEdit);
        etLastName = getView().findViewById(R.id.etLastnameUserDetailsEdit);
        btnback = getView().findViewById(R.id.btnbackprofile);
        //
        //
        btnUpdate = getView().findViewById(R.id.btnUpdateUserDetailsEdit);
        utils = utils.getInstance();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoallstories();
            }
        });
        ivUser = getView().findViewById(R.id.ivuserprofile);
        if (imageStr == null) {
            Picasso.get().load(R.drawable.ic_launcher_foreground).into(ivUser);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Data validation
                    String firstname = etFirstName.getText().toString();
                    String lastname = etLastName.getText().toString();
                    if (firstname.trim().isEmpty() || lastname.trim().isEmpty()) {

                        Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();

                        User1 current = fbs.getCurrentUser();
                        if (current != null) {
                            if (!current.getFirstName().equals(firstname) ||
                                    !current.getLastName().equals(lastname) ||
                                    !current.getPhoto().equals(fbs.getSelectedImageURL().toString())) {
                                User1 user;
                                if (fbs.getSelectedImageURL() != null)
                                    user = new User1(firstname, lastname, fbs.getAuth().getCurrentUser().getEmail(), fbs.getSelectedImageURL().toString());
                                else
                                    user = new User1(firstname, lastname, fbs.getAuth().getCurrentUser().getEmail(), "");

                                fbs.updateUser(user);
                                utils.showMessageDialog(getActivity(), "Data updated succesfully!");
                                fbs.reloadInstance();
                            } else {
                                utils.showMessageDialog(getActivity(), "No changes!");
                            }
                        }
                    }
                }
            });
            ivUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });
            fillUserData();
            flagAlreadyFilled = true;
        }


    }
    private void fillUserData() {
        if (flagAlreadyFilled)
            return;
        User1 current = fbs.getCurrentUser();
        if (current != null) {
            etFirstName.setText(current.getFirstName());
            etLastName.setText(current.getLastName());

            if (current.getPhoto() != null && !current.getPhoto().isEmpty()) {
                Picasso.get().load(current.getPhoto()).into(ivUser);
                fbs.setSelectedImageURL(Uri.parse(current.getPhoto()));
            }
        }
    }

    public void uploadImage(Uri selectedImageUri) {
        if (selectedImageUri != null) {
            imageStr = "images/" + UUID.randomUUID() + ".jpg"; //+ selectedImageUri.getLastPathSegment();
            StorageReference imageRef = fbs.getStorage().getReference().child("images/" + selectedImageUri.getLastPathSegment());

            UploadTask uploadTask = imageRef.putFile(selectedImageUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //selectedImageUri = uri;
                            fbs.setSelectedImageURL(uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    btnUpdate.setEnabled(true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    btnUpdate.setEnabled(true);
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please choose an image first", Toast.LENGTH_SHORT).show();
        }
    }

    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                btnUpdate.setEnabled(false);
                // Get the image's URI
                final android.net.Uri imageUri = data.getData();

                // Load the image into the ImageView using an asynchronous task or a library like Glide or Picasso
                Picasso.get().load(fbs.getCurrentUser().getPhoto()).into(ivUser);
                uploadImage(imageUri);
            }
        }
    }



    private void gotoallstories() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new AllStoriesFragment());
        ft.commit();
    }
}