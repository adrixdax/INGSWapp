package com.example.ingsw;


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
import com.example.ingsw.component.db.adapters.UsersListAdapter;
import com.example.ingsw.component.db.classes.Contact;
import com.example.ingsw.component.db.classes.Notify;
import com.example.ingsw.component.db.classes.User;
import com.example.ingsw.component.films.Film;
import com.example.ingsw.component.films.ListOfFilmAdapter;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import teaspoon.annotations.OnUi;

@SuppressWarnings("ALL")
public class SearchFilmScreen extends Fragment implements RetrofitListInterface {


    final List<Notify> notifyList = new ArrayList<>();
    private EditText Text_of_search;
    private RecyclerView recyclerViewFilm;
    private RecyclerView recyclerViewFriends;
    private ArrayList<User> usersInSearchlist;
    private ArrayList<Contact> contactsList;
    private int playFlag, userFlag = 0;
    private TextView textError;
    private UsersListAdapter useradapter;
    private ListOfFilmAdapter adapter;

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
                    if (recyclerViewFriends != null && recyclerViewFriends.isShown())
                        recyclerViewFriends.setVisibility(View.GONE);
                    if (recyclerViewFilm != null && recyclerViewFilm.isShown())
                        recyclerViewFilm.setVisibility(View.GONE);
                    ((ToolBarActivity) requireActivity()).triggerProgessBar();
                    RetrofitResponse.getResponse("Type=PostRequest&name=" + Text_of_search.getText().toString(), SearchFilmScreen.this, v.getContext(), "getFilm");
                } else {
                    ((ToolBarActivity) requireActivity()).triggerProgessBar();
                    if (recyclerViewFriends != null && recyclerViewFriends.isShown())
                        requireActivity().runOnUiThread(() -> recyclerViewFriends.setVisibility(View.GONE));
                    if (recyclerViewFilm != null && recyclerViewFilm.isShown())
                        requireActivity().runOnUiThread(() -> recyclerViewFilm.setVisibility(View.GONE));
                    recyclerViewFriends.setHasFixedSize(true);
                    recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
                    usersInSearchlist = new ArrayList<>();
                    contactsList = new ArrayList<>();
                    useradapter = new UsersListAdapter(getContext(), usersInSearchlist, contactsList, (ArrayList<Notify>) notifyList);
                    if (useradapter.getItemCount() == 0) {
                        textError.setText("Nessun Utente trovato ");
                    }
                    textError.setText("");
                    LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFriends.getContext(),
                            layoutManager.getOrientation());
                    recyclerViewFriends.addItemDecoration(dividerItemDecoration);
                    recyclerViewFriends.setAdapter(useradapter);
                    recyclerViewFilm.setItemViewCacheSize(usersInSearchlist.size());
                    Query query = ToolBarActivity.getReference().orderByChild("nickname").startAt(String.valueOf(Text_of_search.getText())).endAt(Text_of_search.getText() + "\uf8ff");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String uid = ((ToolBarActivity) (requireActivity())).getUid();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                User model = dataSnapshot.getValue(User.class);
                                model.setIdUser(dataSnapshot.getKey());
                                if (!(uid.equals(dataSnapshot.getKey()))) {
                                    usersInSearchlist.add(model);
                                    useradapter.notifyDataSetChanged();
                                }
                            }
                            RetrofitResponse.getResponse("Type=PostRequest&isFriends=true&idUser=" + uid, SearchFilmScreen.this, SearchFilmScreen.this.getContext(), "getFriends");
                            RetrofitResponse.getResponse(uid, SearchFilmScreen.this, SearchFilmScreen.this.getContext(), "getFriendShipNotify");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    recyclerViewFriends.setVisibility(View.VISIBLE);
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
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            System.out.println(adapter.getItemCount());
            recyclerViewFilm.setAdapter(adapter);
            recyclerViewFilm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerViewFilm.setAdapter(adapter);
            recyclerViewFilm.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setList(List<?> newList) {
        if (playFlag == 0) {
            if (!(newList.isEmpty())) {
                if (newList.get(0) instanceof Notify)
                    notifyList.addAll((Collection<? extends Notify>) newList);
                else
                    contactsList.addAll((Collection<? extends Contact>) newList);
                useradapter.notifyDataSetChanged();
            }
        } else if (playFlag == 1) {
            if (newList.isEmpty()) {
                recyclerViewFilm.setVisibility(View.INVISIBLE);
                textError.setText("Nessun Film trovato");
            } else {
                textError.setText("");
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), ((ToolBarActivity) requireActivity()).activeFragment);
                adapter.setCss(SearchFilmScreen.class);
                adapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
                recyclerViewFilm.setHasFixedSize(true);
                recyclerViewFilm.setItemViewCacheSize(newList.size());
                recyclerViewFilm.setLayoutManager(layoutManager);
                recyclerViewFilm.setAdapter(adapter);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFilm.getContext(),
                        layoutManager.getOrientation());
                recyclerViewFilm.addItemDecoration(dividerItemDecoration);
                recyclerViewFilm.setVisibility(View.VISIBLE);
            }
        }
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}