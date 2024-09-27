package com.example.appmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView txtZ1, txtZ2, txtZ3, txtZ4, txtON_OFF;
    private Button btnActivate, btnDeactivate, btnReset;
    private Updater updater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtZ1 = (TextView) findViewById(R.id.txtZ1);
        txtZ2 = (TextView) findViewById(R.id.txtZ2);
        txtZ3 = (TextView )findViewById(R.id.txtZ3);
        txtZ4 = (TextView) findViewById(R.id.txtZ4);
        txtON_OFF = (TextView) findViewById(R.id.txtON_OFF);

        btnActivate = findViewById(R.id.btnActivate);
        btnDeactivate = findViewById(R.id.btnDeactivate);
        btnReset = findViewById(R.id.btnReset);

        updater = new Updater();

        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiHelper.arm(new ApiHelper.ApiCallback() {
                    @Override
                    public void onResult(final String result) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, "Système activé", Toast.LENGTH_SHORT).show();
                            updateZoneStatus(result);
                        });
                    }
                });
                updater.startUpdating(new ApiHelper.ApiCallback() {
                    @Override
                    public void onResult(final String result) {
                        runOnUiThread(() -> updateZoneStatus(result));
                    }
                });
            }
        });

        btnDeactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiHelper.deactivate(new ApiHelper.ApiCallback() {
                    @Override
                    public void onResult(final String result) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, "Système désactivé", Toast.LENGTH_SHORT).show();
                            setTextViewDeactivate();
                            updater.stopUpdating();
                        });
                    }
                });
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiHelper.reset(new ApiHelper.ApiCallback() {
                    @Override
                    public void onResult(final String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Zones réinitialisées", Toast.LENGTH_SHORT).show();
                                setTextViewReset();
                            }
                        });
                    }
                });
            }
        });


    }
    private void updateZoneStatus(String result) {
        try {
            // Vérifiez si la réponse est un message d'erreur ou autre chose
            if (result.contains("Erreur") || result.contains("Error")) {
                Toast.makeText(MainActivity.this, "Erreur lors de l'appel API", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject jsonObject = new JSONObject(result);
            boolean z1 = jsonObject.getInt("z1") == 1;
            boolean z2 = jsonObject.getInt("z2") == 1;
            boolean z3 = jsonObject.getInt("z3") == 1;
            boolean z4 = jsonObject.getInt("z4") == 1;
            boolean statut = jsonObject.getInt("statut") == 1;


            updateTextView(txtZ1, z1);
            updateTextView(txtZ2, z2);
            updateTextView(txtZ3, z3);
            updateTextView(txtZ4, z4);
            updateTextView(txtON_OFF, statut);

        } catch (JSONException e) {
            Log.e("MainActivity", "Erreur de parsing JSON", e);
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("MainActivity", "Erreur générale", e);
            Toast.makeText(MainActivity.this, "Erreur lors du traitement des données", Toast.LENGTH_SHORT).show();
        }
    }



    private void updateTextView(TextView textView, boolean status) {
        //textView.setText(status ? "true" : "false");
        textView.setTextColor(status ? Color.GREEN : Color.RED);
    }
    private void setTextViewDeactivate() {
        txtZ1.setTextColor(Color.RED);
        txtZ2.setTextColor(Color.RED);
        txtZ3.setTextColor(Color.RED);
        txtZ4.setTextColor(Color.RED);
        txtON_OFF.setTextColor(Color.RED);
    }
    private void setTextViewReset(){
        txtZ1.setTextColor(Color.GREEN);
        txtZ2.setTextColor(Color.GREEN);
        txtZ3.setTextColor(Color.GREEN);
        txtZ4.setTextColor(Color.GREEN);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        updater.stopUpdating();
    }

}