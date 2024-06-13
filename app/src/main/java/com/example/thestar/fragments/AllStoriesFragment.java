package com.example.thestar.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.thestar.Database.FirebaseServices;
import com.example.thestar.R;
import com.example.thestar.Database.Story;
import com.example.thestar.Utilites.StoryAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllStoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllStoriesFragment extends Fragment {

    private FirebaseServices fbs;
    private ArrayList<Story> storieslist, filteredList;
    private RecyclerView rvRests;
    private StoryAdapter adapter;
    private Button probtn , btnSignout,addbtn;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllStoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllStoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllStoriesFragment newInstance(String param1, String param2) {
        AllStoriesFragment fragment = new AllStoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getActivity().getMenuInflater();
       inflater.inflate(R.menu.menu_main, menu);
       return true;
   }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.action_profile) gotoProfile();
        if (item.getItemId()==R.id.action_sign_out) signout();
        if (item.getItemId()==R.id.action_add_story) gotoLAddstory();
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_stories, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();

    }

    private void init() {
        rvRests = getView().findViewById(R.id.rvStory);
        fbs = FirebaseServices.getInstance();
        probtn=getView().findViewById(R.id.Profile);
        btnSignout=getView().findViewById(R.id.signout);
        addbtn=getView().findViewById(R.id.StorySuggestion);
addbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        gotoLAddstory();
    }
});




        btnSignout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fbs.getAuth().signOut();

                        gotoLoginFragment();
                    }
                });
        probtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotoProfile();}
        });

       /*if (fbs.getAuth().getCurrentUser() == null)
            fbs.setCurrentUser(fbs.getCurrentObjectUser());*/
        storieslist = new ArrayList<>();
        rvRests.setHasFixedSize(true);
        rvRests.setLayoutManager(new LinearLayoutManager(getActivity()));
        storieslist = getStories();
        adapter = new StoryAdapter(getActivity(), storieslist);
        filteredList = new ArrayList<>();
        /*
        adapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = storieslist.get(position).getName();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("Story", storieslist.get(position)); // or use Parcelable for better performance
                StoriesDetails sd = new StoriesDetails();
                sd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,sd);
                ft.commit();
            }
        }); */
/*
        srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //applyFilter(newText);
                return false;
            }
        });
        //((MainActivity)getActivity()).pushFragment(new CarsListFragment());
*/
    }

    private void applyFilter(String query) {
        // TODO: add onBackspace - old and new query
        if (query.trim().isEmpty()) {
            adapter = new StoryAdapter(getContext(), storieslist);
            rvRests.setAdapter(adapter);
           // adapter.notifyDataSetChanged();
            return;
        }
        filteredList.clear();
        for (Story story : filteredList) {
            if (story.getName().toLowerCase().contains(query.toLowerCase()) ||
                    story.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                    story.getGenre().toLowerCase().contains(query.toLowerCase()) ||
                    story.getRating().toLowerCase().contains(query.toLowerCase()) ||
                    story.getPhoto().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(story);
            }
        }
        if (filteredList.size() == 0) {
            showNoDataDialogue();
            return;
        }
        adapter = new StoryAdapter(getContext(), filteredList);
        rvRests.setAdapter(adapter);

       /*
        myAdapter= new CarListAdapter2(getActivity(),filteredList);
        recyclerView.setAdapter(myAdapter); */


    }

    private void showNoDataDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Results");
        builder.setMessage("Try again!");
        builder.show();
    }


    public void gotoProfileFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new ProfileFragment());
        ft.commit();
    }

    public ArrayList<Story> getStories() {
        ArrayList<Story> stories = new ArrayList<>();

        try {
            stories.clear();
            fbs.getFire().collection("stories")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                stories.add(document.toObject(Story.class));
                            }

                            StoryAdapter adapter = new StoryAdapter(getActivity(), storieslist);
                            rvRests.setAdapter(adapter);
                            //addUserToCompany(companies, user);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("AllRestActivity: readData()", "Error getting documents.", e.getCause());

                        }
                    });
        } catch (Exception e) {
            Log.e("getCompaniesMap(): ", e.getMessage());
        }

        return stories;
    }
    private void gotoProfile() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new ProfileFragment());
        ft.commit();
    }
    private void gotoLoginFragment() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new LoginFragment());
        ft.commit();
    }
    private void gotoLAddstory() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddStoryFragment());
        ft.commit();
    }
    private void signout() {
        fbs.getAuth().signOut();

        gotoLoginFragment();
    }
}

