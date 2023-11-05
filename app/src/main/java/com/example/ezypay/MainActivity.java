package com.example.ezypay;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ezypay.TransferHandler;
import com.example.ezypay.databinding.ActivityMainBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity"; // Log tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.payButton.setOnClickListener(view -> {
            String amount = binding.amountEditText.getText().toString();
            startPayment(amount);
        });
    }

    private void startPayment(String amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_jSHwMS9oI9FXNi"); // Your Razorpay key

        double finalAmount = Float.parseFloat(amount) * 100;

        try {
            JSONObject options = new JSONObject();
            options.put("amount", finalAmount);
            options.put("currency", "INR");
            options.put("receipt", "txn_123456");

            checkout.open(this, options);
        } catch (Exception e) {
            Log.e(TAG, "Razorpay Payment Error: " + e.getMessage()); // Log error
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        performTransfer("PAYMENT_ID_FROM_RAZORPAY", "50000"); // Simulate a server-side transfer
        binding.myTextView.setText(s);
        Toast.makeText(getApplicationContext(), "Payment Success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG, "Razorpay Payment Error - Code: " + i + ", Description: " + s); // Log error
        Toast.makeText(getApplicationContext(), "Payment Failure!", Toast.LENGTH_SHORT).show();
        binding.myTextView.setText(s);
    }

    private void performTransfer(String paymentId, String amount) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    TransferHandler.handleTransfer(paymentId, amount);
                    Log.i(TAG, "Transfer Process Completed");
                } catch (Exception e) {
                    Log.e(TAG, "Transfer Process Error: " + e.getMessage()); // Log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Transfer Complete!", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
