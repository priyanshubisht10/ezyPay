package com.example.ezypay;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

public class TransferHandler {
    private static final String TAG = "TransferHandler"; // Log tag
    public static void handleTransfer(String paymentId, String amount) {
        // Simulated server-side transfer code
        String keyId = "rzp_test_jSHwMS9oI9FXNi";
        String keySecret = "UOuQNIbK---------WOr1Sq";

        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            JSONObject transferRequest = new JSONObject();
            transferRequest.put("amount", amount);
            transferRequest.put("currency", "INR");
            transferRequest.put("account", "acc_CPRsN1LkFccllA");

            JSONObject amountObject = new JSONObject();
            amountObject.put("amount", amount);
            razorpay.payments.capture(paymentId, amountObject);
            Log.i(TAG, "Payment captured for paymentId: " + paymentId + " with amount: " + amount);

            razorpay.transfers.create(transferRequest);
            Log.i(TAG, "Transfer initiated for amount: " + amount);
        } catch (RazorpayException | JSONException e) {

            Log.e(TAG, "Razorpay Error: " + e.getMessage());
            e.printStackTrace();

        }
    }
}
