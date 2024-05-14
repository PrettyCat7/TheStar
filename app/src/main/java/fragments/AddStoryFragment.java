package fragments;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Toast;

import Database.FirebaseServices;
import com.example.thestar.MainActivity;
import com.example.thestar.R;
import Database.Story;
import Utilites.Utlis;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStoryFragment extends Fragment {

    Utlis utlis;

    private EditText etnameAdd, etdesAdd, etgenreAdd;
    private RatingBar etRating;
    private FirebaseServices fbs;
    private Button btnAdd;


    private ImageView imgstr;
    private MultiAutoCompleteTextView multiAutoCompleteGenre;


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
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        utlis = Utlis.getInstance();
        fbs = FirebaseServices.getInstance();
        etnameAdd = getView().findViewById(R.id.etnameadd);
        etdesAdd = getView().findViewById(R.id.etdesadd);
        //etgenreAdd = getView().findViewById(R.id.etgenadd);
        btnAdd = getView().findViewById(R.id.btnAdd);
        imgstr = getView().findViewById(R.id.IVstr);
        etRating = getView().findViewById(R.id.ratingBar);
        multiAutoCompleteGenre = getView().findViewById(R.id.multiAutoCompleteGenre);
        multiAutoCompleteGenre.setThreshold(1);

        String[] genre = {"Action & Adventure", "Comedy", "Fantasy", "Science Fiction", "Mystery", "Horror", "Historical Fiction", "Romance", "Women’s Fiction ", " Literary Fiction", "Magical Realism", "Graphic Novel", "Short Story", "Children’s","Young Adult","New Adult ","Memoir & Autobiography","Food & Drink","Art & Photography","History","Travel","Crime","Science & Technology","Guide / How-to"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, genre);
        multiAutoCompleteGenre.setAdapter(adapter);
        multiAutoCompleteGenre.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        etRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    Toast.makeText(getActivity(), "Your have rated this story :" + rating +"  "+ "out of 5", Toast.LENGTH_SHORT).show();

                }
            }
        });

        imgstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        ((MainActivity) getActivity()).pushFragment(new AddStoryFragment());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFirestore();
            }

        });

    }

    private void addToFirestore() {
        String Name, Genre, Description, ratingStr,selectedGenres;
        selectedGenres = multiAutoCompleteGenre.getText().toString();
        ratingStr = String.valueOf(etRating.getRating());
        Name = etnameAdd.getText().toString();
      // if it didnt work  Genre = etgenreAdd.getText().toString();
        Description = etdesAdd.getText().toString();


        if (Name.trim().isEmpty() || selectedGenres.trim().isEmpty() || Description.trim().isEmpty() || ratingStr.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Check your inputs.", Toast.LENGTH_SHORT).show();
            return;
        }
        Story story1, story2;
        if (fbs.getSelectedImageURL() == null) {

            story1 = new Story(Name, selectedGenres, Description, ratingStr, "");


        } else {
            story1 = new Story(Name, selectedGenres, Description, ratingStr, fbs.getSelectedImageURL().toString());

        }
        fbs.getFire().collection("stories").add(story1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), "ADD Story is Succeed ", Toast.LENGTH_SHORT).show();
                Log.e("addToFirestore() - add to collection: ", "Successful!");
                gotoallstories();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to add the story please try again", Toast.LENGTH_SHORT).show();
                Log.e("addToFirestore() - add to collection: ", e.getMessage());
            }
        });

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_story, container, false);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imgstr.setImageURI(selectedImageUri);
            utlis.uploadImage(getActivity(), selectedImageUri);
        }
    }

    private void gotoallstories() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new AllStoriesFragment());
        ft.commit();

    }
}