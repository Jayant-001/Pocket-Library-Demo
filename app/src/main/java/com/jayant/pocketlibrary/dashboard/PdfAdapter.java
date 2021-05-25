package com.jayant.pocketlibrary.dashboard;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.jayant.pocketlibrary.R;

public class PdfAdapter extends FirebaseRecyclerAdapter<PdfData, PdfAdapter.PdfViewHolder> {


    public PdfAdapter(@NonNull FirebaseRecyclerOptions<PdfData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PdfAdapter.PdfViewHolder holder, int position, @NonNull PdfData model) {

        holder.pdfName.setText(model.getName());
        holder.pdfViewCount.setText(String.valueOf(model.getView()));

        holder.pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.pdfName.getContext(), PdfView.class);
                intent.putExtra("pdfName", model.getName());
                intent.putExtra("pdfUrl", model.getUrl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.pdfView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_pdf_item, parent, false);

        return new PdfViewHolder(view);
    }


    class PdfViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout pdfView;
        private TextView pdfName, pdfViewCount;

        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);

            pdfView = itemView.findViewById(R.id.pdf_view);
            pdfName = itemView.findViewById(R.id.pdf_view_name);
            pdfViewCount = itemView.findViewById(R.id.pdf_view_count);
        }
    }
}
