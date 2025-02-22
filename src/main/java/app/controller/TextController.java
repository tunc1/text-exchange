package app.controller;

import app.entity.Text;
import app.service.TextService;
import app.controller.request.*;
import app.controller.response.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/text")
public class TextController
{
	private TextService textService;
	public TextController(TextService textService)
	{
		this.textService=textService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public TextSaveResponse save(@RequestBody TextSaveRequest request)
	{
		Long savedId=textService.save(request.getText(),request.getPassword());
		return new TextSaveResponse(savedId);
	}
	@PostMapping("/get")
	public GetTextResponse findById(@RequestBody GetTextRequest request)
	{
		String text=textService.findById(request.getId(),request.getPassword());
		return new GetTextResponse(text);
	}
}