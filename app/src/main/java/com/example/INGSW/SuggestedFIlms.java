package com.example.INGSW;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.INGSW.Component.Films.ListOfFilm;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuggestedFIlms #newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestedFIlms extends Fragment {
/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SuggestedFIlms() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestedFIlms.
     */
    /* TODO: Rename and change types and number of parameters
    public static SuggestedFIlms newInstance(String param1, String param2) {
        SuggestedFIlms fragment = new SuggestedFIlms();
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
*/


    private List<ListOfFilm> film = null;
    private FilmTestController con = new FilmTestController();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_suggested_films, container, false);


        film = ((ToolBarActivity) getActivity()).getConteinerList().get("SuggestedFIlms");

        if (film == null) {
            String latestJson = "";
            try {
                latestJson = (String) con.execute(new String("latest")).get();
                con.isCancelled();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(latestJson);


            try {
                film = (List<ListOfFilm>) getJsonToDecode(latestJson, ListOfFilm.class);
                ((ToolBarActivity) getActivity()).getConteinerList().put("SuggestedFIlms", film);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        ListOfFilmAdapter adapter = new ListOfFilmAdapter(film, getContext(), this);
        adapter.setCss(SuggestedFIlms.class);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setAdapter(adapter);


        return root;
    }
}