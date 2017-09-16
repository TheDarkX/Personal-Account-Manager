public class FinanceInfoClass extends AccountClass
{
    private String $LoggedInUser;
    private String $Title;
    private String $Website;
    private String $Password;
    private String $AdditionalInfo;

    public FinanceInfoClass()
    {
        super();
        $LoggedInUser = "";
        $Title = "";
        $Website = "";
        $Password = "";
        $AdditionalInfo = "";
    }

    //Mutator
    public void setLoggedInUser(String l)
    {
        $LoggedInUser = l;
    }
    public void setTitle(String dc)
    {
        $Title = dc;
    }
    public void setWebsite(String cc)
    {
        $Website = cc;
    }
    public void setPassword(String pp)
    {
        $Password = pp;
    }
    public void setAdditionalInfo(String ba)
    {
        $AdditionalInfo = ba;
    }

    //Accessor
    public String getTitle()
    {
        return $Title;
    }
    public String getWebsite()
    {
        return $Website;
    }
    public String getPassword()
    {
        return $Password;
    }
    public String getAdditionalInfo()
    {
        return $AdditionalInfo;
    }
}