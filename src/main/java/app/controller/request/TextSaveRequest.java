package app.controller.request;

public class TextSaveRequest
{
	private String text;
	private String password;
	public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text=text;
    }
	public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
}