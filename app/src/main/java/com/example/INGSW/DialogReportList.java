package com.example.INGSW;

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

import com.example.INGSW.Component.DB.Adapters.CustomListsAdapter;
import com.example.INGSW.Component.DB.Adapters.ReportListsAdapter;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Utility.ReportType;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DialogReportList extends AppCompatDialogFragment implements RetrofitListInterface {


    private RecyclerView recycler;
    private final List<ReportType> selectedLists = new ArrayList<>();
    private int idRecordToReport;

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.report_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);


        recycler = dialog.findViewById(R.id.recyclerView);


        Button addReport = (Button) dialog.getWindow().findViewById(R.id.ReportButton);
        PushDownAnim.setPushDownAnimTo(addReport);
        addReport.setOnClickListener(v -> {
            String reportTypes="";
            for (ReportType singlelist : selectedLists) {
                 reportTypes =  reportTypes + ":" + singlelist.toString();
            }
            selectedLists.clear();
            RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity)getActivity()).getUid() + "&id_recordRef="+ idRecordToReport + "&reportType="+ reportTypes+ "&addReport=true", DialogReportList.this, DialogReportList.this.getContext(),"addReport" );
            Toast.makeText(this.getContext(), "Segnalazione inviata con successo", Toast.LENGTH_LONG).show();
            dialog.closeOptionsMenu();
            dialog.cancel();
            dialog.dismiss();
        });

        return dialog;

    }

    public void setIdRecordToReport(int idRecordToReport) {
        this.idRecordToReport = idRecordToReport;
    }

    @Override
    public void setList(List<?> newList) {
        recycler.setAdapter(new ReportListsAdapter(this.selectedLists));
        recycler.setItemViewCacheSize(newList.size());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(false);
    }
}
