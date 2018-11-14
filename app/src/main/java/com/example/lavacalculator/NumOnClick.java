package com.example.lavacalculator;
//获取数字按钮内容显示到表达式里 @author 石同尘 中央民族大学 计算机科学与技术
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NumOnClick implements View.OnClickListener {
    String Msg;
    TextView textView;
    EditText editText;
    public NumOnClick(String Msg,TextView textView,EditText editText){
        this.Msg=Msg;
        this.textView=textView;
        this.editText=editText;
    }

    @Override
    public void onClick(View v) {
        if(!textView.getText().toString().equals("")){
            editText.setText("");
            textView.setText("");
        }
        editText.append(Msg);
    }
}
