package com.snob.busmanagmenttool.service.user.actions;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.FeedbackDTO;
import com.snob.busmanagmenttool.model.entity.user.actions.Feedback;
import com.snob.busmanagmenttool.repository.user.actions.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;

    public List<FeedbackDTO> getAllFeedback() {
        return feedbackRepository.findAll()
                .stream()
                .map(feedback -> modelMapper.map(feedback,FeedbackDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<FeedbackDTO> getFeedbackById(String id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Feedback" +
                " with ID " + id + "not found."));
        return Optional.ofNullable(modelMapper.map(feedback,FeedbackDTO.class));
    }

    public void createFeedback(FeedbackDTO feedback) {
        feedbackRepository.save(modelMapper.map(feedback,Feedback.class));
    }

    public Feedback updateFeedback(String id, Map<String, Object> updatedFields) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback with ID " + id + " not found."));

        FeedbackDTO feedbackDTO = modelMapper.map(feedback, FeedbackDTO.class);

        if (updatedFields.containsKey("title")) {
            feedbackDTO.setTitle((String) updatedFields.get("title"));
        }
        if (updatedFields.containsKey("rating")) {
            feedbackDTO.setRating((int) updatedFields.get("rating"));
        }
        if (updatedFields.containsKey("comment")) {
            feedbackDTO.setComment((String) updatedFields.get("comment"));
        }
        if (updatedFields.containsKey("timestamp")) {
            feedbackDTO.setTimestamp((Timestamp) updatedFields.get("timestamp"));
        }
        if (updatedFields.containsKey("userId")) {
            feedbackDTO.setUserId((Long) updatedFields.get("userId"));
        }

        Feedback updatedFeedback = modelMapper.map(feedbackDTO, Feedback.class);
        return feedbackRepository.save(updatedFeedback);
    }

    public void deleteFeedback(String id) {
        feedbackRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Feedback with ID " +
                id + " not found."));
        feedbackRepository.deleteById(id);
    }
}
