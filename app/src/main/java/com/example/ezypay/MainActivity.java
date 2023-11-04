package com.example.ezypay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ezypay.databinding.ActivityMainBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  amount = binding.amountEditText.getText().toString();
                PaymentNow(amount);
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(binding.getRoot());
    }

    private void PaymentNow(String amount){

        final Activity activity = this;

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_jSHwMS9oI9FXNi");
        checkout.setImage(R.drawable.ic_launcher_background);




        double finalAmount = Float.parseFloat(amount)*100;

        try {
            JSONObject options = new JSONObject();
            options.put("name","Priyanshu");
            options.put("description","Reference no 123456");
            options.put("image","Priyanshu");
            options.put("theme.color","#3399cc");
            options.put("currency","INR");
            options.put("amount",finalAmount+"");
            options.put("prefill.email","priyanshubisht204@gmail.com");
            options.put("prefill.contact","1234567890");

            checkout.open(activity,options);
        } catch (Exception e) {
            Log.e("Checkout","Error in starting RazorPay Checkout",e);
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(),"Payment Success!",Toast.LENGTH_SHORT);
        binding.myTextView.setText(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),"Payment Failure!",Toast.LENGTH_SHORT);
        binding.myTextView.setText(s);
    }
}