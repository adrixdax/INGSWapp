package com.example.INGSW;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.Films.ListOfFilm;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class SearchFilmScreen extends Fragment {


    private EditText Text_of_search;
    private RecyclerView recyclerViewFilm;
    private RecyclerView recyclerViewFriends;
    //private ProgressBar progressBar;
    private List<ListOfFilm> filmInSearch = new ArrayList<>();
    private ArrayList<User> usersInSearchlist;
    private int playFlag, userFlag = 0;
    private TextView textError;

    private final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private final DatabaseReference ref = db.getReference("Users");
    private UsersListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.search_film_screen, container, false);

        ImageView bt_search = root.findViewById(R.id.search_button);
        ImageView userbutton = root.findViewById(R.id.userButton);
        ImageView playbutton = root.findViewById(R.id.playButton);
        //progressBar = root.findViewById(R.id.progressBar);
        textError = root.findViewById(R.id.textViewError);

        Glide.with(root.getContext()).load(R.drawable.play_button_active).into(playbutton);
        playFlag = 1;
        PushDownAnim.setPushDownAnimTo(userbutton, playbutton);

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playFlag == 0) {
                    Glide.with(root.getContext()).load(R.drawable.play_button_active).into(playbutton);
                    Glide.with(root.getContext()).load(R.drawable.people_button).into(userbutton);
                    playFlag = 1;
                    userFlag = 0;
                }
            }
        });

        userbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userFlag == 0) {
                    Glide.with(root.getContext()).load(R.drawable.people_button_active).into(userbutton);
                    Glide.with(root.getContext()).load(R.drawable.play_button).into(playbutton);
                    playFlag = 0;
                    userFlag = 1;
                }
            }
        });

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playFlag == 1) {

                    InputMethodManager imm = (InputMethodManager) requireActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    Text_of_search = root.findViewById(R.id.Text_of_search);
                    //if toggle button is on f ilm then {
                    try {
                        if (recyclerViewFriends != null && recyclerViewFriends.getVisibility() == View.VISIBLE)
                            recyclerViewFriends.setVisibility(View.INVISIBLE);
                        //progressBar.setVisibility(View.VISIBLE);

                        FilmTestController filmTestController = new FilmTestController();
                        filmTestController.setNameOfFilm(Text_of_search.getText().toString().trim());
                        String film = filmTestController.getNameOfFilm();
                        System.out.println("Il film che stai cercando -> " + film);


                        String latestJson = (String) filmTestController.execute("search").get();

                        if (latestJson.isEmpty()) {
                            textError.setText("Nessun Film trovato");
                            System.out.println("--------------------------------------------> Vuoto");
                        } else {
                            System.out.println("I Film trovati -> " + latestJson);
                            filmTestController.isCancelled();

                            filmInSearch = (List<ListOfFilm>) getJsonToDecode(latestJson, ListOfFilm.class);


                            LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerViewFilm = root.findViewById(R.id.recyclerViewFilm);
                            ListOfFilmAdapter adapter = new ListOfFilmAdapter(filmInSearch, getContext(), ((ToolBarActivity) getActivity()).activeFragment);
                            adapter.setCss(SearchFilmScreen.class);
                            recyclerViewFilm.setHasFixedSize(false);
                            recyclerViewFilm.setLayoutManager(layoutManager);
                            recyclerViewFilm.setAdapter(adapter);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFilm.getContext(),
                                    layoutManager.getOrientation());
                            recyclerViewFilm.addItemDecoration(dividerItemDecoration);
                            //progressBar.setVisibility(View.GONE);
                            recyclerViewFilm.setVisibility(View.VISIBLE);
                        }

                    } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    //}
                    //else (means is on users)
  /*              try {
                    recyclerViewFilm.setVisibility(View.INVISIBLE);
                    FilmTestController filmTestController = new FilmTestController();
                    filmTestController.setNameOfFilm(Text_of_search.getText().toString().trim());
                    String film = filmTestController.getNameOfFilm();
                    System.out.println("Il film che stai cercando -> "+ film);



                    String latestJson = (String) filmTestController.execute(new String("search")).get();

                    System.out.println("I Film trovati -> "+ latestJson);
                    filmTestController.isCancelled();

                    filmInSearch = (List<ListOfFilm>) getJsonToDecode(latestJson, ListOfFilm.class);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerViewFriends = root.findViewById(R.id.recyclerViewFriends);
                    ListOfFilmAdapter adapter = new ListOfFilmAdapter(filmInSearch,getContext(),((ToolBarActivity) getActivity()).activeFragment );
                    adapter.setCss(SearchFilmScreen.class);
                    recyclerViewFriends.setHasFixedSize(false);
                    recyclerViewFriends.setLayoutManager(layoutManager);
                    recyclerViewFriends.setAdapter(adapter);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Friends.getContext(),
                            layoutManager.getOrientation());
                    recyclerViewFriends.addItemDecoration(dividerItemDecoration);
                    recyclerViewFriends.setVisibility(View.VISIBLE);

                } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
                    e.printStackTrace();
                }
*/

                } else {
                    if (recyclerViewFilm != null && recyclerViewFilm.getVisibility() == View.VISIBLE)
                        recyclerViewFilm.setVisibility(View.INVISIBLE);
                    recyclerViewFriends = root.findViewById(R.id.recyclerViewFriends);
                    recyclerViewFriends.setHasFixedSize(true);
                    recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity()));

                    usersInSearchlist = new ArrayList<>();
                    adapter = new UsersListAdapter(getContext(), usersInSearchlist);
                    if (adapter.userlist.isEmpty()) {
                        textError.setText("Nessun Utente trovato ");
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFriends.getContext(),
                            layoutManager.getOrientation());
                    recyclerViewFriends.addItemDecoration(dividerItemDecoration);
                    recyclerViewFriends.setAdapter(adapter);

                    Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("nickname").startAt(String.valueOf(Text_of_search.getText())).endAt(Text_of_search.getText() + "\uf8ff");

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                User model = dataSnapshot.getValue(User.class);
                                usersInSearchlist.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    recyclerViewFriends.setVisibility(View.VISIBLE);
                }
            }
        });
/*
        userbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewFilm!=null && recyclerViewFilm.getVisibility() == View.VISIBLE)recyclerViewFilm.setVisibility(View.INVISIBLE);
                recyclerViewFriends = root.findViewById(R.id.recyclerViewFriends);
                recyclerViewFriends.setHasFixedSize(true);
                recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity()));

                usersInSearchlist = new ArrayList<>();
                adapter = new UsersListAdapter(getContext(),usersInSearchlist);
                LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFriends.getContext(),
                        layoutManager.getOrientation());
                recyclerViewFriends.addItemDecoration(dividerItemDecoration);
                recyclerViewFriends.setAdapter(adapter);

                Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("nickname").startAt(String.valueOf(Text_of_search.getText())).endAt(String.valueOf(Text_of_search.getText()) + "\uf8ff");

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            User model = dataSnapshot.getValue(User.class);
                            usersInSearchlist.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                recyclerViewFriends.setVisibility(View.VISIBLE);
            }
        });
*/
        Text_of_search = root.findViewById(R.id.Text_of_search);
        Text_of_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { /** senza toggle non possiamo ultimare questo editor action **/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    bt_search.callOnClick();
                    return true;
                }
                return false;
            }
        });

        return root;

    }

}