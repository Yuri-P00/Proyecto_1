package culturemedia.service;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.repository.CultureMediaService;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewsRepository;

import java.util.List;

public class CultureMediaServiceImpl implements CultureMediaService {
    private VideoRepository videoRepository;
    private ViewsRepository viewsRepository;

    // Constructor para inyectar los repositorios
    public CultureMediaServiceImpl(VideoRepository videoRepository, ViewsRepository viewsRepository) {
        this.videoRepository = videoRepository;
        this.viewsRepository = viewsRepository;
    }

    @Override
    public List<Video> findAll() throws VideoNotFoundException {
        List<Video> videos = videoRepository.findAll();

        // Si la lista de videos está vacía, lanzar la excepción
        if (videos.isEmpty()) {
            throw new VideoNotFoundException("No videos found");
        }

        return videos;
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public View save(View view) {
        return viewsRepository.save(view);
    }

    @Override
    public List<Video> find(String title) throws VideoNotFoundException {
        List<Video> filteredVideos = videoRepository.find(title);
        if (filteredVideos.isEmpty()) {
            throw new VideoNotFoundException("No video found with title: " + title);
        }
        return filteredVideos;
    }

    @Override
    public List<Video> find(double fromDuration, double toDuration) throws VideoNotFoundException {
        List<Video> filteredVideos = videoRepository.find(fromDuration, toDuration);
        if (filteredVideos.isEmpty()) {
            throw new VideoNotFoundException("No videos found in the specified duration range.");
        }
        return filteredVideos;
    }
}
