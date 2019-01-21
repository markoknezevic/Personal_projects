import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

public class Movie_Table extends AbstractTableModel{
    ArrayList<Movie> list=new ArrayList<>();


    public Movie_Table(ArrayList<Movie> list){
        this.list=list;
    }
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int i, int c) {
        Movie m=list.get(i);

        switch (c){
            case 0:
                return m.getName();
            case 1:
                return m.getYear();
            case 2:
                return m.getRating();
                default:
                    return "";

        }
    }
    @Override
    public String getColumnName(int c) {

        switch (c){
            case 0:
                return "Name";
            case 1:
                return "Year";
            case 2:
                return "Rating";
            default:
                return "";

        }
    }





}
