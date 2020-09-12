package com.example.pennapphack.create;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pennapphack.R;
import com.example.pennapphack.home.HomeFragment;
import com.example.pennapphack.models.Post;
import com.example.pennapphack.models.Preferences;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {

    String [] TIMELIST ={"30","60","90","120"};
    String [] PRICELIST ={"$","$$","$$$"};

    private Button post;
    private Button picture;
    private EditText information;
    private ImageView foodPic;
    private EditText recipeName;
    private Spinner timeSpin;
    private Spinner priceSpin;
    private Spinner timeSpinner;
    private Spinner priceSpinner;

    public static final String TAG = "CreateFragment";

    private File photoFile;
    private String photoFileName = "photo.jpg";

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public static final int UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE = 50;

    private int time;
    private int price;
    private int access;
    private ParseUser currentUser;


    public CreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post = view.findViewById(R.id.btnPost);
        picture = view.findViewById(R.id.btnFood);
        information = view.findViewById(R.id.etDescription);
        foodPic = view.findViewById(R.id.foodPicture);
        recipeName = view.findViewById(R.id.recipeName);
        timeSpin = view.findViewById(R.id.timeDrop);
        priceSpin = view.findViewById(R.id.priceDrop);

        //time spinner
        ArrayAdapter<String> arrayAdapterTime= new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, TIMELIST);
        timeSpinner = (Spinner)timeSpin;
        timeSpinner.setAdapter(arrayAdapterTime);

        //price spinner
        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, PRICELIST);
        priceSpinner = (Spinner)priceSpin;
        priceSpinner.setAdapter(arrayAdapterPrice);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = information.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(getContext(), "Description cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = recipeName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Give your recipe a name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentUser = ParseUser.getCurrentUser();

                String timeString = timeSpinner.getSelectedItem().toString();
                time = Integer.parseInt(timeString);

                String priceString = priceSpinner.getSelectedItem().toString();
                price = priceString.length();

                getAccess();

            }
        });
    }

    private void getAccess() {
        ParseQuery<Preferences> query = ParseQuery.getQuery(Preferences.class);
        query.whereEqualTo(Preferences.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Preferences>() {
            @Override
            public void done(List<Preferences> preferences, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                access = preferences.get(0).getAccess();
                String description = information.getText().toString();

                savePost(recipeName.getText().toString(), description, currentUser, photoFile, price, time, access);



            }
        });

    }

    private void savePost(final String nameRecipe, String description, ParseUser currentUser, File photoFile, int price, int time, int access) {
        Post post = new Post();
        post.setRecipeName(nameRecipe);
        post.setRecipe(description);
        post.setUser(currentUser);
        if (photoFile != null) {
            post.setImage(new ParseFile(photoFile));
        }
        post.setPrice(price);
        post.setTime(time);
        post.setAccess(access);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Successfully saved post");
                Log.i(TAG, "Successfully saved post");
                recipeName.setText("");
                information.setText("");
                foodPic.setImageResource(0);
                returnHomeFragment();
            }
        });


    }

    private void returnHomeFragment() {
        Fragment fragment = new HomeFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContainer, fragment, TAG)
                .addToBackStack(null)
                .commit();

    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    launchCamera();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.pennapphack.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                foodPic.setImageBitmap(takenImage);

            }
            else if (requestCode == UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE) {
                Uri photoUri = data.getData();

                // Load the image located at photoUri into selectedImage
                Bitmap selectedImage = loadFromUri(photoUri);

                Log.w(TAG, "path of image from gallery" + selectedImage+"");
                foodPic.setImageBitmap(selectedImage);

            }
            else { // Result was a failure
                Toast.makeText(getContext(), "Error getting picture", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            InputStream in = getContext().getContentResolver().openInputStream(photoUri);
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);

                photoFile = createFileFromInputStream(in, photoFileName);


            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoUri);

                photoFile = createFileFromInputStream(in, photoFileName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private File createFileFromInputStream(InputStream inputStream, String fileName) {
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        try {
            File f = new File(mediaStorageDir.getPath() + File.separator + fileName);
            f.setWritable(true, false);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        } catch (IOException e) {
            System.out.println("error in creating a file");
            e.printStackTrace();
        }

        return null;
    }

    private File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }



}