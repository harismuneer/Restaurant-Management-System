package com.dineout.code.order;
import com.dineout.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_welcome_page);
        Toast.makeText(getApplicationContext(), "Continue for Menu!", Toast.LENGTH_LONG).show();

    }

public void redirectToMenu(View v)
{
    startActivity(new Intent(WelcomePage.this,MainActivity.class));
}

}
