package com.example.thestar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllStoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllStoriesFragment extends Fragment {

    private FirebaseServices fbs;
    private ArrayList<Story> list, filteredList;
    private RecyclerView rvRests;
    private StoryAdapter adapter;
    private SearchView srchView;
    private Button btnspinner;

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
        rvRests.setHasFixedSize(true);
        rvRests.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new StoryAdapter(getActivity(), list);
        rvRests.setAdapter(adapter);
        adapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String selectedItem = list.get(position).getName();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.commit();

            }
        });
        fbs.getFire().collection("stories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot : queryDocumentSnapshots.getDocuments()) {
                    Story story = dataSnapshot.toObject(Story.class);
                    list.add(story);
                }


                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        srchView = getView().findViewById(R.id.srchView);
           srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
           });



    }
}

