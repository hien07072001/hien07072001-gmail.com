package view;

import controller.DataController;
import controller.controllerUltility;
import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class View {

    public static DataController controller = new DataController();
    public static controllerUltility ultility = new controllerUltility();

    public static void main(String[] args) throws FileNotFoundException {
        int choice = 0;
        final String books_File_Name = "Book.txt";
        final String readers_File_Name = "READ.txt";
        final String brm_File_Name = "BRM.txt";


        ArrayList<Book> books = new ArrayList<Book>();
        ArrayList<Reader> readers = new ArrayList<Reader>();
        ArrayList<BookReaderManagement> brms = new ArrayList<BookReaderManagement>();

        boolean isReaderCheck = false;
        boolean isBookCheck = false;

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("_______Menu_______");
            System.out.println("1.thêm một đầu sách vào file.");
            System.out.println("2.hiển thị danh sách các sách có trong file ");
            System.out.println("3.thêm một bạ đọc vào file");
            System.out.println("4.hiển thị danh sách bạ đọc có trong file");
            System.out.println("5.nhập thông tin quản lí mượn");
            System.out.println("6.sắp xếp");
            System.out.println("7.tìm kiếm thông tin quản lí mượn theo tên bạn đọc ");
            System.out.println("8.xoá sách khỏi danh sach ");
            System.out.println("9.xoa nguoi khi danh sach");
            System.out.println("0.thoát khỏi ứng dụng ");
            System.out.println("bạn chọn ?");

            choice = scanner.nextInt();

            scanner.nextLine();
//            doc bo cai dong chua lua chon

            switch (choice) {
                case 0:
                    System.out.println("cảm ơn bạ đã sử dụng dịch vụ của chúng tôi ");
                    break;
                case 1:
                    if (!isBookCheck) {
                        checkBookID(controller, books_File_Name);
                        isBookCheck = true;
                    }
                    checkBookID(controller, books_File_Name);

                    String bookName, author, spec;
                    int year, quan;

                    System.out.println("nhập tên sách ");
                    bookName = scanner.nextLine();

                    System.out.println("nhập tên tác giả ");
                    author = scanner.nextLine();

                    System.out.println("nhập thể loại sách ");
                    spec = scanner.nextLine();

                    System.out.println("nhập năm xuất bản ");
                    year = scanner.nextInt();

                    System.out.println("nhập số lượng ");
                    quan = scanner.nextInt();

                    Book book = new Book(0, bookName, author, spec, year, quan);
                    controller.writeBookToFile(book, books_File_Name);
                    break;

                case 2:
                    books = controller.readBooksFromFile(books_File_Name);
                    showBookInfo(books);
                    break;

                case 3:
                    if (!isReaderCheck) {
                        checkReaderID(controller, readers_File_Name);
                        isReaderCheck = true;

                    }
                    String fullName, address, phoneNum;

                    System.out.println("nhập họ và tên ");
                    fullName = scanner.nextLine();

                    System.out.println("nhập địa chỉ ");
                    address = scanner.nextLine();

                    do {
                        System.out.println("nhập số điện thoại ");
                        phoneNum = scanner.nextLine();

                    } while (!phoneNum.matches("\\d{10}"));
//                 neu chua nhap dung 10 so;

                    Reader reader = new Reader(0, fullName, address, phoneNum);
                    controller.writeReaderToFile(reader, readers_File_Name);

                    break;

                case 4:
                    readers = controller.readReadersFromFile(readers_File_Name);
                    showReaderInfo(readers);
                    break;

                case 5:
//                    b0:khoi tao dnah sach;
                    readers = controller.readReadersFromFile(readers_File_Name);
//                    doc ra danh sach cac ban doc
                    books = controller.readBooksFromFile(books_File_Name);
//                    doc ra danh sach cac sach
                    brms = controller.readBRMsFromFile(brm_File_Name);
//                    doc ra thong tin quan li muon
//                    b1:
                    int readerID, bookID;
//                    khoi tao de nhap ma vao xem cac ma do co trung voi cac ma cua cac sinh vien co trong he thong  hay khong
                    boolean isBorrowable = false;
//                    isborrowable: co duoc muon hay khong;

                    showReaderInfo(readers);
//                    hien thi ra dnah sch cac ban doc de nguoi ta con biet de nhap ma;
                    System.out.println("_______________");
                    do {
                        System.out.println("nhập mã bạn đọc,nhập 0 để bỏ qua ");
                        readerID = scanner.nextInt();
                        if (readerID == 0) {
                            break;
//                            tat ca ban doc da  duoc muon du sach quy dinh
                        }
                        isBorrowable = checkBorrowed(brms, readerID);
                        if (isBorrowable) {
//                            neu con duoc muon
                            break;
                        } else {
//                            con neu khong thi se xuat ra thong bao
                            System.out.println("bạn đọc này đã mượn đủ số lượng cho phép ");
                        }
                    } while (true);
//                    b2:
                    boolean isFull = false;
//                    ung voi moi ban muon thi hp da muon du toi da hay chua;
                    do {
                        showBookInfo(books);
                        System.out.println("_______");
                        System.out.println("nhập mã sách ,nhập 0 để bỏ qua ");
                        bookID = scanner.nextInt();
                        if (bookID == 0) {
                            break;

                        }
                        isFull = checkfull(brms, readerID, bookID);
//                        true neu ban da muon du 3
                        if (isFull) {
                            System.out.println("vui lòng chọn đầu sách khác ");
                        } else {
                            break;
                        }

                    } while (true);
//                    b3:
                    int total = getTotal(brms, readerID, bookID);
                    do {
                        System.out.println("nhập số lượng  mượn ,tối đa 3 cuốn (đã mượn  " + total + "):");
                        int x = scanner.nextInt();
//                        tao ra 1 bien tam nao do
                        if ((x + total) >= 1 && (x + total) <= 3) {
                            total += x;
//                            update lai bien total
                            break;
                        } else {
                            System.out.println("nhập quá số lượng quy định !vui lòng nhập lại ");
                        }

                    } while (true);
                    scanner.nextLine();
//                    doc bo dong co chua so;

                    System.out.println("nhập tình trạng ");
                    String status = "";
                    status = scanner.nextLine();

//                    b4:
//                    Book book, Reader reader,
//                    int numOffBorrow, String state, int totalBorrow
                    Book currentBook = getBook(books, bookID);
                    Reader currentRead = getReader(readers, readerID);
                    BookReaderManagement b = new BookReaderManagement(currentBook,
                            currentRead, total, status, 0);

//                    b5:
                    brms = ultility.updateBRMInfo(brms, b);
//                   cap nhat danh sach quan li muon;

                    controller.updateBRMFile(brms, brm_File_Name);
//cap nhat file du lieu;
//                     b6:
                    showBRMInfo(brms);
                    break;

                case 6:
                    brms = controller.readBRMsFromFile(brm_File_Name);
                    brms = ultility.updateTotalBorrowed(brms);
                    System.out.println("__________");
                    System.out.println("_Các lựa chọn sắp xếp __");
                    int x = 0;

                    do {
                        System.out.println("1.sắp xếp theo tên bạn đọc ");
                        System.out.println("2.sắp xếp theo tổng số lượng mượn (giảm dần");
                        System.out.println("0.trở lại menu chính ");
                        System.out.println("Bạn chọn ?");
                        x = scanner.nextInt();
                        if (x == 0) {
                            break;
                        }
                        switch (x) {
                            case 1:
// sap xep theo ten
                                brms = ultility.sortByReaderName(brms);
                                showBRMInfo(brms);
                                break;
                            case 2:
//                                sap xep theo so luong muon;
                                brms = ultility.sortByNumOffBorrow(brms);
                                showBRMInfo(brms);
                                break;
                        }

                    } while (true);
                    break;
                case 7:
                    brms = controller.readBRMsFromFile(brm_File_Name);
                    System.out.println("nhập cụm từ có trong tên bạn đọc cần tìm ");
                    String key = scanner.nextLine();
                    ArrayList<BookReaderManagement> result = ultility.searchByReaderName(brms, key);
                    if (result.size() == 0) {
                        System.out.println("không tìm thấy bạn đọc ");
                    } else {
                        showBRMInfo(result);
                    }
                    System.out.println();
                    break;
                case 8:
                    System.out.println("nhap id cuon sach ban muon xoa");
                    int idBook = scanner.nextInt();
                    books = controller.readBooksFromFile(books_File_Name);
                    deleteBook(books, idBook);
                    break;
                case 9:
                    System.out.println("nhap id nguoi ban muon xoa");
                    int idReader = scanner.nextInt();
                    readers = controller.readReadersFromFile(readers_File_Name);
                    deleteReader(readers, idReader);
                    break;

            }

        } while (choice != 0);

    }

    private static void deleteBook(ArrayList<Book> books, int id) throws FileNotFoundException {
        ultility.deleteBookById(books, id);
        PrintWriter writer = new PrintWriter("book.txt");
        writer.print("");
        writer.close();
        for (Book book : books) {
            controller.writeBookToFile(book,"book.txt");
        }
    }
    private static void deleteReader(ArrayList<Reader> readers, int id) throws FileNotFoundException {
        ultility.deleteReaderById(readers, id);
        PrintWriter writer = new PrintWriter("READ.txt");
        writer.print("");
        writer.close();
        for (Reader reader : readers) {
            controller.writeReaderToFile(reader,"READ.txt");
        }
    }

    private static void showBRMInfo(ArrayList<BookReaderManagement> brms) {
        for (BookReaderManagement b : brms) {
            System.out.println(b);
        }
    }

    private static Book getBook(ArrayList<Book> books, int bookID) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookID() == bookID) {
                return books.get(i);
            }
        }
        return null;
    }

    private static Reader getReader(ArrayList<Reader> readers, int readerID) {
        for (int i = 0; i < readers.size(); i++) {
            if (readers.get(i).getReaderID() == readerID) {
                return readers.get(i);
            }
        }
        return null;
    }

    private static int getTotal(ArrayList<BookReaderManagement> brms, int readID, int bookID) {
        for (BookReaderManagement br : brms) {
            if (br.getReader().getReaderID() == readID &&
                    br.getBook().getBookID() == bookID) {
                return br.getNumOffBorrow();
            }
        }
        return 0;
    }

    private static boolean checkfull(ArrayList<BookReaderManagement> brms, int readerID, int bookID) {
        for (BookReaderManagement br : brms) {
            if (br.getReader().getReaderID() == readerID &&
                    br.getBook().getBookID() == bookID && br.getNumOffBorrow() == 3) {
                return true;
//                khong duoc muon tiep dau sach nay;


            }
        }
        return false;
//        duoc muon tiep;

    }

    private static Boolean checkBorrowed(ArrayList<BookReaderManagement> brms, int readerID) {
        int count = 0;
        for (var br : brms) {
            if (br.getReader().getReaderID() == readerID) {
                count += br.getNumOffBorrow();

            }
        }
        if (count == 15) {
            return false;
//            khong con co hoi muon;

        }
        return true;
//        duoc phep muon;

    }

    private static void showReaderInfo(ArrayList<Reader> readers) {
        System.out.println("________Thong tin cac ban doc trong file_______");
        for (var r : readers) {
            System.out.println(r);
        }
    }

    private static void checkReaderID(DataController controller, String readersFileName) {
        var readers = controller.readReadersFromFile(readersFileName);
        if (readers.size() == 0) {
            // k thuc hien j ca;

        } else {
            Reader.setId(readers.get(readers.size() - 1).getReaderID() + 1);
        }
    }

    private static void checkBookID(DataController controller, String fileName) {
        ArrayList<Book> listBooks = controller.readBooksFromFile(fileName);
        if (listBooks.size() == 0) {
            //khong thuc hien j ca
        } else {
            Book.setId(listBooks.get(listBooks.size() - 1).getBookID() + 1);

        }

    }

    private static void showBookInfo(ArrayList<Book> books) {
        System.out.println("_____thong tin sach trong file______");
        for (var b : books) {
            System.out.println(b);
        }
    }

}
