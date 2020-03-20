package controller;

import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataController {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private Scanner scanner;
//mở file viết
    public void openFileToWrite(String fileName){
        try {
            fileWriter=new FileWriter(fileName,true);
//            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), StandardCharsets.UTF_8));
            printWriter=new PrintWriter(bufferedWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //viết sách vào file;
    public void writeBookToFile(Book book,String fileName){
        openFileToWrite(fileName);
        printWriter.println(book.getBookID()+"|"+book.getBookName()+"|"
                +book.getAuthor()+"|"+book.getSpecialization()+"|"+
                book.getPublishYear()+"|"+book.getQuantity());
        closeFileAfterWrite(fileName);

    }

    // viết người đọc vào file;
    public void writeReaderToFile(Reader reader, String fileName){
        openFileToWrite(fileName);
        printWriter.println(reader.getReaderID()+"|"+reader.getFullName()+"|"
                +reader.getAddress()+"|"+reader.getPhoneNumber());
        closeFileAfterWrite(fileName);

    }

    //viết ngừời mượn sách vào file
    public void writeBRMToFile(BookReaderManagement brm, String fileName){

        openFileToWrite(fileName);
        printWriter.println(brm.getReader().getReaderID()+"|"+brm.getBook().getBookID()+"|"
                +brm.getNumOffBorrow()+"|"+brm.getState());
        closeFileAfterWrite(fileName);

    }

    //đóng file sau khi viết;
    public void closeFileAfterWrite(String fileName){
        try {
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    mở file đọc
    public void openFileToRead(String fileName){
        try {
            File file=new File(fileName);
            if (!file.exists()){

                file.createNewFile();
            }
            scanner=new Scanner(Paths.get(fileName  ));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// đóng file sau khi đọc
    public void closeFileAfterReader(String fileName){
        try {
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

// mảng người đọc từ file
    public ArrayList<Reader> readReadersFromFile(String fileName){
        openFileToRead(fileName);
        ArrayList<Reader> readers=new ArrayList<>();
        while (scanner.hasNextLine()){
            String data=scanner.nextLine();
            Reader reader= createReaderFromDate(data);
            readers.add(reader);
        }
        closeFileAfterReader(fileName);
        return readers;
    }

    public Reader createReaderFromDate(String data) {
        String[] datas=data.split("\\|");

        Reader reader=new Reader(Integer.parseInt(datas[0]),datas[1],datas[2],datas[3]);
        return reader;
    }
//mảng đọc sách từ file
    public ArrayList<Book> readBooksFromFile(String fileName){
        openFileToRead(fileName);
        ArrayList<Book> books=new ArrayList<>();
        while (scanner.hasNextLine()){
            String data=scanner.nextLine();
            Book book=createBookFromData(data);
            books.add(book);

        }
        closeFileAfterReader(fileName);
        return books;
    }

    private Book createBookFromData(String data) {
        String [] datas=data.split("\\|");

        Book book=new Book(Integer.parseInt(datas[0]),datas[1],datas[2],datas[3],
                Integer.parseInt(datas[4]),Integer.parseInt(datas[5]));
        return book;

    }
//mảng người đọc từ file
    public ArrayList<BookReaderManagement> readBRMsFromFile(String fileName){

        ArrayList<Book> books=readBooksFromFile("BOOK.txt");
        ArrayList<Reader> readers=readReadersFromFile("READ.txt");



        openFileToRead(fileName);
        ArrayList<BookReaderManagement> brms=new ArrayList<>();
        while (scanner.hasNextLine()){
            String data=scanner.nextLine();
            BookReaderManagement reader= createBRMFromDate(data,readers,books);
            brms.add(reader);
        }
        closeFileAfterReader(fileName);
        return brms;
    }

    public BookReaderManagement createBRMFromDate(String data,ArrayList<Reader> readers,ArrayList<Book> books) {
        String[] datas=data.split("\\|");
        BookReaderManagement brm= new BookReaderManagement(getBook(books,Integer.parseInt(datas[0])),
                        getReader(readers,Integer.parseInt(datas[1])), Integer.parseInt(datas[2]), datas[3],0);
        return brm;

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

    public void updateBRMFile(ArrayList<BookReaderManagement> list,String fileName){
//        xoa bo file cu
        File file=new File(fileName);
        if (file.exists()){
            file.delete();
//            xoa no di;

        }
//        ghi lai file moi
        openFileToWrite(fileName);

        for (BookReaderManagement v:list){

            printWriter.println(v.getReader().getReaderID()+"|"+v.getBook().getBookID()+"|"
                    +v.getNumOffBorrow()+"|"+v.getState());

        }
        closeFileAfterWrite(fileName);


    }

}
