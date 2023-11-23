package com.example.checkerslab_edulearning.DummyPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.checkerslab_edulearning.R;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class Dummy_Activity extends AppCompatActivity {



    JLatexMathView latexMathView, latexMathView1,latexMathView2,latexMathView3,latexMathView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);


        latexMathView=findViewById(R.id.Theory_assessment_Main_Que_id);
        latexMathView1=findViewById(R.id.Theory_assessment_Main_Que_id1);
        latexMathView2=findViewById(R.id.Theory_assessment_Main_Que_id2);
        latexMathView3=findViewById(R.id.Theory_assessment_Main_Que_id3);
        latexMathView4=findViewById(R.id.Theory_assessment_Main_Que_id4);

     //
        JLatexMathDrawable drawable2 = JLatexMathDrawable.builder("\\textbf {The Gravitational force between two}"
                )
                .textSize(90)
                .padding(8)
                .background(0xFFffffff)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();


        JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\( \\frac{1}{r^{2}} \\)"
                )
                .textSize(90)
                .padding(8)
                .background(0xFFffffff)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();

        latexMathView.setLatexDrawable(drawable2);
        latexMathView1.setLatexDrawable(drawable);
        latexMathView2.setLatexDrawable(drawable);
        latexMathView3.setLatexDrawable(drawable);
        latexMathView4.setLatexDrawable(drawable);

    }
}