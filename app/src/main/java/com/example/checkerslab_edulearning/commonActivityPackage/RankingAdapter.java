package com.example.checkerslab_edulearning.commonActivityPackage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainAdapter;

import java.util.List;

public class RankingAdapter  extends RecyclerView.Adapter<RankingAdapter.ViewHolder>{


    private final List<RankingModel> rankingList;
    Context context;

    public RankingAdapter(List<RankingModel> rankingList, Context context) {
        this.rankingList = rankingList;
        this.context = context;
    }

    @NonNull
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ranking_leader_board_sl_layout, null, false);
        return new RankingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.ViewHolder holder, int position) {
        Log.d("getRankingDetails", "onBindViewHolder: ");
        final RankingModel rankingModel = rankingList.get(position);
        holder.rank.setText(rankingModel.getUserRanking());
        holder.name.setText(rankingModel.getUserName());
        holder.marks.setText(rankingModel.getUserObtainedMarks());
    }

    @Override
    public int getItemCount() {

        return rankingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView rank,name,marks;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.RankingLeaderBoard_rank_id);
            name = itemView.findViewById(R.id.RankingLeaderBoard_UserName_id);
            marks=itemView.findViewById(R.id.RankingLeaderBoard_UserMarks_id);
        }
    }
}
