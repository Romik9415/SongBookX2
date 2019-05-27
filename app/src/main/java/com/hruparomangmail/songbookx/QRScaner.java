package com.hruparomangmail.songbookx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.hruparomangmail.songbookx.activities.Activity_detail_full;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.ContentValues.TAG;

public class QRScaner extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
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

    @Override
    public void handleResult(Result rawResult) {
        if(rawResult.getBarcodeFormat().toString().equals("QR_CODE")) {
            Log.v(TAG, rawResult.getText()); // Prints the scan format (qrcode, pdf417 etc.)
            String result = rawResult.getText();
            String resultType = result.split("/")[0];
            if(resultType.equals("song")) {
                try {
                    String songId = (result.split("/")[1]);
                    Intent intent = new Intent(this, Activity_detail_full.class);
                    intent.putExtra(Activity_detail_full.EXTRA_ID, songId);
                    finish();
                    startActivity(intent);

                }
                catch (NumberFormatException e){
                    Log.e("NumberFormatException",e.toString());
                    Toast.makeText(this, R.string.wrong_qr_code_toast,Toast.LENGTH_SHORT);
                }
            }
            else {
                Toast.makeText(this, R.string.wrong_qr_code_toast,Toast.LENGTH_SHORT);
            }
        }

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}