package culturemedia.service;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;

import java.util.List;

public interface CultureMediaService {
    List<Video> findAll() throws VideoNotFoundException;
    Video save (Video save);
    View save (View save);
    List<Video> find(String title) throws VideoNotFoundException;
    List<Video> find(double fromDuration, double toDuration) throws VideoNotFoundException;
}

