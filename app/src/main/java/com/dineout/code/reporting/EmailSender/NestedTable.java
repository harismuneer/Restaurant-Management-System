package com.dineout.code.reporting.EmailSender;//
//import android.content.Context;
//import android.content.Intent;
//import android.hardware.camera2.TotalCaptureResult;
//import android.net.Uri;
//import android.os.Environment;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.GrayColor;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.*;
//
//
//public class NestedTable
//{
//    public  String DEST;
//    public  String TypeOfReport;
//    public  int numberOfColumns;
//    public  int[] columnWidths;
//    public  String[] columnHeaders;
//    private ArrayList<TableRow> trows= new ArrayList<TableRow>();
//    private int[] Totals;//total sactual price sales, profit
//
//    public NestedTable(String dest, String typeOfReport, int numberOfColumns, int[] columnWidths, String[] columnHeaders, ArrayList<TableRow> trows, int[] totals) {
//        DEST = dest;
//        TypeOfReport= typeOfReport;
//        this.numberOfColumns=numberOfColumns;
//        this.columnWidths=columnWidths;
//        this.columnHeaders= columnHeaders;
//        this.trows=trows;
//        this.Totals=totals;
//
//    }
//
//    static void call(Context context, String DEST, String typeOfReport, int numberOfColumns, int[] columnWidths, String[] columnHeaders, ArrayList<TableRow> trows, int[] totals){
//        try {
//            new NestedTable(DEST,  typeOfReport, numberOfColumns, columnWidths,columnHeaders, trows,totals).createPdf( context, DEST);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//    public void createPdf(Context c,String dest) throws IOException, DocumentException {
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
//        document.close();
//    }
//}