package com.example.ingsw;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.db.classes.UserLists;
import com.example.ingsw.component.films.Film;
import com.example.ingsw.component.films.ListOfFilmAdapter;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;

import java.util.List;

@SuppressWarnings("unchecked")
public class FilmInCustomList extends Fragment implements RetrofitListInterface {

    private RecyclerView filmInCustomList;
    private final UserLists list;

    public FilmInCustomList(UserLists idList) {
        this.list = idList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.film_in_list_custom, container, false);

        TextView title = root.findViewById(R.id.customListName);
        filmInCustomList = root.findViewById(R.id.recyclerViewFilmInListCustom);
        ImageButton friendsComments = root.findViewById(R.id.listcommentsButton);
        TextView description = root.findViewById(R.id.listDescription);
        description.setText(list.getDescription());
        description.setMovementMethod(new ScrollingMovementMethod());
        title.setText(list.getTitle());

        friendsComments.setOnClickListener(v -> {
            if (list.getIdUser().equals(((ToolBarActivity)(requireActivity())).getUid())){
                    FriendsListComments nextFragment = new FriendsListComments(true,String.valueOf(list.getIdUserList()));
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "listComments");
                    transaction.addToBackStack(null);
                    transaction.commit();
            }
            else {
                FriendsListComments nextFragment = new FriendsListComments(false,String.valueOf(list.getIdUserList()));
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "listComments");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse(
                "Type=PostRequest&idList=" + list.getIdUserList(),
                this, this.getContext(), "getFilmInList");

        return root;
    }


    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
            adapter.setCss(FilmInCustomList.class);
            adapter.setIdList(String.valueOf(list.getIdUserList()));
            filmInCustomList.setHasFixedSize(false);
            filmInCustomList.setItemViewCacheSize(newList.size());
            LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            filmInCustomList.setLayoutManager(layoutManager);
            filmInCustomList.setAdapter(adapter);
            filmInCustomList.setVisibility(View.VISIBLE);
        }
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}
