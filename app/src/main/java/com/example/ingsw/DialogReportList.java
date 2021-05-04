package com.example.ingsw;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.db.adapters.ReportListsAdapter;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.example.ingsw.utility.ReportType;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DialogReportList extends AppCompatDialogFragment {


    private final List<ReportType> selectedLists = new ArrayList<>();
    private int idRecordToReport;

    public DialogReportList(){
    }

    public DialogReportList(int idRecordToReport){
        this.idRecordToReport = idRecordToReport;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.report_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        RecyclerView recycler = dialog.findViewById(R.id.recyclerView);
        ReportListsAdapter adapter = new ReportListsAdapter(selectedLists);
        recycler.setAdapter(adapter);
        recycler.setItemViewCacheSize(adapter.getItemCount());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(false);

        Button addReport = (Button) dialog.getWindow().findViewById(R.id.ReportButton);
        PushDownAnim.setPushDownAnimTo(addReport);
        addReport.setOnClickListener(v -> {
            StringBuilder reportTypes= new StringBuilder();
            for (ReportType singlelist : selectedLists) {
                 reportTypes.append(singlelist.toString()).append(":");
            }
            String report = reportTypes.substring(0,reportTypes.length()-1);
            selectedLists.clear();
            RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity)getActivity()).getUid() + "&id_recordRef="+ idRecordToReport + "&reportType="+ report+ "&addReport=true", DialogReportList.this, DialogReportList.this.getContext(),"addReport" );
            Toast.makeText(this.getContext(), "Segnalazione inviata con successo", Toast.LENGTH_LONG).show();
            dialog.closeOptionsMenu();
            dialog.cancel();
            dialog.dismiss();
        });
        return dialog;
    }

}
