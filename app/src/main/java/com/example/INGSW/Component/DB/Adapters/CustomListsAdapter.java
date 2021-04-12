package com.example.INGSW.Component.DB.Adapters;

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

import com.example.INGSW.ChooseActionDialog;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.DialogCustomlList;
import com.example.INGSW.FilmInCustomList;
import com.example.INGSW.MyLists;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class CustomListsAdapter extends RecyclerView.Adapter<CustomListsAdapter.ViewHolder> {
    private final List<UserLists> listofdata;

    private Class css = null;
    private View listItem;
    private List<UserLists> selectedList = null;
    private final Fragment startFragment;


    public CustomListsAdapter(List<UserLists> listofdata, Class css, List<UserLists> selectedList) {
        this.listofdata = listofdata;
        this.css = css;
        this.selectedList = selectedList;
        startFragment = null;
    }

    public CustomListsAdapter(List<UserLists> listofdata, Class css, Fragment startFragment) {
        this.listofdata = listofdata;
        this.css = css;
        this.startFragment = startFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (css.getCanonicalName().equals(MyLists.class.getCanonicalName())) {
            listItem = layoutInflater.inflate(R.layout.lists_of_lists, parent, false);
        } else if (css.getCanonicalName().equals(DialogCustomlList.class.getCanonicalName())) {
            listItem = layoutInflater.inflate(R.layout.list_custom_list_selected, parent, false);
        }
        return new CustomListsAdapter.ViewHolder(listItem, css);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (css.getCanonicalName().equals(DialogCustomlList.class.getCanonicalName())) {
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
        }
        else if(css.getCanonicalName().equals(MyLists.class.getCanonicalName())) {
            holder.circleList.setText(listofdata.get(position).getTitle());
            holder.circleList.setOnClickListener(v -> {
                FilmInCustomList nextFragment = new FilmInCustomList(listofdata.get(position));
                FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "ListFilmCustom");
                transaction.addToBackStack(null);
                transaction.commit();
            });
            holder.circleList.setOnLongClickListener(v -> {
                ChooseActionDialog dlg = new ChooseActionDialog(true, String.valueOf(listofdata.get(position).getIdUserList()), listofdata.get(position).getTitle());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayout;
        public TextView textView;
        public AppCompatCheckBox selectItem;
        public CircleImageView circleImageView;
        public Button circleList;

        public ViewHolder(View itemView, Class css) {
            super(itemView);
            if (css.getCanonicalName().equals(DialogCustomlList.class.getCanonicalName())) {
                this.circleImageView = itemView.findViewById(R.id.list_image);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutAddInCustomList);
                this.selectItem = itemView.findViewById(R.id.checkButtonAddList);
                this.textView = itemView.findViewById(R.id.NameOfCustomList);
            } else if (css.getCanonicalName().equals(MyLists.class.getCanonicalName())) {
                this.circleList = itemView.findViewById(R.id.CircolarCustomList);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutShowCustomList);
            }
            PushDownAnim.setPushDownAnimTo(relativeLayout);
        }
    }

    public Class getCss() {
        return css;
    }

    public void setCss(Class css) {
        this.css = css;
    }
}
