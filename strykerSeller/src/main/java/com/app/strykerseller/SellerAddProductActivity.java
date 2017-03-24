package com.app.strykerseller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.jsoncall.JsonCall;
import com.app.model.CategoryModel;
import com.app.utills.AppUtil;
import com.app.utills.AppUtils;
import com.isseiaoki.simplecropview.CropImageView;
import com.squareup.picasso.Picasso;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import eu.janmuller.android.simplecropimage.CropImage;

import static android.R.attr.angle;
import static android.R.attr.pivotX;
import static android.R.attr.pivotY;


/**
 * Created by Inflac on 19-10-2015.
 */
public class SellerAddProductActivity extends Activity  {

    ImageView img_back, img_Product;
    EditText edt_desc, edt_price, edt_category, edt_product_name, edt_bullet;
    TextView txt_done;
    Button btnAdd;
    LinearLayout lBullets;
    Spinner spinCategory;
    static public int MEDIA_TYPE_IMAGE = 1;
    public static final String TEMP_PHOTO_FILE_NAME = "new_photo.jpg";
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 3;
    public static final int REQUEST_CODE_CROP_IMAGE = 2;
    String path = "";
    public static String imgpath = "";
    String catID;
    ProgressDialog dialog;
    Context context;
    String is_hot = "0", storeId = "";
    List<CategoryModel> listCategory = new ArrayList<>();
    List<String> listCat = new ArrayList<>();
    CategoryModel mod = new CategoryModel();
    ArrayList<HashMap<String,String>> arHm = new ArrayList<HashMap<String, String>>();
    int bulletCount = 0;
    StringBuilder sb = new StringBuilder();
    String uri = "";
    String prodImageUrl = "";
    private static int RESULT_LOAD_IMG = 1;
    RelativeLayout r1_ProductImage;

    Dialog uploadOptionDialog;
    CropImageView pcropImageView;

    boolean isUploading = false;

    String stringImageName = "";
    private String mAttachedFilePath;

    File compressedPictureFile = null;
    private static final int REQUEST_CODE = 0x11;

    Bitmap bitmapResizedImage;
    private Uri fileUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_activity_add_products);

        context = this;
        init();
        setListener();

        Bundle b = getIntent().getExtras();

        if (b != null) {
            if (b.containsKey("store_id")) {
                storeId = b.getString("store_id");
                Log.e("StoreId in Add Product", storeId);
            }
        }

        if (b.containsKey("product_category")) {
            String jsonArray = b.getString("product_category");
            Log.e("Category Array", jsonArray);

            try {
                JSONArray proCategory = new JSONArray(jsonArray);

                JSONObject joCat;
                for(int i=0;i<proCategory.length();i++)
                {
                    joCat = proCategory.getJSONObject(i);
                    String catId = joCat.getString("Id");
                    String catName = joCat.getString("Name");
                    listCategory.add(new CategoryModel(catId,catName));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**Add items to category**/

        for(int i=0; i<listCategory.size();i++)
        {
            listCat.add(listCategory.get(i).getCatName());
        }

        /****/

        /**Spinner Category**/

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, listCat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setPrompt("Choose Category!");
        spinCategory.setAdapter(dataAdapter);
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                catID = listCategory.get(position).getCatId();
            }


            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        /****/



    }


    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        AppUtil.onKeyBoardDown(context);
    }

    public void init() {

        txt_done = (TextView) findViewById(R.id.txt_done);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_Product = (ImageView) findViewById(R.id.img_product);




        edt_desc = (EditText) findViewById(R.id.edt_desc);
        edt_category = (EditText) findViewById(R.id.edt_category);
        edt_price = (EditText) findViewById(R.id.edt_price);
        edt_product_name = (EditText) findViewById(R.id.edt_product_name);

        r1_ProductImage = (RelativeLayout)findViewById(R.id.rl_product_image);
        btnAdd = (Button) findViewById(R.id.btn_add);
        edt_bullet = (EditText) findViewById(R.id.edt_bullet);
        lBullets = (LinearLayout)findViewById(R.id.ll_bullets);

        spinCategory= (Spinner)findViewById(R.id.spin_cat);

    }


    public void setListener()
    {

        img_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        img_Product.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                showOptionDialog(v);


                //selectImage();
            }
        });

        txt_done.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {




               // uploadImage(imgpath);


                AppUtil.onKeyBoardDown(context);
                if(isUploading){
                    AppUtil.showCustomToast(context, "please wait... image is uploading.");
                }else if (AppUtils.isNetworkAvailable(context)) {
                    dialog = ProgressDialog.show(context, "", "Please wait....");
                    AddProduct product = new AddProduct();
                    Log.i("bullet data",getBulletData(arHm));
                    product.execute(new String[]{storeId, edt_product_name.getText().toString(),edt_desc.getText().toString().trim(),
                            catID, edt_price.getText().toString(),prodImageUrl});
                } else {
                    AppUtils.showCustomToast(context, "Please check your internet connection");
                }
            }
        });


        /**Layout View at RunTime**/
        // http://android-er.blogspot.in/2013/05/add-and-remove-view-dynamically.html

        btnAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View addView = layoutInflater.inflate(R.layout.seller_row_add_product_bullet, null);

                TextView txtBullet = (TextView)addView.findViewById(R.id.txt_bullet);
                ImageView imgRemove = (ImageView)addView.findViewById(R.id.img_remove);

                txtBullet.setText(edt_bullet.getText().toString().trim());

                imgRemove.setTag(bulletCount);

                imgRemove.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        int posi = (int)v.getTag();
                        ((LinearLayout) addView.getParent()).removeView(addView);
                        try{
                            for (int i = 0; i< arHm.size(); i++){
                                if(arHm.get(i).get("tag").equalsIgnoreCase(String.valueOf(posi))){
                                    arHm.remove(i);
                                    Log.e("Bullet Data Remove", arHm.toString());
                                    break;
                                }
                            }}catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                });

                lBullets.addView(addView);
                HashMap<String , String> hm = new HashMap<String, String>();
                hm.put("text",edt_bullet.getText().toString());
                hm.put("tag", bulletCount + "");
                arHm.add(hm);
                Log.e("Bullet Data add", arHm.toString());
                edt_bullet.setText(" ");
                bulletCount ++;

            }
        });



        edt_product_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage(imgpath);

            }
        });
        /****/
    }

    private void showOptionDialog(View v) {


        switch (v.getId()) {

            case R.id.img_product:



                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();

                View view = inflater.inflate(R.layout.dialog_chooser_options, null);
                ImageView camera = (ImageView) view.findViewById(R.id.imgcamera);
                ImageView gallery = (ImageView) view.findViewById(R.id.imggallery);
                ImageView cross = (ImageView) view.findViewById(R.id.cross_img);

                cross.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        uploadOptionDialog.dismiss();
                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        img_Product.setRotation(0);

                        ActivityCompat.requestPermissions(SellerAddProductActivity.this,permissions, REQUEST_CODE);
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

                        uploadOptionDialog.dismiss();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {

                                              @Override
                                              public void onClick(View arg0) {

                                                  // img_Product.setRotation(img_Product.getRotation() + 90 +180 +270);


                                                  String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                                                  ActivityCompat.requestPermissions(SellerAddProductActivity.this, permissions, REQUEST_CODE); // without sdk version check
                                                  Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                  fileUri = getOutputMediaFileUri(1);
                                                  uri = fileUri.getPath();
                                                  intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                                  startActivityForResult(intentCamera, 100);
                                                  uploadOptionDialog.dismiss();



                                              }
                                          }
                );

                builder.setView(view);

                uploadOptionDialog = builder.create();
                uploadOptionDialog.show();

                /*if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    showFileUploadOptionDialog();
                } else {
                    Intent intent = new Intent(getActivity(), FileExplorerActivity.class);
                    intent.putExtra(Constants.EXTRA_FILE_TYPE, FileType.IMAGE);
                    startActivityForResult(intent, REQUEST_CODE_FILE_EXPLORER);
                }

                break;
        }*/
        }

    }

    public String getBulletData(ArrayList<HashMap<String,String>> bullets) {

        sb.append(edt_desc.getText().toString().trim());
        if(bullets.size()>0)
        { sb.append("#"); }

        for (int i = 0; i < bullets.size(); i++) {
            sb.append(bullets.get(i).get("text"));
            if (!(i == bullets.size() - 1)) {
                sb.append("#");
            }
        }

        return sb.toString();
    }


    private void uploadImage(String path) {
        {

            if (uri != null && uri != "") {
                // When Image is capturted from Camera
                sendFileUploadRequest(uri);
            } else if (imgpath != null && !imgpath.isEmpty()) {
                // When Image is selected from Gallery
                sendFileUploadRequest(path);
            } else {
                /*if (prodImageUrl.equals(""))
                    Toast.makeText(this, "NO IMAGE SELECTED TO UPLOAD", Toast.LENGTH_LONG).show();
                else {

                }*/
                //HttpAsyncTask_getProfile.execute();

            }
        }
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode,resultCode,data);



        try {
            // When an Image is capture
            if (requestCode == 100) {
                if (resultCode == this.RESULT_OK) {


                   img_Product.setRotation(90);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down sizing image as it throws OutOfMemory Exception for larger images
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(uri, options);

                    img_Product.setImageBitmap(bitmap);

                   /* Picasso
                            .with(context)
                            .load(R.drawable._3c_2_03_b)
                            .rotate(45f, 200f, 100f)
                            .into(img_Product);*/
                    //Picasso.with(context).load(String.valueOf(bitmap)).into(img_Product);
                    if (AppUtil.isNetworkAvailable(context)) {
                        //  uploadTask(path);
                        uploadImage(path);
                    } else {
                        AppUtil.showCustomToast(context, "Please check your internet");
                    }


                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                } else {
                    // failed to capture image
                    Toast.makeText(this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                }
                rotateBitmap(path);

               /* Bitmap scalebmp;
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize=6;
                final float scale=getResources().getDisplayMetrics().density;
                Display dis=getWindowManager().getDefaultDisplay();
                int width=dis.getWidth();
                scalebmp=Bitmap.createScaledBitmap(bitmapResizedImage,400,300, true);
                scalebmp.compress(Bitmap.CompressFormat.JPEG, 100, bao);

                byte [] ba = bao.toByteArray();
                String imgstr= Base64.encodeToString(ba,Base64.DEFAULT);
*/
            }

            // When an Image is picked from gallery
            else {
                if (resultCode == this.RESULT_OK) {



                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    if(AppUtil.isNetworkAvailable(context)){
                        uploadImage(imgpath);
                    }else{
                        AppUtil.showCustomToast(context, "Please check your internet");
                    }
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgpath = cursor.getString(columnIndex);
                    cursor.close();

                    // Set the Image in ImageView
                    final Bitmap bitmap = BitmapFactory.decodeFile(imgpath);
                    //Picasso.with(context).load(String.valueOf(bitmap)).into(img_Product);
                    img_Product.setImageBitmap(bitmap);
                    imgpath = imgpath;

                }

                //upload image
                //uploadAttachment();


                else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(this, "User cancelled image capture", Toast.LENGTH_SHORT).show();

                }

            }

        }catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }


    //.....SET IMAGE ORIENTATION.....//
    public static Bitmap rotateBitmap(String src) {
        Bitmap bitmap = BitmapFactory.decodeFile(src);
        try {
            ExifInterface exif = new ExifInterface(src);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                case ExifInterface.ORIENTATION_UNDEFINED:
                default:
                    return bitmap;
            }

            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //.....CONVERT IN BITMAP.....//
    private Bitmap decodeFileIntoBitmap(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 200;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }


    //.....MULTI PART ENTITY.....//
    private org.apache.http.entity.mime.MultipartEntity getMultipartEntity(String pRequestJson) {
        try {
            org.apache.http.entity.mime.MultipartEntity multipartEntity = new org.apache.http.entity.mime.MultipartEntity();

            multipartEntity.addPart("MyJson", new StringBody(pRequestJson));
            if (mAttachedFilePath != null && !mAttachedFilePath.equals("")) {
                int end = mAttachedFilePath.toString().lastIndexOf("/");
                String str1 = mAttachedFilePath.toString().substring(0, end);
                String str2 = mAttachedFilePath.toString().substring(end + 1, mAttachedFilePath.length());

                str1 = str1.replaceAll(" ", "%20");

                str2 = str2.replaceAll(" ", "%20");

                File file = new File(new URI("file://" + str1 + "/" + str2));

                bitmapResizedImage = decodeFileIntoBitmap(file);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapResizedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteData = byteArrayOutputStream.toByteArray();
                ByteArrayBody byteArrayBody = new ByteArrayBody(byteData, "image.png"); // second parameter is the name of the image (//TODO HOW DO I MAKE IT USE THE IMAGE FILENAME?)

                try {
                    multipartEntity.addPart("file1", byteArrayBody);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return multipartEntity;

        } catch (Exception e) {
            e.printStackTrace();


            return null;
        }
    }



    private void sendFileUploadRequest(final String filePath) {
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {


                uploadImageTask task = new uploadImageTask(filePath);
                task.execute(new String[]{filePath});


             /*   UploadImageHandler1 uploadImageHandler = new UploadImageHandler1(filePath);

                // uploadImageHandler.execute(Constants.uploadProfileImage, sendAuditRequest());
                uploadImageHandler.execute(Constants.Baseurl,getBaseRequestJson(Constants.REQUEST_SERVICE_PROFILE_PHOTO), "" );
*/
            }
        });
    }




    public Uri getOutputMediaFileUri(int type) {

        return Uri.fromFile(getOutputMediaFile(type));
    }
    private File getOutputMediaFile(int type) {


        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), "Android file upload");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    //.....ASYNC FOR UPLOAD IMAGE.....//
    public class uploadImageTask extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
            super.onPreExecute();
            ((RelativeLayout) findViewById(R.id.rl_progress)).setVisibility(View.VISIBLE);
            isUploading = true;
        }


        public uploadImageTask(String pAttachedFilePath) {
            mAttachedFilePath = pAttachedFilePath;
        }

        protected String doInBackground(String... params) {

            String response = "";

            try {

                JsonCall obj = new JsonCall();
                response = obj.storeImageUpload(params[0], getString(R.string.add_image_url));
                Log.e("Registration Response", "" + response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            isUploading = false;
            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jObject = jsonObject.getJSONObject("commandResult");

                    Integer status = jObject.getInt("success");
                    Log.e("Success", status.toString());

                    if (status == 1) {
                        String message = jObject.getString("message");
                        JSONObject j_data = jObject.getJSONObject("data");
                        String imageUrl = j_data.getString("imageUrl");

                        uri = "";
                        imgpath = "";

                        Log.e("ImageURL", imageUrl);
                        prodImageUrl = imageUrl;
                        Picasso.with(context)
                                .load(getResources().getString(R.string.image_base_url) + "" + imageUrl)
                                .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                                .into(img_Product);



                    } else {
                        String message = jObject.getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                    ((RelativeLayout) findViewById(R.id.rl_progress)).setVisibility(View.GONE);
                    if (dialog != null)
                        dialog.cancel();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            ((RelativeLayout) findViewById(R.id.rl_progress)).setVisibility(View.GONE);
            if (dialog != null)
                dialog.cancel();
        }
    }


    //.....ASYNC FOR ADD PRODUCTS.....//
    public class AddProduct extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.product(urls[0], urls[1], urls[2], urls[3],
                        urls[4],urls[5], getString(R.string.addproduct_url));
                Log.e("Response", "" + response);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        protected void onPostExecute(String result) {
            JSONObject jsonObject;

            try {
                if (result != null) {
                    jsonObject = new JSONObject(result);

                    JSONObject job = jsonObject.getJSONObject("commandResult");
                    String message = job.getString("message");
                    int status = job.getInt("success");
                    Log.e("status", "" + status);

                    if (status == 1) {

                        JSONObject jdata = job.getJSONObject("data");
                        JSONObject jprodetails = jdata.getJSONObject("addproductdetail");

                        String proName = jprodetails.getString("Name");
                        String proDesc = jprodetails.getString("Description");
                        String proPrice = jprodetails.getString("Price");
                        String proCatName = jprodetails.getString("product_cat_name");
                        String proId = jprodetails.getString("ID");

                        Intent in = new Intent();

                        in.putExtra("proName",proName);
                        in.putExtra("proDesc",proDesc);
                        in.putExtra("proPrice",proPrice);
                        in.putExtra("proCatName",proCatName);
                        in.putExtra("proID",proId);

                        setResult(100, in);
                        finish();

                    } else {
                        AppUtil.showCustomToast(context, message);
                    }

                } else {
                    AppUtil.showCustomToast(context,
                            "please check your internet connection");
                }

                if (dialog != null) {
                    dialog.cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (dialog != null) {
                    dialog.cancel();
                }
            }
        }
    }

}
