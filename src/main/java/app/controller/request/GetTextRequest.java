package app.controller.request;

public class GetTextRequest
{
	private Long id;
	private String password;
	public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
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