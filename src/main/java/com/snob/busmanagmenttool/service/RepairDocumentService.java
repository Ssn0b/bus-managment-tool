package com.snob.busmanagmenttool.service;

import com.snob.busmanagmenttool.exception.EntityNotFoundException;
import com.snob.busmanagmenttool.exception.RepairDocumentAlreadyExists;
import com.snob.busmanagmenttool.model.dto.BusDTO;
import com.snob.busmanagmenttool.model.dto.RepairDocumentDTO;
import com.snob.busmanagmenttool.model.dto.RepairmanDTO;
import com.snob.busmanagmenttool.model.entity.machinery.Bus;
import com.snob.busmanagmenttool.model.entity.machinery.BusParts;
import com.snob.busmanagmenttool.model.entity.machinery.RepairDocument;
import com.snob.busmanagmenttool.model.entity.user.Repairman;
import com.snob.busmanagmenttool.repository.BusRepository;
import com.snob.busmanagmenttool.repository.RepairDocumentRepository;
import com.snob.busmanagmenttool.repository.user.RepairmanRepository;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepairDocumentService {
  private final RepairDocumentRepository repository;
  private final BusRepository busRepository;
  private final RepairmanRepository repairmanRepository;
  private final ModelMapper modelMapper;

  void createRepairDocument(RepairDocumentDTO repairDocumentDTO) {
    RepairDocument repairDocument = modelMapper.map(repairDocumentDTO, RepairDocument.class);
    repository.save(repairDocument);
  }

  public void createRepairDocumentFromFile(InputStream pdfFile) throws IOException {
    PDDocument pdfDocument = Loader.loadPDF(pdfFile.readAllBytes());
    PDFTextStripper pdfTextStripper = new PDFTextStripper();
    String pdfText = pdfTextStripper.getText(pdfDocument);
    RepairDocument repairDocument = parseRepairDocumentFromPdf(pdfText);
    pdfDocument.close();
    repository.save(repairDocument);
  }

  public RepairDocument parseRepairDocumentFromPdf(String pdfText) {
    RepairDocumentDTO repairDocument = new RepairDocumentDTO();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
      String repairDocNum = matcher.group(1).trim();
      if (repository.existsByRepairDocNum(repairDocNum)) {
        throw new RepairDocumentAlreadyExists(
            "Repair document " + repairDocNum + " already exists.");
      } else {
        repairDocument.setRepairDocNum(repairDocNum);
      }
    }

    matcher = busPattern.matcher(pdfText);
    if (matcher.find()) {
      String carNum = matcher.group(1).trim();
      Optional<Bus> bus =
          Optional.ofNullable(
              busRepository
                  .findByCarNumber(carNum)
                  .orElseThrow(
                      () ->
                          new EntityNotFoundException(
                              "Bus with car number " + carNum + " not found.")));
      repairDocument.setBus(modelMapper.map(bus.get(), BusDTO.class));
    }

    matcher = startDatePattern.matcher(pdfText);
    if (matcher.find()) {
      try {
        Date date = sdf.parse(matcher.group(1).trim());
        Timestamp timestamp = new Timestamp(date.getTime());
        repairDocument.setStartDateOfRepair(timestamp);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }

    matcher = endDatePattern.matcher(pdfText);
    if (matcher.find()) {
      try {
        Date date = sdf.parse(matcher.group(1).trim());
        Timestamp timestamp = new Timestamp(date.getTime());
        repairDocument.setEndDateOfRepair(timestamp);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }

    matcher = busPartsPattern.matcher(pdfText);
    if (matcher.find()) {
      String busPartsList = matcher.group(1);
      List<BusParts> parts = new ArrayList<>();
      String[] partsArray = busPartsList.trim().split(", ");
      for (String part : partsArray) {
        if (part.contains(" ")) {
          part = part.replace(" ", "_");
        }
        parts.add(BusParts.valueOf(part.toUpperCase()));
      }
      repairDocument.setBusPartsForRepair(parts);
    }

    matcher = descriptionPattern.matcher(pdfText);
    if (matcher.find()) {
      String description = matcher.group(1).trim();
      repairDocument.setDescription(description);
    }

    matcher = repairmanPattern.matcher(pdfText);
    if (matcher.find()) {
      String[] fullName = matcher.group(1).split(" ");
      Optional<Repairman> repairman =
          Optional.ofNullable(
              repairmanRepository
                  .findByFirstnameAndLastname(fullName[0].trim(), fullName[1].trim())
                  .orElseThrow(
                      () ->
                          new EntityNotFoundException(
                              "Repairman with username "
                                  + fullName[0]
                                  + " "
                                  + fullName[1]
                                  + " not found.")));
      repairDocument.setRepairman(modelMapper.map(repairman.get(), RepairmanDTO.class));
    }

    matcher = pricePattern.matcher(pdfText);
    if (matcher.find()) {
      Double price = Double.valueOf(matcher.group(1));
      repairDocument.setPrice(price);
    }

    return modelMapper.map(repairDocument, RepairDocument.class);
  }
}
