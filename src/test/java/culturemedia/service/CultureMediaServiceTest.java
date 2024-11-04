package culturemedia.service;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.repository.impl.VideoRepositoryImpl;
import culturemedia.repository.impl.ViewsRepositoryImpl;
import culturemedia.service.impl.CultureMediaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CultureMediaServiceTest {

    private CultureMediaService cultureMediaService;

    @BeforeEach
    void init(){

        cultureMediaService = new CultureMediaServiceImpl(new VideoRepositoryImpl(),new ViewsRepositoryImpl());

        List<Video> videos = List.of(
                new Video("01", "Título 1", "----", 4.5),
                new Video("02", "Título 2", "----", 5.5),
                new Video("03", "Título 3", "----", 4.4),
                new Video("04", "Título 4", "----", 3.5),
                new Video("05", "Clic 5", "----", 5.7),
                new Video("06", "Clic 6", "----", 5.1)
        );


        for ( Video video : videos ) {
            cultureMediaService.save( video );
        }

    }

    @Test
    void when_FindAll_all_videos_should_be_returned_successfully() throws VideoNotFoundException {
                  List<Video> videos = cultureMediaService.findAll();
                       assertEquals(6, videos.size());
           }


    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        CultureMediaService emptyService = new CultureMediaServiceImpl(new VideoRepositoryImpl(), new ViewsRepositoryImpl());
        VideoNotFoundException exception = assertThrows(VideoNotFoundException.class,
                ()->       emptyService.findAll(),
                "Expected VideoNotFoundException to be thrown when no videos are found.");
        assertEquals("No videos found", exception.getMessage());
    }

    @Test
    void when_FindByTitle_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() throws VideoNotFoundException {
                  String titleToSearch = "Título Inexistente";
        VideoNotFoundException exception = assertThrows(VideoNotFoundException.class,()->cultureMediaService.find(titleToSearch));
        assertEquals("No video found with title: " + titleToSearch, exception.getMessage());
                     }

    @Test
    void when_FindByTitle_finds_videos_successfully() throws VideoNotFoundException {
        String titleToSearch = "Clic";
                   List<Video> videos = cultureMediaService.find(titleToSearch);
            assertFalse(videos.isEmpty(), "Expected videos to be found but none were returned.");
            assertEquals(2, videos.size());

           }

    @Test
    void when_FindByDuration_finds_videos_successfully() throws VideoNotFoundException {
        double fromDuration = 4.0;
        double toDuration = 5.5;
                   List<Video> videos = cultureMediaService.find(fromDuration, toDuration);
            assertFalse(videos.isEmpty(), "Expected videos to be found but none were returned.");
            assertEquals(4, videos.size());

    }

    @Test
    void when_FindByDuration_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() throws VideoNotFoundException {
        double fromDuration = 10.0;
        double toDuration = 15.0;
        assertThrows(VideoNotFoundException.class, () -> { List<Video> videos = cultureMediaService.find(fromDuration, toDuration);
            assertTrue(videos.isEmpty(), "Expected no videos to be found, but some were returned.");
        });
    }
}
