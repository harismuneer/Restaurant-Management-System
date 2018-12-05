package com.dineout.code.reporting;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class EndOfDay_EventHandler {
    private Context context;

    public EndOfDay_EventHandler(Context c) {
        this.context = c;
    }

    final double TAX_PERCENT = 0.1;
    final double PROFIT_PERCENT = 0.1;

    public void createPDF(int type, int total_sales, int total_tax, int total_loss, int total_profit)
    {
        Date today = new Date();
        File report_file = new File(context.getFilesDir(), "Report.pdf");
        Document document = new Document();
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream(report_file, false));
            document.open();
            Paragraph p = new Paragraph();

            //yearly report
            if(type == 1)
            {
                p.add("Yearly Report for Year "+today.getYear());

            }
            //monthly report
            else if (type == 2)
            {
                p.add("Monthly Report for Month "+today.getMonth());
            }
            //weekly report
            else if(type == 3)
            {
                p.add("Weekly Report");
            }
            //daily report
            else
            {
                p.add("Daily Report for Date "+today.getDate());
            }
            p.add("\n");
            p.add("Total Sales:    "+ total_sales);
            p.add("Total Profit:   "+ total_profit);
            p.add("Total Tax paid: "+total_tax);
            p.add("Total Loss:     "+total_loss);

            document.add(p);



            /*
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100f);
            PdfPCell cell = new PdfPCell(new Phrase("Sales Report"));
            table.addCell(cell);
            table.setSplitLate(false);
            //second colunm is 3 times 1st column
            table.setWidths(new float[] { 1, 3 });
            table.getDefaultCell().setBackgroundColor(BaseColor.CYAN);
            table.setWidthPercentage(100f);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
        */

            document.close();
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
            System.out.print(e.getMessage());
        }


    }



    public void SendMail(int type, int total_sales, int total_tax, int total_loss,int total_profit)
    {


        createPDF(1,78,10,20,25);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "", "dineoutx@gmail.com", "hassaan.elahi15@gmail.com");
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }


    }





//    public void createPdf(Context c, String dest) throws IOException, DocumentException {
//
//        File file1 = new File(c.getFilesDir(),"files");
//        if(!file1.exists())
//        {
//            file1.mkdir();
//        }
//        else
//        {
//            System.out.println("File already exists");
//        }
//
//        File file = new File(file1, DEST);
//        //System.out.println(file.getPath());
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream(file,false));
//        document.open();
//        PdfPTable table = new PdfPTable(numberOfColumns);
//        table.setSplitLate(false);
//        table.setWidths(columnWidths);
//        table.getDefaultCell().setBackgroundColor(BaseColor.CYAN);
//        table.setWidthPercentage(100f);
//        table.getDefaultCell().setUseAscender(true);
//        table.getDefaultCell().setUseDescender(true);
//
//        // Add the first header row. It says Sales Report
//        Font f = new Font(Font.FontFamily.HELVETICA, 17, Font.NORMAL, GrayColor.GRAYWHITE);
//        f.setColor(BaseColor.WHITE);
//        PdfPCell cell = new PdfPCell(new Phrase(TypeOfReport, f));
//        cell.setBackgroundColor(BaseColor.BLACK);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setColspan(numberOfColumns);
//        cell.setFixedHeight(50);
//        table.addCell(cell);
//        //second row has header for columns like dish name prce
//        for (int i = 0; i < numberOfColumns; i++) {
//            cell = new PdfPCell(new Phrase(columnHeaders[i], f));
//
//            cell.setBackgroundColor(BaseColor.GRAY);
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(cell);
//        }
//        table.getDefaultCell().setBackgroundColor(BaseColor.PINK);
//        table.setHeaderRows(2);
//        f = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, GrayColor.GRAYWHITE);
//        f.setColor(BaseColor.BLACK);
//        //Our Row data goes here
//        for (TableRow rows:trows) {
//            for (int i=0;i<numberOfColumns;i++) {
//                cell = new PdfPCell(new Phrase(rows.cells[i].toString(), f));
//                cell.setBackgroundColor(BaseColor.PINK);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//            }
//        }
//        //                new int[]{60, 30,30,30};
//        int[] innerwidths=new int[Totals.length+1];
//        innerwidths[0]=0;
//        for(int i=0;i<columnWidths.length-Totals.length;i++){
//            innerwidths[0]+=columnWidths[i];
//        }
//        for(int i=1;i<=Totals.length;i++){
//            innerwidths[i]= columnWidths[columnWidths.length-Totals.length+i-1];
//        }
//        PdfPTable innertable = new PdfPTable(Totals.length+1);
//        innertable.setWidths(innerwidths);
//        innertable.setWidthPercentage(100f);
//        cell = new PdfPCell(new Phrase("Total", f));
//        cell.setBackgroundColor(BaseColor.PINK);
//
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        innertable.addCell(cell);
//        f = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, GrayColor.GRAYWHITE);
//        f.setColor(BaseColor.BLACK);
//        for (int i = 0; i <Totals.length; i++) {
//
//            cell.setPhrase(new Phrase(Integer.toString(Totals[i])));
//            cell.setBackgroundColor(BaseColor.CYAN);
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            innertable.addCell(cell);
//
//        }
//        document.add(table);
//        document.add(innertable);
//        document.close();*/
//    }
//
//
//

    public void YearlyReport(Map<Date,order> time_stamp_order)
    {
        Date today = new Date();
        int total_sales = 0;
        int total_profits = 0;
        int total_tax = 0;
        int total_loss = 0;

        Iterator it = time_stamp_order.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            if (((Date)pair.getKey()).getYear() == today.getYear())
            {

                //to be checked
                total_sales += ((order)pair.getValue()).total_amount;
                total_tax += TAX_PERCENT * ((order)pair.getValue()).total_amount;
                total_profits += PROFIT_PERCENT * ((order)pair.getValue()).total_amount;
                total_loss += ((order)pair.getValue()).loss;
                it.remove();
            }
        }

        createPDF(1,total_sales,total_tax,total_loss,total_profits);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "", "dineoutx@gmail.com", "hassaan.elahi15@gmail.com");
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }

    }

    public void MonthlyReport(Map<Date,order> time_stamp_order) {
        Date today = new Date();
        int total_sales = 0;
        int total_profits = 0;
        int total_tax = 0;
        int total_loss = 0;


        Iterator it = time_stamp_order.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            if (((Date)pair.getKey()).getMonth() == today.getMonth())
            {

                //to be checked
                total_sales += ((order)pair.getValue()).total_amount;
                total_tax += TAX_PERCENT * ((order)pair.getValue()).total_amount;
                total_profits += PROFIT_PERCENT * ((order)pair.getValue()).total_amount;
                total_loss += ((order)pair.getValue()).loss;
                it.remove();
            }
        }
        createPDF(2,total_sales,total_tax,total_loss,total_profits);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "", "dineoutx@gmail.com", "hassaan.elahi15@gmail.com");
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }



    }

    public void WeeklyReport(Map<Date,order> time_stamp_order)
    {
        Date today = new Date();
        int total_sales = 0;
        int total_profits = 0;
        int total_tax = 0;
        int total_loss = 0;



        Iterator it = time_stamp_order.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            if (((Date)pair.getKey()).getDay() <= today.getDay() && ((Date)pair.getKey()).getDay() > today.getDay() - 7)
            {

                //to be checked
                total_sales += ((order)pair.getValue()).total_amount;
                total_tax += TAX_PERCENT * ((order)pair.getValue()).total_amount;
                total_profits += PROFIT_PERCENT * ((order)pair.getValue()).total_amount;
                total_loss += ((order)pair.getValue()).loss;
                it.remove();
            }
        }

        createPDF(3,total_sales,total_tax,total_loss,total_profits);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "", "dineoutx@gmail.com", "hassaan.elahi15@gmail.com");
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }



    }


    public void DailyReport(Map<Date,order> time_stamp_order)
    {

        Date today = new Date();
        int total_sales = 0;
        int total_profits = 0;
        int total_tax = 0;
        int total_loss = 0;

        Iterator it = time_stamp_order.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            if (((Date)pair.getKey()).getDay() == today.getDay())
            {

                //to be checked
                total_sales += ((order)pair.getValue()).total_amount;
                total_tax += TAX_PERCENT * ((order)pair.getValue()).total_amount;
                total_profits += PROFIT_PERCENT * ((order)pair.getValue()).total_amount;
                total_loss += ((order)pair.getValue()).loss;
                it.remove();
            }
        }

        createPDF(4,total_sales,total_tax,total_loss,total_profits);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "", "dineoutx@gmail.com", "hassaan.elahi15@gmail.com");
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
        //Send these stats here

    }

    public void HandleCloseDayEvent()
    {

        final Map<String, Integer> dish_prices = new HashMap<>();
        final Map<Integer, Date> id_timestamp = new HashMap<>();
        // timestamp -> order(total amount, loss)
        //each timestamp denotes single order placed on the corresponding time
        final Map<Date, order> time_stamp_order = new HashMap<>();
        final String[] end_of_week = new String[1];
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();



        //getting dishes and their prices
        Query menu_query = ref.child("Menu");
        menu_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    //creating a map of DishName -> Price
                    dish_prices.put((String) userSnapshot.child("dishName").getValue(), Integer.parseInt((String)userSnapshot.child("price").getValue()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        //getting all orders
        Query order_query = ref.child("Order");
        order_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    try
                    {
                        //creating a mapping of id -> timestamp(to distinguish different reports)
                        String timeStamp = (String) userSnapshot.child("timestamp").getValue();
                        Date ts = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).parse(timeStamp);
                        id_timestamp.put(Integer.parseInt((String)userSnapshot.child("id").getValue()), ts);
                        time_stamp_order.put(ts,new order());
                    } catch (Exception e) {

                        System.out.print(e.getStackTrace());
                        System.out.print(e.getMessage());

                    }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });


        //here we will check for dishes
        Query order_detail = ref.child("OrderDetails");
        order_detail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot order_detail : dataSnapshot.getChildren())
                {

                    String dish_name = (String) order_detail.child("dishname").getValue();
                    int order_id =  Integer.parseInt((String)order_detail.child("orderid").getValue());
                    int servings =  ((Long)order_detail.child("servings").getValue()).intValue();
                    int priority = ((Long) order_detail.child("priority").getValue()).intValue();


                    //if this id exists in order also then
                    //it means this dish is part of collected order
                    if (id_timestamp.get(order_id) != null)
                    {

                        try
                        {
                            //check if this dish was wasted
                            if (priority == 1)
                            {

                                order o = time_stamp_order.get(id_timestamp.get(order_id));
                                o.loss = o.loss + dish_prices.get(dish_name);
                                time_stamp_order.put(id_timestamp.get(order_id), o);
                            }
                            else
                                {

                                order o = time_stamp_order.get(id_timestamp.get(order_id));
                                o.total_amount = o.total_amount + (servings * dish_prices.get(dish_name));
                                time_stamp_order.put(id_timestamp.get(order_id), o);
                            }
                        }
                        catch (Exception e)
                        {
                            System.out.print(e);
                        }

                    }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });


        Query end_week_query = ref.child("EndOfWeek");
        end_week_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                end_of_week[0] = (String)dataSnapshot.getValue();
                 /*Here we are checking which report to send
                * for example weekly, monhtly, */
                Date current_date = new Date();
                Calendar calendar = Calendar.getInstance();

                int last_day_ofmonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);


                if (current_date.getDay() == last_day_ofmonth && current_date.getMonth() == 12)
                {
                    YearlyReport(time_stamp_order);
                } else if (current_date.getDay() == last_day_ofmonth)
                {
                    MonthlyReport(time_stamp_order);
                } else if (dayOfWeek == 5)
                {
                    WeeklyReport(time_stamp_order);
                }
                else
                {
                    DailyReport(time_stamp_order);
                }


            }
            @Override
            public void onCancelled (DatabaseError databaseError){
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });





    }



}

