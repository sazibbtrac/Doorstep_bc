package com.btracsolution.deliverysystem.Utility;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.BillHistoryActivity;
import com.btracsolution.deliverysystem.Model.BillHistoryResponse;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PrintUtility {

    public static void printKitchen(BluetoothSocket mBluetoothSocket, OrderDetailsModel.orderBasicData OrderList) {
        /* Get time and date */
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final String formattedDateTime = df.format(c.getTime());
        final String formattedDate = df.format(c.getTime());
        try {
            OutputStream os = mBluetoothSocket
                    .getOutputStream();
            String header = "",end="";
            String he = "";
            String blank = "";
            String item = "";
            String header2 = "";
            String BILL = "";
            String vio = "";
            String mvdtail = "";
            String header4 = "";
            String offname = "";
            String remarks = "";
            String servedby = "";
            String checktop_status = "";

            blank = "\n\n        Banani Club\n";
            // he = he+ "\t Banani Club \n";
            he = he + "        Banani Dhaka\n\n";
            he = he + "A/C No: "+OrderList.getMemberId()+"\n";
            he = he + "ORDER FROM\n";
            he = he + "--------------------------------\n";

            header = "No:"+OrderList.getOrdernumber()+"\t "+formattedDate+"\n";
            header = header+"----------------------------\n";

            item= getOrderItems(OrderList);


            remarks=setRemarks(OrderList);
            servedby="\n Served by: "+OrderList.getWaiter().getFullName()+"\n";

            end=end+"\n\n--------------------------------";


            BILL = "Member Details" + "\n";
            BILL = BILL+"Id: "+OrderList.getMember().getMemberId() + "\n";
            BILL = BILL+"Name: "+OrderList.getMember().getName() + "\n";
            BILL = BILL+ "================================\n";
            header2 = "COMPANY'S NAME:\n";
            vio = "B-Trac Solutions" + "\n";
            vio = vio
                    + "================================\n";
            mvdtail = "28" + "\n";
            mvdtail = mvdtail
                    + "================================\n";

            header4 = "AGENT DETAILS:\n";
            offname = "Kitchen Banani Club" + "\n";
            offname = offname
                    + "--------------------------------\n";
            String time = formattedDate + "\n\n";
            //copy = "-Agents's Copy\n\n\n\n\n\n\n";

            os.write(blank.getBytes());
            os.write(he.getBytes());
            os.write(header.getBytes());
            os.write(item.getBytes());
            os.write(remarks.getBytes());

            os.write(servedby.getBytes());
            os.write(end.getBytes());
//            os.write(header2.getBytes());
//            os.write(vio.getBytes());
//            os.write(header3.getBytes());
//            os.write(mvdtail.getBytes());
//            os.write(header4.getBytes());
//            os.write(offname.getBytes());
//            os.write(checktop_status.getBytes());
//            os.write(time.getBytes());
//            os.write(copy.getBytes());


            //This is printer specific code you can comment ==== > Start

            // Setting height
            int gs = 29;
            os.write(intToByteArray(gs));
            int h = 150;
            os.write(intToByteArray(h));
            int n = 170;
            os.write(intToByteArray(n));

            // Setting Width
            int gs_width = 29;
            os.write(intToByteArray(gs_width));
            int w = 119;
            os.write(intToByteArray(w));
            int n_width = 2;
            os.write(intToByteArray(n_width));


        } catch (Exception e) {
            Log.e("PrintActivity", "Exe ", e);
        }
    }

    private static String setRemarks(OrderDetailsModel.orderBasicData orderBasicData) {
        String remarks="Remarks: \n";
        int count=0;

        try {
            for (int i=0;i<orderBasicData.getOrderitem().size();i++){
                if (orderBasicData.getOrderitem().get(i).getItemRemarks() == null || orderBasicData.getOrderitem().get(i).getItemRemarks().length() == 0) {
                    // tx_special_instruction.setVisibility(View.GONE);
                } else {
                    remarks = remarks + orderBasicData.getOrderitem().get(i).getFoodinfo().getFoodname()+" - "+orderBasicData.getOrderitem().get(i).getItemRemarks() + "\n";
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (count<1){
            remarks="";
        }else{

        }

        return remarks;

    }


    public static void printBill(BluetoothSocket mBluetoothSocket, OrderDetailsModel orderDetailsModel, Context context,String payMethdo,String total) {
        String payMode=getPaymentMode(payMethdo);
        String zoneName="";
        try {
             zoneName=orderDetailsModel.getOrderList().get(0).getDeliveryzone().getZonename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Get time and date */
        SharedData sharedData=new SharedData(context);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final String formattedDateWithoutTime = dfDate.format(c.getTime());
        final String formattedDate = df.format(c.getTime());
        try {
            OutputStream os = mBluetoothSocket
                    .getOutputStream();
            String header = "";
            String he = "";
            String blank = "";
            String item = "",email="",printedOn="",preaparedBy="",servedBy="",signature="",end="";
            String header2 = "";
            String BILL = "";
            String vio = "";
            String header3 = "";
            String mvdtail = "";
            String header4 = "";
            String offname = "";
            String mode = "";
            String checktop_status = "";



            blank = "**********\n\n";
            // he = he+ "\t Banani Club \n";
            he = he + "      Banani Club Limited\n";
            he = he + "            Invoice\n";
            he = he + "          "+zoneName+"\n";
            he = he + "--------------------------------\n\n";

            try {
                header = "Ac No: "+orderDetailsModel.getOrderList().get(0).getMember().getMemberId()+"\nInvoice: "+orderDetailsModel.getOrderList().get(0).getOrdernumber()+"\n";
            } catch (Exception e) {
                e.printStackTrace();
            }
            // header = header+"----------------------------\n";

            offname=offname+"Name: "+orderDetailsModel.getOrderList().get(0).getMember().getName()+"\n";

            email=email+"Phone: "+orderDetailsModel.getOrderList().get(0).getMember().getContactno()+"\n";

            printedOn = printedOn+"printed on: "+formattedDate+"\n";
            mode = mode+"Mode: "+payMode+"\n";


            item=getOrderItemsForWaiter(orderDetailsModel,total);

            preaparedBy=preaparedBy+"Preapared By: "+sharedData.getMyData().getData().getFullName()+"\n";
            try {
               // servedBy=servedBy+"Served By: "+orderDetailsModel.getOrderList().get(0).getWaiter().getFullName();
                servedBy=servedBy+"Served By: "+sharedData.getMyData().getData().getFullName()+"\n\n\n";
            } catch (Exception e) {
                e.printStackTrace();
            }

            signature=signature+"\n\n\t\t_______________\n\t     Signature of Member\n";
            end=end+"\n\n--------------------------------";

            os.write(blank.getBytes());
            os.write(he.getBytes());
            os.write(header.getBytes());
            os.write(offname.getBytes());
            os.write(email.getBytes());
            os.write(printedOn.getBytes());
            os.write(mode.getBytes());
            os.write(item.getBytes());
//            os.write(preaparedBy.getBytes());
            os.write(servedBy.getBytes());
            os.write(signature.getBytes());
            os.write(end.getBytes());


            //This is printer specific code you can comment ==== > Start

            // Setting height
            int gs = 29;
            os.write(intToByteArray(gs));
            int h = 150;
            os.write(intToByteArray(h));
            int n = 170;
            os.write(intToByteArray(n));

            // Setting Width
            int gs_width = 29;
            os.write(intToByteArray(gs_width));
            int w = 119;
            os.write(intToByteArray(w));
            int n_width = 2;
            os.write(intToByteArray(n_width));


        } catch (Exception e) {
            Log.e("PrintActivity", "Exe ", e);
        }
    }

    private static String getPaymentMode(String payMethod) {
        String payMode="";
        try {
            if (payMethod.contentEquals("1")){
                payMode="CashCard";
            }else if(payMethod.contentEquals("2")){
                payMode="Card";
            }
            else if (payMethod.contentEquals("3")){
                payMode="Credit";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payMode;
    }


    private static String getOrderItems(OrderDetailsModel.orderBasicData orderList) {

        String item="";
        item="DOORSTEP \n";
        item= item+"Qty \t\t items\n";
        for (int i=0; i<orderList.getOrderitem().size();i++){
            item=item+ orderList.getOrderitem().get(i).getQuantity()+"\t\t"+orderList.getOrderitem().get(i).getFoodinfo().getFoodname()+"\n";
        }
        item=item+"----------------------------\n\n";

        return item;
    }



    private static String getOrderItemsForWaiter(OrderDetailsModel orderDetailsModel,String total) {

        ArrayList<OrderDetailsModel.orderBasicData> orderList = new ArrayList<>();
        orderList.clear();
        orderList.addAll(orderDetailsModel.getOrderList());
        String item="";
        item="\n\n";
        //item= item+"Qty \t\t items\n";
        for (int j=0;j<orderList.size();j++) {
            if (orderList.get(j).isOrderChecked()) {
                for (int i = 0; i < orderList.get(j).getOrderitem().size(); i++) {
                    item = item + orderList.get(j).getOrderitem().get(i).getFoodinfo().getFoodname() + "\n" + orderList.get(j).getOrderitem().get(i).getQuantity() + "x" + orderList.get(j).getOrderitem().get(i).getPrice() + "\t\t" + orderList.get(j).getOrderitem().get(i).getTotalprice() + "\n\n";
                }
            }
        }
        item=item+"Total\t\t"+total+"\n";
        item=item+"----------------------------\n\n";

        return item;
    }
    public static String getOrderItemsForWaiterV2(ArrayList<OrderDetailsModel.orderBasicData> orderList) {

       // orderList.clear();
      //  orderList.addAll(orderDetailsModel.getOrderList());
        String item="";
        item="\n\n";
        //item= item+"Qty \t\t items\n";
        for (int j=0;j<orderList.size();j++) {
            for (int i = 0; i < orderList.get(j).getOrderitem().size(); i++) {
                String foodName=getFormattedString(orderList.get(j).getOrderitem().get(i).getFoodinfo().getFoodname());
                String space=getSpace(foodName);
                item = item + foodName + space + orderList.get(j).getOrderitem().get(i).getQuantity() + "  " + orderList.get(j).getOrderitem().get(i).getPrice() + "  " + orderList.get(j).getOrderitem().get(i).getTotalprice() + "\n\n";
            }
        }
      //  item=item+"Total\t\t"+orderDetailsModel.getBillDetails().getTotalamount()+"\n";
        item=item+"----------------------------\n\n";

        System.out.println(item);
        return item;
    }


    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + byteToHex(b[k]));
        }

        return b[3];
    }
    static public String byteToHex(byte b) {
        // Returns hex String representation of byte b
        char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
        return new String(array);
    }

    static public String charToHex(char c) {
        // Returns hex String representation of char c
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }
    private static String getSpace(String foodName) {
        String space="";
        if (foodName.contains("\n")) {
            String[] works = foodName.split("\n");
            String lastName=works[works.length-1];

            int afIndex=16-lastName.length();
            for(int i =0; i<afIndex;i++){
                space=space+" ";
            }
        }else{
            if (foodName.length()<16){
                int afIndex=16-foodName.length();
                for(int i =0; i<afIndex;i++){
                    space=space+" ";
                }
            }
        }
        return space;
    }
    static public String getFormattedString(String text) {

//        String text = "Burger King, Dhaka Bangladesh. Testing test.";

        String[] works = text.split(" ");
        StringBuilder line = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (String work : works) {
            if (line.length() + work.length() > 15) {
                result.append(line).append("\n");
                line = new StringBuilder();
            }
            line.append(work).append(" ");
        }
        result.append(line);
        return result.toString();
    }

    public static void printBillHistory(BluetoothSocket mBluetoothSocket, BillHistoryResponse.Datum billHistoryData, Context context) {
        String payMode=getPaymentMode(String.valueOf(billHistoryData.getPaymentmethod()));
        String zoneName="";
        try {
            zoneName=billHistoryData.getBillitem().get(0).getOrder().get(0).getDeliveryzone().getZonename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Get time and date */
        SharedData sharedData=new SharedData(context);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final String formattedDateWithoutTime = dfDate.format(c.getTime());
        final String formattedDate = df.format(c.getTime());
        try {
            OutputStream os = mBluetoothSocket
                    .getOutputStream();
            String header = "";
            String he = "";
            String blank = "";
            String item = "",email="",printedOn="",preaparedBy="",servedBy="",signature="",end="";
            String header2 = "";
            String BILL = "";
            String vio = "";
            String header3 = "";
            String mvdtail = "";
            String header4 = "";
            String offname = "";
            String mode = "";
            String checktop_status = "";



            blank = "**********\n\n";
            // he = he+ "\t Banani Club \n";
            he = he + "      Banani Club Limited\n";
            he = he + "            Invoice\n";
            he = he + "          "+zoneName+"\n";
            he = he + "--------------------------------\n\n";

            try {
               // header = "Ac No: "+billHistoryData.getMemberId()+"\n";
                header = "Ac No: "+billHistoryData.getMemberId()+"\nInvoice: "+billHistoryData.getBillitem().get(0).getOrder().get(0).getOrdernumber()+"\n";
            } catch (Exception e) {
                e.printStackTrace();
            }
            // header = header+"----------------------------\n";

            offname=offname+"Name: "+billHistoryData.getMember().getName()+"\n";

            email=email+"Phone: "+billHistoryData.getMember().getContactno()+"\n";

            printedOn = printedOn+"printed on: "+formattedDate+"\n";
            mode = mode+"Mode: "+payMode+"\n";


            item=getOrderItemsFromBillHistory(billHistoryData);

            preaparedBy=preaparedBy+"Preapared By: "+sharedData.getMyData().getData().getFullName()+"\n";
            try {
                // servedBy=servedBy+"Served By: "+orderDetailsModel.getOrderList().get(0).getWaiter().getFullName();
                servedBy=servedBy+"Served By: "+sharedData.getMyData().getData().getFullName()+"\n\n\n";
            } catch (Exception e) {
                e.printStackTrace();
            }

            signature=signature+"\n\n\t\t_______________\n\t     Signature of Member\n";
            end=end+"\n\n--------------------------------";

            os.write(blank.getBytes());
            os.write(he.getBytes());
            os.write(header.getBytes());
            os.write(offname.getBytes());
            os.write(email.getBytes());
            os.write(printedOn.getBytes());
            os.write(mode.getBytes());
            os.write(item.getBytes());
//            os.write(preaparedBy.getBytes());
            os.write(servedBy.getBytes());
            os.write(signature.getBytes());
            os.write(end.getBytes());


            //This is printer specific code you can comment ==== > Start

            // Setting height
            int gs = 29;
            os.write(intToByteArray(gs));
            int h = 150;
            os.write(intToByteArray(h));
            int n = 170;
            os.write(intToByteArray(n));

            // Setting Width
            int gs_width = 29;
            os.write(intToByteArray(gs_width));
            int w = 119;
            os.write(intToByteArray(w));
            int n_width = 2;
            os.write(intToByteArray(n_width));


        } catch (Exception e) {
            Log.e("PrintActivity", "Exe ", e);
        }
    }

    private static String getOrderItemsFromBillHistory( BillHistoryResponse.Datum billHistoryData) {
        ArrayList<BillHistoryResponse.Billitem> billitemArrayList=billHistoryData.getBillitem();
        ArrayList<BillHistoryResponse.Orderitem> orderList = new ArrayList<>();
        orderList.clear();
        for (int i=0;i<billitemArrayList.size();i++) {
            orderList.addAll(billitemArrayList.get(i).getOrderitem());

        }
        String item="";
        item="\n\n";
        //item= item+"Qty \t\t items\n";
        for (int j=0;j<orderList.size();j++) {
                item = item + orderList.get(j).getFoodinfo().getFoodname() + "\n" + orderList.get(j).getQuantity() + "x" + orderList.get(j).getPrice() + "\t\t" + orderList.get(j).getTotalprice() + "\n\n";

        }
        item=item+"Total\t\t"+billHistoryData.getTotalpaid()+"\n";
        item=item+"----------------------------\n\n";

        return item;
    }
}
