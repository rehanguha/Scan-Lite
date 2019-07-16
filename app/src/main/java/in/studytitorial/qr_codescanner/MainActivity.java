package in.studytitorial.qr_codescanner;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    public void resumeScanning(){
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText() + "\n" +  rawResult.getBarcodeFormat());

        //Button One : Yes
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resumeScanning();
                Toast.makeText(MainActivity.this, "Yes button Clicked!", Toast.LENGTH_LONG).show();
            }
        });


        //Button Two : No
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resumeScanning();
                Toast.makeText(MainActivity.this, "No button Clicked!", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });


        //Button Three : Neutral
        builder.setNeutralButton("Can't Say!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resumeScanning();
                Toast.makeText(MainActivity.this, "Neutral button Clicked!", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });

        AlertDialog alert1 = builder.create();
        alert1.setCancelable(false);
        alert1.setCanceledOnTouchOutside(false);
        alert1.show();
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);

    }

}
