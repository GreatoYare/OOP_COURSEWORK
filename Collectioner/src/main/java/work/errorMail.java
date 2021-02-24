package work;

public class errorMail extends Exception
{
    private String message;
    public errorMail(String myMessage)
    {
        this.message = myMessage;
    }
    @Override
    public String getMessage()
    {
        return this.message;
    }
}
