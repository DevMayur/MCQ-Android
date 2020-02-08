package com.srkakadesir.onlineexaminationsystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.srkakadesir.onlineexaminationsystem.R;
import com.srkakadesir.onlineexaminationsystem.Test.AllTests;
import com.srkakadesir.onlineexaminationsystem.models.SubjectModel;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    private Context context;
    private List<SubjectModel> sList;

    public SubjectAdapter(Context context, List<SubjectModel> sList) {
        this.context = context;
        this.sList = sList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_subject,parent,false);
        return new SubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_title.setText(sList.get(position).getTitle());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllTests.class);
                intent.putExtra("sub_id",sList.get(position).BlogPostId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title_single_subject);
            card = itemView.findViewById(R.id.card_single_subject);

        }
    }
}
