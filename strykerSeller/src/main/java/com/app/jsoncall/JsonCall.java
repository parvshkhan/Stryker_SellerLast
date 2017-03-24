package com.app.jsoncall;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.preference.PreferenceActivity.Header;
import android.util.Log;

public class JsonCall {
    String res = "", reslatlon ="";
    FileInputStream in = null;


    public String updateProduct(String productId, String statusId, String url) {

        Log.e("product_id", productId);
        Log.e("status_id", statusId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            nameValuePairs.add(new BasicNameValuePair("product_id", productId));
            nameValuePairs.add(new BasicNameValuePair("status_id", statusId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String getDashboard(String url) {
        Log.e("url***", "***" + url);
        String response = "";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
        // HttpResponse response;
        JSONObject json = new JSONObject();
        try {
            HttpGet get = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = client.execute(get, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getLogin(String logintype, String email, String password,
                           String fullname, String age, String gender,
                           String phone, String socialid, String latitude,
                           String longitude, String deviceToken, String url) {

        // http: //
        // services.appliconsultants.com/stryker/web/service/login?
        //user_role=1&login_type=1&email=seller01@gmail.com&password=admin&
        //full_name=Amit%20Sinha&age=32&gender=M&phone=9555436989&
        //socialid=1&latitude=21.12345&longitude=77.12345&
        //device_id=did1&device_type=android

        //User Role can be 1->Admin, 2->Seller, 3->Buyer
        //Login Type can be 1->Normal, 2->Facebook, 3->Google

        Log.e("user_role", "2");
        Log.e("login_type", logintype);
        Log.e("email", email);
        Log.e("password", password);
        Log.e("full_name", fullname);
        Log.e("age", age);
        Log.e("gender", gender);
        Log.e("phone", phone);
        Log.e("socialid", socialid);
        Log.e("latitude", latitude);
        Log.e("longitude", longitude);
        Log.e("device_id", deviceToken);
        Log.e("device_type", "android");
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                    13);
            nameValuePairs.add(new BasicNameValuePair("user_role", "2"));
            nameValuePairs.add(new BasicNameValuePair("login_type", logintype));
            nameValuePairs.add(new BasicNameValuePair("loginId", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("full_name", fullname));
            nameValuePairs.add(new BasicNameValuePair("age", age));
            nameValuePairs.add(new BasicNameValuePair("gender", gender));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("socialid", socialid));
            nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
            nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
            nameValuePairs.add(new BasicNameValuePair("device_id",
                    deviceToken));
            nameValuePairs
                    .add(new BasicNameValuePair("device_type", "android"));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String sellerHomeData(String seller_id, String url) {

        //Log.e("seller_id", seller_id);
        //Log.e("url", url);

        //seller_id: "2"
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("seller_id", seller_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String manageUsers(String seller_id,String store_id, String url) {

        Log.e("seller_id", seller_id);
        Log.e("store_id", store_id);
        Log.e("url", url);

        //seller_id: "1"

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

            nameValuePairs.add(new BasicNameValuePair("seller_id", seller_id));
            nameValuePairs.add(new BasicNameValuePair("store_id", store_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }


    public String getDialog(String seller_id,String buyerId, String url) {

        Log.e("seller_id", seller_id);
        Log.e("buyer_id", buyerId);
        Log.e("url", url);

        //seller_id: "1"

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

            nameValuePairs.add(new BasicNameValuePair("seller_id", seller_id));
            nameValuePairs.add(new BasicNameValuePair("buyer_id", buyerId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String addDialog(String seller_id,String buyerId,String dialogId, String url) {

        Log.e("seller_id->", seller_id);
        Log.e("buyer_id", buyerId);
        Log.e("dialog_id", dialogId);
        Log.e("url", url);

        //seller_id: "1"

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

            nameValuePairs.add(new BasicNameValuePair("seller_id", seller_id));
            nameValuePairs.add(new BasicNameValuePair("buyer_id", buyerId));
            nameValuePairs.add(new BasicNameValuePair("dialog_id", dialogId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }


    public String RemoveChatUser(String userId, String store_id, String url) {

        Log.e("user_id", userId);
        Log.e("store_id", store_id);
        Log.e("url", url);

        //user_id: "2"
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_id", userId));
            nameValuePairs.add(new BasicNameValuePair("store_id", store_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String BlockChatUser(String sellerId, String buyerId, String url) {

        Log.e("seller_id", sellerId);
        Log.e("buyer_id", buyerId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("seller_id", sellerId));
            nameValuePairs.add(new BasicNameValuePair("buyer_id", buyerId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }


    public String OrderHistory(String buyerId,String storeId, String url) {

        Log.e("user_id", buyerId);
        Log.e("store_id", storeId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_id", buyerId));
            nameValuePairs.add(new BasicNameValuePair("store_id", storeId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String sellerRemoveStore(String userType,String userId, String store_id, String url) {

        Log.e("user_type", userType);
        Log.e("userId", userId);
        Log.e("store_id", store_id);
        Log.e("url", url);

        //user_id: "2"
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_type", userType));
            nameValuePairs.add(new BasicNameValuePair("user_id", userId));
            nameValuePairs.add(new BasicNameValuePair("store_id", store_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }
    public String updatechat(String user_id,String chat_id, String chat_login_id, String url) {

        Log.e("chat_id", chat_id);
        Log.e("chatLoginID", chat_login_id);
        Log.e("user_id", user_id);

        Log.e("url", url);
        //store_id: "did1",
        //user_id: "13"
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("chat_id", chat_id));
            nameValuePairs.add(new BasicNameValuePair("chatLoginID", chat_login_id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }
    public String forgotPassword(String email, String url) {

        Log.e("email", email);

        Log.e("url", url);
        // http://services.appliconsultants.com/inffo/web/service/forgetpassword?email=aksinha1982@gmail.com
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("roletype", "2"));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String storeList(String url) {



        Log.e("url", url);
        // http://services.appliconsultants.com/inffo/web/service/forgetpassword?email=aksinha1982@gmail.com
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }
	
	/*public String addStore(String sellerId, String storeId, String busName,String busAdd,String busEmail,String busPhone,
						   String openTime, String closeTime,String isMon,String isTue,String isWed,String isThur,String isFri,
						   String isSat,String isSun,String deliveryAvailable, String distance,String imagesGal, String lat,String lon, String url) {

		Log.e("SellerId",sellerId );
		Log.e("StoreCode",storeId );
		Log.e("BusinessName",busName);
		Log.e("BusinessAdd",busAdd);
		Log.e("BusinessEmail",busEmail);
		Log.e("BusinessPhone",busPhone);
		Log.e("OpenTime", openTime);
		Log.e("CloseTime",closeTime );
		Log.e("Mon",isMon );
		Log.e("Tue",isTue );
		Log.e("Wed",isWed );
		Log.e("Thur",isThur );
		Log.e("Fri",isFri );
		Log.e("Sat",isSat );
		Log.e("Sun",isSun );
		Log.e("Distance",distance);
		Log.e("Images",imagesGal);
		Log.e("DeliveryAvailable",deliveryAvailable);
		Log.e("Latitude",lat);
		Log.e("Longitude",lon);
		Log.e("url", url);

		//http://services.appliconsultants.com/stryker/web/service/addbuyerstore?buyer_id=&store_id=1&device_id=did3
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("seller_id",sellerId ));
			nameValuePairs.add(new BasicNameValuePair("store_code",storeId));
			nameValuePairs.add(new BasicNameValuePair("business_name", busName));
			nameValuePairs.add(new BasicNameValuePair("business_address",busAdd));
			nameValuePairs.add(new BasicNameValuePair("email_id",busEmail));
			nameValuePairs.add(new BasicNameValuePair("mobile",busPhone));
			nameValuePairs.add(new BasicNameValuePair("opening_time", openTime));
			nameValuePairs.add(new BasicNameValuePair("closing_time", closeTime));
			nameValuePairs.add(new BasicNameValuePair("is_mon", isMon));
			nameValuePairs.add(new BasicNameValuePair("is_tue", isTue));
			nameValuePairs.add(new BasicNameValuePair("is_wed", isWed));
			nameValuePairs.add(new BasicNameValuePair("is_thu", isThur));
			nameValuePairs.add(new BasicNameValuePair("is_fri", isFri));
			nameValuePairs.add(new BasicNameValuePair("is_sat", isSat));
			nameValuePairs.add(new BasicNameValuePair("is_sun", isSun));
			nameValuePairs.add(new BasicNameValuePair("latitude",lat ));
			nameValuePairs.add(new BasicNameValuePair("longitude",lon ));
			nameValuePairs.add(new BasicNameValuePair("is_delivery",deliveryAvailable ));

			nameValuePairs.add(new BasicNameValuePair("galleries", imagesGal));
			//nameValuePairs.add(new BasicNameValuePair("delivery_distance",distance ));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				res = EntityUtils.toString(resEntity);
				// .....Read the response
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}*/

    public String indusType(String url) {

        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }
    public String addStore(String sellerId, String storeId, String busName,String busAdd,String busEmail,String busPhone,
                           String openTime, String closeTime,String isMon,String isTue,String isWed,String isThur,String isFri,
                           String isSat,String isSun,String deliveryAvailable, String distance,String imagesGal,
                           String lat,String lon,String categoryId,String minOrder, String logoUrl, String broucherUrl,
                           String standard_deliv_time,String aboutUs,String url) {

        Log.e("SellerId",sellerId );
        Log.e("StoreCode",storeId );
        Log.e("BusinessName",busName);
        Log.e("about_us", aboutUs);
        Log.e("BusinessAdd",busAdd);
        Log.e("BusinessEmail",busEmail);
        Log.e("BusinessPhone",busPhone);
        Log.e("OpenTime", openTime);
        Log.e("CloseTime",closeTime );
        Log.e("Mon",isMon );
        Log.e("Tue",isTue );
        Log.e("Wed",isWed );
        Log.e("Thur",isThur );
        Log.e("Fri",isFri );
        Log.e("Sat",isSat );
        Log.e("Sun",isSun );
        Log.e("Distance",distance);
        Log.e("Images",imagesGal);
        Log.e("DeliveryAvailable",deliveryAvailable);
        Log.e("Latitude",lat);
        Log.e("Longitude",lon);
        Log.e("industry_type",categoryId);
        Log.e("min_order_value",minOrder);
        Log.e("logo",logoUrl);
        Log.e("ebrochure",broucherUrl);

        Log.e("standard_deliv_time",standard_deliv_time);
        Log.e("url", url);

        //http://services.appliconsultants.com/stryker/web/service/addbuyerstore?buyer_id=&store_id=1&device_id=did3
        try {
            String aboutText = URLEncoder.encode(aboutUs, "UTF-8");
            // String aboutText = new String(aboutUs.getBytes("UTF-8"));
            Log.e("After encode about us", aboutText+ " ***");
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("seller_id",sellerId ));
            nameValuePairs.add(new BasicNameValuePair("store_code",storeId));
            nameValuePairs.add(new BasicNameValuePair("business_name", busName));
            nameValuePairs.add(new BasicNameValuePair("about_us",aboutText));
            nameValuePairs.add(new BasicNameValuePair("business_address",busAdd));
            nameValuePairs.add(new BasicNameValuePair("email_id",busEmail));
            nameValuePairs.add(new BasicNameValuePair("mobile",busPhone));
            nameValuePairs.add(new BasicNameValuePair("opening_time", openTime));
            nameValuePairs.add(new BasicNameValuePair("closing_time", closeTime));
            nameValuePairs.add(new BasicNameValuePair("is_mon", isMon));
            nameValuePairs.add(new BasicNameValuePair("is_tue", isTue));
            nameValuePairs.add(new BasicNameValuePair("is_wed", isWed));
            nameValuePairs.add(new BasicNameValuePair("is_thu", isThur));
            nameValuePairs.add(new BasicNameValuePair("is_fri", isFri));
            nameValuePairs.add(new BasicNameValuePair("is_sat", isSat));
            nameValuePairs.add(new BasicNameValuePair("is_sun", isSun));
            nameValuePairs.add(new BasicNameValuePair("latitude",lat ));
            nameValuePairs.add(new BasicNameValuePair("longitude",lon ));
            nameValuePairs.add(new BasicNameValuePair("is_delivery",deliveryAvailable ));

            nameValuePairs.add(new BasicNameValuePair("galleries", imagesGal));

            nameValuePairs.add(new BasicNameValuePair("industry_type", categoryId));
            nameValuePairs.add(new BasicNameValuePair("min_order_value", minOrder));
            nameValuePairs.add(new BasicNameValuePair("ebrochure", broucherUrl));
            nameValuePairs.add(new BasicNameValuePair("logo", logoUrl));
            nameValuePairs.add(new BasicNameValuePair("distance",distance));
            nameValuePairs.add(new BasicNameValuePair("standard_deliv_time",standard_deliv_time));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }



    //.....Refer By Message.....//
    public String promoteApp(String phone_no, String messsage, String url) {

        Log.e("SellerId",phone_no );
        Log.e("StoreCode",messsage );
        Log.e("url", url);

        //http://services.appliconsultants.com/stryker/web/service/addbuyerstore?buyer_id=&store_id=1&device_id=did3
        try {
            //String aboutText = URLEncoder.encode(aboutUs, "UTF-8");
            // String aboutText = new String(aboutUs.getBytes("UTF-8"));
            //Log.e("After encode about us", aboutText+ " ***");
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("phone_no",phone_no ));
            nameValuePairs.add(new BasicNameValuePair("messsage",messsage));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }



    public String favInstituteList(String userId, String url) {
        Log.e("loggedin_user_id", userId);
        Log.e("url", url);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String searchInstitutes(String userId, String region_id,
                                   String distance, String latitude, String longitude,
                                   String page_num, String sort_by, String url) {

        Log.e("loggedin_user_id", userId);
        Log.e("region_id", region_id);
        Log.e("distance", distance);
        Log.e("latitude", latitude);
        Log.e("longitude", longitude);
        Log.e("page_num", page_num);
        Log.e("sort_by", sort_by);
        Log.e("url", url);

        Log.e("url", url);
        // loggedin_user_id=&region_id=&distance=&latitude=&longitude=&page_num=&sort_by=
        // if not login sort_by = 2 else 1. distance = 200
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));
            nameValuePairs.add(new BasicNameValuePair("region_id", region_id));
            nameValuePairs.add(new BasicNameValuePair("distance", distance));
            nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
            nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
            nameValuePairs.add(new BasicNameValuePair("page_num", page_num));
            nameValuePairs.add(new BasicNameValuePair("sort_by", sort_by));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String instituteDetail(String institute_id, String latitude,
                                  String longitude, String loggedin_user_id, String url) {

        Log.e("institute_id", institute_id);
        Log.e("latitude", latitude);
        Log.e("longitude", longitude);
        Log.e("loggedin_user_id", loggedin_user_id);
        Log.e("url", url);

        Log.e("url", url);
        // institute_id=1&latitude=44.312603&longitude=23.812012&loggedin_user_id
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("institute_id",
                    institute_id));
            nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
            nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    loggedin_user_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String likeInstitute(String userId, String institute_id,
                                String to_do, String url) {

        Log.e("loggedin_user_id", userId);
        Log.e("institute_id", institute_id);
        Log.e("to_do", to_do);
        Log.e("url", url);

        Log.e("url", url);
        // loggedin_user_id=2&institute_id=1&to_do=1
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));
            nameValuePairs.add(new BasicNameValuePair("institute_id",
                    institute_id));
            nameValuePairs.add(new BasicNameValuePair("to_do", to_do));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String instituteViceLikes(String institute_id, String url) {
        Log.e("institute_id", institute_id);
        Log.e("url", url);
        Log.e("url", url);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("institute_id",
                    institute_id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String chatUserMessageList(String userId, String latitude,
                                      String longitude, String url) {

        Log.e("loggedin_user_id", userId);
        Log.e("latitude", latitude);
        Log.e("longitude", longitude);

        Log.e("url", url);
        // http://services.appliconsultants.com/inffo/web/service/forgetpassword?email=aksinha1982@gmail.com
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));
            nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
            nameValuePairs.add(new BasicNameValuePair("longitude", longitude));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String chatList(String userId, String sender_id, String url) {

        Log.e("loggedin_user_id", userId);
        Log.e("sender_id", sender_id);

        Log.e("url", url);
        // http://services.appliconsultants.com/inffo/web/service/forgetpassword?email=aksinha1982@gmail.com
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));
            nameValuePairs.add(new BasicNameValuePair("sender_id", sender_id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String sendMessage(String sender_id, String receiver_id,
                              String message, String url) {

        Log.e("sender_id", sender_id);
        Log.e("receiver_id", receiver_id);
        Log.e("message", message);

        Log.e("url", url);
        // http://services.appliconsultants.com/inffo/web/service/forgetpassword?email=aksinha1982@gmail.com
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("sender_id", sender_id));
            nameValuePairs.add(new BasicNameValuePair("receiver_id",
                    receiver_id));
            nameValuePairs.add(new BasicNameValuePair("message", message));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String myCommentList(String userId, String url) {

        Log.e("loggedin_user_id", userId);

        Log.e("url", url);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String postComment(String userId, String institute_id,
                              String comment, String ratting, String url) {

        Log.e("loggedin_user_id", userId);
        Log.e("institute_id", institute_id);
        Log.e("comment", comment);
        Log.e("ratting", ratting);

        Log.e("url", url);
        // loggedin_user_id=2&institute_id=1&comment=Good%20institute&ratting=4
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));
            nameValuePairs.add(new BasicNameValuePair("institute_id",
                    institute_id));
            nameValuePairs.add(new BasicNameValuePair("comment", comment));
            nameValuePairs.add(new BasicNameValuePair("ratting", ratting));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String myProfile(String userId, String url) {

        Log.e("loggedin_user_id", userId);

        Log.e("url", url);
        // http://services.appliconsultants.com/inffo/web/service/forgetpassword?email=aksinha1982@gmail.com
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String changeSettings(String userId, String field_name,
                                 String field_value, String url) {

        Log.e("loggedin_user_id", userId);
        Log.e("field_name", field_name);
        Log.e("field_value", field_value);

        Log.e("url", url);
        // http://services.appliconsultants.com/inffo/web/service/forgetpassword?email=aksinha1982@gmail.com
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("loggedin_user_id",
                    userId));
            nameValuePairs
                    .add(new BasicNameValuePair("field_name", field_name));
            nameValuePairs.add(new BasicNameValuePair("field_value",
                    field_value));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

	/*public String updateProfile(String user_id, String name, String age,
			String location, String latitude, String logitude,
			String profile_pic, String url) throws ClientProtocolException,
			IOException {
		// field_name can be Name or ProfilePic
		// Parameter for image is : profile_pic
		*//***//*
		String responce = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		FileBody filebodyImage = null;
		if (!profile_pic.equalsIgnoreCase("")) {
			filebodyImage = new FileBody(new File(profile_pic));
		}
		Log.e("user_id", user_id);
		Log.e("name", name);
		Log.e("age", age);
		Log.e("location", location);
		Log.e("latitude", latitude);
		Log.e("logitude", logitude);
		Log.e("profile_pic", profile_pic);
		Log.e("url", url);

		StringBody userId1 = new StringBody(user_id);
		StringBody name1 = new StringBody(name);
		StringBody age1 = new StringBody(age);
		StringBody location1 = new StringBody(location);
		StringBody latitude1 = new StringBody(latitude);
		StringBody longitude1 = new StringBody(logitude);

		MultipartEntity reqEntity = new MultipartEntity();
		if (!profile_pic.equalsIgnoreCase("")) {
			reqEntity.addPart("profile_pic", filebodyImage);
		}
		reqEntity.addPart("user_id", userId1);
		reqEntity.addPart("name", name1);
		reqEntity.addPart("age", age1);
		reqEntity.addPart("location", location1);
		reqEntity.addPart("latitude", latitude1);
		reqEntity.addPart("logitude", longitude1);

		httppost.setEntity(reqEntity);

		// DEBUG
		System.out.println("executing request " + httppost.getRequestLine());
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		// DEBUG
		System.out.println(response.getStatusLine());
		if (resEntity != null) {
			responce = EntityUtils.toString(resEntity);
			System.out.println(responce);
		} // end if
			// Log.e("reponse", "****************"+EntityUtils.toString(
			// resEntity ));
		if (resEntity != null) {
			resEntity.consumeContent();
		} // end if

		httpclient.getConnectionManager().shutdown();

		return responce;

		*//***//*

	}*/

    public String deleteImage(String name, String url) {

        Log.e("file_url", name);

        Log.e("url", url);
        // http://services.appliconsultants.com/inffo/web/service/forgetpassword?email=aksinha1982@gmail.com
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("file_url", name));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }


    public String getProductlist(String store_id, String url) {
        Log.e("store_id" ,store_id);
        Log.e("store_Url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("store_id", store_id));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(post);

            if(response != null){
                HttpEntity respentity = response.getEntity();
                res = EntityUtils.toString(respentity);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public String publishProduct(String store_id,String isPublish, String url) {

        Log.e("ispublish",isPublish);
        Log.e("store_id" ,store_id);
        Log.e("store_Url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("store_id", store_id));
            nameValuePairs.add(new BasicNameValuePair("ispublish", isPublish));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(post);

            if(response != null){
                HttpEntity respentity = response.getEntity();
                res = EntityUtils.toString(respentity);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    public String updateProduct(String product_id,String product_name,String product_price,String product_image, String url) {
        //product_id=10&product_name=Store%206%20Pro_new%201&product_price=28
        Log.e("product_id",product_id);
        Log.e("product_name" ,product_name);
        Log.e("product_price", product_price);
        Log.e("product_image", product_price);

        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),10000);
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
            nameValuePairs.add(new BasicNameValuePair("product_name", product_name));
            nameValuePairs.add(new BasicNameValuePair("product_price", product_price));
            nameValuePairs.add(new BasicNameValuePair("product_image", product_image));


            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(post);

            if(response != null){
                HttpEntity respentity = response.getEntity();
                res = EntityUtils.toString(respentity);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }



    public String product(String store_id, String productname, String desc,
                          String product_cat_id, String price, String product_image, String url) {

        Log.e("store_id",store_id);
        Log.e("product_name", productname);
        Log.e("product_cat_id", product_cat_id);
        Log.e("product_description", desc);
        Log.e("product_price", price);
        Log.e("product_image", product_image);
        Log.e("Url", url);

        try {
            String descriptionEncode = URLEncoder.encode(desc, "utf-8");
            //String descriptionEncode = new String(desc.getBytes("UTF-8"));
            Log.e("encoded description ** ", descriptionEncode + "  ***");
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("store_id", store_id));
            nameValuePairs.add(new BasicNameValuePair("product_name",productname));
            //nameValuePairs.add(new BasicNameValuePair("product_description", descriptionEncode));
            nameValuePairs.add(new BasicNameValuePair("product_description", desc));
            nameValuePairs.add(new BasicNameValuePair("product_cat_id", product_cat_id));
            nameValuePairs.add(new BasicNameValuePair("product_price", price));
            nameValuePairs.add(new BasicNameValuePair("product_image",product_image));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(post);
            if(response != null){
                HttpEntity respentity = response.getEntity();
                res = EntityUtils.toString(respentity);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public String deleteProduct(String productId, String url) {

        Log.e("product_id", productId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("product_id", productId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String productWiseReport(String storeId, String url) {

        Log.e("store_id", storeId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("store_id", storeId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String userWiseReport(String storeId, String url) {

        Log.e("store_id", storeId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("store_id", storeId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String dateWiseReport(String storeId, String url) {

        Log.e("store_id", storeId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("store_id", storeId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String resendCode(String sellerId, String url) {

        Log.e("seller_id", sellerId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("seller_id", sellerId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

	/*public String deleteImage(String name, String url)
	{
		Log.e("file_url",name);
		Log.e("URL", url);

		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
			HttpPost httpPost = new HttpPost();

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

			nameValuePairs.add(new BasicNameValuePair("file_url",name));

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				res = EntityUtils.toString(resEntity);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return res;
	}*/

    public String storeImageUpload(String offer_image_path, String url) throws ClientProtocolException, IOException {

/***/
        String responce = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
        HttpPost httppost = new HttpPost(url);
        FileBody filebodyImage = null;
        if (!offer_image_path.equalsIgnoreCase("")) {
            filebodyImage = new FileBody(new File(offer_image_path));
        }
        Log.e("ProductImage", offer_image_path);
        Log.e("url", url);

        MultipartEntity reqEntity = new MultipartEntity();

        if (!offer_image_path.equalsIgnoreCase("")) {
            reqEntity.addPart("productimage", filebodyImage);
        }

        httppost.setEntity(reqEntity);

        // DEBUG
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        // DEBUG
        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            responce = EntityUtils.toString(resEntity);
            System.out.println(responce);
        } // end if
        // Log.e("reponse", "****************"+EntityUtils.toString(
        // resEntity ));
        if (resEntity != null) {
            resEntity.consumeContent();
        } // end if

        httpclient.getConnectionManager().shutdown();

        return responce;

        /***/
    }


    public String storeFileUpload(String file_path, String url) throws ClientProtocolException, IOException {

/***/
        String responce = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
        HttpPost httppost = new HttpPost(url);
        FileBody filebodyImage = null;
        if (!file_path.equalsIgnoreCase("")) {
            filebodyImage = new FileBody(new File(file_path));
        }
        Log.e("ProductImage", file_path);
        Log.e("url", url);

        MultipartEntity reqEntity = new MultipartEntity();

        if (!file_path.equalsIgnoreCase("")) {
            reqEntity.addPart("productimage", filebodyImage);
        }

        httppost.setEntity(reqEntity);

        // DEBUG
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        // DEBUG
        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            responce = EntityUtils.toString(resEntity);
            System.out.println(responce);
        } // end if
        // Log.e("reponse", "****************"+EntityUtils.toString(
        // resEntity ));
        if (resEntity != null) {
            resEntity.consumeContent();
        } // end if

        httpclient.getConnectionManager().shutdown();

        return responce;

        /***/
    }

    public String cancelOrder(String order_id,String status_id,String user_id,String device_id, String url) {

        Log.e("order_id", order_id);
        Log.e("status_id", status_id);
        Log.e("user_id", user_id);
        Log.e("device_id", device_id);

        Log.e("url", url);
        //store_id: "did1",
        //user_id: "13"
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("order_id", order_id));
            nameValuePairs.add(new BasicNameValuePair("status_id", status_id));
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("device_id", device_id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;

    }

    public String showNotifications(String sellerId, String storeId, String url) {

        Log.e("seller_id", sellerId);
        Log.e("store_id", storeId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("seller_id", sellerId));
            nameValuePairs.add(new BasicNameValuePair("store_id", storeId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String sendNotifications(String storeId, String message, String url) {

        Log.e("store_id", storeId);
        Log.e("message", message);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("store_id", storeId));
            nameValuePairs.add(new BasicNameValuePair("message", message));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }


    public String verifyCode(String sellerId,String verificationCode, String url) {

        Log.e("seller_id", sellerId);
        Log.e("verification_code", verificationCode);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("seller_id", sellerId));
            nameValuePairs.add(new BasicNameValuePair("verification_code", verificationCode));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String localNotifications(String sellerId, String url) {

        Log.e("seller_id-->", sellerId);
        Log.e("url", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000); // Timeout
            HttpPost httppost = new HttpPost(url);
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("seller_id", sellerId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                res = EntityUtils.toString(resEntity);
                // .....Read the response
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String productUpdate(String proId, String storeId, String catId, String name, String desc, String price, String userId,String imageUrl) {



        try {
            String descriptionEncode = URLEncoder.encode(desc, "utf-8");
            //String descriptionEncode = new String(desc.getBytes("UTF-8"));
            Log.e("encoded description ** ", descriptionEncode + "  ***");
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
            HttpPost post = new HttpPost("http://marketapp.online/web/service/editProductDetail");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("productId", proId));
            nameValuePairs.add(new BasicNameValuePair("storeId",storeId));
            nameValuePairs.add(new BasicNameValuePair("productCategoryID", catId));
            nameValuePairs.add(new BasicNameValuePair("productName", name));
            nameValuePairs.add(new BasicNameValuePair("description", desc));
            nameValuePairs.add(new BasicNameValuePair("price",price));
            nameValuePairs.add(new BasicNameValuePair("modifiedBy",userId));
            nameValuePairs.add(new BasicNameValuePair("productimage ",imageUrl));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(post);
            if(response != null){
                HttpEntity respentity = response.getEntity();
                res = EntityUtils.toString(respentity);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
