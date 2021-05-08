package com.example.ingsw.component.db.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.ChooseActionDialog;
import com.example.ingsw.DialogCustomlList;
import com.example.ingsw.FilmInCustomList;
import com.example.ingsw.FriendProfile;
import com.example.ingsw.MyLists;
import com.example.ingsw.R;
import com.example.ingsw.ToolBarActivity;
import com.example.ingsw.component.db.classes.UserLists;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

@SuppressWarnings("ALL")
public class CustomListsAdapter extends RecyclerView.Adapter<CustomListsAdapter.ViewHolder> {
    private static List<UserLists> listofdata;
    private final Fragment startFragment;
    private Class css;
    private View listItem;
    private List<UserLists> selectedList = null;


    public CustomListsAdapter(List<UserLists> list, Class css, List<UserLists> selectedList) {
        listofdata = list;
        this.css = css;
        this.selectedList = selectedList;
        startFragment = null;
    }

    public CustomListsAdapter(List<UserLists> list, Class css, Fragment startFragment) {
        listofdata = list;
        this.css = css;
        this.startFragment = startFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (Objects.equals(css, MyLists.class) || Objects.equals(css, FriendProfile.class)) {
            listItem = layoutInflater.inflate(R.layout.lists_of_lists, parent, false);
        } else if (Objects.equals(css, DialogCustomlList.class)) {
            listItem = layoutInflater.inflate(R.layout.list_custom_list_selected, parent, false);
        }
        return new CustomListsAdapter.ViewHolder(listItem, css);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (Objects.equals(css.getCanonicalName(), DialogCustomlList.class.getCanonicalName())) {
            try {
                holder.textView.setText(listofdata.get(position).getTitle());
                with(holder.itemView).load("http://cdn.onlinewebfonts.com/svg/img_568523.png").into((CircleImageView) holder.itemView.findViewById(R.id.list_image));
                holder.selectItem.setOnCheckedChangeListener(null);
                holder.selectItem.setChecked(false);
                holder.selectItem.setClickable(false);
                holder.relativeLayout.setOnClickListener(v -> {
                    if (!holder.selectItem.isChecked()) {
                        holder.selectItem.setChecked(true);
                        selectedList.add(listofdata.get(position));
                    } else {
                        holder.selectItem.setChecked(false);
                        selectedList.remove(listofdata.get(position));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (Objects.equals(css, MyLists.class) || Objects.equals(css, FriendProfile.class)) {
            holder.circleList.setText(listofdata.get(position).getTitle());
            holder.circleList.setOnClickListener(v -> {
                FilmInCustomList nextFragment = new FilmInCustomList(listofdata.get(position));
                assert startFragment != null;
                FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "ListFilmCustom");
                transaction.addToBackStack(null);
                transaction.commit();
            });
            if (listofdata.get(position).getIdUser().equals(((ToolBarActivity)(startFragment.getActivity())).getUid()))
            holder.circleList.setOnLongClickListener(v -> {
                ChooseActionDialog dlg = new ChooseActionDialog(true, String.valueOf(listofdata.get(position).getIdUserList()), listofdata.get(position).getTitle(), startFragment);
                dlg.show(((ToolBarActivity) v.getContext()).getSupportFragmentManager(), "Choose action");
                return true;
            });

        }
    }


    @Override
    public int getItemCount() {
        try {
            if (listofdata != null) {
                return listofdata.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressWarnings("rawtypes")
    public Class getCss() {
        return css;
    }

    public void setCss(Class css) {
        this.css = css;
    }

    @SuppressWarnings("rawtypes")
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayout;
        public TextView textView;
        public AppCompatCheckBox selectItem;
        public CircleImageView circleImageView;
        public Button circleList;

        public ViewHolder(View itemView, Class css) {
            super(itemView);
            if (Objects.equals(css, DialogCustomlList.class)) {
                this.circleImageView = itemView.findViewById(R.id.list_image);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutAddInCustomList);
                this.selectItem = itemView.findViewById(R.id.AddList);
                this.textView = itemView.findViewById(R.id.NameOfCustomList);
            } else if (Objects.equals(css.getCanonicalName(), MyLists.class.getCanonicalName()) || Objects.equals(css, FriendProfile.class)) {
                this.circleList = itemView.findViewById(R.id.CircolarCustomList);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutShowCustomList);
            }
            PushDownAnim.setPushDownAnimTo(relativeLayout);
        }
    }

}
