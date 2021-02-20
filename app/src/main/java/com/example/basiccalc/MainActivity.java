package com.example.basiccalc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.renderscript.Script;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static java.lang.Double.*;


public class MainActivity extends AppCompatActivity {



    static EditText input;
    String unit,exp="",temExp="";
    String endUser;
    int x=0;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input=findViewById(R.id.inText);
        input.setShowSoftInputOnFocus(false);

    }
    static class BackEnd
    {
        public static void addString(String a)
        {
            String oldString = input.getText().toString();
            int courserLoc = input.getSelectionStart();
            String leftStr = oldString.substring(0,courserLoc);
            String rightStr = oldString.substring(courserLoc);
            input.setText(String.format("%s%s%s",leftStr,a,rightStr));
            input.setSelection(courserLoc+1);
        }
    }

    public void Zero(View view)
    {
        unit="0";
        BackEnd.addString(unit);
    }
    public void One(View view)
    {
        unit="1";
        BackEnd.addString(unit);
    }
    public void Two(View view)
    {
        unit="2";
        BackEnd.addString(unit);
    }public void Three(View view)
    {
        unit="3";
        BackEnd.addString(unit);
    }public void Four(View view)
    {
        unit="4";
        BackEnd.addString(unit);
    }public void Five(View view)
    {
        unit="5";
        BackEnd.addString(unit);
    }public void Six(View view)
    {
        unit="6";
        BackEnd.addString(unit);
    }
    public void Seven(View view)
    {
        unit="7";
        BackEnd.addString(unit);
    }public void Eight(View view)
    {
        unit="8";
        BackEnd.addString(unit);
    }public void Nine(View view)
    {
        unit="9";
        BackEnd.addString(unit);
    }
    public void allClear(View view)
    {
        input.setText("");

    }

    public  void bClear(View view)
    {
        int courser=input.getSelectionStart();
        int inpLenght=input.getText().length();
        if(courser!=0 && inpLenght!=0 )
        {
            SpannableStringBuilder leo= (SpannableStringBuilder) input.getText();
            leo.replace(courser-1,courser,"");
            input.setText(leo);
            input.setSelection(courser-1);
        }

    }

    public void div(View view)
    {
        unit="÷";
        BackEnd.addString(unit);
    }

    public void pro(View view)
    {
        unit="×";
        BackEnd.addString(unit);
    }

    public void minus(View view)
    {
        unit="-";
        BackEnd.addString(unit);
    }

    public  void plus(View view)
    {
        unit="+";
        BackEnd.addString(unit);
    }

    public void deci(View view)
    {
        unit=".";
        BackEnd.addString(unit);
    }

    public void power(View view)
    {
        unit="^";
        BackEnd.addString(unit);
    }

    public  void prnthess(View view)
    {

        int pos = input.getSelectionStart();
        int openPar = 0;
        int closedPar = 0;
        int inboxLength = input.getText().toString().length();

        for (int i = 0; i < input.getSelectionStart(); i++){
            if (input.getText().toString().substring(i, i+1).equals("(")){
                openPar += 1;
            }
            else if (input.getText().toString().substring(i, i+1).equals(")")){
                closedPar += 1;
            }
        }

        if (openPar == closedPar || input.getText().toString().substring(
                inboxLength - 1, inboxLength).equals("(")){
            //usersInputBox.setText(String.format("%s%s", usersInputBox.getText().toString(), "("));
           //usersInputBox.setText(updateText("(", usersInputBox.getText().toString(), usersInputBox.getSelectionStart()));
            BackEnd.addString("(");
        }
        else if (closedPar < openPar && !input.getText().toString().substring(
                inboxLength - 1, inboxLength).equals("(")){
            //usersInputBox.setText(String.format("%s%s", usersInputBox.getText().toString(), ")"));
           // usersInputBox.setText(updateText(")", usersInputBox.getText().toString(), usersInputBox.getSelectionStart()));
            BackEnd.addString(")");
        }
        input.setSelection(pos + 1);

    }

    public void equalBtn(View view)
    {   
        
        endUser=input.getText().toString().trim();
        endUser=endUser.replaceAll("÷","/");
        endUser=endUser.replaceAll("×","*");

        Double res=null;
        ScriptEngine engine=new ScriptEngineManager().getEngineByName("rhino");
        exponentialExp();
        try {
            res=(double)engine.eval(exp);
        } catch (ScriptException e) {
           Toast.makeText(com.example.basiccalc.MainActivity.this,"Invalid Input",Toast.LENGTH_LONG).show();
        }
        if(res!=null)
        {
        input.setText(String.valueOf(res));
        input.setSelection(String.valueOf(res).length());
        }
    }

    private void exponentialExp() {

        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for(int i = 0; i < endUser.length(); i++)
        {
            if (endUser.charAt(i) == '^')
                indexOfPowers.add(i);
        }

        exp = endUser;
        temExp = endUser;
        for(Integer index: indexOfPowers)
        {
            changeFormula(index);
        }
        exp = temExp;

    }

    private void changeFormula(Integer index) {

        String leftnum="",rightnum="";
        for(int i = index + 1; i< endUser.length(); i++)
        {
            if(isNumeric(endUser.charAt(i)))
            {rightnum = rightnum + endUser.charAt(i);}

            else
            {break;}
        }


        for(int i = index - 1; i >= 0; i--)
        {
            if(isNumeric(endUser.charAt(i)))
            { leftnum = leftnum + endUser.charAt(i);}
            else
            { break;}
        }StringBuilder leftNumberBuilder = new StringBuilder(leftnum);
        leftnum = leftNumberBuilder.reverse().toString();

        String original = leftnum + "^" + rightnum;
        String changed = Double.toString(Math.pow(Double.parseDouble(leftnum),Double.parseDouble(rightnum)));
        temExp = temExp.replace(original,changed);
    }

    private boolean isNumeric(char c)
    {
        if((c <= '9' && c >= '0') || c == '.')
        { return true;}

       else{ return false;}
    }


}