public class Finance_Information extends Account
{
    private String $DebitCard;
    private String $CreditCard;
    private String $PinPass;
    private String $BankAccount;

    public Finance_Information()
    {
        super();
        $DebitCard = "";
        $CreditCard = "";
        $PinPass = "";
        $BankAccount = "";
    }
    public Finance_Information(String dc, String cc, String pp, String ba)
    {
        super();
        $DebitCard = "0";
        $CreditCard = "0";
        $PinPass = "12345";
        $BankAccount = "0";
    }

    //Mutator
    public void setDebitCard(String dc)
    {
        $DebitCard = dc;
    }
    public void setCreditCard(String cc)
    {
        $CreditCard = cc;
    }
    public void setPinPass(String pp)
    {
        $PinPass = pp;
    }
    public void setBankAccount(String ba)
    {
        $BankAccount = ba;
    }

    //Accessor
    public String getDebitCard()
    {
        return $DebitCard;
    }
    public String getCreditCard()
    {
        return $CreditCard;
    }
    public String getPinPass()
    {
        return $PinPass;
    }
    public String getBankAccount()
    {
        return $BankAccount;
    }
}