package com.aloknath.crudapp.HttpManager;

import android.util.Log;

import com.aloknath.crudapp.Objects.Item;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Created by ALOKNATH on 4/12/2015.
 */

public class HttpManager {

    private static HttpClient httpClient;

    public static String putData(String category, String itemName, int id){

        try {

            httpClient = new DefaultHttpClient();

            Item item = new Item();
            item.setCategory(category);
            item.setName(itemName);

            StringEntity stringEntity = new StringEntity(item.toString());
            Log.i("The Item String: ", item.toString());
            Log.i("The URL: ", "https://czshopper.herokuapp.com/items/"+String.valueOf(id)+".json");

            HttpPut httpPut = new HttpPut("https://czshopper.herokuapp.com/items/"+String.valueOf(id)+".json");
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");
            httpPut.addHeader("X-CZ-Authorization", "2exdtXq4QRpoXZhtbwx7");
            httpPut.setEntity(stringEntity);
            HttpResponse httpResponse = httpClient.execute(httpPut);

            InputStream ips  = httpResponse.getEntity().getContent();
            BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8"));

            if(httpResponse.getStatusLine().getStatusCode()!= HttpStatus.SC_OK)
            {
                try {
                    throw new Exception(httpResponse.getStatusLine().getReasonPhrase());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StringBuilder sb = new StringBuilder();
            String s;
            while(true)
            {
                s = buf.readLine();
                if(s==null || s.length()==0)
                    break;
                sb.append(s);

            }
            buf.close();
            ips.close();

            Log.i("Returned Data from the Put Operation: ",sb.toString());

            return sb.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getData(String urlPassed) {

         httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(urlPassed);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("X-CZ-Authorization", "2exdtXq4QRpoXZhtbwx7");

        HttpResponse httpResponse;

        try {
            httpResponse = httpClient.execute(httpGet);

            InputStream ips  = httpResponse.getEntity().getContent();
            BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8"));

            if(httpResponse.getStatusLine().getStatusCode()!= HttpStatus.SC_OK)
            {
                try {
                    throw new Exception(httpResponse.getStatusLine().getReasonPhrase());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StringBuilder sb = new StringBuilder();
            String s;
            while(true)
            {
                s = buf.readLine();
                if(s==null || s.length()==0)
                    break;
                sb.append(s);

            }
            buf.close();
            ips.close();

            Log.i("Returned Data: ",sb.toString());

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
