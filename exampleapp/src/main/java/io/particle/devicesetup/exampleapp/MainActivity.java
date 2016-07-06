package io.particle.devicesetup.exampleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import io.particle.android.sdk.devicesetup.ParticleDeviceSetupLibrary;
import io.particle.android.sdk.devicesetup.SetupCompleteIntentBuilder;
import io.particle.android.sdk.devicesetup.SetupResult;
import io.particle.android.sdk.utils.ui.Ui;

public class MainActivity extends AppCompatActivity {
    private final static String EXTRA_SETUP_LAUNCHED_TIME = "io.particle.devicesetup.exampleapp.SETUP_LAUNCHED_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParticleDeviceSetupLibrary.init(this.getApplicationContext());

        Ui.findView(this, R.id.start_setup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invokeDeviceSetup();
            }
        });

        Ui.findView(this, R.id.start_setup_custom_intent_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeDeviceSetupWithCustomIntentBuilder();
            }
        });

        String setupLaunchTime = this.getIntent().getStringExtra(EXTRA_SETUP_LAUNCHED_TIME);

        if (setupLaunchTime != null) {
            TextView label = Ui.findView(this, R.id.textView);

            label.setText(String.format(getString(R.string.welcome_back), setupLaunchTime));
        }
    }

    public void invokeDeviceSetup() {
        ParticleDeviceSetupLibrary.startDeviceSetup(this, MainActivity.class);
    }

    private void invokeDeviceSetupWithCustomIntentBuilder() {
        final String setupLaunchedTime = new Date().toString();

        ParticleDeviceSetupLibrary.startDeviceSetup(this, new SetupCompleteIntentBuilder() {
            @Override
            public Intent buildIntent(Context ctx, SetupResult result) {
                Intent intent = new Intent(ctx, MainActivity.class);
                intent.putExtra(EXTRA_SETUP_LAUNCHED_TIME, setupLaunchedTime);

                return intent;
            }
        });

    }
}
