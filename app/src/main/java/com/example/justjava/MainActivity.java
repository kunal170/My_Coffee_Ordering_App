package com.example.justjava;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity=2;
    boolean hasWhippedCream=false;
    boolean hasChocolate = false;
    int priceChocolate = 0;
    int priceWhippedCream = 0;
    String customerName=" ";
    String customerEmail = " ";
    public void submitOrder(View view) {

        /*display(quantity);
        displayPrice(quantity * 5);*/
        String priceMessage = createOrderSummary();
        composeEmail(priceMessage);
    }
    public void whippedCream(View view){
     if(hasWhippedCream == false) {
         hasWhippedCream = true;
         priceWhippedCream = 2;
     }
     else {
         hasWhippedCream = false;
         priceWhippedCream = 0;
     }
    }
    public void chocolate(View view){
        if(hasChocolate == false) {
            hasChocolate = true;
            priceChocolate = 1;
        }
        else {
            hasChocolate = false;
            priceChocolate = 0;
        }
    }
    private String createOrderSummary(){
        String toppings;
        if(hasWhippedCream == true && hasChocolate == true)
            toppings="Whipped Cream, Chocolate";
        else if(hasWhippedCream == true)
            toppings = "Whipped Cream";
        else if(hasChocolate == true)
            toppings = "Chocolate";
        else
            toppings="NIL";

        EditText text = (EditText) findViewById(R.id.entered_name);
        String name = text.getText().toString();
        customerName = name;
        EditText emailText = (EditText) findViewById(R.id.email);
        String email = emailText.getText().toString();
        customerEmail = email;
        String message="Name: " +  name + "\n" + "Quantity: " + quantity + "\n" + "Toppings: " + toppings +"\n" + "Total: $ "+ calculatePrice() + "\n" + "Thank You!";
        return message;
    }
    void composeEmail(String orderSummary){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        String subject = "Coffee Order For " + customerName;
        String[] addresses={customerEmail};
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

private int calculatePrice(){
        int priceOfOneCoffee = 5 + priceChocolate + priceWhippedCream;
        return (quantity * priceOfOneCoffee);
}
    public void increment(View view){
      if(quantity >= 100)
          quantity = 100;
      else
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view){
       if(quantity <= 1)
           quantity = 1;
       else
        quantity=quantity-1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int number) {
        TextView  orderSummaryTextView= (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}