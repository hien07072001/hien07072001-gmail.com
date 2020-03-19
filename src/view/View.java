package view;

import controller.DataController;
import controller.controllerUltility;
import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        int choice=0;
        final String books_File_Name="Book.txt";
        final String readers_File_Name="READ.txt";
        final String brm_File_Name="BRM.txt";

        DataController controller =new DataController();
        controllerUltility Ultility=new controllerUltility();

        ArrayList<Book> books=new ArrayList<Book>();
        ArrayList<Reader> readers=new ArrayList<Reader>();
        ArrayList<BookReaderManagement> brms=new ArrayList<>();

        boolean isReaderCheck=false;
        boolean isBookCheck=false;

        Scanner scanner=new Scanner(System.in);

        do {
            System.out.println( "_______Menu_______");
            System.out.println("1.thêm một đầu sách vao file.");
            System.out.println("2.hien thi danh sach cac sach co trong file");
            System.out.println("3.them mot ban doc vao file");
            System.out.println("4.hien thi danh sach cac ban doc co trong file");
            System.out.println("5.nhap thong tin quan li muon");
            System.out.println("6.sap xep");
            System.out.println("7.tim kiem thong tin quan li muon theo ten ban doc");
            System.out.println("0.thoat khoi ung dung");
            System.out.println("ban chon?");

            choice =scanner.nextInt();

            scanner.nextLine();
//            doc bo cai dong chua lua chon

            switch (choice){
                case 0:

                    System.out.println("cam on ban da su dung dich vu cua chung toi");
                    break;
                case 1:
                    if (!isBookCheck){
                        checkBookID(controller,books_File_Name);
                        isBookCheck=true;
                    }
                    checkBookID(controller,books_File_Name);
                    String bookName,author,spec;
                    int year,quan;

                    System.out.println("nhap ten sach");
                    bookName=scanner.nextLine();

                    System.out.println("nhap ten tac gia");
                    author =scanner.nextLine();

                    System.out.println("nhap the loai sach");
                    spec=scanner.nextLine();

                    System.out.println("nhap vao nam xuat ban");
                    year=scanner.nextInt();

                    System.out.println("nhap so luomg");
                    quan=scanner.nextInt();

                    Book book=new Book(0,bookName,author,spec,year,quan);
                    controller.writeBookToFile( book,books_File_Name);
                     break;

                case 2:
                    books =controller.readBooksFromFile(books_File_Name);
                    showBookInfo(books);
                    break;

                case 3:
                    if (!isReaderCheck){
                        checkReaderID(controller,readers_File_Name);
                        isReaderCheck=true;

                    }
                    String fullName,address,phoneNum;

                    System.out.println("nhap ho ten");
                    fullName=scanner.nextLine();

                    System.out.println("nhap dia chi");
                    address=scanner.nextLine();

                 do {
                      System.out.println("nhap sdt");
                      phoneNum=scanner.nextLine();

                  }while (!phoneNum.matches("\\d{10}"));
//                 neu chua nhap dung 10 so;

                    Reader reader=new Reader(0,fullName,address,phoneNum);
                    controller.writeReaderToFile(reader,readers_File_Name);

                    break;

                case 4:
                    readers=controller.readReadersFromFile(readers_File_Name);
                    showReaderInfo(readers);
                    break;

                case 5:
//                    b0:khoi tao dnah sach;
                    readers=controller.readReadersFromFile(readers_File_Name);
//                    doc ra danh sach cac ban doc
                    books=controller.readBooksFromFile(books_File_Name);
//                    doc ra danh sach cac sach
                    brms=controller.readBRMsFromFile(brm_File_Name);
//                    doc ra thong tin quan li muon
//                    b1:
                    int readerID,bookID;
//                    khoi tao de nhap ma vao xem cac ma do co trung voi cac ma cua cac sinh vien co trong he thong  hay khong
                    boolean isBorrowable= false;
//                    isborrowable: co duoc muon hay khong;

                    showReaderInfo(readers);
//                    hien thi ra dnah sch cac ban doc de nguoi ta con biet de nhap ma;
                    System.out.println("_______________");
                    do {
                        System.out.println("nhap ma ban doc: , nhap 0 de bo qua");
                        readerID =scanner.nextInt();
                        if (readerID == 0){
                            break;
//                            tat ca ban doc da  duoc muon du sach quy dinh
                        }
                        isBorrowable=checkBorrowed(brms,readerID);
                        if (isBorrowable){
//                            neu con duoc muon
                            break;
                        }
                        else {
//                            con neu khong thi se xuat ra thong bao
                            System.out.println("ban doc nay da muon du so luon cho phep");
                        }
                    }while (true);
//                    b2:
                    boolean isFull=false;
//                    ung voi moi ban muon thi hp da muon du toi da hay chua;
                    do {
                        showBookInfo(books);
                        System.out.println("_______");
                        System.out.println("nhap ma sach,nhap 0 de bo qua:");
                        bookID=scanner.nextInt();
                        if (bookID==0){
                            break;

                        }
                        isFull=checkfull(brms,readerID,bookID);
//                        true neu ban da muon du 3
                        if (isFull){
                            System.out.println( "vui long chon dau sach khac");
                        }else {
                            break;
                        }

                    }while (true);
//                    b3:
                    int total=getTotal(brms,readerID,bookID);
                    do {
                        System.out.println("nhap so luong muon ,toi da 3 cuon ( da muon "+total+ ") :");
                        int x=scanner.nextInt();
//                        tao ra 1 bien tam nao do
                        if ((x+total) >= 1 && (x+total) <= 3){
                            total +=x;
//                            update lai bien total
                            break;
                        }else {
                            System.out.println("nhap qua so luong quy dinh! vui long nhap lai");
                        }

                    }while (true);
                    scanner.nextLine();
//                    doc bo dong co chua so;

                    System.out.println("nhap tinh trang");
                    String status="";
                    status=scanner.nextLine();

//                    b4:
//                    Book book, Reader reader,
//                    int numOffBorrow, String state, int totalBorrow
                    Book currentBook = getBook(books,bookID);
                    Reader currentRead=getReader(readers,readerID);
                    BookReaderManagement b=new BookReaderManagement(currentBook,
                            currentRead,total,status,0);

//                    b5:
                   brms=Ultility.updateBRMInfo(brms,b);
//                   cap nhat danh sach quan li muon;

                   controller.updateBRMFile(brms,brm_File_Name);
//cap nhat file du lieu;
//                     b6:
                    showBRMInfo(brms);
                    break;

                case 6:
                   brms=controller.readBRMsFromFile(brm_File_Name);
                   brms=Ultility.updateTotalBorrowed(brms);
                    System.out.println("__________");
                    System.out.println("_cac lua chon sap xep__");
                    int x=0;

                    do {
                       System.out.println("1.sap xep theo ten cua ban doc");
                       System.out.println("2.sap xep theo tong so luong muon(gian dan");
                       System.out.println("0.tro lai menu chinh");
                       System.out.println("ban chon?");
                        x=scanner.nextInt();
                       if ( x==0){
                            break;
                        }
                       switch (x){
                            case 1:
// sap xep theo ten
                               brms=Ultility.sortByReaderName(brms);
                                showBRMInfo(brms);
                                break;
                            case 2:
//                                sap xep theo so luong muon;
                                brms=Ultility.sortByNumOffBorrow(brms);
                                showBRMInfo(brms);
                                break;
                        }

                    }while (true);
                    break;
                case 7:
                    brms=controller.readBRMsFromFile(brm_File_Name);
                    System.out.println("nhap cum tu co tron ten ban doc can tim");
                    String key=scanner.nextLine();
                    ArrayList<BookReaderManagement> result=Ultility.searchByReaderName(brms,key);
                    if (result.size()==0){
                        System.out.println("khong tim thay ban doc ");
                    }else {
                        showBRMInfo(result);
                    }
                    System.out.println();
                    break;
            }

        }while (choice !=0);

    }

    private static void showBRMInfo(ArrayList<BookReaderManagement> brms){
        for (BookReaderManagement b:brms){
            System.out.println(b);
        }
    }

    private static Book getBook(ArrayList<Book> books, int bookID) {
        for (int i=0;i<books.size();i++){
            if (books.get(i).getBookID()==bookID){
                return books.get(i);
            }
        }
        return null;
    }

    private static Reader getReader(ArrayList<Reader> readers,int readerID){
        for (int i=0;i<readers.size();i++){
            if (readers.get(i).getReaderID() == readerID){
                return readers.get(i);
            }
        }
        return null;
    }

    private static int getTotal(ArrayList<BookReaderManagement> brms,int readID,int bookID){
        for (BookReaderManagement br:brms){
            if (br.getReader().getReaderID()==readID &&
            br.getBook().getBookID()==bookID ){
                return br.getNumOffBorrow();
            }
        }
        return 0;
    }

    private static boolean checkfull(ArrayList<BookReaderManagement> brms,int readerID,int bookID){
        for (BookReaderManagement br:brms){
            if (br.getReader().getReaderID()==readerID &&
            br.getBook().getBookID()==bookID && br.getNumOffBorrow()==3){
                return true;
//                khong duoc muon tiep dau sach nay;


            }
        }
        return false;
//        duoc muon tiep;

    }

    private static Boolean checkBorrowed(ArrayList<BookReaderManagement> brms,int readerID){
        int count=0;
        for (var br:brms){
            if (br.getReader().getReaderID()==readerID){
                count += br.getNumOffBorrow();

            }
        }
        if (count==15){
            return false;
//            khong con co hoi muon;

        }
        return true;
//        duoc phep muon;

    }

    private static void showReaderInfo(ArrayList<Reader> readers) {
        System.out.println("________Thong tin cac ban doc trong file_______");
        for (var r:readers){
            System.out.println(r);
        }
    }

    private static void checkReaderID(DataController controller, String readersFileName) {
          var readers=controller.readReadersFromFile(readersFileName);
          if (readers.size()==0){
        // k thuc hien j ca;

          }else{
              Reader.setId(readers.get(readers.size()-1).getReaderID()+1);
          }
    }

    private static void checkBookID(DataController controller,String fileName) {
        var listBooks=  controller.readBooksFromFile(fileName);
        if (listBooks.size()==0){
        //khong thuc hien j ca
        }else {
            Book.setId(listBooks.get(listBooks.size()-1).getBookID()+1);

        }

    }

    private static void showBookInfo(ArrayList<Book> books) {
        System.out.println("_____thong tin sach trong file______");
        for (var b:books){
            System.out.println(b);
        }
    }

}
