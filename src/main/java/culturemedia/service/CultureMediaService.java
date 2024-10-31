package culturemedia.service;

 R8-Adding–business-logic-to-the-service
import culturemedia.exception.VideoNotFoundException;
=======
main
import culturemedia.model.Video;
import culturemedia.model.View;

import java.util.List;

public interface CultureMediaService {
 R8-Adding–business-logic-to-the-service
    List<Video> findAll() throws VideoNotFoundException;
    Video save (Video save);
    View save (View save);
    List<Video> find(String title) throws VideoNotFoundException;
    List<Video> find(double fromDuration, double toDuration) throws VideoNotFoundException;
}


=======
    List<Video> findAll();
    Video save (Video save);
    View save (View save);
}
 main
