package com.example.checkerslab_edulearning.DummyPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.checkerslab_edulearning.R;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class Dummy_Match_Pair_Activity extends AppCompatActivity {

    JLatexMathView latexMathView, latexMathView1,latexMathView2,latexMathView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_match_pair);

        latexMathView=findViewById(R.id.Theory_assessment_Main_Que_id1);
        latexMathView1=findViewById(R.id.Theory_assessment_101);
        latexMathView2=findViewById(R.id.Theory_assessment_102);
        latexMathView3=findViewById(R.id.Theory_assessment_103);


        JLatexMathDrawable drawable2 = JLatexMathDrawable.builder("\\textbf {Specific heat Capacity}"
                )
                .textSize(70)
                .padding(8)
                .background(0xFFffffff)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();


        JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\( \\mathrm{Cal} / \\mathrm{g} .{ }^{\\circ} \\mathrm{C} \\)"
                )
                .textSize(70)
                .padding(8)
                .background(0xFFffffff)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();

        latexMathView.setLatexDrawable(drawable2);
        latexMathView1.setLatexDrawable(drawable);
        latexMathView2.setLatexDrawable(drawable);
        latexMathView3.setLatexDrawable(drawable);

    }
}