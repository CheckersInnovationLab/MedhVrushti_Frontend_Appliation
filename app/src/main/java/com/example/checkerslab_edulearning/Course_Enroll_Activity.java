package com.example.checkerslab_edulearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Course_Enroll_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enroll);
        TextView text1 = findViewById(R.id.text1);
        text1.setText("Important Due to Bug #69477, redo log writes for large, externally stored BLOB fields could overwrite the most recent checkpoint. To address this bug, a patch introduced in MySQL 5.6.20 limits the size of redo log BLOB writes to 10% of the redo log file size. As a result of this limit, innodb_log_file_size should be set to a value greater than 10 times the largest BLOB data size found in the rows of your tables plus the length of other variable");
    }
}