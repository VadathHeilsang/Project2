import java.util.Comparator;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalTime;
/**
@Authors: Joshua Bellows, Nathan Thibodeau, Daniel Lemere, David Nguyen
@Version: November 1 2019

 Main Class file for BikePart User Interface.

 @Param Args
 */
public class BikePartUI{
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        //FileInputStream inputfile = new FileInputStream("WarehouseDB.txt");

        ArrayList<ArrayList> inventory = new ArrayList();
        ArrayList WarehouseMain = FileToArrayList("WarehouseDB.txt");
        ArrayList Warehouse2 = FileToArrayList("Warehouse2.txt");
        ArrayList Warehouse1 = FileToArrayList("Warehouse1.txt");
        inventory.add(WarehouseMain);
        inventory.add(Warehouse1);
        inventory.add(Warehouse2);

        ArrayList<SalesVan> Salevanfleet = new ArrayList();
        SalesVan Salesvan1 = new SalesVan();
        SalesVan Salesvan2 = new SalesVan();
        SalesVan Salesvan3 = new SalesVan();
        Salevanfleet.add(Salesvan1);
        Salevanfleet.add(Salesvan2);
        Salevanfleet.add(Salesvan3);

        /*
        Scanner inputfilescanner = new Scanner (inputfile);
        boolean inputfilecontains = true;
        while(inputfilecontains){
            if(inputfilescanner.hasNextLine()) {
                inventory.add(inputfilescanner.nextLine());
            }
            else {
                inputfilecontains = false;
            }
        }
         */
        //BikePart tester = BikePartConverter((String) inventory.get(0));
        //System.out.println(tester.Stringbikepart());

        ArrayList deliveryparts = new ArrayList();
        int choice;
        boolean programrunning = true;
        while (programrunning) {
            System.out.println("1: Read File");
            System.out.println("2: Enter Part");
            System.out.println("3: Sell Part");
            System.out.println("4: Display Part");
            System.out.println("5: Sort Name");
            System.out.println("6: Sort Number");
            System.out.println("7: Add Sales Van to Fleet");
            System.out.println("8: Display All Parts Alphabetically");
            System.out.println(("9: Display All Parts Numerically"));
            System.out.println("10: Add item" + " to SalesVan from Main Warehouse");
            System.out.println("11: Put items from SalesVan into Warehouse");
            System.out.println("12: Quit Program");
            choice = input.nextInt();
            if (choice == 1) {
                FileInputStream readfile = new FileInputStream("inventory.txt");

                Scanner readinputfile = new Scanner(readfile);
                boolean readingfile = true;
                while (readingfile) {
                    if (readinputfile.hasNextLine()) {
                        String currentline = readinputfile.nextLine();

                        ArrayList newarraylist = BikePartAdd(WarehouseMain, currentline);
                        WarehouseMain = newarraylist;
                    }
                    if (readinputfile.hasNextLine() == false) {
                        readingfile = false;
                    }
                }
                readfile.close();
                readinputfile.close();
            } else if (choice == 2) {
                System.out.println("Enter Part(String Part name, int Part Number, double Part Price, double Part Sale Price, boolean whether price on sale, int quantity)");
                String newpart = input.next();
                newpart += input.nextLine();
                ArrayList newarraylist = BikePartAdd(WarehouseMain, newpart);
                WarehouseMain = newarraylist;
            } else if (choice == 3) {
                System.out.println("Select Warehouse:");
                for(int i = 0; i < inventory.size();i++){
                    System.out.println(i + ":"+inventory.get(i));
                }
                int selectedwarehouse = input.nextInt();
                String partname = "";
                boolean founditem = false;
                System.out.println("Enter Part Number:");
                String sellpart = input.next();
                for (int i = 0; i < inventory.get(selectedwarehouse).size(); i++) {
                    String currentline = (String) inventory.get(selectedwarehouse).get(i);
                    String price = "";
                    String sale = "";
                    String[] currentlinearray = currentline.split(",");
                    String trimmedpartnum = (String) currentlinearray[1].trim();
                    if (trimmedpartnum.equals(sellpart)) {
                        if (Boolean.parseBoolean(currentlinearray[4].trim())) {
                            price = currentlinearray[3];
                            sale = "The item is on sale.";
                        } else {
                            price = currentlinearray[2];
                            sale = "The item is not on sale.";
                        }
                        //}
                        System.out.println(currentlinearray[0] + ", " + currentlinearray[1] + ", the price is $" + price);
                        System.out.println(sale);
                        partname = currentlinearray[0];
                        BikePart currentpart = BikePartConverter(currentline);
                        currentpart.setQuantity(currentpart.getQuantity() - 1);
                        String bikepartstring = currentpart.getPartName() + "," + currentpart.getPartNumber() + "," + currentpart.getPrice()
                                + "," + currentpart.getSalesPrice() + "," + currentpart.isOnSale() + "," + currentpart.getQuantity();
                        inventory.get(selectedwarehouse).set(i, bikepartstring);
                        System.out.println("A " + partname + " was sold at " + LocalTime.now().toString());
                        founditem = true;
                        i = inventory.get(selectedwarehouse).size() + 1;

                    }
                    if (founditem = false) {
                        System.out.println("Error: Item not found");
                    }

                }

            } else if (choice == 4) {
                System.out.println("Enter part name:");
                String displaypart = input.next();
                displaypart += input.nextLine();
                boolean pricefound = false;
                for (int i = 0; i < WarehouseMain.size(); i++) {
                    String currentline = (String) WarehouseMain.get(i);
                    String[] currentlinearray = currentline.split(",");
                    String trimmedname = currentlinearray[0].trim();
                    String sale = "";
                    String price = "";
                    if (trimmedname.equals(displaypart)) {
                        if (Boolean.parseBoolean(currentlinearray[4].trim())) {
                            price = "price is $" + currentlinearray[3];
                        } else {
                            price = "price is $" + currentlinearray[2];
                        }
                        System.out.println(currentlinearray[0] + ", " + currentlinearray[1] + ", " + price);
                        pricefound = true;
                        i = WarehouseMain.size() + 1;
                    }


                }
                if (pricefound == false) {
                    System.out.println("Error: Item not found");
                }
            } else if (choice == 5) {
                WarehouseMain.sort(null);
                for (int i = 0; i < WarehouseMain.size(); i++) {
                    System.out.println(WarehouseMain.get(i));
                }
            } else if (choice == 6) {
                ArrayList numbers = new ArrayList();
                ArrayList orderedparts = new ArrayList();
                for (int i = 0; i < WarehouseMain.size(); i++) {
                    String currentline = (String) WarehouseMain.get(i);
                    String[] currentlinearray = currentline.split(",");
                    int partnumber = Integer.parseInt(currentlinearray[1].trim());
                    numbers.add(partnumber);

                }
                numbers.sort(null);
                for (int i = 0; i < numbers.size(); i++) {
                    for (int z = 0; z < WarehouseMain.size(); z++) {
                        String currentline = (String) WarehouseMain.get(z);
                        String[] currentlinearray = currentline.split(",");
                        String partnumber = currentlinearray[1].trim();
                        String currentnumber = numbers.get(i).toString();
                        System.out.println(partnumber + " " + numbers.get(i));
                        System.out.println(partnumber.equals(currentnumber));
                        if (partnumber.equals(currentnumber)) {
                            orderedparts.add(currentline);
                            z = WarehouseMain.size() + 10;
                        }
                    }
                }
                for (int i = 0; i < orderedparts.size(); i++) {
                    System.out.println(orderedparts.get(i));
                }

            } else if (choice == 7) {
                Salevanfleet.add(new SalesVan());
                System.out.println("New Sales Van added to warehouse");

            } else if (choice == 8) {
                System.out.println("Select Warehouse to display inventory");
                System.out.println("1: All Warehouses");
                System.out.println("2: Main warehouse");
                System.out.println("3: Individual Warehouse");
                int thischoice = input.nextInt();
                if (thischoice == 1) {
                    ArrayList totallist = new ArrayList();
                    totallist.addAll(Warehouse1);
                    totallist.addAll(Warehouse2);
                    totallist.addAll(WarehouseMain);
                    totallist.sort(null);
                    for (int i = 0; i < totallist.size(); i++) {
                        System.out.println(totallist.get(i));
                    }

                } else if (thischoice == 2) {
                    WarehouseMain.sort(null);
                    for (int i = 0; i < WarehouseMain.size(); i++) {
                        System.out.println(WarehouseMain.get(i));
                    }
                } else if (thischoice == 3) {
                    System.out.println("Select SalesVan");
                    for (int i = 0; i < Salevanfleet.size(); i++) {
                        System.out.println(i + ":" + Salevanfleet.get(i));
                    }
                    int salesvanchoice = input.nextInt();
                    Salevanfleet.get(salesvanchoice).sort(null);
                    for (int i = 0; i < Salevanfleet.get(salesvanchoice).size(); i++) {
                        System.out.println(Salevanfleet.get(salesvanchoice).get(i));
                    }

                }
            } else if (choice == 9) {
                System.out.println("Select Warehouse to display inventory");
                System.out.println("1: All Warehouses");
                System.out.println("2: Main warehouse");
                System.out.println("3: Individual Warehouse");
                int thischoice = input.nextInt();
                if (thischoice == 1) {
                    ArrayList<BikePart> bikeparts = new ArrayList();
                    for (int i = 0; i < WarehouseMain.size(); i++) {
                        bikeparts.add(BikePartConverter((String) WarehouseMain.get(i)));
                    }
                    for (int i = 0; i < Warehouse1.size(); i++) {
                        bikeparts.add(BikePartConverter((String) Warehouse1.get(i)));
                    }
                    for (int i = 0; i < Warehouse2.size(); i++) {
                        bikeparts.add(BikePartConverter((String) Warehouse2.get(i)));
                    }
                    ArrayList numbers = new ArrayList();
                    for (int i = 0; i < bikeparts.size(); i++) {
                        numbers.add(Integer.parseInt(bikeparts.get(i).getPartNumber().trim()));
                    }
                    numbers.sort(null);
                    ArrayList<BikePart> sortedbikeparts = new ArrayList();
                    for (int i = 0; i < bikeparts.size(); i++) {
                        for (int z = 0; z < bikeparts.size(); z++) {
                            if ((numbers.get(i)).equals(Integer.parseInt(bikeparts.get(z).getPartNumber().trim()))) {
                                sortedbikeparts.add(bikeparts.get(z));
                            }
                        }


                    }
                    for (int i = 0; i < sortedbikeparts.size(); i++) {
                        System.out.println(sortedbikeparts.get(i).Stringbikepart());
                    }
                } else if (thischoice == 2) {
                    ArrayList<BikePart> bikeparts = new ArrayList();
                    for (int i = 0; i < inventory.size(); i++) {
                        bikeparts.add(BikePartConverter((String) WarehouseMain.get(i)));
                    }
                    ArrayList numbers = new ArrayList();
                    for (int i = 0; i < bikeparts.size(); i++) {
                        numbers.add(Integer.parseInt(bikeparts.get(i).getPartNumber().trim()));
                    }
                    numbers.sort(null);
                    ArrayList<BikePart> sortedbikeparts = new ArrayList();
                    for (int i = 0; i < bikeparts.size(); i++) {
                        for (int z = 0; z < bikeparts.size(); z++) {
                            if ((numbers.get(i)).equals(Integer.parseInt(bikeparts.get(z).getPartNumber().trim()))) {
                                sortedbikeparts.add(bikeparts.get(z));

                            }
                        }
                    }
                    for (int i = 0; i < sortedbikeparts.size(); i++) {
                        System.out.println(sortedbikeparts.get(i).Stringbikepart());

                    }
                } else if (thischoice == 3) {
                    System.out.print("Select SalesVan");
                    for (int i = 0; i < Salevanfleet.size(); i++) {
                        System.out.println(i + ":" + Salevanfleet.get(i));

                    }
                    int salesvanchoice = input.nextInt();
                    ArrayList<BikePart> bikeparts = new ArrayList();
                    for (int i = 0; i < Salevanfleet.get(salesvanchoice).size(); i++) {
                        bikeparts.add(BikePartConverter((String) Salevanfleet.get(salesvanchoice).get(i)));
                    }
                    ArrayList number = new ArrayList();
                    for (int i = 0; i < bikeparts.size(); i++) {
                        number.add(bikeparts.get(i).getPartNumber());
                    }
                    number.sort(null);
                    ArrayList<BikePart> sortedpartlist = new ArrayList();
                    for (int i = 0; i < number.size(); i++) {
                        for (int z = 0; z < bikeparts.size(); z++) {
                            if (number.get(i).equals(Integer.parseInt(bikeparts.get(z).getPartNumber().trim()))) {
                                sortedpartlist.add(bikeparts.get(z));
                            }
                        }
                    }
                    for (int i = 0; i < bikeparts.size(); i++) {
                        System.out.println(sortedpartlist.get(i).Stringbikepart());
                    }
                }

                } else if (choice == 10) {
                System.out.println("Select Salesvan");
                for (int i = 0; i < Salevanfleet.size(); i++) {
                    System.out.println(i + ":" + Salevanfleet.get(i));
                }
                int Salesvanselection = input.nextInt();
                System.out.println("Enter Part number:");
                String partnumber = input.next();
                SalesVanTransferTo(Salevanfleet.get(Salesvanselection),(ArrayList) inventory.get(0),partnumber);
                }
            else if (choice == 11) {
                    System.out.println("Select Salesvan inventory to take from");
                    for (int i = 1; i < Salevanfleet.size(); i++) {
                        System.out.println(i + ":" + Salevanfleet.get(i));
                    }
                    int Warehouseselection = input.nextInt();
                    System.out.println("Select Salesvan inventory to add to");
                    for (int i = 0; i < Salevanfleet.size(); i++) {
                        System.out.println((i + ":" + Salevanfleet.get(i)));
                    }
                    int Salesvanselection = input.nextInt();
                    System.out.println("Enter Part number:");
                    String partnumber = input.next();
                    SalesVanTransferFrom(Salevanfleet.get(Salesvanselection),Salevanfleet.get(Warehouseselection),partnumber);
                } else if (choice == 12) {
                    //FileOutputStream outfile = new FileOutputStream("WarehouseDB.txt");
                   // PrintWriter output = new PrintWriter(outfile);
                   // for (int i = 0; i < inventory.size(); i++) {
                      //  output.println(inventory.get(i));
                   // }
                    input.close();
                    //inputfile.close();
                    //inputfilescanner.close();
                ArrayListToFile(WarehouseMain,"WarehouseDB.txt");
                ArrayListToFile(Warehouse1,"Warehouse1.txt");
                ArrayListToFile(Warehouse2,"Warehouse2.txt");
                    //output.flush();
                   // output.close();
                    programrunning = false;
                } else {
                    System.out.println("Not one of the choices, please pick one from the list");
                }


            }
        }


    /**
     *
     * @param newline The line to be converted into a bike part
     * @return BikePart that is the converted line
     * @throws IOException
     */
    public static BikePart BikePartConverter(String newline) throws IOException{
        String[] newlinearray = newline.split(",");
        Scanner newpartline = new Scanner(newline);
        String bikepartname = newlinearray[0];
        String bikepartnumber = newlinearray[1];
        String bikepartprice = newlinearray[2];
        String bikepartsaleprice = newlinearray[3];
        boolean bikepartonsale = Boolean.getBoolean(newlinearray[4]);
        String trimint = newlinearray[5].trim();
        int bikepartquantity = Integer.parseInt(trimint);
        BikePart newbikepart = new BikePart(bikepartname,bikepartnumber,bikepartprice,bikepartsaleprice,bikepartonsale,bikepartquantity);
        newpartline.close();
        return newbikepart;
    }
    /**
     * @Param ArrayList currentarray:The Arraylist that you want add a bike part to.
     * @Parem String Newbikepart: The Bike part to add to the arraylist
     *
     * @Return Updated ArrayList.
    */
    public static ArrayList BikePartAdd(ArrayList currentarray, String newbikepart){
        boolean matches = false;
        ArrayList newone = new ArrayList();
        int sizeofarray = currentarray.size();
        for(int i = 0; i < sizeofarray; i++) {
            String currentline = (String) currentarray.get(i);
            String[] currentlinearray = currentline.split(",");
            String[] newbikepartarray = newbikepart.split(",");
            if(currentlinearray[0].equals(newbikepartarray[0])){
                currentlinearray[2] = newbikepartarray[2];
                currentlinearray[3] = newbikepartarray[3];
                currentlinearray[4] = newbikepartarray[4];
                String trimint1 = currentlinearray[5].trim();
                String trimint2 = newbikepartarray[5].trim();
                int quantitybikepart = Integer.parseInt(trimint1);
                int quantitynewbikepart = Integer.parseInt(trimint2);
                int result = quantitybikepart + quantitynewbikepart;
                currentlinearray[5] = Integer.toString(result);
                String newlineforarray = currentlinearray[0] + ", " + currentlinearray[1] + ", " + currentlinearray[2] + ", " + currentlinearray[3] + ", " + currentlinearray[4] + ", " + currentlinearray[5];
                newone.add(i,newlineforarray);
            }
            else{
                newone.add(currentline);



            }

        }
        return newone;
    }

    /**
     *
     * @param filename the file to be converted into an Arraylist
     * @return the converted file.
     * @throws IOException
     */
    public static ArrayList FileToArrayList(String filename) throws IOException{
        ArrayList inventory = new ArrayList();
        FileInputStream fileinput = new FileInputStream(filename);
        Scanner inputfile = new Scanner(fileinput);
        boolean filehasstuff = true;
        while(filehasstuff){
            if(inputfile.hasNextLine()){
                inventory.add(inputfile.nextLine());
            }
            else{
                filehasstuff = false;
            }
        }
        return inventory;
    }

    /**
     *
     * @param Arrayname The Array to be converted to file
     * @param filename the file where the Array will be placed
     * @throws IOException
     */
    public static void ArrayListToFile(ArrayList Arrayname, String filename) throws IOException{
        FileOutputStream outputfile = new FileOutputStream(filename);
        PrintWriter output = new PrintWriter(outputfile);
        for(int i = 0; i < Arrayname.size();i++){
            output.println(Arrayname.get(i));
        }
        output.flush();
        output.close();
        outputfile.close();
    }

    /**
     *
     * @param van The sales van for the inventory to be added
     * @param warehouse The warehouse where the inventory is coming from
     * @param partnumber The part number for the item to be transfered
     * @throws IOException
     */
    public static void SalesVanTransferTo(SalesVan van,ArrayList warehouse,String partnumber) throws IOException{

        ArrayList<BikePart> helper = new ArrayList();
        for(int i = 0; i < warehouse.size();i++){
            helper.add(BikePartConverter((String) (warehouse.get(i))));
        }
        int locofitem = 0;
        for(int i = 0; i < helper.size();i++){
            if(partnumber.equals(helper.get(i).getPartNumber().trim())){
                locofitem = i;
            }
        }
        van.add(warehouse.get(locofitem));
        warehouse.remove(locofitem);
    }

    /**
     *
     * @param van The van for items be transferd from
     * @param van2 The van for items to be transfered to
     * @param partnumber The part number of the item to be transfered
     * @throws IOException
     */
    public static void SalesVanTransferFrom(SalesVan van, SalesVan van2, String partnumber) throws IOException{

        ArrayList<BikePart> helper= new ArrayList();
        for(int i = 0; i < van.size(); i++){
            helper.add(BikePartConverter((String) (van.get(i))));
        }
        int locofitem = 0;
        for(int i = 0; i < helper.size();i++){
            if(partnumber.equals(helper.get(i).getPartNumber().trim())){
                locofitem = i;
            }
        }
        van2.add(van.get(locofitem));
        van.remove(locofitem);
    }
}