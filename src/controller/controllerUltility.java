package controller;

import model.Book;
import model.BookReaderManagement;

import java.util.ArrayList;

    public class controllerUltility {
    public ArrayList<BookReaderManagement> updateBRMInfo(ArrayList<BookReaderManagement> list, BookReaderManagement brm){
//        ArrayList<BootstrapMethodError> :tham so dau tien la danh sach cac ban muon
//        BootstrapMethodError: tham so 2 la doi tuong

        boolean isUpdate=false;
//        isupdate:da duoc update hay chua
        for (int i=0; i<list.size();i++){
            BookReaderManagement b=list.get(i);
            if (b.getBook().getBookID()== brm.getBook().getBookID()
            && brm.getReader().getReaderID()==brm.getReader().getReaderID()){
                list.set(i,brm);
//                cap nhat lai doi tuong quan li muon
                isUpdate=true;
                break;
            }
        }
        if (!isUpdate){
            list.add(brm);
//            list se them doi tuong vao cuoi
        }
        return list;

    }
        public ArrayList<BookReaderManagement> updateTotalBorrowed(ArrayList<BookReaderManagement> list) {
            for (int i = 0; i < list.size(); i++) {
                BookReaderManagement b = list.get(i);
                int count = 0;
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getReader().getReaderID() == b.getReader().getReaderID()) {
                        count += list.get(j).getTotalBorrow();

                    }
                }
                b.setTotalBorrow(count);
//update:totalborrow;
                list.set(i, b);

            }
            return list;
        }

    public ArrayList<BookReaderManagement> sortByReaderName(ArrayList<BookReaderManagement>list){
        for (int i=0;i<list.size();i++){
            for (int j=list.size()-1;j>i;j--){
                BookReaderManagement b1=list.get(j);
                BookReaderManagement b2=list.get(j-1);
                String [] name1=b1.getReader().getFullName().split("\\s");

                String [] name2=b2.getReader().getFullName().split("\\s");

                if (name1[name1.length-1].compareTo(name2[name2.length-1])<0){
//doi cho
                    list.set(j,b2);
                    list.set(j-1,b1);
                }
            }
        }
        return list;
    }
        public ArrayList<BookReaderManagement> sortByNumOffBorrow(ArrayList<BookReaderManagement>list){
            for (int i=0;i<list.size();i++){
                for (int j=list.size()-1;j>i;j--){
                    BookReaderManagement b1=list.get(j);
                    BookReaderManagement b2=list.get(j-1);
                    if (b1.getTotalBorrow()>b2.getTotalBorrow()){
//doi cho
                        list.set(j,b2);
                        list.set(j-1,b1);
                    }
                }
            }
            return list;
        }
        public ArrayList<BookReaderManagement> searchByReaderName(ArrayList<BookReaderManagement>list,String key){
            ArrayList<BookReaderManagement> result=new ArrayList<>();
            String pattern= ".*" + key + ".*";

            for (int i=0;i<list.size();i++){
                BookReaderManagement b=list.get(i);
                if (b.getReader().getFullName().matches(pattern)){
                    result.add(b);

                }

            }
            return result;
        }

}
