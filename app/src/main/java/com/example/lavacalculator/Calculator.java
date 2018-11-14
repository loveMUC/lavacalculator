package com.example.lavacalculator;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//中缀转后缀，后缀表达式计算 @author 石同尘 中央民族大学 计算机科学与技术
public class Calculator {
    public String s1;
    StringBuilder str;
    public Calculator(String s1){
        this.s1=s1;
        try{
            eval();
        }catch (Exception e){
            str.delete(0,str.length());
            str.append("ERROR");
        }
    }
    //处理 s1，运用正则表达式得到中缀list
    public void eval()throws Exception{
        List<String> list=new ArrayList<String>();
        Pattern p=Pattern.compile("[+\\-/\\*()]|\\d+\\.?\\d*");
        Matcher m=p.matcher(s1);
        while(m.find()){
            list.add(m.group());
        }
        List<String> afterList=infixToSuffix(list);
        countSuffix(afterList);
    }
    //中缀转后缀
    /*
 遍历中缀list
    1.数字时,加入后缀list
    2.左括号'('，进符号栈
    3.右括号')'，符号栈栈顶元素弹出到后缀list，直到出现栈顶往下的第一个'(',将第一个'('弹出，'('不弹到后缀list
    4.若为运算符，则做如下处置
        1.如果符号栈为空，压栈
        2.如果符号栈不为空
            1.栈顶等于'('，压栈
            2.与栈顶比较优先级
                1.高，压栈
                2.低或者相同,栈顶元素弹出到后缀list
                    1.判断栈顶若为空，压栈
                    2.栈顶为'('，压栈
                    3.比栈顶优先级高，压栈，否则返回上层2.

    重复1.或者2.或者3.或者4.
    直到中缀list遍历完，再将符号栈剩余元素弹出到后缀list ，返回后缀list
     */
    public List<String> infixToSuffix(List<String> midList)throws EmptyStackException {
        List<String> afterList=new ArrayList<String>();//后缀list待添加元素
        Stack<String> stack=new Stack<String>();//符号栈
        for(String str:midList){
            int flag=matchWitch(str);
            switch (flag){
                case 7://中缀list元素为整数或者小数时进入后缀list
                    afterList.add(str);
                    break;
                case 1://左括号'('进入符号栈
                    stack.push(str);
                    break;
                case 2://右括号')'，符号栈栈顶元素弹出到后缀list，直到出现栈顶往下的第一个'(',将第一个'('弹出，'('不弹到后缀list
                    String pop=stack.pop();
                    while(!pop.equals("(")){
                        afterList.add(pop);
                        pop=stack.pop();
                    }
                    break;
                    default://运算符处置
                        if(stack.isEmpty()){//栈为空，压栈
                            stack.push(str);
                            break;
                        }else{//栈顶左括号'('，压栈
                            if(stack.peek().equals("(")){
                                stack.push(str);
                                break;
                            }else{//与栈顶比较优先级
                                int lv_1=proirity(str);
                                int lv_2=proirity(stack.peek());
                                if(lv_1>lv_2){//高于栈顶元素，压栈
                                    stack.push(str);
                                }else{//当栈不为空
                                    while(!stack.isEmpty()){
                                        String f=stack.peek();
                                        if(f.equals("(")){//栈顶为左括号'('，压栈
                                            stack.push(str);
                                            break;
                                        }else{//优先级低于栈顶，栈顶弹出到后缀list
                                            if(proirity(str)<=proirity(f)){
                                                afterList.add(f);
                                                stack.pop();
                                            }else{
                                                stack.push(str);
                                                break;
                                            }
                                        }
                                    }
                                    if(stack.isEmpty()){
                                        stack.push(str);
                                    }
                                }
                                break;
                            }
                        }
            }
        }
        while(!stack.isEmpty()){//符号栈剩余元素加入后缀list
            afterList.add(stack.pop());
        }
        StringBuffer sb=new StringBuffer();//保留后缀list字符串
        for(String s:afterList){
            sb.append(s+" ");
        }
        return afterList;
    }
    //计算后缀表达式
    private double result;//存放后缀表达式计算结果

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void countSuffix(List<String> list){
        str=new StringBuilder("");
        state=0;
        result=0;
        Stack<Double> stack=new Stack<Double>();//计算栈,存放计算结果
        for(String str:list){
            int flag=matchWitch(str);//识别后缀表达式元素，此时已没有左右括号存在
            switch (flag){
                case 3:
                case 4:
                case 5:
                case 6://若元素是运算符，将栈顶和次栈顶元素弹出计算，计算结果再次压入计算栈
                    Double pop1=stack.pop();
                    Double pop2=stack.pop();
                    Double value=singleEval(pop2,pop1,str);
                    stack.push(value);
                    break;
                    default://若元素是数字，数字进入计算栈
                        Double push=Double.parseDouble(str);
                        stack.push(push);
                        break;
            }
        }
        if(stack.isEmpty()){
            state=1;
        }else{
            result=stack.peek();
            str.append(stack.pop());
        }
    }
    //后缀表达式单位计算
    public Double singleEval(Double pop2,Double pop1,String str){
        Double value=0.0;
        if(str.equals("+"))
            value=pop2+pop1;
        else if(str.equals("-"))
            value=pop2-pop1;
        else if(str.equals("*"))
            value=pop2*pop1;
        else
            value=pop2/pop1;
        return value;
    }
    //标记字符串是小数或者整数，还是左右括号，还是运算符
    public int matchWitch(String s){
        if(s.equals("("))
            return 1;
        else if(s.equals(")"))
            return 2;
        else if(s.equals("+"))
            return 3;
        else if(s.equals("-"))
            return 4;
        else if(s.equals("*"))
            return 5;
        else if(s.equals("/"))
            return 6;
        else
            return 7;
    }
    //标记运算符优先级
    public int proirity(String str){
        int result=0;
        if(str.equals("+")||str.equals("-"))
            result=1;
        else
            result=2;
        return result;
    }
}
