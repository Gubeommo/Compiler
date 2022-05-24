package org.ganymede.minicompiler;


import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import org.ganymede.minicompiler.Api.ApiHandler;
import org.ganymede.minicompiler.Api.ApiService;
import org.ganymede.minicompiler.Api.PostData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    private List<String> list;
    TextView tvResult;
    EditText  etInput;
    Button  btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult  = findViewById(R.id.tv_result);
        etInput   = findViewById(R.id.et_input);
        btnSubmit = findViewById(R.id.btn_submit);

        list = new ArrayList<String>();
        settingList();

        final MultiAutoCompleteTextView autoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.et_input);
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,  list ));
        autoCompleteTextView.setTokenizer(new SpaceTokenizer());

        autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keycode, KeyEvent keyEvent) {
                switch (keycode){
                    case KeyEvent.KEYCODE_ENTER:etInput.append("\n ");
                }
                return true;
            }
        });







        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService apiService = ApiHandler.getRetrofitInstance();
                Call<String> execute = apiService.execute(new PostData(etInput.getText().toString()));

                tvResult.setText("Loading...");

                execute.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        tvResult.setText("");

                        try {

                            if(response.isSuccessful()){


                                JSONObject responseJson = new JSONObject(response.body().toString());
                                String output = responseJson.getString("output");
                                tvResult.setText(output);

                            }else{

                                Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Gagal Parsing JSON : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        tvResult.setText("Failed");
                        Toast.makeText(MainActivity.this, "Gagal : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }



    private void settingList(){
        list.add("#include <stdio.h>");
        list.add("#include <time.h>");
        list.add("#include <math.h>");
        list.add("#include <stdlib.h>");
        list.add("#include <string.h>");
        list.add("#include <ctype.h>");
        list.add("int main(void){\n\n}");
        list.add("int");
        list.add("String");
        list.add("char");
        list.add("double");
        list.add("float");
        list.add("abs();");
        list.add("acos();");
        list.add("asctime();");
        list.add("asctime_r();");
        list.add("asin();");
        list.add("atan();");
        list.add("atan2();");
        list.add("atof();");
        list.add("atoi();");
        list.add("atol();");
        list.add("bsearch();");
        list.add("btowc();");
        list.add("calloc();");
        list.add("ceil();");
        list.add("clearerr();");
        list.add("clock();");
        list.add("cos();");
        list.add("cosh();");
        list.add("ctime();");
        list.add("ctime64();");
        list.add("ctime_r();");

        list.add("printf();");


        list.add("return 0;");

    }

}
