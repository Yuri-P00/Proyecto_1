package culturemedia.controller;

import java.util.List;

import culturemedia.exception.DuracionNotValidException;
import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.service.CultureMediaService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin (origins = "*")
public class CultureMediaController {

	private final CultureMediaService cultureMediaService;

	public CultureMediaController(CultureMediaService cultureMediaService) {
		this.cultureMediaService = cultureMediaService;
	}

	@GetMapping("/videos")
	public List<Video> findAllVideos() throws VideoNotFoundException {
		return cultureMediaService.findAll();
	}

	@PostMapping("/videos")
	public Video save(@RequestBody Video video) throws DuracionNotValidException {
		return cultureMediaService.save(video);
	}
}