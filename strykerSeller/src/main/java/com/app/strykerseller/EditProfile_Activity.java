package com.app.strykerseller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.app.jsoncall.JsonCall;
import com.app.strykerseller.R;
import com.app.utills.AppUtil;
import com.app.utills.AppUtils;
import com.app.utills.GPSTracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eu.janmuller.android.simplecropimage.CropImage;

/**
 * Created by admin on 2/16/2017.
 */

public class EditProfile_Activity extends Activity {

    Context context;
    ProgressDialog dialog;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int REQUEST_CODE_CROP_IMAGE = 2;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 3;
    static int MEDIA_TYPE_IMAGE = 1;
    private static final String FIELD_TYPE = "ProfilePic";

    EditText editTextUpdate_BusinessName, editTextUpdate_BusinessAddress,  editTextUpdate_AboutUs, editTextUpdate_BusinessPhone, editTextUpdate_BusinessEmail, editTextUpdate_Distance,editTextUpdate_Business_MinOrder, editTextUpdate_Business_StandardDelTime;
    ImageView openTime, closeTime, deliveryTimeFrom, deliveryTimeTo;
    TextView tvDone, txtOpenTime, txtCloseTime, txtDeliveryFrom, txtDeliveryTo;
    static final int TIME_DIALOG_ID = 1111;
    private int hour, minute;
    String setTime;
    int clickedOn = 0;
    ToggleButton tbYes, tbNo, tb1, tb2, tb3, tb4, tb5, tb6, tb7;


    //StringBuilder sb = new StringBuilder();
    List<ToggleButton> tb = new ArrayList<ToggleButton>();
    String isMon = "0", isTue = "0", isWed = "0", isThur = "0",
            isFri = "0", isSat = "0", isSun = "0";
    String deliveryAvailable = "0";
    String path = "";

    Spinner spinIndusType;
    ArrayList<String> catId = new ArrayList<>();
    ArrayList<String> catName = new ArrayList<>();

    /**
     * gallery images
     */
    ImageView imgGallary_1, imgGallary_2, imgGallary_3, imgGallary_4, imgGallary_5, imgBack;
    ImageView imgDelete_1, imgDelete_2, imgDelete_3, imgDelete_4, imgDelete_5;
    RelativeLayout rl_image1, rl_image2, rl_image3, rl_image4, rl_image5;
    Integer imgFlag = 0;
    Integer delFlag = 0;

    ArrayList<String> galImagePath = new ArrayList<String>();
    ArrayList<String> galImagePathLocal = new ArrayList<String>();
    /**
     * File Upload
     **/
    private static final int FILE_SELECT_CODE = 0;

    String categoryId = "";
    String from = "";
    String logoPath = "", broucherPath = "";
    double mLat = 0.0d, mLong = 0.0d;

    File compressedPictureFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        context = this;
        dialog = new ProgressDialog(this, R.style.MyTheme_ProgressDialog);
        dialog.setCancelable(false);
        dialog.setMessage("please wait..");
        /**Industry Type Service**/
        catId.add("");
        catName.add("Choose Category");

        if (AppUtils.isNetworkAvailable(this)) {
            //dialog = ProgressDialog.show(context, "", "Please wait..");
            dialog.show();
            IndustryTypeTask task = new IndustryTypeTask();
            task.execute(new String[]{});

        } else {
            AppUtils.showCustomToast(context, "Please check your internet connection");
        }



        init();
        setListener();
    }

    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        AppUtil.onKeyBoardDown(context);
    }

    private void setListener() {

        /**Spinner Category**/

        spinIndusType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryId =  catId.get(position);

            }


            public void onNothingSelected(AdapterView<?> parent) {


            }
        });




        tvDone.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AppUtil.onKeyBoardDown(context);
                isMon = isToggleChecked(tb1);
                isTue = isToggleChecked(tb2);
                isWed = isToggleChecked(tb3);
                isThur = isToggleChecked(tb4);
                isFri = isToggleChecked(tb5);
                isSat = isToggleChecked(tb6);
                isSun = isToggleChecked(tb7);



                if (editTextUpdate_BusinessEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    AppUtils.showCustomToast(context, "Please enter your email id");
                } else if (!AppUtils.isEmailValid(editTextUpdate_BusinessEmail.getText().toString())) {
                    AppUtils.showCustomToast(context, "Please enter valid email id");
                }
                else if (editTextUpdate_BusinessName.getText().toString().equalsIgnoreCase("")) {
                    AppUtils.showCustomToast(context, "Please enter business name");
                }else if(editTextUpdate_BusinessName.getText().toString().length() < 4){
                    AppUtils.showCustomToast(context, "Business name should be of minimum 4 characters.");
                }
                else if (editTextUpdate_BusinessAddress.getText().toString().equalsIgnoreCase("")) {
                    AppUtils.showCustomToast(context, "Please enter business address");
                } else if (editTextUpdate_BusinessPhone.getText().toString().equalsIgnoreCase("")) {
                    AppUtils.showCustomToast(context, "Please enter business phone");
                }else if(categoryId.equalsIgnoreCase("")){
                    AppUtils.showCustomToast(context, "Please select industry type");
                }
                else {

                }


                ((ImageView) findViewById(R.id.img_store1)).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        imgFlag = 1;
                        selectImage();
                    }
                });

                ((ImageView) findViewById(R.id.img_store2)).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        imgFlag = 2;
                        selectImage();
                    }
                });

                ((ImageView) findViewById(R.id.img_store3)).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        imgFlag = 3;
                        selectImage();
                    }
                });

            /*    if (AppUtils.isNetworkAvailable(context)) {
                    //dialog = ProgressDialog.show(context, "", "Please wait..");
                    dialog.show();
                    AddStoreActivity.AddStoreTask task = new AddStoreActivity.AddStoreTask();

                    task.execute(new String[]{AppUtil.getUserId(context), storeId.getText().toString(), busName.getText().toString(),
                            busAddress.getText().toString(), busEmail.getText().toString(), busPhone.getText().toString(),
                            txtOpenTime.getText().toString(), txtCloseTime.getText().toString(), isMon, isTue, isWed, isThur,
                            isFri, isSat, isSun, deliveryAvailable, distance.getText().toString(),
                            selectedImagesGal(galImagePath), mLat+"", mLong+"", categoryId, minOrder.getText().toString(),logoPath,broucherPath,standardDelTime.getText().toString(),edtAboutUs.getText().toString()});

                } else {
                    AppUtils.showCustomToast(context, "Please check your internet connection");
                }*/

            }
        });

        ((TextView) findViewById(R.id.txt_upload)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                from = "broucher";
                showFileChooser();
            }
        });
        /****/


        tbYes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (tbYes.isChecked()) {
                    tbNo.setChecked(false);
                    deliveryAvailable = "1";
                    deliveryTimeFrom.setClickable(true);
                    deliveryTimeTo.setClickable(true);
                } else {
                    tbNo.setChecked(true);
                    deliveryAvailable = "0";
                    deliveryTimeFrom.setClickable(false);
                    deliveryTimeTo.setClickable(false);
                }
            }
        });


        tbNo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (tbNo.isChecked()) {
                    tbYes.setChecked(false);
                    deliveryAvailable = "0";
                    deliveryTimeFrom.setClickable(false);
                    deliveryTimeTo.setClickable(false);
                } else {
                    tbYes.setChecked(true);
                    deliveryAvailable = "1";
                    deliveryTimeFrom.setClickable(true);
                    deliveryTimeTo.setClickable(true);
                }
            }
        });

        ((TextView) findViewById(R.id.txt_upload_logo)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                from = "logo";
                showFileChooser();
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /********* display  time Listeners ********/

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        updateTime(hour, minute);


        openTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                clickedOn = 1;
                showDialog(TIME_DIALOG_ID);
            }
        });

        closeTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                clickedOn = 2;
                showDialog(TIME_DIALOG_ID);
            }
        });

        deliveryTimeFrom.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                clickedOn = 3;
                showDialog(TIME_DIALOG_ID);
            }
        });

        deliveryTimeTo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                clickedOn = 4;
                showDialog(TIME_DIALOG_ID);
            }
        });

        /********* End ********/

    }

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, hour, minute, false);
        }

        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            hour = hourOfDay;
            minute = minute;

            updateTime(hour, minute);
        }
    };

    private static String utilTime(int value) {
        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }


    public void init() {

        editTextUpdate_BusinessName = (EditText)findViewById(R.id.edt_business_name_update);
        editTextUpdate_AboutUs = (EditText) findViewById(R.id.edt_business_details_update);
        editTextUpdate_BusinessAddress = (EditText) findViewById(R.id.edt_business_add_update);
        editTextUpdate_BusinessPhone = (EditText) findViewById(R.id.edt_business_phone_update);
        editTextUpdate_BusinessEmail = (EditText) findViewById(R.id.edt_business_email_update);
        editTextUpdate_Distance = (EditText) findViewById(R.id.edt_distance_update);
        editTextUpdate_Business_StandardDelTime = (EditText) findViewById(R.id.edt_order_value_update);

        imgBack = (ImageView)findViewById(R.id.img_back);
        tvDone = (TextView) findViewById(R.id.txt_done);

        openTime = (ImageView) findViewById(R.id.img_start_time);
        closeTime = (ImageView) findViewById(R.id.img_end_time);
        deliveryTimeFrom = (ImageView) findViewById(R.id.img_delivery_start_time);
        deliveryTimeTo = (ImageView) findViewById(R.id.img_delivery_end_time);

        txtOpenTime = (TextView) findViewById(R.id.txt_start_time);
        txtCloseTime = (TextView) findViewById(R.id.txt_end_time);
        txtDeliveryFrom = (TextView) findViewById(R.id.txt_delivery_start_time);
        txtDeliveryTo = (TextView) findViewById(R.id.txt_delivery_end_time);


        tbYes = (ToggleButton) findViewById(R.id.tog_btn_yes);
        tbNo = (ToggleButton) findViewById(R.id.tog_btn_no);
        tb1 = (ToggleButton) findViewById(R.id.tog_btn_mon);
        tb2 = (ToggleButton) findViewById(R.id.tog_btn_tue);
        tb3 = (ToggleButton) findViewById(R.id.tog_btn_wed);
        tb4 = (ToggleButton) findViewById(R.id.tog_btn_thur);
        tb5 = (ToggleButton) findViewById(R.id.tog_btn_fri);
        tb6 = (ToggleButton) findViewById(R.id.tog_btn_sat);
        tb7 = (ToggleButton) findViewById(R.id.tog_btn_sun);

  /*      tb.add(tb1);
        tb.add(tb2);
        tb.add(tb3);
        tb.add(tb4);
        tb.add(tb5);
        tb.add(tb6);
        tb.add(tb7);*/

        imgGallary_1 = (ImageView) findViewById(R.id.img_store1);
        imgGallary_2 = (ImageView) findViewById(R.id.img_store2);
        imgGallary_3 = (ImageView) findViewById(R.id.img_store3);

//        imgGallary_4 = (ImageView) findViewById(R.id.img_store4);
//        imgGallary_5 = (ImageView) findViewById(R.id.img_store5);

        imgDelete_1 = (ImageView) findViewById(R.id.img_del_1);
        imgDelete_2 = (ImageView) findViewById(R.id.img_del_2);
        imgDelete_3 = (ImageView) findViewById(R.id.img_del_3);

        //      imgDelete_4 = (ImageView) findViewById(R.id.img_del_4);
        //      imgDelete_5 = (ImageView) findViewById(R.id.img_del_5);

        rl_image1 = (RelativeLayout) findViewById(R.id.rl_img_1);
        rl_image2 = (RelativeLayout) findViewById(R.id.rl_img_2);
        rl_image3 = (RelativeLayout) findViewById(R.id.rl_img_3);

        //       rl_image4 = (RelativeLayout) findViewById(R.id.rl_img_4);
        //       rl_image5 = (RelativeLayout) findViewById(R.id.rl_img_5);

        spinIndusType= (Spinner)findViewById(R.id.spin_cat);


        GPSTracker mGPS = new GPSTracker(this);
        if (mGPS.canGetLocation) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();
        }
    }

    public String isToggleChecked(ToggleButton tb) {
        if (tb.isChecked())
            return "1";
        else
            return "0";
    }


    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String setTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        if (clickedOn == 1) {
            txtOpenTime.setText(setTime);
        } else if (clickedOn == 2) {
            txtCloseTime.setText(setTime);
        } else if (clickedOn == 3) {
            txtDeliveryFrom.setText(setTime);
        } else if (clickedOn == 4) {
            txtDeliveryTo.setText(setTime);
        }

    }


    public void resetImages(ArrayList<String> imglist) {

        Log.e("ImageCount", "" + imglist.size());

        rl_image1.setVisibility(View.VISIBLE);
        rl_image2.setVisibility(View.GONE);
        rl_image3.setVisibility(View.GONE);

        //  rl_image4.setVisibility(View.GONE);
        //  rl_image5.setVisibility(View.GONE);


        switch (imglist.size()) {

            case 0: {

                rl_image1.setVisibility(View.VISIBLE);
                imgGallary_1.setClickable(true);
                imgDelete_1.setVisibility(View.GONE);
                imgGallary_1.setVisibility(View.VISIBLE);
                //imgGallary_1.setBackgroundResource(R.drawable.placeholder);
                //imgGallary_1.setImageResource(R.drawable.placeholder_s);

            }
            break;

            case 1: {

                Log.e("ImageCount", "" + imglist.size());

                rl_image1.setVisibility(View.VISIBLE);
                rl_image2.setVisibility(View.VISIBLE);
                rl_image3.setVisibility(View.GONE);
                //           rl_image4.setVisibility(View.GONE);
                //           rl_image5.setVisibility(View.GONE);


                imgGallary_1.setClickable(false);

                imgDelete_1.setVisibility(View.VISIBLE);
                imgDelete_1.setClickable(true);

                imgDelete_2.setVisibility(View.GONE);
                imgGallary_2.setClickable(true);

                imgGallary_1.setImageBitmap(BitmapFactory.decodeFile(galImagePathLocal.get(0)));
               /* Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url)+imglist.get(0))
                        .placeholder(R.drawable.placeholder)
                        .resize(350, 300)
                                //.transform(new CircleTransform())
                        .into(imgGallary_1);*/

                imgGallary_2.setImageResource(R.drawable.placeholder_s);


            }
            break;

            case 2: {
                rl_image1.setVisibility(View.VISIBLE);
                rl_image2.setVisibility(View.VISIBLE);
                rl_image3.setVisibility(View.VISIBLE);

                imgDelete_1.setVisibility(View.VISIBLE);
                imgDelete_2.setVisibility(View.VISIBLE);
                imgGallary_1.setClickable(false);
                imgGallary_2.setClickable(false);
                imgDelete_1.setClickable(true);
                imgDelete_2.setClickable(true);

                imgGallary_3.setClickable(true);
                imgDelete_3.setVisibility(View.GONE);


              /*  Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url)+imglist.get(0))
                        .placeholder(R.drawable.placeholder)
                         .resize(350, 300)
                                //.transform(new CircleTransform())
                        .into(imgGallary_1);*/
                imgGallary_1.setImageBitmap(BitmapFactory.decodeFile(galImagePathLocal.get(0)));

                /*Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(1))
                        .placeholder(R.drawable.placeholder)
                         .resize(350, 300)
                                //.transform(new CircleTransform())
                        .into(imgGallary_2);*/
                imgGallary_2.setImageBitmap(BitmapFactory.decodeFile(galImagePathLocal.get(1)));

                imgGallary_3.setImageResource(R.drawable.placeholder_s);
            }
            break;

            case 3: {
                rl_image1.setVisibility(View.VISIBLE);
                rl_image2.setVisibility(View.VISIBLE);
                rl_image3.setVisibility(View.VISIBLE);
                //            rl_image4.setVisibility(View.VISIBLE);


                imgDelete_1.setVisibility(View.VISIBLE);
                imgDelete_2.setVisibility(View.VISIBLE);
                imgDelete_3.setVisibility(View.VISIBLE);

                imgGallary_1.setClickable(false);
                imgGallary_2.setClickable(false);
                imgGallary_3.setClickable(false);

                imgDelete_1.setClickable(true);
                imgDelete_2.setClickable(true);
                imgDelete_3.setClickable(true);

                //               imgGallary_4.setClickable(true);
                //              imgDelete_4.setVisibility(View.GONE);

               /* Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(0))
                        .placeholder(R.drawable.placeholder)
                         .resize(350, 300)
                                //.transform(new CircleTransform())
                        .into(imgGallary_1);*/
                imgGallary_1.setImageBitmap(BitmapFactory.decodeFile(galImagePathLocal.get(0)));

               /* Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url)+imglist.get(1))
                        .placeholder(R.drawable.placeholder)
                         .resize(350, 300)
                                //.transform(new CircleTransform())
                        .into(imgGallary_2);*/

                imgGallary_2.setImageBitmap(BitmapFactory.decodeFile(galImagePathLocal.get(1)));

               /* Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(2))
                        .placeholder(R.drawable.placeholder)
                         .resize(350, 300)
                                //.transform(new CircleTransform())
                        .into(imgGallary_3);*/
                imgGallary_3.setImageBitmap(BitmapFactory.decodeFile(galImagePathLocal.get(2)));
                //           imgGallary_4.setImageResource(R.drawable.placeholder_s);



                ((ImageView) findViewById(R.id.img_del_1)).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        delFlag = 0;
                        if (AppUtil.isNetworkAvailable(context)) {
                            galImagePathLocal.remove(0);
                            deleteImageTask(galImagePath.get(0));
                        }else{
                            AppUtil.showCustomToast(context, "Please check your internet.");
                        }

                    }
                });
            }


            break;

          /*  case 4: {
                rl_image1.setVisibility(View.VISIBLE);
                rl_image2.setVisibility(View.VISIBLE);
                rl_image3.setVisibility(View.VISIBLE);
                rl_image4.setVisibility(View.VISIBLE);
                rl_image5.setVisibility(View.VISIBLE);


                imgDelete_1.setVisibility(View.VISIBLE);
                imgDelete_2.setVisibility(View.VISIBLE);
                imgDelete_3.setVisibility(View.VISIBLE);
                imgDelete_4.setVisibility(View.VISIBLE);

                imgGallary_1.setClickable(false);
                imgGallary_2.setClickable(false);
                imgGallary_3.setClickable(false);
                imgGallary_4.setClickable(false);
                imgDelete_1.setClickable(true);
                imgDelete_2.setClickable(true);
                imgDelete_3.setClickable(true);
                imgDelete_4.setClickable(true);

                imgGallary_5.setClickable(true);
                imgDelete_5.setVisibility(View.GONE);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url)+imglist.get(0))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_1);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(1))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_2);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(2))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_3);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(3))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_4);
                imgGallary_5.setImageResource(R.drawable.placeholder_s);

            }
            break;

            case 5: {
                rl_image1.setVisibility(View.VISIBLE);
                rl_image2.setVisibility(View.VISIBLE);
                rl_image3.setVisibility(View.VISIBLE);
                rl_image4.setVisibility(View.VISIBLE);
                rl_image5.setVisibility(View.VISIBLE);


                imgDelete_1.setVisibility(View.VISIBLE);
                imgDelete_2.setVisibility(View.VISIBLE);
                imgDelete_3.setVisibility(View.VISIBLE);
                imgDelete_4.setVisibility(View.VISIBLE);
                imgDelete_5.setVisibility(View.VISIBLE);

                imgGallary_1.setClickable(false);
                imgGallary_2.setClickable(false);
                imgGallary_3.setClickable(false);
                imgGallary_4.setClickable(false);
                imgGallary_5.setClickable(false);

                imgDelete_1.setClickable(true);
                imgDelete_2.setClickable(true);
                imgDelete_3.setClickable(true);
                imgDelete_4.setClickable(true);
                imgDelete_5.setClickable(true);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(0))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_1);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(1))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_2);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(2))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_3);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url)+imglist.get(3))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_4);

                Picasso.with(context)
                        .load(getResources().getString(R.string.image_base_url) + imglist.get(4))
                        .placeholder(R.drawable.placeholder)
                                //.transform(new CircleTransform())
                        .into(imgGallary_5);

            }
            break;*/
        }

    }



    public class IndustryTypeTask extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {

            String response = "";

            try {

                JsonCall obj = new JsonCall();
                response = obj.indusType(getResources().getString(R.string.indus_type_url));
                Log.e("Response", "" + response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null) {

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jObject = jsonObject.getJSONObject("commandResult");
                    Integer status = jObject.getInt("success");
                    String message = jObject.getString("message");
                    Log.e("Success", status.toString());

                    if (status == 1) {

                        JSONArray jaData = jObject.getJSONArray("data");
                        JSONObject joData;
                        for(int i=0;i<jaData.length();i++)
                        {
                            joData = jaData.getJSONObject(i);
                            catId.add(joData.getString("Id"));
                            catName.add(joData.getString("Name"));
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                                android.R.layout.simple_spinner_item, catName);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinIndusType.setAdapter(dataAdapter);
                        resetImages(galImagePath);

                    } else {
                        //  Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Null Response from service", Toast.LENGTH_SHORT).show();
                }
                if (dialog != null)
                    dialog.cancel();

            } catch (Exception e) {
                e.printStackTrace();
                if (dialog != null)
                    dialog.cancel();
            }

        }
    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                EditProfile_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    // create Intent to take a picture and return control to the
                    // calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    mImageCaptureUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    // create
                    // a
                    // file
                    // to
                    // save
                    // the
                    // image
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    // set
                    // the
                    // image
                    // file
                    // name
                    intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    // start the image capture Intent
                    startActivityForResult(intent, PICK_FROM_CAMERA);

                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(
                            Intent.createChooser(intent, "Select"),
                            PICK_FROM_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MarketApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MarketApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {

            Log.e("filepath", "*******" + filepath);
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognise a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }

        return degree;
    }

    public void deleteImageTask(String name) {
        /**save the uploaded images path */
        //dialog = ProgressDialog.show(context, "", "Please wait..");
        dialog.show();
        DeleteImageTask task = new DeleteImageTask();
        task.execute(new String[]{name});

    }

    public class DeleteImageTask extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {

            String response = "";

            try {

                JsonCall obj = new JsonCall();
                response = obj.deleteImage(params[0], getString(R.string.remove_image_url));
                Log.e("Response", "" + response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null) {

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jObject = jsonObject.getJSONObject("commandResult");

                    Integer status = jObject.getInt("success");
                    Log.e("Success", status.toString());

                    if (status == 1) {
                        String message = jObject.getString("message");
                        String data = jObject.getString("data");
                        Log.e("Result", data);

                        Log.e("delFlag", delFlag.toString());

                        galImagePath.remove(galImagePath.get(delFlag));

                        resetImages(galImagePath);

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    } else {
                        String message = jObject.getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(context, "Null Response from service", Toast.LENGTH_SHORT).show();
                }
                if (dialog != null)
                    dialog.cancel();

            } catch (Exception e) {
                e.printStackTrace();
                if (dialog != null)
                    dialog.cancel();
            }

        }
    }


    private Bitmap getExifImageBitmap(String selectedPath, Uri selectedImageUri) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeFile(selectedPath, options);
            // rotate bitmap
            Matrix matrix = new Matrix();
            matrix.postRotate(getExifOrientation(selectedPath));
            // create new rotated bitmap
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        /**camera code*/
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                path = mImageCaptureUri.getPath();
                //startCropImage(path);hh
                try {
                    compressedPictureFile = new File(path);
                    //Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap bm = ShrinkBitmap(path, 450, 600);
                    FileOutputStream fOut = new FileOutputStream(compressedPictureFile);
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                if(AppUtil.isNetworkAvailable(context)){
                    galImagePathLocal.add(path);
                    uploadTask(path);
                }else{
                    AppUtil.showCustomToast(context, "Please check your internet connection");
                }

                if (path == null) {
                    return;
                }

              /*  if (imgFlag == 1) {
                    ((ImageView) findViewById(R.id.img_store1)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 2) {
                    ((ImageView) findViewById(R.id.img_store2)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 3) {
                    ((ImageView) findViewById(R.id.img_store3)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 4) {
                    ((ImageView) findViewById(R.id.img_store4)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 5) {
                    ((ImageView) findViewById(R.id.img_store5)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                }*/
                break;

            case PICK_FROM_FILE:

                System.out.println("SELECT_AUDIO");
                Uri selectedImageUri1 = data.getData();
                path = getPath(selectedImageUri1);
                // startCropImage(path);
                String convPath = "";
                try {
                    // create a File object for the parent directory
                    File SentDirectory = new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                            "MarketAppSent");
                    // have the object build the directory structure, if needed.
                    if (!SentDirectory.exists()) {
                        SentDirectory.mkdirs();
                    }

                    String [] name = (path.toString()).split("/");
                    // create a File object for the output file
                    File outputFile = new File(SentDirectory, name[name.length-1]);
                    convPath = SentDirectory.getPath() + "/" + name[name.length-1];
                    // now attach the OutputStream to the file object, instead of a String representation
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    //compressedPictureFile = new File(path);
                    //Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap bm = ShrinkBitmap(path, 450, 600);
                    //FileOutputStream fOut = new FileOutputStream(compressedPictureFile);
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                if(AppUtil.isNetworkAvailable(context)){
                    galImagePathLocal.add(convPath);
                    uploadTask(convPath);
                }else{
                    AppUtil.showCustomToast(context, "Please check your internet connection");
                }


                if (path == null) {
                    return;
                }

              /*  if (imgFlag == 1) {
                    ((ImageView) findViewById(R.id.img_store1)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 2) {
                    ((ImageView) findViewById(R.id.img_store2)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 3) {
                    ((ImageView) findViewById(R.id.img_store3)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 4) {
                    ((ImageView) findViewById(R.id.img_store4)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 5) {
                    ((ImageView) findViewById(R.id.img_store5)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                }*/
                break;


            case REQUEST_CODE_CROP_IMAGE:

                path = data.getStringExtra(CropImage.IMAGE_PATH);
                uploadTask(path);

                if (path == null) {
                    return;
                }

               /* if (imgFlag == 1) {
                    ((ImageView) findViewById(R.id.img_store1)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 2) {
                    ((ImageView) findViewById(R.id.img_store2)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 3) {
                    ((ImageView) findViewById(R.id.img_store3)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 4) {
                    ((ImageView) findViewById(R.id.img_store4)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                } else if (imgFlag == 5) {
                    ((ImageView) findViewById(R.id.img_store5)).setImageBitmap(getExifImageBitmap(path,
                            mImageCaptureUri));
                }*/
                break;
            /**camera code*/

            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.e("Path", "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    try {
                        path = AppUtil.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.e("Path", "File Path: " + path);
                    uploadFile(path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
    }
    Bitmap ShrinkBitmap(String file, int width, int height){

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
        int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);

        if (heightRatio > 1 || widthRatio > 1)
        {
            if (heightRatio > widthRatio)
            {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void uploadTask(String path) {
        /**save the uploaded images path */
        // dialog = ProgressDialog.show(context, "", "Please wait..");
        dialog.show();
        uploadImageTask task = new uploadImageTask();
        task.execute(new String[]{path});

    }
    public class uploadImageTask extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
            super.onPreExecute();
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

                        Log.e("ImageURL", imageUrl);
                        galImagePath.add(imageUrl);
                        resetImages(galImagePath);


                    } else {
                        String message = jObject.getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                    if (dialog != null)
                        dialog.cancel();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (dialog != null)
                dialog.cancel();
        }
    }

    public void uploadFile(String path) {
        /**save the uploaded images path */
        //dialog = ProgressDialog.show(context, "", "Please wait..");
        dialog.show();
        uploadFileTask task = new uploadFileTask(from);
        task.execute(new String[]{path});


    }

    public class uploadFileTask extends AsyncTask<String, Void, String> {
        String from1 ="";
        public uploadFileTask(String from) {

            this.from1 = from;
        }


        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected String doInBackground(String... params) {

            String response = "";

            try {

                JsonCall obj = new JsonCall();
                response = obj.storeFileUpload(params[0], getString(R.string.add_image_url));
                Log.e("Response", "" + response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jObject = jsonObject.getJSONObject("commandResult");

                    Integer status = jObject.getInt("success");
                    Log.e("Success", status.toString());

                    if (status == 1) {

                        String message = jObject.getString("message");
                        JSONObject j_data = jObject.getJSONObject("data");
                        String fileUrl = j_data.getString("imageUrl");
                        if(from1.equalsIgnoreCase("logo"))
                        {
                            logoPath = fileUrl;
                            ((TextView) findViewById(R.id.txt_upload_status1))
                                    .setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.txt_upload_status1))
                                    .setText("Logo uploaded");
                        }else{
                            broucherPath = fileUrl;
                            ((TextView) findViewById(R.id.txt_upload_status2))
                                    .setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.txt_upload_status2))
                                    .setText("Brochure uploaded");
                        }
                        Log.e("ImageURL", fileUrl);

                        Toast.makeText(context, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        String message = jObject.getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        if(from1.equalsIgnoreCase("logo")){
                            ((TextView) findViewById(R.id.txt_upload_status1))
                                    .setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.txt_upload_status1))
                                    .setText("Retry");
                        }else{
                            ((TextView) findViewById(R.id.txt_upload_status2))
                                    .setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.txt_upload_status2))
                                    .setText("Retry");
                        }
                    }


                    if (dialog != null)
                        dialog.cancel();
                }else{
                    if(from1.equalsIgnoreCase("logo")){
                        ((TextView) findViewById(R.id.txt_upload_status1))
                                .setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.txt_upload_status1))
                                .setText("Retry");
                    }else{
                        ((TextView) findViewById(R.id.txt_upload_status2))
                                .setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.txt_upload_status2))
                                .setText("Retry");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                if(from1.equalsIgnoreCase("logo")){
                    ((TextView) findViewById(R.id.txt_upload_status1))
                            .setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.txt_upload_status1))
                            .setText("Retry");
                }else{
                    ((TextView) findViewById(R.id.txt_upload_status2))
                            .setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.txt_upload_status2))
                            .setText("Retry");
                }
            }

            if (dialog != null)
                dialog.cancel();
        }
    }

}
