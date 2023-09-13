package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;

import java.util.List;

public class Ass_Subjects_Adapter extends RecyclerView.Adapter<Ass_Subjects_Adapter.ViewHolder>{

    List<Ass_Subjects_Model> list;
    Context context;

    public Ass_Subjects_Adapter(List<Ass_Subjects_Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ass_subjects_sl_layout,null,false);
        return new Ass_Subjects_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ass_Subjects_Model model=list.get(position);

        holder.subjectName.setText(model.getSubjectName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Ass_Chapters_activity.class);
                intent.putExtra("Ass_Subject_id",String.valueOf(model.getSubjectId()));

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ///***



                //***
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subjectName;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName= itemView.findViewById(R.id.Ass_SubjectName_id);
            cardView=itemView.findViewById(R.id.Ass_subject_cardView_id);
        }
    }
}
