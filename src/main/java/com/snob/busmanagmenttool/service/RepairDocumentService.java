package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.model.dto.RepairDocumentDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.machinery.BusParts;
import com.snob.busmanagmenttool.model.entity.machinery.RepairDocument;
import com.snob.busmanagmenttool.model.entity.user.Repairman;
import com.snob.busmanagmenttool.repository.BusRepository;
import com.snob.busmanagmenttool.repository.RepairDocumentRepository;
import com.snob.busmanagmenttool.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.io.RandomAccessStreamCache;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class RepairDocumentService {
    private final RepairDocumentRepository repository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    void createRepairDocument(RepairDocumentDTO repairDocumentDTO){
        RepairDocument repairDocument = modelMapper.map(repairDocumentDTO, RepairDocument.class);
        repository.save(repairDocument);
    }
    public void createRepairDocumentFromFile(InputStream pdfInputStream) {
        try {
            RepairDocument repairDocument = parseRepairDocumentFromPdf(pdfInputStream);
            repository.save(repairDocument);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse and save Repair Document.", e);
        }
    }

    public RepairDocument parseRepairDocumentFromPdf(InputStream pdfInputStream) throws IOException {
        PDDocument pdfDocument = new PDDocument((RandomAccessStreamCache.StreamCacheCreateFunction) pdfInputStream);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText = pdfTextStripper.getText(pdfDocument);

        RepairDocumentDTO repairDocument = new RepairDocumentDTO();

        Pattern repairDocNumPattern = Pattern.compile("Repair Document Number: (.+)");
        Pattern busPattern = Pattern.compile("Bus: (.+)");
        Pattern startDatePattern = Pattern.compile("Start Date of Repair: (.+)");
        Pattern endDatePattern = Pattern.compile("End Date of Repair: (.+)");
        Pattern busPartsPattern = Pattern.compile("Bus Parts: (.+)");
        Pattern descriptionPattern = Pattern.compile("Description: (.+)");
        Pattern repairmanPattern = Pattern.compile("Repairman: (.+)");
        Pattern pricePattern = Pattern.compile("Price: ([0-9.]+) USD");

        Matcher matcher;

        matcher = repairDocNumPattern.matcher(pdfText);
        if (matcher.find()) {
            repairDocument.setRepairDocNum(matcher.group(1));
        }

        matcher = busPattern.matcher(pdfText);
        if (matcher.find()) {
            String carNum = matcher.group(1);
            Optional<Bus> bus = Optional.ofNullable(busRepository.findByCarNumber(carNum).orElseThrow(() ->
                    new EntityNotFoundException("Bus with car number " + carNum + " not found.")));
            repairDocument.setBusId(bus.get().getId());
        }

        matcher = startDatePattern.matcher(pdfText);
        if (matcher.find()) {
            Timestamp startDate = Timestamp.valueOf(matcher.group(1) + " 00:00:00");
            repairDocument.setStartDateOfRepair(startDate);
        }

        matcher = endDatePattern.matcher(pdfText);
        if (matcher.find()) {
            Timestamp startDate = Timestamp.valueOf(matcher.group(1) + " 00:00:00");
            repairDocument.setEndDateOfRepair(startDate);
        }

        matcher = busPartsPattern.matcher(pdfText);
        if (matcher.find()) {
            String busPartsList = matcher.group(1);
            List<BusParts> parts = new ArrayList<>();
            String[] partsArray = busPartsList.split(", ");
            for (String part : partsArray) {
                parts.add(BusParts.valueOf(part));
            }
            repairDocument.setBusPartsForRepair(parts);
        }

        matcher = descriptionPattern.matcher(pdfText);
        if (matcher.find()) {
            String description = matcher.group(1);
            repairDocument.setDescription(description);
        }

        matcher = repairmanPattern.matcher(pdfText);
        if (matcher.find()) {
            String username = matcher.group(1);
            Optional<Repairman> repairman = Optional.ofNullable((Repairman) userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Repairman with username "
                            + username + " not found.")));
            repairDocument.setRepairmanId(repairman.get().getId());
        }

        matcher = pricePattern.matcher(pdfText);
        if (matcher.find()) {
            Double price = Double.valueOf(matcher.group(1));
            repairDocument.setPrice(price);
        }
        pdfDocument.close();

        return modelMapper.map(repairDocument, RepairDocument.class);
    }
}
