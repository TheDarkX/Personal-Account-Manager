public class DiaryClass extends AccountClass implements java.io.Serializable
{
    private String $LoggedInUser;
    private String $Date;
    private String $Weather;
    private String $Diary;

    public DiaryClass()
    {
        super();
        $LoggedInUser = "";
        $Date = "";
        $Weather = "";
        $Diary = "";
    }

    //Mutator
    public void setLoggedInUser(String l)
    {
        $LoggedInUser = l;
    }
    public void setDate(String d)
    {
        $Date = d;
    }
    public void setWeather(String w)
    {
        $Weather = w;
    }
    public void setDiary(String d)
    {
        $Diary = d;
    }

    //Accessor
    public String getLoggedInUser()
    {
        return $LoggedInUser;
    }
    public String getDate()
    {
        return $Date;
    }
    public String getWeather()
    {
        return $Weather;
    }
    public String getDiary()
    {
        return $Diary;
    }
}