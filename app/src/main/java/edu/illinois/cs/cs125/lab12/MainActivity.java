package edu.illinois.cs.cs125.lab12;

//package edu.illinois.cs.cs125.mp6;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Main class for our UI design lab.
 */
public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "CherryChanga";

    private static final int READ_REQUEST_CODE = 42;

    private static final int IMAGE_CAPTURE_REQUEST_CODE = 1;

    private static final int REQUEST_WRITE_STORAGE = 112;

    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    private boolean canWriteToPublicStorage = false;

    private boolean photoRequestActive = false;

    private File currentPhotoFile = null;

    /**
     * Run when this activity comes to the foreground.
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        Log.i(TAG, "Our app was created.");

        final ImageButton cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Camera button clicked");
                startCamera();
            }
        });

//        canWriteToPublicStorage = (ContextCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
//        Log.d(TAG, "Do we have permission to write to external storage: "
//                + canWriteToPublicStorage);
//
//        if (!canWriteToPublicStorage) {
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_WRITE_STORAGE);
//        }


        //startAPICall();
    }

    /**
     * Turns on the Camera
     */
    private void startCamera() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/AAPL/batch?types=quote,news",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());

                            System.out.println("Camera button clicked");


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (photoRequestActive) {
//            Log.w(TAG, "Overlapping photo requests");
//            return;
//        }
//
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        currentPhotoFile = getSaveFilename();
//        if (takePictureIntent.resolveActivity(getPackageManager()) == null
//                || currentPhotoFile == null) {
//            Toast.makeText(getApplicationContext(), "Problem taking photo",
//                    Toast.LENGTH_LONG).show();
//            Log.w(TAG, "Problem taking photo");
//            return;
//        }
//
//        Uri photoURI = FileProvider.getUriForFile(this,
//                "SOMEFILE", currentPhotoFile);
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//        photoRequestActive = true;
//        startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQUEST_CODE);
//    }
//
//    /** Initiate the image recognition process. */
//    private void startProcessImage() {
//        startProcessImagetartProcessImageif (currentBitmap == null) {
//            Toast.makeText(getApplicationContext(), "No image selected",
//                    Toast.LENGTH_LONG).show();
//            Log.w(TAG, "No image selected");
//            return;
//        }
//
//        /*
//         * Launch our background task which actually makes the request. It will call
//         * finishProcessImage below with the JSON string when it finishes.
//         */
//        new Tasks.ProcessImageTask(MainActivity.this, requestQueue)
//                .execute(currentBitmap);
}

    /**
     * Opens a file
     */
    private void startOpenFile() {
        Intent openFileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        openFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        openFileIntent.setType("image/*");
        startActivityForResult(openFileIntent, READ_REQUEST_CODE);
    }

//    @Override
//    public void onActivityResult(final int requestCode, final int resultCode,
//                                 final Intent resultData) {
//        if (resultCode != Activity.RESULT_OK) {
//            Log.w(TAG, "onActivityResult with code " + requestCode + " failed");
//            if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
//                photoRequestActive = false;
//            }
//            return;
//        }
//        Uri currentPhotoURI;
//        if (requestCode == READ_REQUEST_CODE) {
//            currentPhotoURI = resultData.getData();
//        } else if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
//            currentPhotoURI = Uri.fromFile(currentPhotoFile);
//            photoRequestActive = false;
//            if (canWriteToPublicStorage) {
//                addPhotoToGallery(currentPhotoURI);
//            }
//        } else {
//            Log.w(TAG, "Unhandled activityResult with code " + requestCode);
//            return;
//        }
//        Log.d(TAG, "Photo selection produced URI " + currentPhotoURI);
//        loadPhoto(currentPhotoURI);
//    }

    /**
     * Run when this activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }





//    /**
//     * Process the result from making the API call.
//     *
//     * @param jsonResult the result of the API call as a string
//     * */
//    protected void finishProcessImage(final String jsonResult) {
//        /*
//         * Pretty-print the JSON into the bottom text-view to help with debugging.
//         */
//        TextView textView = findViewById(R.id.jsonResult);
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jsonParser = new JsonParser();
//        JsonElement jsonElement = jsonParser.parse(jsonResult);
//        String prettyJsonString = gson.toJson(jsonElement);
//        textView.setText(prettyJsonString);
//
//        /*
//         * Create a string describing the image type, width and height.
//         */
//        int width = RecognizePhoto.getWidth(jsonResult);
//        int height = RecognizePhoto.getHeight(jsonResult);
//        String format = RecognizePhoto.getFormat(jsonResult);
//        format = format.toUpperCase();
//        String description = String.format(Locale.US, "%s (%d x %d)", format, width, height);
//
//        /*
//         * Update the UI to display the string.
//         */
//        TextView photoInfo = findViewById(R.id.photoInfo);
//        photoInfo.setText(description);
//
//        /*
//         * Add code here to show the caption, show or hide the dog and cat icons,
//         * and deal with Rick.
//         */
//    }
//
//    /** Current bitmap we are working with. */
//    private Bitmap currentBitmap;
//
//    /**
//     * Process a photo.
//     *
//     * Resizes an image and loads it into the UI.
//     *
//     * @param currentPhotoURI URI of the image to process
//     */
//    private void loadPhoto(final Uri currentPhotoURI) {
//        //enableOrDisableButtons(false);
//        //final ImageButton rotateLeft = findViewById(R.id.rotateLeft);
//        //rotateLeft.setClickable(false);
//        //rotateLeft.setEnabled(false);
//
//        if (currentPhotoURI == null) {
//            Toast.makeText(getApplicationContext(), "No image selected",
//                    Toast.LENGTH_LONG).show();
//            Log.w(TAG, "No image selected");
//            return;
//        }
//        String uriScheme = currentPhotoURI.getScheme();
//
//        byte[] imageData;
//        try {
//            switch (uriScheme) {
//                case "file":
//                    imageData = FileUtils.readFileToByteArray(new File(currentPhotoURI.getPath()));
//                    break;
//                case "content":
//                    InputStream inputStream = getContentResolver().openInputStream(currentPhotoURI);
//                    assert inputStream != null;
//                    imageData = IOUtils.toByteArray(inputStream);
//                    inputStream.close();
//                    break;
//                default:
//                    Toast.makeText(getApplicationContext(), "Unknown scheme " + uriScheme,
//                            Toast.LENGTH_LONG).show();
//                    return;
//            }
//        } catch (IOException e) {
//            Toast.makeText(getApplicationContext(), "Error processing file",
//                    Toast.LENGTH_LONG).show();
//            Log.w(TAG, "Error processing file: " + e);
//            return;
//        }
//
//        /*
//         * Resize the image appropriately for the display.
//         */
//        final ImageView photoView = findViewById(R.id.photoView);
//        int targetWidth = photoView.getWidth();
//        int targetHeight = photoView.getHeight();
//
//        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
//        decodeOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(imageData, 0, imageData.length, decodeOptions);
//
//        int actualWidth = decodeOptions.outWidth;
//        int actualHeight = decodeOptions.outHeight;
//        int scaleFactor = Math.min(actualWidth / targetWidth, actualHeight / targetHeight);
//
//        BitmapFactory.Options modifyOptions = new BitmapFactory.Options();
//        modifyOptions.inJustDecodeBounds = false;
//        modifyOptions.inSampleSize = scaleFactor;
//        modifyOptions.inPurgeable = true;
//
//        // Actually draw the image
//        updateCurrentBitmap(BitmapFactory.decodeByteArray(imageData,
//                0, imageData.length, modifyOptions), true);
//    }
//
//    /*
//     * Helper functions follow.
//     */
//
//    /**
//     * Update the currently displayed image.
//     *
//     * @param setCurrentBitmap the new bitmap to display
//     * @param resetInfo whether to reset the image information
//     */
//    void updateCurrentBitmap(final Bitmap setCurrentBitmap, final boolean resetInfo) {
//        currentBitmap = setCurrentBitmap;
//        ImageView photoView = findViewByID(R.id.photoView);
//        photoView.setImageBitmap(currentBitmap);
//        //enableOrDisableButtons(true);
//
//    }
//
//
//    /**
//     * Add a photo to the gallery so that we can use it later.
//     *
//     * @param toAdd URI of the file to add
//     */
//    void addPhotoToGallery(final Uri toAdd) {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        mediaScanIntent.setData(toAdd);
//        this.sendBroadcast(mediaScanIntent);
//        Log.d(TAG, "Added photo to gallery: " + toAdd);
//    }
//
//    /**
//     * Get a new file location for saving.
//     *
//     * @return the path to the new file or null of the create failed
//     */
//    File getSaveFilename() {
//        String imageFileName = "MP6_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
//                .format(new Date());
//        File storageDir;
//        if (canWriteToPublicStorage) {
//            storageDir = Environment
//                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        } else {
//            storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        }
//        try {
//            return File.createTempFile(imageFileName, ".jpg", storageDir);
//        } catch (IOException e) {
//            Log.w(TAG, "Problem saving file: " + e);
//            return null;
//        }
//    }
}
