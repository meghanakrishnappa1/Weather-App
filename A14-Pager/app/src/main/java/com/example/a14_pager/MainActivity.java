package com.example.a14_pager;

import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.a14_pager.databinding.ActivityMainBinding;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//A14-Pager ver03
public class MainActivity extends AppCompatActivity implements MyFragmentDataPassListener{
    private ViewPager2 viewPager2;
    private ActivityMainBinding binding;
    MyViewPager2Adapter adapter;
    ArrayList<MyRecyclerViewData> data;
    private char F = 'C';

    String json = " ";
    public MyFragmentDataPassListener delegate;
    private int zipCode;
    private MyRecyclerViewData data1 = null;
    private String endpoint = "https://api.openweathermap.org/data/2.5/weather?zip=";
    private final String key = "e43a4f72578b835e5977afa214261785";

    public MainActivity(int zipCode) {
        this.zipCode = zipCode;
        endpoint = endpoint + zipCode + "&appid="+ key;
    }

    public MainActivity(){

    }
    ArrayList<MyRecyclerViewData> lv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        data = new ArrayList<>();
        InputStream  is = getResources().openRawResource(R.raw.savedcities);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try {
            String line = reader.readLine();
            while(line != null){
                MainActivity request = new MainActivity(Integer.parseInt(line));
                request.delegate = this;
                request.callApi();

                line = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        adapter = new MyViewPager2Adapter(data);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(adapter);
        SpringDotsIndicator springDotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        springDotsIndicator.setViewPager2(viewPager2);

        binding.button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String temp;
            String low;
            String high;

            if(F == 'C'){
                double tempToFahrenheit = (((double)9/5)) + 32;
                temp = String.format("%.0f", tempToFahrenheit) + "°F";
                tempToFahrenheit = (((double)9/5) + 32);
                low  = String.format("%.0f", tempToFahrenheit) + "°F";
                tempToFahrenheit = (((double)9/5) + 32);
                high = String.format("%.0f", tempToFahrenheit) + "°F";
                binding.temp.setText(temp);
                binding.tempMin.setText(low);
                binding.tempMax.setText(high);
                binding.button.setText("°C");
                F = 'F';
            }
            else{
                double tempToCelsius = (((double)9/5)) + 32;
                temp = String.format("%.0f", tempToCelsius) + "°C";
                low  = String.format("%.0f", tempToCelsius)  + "°C";
                high = String.format("%.0f", tempToCelsius) + "°C";
                binding.temp.setText(temp);
                binding.tempMin.setText(low);
                binding.tempMax.setText(high);
                binding.button.setText("°F");
                F = 'C';
            }
        }
    });
}


    @Override
    public void getData(MyRecyclerViewData cityData) {
        Log.i("testing",cityData.toString());
        data.add(cityData);
        adapter.notifyDataSetChanged();
    }

public void callApi() {
    JSONParser parser = new JSONParser();
    new Thread(new Runnable() {
        String result = "";
        @Override
        public void run() {
            try {
                result = downloadUrl(endpoint);

            } catch (IOException e) {
                return;
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        JSONObject object = (JSONObject) parser.parse(result);
                        JSONObject tempObject = (JSONObject) object.get("main");
                        JSONArray weatherObject = (JSONArray)object.get("weather");

                        String name = object.get("name").toString();
                        int temperature = (int)Double.parseDouble(tempObject.get("temp").toString());
                        temperature = (int) ((temperature - 273.15) * 1.8 + 32);

                        int minTemp = (int) Double.parseDouble(tempObject.get("temp_min").toString());
                        minTemp = (int) ((minTemp - 273.15) * 1.8 + 32);

                        int maxTemp = (int) Double.parseDouble(tempObject.get("temp_max").toString());
                        maxTemp = (int) ((maxTemp - 273.15) * 1.8 + 32);

                        JSONObject conditionObject = (JSONObject) weatherObject.get(0);
                        String condition = conditionObject.get("main").toString();
                        int climaCode = Integer.parseInt(conditionObject.get("id").toString());


                        data1 = new MyRecyclerViewData(zipCode,name,temperature,condition,climaCode,minTemp,maxTemp);
                        delegate.getData(data1);
                    }catch (ParseException | org.json.simple.parser.ParseException e){
                        e.printStackTrace();
                    }

                }
            });
        }


    }).start();
}

    private String downloadUrl(String urlString) throws IOException {
        String result = "Download https error";
        InputStream in = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            in = conn.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();
        } catch (IOException e) {
        } finally {
            if(in != null)
                try {
                    in.close();
                    reader.close();
                } catch (IOException e) {
                }
        }
        return result;
    }

}