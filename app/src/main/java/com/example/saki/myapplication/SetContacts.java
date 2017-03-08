package com.example.saki.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class SetContacts extends AppCompatActivity {

    EditText phone1, phone2, phone3;
    Button savecontacts;
    String phoneNumber1, phoneNumber2, phoneNumber3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_contacts);

                phone1 = (EditText) findViewById(R.id.phone1);
                phone2 = (EditText) findViewById(R.id.phone2);
                phone3 = (EditText) findViewById(R.id.phone3);

                savecontacts = (Button) findViewById(R.id.savecontact);

                savecontacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        save();


                }
                    public void save()
                    {


                        phoneNumber1 = phone1.getText().toString();
                        phoneNumber2 = phone2.getText().toString();
                        phoneNumber3 = phone3.getText().toString();

                        try {
                            FileOutputStream fos1 = openFileOutput("contacts1.txt" , MODE_WORLD_READABLE);
                            OutputStreamWriter osw = new OutputStreamWriter(fos1);
                            osw.write(phoneNumber1);
                            osw.write(phoneNumber2);
                            osw.write(phoneNumber3);
                            osw.flush();
                            osw.close();
                            Toast.makeText(getBaseContext(), "Saved" ,Toast.LENGTH_LONG).show();
                            phone1.setText("");
                            phone2.setText("");
                            phone3.setText("");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    public void show() throws IOException {
                        String number;
                        FileInputStream fis = openFileInput("contacts1.txt");
                        InputStreamReader inputStreamReader =new InputStreamReader(fis);
                        BufferedReader bufferedReader =new BufferedReader(inputStreamReader);
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((number = bufferedReader.readLine())!= null)
                        {
                            stringBuffer.append(number + "\n");
                            //display.setText(stringBuffer.toString());
                          //  display.setVisibility(View.VISIBLE);
                        }

                       // display.setText(stringBuffer.toString());
                        //display.setVisibility(View.VISIBLE);
                    }

            });
        }


    }






