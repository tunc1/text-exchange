package app.service;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import app.entity.Text;
import app.dto.EncryptedText;
import app.repository.TextRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class TextService
{
	@Value("${text-delete-schedule.minutes-to-delete}")
	private int minutes;
	private TextRepository textRepository;
	private EncryptionService encryptionService;
	public TextService(TextRepository textRepository,EncryptionService encryptionService)
	{
		this.textRepository=textRepository;
		this.encryptionService=encryptionService;
	}
	public Long save(String text,String password)
	{
		EncryptedText encryptedText=encryptionService.encrypt(text,password);
		Text textEntity=new Text();
		textEntity.setVector(encryptedText.vector());
		textEntity.setText(encryptedText.text());
		textEntity.setDate(LocalDateTime.now());
		return textRepository.save(textEntity).getId();
	}
	public String findById(Long id,String password)
	{
		Text text=textRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		return encryptionService.decrypt(text.getText(),text.getVector(),password);
	}
	@Scheduled(fixedRateString = "#{${text-delete-schedule.minutes-to-delete}*60000}")
	@Transactional
	public void deleteOldTexts()
	{
		textRepository.deleteByDateLessThan(LocalDateTime.now().minusMinutes(minutes));
	}
}