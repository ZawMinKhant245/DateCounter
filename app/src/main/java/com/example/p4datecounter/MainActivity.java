package com.example.p4datecounter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.p4datecounter.databinding.ActivityHomeBinding;
import com.example.p4datecounter.databinding.ActivityMainBinding;
import com.example.p4datecounter.databinding.ChangeAgeBinding;
import com.example.p4datecounter.databinding.ChangeNameBinding;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private LocalDate startDate;
    private final  String TAG="KO";


   public static    int ageInt;
    private DatePickerDialog datePickerDialog;
    private  AlertDialog changeNameDialog,changeAgeDialog;
    private  enum  Gender{
        MALE,FEMALE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDataBase();
        inlister();
        periodDate();



    }

    private void initDataBase() {
//

        String male=Database.readName(this,Gender.MALE.toString()).isEmpty() ? "Male" : Database.readName(this,Gender.MALE.toString());
        binding.tv111Male.setText(male);
//
        String female=Database.readName(this,Gender.FEMALE.toString()).isEmpty() ? "Female" : Database.readName(this,Gender.FEMALE.toString());
        binding.tv111Female.setText(female);
//////
        long datelong=Database.readDate(this,Database.Date);
        startDate= datelong == -1 ? LocalDate.now() :LocalDate.ofEpochDay(datelong);

         ageInt=Database.readAge(this,Database.AGE);

        updateUni();
    }

    private void inlister() {
        binding.btDate.setOnClickListener(v -> {
            showDatePicker();
        });
        binding.tv111Male.setOnClickListener(v -> {
            showChaangeNameDialog(Gender.MALE);
        });
        binding.tv111Female.setOnClickListener(v -> {
            showChaangeNameDialog(Gender.FEMALE);
        });
        binding.tvage.setOnClickListener(v -> {
            showChaangeAgeDialog(Gender.MALE);
        });
        binding.tvage1.setOnClickListener(v -> {
            showChaangeAgeDialog(Gender.FEMALE);
        });

    }
    private ChangeAgeBinding changeAgeBinding;


    private ChangeNameBinding changeNameBinding;
    private void showChaangeAgeDialog(Gender gender) {


        if (changeAgeDialog== null) {
            changeAgeBinding = ChangeAgeBinding.inflate(getLayoutInflater());
            changeAgeDialog = new AlertDialog.Builder(this)
                    .setView(changeAgeBinding.getRoot())
                    .create();

            changeAgeBinding.btcancelAge.setOnClickListener(v -> {
                changeAgeDialog.cancel();
            });


        }
        changeAgeBinding.btokayAge.setOnClickListener(v -> {
            String newAge= changeAgeBinding.etChangeAge.getText().toString();
            if (gender ==Gender.MALE){
                binding.tvage.setText("♂"+newAge);
            }
            else{
                binding.tvage1.setText("♀"+newAge);
            }
            Database.saveAge(this,Database.AGE,ageInt);
            changeAgeDialog.cancel();
        });
        changeAgeDialog.show();
    }

    private void showChaangeNameDialog(Gender gender) {


        if (changeNameDialog ==null){
            changeNameBinding=ChangeNameBinding.inflate(getLayoutInflater());
            changeNameDialog =new AlertDialog.Builder(this)
                    .setView(changeNameBinding.getRoot())
                    .create();

            changeNameBinding.btcancel.setOnClickListener(v -> {
                changeNameDialog.cancel();
            });


        }

        if(gender == Gender.MALE){
            changeNameBinding.etlChangeName.setHint("Enter Mr name");
            changeNameBinding.etChangeName.setText(binding.tv111Male.getText().toString());
        }else{
            changeNameBinding.etlChangeName.setHint("Enter Mrs name");
            changeNameBinding.etChangeName.setText(binding.tv111Female.getText().toString());
        }


        changeNameBinding.btokay.setOnClickListener(v -> {
            String newName= changeNameBinding.etChangeName.getText().toString();
            if (gender ==Gender.MALE){
                binding.tv111Male.setText(newName);
            }
            else{
               binding.tv111Female.setText(newName);
            }
            Database.saveName(this,gender.toString(),newName);
            changeNameDialog.cancel();
        });
        changeNameDialog.show();
//        if ((changeNameDialog ==null)){
//            changeNameDialog=new AlertDialog.Builder(this)
//                    .setTitle("Sample Dialog")
//                    .setMessage("I am a dialog")
//                    .setPositiveButton("ok", (dialog, which) -> {
//                        String message=gender == Gender.MALE ? "Male" :"Femlae";
//                        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
//                        dialog.cancel();
//                    })
//                    .setNegativeButton("Cancel", (dialog, which) -> {
//                        dialog.cancel();
//                    })
//                    .create();
//        }
//
//        changeNameDialog.show();
    }

    @SuppressLint("NewApi")
    private void showDatePicker() {

        if(datePickerDialog==null){
            datePickerDialog=new DatePickerDialog(this);
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                startDate = LocalDate.of(year,month +1 ,dayOfMonth);
                updateUni();
                periodDate();
                Database.saveDate(this,Database.Date,startDate.toEpochDay());
            });
        }

         datePickerDialog.show();


    }

    private void periodDate() {
//        binding.btDate.setOnClickListener(v -> {
            Period period=Period.between(startDate,LocalDate.now());
            String years=String.valueOf(period.getYears());
            String months=String.valueOf(period.getMonths());
            String days=String.valueOf(period.getDays());
//            binding.tvDays.setText(years+"y"+months+"m"+days+"d");
            binding.tvYear.setText(years);
            binding.tvMonth.setText(months);
            binding.tvDayss.setText(days);

//        });
//        updateUni();

    }



    private void updateUni() {
        binding.tvText2.setText("Since "+DateTimeFormatter.ofPattern("MM.dd.yy").format(startDate));
//        binding.btDate.setText(DateTimeFormatter.ofPattern("MMM, dd ,yyyy").format(startDate));
        binding.btDate.setText("Edit Date");
        long days= LocalDate.now().toEpochDay() - startDate.toEpochDay();//show total of days
        binding.tvDays.setText(days+" Days");

    }



}