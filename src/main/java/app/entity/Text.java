package app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Text
{
    @Id
    @SequenceGenerator(name="TEXT_SEQUENCE_GENERATOR",sequenceName="TEXT_SEQUENCE",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="TEXT_SEQUENCE_GENERATOR")
    private Long id;
	private String text;
	private String vector;
	private LocalDateTime date;
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
	public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text=text;
    }
	public String getVector()
    {
        return vector;
    }
    public void setVector(String vector)
    {
        this.vector=vector;
    }
	public LocalDateTime getDate()
    {
        return date;
    }
    public void setDate(LocalDateTime date)
    {
        this.date=date;
    }
}