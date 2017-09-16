public class Diary extends Account
{
    private String $Note;
    private String $Date;
    private String $Weather;
    private String $Day;

    public Diary()
    {
        super();
        $Note = "";
        $Date = "";
        $Weather = "";
        $Day = "";
    }
    public Diary(String n, String date, String w, String day)
    {
        super();
        $Note = "Hello World!";
        $Date = "1/1/2013";
        $Weather = "Rainy";
        $Day = "Sunday";
    }

    //Mutator
    public void setNote(String n)
    {
        $Note = n;
    }
    public void setDate(String d)
    {
        $Date = d;
    }
    public void setWeather(String w)
    {
        $Weather = w;
    }
    public void setDay(String d)
    {
        $Day = d;
    }

    //Accessor
    public String getNote()
    {
        return $Note;
    }
    public String getDate()
    {
        return $Date;
    }
    public String getWeather()
    {
        return $Weather;
    }
    public String getDay()
    {
        return $Day;
    }
}