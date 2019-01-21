import javax.swing.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Head {

    private JPanel panelMain;
    private JButton button1;
    private JTable table1;
    private JButton sortByRatingButton;
    private JButton sortByNameButton;
    private JButton sortByYearButton;
    private JRadioButton ascedingRadioButton;
    private JRadioButton descendingRadioButton;
    private ArrayList<Movie> main_list=new ArrayList<>();




    public static ArrayList<Movie> Get_Data() throws Exception{
        ArrayList<Movie> movie_list=new ArrayList<>();
        final Document doc = Jsoup.connect("https://www.imdb.com/chart/top").get();
        for(Element row: doc.select("table.chart.full-width tr"))
        {
            String name=row.select(".titleColumn a").text();
            String year=row.select(".secondaryInfo").text();
            String rating=row.select(".imdbRating").text();
            Movie movie=new Movie(name,year,rating);
            movie_list.add(movie);

        }

        return movie_list;

    }




    public Head() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    main_list = Get_Data();
                    Movie_Table table=new Movie_Table(main_list);
                    table1.setModel(table);
                    button1.setEnabled(false);

                }
                catch (Exception e){
                JOptionPane.showMessageDialog(null,e);
                }
            }
        });
        sortByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(ascedingRadioButton.isSelected())
                        Collections.sort(main_list,Movie.Sort_by_Name_Asc);
                    else
                        Collections.sort(main_list,Movie.Sort_by_Name_Desc);


                    Movie_Table table=new Movie_Table(main_list);
                    table1.setModel(table);

                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }
        });
        sortByYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(ascedingRadioButton.isSelected())
                    Collections.sort(main_list,Movie.Sort_by_Year_Asc);
                else
                    Collections.sort(main_list,Movie.Sort_by_Year_Desc);


                Movie_Table table=new Movie_Table(main_list);
                        table1.setModel(table);
            }
        });
        sortByRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(ascedingRadioButton.isSelected())
                    Collections.sort(main_list,Movie.Sort_by_Rating_Asc);
                else
                    Collections.sort(main_list,Movie.Sort_by_Rating_Desc);


                Movie_Table table=new Movie_Table(main_list);
                table1.setModel(table);
            }
        });
        ascedingRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            descendingRadioButton.setSelected(false);
            ascedingRadioButton.setSelected(true);
            }
        });
        descendingRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                descendingRadioButton.setSelected(true);
                ascedingRadioButton.setSelected(false);
            }
        });
    }



    public static void main(String[] args) throws Exception {
        JFrame frame=new JFrame("Web Scraper");
        frame.setContentPane(new Head().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,500);


        frame.setVisible(true);

    }


}
