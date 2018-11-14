package com.example.lavacalculator;
//获取运算符按钮内容显示到表达式里 @author 石同尘 中央民族大学 计算机科学与技术
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CalOnClick implements View.OnClickListener {
    String Msg;
    TextView textView;
    EditText editText;
    String[] calSymbol={"+","-","*","/","."};
    public CalOnClick(String Msg,TextView textView,EditText editText){
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
        //检查运算符是否重复输入
        for(int i=0;i<calSymbol.length;i++){
            if(Msg.equals(calSymbol[i])){
                if(editText.getText().toString().split("")
                        [editText.getText().toString().split("").length-1].equals(calSymbol[i])){
                    Msg="";
                }
            }
        }
        editText.append(Msg);
    }
}
