package culturemedia.service;

import culturemedia.exception.DuracionNotValidException;
import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewsRepository;
import culturemedia.service.impl.CultureMediaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CultureMediaServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ViewsRepository viewsRepository;

    @InjectMocks
    private CultureMediaServiceImpl cultureMediaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cultureMediaService = new CultureMediaServiceImpl(videoRepository, viewsRepository);
    }

    @Test
    void when_FindAll_all_videos_should_be_returned_successfully() throws VideoNotFoundException {
        mockFindAll(List.of(new Video("01", "Título 1", "----", 4.5),
                new Video("02", "Título 2", "----", 5.5),
                new Video("03", "Título 3", "----", 4.4),
                new Video("04", "Título 4", "----", 3.5),
                new Video("05", "Clic 5", "----", 5.7),
                new Video("06", "Clic 6", "----", 5.1))
        );
        List<Video> videos = cultureMediaService.findAll();
        assertFalse(videos.isEmpty());
    }

    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        mockFindAll(List.of());
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.findAll());
    }

    @Test
    void when_FindByTitle_only_videos_which_contains_the_word_in_the_title_should_be_returned_successfully() throws VideoNotFoundException {
        mockFindByTitle("Clic", List.of(new Video("05", "Clic 5", "----", 5.7)));
        List<Video> videos = cultureMediaService.find("Clic");
        assertTrue(videos.stream().allMatch(video -> video.title().contains("Clic")));
    }

    @Test
    void when_FindByTitle_does_not_find_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        mockFindByTitle("Título no existe", List.of());
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.find("Inexistent Title"));
    }

    @Test
    void when_FindByDuration_only_videos_between_the_range_should_be_returned_successfully() throws VideoNotFoundException {
        mockFindByDuration(5.0, 6.0, List.of(new Video("05", "Clic 5", "----", 5.7),
                new Video("06", "Clic 6", "----", 5.1)));
        List<Video> videos = cultureMediaService.find(5.0, 6.0);
        assertTrue(videos.stream().allMatch(video -> video.duration() >= 5.0 && video.duration() <= 6.0));
    }

    @Test
    void when_FindByDuration_does_not_match_any_video_an_VideoNotFoundException_should_be_thrown_successfully() {
        mockFindByDuration(10.0, 15.0, List.of());
        assertThrows(VideoNotFoundException.class, () -> cultureMediaService.find(10.0, 15.0));
    }

    @Test
    void when_save_video_with_valid_duration() throws DuracionNotValidException {
        Video video = new Video("07", "Test Video", "----", 3.0);
        mockSaveVideo(video, video);

        Video savedVideo = cultureMediaService.save(video);
        assertEquals(video.duration(), savedVideo.duration());
    }

    @Test
    void when_save_view() {
        View view = new View("Carlos", LocalDateTime.now(), 25, new Video("01", "Test Video", "----", 3.0));
        mockSaveView(view, view);

        View savedView = cultureMediaService.save(view);
        assertEquals(view.userFullName(), savedView.userFullName());
    }

    private void mockFindAll(List<Video> returnVideos) {
        when(videoRepository.findAll()).thenReturn(returnVideos);
    }

    private void mockFindByTitle(String title, List<Video> returnVideos) {
        when(videoRepository.find(title)).thenReturn(returnVideos);
    }

    private void mockFindByDuration(double minDuration, double maxDuration, List<Video> returnVideos) {
        when(videoRepository.find(minDuration, maxDuration)).thenReturn(returnVideos);
    }

    private void mockSaveVideo(Video video, Video savedVideo) {
        when(videoRepository.save(savedVideo)).thenReturn(video);
    }

    private void mockSaveView(View view, View savedView) {
        when(viewsRepository.save(savedView)).thenReturn(view);
    }
}
