package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addButton, deleteButton, confirmButton;
    EditText cityInput;
    LinearLayout inputContainer;

    int currentIndex = -1; // indexing to basically check the selected data and track it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // linking java variables to corresponding xml views
        cityList = findViewById(R.id.city_list);    //display city
        addButton = findViewById(R.id.add_button);  //add city button
        deleteButton = findViewById(R.id.delete_button);  //delete city button
        confirmButton = findViewById(R.id.confirm_button); // confirm
        cityInput = findViewById(R.id.input_name); //text inpout
        inputContainer = findViewById(R.id.input_container); //holds input and confirms

        // initial starting list of cities
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin",
                "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        //using built in layout
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dataList);
        cityList.setAdapter(cityAdapter); //attach listview and adapter
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE); //limiting to one city at a time

        // for handling clivks anf saving index
        cityList.setOnItemClickListener((adapterView, view, position, id) -> {
            currentIndex = position;
            cityList.setItemChecked(position, true);
        });

        // dealing with add city, showinf input text bar at bottom for user
        addButton.setOnClickListener(v -> {
            inputContainer.setVisibility(LinearLayout.VISIBLE);
            cityInput.requestFocus();
        });

        // dealing with confirm button to use entered text and update ListView
        confirmButton.setOnClickListener(v -> {
            String newCity = cityInput.getText().toString().trim();
            if (!newCity.isEmpty()) {
                dataList.add(newCity);
                cityAdapter.notifyDataSetChanged();
                cityInput.setText("");
                inputContainer.setVisibility(LinearLayout.GONE);
            } else {
                Toast.makeText(this, "Enter a city name!", Toast.LENGTH_SHORT).show(); //dealing with if no city is entrered but confirm is pressed
            }
        });

        // dealing with delete button
        deleteButton.setOnClickListener(v -> {
            if (currentIndex != -1) {
                dataList.remove(currentIndex);
                cityAdapter.notifyDataSetChanged();
                cityList.clearChoices();
                currentIndex = -1;
            } else {
                Toast.makeText(this, "Select a city to delete", Toast.LENGTH_SHORT).show(); //exception incase no city selected to delete
            }
        });
    }
}
