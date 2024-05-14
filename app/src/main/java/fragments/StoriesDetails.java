package fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import Database.FirebaseServices;

import com.example.thestar.R;

import Database.Story;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoriesDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoriesDetails extends Fragment {
    private static final int PERMISSION_SEND_SMS = 1;
    private static final int REQUEST_CALL_PERMISSION = 2;
    private FirebaseServices fbs;
    private TextView name, genre, description;
    private ImageView ivstrPhoto, imgwhatsapp;
    private Story myStory;
    private Button btnWhatsapp;
    private boolean isEnlarged = false;
    private EditText message;
    private Uri uri;
    private String imgurl = "http://www.google.com";
    private RatingBar rating;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StoriesDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoriesDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static StoriesDetails newInstance(String param1, String param2) {
        StoriesDetails fragment = new StoriesDetails();
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
        return inflater.inflate(R.layout.fragment_stories_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        ImageView ivstrPhoto = getView().findViewById(R.id.storyImage);
        ivstrPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = ivstrPhoto.getLayoutParams();
                if (isEnlarged) {
                    layoutParams.height = 500;
                } else {
                    layoutParams.height = 2200;
                }
                ivstrPhoto.setLayoutParams(layoutParams);

                // נשנה את המצב הנוכחי של התמונה
                isEnlarged = !isEnlarged;
            }
        });
    }

    private void init() {
        fbs = FirebaseServices.getInstance();
        name = getView().findViewById(R.id.storyName);
        genre = getView().findViewById(R.id.storyGenre);
        description = getView().findViewById(R.id.storyDescription);
        rating = getView().findViewById(R.id.storyRatingD);

        ivstrPhoto = getView().findViewById(R.id.storyImage);


        Bundle args = getArguments();
        if (args != null) {
            myStory = args.getParcelable("story");
            if (myStory != null) {
                //String data = myObject.getData();
                // Now you can use 'data' as needed in FragmentB
                name.setText(myStory.getName());
                genre.setText(myStory.getGenre());
                description.setText(myStory.getDescription());
                rating.setIsIndicator(true);
                String ratingStr = myStory.getRating(); // Get the rating as a String
                float ratingValue = Float.parseFloat(ratingStr); // Convert the String to a float
                rating.setRating(ratingValue); // Set the rating of the RatingBar


            }
        }
        btnWhatsapp = getView().findViewById(R.id.shareButton);
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getActivity().getPackageManager();
                //  try {

                Intent Intent = new Intent();
                Intent.setAction(Intent.ACTION_SEND);
                Intent.putExtra(Intent.EXTRA_TEXT, "Hello" + "check this story" + myStory.getName());
                Intent.setType("text/plain");

                if (Intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(Intent);
                }
                    /*
                    String text = "Want to share this";
                    Intent.putExtra(Intent.EXTRA_TEXT, " check out this story  " +myStory.getName()+ "  Story :  " + myStory );
                    String url = "http://your-server.com/path/to/content"; // Replace with your actual URL

                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    Intent.setPackage("com.whatsapp");
                    Intent.putExtra(Intent.ACTION_VIEW, uri);
                    Intent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(Intent, "Share with"));


                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), "Whatsapp is not installed", Toast.LENGTH_SHORT).show();
                }
                       */
            }

        });
    }

}
