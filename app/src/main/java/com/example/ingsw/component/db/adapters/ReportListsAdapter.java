package com.example.ingsw.component.db.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.R;
import com.example.ingsw.utility.ReportType;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

public class ReportListsAdapter extends RecyclerView.Adapter<ReportListsAdapter.ViewHolder> {
    private static final List<String> listofdata = new ArrayList<>();
    private final List<ReportType> selectedList;


    public ReportListsAdapter(List<ReportType> selectedList) {
        this.selectedList = selectedList;
        createList();
    }

    private void createList() {
        if (listofdata.isEmpty()) {
            listofdata.add(ReportType.LIGUAGGIO_OFFENSIVO.toString().replace('_', ' '));
            listofdata.add(ReportType.DISCRIMINAZIONE.toString().replace('_', ' '));
            listofdata.add(ReportType.INCITAZIONE_ALLO_ODIO.toString().replace('_', ' ').replaceFirst("ALLO", "ALL'"));
            listofdata.add(ReportType.RECENSIONE_PRIVA_DI_SENSO.toString().replace('_', ' '));
            listofdata.add(ReportType.BLASFEMIA.toString().replace('_', ' '));
            listofdata.add(ReportType.SPAM.toString().replace('_', ' '));
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_report_type, parent, false);
        return new ReportListsAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.textView.setText(listofdata.get(position));
            holder.selectItem.setOnCheckedChangeListener(null);
            holder.selectItem.setChecked(false);
            holder.selectItem.setClickable(false);
            holder.relativeLayout.setOnClickListener(v -> {
                if (!holder.selectItem.isChecked()) {
                    holder.selectItem.setChecked(true);
                    selectedList.add(ReportType.valueOf(listofdata.get(position).replace('\'', 'O').replace(' ', '_')));
                } else {
                    holder.selectItem.setChecked(false);
                    selectedList.remove(ReportType.valueOf(listofdata.get(position).replace('\'', 'O').replace(' ', '_')));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
        public final RelativeLayout relativeLayout;
        public final TextView textView;
        public final AppCompatCheckBox selectItem;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutreportType);
            this.selectItem = itemView.findViewById(R.id.AddList);
            this.textView = itemView.findViewById(R.id.NameOfReportType);
            PushDownAnim.setPushDownAnimTo(relativeLayout);

        }
    }
}
