public class Account
{
    private String $Name;
    private String $Username;
    private String $Password;
    private String $Website;
    private String $AdditionalNote;

    public Account()
    {
        $Name = "";
        $Username = "";
        $Password = "";
        $AdditionalNote = "";
    }

    //Mutator
    public void setName(String n)
    {
        $Name = n;
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
    public void setAdditionalNote(String an)
    {
        $AdditionalNote = an;
    }

    //Accessor
    public String getName()
    {
        return $Name;
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
        return $AdditionalNote;
    }
}