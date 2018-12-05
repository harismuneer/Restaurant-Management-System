package com.dineout.code.reporting.EmailSender;//
//import android.content.Context;
//import android.content.Intent;
//import android.net.NetworkRequest;
//import android.net.Uri;
//import android.os.Environment;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import com.itextpdf.text.DocumentException;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Properties;
//
//public class dummyActivity extends AppCompatActivity
//{
//
//    Context c=this;
//    public static String DEST = "dailyReport.pdf";
//    public static String TypeOfReport= "Daily Sales Report";
//    public int numberOfColumns=5;
//    public int[] columnWidths=new int[]{30 , 30,30 ,30 ,30};
//    public String[] columnHeaders= new String[]{"DishName","Quantity","Actual Price","Sales","profit/Loss"};
//
//    private ArrayList<TableRow> trows= new ArrayList<TableRow>();
//    private int[] Totals;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.reporting_activity_main);
//        //Logic calculation by hasan here
//        int typeOfreport=1;
//        switch (typeOfreport)
//        {
//
//
//            case 1:
//                DEST = "dailyReport.pdf";
//                TypeOfReport= "Daily Sales Report";
//                numberOfColumns=5;
//                columnWidths=new int[]{30 , 30,30 ,30 ,30};
//
//                columnHeaders= new String[]{"DishName","Quantity","Actual Price","Sales","profit/Loss"};
//                TableRow r= new TableRow(new Object[]{"lasagna",1,1,1,1});
//
//                trows.add(r);
//                Totals=new int[]{123,234,345};//total sactual price sales, profit
//                NestedTable.call(c,DEST,TypeOfReport,numberOfColumns,columnWidths,columnHeaders,trows,Totals);
//                break;
//            case 2:
//                DEST = "MonthlyReport.pdf";
//                TypeOfReport= "Monthly Sales Report";
//                numberOfColumns=4;
//                columnWidths=new int[]{30 , 30,30 ,30};
//                columnHeaders= new String[]{"Day","Sales","Expense","profit/Loss"};
//                TableRow r1= new TableRow(new Object[]{"01/01/2018",1,1,1});
//
//                trows.add(r1);
//
//                Totals=new int[]{123,234,345};//total sactual price sales, profit
//
//                NestedTable.call(c,DEST,TypeOfReport,numberOfColumns,columnWidths,columnHeaders,trows,Totals);
//                break;
//        }
//
//        try {
//
//            GMailSender sender = new GMailSender("dineoutx@gmail.com","ShafaqAirlines78");
//
//            sender.addAttachment(c.getFilesDir()+"/files/"+DEST);
//
//            sender.sendMail( TypeOfReport, "This mail has been sent from android app along with sales report...This is auto generated email please donnot reply",
//
//                    "dineoutx@gmail.com",
//
//                    "arshadshafaq15@gmail.com");
//
//
//
//
//
//
//        } catch (Exception e) {
//
//            System.out.println(e);
//
//        }
//
//    }
//    }
//
//
