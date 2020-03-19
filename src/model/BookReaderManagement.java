package model;

public class BookReaderManagement {
    private  Book book;
    private Reader reader;
    private int numOffBorrow;
    private String state;
    private int totalBorrow;

    public BookReaderManagement() {
    }

    public BookReaderManagement(Book book, Reader reader,
                                int numOffBorrow, String state, int totalBorrow) {
        this.book = book;
        this.reader = reader;
        this.numOffBorrow = numOffBorrow;
        this.state = state;
        this.totalBorrow = totalBorrow;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getNumOffBorrow() {
        return numOffBorrow;
    }

    public void setNumOffBorrow(int numOffBorrow) {
        this.numOffBorrow = numOffBorrow;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotalBorrow() {
        return totalBorrow;
    }

    public void setTotalBorrow(int totalBorrow) {
        this.totalBorrow = totalBorrow;
    }

    @Override
    public String toString() {
        return "BookReaderManagement{" +
                "readerID=" + reader .getReaderID()+
                ", readerName="+reader.getFullName()+
                ", bookID=" + book.getBookID() +
                ", bookName="+book.getBookName()+
                ", numOffBorrow=" + numOffBorrow +
                ", state='" + state + '\'' +
                ", totalBorrow=" + totalBorrow +
                '}';
    }
}
