package com.snob.busmanagmenttool.repository.user.actions;

import com.snob.busmanagmenttool.model.entity.user.actions.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {}
