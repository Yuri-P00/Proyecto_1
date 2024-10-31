package culturemedia.controller;

import java.util.*;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.service.CultureMediaServiceImpl;

public class CultureMediaController {

	private final CultureMediaServiceImpl cultureMediaService;


	public CultureMediaController(CultureMediaServiceImpl cultureMediaService) {
		this.cultureMediaService = cultureMediaService;
	}


	public List<Video> find_allVideos() throws VideoNotFoundException {
		List<Video> videos = null;
		videos = cultureMediaService.findAll();
		return videos;
	}


}
