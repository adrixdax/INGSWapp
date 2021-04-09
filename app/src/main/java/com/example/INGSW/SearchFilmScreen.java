package com.example.INGSW;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.DB.Adapters.UsersListAdapter;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.NotifyTestController;
import com.example.INGSW.Controllers.UserServerController;
import com.example.INGSW.Utility.UpdateRecyclers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import teaspoon.annotations.OnUi;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class SearchFilmScreen extends Fragment implements UpdateRecyclers {


    private EditText Text_of_search;
    private RecyclerView recyclerViewFilm;
    private RecyclerView recyclerViewFriends;
    private CircularProgressBar progressBar;
    private List<Film> filmInSearch = new ArrayList<>();
    private ArrayList<User> usersInSearchlist;
    private int playFlag, userFlag = 0;
    private TextView textError;
    private UsersListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.search_film_screen, container, false);

        ImageView bt_search = root.findViewById(R.id.search_button);
        ImageView userbutton = root.findViewById(R.id.userButton);
        ImageView playbutton = root.findViewById(R.id.playButton);
        textError = root.findViewById(R.id.textViewError);
        recyclerViewFilm = root.findViewById(R.id.recyclerViewFilm);
        recyclerViewFriends = root.findViewById(R.id.recyclerViewFriends);

        Glide.with(root.getContext()).load(R.drawable.play_button_active).into(playbutton);
        playFlag = 1;
        PushDownAnim.setPushDownAnimTo(userbutton, playbutton);

        playbutton.setOnClickListener(v -> {
            if (playFlag == 0) {
                Glide.with(root.getContext()).load(R.drawable.play_button_active).into(playbutton);
                Glide.with(root.getContext()).load(R.drawable.people_button).into(userbutton);
                playFlag = 1;
                userFlag = 0;
            }
        });

        userbutton.setOnClickListener(v -> {
            if (userFlag == 0) {
                Glide.with(root.getContext()).load(R.drawable.people_button_active).into(userbutton);
                Glide.with(root.getContext()).load(R.drawable.play_button).into(playbutton);
                playFlag = 0;
                userFlag = 1;
            }
        });

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            @OnUi
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) requireActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isAcceptingText()) {
                    imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
                }
                if (playFlag == 1) {
                    try {
                        if (recyclerViewFriends != null && recyclerViewFriends.isShown())
                            recyclerViewFriends.setVisibility(View.GONE);
                        if (recyclerViewFilm != null && recyclerViewFilm.isShown())
                            recyclerViewFilm.setVisibility(View.GONE);
                        FilmTestController filmTestController = new FilmTestController(new ArrayList(), getActivity());
                        filmTestController.setNameOfFilm(Text_of_search.getText().toString().trim());
                        String latestJson = "";
                        filmTestController.execute("search");
                        if (latestJson.isEmpty()) {
                            textError.setText("Nessun Film trovato");
                        } else {
                            textError.setText("");
                            filmTestController.isCancelled();
                            filmInSearch = (List<Film>) getJsonToDecode(latestJson, Film.class);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
                            ListOfFilmAdapter adapter = new ListOfFilmAdapter(filmInSearch, v.getContext(), ((ToolBarActivity) getActivity()).activeFragment);
                            adapter.setCss(SearchFilmScreen.class);
                            adapter.setHasStableIds(true);
                            recyclerViewFilm.setHasFixedSize(true);
                            recyclerViewFilm.setItemViewCacheSize(10);
                            recyclerViewFilm.setLayoutManager(layoutManager);
                            recyclerViewFilm.setAdapter(adapter);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFilm.getContext(),
                                    layoutManager.getOrientation());
                            recyclerViewFilm.addItemDecoration(dividerItemDecoration);
                        }
                        recyclerViewFilm.setVisibility(View.VISIBLE);
                        ((ToolBarActivity) (getActivity())).stopProgressBar();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (recyclerViewFriends != null && recyclerViewFriends.isShown())
                        requireActivity().runOnUiThread(() -> recyclerViewFriends.setVisibility(View.GONE));
                    if (recyclerViewFilm != null && recyclerViewFilm.isShown())
                        requireActivity().runOnUiThread(() -> recyclerViewFilm.setVisibility(View.GONE));
                    recyclerViewFriends.setHasFixedSize(true);
                    recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
                    usersInSearchlist = new ArrayList<>();
                    ArrayList<Boolean> areFriends = new ArrayList<>();
                    adapter = new UsersListAdapter(getContext(), usersInSearchlist, areFriends);
                    if (adapter.getItemCount() == 0) {
                        textError.setText("Nessun Utente trovato ");
                    }
                    textError.setText("");
                    LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFriends.getContext(),
                            layoutManager.getOrientation());
                    recyclerViewFriends.addItemDecoration(dividerItemDecoration);
                    recyclerViewFriends.setAdapter(adapter);
                    Query query = ToolBarActivity.getReference().orderByChild("nickname").startAt(String.valueOf(Text_of_search.getText())).endAt(Text_of_search.getText() + "\uf8ff");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    User model = dataSnapshot.getValue(User.class);
                                    if (!((ToolBarActivity) (getActivity())).getUid().equals(dataSnapshot.getKey())) {
                                        UserServerController con = new UserServerController();
                                        con.setUserId(((ToolBarActivity) (getActivity())).getUid());
                                        con.setIdOtherUser(dataSnapshot.getKey());
                                        model.setIdUser(dataSnapshot.getKey());
                                        usersInSearchlist.add(model);
                                        boolean friend = false;
                                        List<Notify> notifyList;
                                        try {
                                            notifyList = (List<Notify>) getJsonToDecode(String.valueOf(new NotifyTestController().execute("idUser=" + model.getIdUser()).get()), Notify.class);
                                            if (!notifyList.isEmpty()) {
                                                int i = 0;
                                                while (i < notifyList.size() && !friend) {
                                                    if (notifyList.get(i).getId_sender().equals(((ToolBarActivity) getActivity()).getUid())) {
                                                        friend = true;
                                                    }
                                                    i++;
                                                }
                                            }
                                            if (!friend) {
                                                UserServerController usc = new UserServerController();
                                                usc.setUserId(((ToolBarActivity) getActivity()).getUid());
                                                usc.setIdOtherUser(model.getIdUser());
                                                if (Boolean.parseBoolean((String) usc.execute("isFriends").get())) {
                                                    friend = true;
                                                }
                                            }
                                        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        areFriends.add(friend);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    recyclerViewFriends.setVisibility(View.VISIBLE);
                    ((ToolBarActivity) (getActivity())).stopProgressBar();
                }
            }

        });

        Text_of_search = root.findViewById(R.id.Text_of_search);
        Text_of_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                bt_search.callOnClick();
                return true;
            }
            return false;
        });

        return root;

    }

    @Override
    public void updateRecyclerView() {

    }
}
