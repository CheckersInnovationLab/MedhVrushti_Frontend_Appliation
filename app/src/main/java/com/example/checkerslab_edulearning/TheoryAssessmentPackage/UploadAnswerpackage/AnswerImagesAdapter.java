package com.example.checkerslab_edulearning.TheoryAssessmentPackage.UploadAnswerpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnswerImagesAdapter extends RecyclerView.Adapter<AnswerImagesAdapter.ViewHolder>{

    private final List<AnswerImagedModel> ansImgList;
    Context context;

    public AnswerImagesAdapter(List<AnswerImagedModel> ansImgList, Context context) {
        this.ansImgList = ansImgList;
        this.context = context;
    }


    @NonNull
    @Override
    public AnswerImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.answer_image_sl_layout, null, false);
        return new AnswerImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerImagesAdapter.ViewHolder holder, int position) {
        final AnswerImagedModel imgModel = ansImgList.get(position);

        Picasso.get().load(imgModel.getAnswerImgUrl()).into(holder.courseImage);
        
        holder.courseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ansImgList.remove(position)

                Toast.makeText(context, String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return ansImgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.Answer_image_id);
        }
    }
}
