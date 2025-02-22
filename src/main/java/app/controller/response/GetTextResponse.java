package app.controller.response;

public class GetTextResponse
{
	private String text;
	public GetTextResponse(String text)
	{
		this.text=text;
	}
	public String getText()
    {
        return text;
    }
}