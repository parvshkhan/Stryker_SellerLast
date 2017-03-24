package com.app.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.ProgressDialog;
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
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.Database.SQLiteHelper;
import com.app.adapter.Hot_ProductList_Adapter;
import com.app.adapter.ProductList_Adapter;
import com.app.jsoncall.JsonCall;
import com.app.listener.MarkHotProductListener;
import com.app.model.PendingModel;
import com.app.model.StoreHomeListModel;
import com.app.strykerseller.ProductListActivity;
import com.app.strykerseller.R;
import com.app.strykerseller.SellerAddProductActivity;
import com.app.strykerseller.SellerAddProductActivity.uploadImageTask;
import com.app.strykerseller.UpdateProductDetailsActivity;
import com.app.utills.AppUtil;
import com.app.utills.AppUtils;
import com.app.utills.GPSTracker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Inflectica on 10/23/2015.
 */
public class ProductListFragment extends android.support.v4.app.Fragment
		implements MarkHotProductListener {

	Context context;
	private Double mLat = 0.0, mLong = 0.0;
	ProgressDialog dialog;
	ArrayList<PendingModel> arlistProduct = new ArrayList<>();
	ArrayList<PendingModel> arlistHotProduct = new ArrayList<>();

	ListView listProducts, listHotProduct;
	ImageView imgAdd, imgPublish, imgUnpublish;
	TextView txtProductList, txtHotProduct, txtPublish, txtUnpublish;
	RelativeLayout rl_ProductList, rl_HotProduct;
	ProductList_Adapter adapter;
	Hot_ProductList_Adapter hotadapter;
	int flag;
	String storeId = "";
	JSONArray jaHotProducts, jaProduct, proCategory;
	String isPublish = "0";

	int clickedPosition;

	@Nullable

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_product_list_1,
				container, false);

		return rootview;
	}


	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		context = getActivity();
		init();

		ProductListActivity prodActivity = (ProductListActivity) getActivity();
		storeId = prodActivity.StoreId();

		Log.e("Store_Id in Fragment", storeId);

		GPSTracker mGPS = new GPSTracker(context);
		if (mGPS.canGetLocation) {
			mLat = mGPS.getLatitude();
			mLong = mGPS.getLongitude();
		}

/*
        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(getActivity());
        arlistProduct = sqLiteHelper.getProducts();
        sqLiteHelper.close();*/
		if (AppUtils.isNetworkAvailable(context)) {

		/*	if (arlistProduct.isEmpty()) {


                dialog = ProgressDialog.show(context, "", "Please wait....");
                ProductList pro = new ProductList();
                pro.execute(new String[] { storeId });

			}else {

                try {
                    setData(jaHotProducts, jaProduct);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
*/


			dialog = ProgressDialog.show(context, "", "Please wait....");
			ProductList pro = new ProductList();
			pro.execute(new String[] { storeId });
		} else {
			AppUtils.showCustomToast(context,
					"Please check your internet connection");
		}
	}


	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		context = getActivity();

		imgAdd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Intent in = new Intent(getActivity(),
						SellerAddProductActivity.class);
				in.putExtra("store_id", storeId);
				in.putExtra("product_category", proCategory.toString());
				getActivity().startActivityForResult(in, 10);
			}
		});



	}

	public void init() {

		listProducts = (ListView) getView().findViewById(R.id.lv_products);
		listHotProduct = (ListView) getView()
				.findViewById(R.id.lv_hot_products);
		imgAdd = (ImageView) getView().findViewById(R.id.img_add);
		imgPublish = (ImageView) getView().findViewById(R.id.img_publish);
		imgUnpublish = (ImageView) getView().findViewById(R.id.img_unpublish);

		rl_ProductList = (RelativeLayout) getView().findViewById(
				R.id.rl_product_list);
		rl_HotProduct = (RelativeLayout) getView().findViewById(
				R.id.rl_hot_product);
		txtProductList = (TextView) getView()
				.findViewById(R.id.txt_productlist);
		txtHotProduct = (TextView) getView().findViewById(R.id.txt_hotproduct);
		txtPublish = (TextView) getView().findViewById(R.id.txt_publish);
		txtUnpublish = (TextView) getView().findViewById(R.id.txt_unpublish);



		listProducts.setOnItemClickListener(new OnItemClickListener() {


			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				clickedPosition = position;
				Intent i = new Intent(context,UpdateProductDetailsActivity.class);
				i.putExtra("store_id", storeId);
				i.putExtra("product_category", proCategory.toString());
				i.putExtra("proId",arlistProduct.get(position).getProductId());
				startActivity(i);

			}
		});

		listHotProduct.setOnItemClickListener(new OnItemClickListener() {


			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				clickedPosition = position;
				Intent i = new Intent(context,UpdateProductDetailsActivity.class);
				i.putExtra("id", arlistHotProduct.get(position).getProductId());
				i.putExtra("name", arlistHotProduct.get(position).getProName());
				i.putExtra("price", arlistHotProduct.get(position).getTotalCost());
				i.putExtra("image", arlistHotProduct.get(position).getProductImageUrl());
				startActivity(i);

			}
		});

		rl_ProductList.setOnClickListener(new View.OnClickListener() {


			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag = 0;
				switchTab(0);
			}
		});

		rl_HotProduct.setOnClickListener(new View.OnClickListener() {


			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag = 1;
				switchTab(1);
			}
		});

		imgPublish.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				isPublish = "1";
				imgAdd.setVisibility(View.INVISIBLE);
				imgPublish.setVisibility(View.VISIBLE);
				txtPublish.setVisibility(View.VISIBLE);

				// if(jaHotProducts.length()>0)
				if (arlistHotProduct.size() > 0) {
					imgPublish.setVisibility(View.INVISIBLE);
					txtPublish.setVisibility(View.INVISIBLE);
					imgUnpublish.setVisibility(View.VISIBLE);
					txtUnpublish.setVisibility(View.VISIBLE);

					if (AppUtils.isNetworkAvailable(context)) {

						dialog = ProgressDialog.show(context, "",
								"Please wait....");
						PublishTask task = new PublishTask();
						task.execute(new String[] { storeId, isPublish });
					} else {
						AppUtils.showCustomToast(context,
								"Please check your internet connection");
					}
				} else {
					AppUtils.showCustomToast(context,
							"Please add products to publish.");
				}

			}
		});

		imgUnpublish.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				isPublish = "0";

				imgAdd.setVisibility(View.INVISIBLE);
				imgPublish.setVisibility(View.VISIBLE);
				txtPublish.setVisibility(View.VISIBLE);
				imgUnpublish.setVisibility(View.INVISIBLE);
				txtUnpublish.setVisibility(View.INVISIBLE);

				if (AppUtils.isNetworkAvailable(context)) {

					dialog = ProgressDialog
							.show(context, "", "Please wait....");
					PublishTask task = new PublishTask();

					task.execute(new String[] { storeId, isPublish });
				} else {
					AppUtils.showCustomToast(context,
							"Please check your internet connection");
				}
			}
		});

	}



	public void switchTab(int tabPosition) {
		if (tabPosition == 0) {
			Log.e("TabPosition", "" + tabPosition);
			txtProductList.setTextColor(getResources().getColor(
					R.color.Text_color_black));
			txtHotProduct.setTextColor(getResources().getColor(
					R.color.Text_color_black));
			txtProductList.setTextColor(getResources()
					.getColor(R.color.orange1));
			imgAdd.setVisibility(View.VISIBLE);
			imgPublish.setVisibility(View.INVISIBLE);
			imgUnpublish.setVisibility(View.INVISIBLE);
			txtPublish.setVisibility(View.INVISIBLE);
			txtUnpublish.setVisibility(View.INVISIBLE);
			listProducts.setVisibility(View.VISIBLE);
			listHotProduct.setVisibility(View.GONE);
		} else {
			txtHotProduct
					.setTextColor(getResources().getColor(R.color.orange1));
			imgAdd.setVisibility(View.INVISIBLE);
			listProducts.setVisibility(View.GONE);
			listHotProduct.setVisibility(View.VISIBLE);
			if (isPublish.equalsIgnoreCase("0")) {
				imgPublish.setVisibility(View.VISIBLE);
				imgUnpublish.setVisibility(View.INVISIBLE);
				txtPublish.setVisibility(View.VISIBLE);
				txtUnpublish.setVisibility(View.INVISIBLE);
			} else {
				imgPublish.setVisibility(View.INVISIBLE);
				imgUnpublish.setVisibility(View.VISIBLE);
				txtPublish.setVisibility(View.INVISIBLE);
				txtUnpublish.setVisibility(View.VISIBLE);
			}
		}
	}

	public void setData(JSONArray hotProduct, JSONArray product)
			throws JSONException {

		Log.e("HotProducts", "" + jaHotProducts);
		Log.e("Products", "" + jaProduct);
		// if (tabPosition == 0) {
		arlistProduct.clear();

		try {
			for (int i = 0; i < product.length(); i++) {

				JSONObject joP = product.getJSONObject(i);
				String proCategoryName = joP.getString("name");


				JSONArray jaItem = joP.getJSONArray("items");
				JSONObject joItem;
				for (int j = 0; j < jaItem.length(); j++) {
					joItem = jaItem.getJSONObject(j);

					String proName = joItem.getString("ProName");
					String proDesc = joItem.getString("ProDescription");
					String proPrice = joItem.getString("ProPrice");
					String productId = joItem.getString("ProID");
					String isHot = joItem.getString("ProHot");
					String prodImageUrl = joItem.getString("ProImageUrl");


/*

					SQLiteHelper sQLiteHelper = new SQLiteHelper(getActivity());
					sQLiteHelper.insertStoreProducts(new PendingModel
							(productId, prodImageUrl, proPrice, isHot, proDesc, proName, false));
*/

					if (j == 0) {
						arlistProduct.add(new PendingModel(proCategoryName,
								productId, proName, proDesc, proPrice, isHot,
								true, prodImageUrl));
					} else {
						arlistProduct.add(new PendingModel(proCategoryName,
								productId, proName, proDesc, proPrice, isHot,
								false, prodImageUrl));
					}
				}
			}
			adapter = new ProductList_Adapter(context,
					R.layout.seller_row_product, arlistProduct, this);
			listProducts.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		// } else if (tabPosition == 1) {

		String isHot = "1";

		arlistHotProduct.clear();

		try {
			for (int i = 0; i < hotProduct.length(); i++) {

				JSONObject joHp = hotProduct.getJSONObject(i);

				String hotId = joHp.getString("Id");
				String hotName = joHp.getString("Name");
				String hotDesc = joHp.getString("Description");
				String hotPrice = joHp.getString("Price");
				JSONObject hotCat = joHp.getJSONObject("ProductCategory");
				String hotCategoryName = hotCat.getString("Name");
				String prodImageUrl = joHp.getString("ImageUrl");
				if (i == 0) {
					arlistHotProduct.add(new PendingModel(hotCategoryName,
							hotId, hotName, hotDesc, hotPrice, isHot, true,
							prodImageUrl));
				} else {
					arlistHotProduct.add(new PendingModel(hotCategoryName,
							hotId, hotName, hotDesc, hotPrice, isHot, false,
							prodImageUrl));
				}
			}
			hotadapter = new Hot_ProductList_Adapter(context,
					R.layout.seller_row_hot_product, arlistHotProduct);
			listHotProduct.setAdapter(hotadapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		// }
	}


	public void setMarkHotProduct(int position, String isHot) {
		/** Update Hot Product List **/
		if (isHot.equalsIgnoreCase("1")) {
			arlistHotProduct.add(arlistProduct.get(position));
		} else {
			for (int i = 0; i < arlistHotProduct.size(); i++) {
				if (arlistProduct
						.get(position)
						.getProductId()
						.equalsIgnoreCase(
								arlistHotProduct.get(i).getProductId())) {
					arlistHotProduct.remove(i);
					break;
				}
			}
		}
		hotadapter.notifyDataSetChanged();

		arlistProduct.get(position).setIsHot(isHot);
		adapter.notifyDataSetChanged();

	}

	private class PublishTask extends AsyncTask<String, Void, String> {


		protected void onPreExecute() {
			super.onPreExecute();
		}


		protected String doInBackground(String... url) {
			String response = "";
			try {
				JsonCall obj = new JsonCall();
				response = obj.publishProduct(url[0], url[1],
						getString(R.string.publish_url));
				Log.e("PublishProduct Response", "" + response);
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
						JSONObject input = job.getJSONObject("input");
						if (input.getString("ispublish").equalsIgnoreCase("1")) {
							isPublish = "0";
						} else {
							isPublish = "1";
						}

						AppUtil.showCustomToast(context, message);

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

	private class ProductList extends AsyncTask<String, Void, String> {


		protected void onPreExecute() {
			super.onPreExecute();
		}


		protected String doInBackground(String... url) {
			String response = "";
			try {
				JsonCall jsonClass = new JsonCall();
				response = jsonClass.getProductlist(url[0],
						getString(R.string.productlist_url));
				Log.e("Productlist Response", "***" + response);
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

						JSONObject data_object = job.getJSONObject("data");

						JSONObject data_store = data_object
								.getJSONObject("store");
						jaHotProducts = data_object.getJSONArray("hotproducts");
						JSONObject data_products = data_object
								.getJSONObject("products");
						jaProduct = data_products.getJSONArray("category");
						proCategory = data_object
								.getJSONArray("productCategory");
						isPublish = data_store
								.getString("IsHotProductsPublished");

						setData(jaHotProducts, jaProduct);
						switchTab(0);

						if (dialog != null) {
							dialog.cancel();
						}

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






    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        getActivity();
		if (requestCode == 10 && resultCode == 100) {
			String isHot = "0";
			Bundle b = data.getExtras();

			arlistProduct.add(new PendingModel(b.getString("proCatName"), b
					.getString("proID"), b.getString("proName"), b
					.getString("proDesc"), b.getString("proPrice"), isHot,
					false, ""));
			listProducts.setAdapter(adapter);
		}

    }





}
