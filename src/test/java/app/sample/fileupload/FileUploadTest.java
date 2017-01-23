package app.sample.fileupload;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import app.sample.fileupload.storage.StorageFileNotFoundException;
import app.sample.fileupload.storage.StorageService;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadTest {

    private MockMvc mvc;

    @Mock
    private StorageService storageService;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(
                new FileUploadController(storageService)).build();
    }

    @Test
    public void shouldListAllFiles() throws Exception {
       given(this.storageService.loadAll())
               .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

       this.mvc.perform(get("/sample/file"))
               .andExpect(status().isOk())
       .andExpect(model().attribute("files",
               contains("http://localhost/sample/files/first.txt", "http://localhost/sample/files/second.txt")));
    }

    @Test
    public void shouldSaveUploadFile() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());
        this.mvc.perform(fileUpload("/sample/file").file(multipartFile))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/sample/file"));

        then(this.storageService).should().store(multipartFile);
    }

    @Test
    public void should404WhenMissingFile() throws Exception {
        given(this.storageService.loadAsResource("test.txt"))
                .willThrow(StorageFileNotFoundException.class);

        this.mvc.perform(get("/files/test.txt"))
                .andExpect(status().isNotFound());
    }
}