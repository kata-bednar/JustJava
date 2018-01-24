/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;
    private String orderSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whipping = (CheckBox) findViewById(R.id.whipping_checkbox);
        boolean hasWhippedCream = whipping.isChecked();
        Log.i ("MainActivity.java", "Whipping checkbox is checked: " + Boolean.toString(hasWhippedCream));

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        EditText name= (EditText) findViewById(R.id.enter_name);
        String customerName = name.getText().toString();
        Log.i ("MainActivity.java", "Name:" + customerName);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        createOrderSummary (price, hasWhippedCream, hasChocolate, customerName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        String [] addresses = new String[] {"udacityandroiddemo@gmail.com"};
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);


        //intent.setData(Uri.parse("mailto: udacityandroiddemo@gmail.com"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = quantity * 5;
        if (hasWhippedCream == true) {
            price = price + 1 * quantity;
        }

        if (hasChocolate == true) {
            price = price + 2 * quantity;
        }
        return price;}

    /**
     * Create the summary of the order.
     *
     * @param price of the order
     * @return text summary
     */
    private void createOrderSummary (int price, boolean hasWhippedCream, boolean hasChocolate, String customerName) {
        String priceMessage = "Name: " + customerName;
        priceMessage += "\n" + getString(R.string.whipping) +"?" + hasWhippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);

        orderSummary = priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {

        if (quantity >= 100) {
            Toast.makeText(this, "You can not order more than 100 units of coffee.", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
            displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {

        if (quantity < 2) {
            Toast.makeText(this, "You can not order less than 1 unit of coffee.", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

}