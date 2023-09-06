package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.FeedbackDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.user.actions.Feedback;
import com.snob.busmanagmenttool.repository.user.actions.FeedbackRepository;
import com.snob.busmanagmenttool.service.user.actions.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class FeedbackServiceTest {
    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFeedback() {
        when(feedbackRepository.findAll()).thenReturn(List.of(new Feedback()));
        List<FeedbackDTO> result = feedbackService.getAllFeedback();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetFeedbackById() {
        // Create a sample feedback
        Feedback feedback = new Feedback();
        // Set properties of the feedback

        // Create a corresponding DTO for the sample feedback
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        // Set properties of the DTO

        // Mock the FeedbackRepository to return the sample feedback when findById is called
        when(feedbackRepository.findById(anyString())).thenReturn(Optional.of(feedback));

        // Mock the ModelMapper to return the corresponding DTO when map is called
        when(modelMapper.map(any(Feedback.class), eq(FeedbackDTO.class))).thenReturn(feedbackDTO);

        // Call the method under test
        Optional<FeedbackDTO> result = feedbackService.getFeedbackById("sampleId");

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(feedbackDTO, result.get());
    }
}
