package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;

import java.util.List;

public class Ass_standards_adapter extends RecyclerView.Adapter<Ass_standards_adapter.ViewHolder>{

    List<Ass_Standards_Model> list;
    Context context;

    public Ass_standards_adapter(List<Ass_Standards_Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Ass_standards_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ass_standards_sl_layoout,null,false);
        return new Ass_standards_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ass_standards_adapter.ViewHolder holder, int position) {
        Ass_Standards_Model model=list.get(position);

        holder.subCourseName.setText(model.getStdName());
        Toast.makeText(context, model.getStdName(), Toast.LENGTH_SHORT).show();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Ass_Subjects_activity.class);
                intent.putExtra("Ass_standard_id",String.valueOf(model.getStdId()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView subCourseName,subCourseAuthor;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCourseName=itemView.findViewById(R.id.Ass_standards_name_id);
            subCourseAuthor=itemView.findViewById(R.id.Ass_standards_author_name);
            cardView=itemView.findViewById(R.id.Ass_standards_sl_cardView_id);
        }
    }

}
