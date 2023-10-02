package com.snob.busmanagmenttool.repository;

import com.snob.busmanagmenttool.model.entity.machinery.RepairDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairDocumentRepository extends MongoRepository<RepairDocument, String> {
    boolean existsByRepairDocNum(String repairDocNum);
}
