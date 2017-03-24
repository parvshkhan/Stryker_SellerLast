package com.app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.model.OrderHistoryModel;
import com.app.model.PendingModel;
import com.app.model.StoreHomeListModel;

import java.sql.Blob;
import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 83;
    public static final String DATABASE_NAME = "SQLiteSewsftds.db";


    public static final String TABLE_NAME_HOME = "StoreHome";

    public static final String COLUMN_STORE_ID = "storeid";                             //Table Name HOME
    public static final String COLUMN_STORE_IMAGE_URL = "imgurl";
    public static final String COLUMN_STORE_NAME = "storename";


    public static final String TABLE_NAME_REPORT_DATE = "reportdate";
    //Table Name REPORT DATE
    public static final String COLUMN_ORDER_DATE = "OrderDate";
    public static final String COLUMN_TOTAL_AMOUNT = "TotalAmount";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_DATE_ID = "id";


    public static final String TABLE_NAME_PRODUCTS = "productList";                   //Table Name products
    public static final String COLUMN_PRODUCT_ID = "ProID";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "ProDescription";
    public static final String COLUMN_PRODUCT_PRICE  = "ProPrice";
    public static final String COLUMN_PRODUCT_IS_HOT = "ProHot";
    public static final String COLUMN_PRODUCT_NAME = "ProName";
    public static final Boolean COLUMN_PRODUCT_HEADER = true;
    public static final String COLUMN_PRODUCT_IMAGE  = "ProImageUrl";



    public static final String TABLE_NAME_HOT_PRODUCTS = "hotProducts";                   //Table Name HOT products
    public static final String COLUMN_PRODUCT_HOT_ID = "ProID";
    public static final String COLUMN_PRODUCT_HOT_DESCRIPTION = "ProDescription";
    public static final String COLUMN_PRODUCT_HOT_PRICE  = "ProPrice";
    public static final String COLUMN_PRODUCT_HOT = "ProHot";
    public static final String COLUMN_PRODUCT_HOT_NAME = "ProName";
    public static final Boolean COLUMN_PRODUCT_HOT_HEADER = true;
    public static final String COLUMN_PRODUCT_HOT_IMAGE  = "ProImageUrl";



    public static final String TABLE_NAME_PRODUCT_CATEGORY = "poductsCategory";                   //Table Name HOT products
    public static final String COLUMN_PRODUCT_CAT_ID = "ProID";
    public static final String COLUMN_PRODUCT_CAT_DESCRIPTION = "ProDescription";
    public static final String COLUMN_PRODUCT_CAT_NAME  = "ProPrice";



    private SQLiteDatabase database;

    private static SQLiteHelper sqLiteHelper = null;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

    public static SQLiteHelper getInstance(Context context) {
        if (sqLiteHelper == null)
            return new SQLiteHelper(context);
        else
            return sqLiteHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_HOME + " ( " + COLUMN_STORE_ID + " VARCHAR primary key , " + COLUMN_STORE_IMAGE_URL + " VARCHAR, " + COLUMN_STORE_NAME + " VARCHAR);");
        db.execSQL("create table " + TABLE_NAME_REPORT_DATE + " ( " + COLUMN_DATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ORDER_DATE + " VARCHAR, " + COLUMN_TOTAL_AMOUNT + " VARCHAR," + COLUMN_QUANTITY + " VARCHAR);");
       // db.execSQL("create table " + TABLE_NAME_PRODUCTS + " ( " + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PRODUCT_DESCRIPTION + " VARCHAR, " + COLUMN_PRODUCT_PRICE + " VARCHAR," + COLUMN_PRODUCT_IS_HOT + " VARCHAR,"+ COLUMN_PRODUCT_NAME + " VARCHAR,"+ COLUMN_PRODUCT_IMAGE + " VARCHAR, "+ COLUMN_PRODUCT_HEADER + " Blob);");
     //   db.execSQL("create table " + TABLE_NAME_HOT_PRODUCTS + " ( " + COLUMN_PRODUCT_HOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PRODUCT_HOT_DESCRIPTION + " VARCHAR, " + COLUMN_PRODUCT_HOT_PRICE + " VARCHAR," + COLUMN_PRODUCT_HOT + " VARCHAR,"+ COLUMN_PRODUCT_HOT_NAME + " VARCHAR,"+ COLUMN_PRODUCT_HOT_IMAGE + " VARCHAR, "+ COLUMN_PRODUCT_HOT_HEADER + " Blob);");

        Log.i("Table Created", "");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_REPORT_DATE);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ASSOCIATE);
        onCreate(db);
    }


    //.....HOME DATA.....//
    public void insertStoreHome(StoreHomeListModel storeHomeListModel) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STORE_ID, storeHomeListModel.getSrore_id());
        contentValues.put(COLUMN_STORE_IMAGE_URL, storeHomeListModel.getStore_image());
        contentValues.put(COLUMN_STORE_NAME, storeHomeListModel.getStore_name());
        long res = database.insert(TABLE_NAME_HOME, null, contentValues);
        Log.i("res", res + "");
        database.close();
    }


      /*  //.....PRODUCTS DATA.....//
    public void insertStoreProducts(PendingModel storeProducts) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_ID, storeProducts.getProductId());
        contentValues.put(COLUMN_PRODUCT_IS_HOT, storeProducts.getIsHot());
        contentValues.put(COLUMN_PRODUCT_DESCRIPTION, storeProducts.getProDescription());
        contentValues.put(COLUMN_PRODUCT_PRICE, storeProducts.getTotalCost());
        contentValues.put(COLUMN_PRODUCT_IMAGE, storeProducts.getProductImageUrl());
        contentValues.put(COLUMN_PRODUCT_NAME, storeProducts.getProName());
        contentValues.put(String.valueOf(COLUMN_PRODUCT_HEADER), storeProducts.getIsShowHeader());
        //  (helperdatabase.ALLOW_GO,booleanvalues[0]);


        long res = database.insert(TABLE_NAME_PRODUCTS, null, contentValues);
        Log.i("res", res + "");
        database.close();
    }
*/
    //.....REPORTS DATA.....//
    public long inserReportDate(OrderHistoryModel orderHistoryModel) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ORDER_DATE, orderHistoryModel.getOrderDate());
        contentValues.put(COLUMN_TOTAL_AMOUNT, orderHistoryModel.getTotalPrice());
        contentValues.put(COLUMN_QUANTITY, orderHistoryModel.getTotalUnit());
        long res = database.insert(TABLE_NAME_REPORT_DATE, null, contentValues);

        Log.i("res", res + "");
        database.close();
        return res;
    }


    public void updateRecord(OrderHistoryModel orderHistoryModel) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ORDER_DATE, orderHistoryModel.getOrderDate());
        contentValues.put(COLUMN_QUANTITY, orderHistoryModel.getTotalUnit());
        contentValues.put(COLUMN_TOTAL_AMOUNT, orderHistoryModel.getTotalPrice());
        database.update(TABLE_NAME_REPORT_DATE, contentValues, COLUMN_DATE_ID + " = ?", new String[]{orderHistoryModel.getOrderId()});
        database.close();
    }


    public void deleteCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_REPORT_DATE); //delete all rows in a table
        db.close();
    }



    //.....PRODUCTS HOT DATA.....//
    public void insertStoreHotProducts(PendingModel storeProducts) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_HOT_ID, storeProducts.getProductId());
        contentValues.put(COLUMN_PRODUCT_HOT_DESCRIPTION, storeProducts.getProDescription());
        contentValues.put(COLUMN_PRODUCT_HOT_PRICE, storeProducts.getTotalCost());
        contentValues.put(COLUMN_PRODUCT_HOT, storeProducts.getIsHot());
        contentValues.put(COLUMN_PRODUCT_IMAGE, storeProducts.getProductImageUrl());
        contentValues.put(COLUMN_PRODUCT_HOT_NAME, storeProducts.getProName());
        contentValues.put(String.valueOf(COLUMN_PRODUCT_HOT_HEADER), storeProducts.getIsShowHeader());
        //  (helperdatabase.ALLOW_GO,booleanvalues[0]);


        long res = database.insert(TABLE_NAME_HOT_PRODUCTS, null, contentValues);
        Log.i("res", res + "");
        database.close();
    }

/*
    public ArrayList<StoreHomeListModel> getAllRecordsOriginal() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<StoreHomeListModel> contacts = new ArrayList<>();
        StoreHomeListModel storeHomeListModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                storeHomeListModel = new StoreHomeListModel();
                storeHomeListModel.setSrore_id(cursor.getString(0));
                storeHomeListModel.setStore_name(cursor.getString(1));
                storeHomeListModel.setStore_image(cursor.getString(2));
                contacts.add(storeHomeListModel);
            }
        }
        cursor.close();
        database.close();
        return contacts;
    }
*/

    public ArrayList<OrderHistoryModel> getAllRecords() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME_REPORT_DATE, null, null, null, null, null, null);
        ArrayList<OrderHistoryModel> contacts = new ArrayList<OrderHistoryModel>();
        OrderHistoryModel contactModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                contactModel = new OrderHistoryModel();
                contactModel.setOrderDate(cursor.getString(1));
                contactModel.setTotalPrice(cursor.getString(2));
                contactModel.setTotalUnit(cursor.getString(3));
                contacts.add(contactModel);
            }
        }
        cursor.close();
        database.close();
        return contacts;
    }


    public ArrayList<StoreHomeListModel> getStoreHome() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME_HOME, null, null, null, null, null, null);
        ArrayList<StoreHomeListModel> storeHomeListModels = new ArrayList<>();
        StoreHomeListModel storeListModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                storeListModel = new StoreHomeListModel();
                storeListModel.setSrore_id(cursor.getString(0));
                storeListModel.setStore_image(cursor.getString(1));
                storeListModel.setStore_name(cursor.getString(2));
                storeHomeListModels.add(storeListModel);
            }
        }
        cursor.close();
        database.close();
        return storeHomeListModels;
    }



/*
    public ArrayList<PendingModel> getProducts() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME_PRODUCTS, null, null, null, null, null, null, null);
        ArrayList<PendingModel> productListModels = new ArrayList<>();
        PendingModel productModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                productModel = new PendingModel();
                productModel.setProductId(cursor.getString(0));
                productModel.setProDescription(cursor.getString(1));
                productModel.setTotalCost(cursor.getString(2));
                productModel.setProductImageUrl(cursor.getString(3));
                productModel.setProName(cursor.getString(4));
                productModel.setIsShowHeader(Boolean.valueOf(cursor.getString(5)));
                productModel.setIsHot(cursor.getString(6));

                productListModels.add(productModel);
            }
        }
        cursor.close();
        database.close();
        return productListModels;
    }
*/


    public ArrayList<PendingModel> getHotProducts() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME_HOT_PRODUCTS, null, null, null, null, null, null, null);
        ArrayList<PendingModel> productListModels = new ArrayList<>();
        PendingModel productModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                productModel = new PendingModel();
                productModel.setProductId(cursor.getString(0));
                productModel.setProDescription(cursor.getString(1));
                productModel.setTotalCost(cursor.getString(2));
                productModel.setProductImageUrl(cursor.getString(3));
                productModel.setProName(cursor.getString(4));
                productModel.setIsShowHeader(Boolean.valueOf(cursor.getString(5)));
                productModel.setIsHot(cursor.getString(6));

                productListModels.add(productModel);
            }
        }
        cursor.close();
        database.close();
        return productListModels;
    }



    public void deleteTableAllReport() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_REPORT_DATE); //delete all rows in a table
        db.close();
    }
    public long size()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long numRows = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM "+TABLE_NAME_REPORT_DATE, null);
        db.close();
        return numRows;
    }


    public void clearDB()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_HOME); //delete all rows in a table
        db.execSQL("DELETE FROM " + TABLE_NAME_REPORT_DATE); //delete all rows in a table
       // db.execSQL("DELETE FROM " + TABLE_NAME_REPORT_DATE); //delete all rows in a table
        db.close();

    }


}