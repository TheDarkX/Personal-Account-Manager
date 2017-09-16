public class AccountClass
{
    private String $LoggedInUser;
    private String $Title;
    private String $Username;
    private String $Password;
    private String $Website;
    private String $AdditionalInfo;

    public AccountClass()
    {
        $Title = "";
        $Username = "";
        $Password = "";
        $AdditionalInfo = "";
    }

    //Mutator
    public void setLoggedInUser(String l)
    {
        $LoggedInUser = l;
    }
    public void setTitle(String t)
    {
        $Title = t;
    }
    public void setUsername(String un)
    {
        $Username = un;
    }
    public void setPassword(String pw)
    {
        $Password = pw;
    }
    public void setWebsite(String web)
    {
        $Website = web;
    }
    public void setAdditionalInfo(String an)
    {
        $AdditionalInfo = an;
    }

    //Accessor
    public String getLoggedInUser()
    {
        return $LoggedInUser;
    }
    public String getTitle()
    {
        return $Title;
    }
    public String getUsername()
    {
        return $Username;
    }
    public String getPassword()
    {
        return $Password;
    }
    public String getWebsite()
    {
        return $Website;
    }
    public String getAdditionalNote()
    {
        return $AdditionalInfo;
    }
}