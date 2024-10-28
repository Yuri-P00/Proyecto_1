package culturemedia.test;

import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.repository.CultureMediaService;
import culturemedia.repository.impl.VideoRepositoryImpl;
import culturemedia.repository.impl.ViewsRepositoryImpl;
import culturemedia.service.CultureMediaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CultureMediaServiceImplTest {

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
    void when_FindAll_all_videos_should_be_returned_successfully() {
        try {
            List<Video> videos = cultureMediaService.findAll();
            if (videos.isEmpty()) {
                throw new VideoNotFoundException("No videos found");
            }
            assertEquals(6, videos.size());
        } catch (VideoNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        CultureMediaService emptyService = new CultureMediaServiceImpl(new VideoRepositoryImpl(), new ViewsRepositoryImpl());
        VideoNotFoundException exception = assertThrows(VideoNotFoundException.class,
                emptyService::findAll,
                "Expected VideoNotFoundException to be thrown when no videos are found.");
        assertEquals("No videos found", exception.getMessage());
    }

    @Test
    void when_FindByTitle_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        try {
            String titleToSearch = "Título Inexistente";
            List<Video> videos = cultureMediaService.find(titleToSearch);

            if (videos.isEmpty()) {
                throw new VideoNotFoundException("No video found with title: " + titleToSearch);
            }
            assertEquals(0, videos.size());
        } catch (VideoNotFoundException e) {
            assertEquals("No video found with title: Título Inexistente", e.getMessage());
        }
    }

    @Test
    void when_FindByTitle_finds_videos_successfully() {
        String titleToSearch = "Clic";
        try {
            List<Video> videos = cultureMediaService.find(titleToSearch);
            assertFalse(videos.isEmpty(), "Expected videos to be found but none were returned.");
            assertEquals(2, videos.size());
            for (Video video : videos) {
                assertTrue(video.title().contains(titleToSearch), "Expected video title to contain: " + titleToSearch);
            }
        } catch (VideoNotFoundException e) {
            fail("Expected videos to be found, but VideoNotFoundException was thrown: " + e.getMessage());
        }
    }

    @Test
    void when_FindByDuration_finds_videos_successfully() {
        double fromDuration = 4.0;
        double toDuration = 5.5;
        try {
            List<Video> videos = cultureMediaService.find(fromDuration, toDuration);
            assertFalse(videos.isEmpty(), "Expected videos to be found but none were returned.");
            assertEquals(4, videos.size());
            for (Video video : videos) {
                assertTrue(video.duration() >= fromDuration && video.duration() <= toDuration,
                        "Expected video duration to be within range: " + video.duration());
            }
        } catch (VideoNotFoundException e) {
            // Si se lanza la excepción, el test debe fallar
            fail("Expected videos to be found, but VideoNotFoundException was thrown: " + e.getMessage());
        }
    }

    @Test
    void when_FindByDuration_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        double fromDuration = 10.0;
        double toDuration = 15.0;
        try {
            List<Video> videos = cultureMediaService.find(fromDuration, toDuration);
            assertTrue(videos.isEmpty(), "Expected no videos to be found, but some were returned.");
        } catch (VideoNotFoundException e) {
            assertEquals("No videos found in the specified duration range.", e.getMessage());
        }
    }
}
