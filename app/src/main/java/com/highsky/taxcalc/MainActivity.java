package com.highsky.taxcalc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Locale;

import static java.math.RoundingMode.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    StringBuilder equation = new StringBuilder("");
    double total = 0;
    StringBuilder currentValue = new StringBuilder();
    boolean start = true;
    boolean firstNumber = true;
    boolean decimalClicked = false;
    boolean operatorClicked = false;
    String lastOperator = "blank";
    double tax = 0;
    boolean equalPressed = false;
    double addedFromTax = 0;
    double subtotal = 0;
    //DecimalFormat df = new DecimalFormat(".##");
    //df.setRoundingMode(HALF_UP);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn0 = (Button) findViewById(R.id.Btn0);
        btn0.setOnClickListener(this);
        Button btn1 = (Button) findViewById(R.id.Btn1);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.Btn2);
        btn2.setOnClickListener(this);
        Button btn3 = (Button) findViewById(R.id.Btn3);
        btn3.setOnClickListener(this);
        Button btn4 = (Button) findViewById(R.id.Btn4);
        btn4.setOnClickListener(this);
        Button btn5 = (Button) findViewById(R.id.Btn5);
        btn5.setOnClickListener(this);
        Button btn6 = (Button) findViewById(R.id.Btn6);
        btn6.setOnClickListener(this);
        Button btn7 = (Button) findViewById(R.id.Btn7);
        btn7.setOnClickListener(this);
        Button btn8 = (Button) findViewById(R.id.Btn8);
        btn8.setOnClickListener(this);
        Button btn9 = (Button) findViewById(R.id.Btn9);
        btn9.setOnClickListener(this);
        Button btnDec = (Button) findViewById(R.id.BtnDec);
        btnDec.setOnClickListener(this);
        Button btnC = (Button) findViewById(R.id.BtnClear);
        btnC.setOnClickListener(this);
        Button btnAdd = (Button) findViewById(R.id.BtnPlus);
        btnAdd.setOnClickListener(this);
        Button btnSub = (Button) findViewById(R.id.BtnSub);
        btnSub.setOnClickListener(this);
        Button btnMul = (Button) findViewById(R.id.BtnMul);
        btnMul.setOnClickListener(this);
        Button btnDiv = (Button) findViewById(R.id.BtnDiv);
        btnDiv.setOnClickListener(this);
        Button btnEq = (Button) findViewById(R.id.BtnEqual);
        btnEq.setOnClickListener(this);
        setEquation("0");
        setSubTotal(0);
        setAmountFromTaxed(0);
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        tax = pref.getFloat("tax", 0);

        //add listeners for +-*/
    }

    private void setEquation(String set) {
        TextView textView = (TextView) findViewById(R.id.equation);
        equation.append(set);
        textView.setText(equation.toString());
    }

    private void setSubTotal(double subTotal) {
        TextView textView = (TextView) findViewById(R.id.subTotal);
        String fmt = String.format(Locale.US, "%.2f", subTotal);
        textView.setText(MessageFormat.format("Subtotal: ${0}", fmt));

    }

    private void setAmountFromTaxed(double fromTaxed) {
        TextView textView = (TextView) findViewById(R.id.amountTax);
        String fmt = String.format(Locale.US, "%.2f", fromTaxed);
        textView.setText(MessageFormat.format("From tax: ${0}", fmt));

    }

    private void setTotal(String set) {
        TextView textView = (TextView) findViewById(R.id.sum);
        textView.setText(set);
    }

    private void reset() {
        start = true;
        firstNumber = true;
        equation.setLength(0);
        setEquation("0");
        total = 0;
        setTotal(Double.toString(total));
        currentValue.setLength(0);
        decimalClicked = false;
        addedFromTax = 0;
        setAmountFromTaxed(addedFromTax);
        lastOperator = "";
        setSubTotal(0);
        equalPressed = false;
    }

    private void operator(String operator) {
        //convert currentValue to double Double.parseDouble
        //check if first num(using boolean) and add to total if it is change boolean to false, else do operation using total and current value

        if (firstNumber) {
            firstNumber = false;
            decimalClicked = false;
            subtotal = Double.parseDouble(currentValue.toString());
            setSubTotal(subtotal);
            addedFromTax = (subtotal * tax);
            setAmountFromTaxed(addedFromTax);
            lastOperator = operator;
            currentValue.setLength(0);
            setEquation(operator);
            setTotal(Double.toString(subtotal));
            return;
        }
            /*
            * if equalpressed{
            *   lastOperator = operator
            *   decimalClicked, currentValue(0)
            * }
            * */
        if (equalPressed) {
            equalPressed = false;
            operatorClicked = true;
            setEquation(operator);
            decimalClicked = false;
            currentValue.setLength(0);
            lastOperator = operator;
            return;
        }

        if (lastOperator.equals("+")) {
            subtotal = subtotal + Double.parseDouble(currentValue.toString());
            setSubTotal(subtotal);
            addedFromTax = (subtotal * tax);
            setAmountFromTaxed(addedFromTax);
            setEquation(operator);
            setTotal(Double.toString(subtotal));
            currentValue.setLength(0);
            lastOperator = operator;
            decimalClicked = false;
        } else if (lastOperator.equals("-")) {
            subtotal = subtotal - Double.parseDouble(currentValue.toString());
            setSubTotal(subtotal);
            addedFromTax = (subtotal * tax);
            setAmountFromTaxed(addedFromTax);
            setEquation(operator);
            setTotal(Double.toString(subtotal));
            currentValue.setLength(0);
            lastOperator = operator;
            decimalClicked = false;
        } else if (lastOperator.equals("*")) {
            subtotal = subtotal * Double.parseDouble(currentValue.toString());
            setSubTotal(subtotal);
            addedFromTax = (subtotal * tax);
            setAmountFromTaxed(addedFromTax);
            setEquation(operator);
            setTotal(Double.toString(subtotal));
            currentValue.setLength(0);
            lastOperator = operator;
            decimalClicked = false;
        } else if (lastOperator.equals("/")) {
            if (Double.parseDouble(currentValue.toString()) == 0) {
                reset();
                setTotal("Can't divide by zero");
            } else {
                subtotal = subtotal / Double.parseDouble(currentValue.toString());
                setSubTotal(subtotal);
                addedFromTax = (subtotal * tax);
                setAmountFromTaxed(addedFromTax);
                setEquation(operator);
                setTotal(Double.toString(subtotal));
                currentValue.setLength(0);
                lastOperator = operator;
                decimalClicked = false;
            }
        }
        operatorClicked = true;

    }

    private void findTotal() {
        equalPressed = true;
        if (firstNumber) {
            subtotal = Double.parseDouble(currentValue.toString());
            setSubTotal(subtotal);
            addedFromTax = (subtotal * tax);
            total = subtotal + addedFromTax;
            String fmt = String.format(Locale.US, "%.2f", total);
            setTotal("$" + fmt);
            setAmountFromTaxed(addedFromTax);
            return;
        }
        if (lastOperator.equals("+")) {
            subtotal = subtotal + Double.parseDouble(currentValue.toString());
            setSubTotal(subtotal);
            addedFromTax = (subtotal * tax);
            total = subtotal + addedFromTax;
            String fmt = String.format(Locale.US, "%.2f", total);
            setTotal("$" + fmt);

        } else if (lastOperator.equals("-")) {
            subtotal = subtotal - Double.parseDouble(currentValue.toString());
            setSubTotal(subtotal);
            addedFromTax = (subtotal * tax);
            total = subtotal + addedFromTax;
            String fmt = String.format(Locale.US, "%.2f", total);
            setTotal("$" + fmt);
        } else if (lastOperator.equals("*")) {
            subtotal = subtotal * Double.parseDouble(currentValue.toString());
            setSubTotal(subtotal);
            addedFromTax = (subtotal * tax);
            total = subtotal + addedFromTax;
            String fmt = String.format(Locale.US, "%.2f", total);
            setTotal("$" + fmt);
        } else {
            //handle division case here
            if (Double.parseDouble(currentValue.toString()) == 0) {
                reset();
                setTotal("Can't divide by zero");
            } else {
                subtotal = subtotal / Double.parseDouble(currentValue.toString());

                setSubTotal(subtotal);
                addedFromTax = (subtotal * tax);
                total = subtotal + addedFromTax;
                String fmt = String.format(Locale.US, "%.2f", total);
                setTotal("$" + fmt);
            }
        }
        setAmountFromTaxed(addedFromTax);
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        switch (view.getId()) {
            case R.id.Btn0:
                if (equalPressed) {
                    reset();
                }
                if (start) {
                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn1:
                if (equalPressed) {
                    reset();
                }
                if (start) {
                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn2:
                if (equalPressed) {
                    reset();
                }
                if (start) {
                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn3:
                if (equalPressed) {
                    reset();
                }
                if (start) {
                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn4:
                if (equalPressed) {
                    reset();
                }
                if (start) {

                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn5:
                if (equalPressed) {
                    reset();
                }
                if (start) {

                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn6:
                if (equalPressed) {
                    reset();
                }
                if (start) {

                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn7:
                if (equalPressed) {
                    reset();
                }
                if (start) {

                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn8:
                if (equalPressed) {
                    reset();
                }
                if (start) {

                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.Btn9:
                if (equalPressed) {
                    reset();
                }
                if (start) {

                    equation.setLength(0);
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                    start = false;
                } else if (operatorClicked) {
                    operatorClicked = false;
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                } else {
                    setEquation(b.getText().toString());
                    currentValue.append(b.getText().toString());
                }
                break;
            case R.id.BtnDec:
                if (equalPressed) {
                    reset();
                }
                if (!decimalClicked) {
                    decimalClicked = true;
                    if (start) {
                        equation.setLength(0);
                        setEquation(b.getText().toString());
                        currentValue.append(b.getText().toString());
                        start = false;
                    } else if (operatorClicked) {
                        operatorClicked = false;
                        setEquation(b.getText().toString());
                        currentValue.append(b.getText().toString());
                    } else {
                        setEquation(b.getText().toString());
                        currentValue.append(b.getText().toString());
                    }
                }
                break;
            case R.id.BtnClear:
                reset();
                break;
            case R.id.BtnPlus:
                if (!operatorClicked) {
                    if (currentValue.length() == 0)
                        currentValue.append("0");
                    operator("+");
                }
                break;
            case R.id.BtnSub:
                if (!operatorClicked) {
                    if (currentValue.length() == 0)
                        currentValue.append("0");
                    operator("-");
                }
                break;
            case R.id.BtnMul:
                if (!operatorClicked) {
                    if (currentValue.length() == 0)
                        currentValue.append("0");
                    operator("*");
                }
                break;
            case R.id.BtnDiv:
                if (!operatorClicked) {
                    if (currentValue.length() == 0)
                        currentValue.append("0");
                    operator("/");
                }
                break;
            case R.id.BtnEqual:
                if (!operatorClicked) {
                    equalPressed = true;
                    findTotal();
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this,item.getItemId()+" clicked", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        alert.setMessage("Enter in the tax rate (no negative)");
        alert.setTitle("Tax Rate");

        alert.setView(edittext);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                //Editable YouEditTextValue = edittext.getText();
                //OR
                String YouEditTextValue = edittext.getText().toString();

                if (!isNumeric(YouEditTextValue) || YouEditTextValue.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Not even a number dude", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(YouEditTextValue) < 0) {
                    Toast.makeText(MainActivity.this, "Tax rates can't be negative dummy", Toast.LENGTH_SHORT).show();
                } else {
                    tax = Double.parseDouble(YouEditTextValue);
                    SharedPreferences pref = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putFloat("tax", (float) tax);
                    editor.apply();

                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                Toast.makeText(MainActivity.this, "Current rate is " + String.format(Locale.US, "%.2f", tax), Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();

        return super.onOptionsItemSelected(item);
    }

    public static boolean isNumeric(String str) {

        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
