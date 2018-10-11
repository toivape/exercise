package com.ekahau.exercise.controller;

import com.ekahau.exercise.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.ekahau.exercise.TestUtil.jsonBook;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final String TITLE = "A Space Odyssey";
    private static final String AUTHOR = "Arthur C. Clarke";
    private static final int YEAR = 1968;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void giveAddBook_whenTitleParamIsMissing_thenReturnValidationError() throws Exception {
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook(null, AUTHOR, YEAR)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Book title is required")));
    }

    @Test
    public void giveAddBook_whenTitleIsTooShort_thenReturnValidationError() throws Exception {
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook("", AUTHOR, YEAR)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Book title length must be between 1 and 100")));
    }

    @Test
    public void giveAddBook_whenAuthorParamIsMissing_thenReturnValidationError() throws Exception {
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook(TITLE, null, YEAR)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Book author is required")));
    }

    @Test
    public void giveAddBook_whenAuthorIsTooShort_thenReturnValidationError() throws Exception {
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook(TITLE, "", YEAR)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Book author length must be between 1 and 100")));
    }

    @Test
    public void giveAddBook_whenYearIsMissing_thenReturnValidationError() throws Exception {
        doNothing().when(bookService).addBook(any(), any(), any());
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook(TITLE, AUTHOR, null)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Book year is required")));

        verify(bookService, times(0)).addBook(any(), any(), any());
    }


}
