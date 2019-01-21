import java.util.Comparator;

public class Movie{
    private String name;
    private String year;
    private String rating;



    public Movie()
    {

    }

    public Movie(String name,String year,String rating){
        this.name=name;
        this.year=year;
        this.rating=rating;

    }

    public String getName()
    {
        return name;
    }

    public String getYear()
    {
        return year;
    }

    public String getRating()
    {
        return rating;
    }

    @Override
    public String toString(){
        return name+" "+year+" "+rating+"\n";
    }

    public static Comparator<Movie> Sort_by_Name_Asc = new Comparator<Movie>() {

        public int compare(Movie s1, Movie s2) {
            String StudentName1 = s1.getName().toUpperCase();
            String StudentName2 = s2.getName().toUpperCase();

            return StudentName1.compareTo(StudentName2);


        }};

    public static Comparator<Movie> Sort_by_Name_Desc = new Comparator<Movie>() {

        public int compare(Movie s1, Movie s2) {
            String StudentName1 = s1.getName().toUpperCase();
            String StudentName2 = s2.getName().toUpperCase();

            return StudentName2.compareTo(StudentName1);


        }};


    public static Comparator<Movie> Sort_by_Year_Asc = new Comparator<Movie>() {

        public int compare(Movie s1, Movie s2) {
            String StudentName1 = s1.getYear().toUpperCase();
            String StudentName2 = s2.getYear().toUpperCase();

            return StudentName1.compareTo(StudentName2);


        }};

    public static Comparator<Movie> Sort_by_Year_Desc= new Comparator<Movie>() {

        public int compare(Movie s1, Movie s2) {
            String StudentName1 = s1.getYear().toUpperCase();
            String StudentName2 = s2.getYear().toUpperCase();

            return StudentName2.compareTo(StudentName1);


        }};
    public static Comparator<Movie> Sort_by_Rating_Asc = new Comparator<Movie>() {

        public int compare(Movie s1, Movie s2) {
            String StudentName1 = s1.getRating().toUpperCase();
            String StudentName2 = s2.getRating().toUpperCase();

            return StudentName1.compareTo(StudentName2);


        }};

    public static Comparator<Movie> Sort_by_Rating_Desc = new Comparator<Movie>() {

        public int compare(Movie s1, Movie s2) {
            String StudentName1 = s1.getRating().toUpperCase();
            String StudentName2 = s2.getRating().toUpperCase();

            return StudentName2.compareTo(StudentName1);


        }};
}
