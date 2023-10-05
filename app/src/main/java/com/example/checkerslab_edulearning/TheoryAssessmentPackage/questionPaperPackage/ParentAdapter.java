package com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;

import java.util.List;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class ParentAdapter  extends RecyclerView.Adapter<ParentAdapter.ViewHolder>{



    List<ParentModel> mainQuesList;
    Context context;

    public ParentAdapter(List<ParentModel> mainQuesList, Context context) {
        this.mainQuesList = mainQuesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.assessment_main_question_sl_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAdapter.ViewHolder holder, int position) {

        /////////////////
        ParentModel parentModel=mainQuesList.get(position);

        RecyclerView.RecycledViewPool
                viewPool
                = new RecyclerView
                .RecycledViewPool();


        JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\text{ "+parentModel.getMainQuestion()+"}")
                .textSize(150)
                .padding(2)
                .background(0xFFffffff)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();


        holder.latexMathView.setLatexDrawable(drawable);
        ///////////////

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                holder
                        .mainRecyclerView
                        .getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        layoutManager
                .setInitialPrefetchItemCount(
                        parentModel
                                .getSubQuestionList()
                                .size());



        //     holder.mainQues.setText(mainQuesList.get(position).mainQuestion);

        ChildAdapter childAdapter;

        Toast.makeText(context, String.valueOf(mainQuesList.get(0).subQuestionList.size()), Toast.LENGTH_SHORT).show();

//        for (ChildModel childModel2:mainQuesList.get(0).subQuestionList)
//        {
//            Log.d("gog111",childModel2.subQuestion);
//        }
       // Log.d("sizee","list number="+String.valueOf(position)+"mianlistSize is="+String.valueOf(mainQuesList.size())+""+String.valueOf(mainQuesList.get(1).subQuestionList.size()));

        childAdapter=new ChildAdapter(parentModel.getSubQuestionList(),context);
        holder.mainRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.mainRecyclerView.setAdapter(childAdapter);

//        childAdapter=new ChildAdapter(parentModelClassList2.get(position).childModelClassList,context);
//        holder.recyclerView2.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
//        holder.recyclerView2.setAdapter(childAdapter);

        holder
                .mainRecyclerView
                .setRecycledViewPool(viewPool);
        childAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mainQuesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView mainRecyclerView;

        JLatexMathView latexMathView;


        TextView mainQues;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainRecyclerView=itemView.findViewById(R.id.parent_recycleView_id);
            latexMathView=itemView.findViewById(R.id.Theory_assessment_Main_Que_id);
            // recyclerView2=itemView.findViewById(R.id.Verticle_Child_recycleView_id);
           // mainQues=itemView.findViewById(R.id.Theory_assessment_Main_Que_id);



        }
    }
}
