
package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=1;
    boolean hasChocolate;
    boolean hasWhippedCream;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCB=(CheckBox) findViewById(R.id.whippedCream_CB);
        hasWhippedCream=whippedCreamCB.isChecked();
        CheckBox ChocolateCB=(CheckBox) findViewById(R.id.Chocolate_CB);
        hasChocolate=ChocolateCB.isChecked();
        EditText Name=(EditText) findViewById(R.id.name);
        String val=Name.getText().toString();
        int price=calculatePrice();
        String msg;
        msg=createOrderSummary(price, hasWhippedCream, hasChocolate,val);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: "+val);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private int  calculatePrice(){
        int price=quantity*5;
        if(hasChocolate){
            price+=2*quantity;
        }
        if(hasWhippedCream)
        {
            price+=quantity;
        }
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_tv);
        quantityTextView.setText("" + number);
    }

    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this,"You can't order more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity+=1;
        display(quantity);
    }
    public void decrement(View view) {
        if(quantity>1)
        {
            quantity -=1;
        }
        else
    {
            Toast.makeText(this,"You can't order less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }
    private String createOrderSummary(int price,boolean see, boolean choco,String name){
        String message;
        message="Name: "+name+"\nAdd whipped Cream? "+see+"\nAdd Chocolate? "+choco+"\nQuantity: "+quantity+"\nTotal: $"+calculatePrice()+"\nThank You!";
        return message;
    }
}
