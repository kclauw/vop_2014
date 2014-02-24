package exception;

public class WrongLoginExeption extends IllegalArgumentException{
    public WrongLoginExeption() 
    {
        super("Login Failed: Wrong username or password");
    }

    public WrongLoginExeption(String s) 
    {
        super(s);
    }
}