package com.example.lavacalculator;
/*
@author 石同尘 中央民族大学 计算机科学与技术
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //收集数字按键按钮id，0 -> 9
    private int[] idNum={R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,
                            R.id.btn_5,R.id.btn_6,R.id.btn_7,R.id.btn_8,R.id.btn_9};
    //收集运算符按钮id，+ - * / ( ) .
    private int[] idCal={R.id.btn_plus,R.id.btn_minus,R.id.btn_mul,R.id.btn_div,
                            R.id.btn_left,R.id.btn_right,R.id.btn_dot};
    //创建数字按钮数组，运算符按钮数组
    private Button[] btnNum=new Button[idNum.length];
    private Button[] btnCal=new Button[idCal.length];
    //声明 '=' ,'Clean','Delete'等按钮,'input_edit_text'输入的中缀表达式,'output_text_view'计算结果
    private Button btnEqual;
    private Button btnClean;
    private Button btnDelete;
    private EditText inputEditText;
    private TextView outputTextView;
    private static  String Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取除数字按钮和运算符按钮以外的组件View
        inputEditText=(EditText)findViewById(R.id.input_edit_text);
        outputTextView=(TextView)findViewById(R.id.output_text_view);
        btnEqual=(Button)findViewById(R.id.btn_equal);
        btnClean=(Button)findViewById(R.id.btn_clean);
        btnDelete=(Button)findViewById(R.id.btn_delete);
        //设置操作符点击事件
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputTextView.setText(new Calculator(inputEditText.getText().toString()).str);
            }
        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEditText.setText("");
                outputTextView.setText("");
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputEditText.getText().toString().isEmpty()){
                    Text=inputEditText.getText().toString();
                    Text=Text.substring(0,Text.length()-1);
                    inputEditText.setText(Text);
                }
            }
        });
        //获取数字按钮View，运算符View；获取数字按钮内容或者运算符按钮内容，显示到表达式里
        for(int i=0;i<idNum.length;i++){
            btnNum[i]=(Button)findViewById(idNum[i]);
            btnNum[i].setOnClickListener(new NumOnClick(btnNum[i].getText().toString(),
                                                          outputTextView,inputEditText));
        }
        for(int idcal=0;idcal<idCal.length;idcal++){
            btnCal[idcal]=(Button)findViewById(idCal[idcal]);
            btnCal[idcal].setOnClickListener(new CalOnClick(btnCal[idcal].getText().toString(),
                                                              outputTextView,inputEditText));
        }
    }
}
