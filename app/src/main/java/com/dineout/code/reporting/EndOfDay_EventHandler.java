package com.dineout.code.reporting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.dineout.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class EndOfDay_EventHandler
{

    private Context context;
    private String email = "haris.muneer5@gmail.com";

    private String GetHTML(int total_sales, int total_tax, int total_loss, int total_profit)
    {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "    font-family: arial, sans-serif;\n" +
                "    border-collapse: collapse;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "    border: 1px solid #dddddd;\n" +
                "    text-align: left;\n" +
                "    padding: 8px;\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "    background-color: #dddddd;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h3><strong>Sales Report</strong></h3>\n" +
                "\n" +
                "<table>\n" +
                "  <tr style=\"color:black\">\n" +
                "    <th >Report</th>\n" +
                "    <th>Values</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <b><td>Total Sales</td></b>\n" +
                "    <td>"+total_sales+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Total Profit</td>\n" +
                "    <td>"+total_profit+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Total Tax paid</td>\n" +
                "    <td>"+total_tax+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Total Loss</td>\n" +
                "    <td>"+total_loss+"</td>\n" +
                "\n" +
                "  </tr>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Total Expenditure</td>\n" +
                "    <td>"+(total_sales-(total_profit+total_tax))+"</td>\n" +
                "\n" +
                "  </tr>\n" +
                "  \n" +
                "  \n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
        return html;
    }


    public EndOfDay_EventHandler(Context c, String owner_email)

    {
        if(owner_email == null)
        {
            email = "hassaan.elahi15@gmail.com";
        }
        else
        {
            email = owner_email;
        }

        this.context = c;
    }

    final double TAX_PERCENT = 0.15;
    final double PROFIT_PERCENT = 0.45;


    public int GetWeekDayIndex(String w)
    {
        if("Monday".equals(w))
        {
            return 3;
        }
        else if("Tuesday".equals(w))
        {
            return 4;
        }
        else if("Wednesday".equals(w))
        {
            return 5;
        }
        else if("Thursday".equals(w))
        {
            return 6;
        }
        else if("Friday".equals(w))
        {
            return 7;
        }
        else if("Saturday".equals(w))
        {
            return 1;
        }
        else
        {
            return 2;
        }

    }


    public void createPDF(int type, int total_sales, int total_tax, int total_loss, int total_profit)
    {

        String[] columnHeaders= new String[]{"Total Sales ",String.valueOf(total_sales),"Taxes ",String.valueOf(total_tax)," Losses "
                ,String.valueOf(total_loss)," Profit",String.valueOf(total_profit)};
        Date date=new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int week= cal.get(Calendar.DAY_OF_WEEK);

        File file = new File(context.getFilesDir(),"Report.pdf");
        Document document = new Document();
        PdfWriter pdf = null;
        try {
            pdf= PdfWriter.getInstance(document, new FileOutputStream(file,false));
            document.open();
            addHeader(pdf,document);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PdfPTable table = new PdfPTable(2);
        table.setSplitLate(false);
        try {
            table.setWidths(new int[]{60,60});

            table.getDefaultCell().setBackgroundColor(BaseColor.CYAN);
            table.setWidthPercentage(100f);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Paragraph title = new Paragraph();
            title.setAlignment(Element.ALIGN_CENTER);
            Font fp = new Font(Font.FontFamily.HELVETICA, 17, Font.NORMAL, GrayColor.RED);
            fp.setColor(BaseColor.WHITE);
            //yearly report
            if(type == 1)
            {
                // Add the first header row. It says Sales Report
                Font f = new Font(Font.FontFamily.HELVETICA, 17, Font.NORMAL, GrayColor.GRAYWHITE);
                f.setColor(BaseColor.WHITE);
                PdfPCell cell = new PdfPCell(new Phrase("Yearly Sales Report", f));
                cell.setBackgroundColor(BaseColor.BLACK);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setColspan(2);
                cell.setFixedHeight(50);
                table.addCell(cell);
                cell.setPhrase(new Phrase("Year: "+String.valueOf(year) ,fp));
                cell.setBackgroundColor(BaseColor.BLACK);
                cell.setFixedHeight(25);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                table.addCell(cell);

            }
            //monthly report
            else if (type == 2)
            {
                Font f = new Font(Font.FontFamily.HELVETICA, 17, Font.NORMAL, GrayColor.GRAYWHITE);
                f.setColor(BaseColor.WHITE);
                PdfPCell cell = new PdfPCell(new Phrase("Monthly Sales Report", f));
                cell.setBackgroundColor(BaseColor.BLACK);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setColspan(2);
                cell.setFixedHeight(50);
                table.addCell(cell);
                cell.setPhrase(new Phrase("Month: "+getMonth(month) ,fp));
                cell.setBackgroundColor(BaseColor.BLACK);
                cell.setFixedHeight(25);

                cell.setVerticalAlignment(Element.ALIGN_TOP);
                table.addCell(cell);
            }
            //weekly report
            else if(type == 3)
            {
                Font f = new Font(Font.FontFamily.HELVETICA, 17, Font.NORMAL, GrayColor.GRAYWHITE);
                f.setColor(BaseColor.WHITE);
                PdfPCell cell = new PdfPCell(new Phrase("Weekly Sales Report", f));
                cell.setBackgroundColor(BaseColor.BLACK);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setColspan(2);
                cell.setFixedHeight(50);
                table.addCell(cell);
                cell.setPhrase(new Phrase("Week: "+String.valueOf(week) ,fp));
                cell.setBackgroundColor(BaseColor.BLACK);
                cell.setFixedHeight(25);

                cell.setVerticalAlignment(Element.ALIGN_TOP);
                table.addCell(cell);
            }
            //daily report
            else
            {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = new Date();
                System.out.println(formatter.format(date1));
                Font f = new Font(Font.FontFamily.HELVETICA, 17, Font.NORMAL, GrayColor.GRAYWHITE);
                f.setColor(BaseColor.WHITE);
                PdfPCell cell = new PdfPCell(new Phrase("Daily Sales Report", f));
                cell.setBackgroundColor(BaseColor.BLACK);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setColspan(2);
                cell.setFixedHeight(50);
                table.addCell(cell);
                cell.setPhrase(new Phrase(formatter.format(date1) ,fp));
                cell.setBackgroundColor(BaseColor.BLACK);
                cell.setFixedHeight(25);

                cell.setVerticalAlignment(Element.ALIGN_TOP);
                table.addCell(cell);

            }
            PdfPCell cell;
            //second row has header for columns like dish name prce
            for (int i = 0; i < 8; i++) {
                if(i%2==0) {
                    Font f = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, GrayColor.GRAYWHITE);
                    f.setColor(BaseColor.BLACK);

                    cell = new PdfPCell(new Phrase(columnHeaders[i], f));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                    cell.setFixedHeight(25);
                    table.addCell(cell);
                }
                else{
                    table.getDefaultCell().setBackgroundColor(BaseColor.PINK);
                    Font f = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, GrayColor.GRAYWHITE);
                    f.setColor(BaseColor.BLACK);
                    cell = new PdfPCell(new Phrase(columnHeaders[i].toString(), f));
                    cell.setBackgroundColor(new BaseColor(255,240,245));
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                    cell.setFixedHeight(25);
                    table.addCell(cell);
                }
            }
            //table.writeSelectedRows(0,-1,34,806,pdf.getDirectContent());
            document.add(table);
            document.close();

        } catch (DocumentException e) {
            System.out.println("hsjsjs");
            e.printStackTrace();
        }





    }


    private String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
        }
        return day;
    }
    private void addHeader(PdfWriter writer, Document document){
        PdfPTable header = new PdfPTable(2);
        System.out.println("ok1 here");

        try {
            // set defaults
            header.setWidths(new int[]{2, 24});
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            Drawable d = context.getDrawable(R.drawable.logo);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            // add image]
            header.addCell(image);
            // add text
            PdfPCell text = new PdfPCell();
            text.setPaddingBottom(15);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.BOTTOM);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Phrase("DineOut-The Restaurant Of Tommorrrow", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("https://dineout.com", new Font(Font.FontFamily.HELVETICA, 8)));
            header.addCell(text);
            document.add(header);
            // write content
            //   header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());

            System.out.println("ok here");


        } catch(DocumentException de) {
            System.out.println("not here");

            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            System.out.println("sjjds here");

            throw new ExceptionConverter(e);
        } catch (IOException e) {
            System.out.println("oksnsnere");

            throw new ExceptionConverter(e);
        }
    }

    private String getMonth(int month) {
        String[] months = new DateFormatSymbols().getMonths();
        return months[month-1];
    }


    public void SendMail(int type, int total_sales, int total_tax, int total_loss,int total_profit)
    {


        createPDF(1,78,10,20,25);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "This is an automatic mail",GetHTML(total_sales,total_tax,total_loss,2415) ,"dineoutx@gmail.com", "shafaq.arshad15@gmail.com");
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }


    }


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
                total_profits += PROFIT_PERCENT * (((order)pair.getValue()).total_amount - (TAX_PERCENT * ((order)pair.getValue()).total_amount));
                total_loss += ((order)pair.getValue()).loss;
                it.remove();
            }
        }

        createPDF(1,total_sales,total_tax,total_loss,total_profits);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "This is an automatic Email",GetHTML(total_sales,total_tax,total_loss,total_profits), "dineoutx@gmail.com", email);
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
                total_profits += PROFIT_PERCENT * (((order)pair.getValue()).total_amount - (TAX_PERCENT * ((order)pair.getValue()).total_amount));
                total_loss += ((order)pair.getValue()).loss;
                it.remove();
            }
        }
        createPDF(1,total_sales,total_tax,total_loss,total_profits);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "This is an automatic Email",GetHTML(total_sales,total_tax,total_loss,total_profits), "dineoutx@gmail.com", email);
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
                total_profits += PROFIT_PERCENT * (((order)pair.getValue()).total_amount - (TAX_PERCENT * ((order)pair.getValue()).total_amount));
                total_loss += ((order)pair.getValue()).loss;
                it.remove();
            }
        }

        createPDF(3,total_sales,total_tax,total_loss,total_profits);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "This is an automatic Email Generated from Dineout system",GetHTML(total_sales,total_tax,total_loss,total_profits), "dineoutx@gmail.com", email);
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
                total_profits += PROFIT_PERCENT * (((order)pair.getValue()).total_amount - (TAX_PERCENT * ((order)pair.getValue()).total_amount));
                total_loss += ((order)pair.getValue()).loss;
                it.remove();
            }
        }

        createPDF(4,total_sales,total_tax,total_loss,total_profits);
        GMailSender sender = new GMailSender("dineoutx@gmail.com", "ShafaqAirlines78");
        try
        {
            sender.addAttachment(context.getFilesDir() + "/" + "Report.pdf");
            sender.sendMail("Sales Report", "This is an automatic Email Generated From Dineout system. Sales report for today dated: "+today.getDate()+" is Attached",GetHTML(total_sales,total_tax,total_loss,total_profits), "dineoutx@gmail.com", email);
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }


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
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    //creating a map of DishName -> Price
                    dish_prices.put((String) userSnapshot.child("dishName").getValue(), Integer.parseInt((String)userSnapshot.child("price").getValue()));
                }




                //getting all orders
                Query order_query = ref.child("Order");
                order_query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                        {
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
                                                o.loss = o.loss + (int)(dish_prices.get(dish_name) - (dish_prices.get(dish_name)*PROFIT_PERCENT));
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




                                Query end_week_query = ref.child("EndOfWeek");
                                end_week_query.addListenerForSingleValueEvent(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        end_of_week[0] = (String)dataSnapshot.getValue();
                                 /*Here we are checking which report to send
                                * for example weekly, monhtly, */
                                        Date current_date = new Date();
                                        Calendar calendar = Calendar.getInstance();
                                        int weekday = GetWeekDayIndex(end_of_week[0]);

                                        int last_day_ofmonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                                        boolean awain=false;
                                        if ((current_date.getDay() == last_day_ofmonth && current_date.getMonth() == 12))
                                        {
                                            YearlyReport(time_stamp_order);
                                        } else if (awain||current_date.getDay() == last_day_ofmonth)
                                        {
                                            MonthlyReport(time_stamp_order);
                                        } else if (awain && dayOfWeek == weekday)
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
                            @Override
                            public void onCancelled (DatabaseError databaseError){
                                System.out.println("The read failed: " + databaseError.getCode());
                            }

                        });











                    }
                    @Override
                    public void onCancelled (DatabaseError databaseError){
                        System.out.println("The read failed: " + databaseError.getCode());
                    }

                });









            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });









    }

}


